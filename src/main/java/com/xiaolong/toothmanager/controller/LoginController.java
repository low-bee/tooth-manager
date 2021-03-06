package com.xiaolong.toothmanager.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.google.code.kaptcha.Producer;
import com.wf.captcha.base.Captcha;
import com.xiaolong.toothmanager.annotation.AnonymousDeleteMapping;
import com.xiaolong.toothmanager.annotation.AnonymousGetMapping;
import com.xiaolong.toothmanager.annotation.AnonymousPostMapping;
import com.xiaolong.toothmanager.common.exception.BadRequestException;
import com.xiaolong.toothmanager.config.RsaProperties;
import com.xiaolong.toothmanager.security.bean.LoginCodeEnum;
import com.xiaolong.toothmanager.security.bean.LoginProperties;
import com.xiaolong.toothmanager.security.bean.SecurityProperties;
import com.xiaolong.toothmanager.security.secrity.TokenProvider;
import com.xiaolong.toothmanager.service.RegisterService;
import com.xiaolong.toothmanager.service.dto.AuthRegisterDto;
import com.xiaolong.toothmanager.service.dto.AuthUserDto;
import com.xiaolong.toothmanager.service.dto.JwtUserDto;
import com.xiaolong.toothmanager.service.impl.OnlineUserService;
import com.xiaolong.toothmanager.utils.RedisUtil;
import com.xiaolong.toothmanager.utils.RsaUtils;
import com.xiaolong.toothmanager.utils.SecurityUtils;
import com.xiaolong.toothmanager.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description: ????????????
 * @Author xiaolong
 * @Date 2022/1/15 4:37 ??????
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "???????????????????????????")
public class LoginController extends BaseController {

    private final SecurityProperties properties;
    private final RedisUtil redisUtils;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RegisterService registerService;

    @Resource
    private LoginProperties loginProperties;

    Producer producer;

    @AnonymousGetMapping("/captcha")
    public ResponseEntity<Map<Object, Object>> captcha() {
        // key, value
        // ?????????????????????
        Captcha captcha = loginProperties.getCaptcha();
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        //????????????????????? arithmetic???????????? >= 2 ??????captcha.text()??????????????????????????????
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // ??????
        redisUtils.set(uuid, captchaValue, loginProperties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        return ResponseEntity.ok(
                MapUtil.builder()
                        .put("key", uuid)
                        .put("captchaValue", captchaValue)
                        .put("base64Img",  captcha.toBase64())
                        .build()
        );
    }

    @ApiOperation("????????????")
    @AnonymousPostMapping(value = "/register/test")
    public ResponseEntity<AuthRegisterDto> getKey(@Validated @RequestBody AuthRegisterDto authRegisterDto, HttpServletRequest request) throws Exception {
        if (StringUtils.isEmpty(authRegisterDto.getPassword())) {
            throw new BadRequestException("??????????????????!");
        }

        String s = RsaUtils.encryptByPublicKey(RsaProperties.publicKey, authRegisterDto.getPassword());
        authRegisterDto.setPassword(s);

        return new ResponseEntity<>(authRegisterDto, HttpStatus.OK);
    }

    @ApiOperation("????????????")
    @AnonymousPostMapping(value = "/register")
    public ResponseEntity<Boolean> register(@Validated @RequestBody AuthRegisterDto authRegisterDto, HttpServletRequest request) throws Exception {

        if (StringUtils.isEmpty(authRegisterDto.getPassword())
                || StringUtils.isEmpty(authRegisterDto.getUsername())
                || StringUtils.isEmpty(authRegisterDto.getPhone())) {
            throw new BadRequestException("??????????????????!");
        }

        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authRegisterDto.getPassword());

        if (StringUtils.isEmpty(password)) {
            throw new BadRequestException("?????????????????????");
        }
        authRegisterDto.setPassword(password);
        boolean register = registerService.register(authRegisterDto);
        return new ResponseEntity<>(register, register ? HttpStatus.OK: HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("??????????????????")
    @GetMapping(value = "/info")
    public ResponseEntity<Object> getUserInfo() {
        return new ResponseEntity<>(SecurityUtils.getCurrentUser(), HttpStatus.OK);
    }

    @ApiOperation("????????????")
    @AnonymousPostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) throws Exception {
        // ????????????
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        // for test ???????????? uuid ???1????????????
        // ???????????????
        String code = (String) redisUtils.get(authUser.getUuid());
        // ???????????????
        redisUtils.del(authUser.getUuid());

        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("??????????????????????????????");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("???????????????");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // ??????????????????
        onlineUserService.save(jwtUserDto, token, request);
        // ?????? token ??? ????????????
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};
        if (loginProperties.isSingleLogin()) {
            //???????????????????????????token
            onlineUserService.checkLoginOnUser(authUser.getUsername(), token);
        }
        return ResponseEntity.status(HttpStatus.OK).body(authInfo);
    }

    @ApiOperation("????????????")
    @AnonymousDeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("????????????");
    }
}
