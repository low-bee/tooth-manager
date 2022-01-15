package com.xiaolong.toothmanager.controller;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.xiaolong.toothmanager.common.lang.Const;
import com.xiaolong.toothmanager.common.lang.Result;
import com.xiaolong.toothmanager.entity.login.LoginEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Map;
import java.util.UUID;

/**
 * @Description: 登录模块
 * @Author xiaolong
 * @Date 2022/1/15 4:37 下午
 */
@Slf4j
@RestController
public class LoginController extends BaseController {

    @Autowired
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

    @PostMapping(value = "/login")
    public Result login(
//            @RequestParam("username") String username,
//            @RequestParam("password") String password,
//            @RequestParam("code") String code,
//            @RequestParam("token") String token,
//            @RequestParam("key") String key
            @RequestBody LoginEntity loginEntity){
        System.out.println(loginEntity.getUsername());
        log.info(loginEntity.getCode());
        return Result.success(loginEntity.getToken());
    }
}
