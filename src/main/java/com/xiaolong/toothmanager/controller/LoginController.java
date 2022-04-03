package com.xiaolong.toothmanager.controller;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.xiaolong.toothmanager.annotation.AnonymousPostMapping;
import com.xiaolong.toothmanager.common.exception.BadRequestException;
import com.xiaolong.toothmanager.common.lang.Const;
import com.xiaolong.toothmanager.common.lang.Result;
import com.xiaolong.toothmanager.config.RsaProperties;
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
import com.xiaolong.toothmanager.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @GetMapping("/captcha")
    public Result<Map> captcha() throws IOException {
        // key, value
        String key = UUID.randomUUID().toString();
        String code = producer.createText();

        BufferedImage image = producer.createImage(code);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);

        BASE64Encoder base64Encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + base64Encoder.encode(byteArrayOutputStream.toByteArray());

        redisUtil.hset(Const.CAPTCHA_KEY, key, code, LocalTime.of(0, 5));

        return Result.success(
                MapUtil.builder()
                        .put("key", key)
                        .put("base64Img", base64Img)
                        .build()
        );
    }

    @ApiOperation("登录注册")
    @AnonymousPostMapping(value = "/register/test")
    public Result<AuthRegisterDto> getKey(@Validated @RequestBody AuthRegisterDto authRegisterDto, HttpServletRequest request) throws Exception {
        if (StringUtils.isEmpty(authRegisterDto.getPassword())) {
            throw new BadRequestException("参数解析错误!");
        }

        String s = RsaUtils.encryptByPublicKey(RsaProperties.publicKey, authRegisterDto.getPassword());
        authRegisterDto.setPassword(s);

        return Result.success(authRegisterDto);
    }

    @ApiOperation("登录注册")
    @AnonymousPostMapping(value = "/register")
    public boolean register(@Validated @RequestBody AuthRegisterDto authRegisterDto, HttpServletRequest request) throws Exception {

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
        boolean success = registerService.register(authRegisterDto);

        return success;
    }

    @ApiOperation("登录授权")
    @AnonymousPostMapping(value = "/login")
    public Result<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) throws Exception {
        // 密码解密
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        // for test 创建一个 uuid 为1的验证码
        redisUtil.set("12345678", "1");
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
        return Result.success(authInfo);
    }


}
