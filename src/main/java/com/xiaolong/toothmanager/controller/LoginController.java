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
 * @Description: 登录模块
 * @Author xiaolong
 * @Date 2022/1/15 4:37 下午
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "系统：系统授权接口")
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
        // 获取运算的结果
        Captcha captcha = loginProperties.getCaptcha();
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        redisUtils.set(uuid, captchaValue, loginProperties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        return ResponseEntity.ok(
                MapUtil.builder()
                        .put("key", uuid)
                        .put("captchaValue", captchaValue)
                        .put("base64Img",  captcha.toBase64())
                        .build()
        );
    }

    @ApiOperation("登录注册")
    @AnonymousPostMapping(value = "/register/test")
    public ResponseEntity<AuthRegisterDto> getKey(@Validated @RequestBody AuthRegisterDto authRegisterDto, HttpServletRequest request) throws Exception {
        if (StringUtils.isEmpty(authRegisterDto.getPassword())) {
            throw new BadRequestException("参数解析错误!");
        }

        String s = RsaUtils.encryptByPublicKey(RsaProperties.publicKey, authRegisterDto.getPassword());
        authRegisterDto.setPassword(s);

        return new ResponseEntity<>(authRegisterDto, HttpStatus.OK);
    }

    @ApiOperation("登录注册")
    @AnonymousPostMapping(value = "/register")
    public ResponseEntity<Boolean> register(@Validated @RequestBody AuthRegisterDto authRegisterDto, HttpServletRequest request) throws Exception {

        if (StringUtils.isEmpty(authRegisterDto.getPassword())
                || StringUtils.isEmpty(authRegisterDto.getUsername())
                || StringUtils.isEmpty(authRegisterDto.getPhone())) {
            throw new BadRequestException("参数解析错误!");
        }

        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authRegisterDto.getPassword());

        if (StringUtils.isEmpty(password)) {
            throw new BadRequestException("输入的密码为空");
        }
        authRegisterDto.setPassword(password);
        boolean register = registerService.register(authRegisterDto);
        return new ResponseEntity<>(register, register ? HttpStatus.OK: HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public ResponseEntity<Object> getUserInfo() {
        return new ResponseEntity<>(SecurityUtils.getCurrentUser(), HttpStatus.OK);
    }

    @ApiOperation("登录授权")
    @AnonymousPostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) throws Exception {
        // 密码解密
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        // for test 创建一个 uuid 为1的验证码
        // 查询验证码
        String code = (String) redisUtils.get(authUser.getUuid());
        // 清除验证码
        redisUtils.del(authUser.getUuid());

        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // 保存在线信息
        onlineUserService.save(jwtUserDto, token, request);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};
        if (loginProperties.isSingleLogin()) {
            //踢掉之前已经登录的token
            onlineUserService.checkLoginOnUser(authUser.getUsername(), token);
        }
        return ResponseEntity.status(HttpStatus.OK).body(authInfo);
    }

    @ApiOperation("退出登录")
    @AnonymousDeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("登出成功");
    }
}
