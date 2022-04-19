package com.xiaolong.toothmanager;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description: 测试加密解密器
 * @Author xiaolong
 * @Date 2022/3/31 22:16
 */
@Slf4j
public class TestPassEncoder {


    @Test
    public void testEncoder(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String chuanzhi = passwordEncoder.encode("chuanzhi");
        log.info(chuanzhi);
        Assert.assertTrue(passwordEncoder.matches("chuanzhi" , chuanzhi));

    }
    @Test
    public void testEncoderByDatabase(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String chuanzhi = "$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa";
        log.info(chuanzhi);
        Assert.assertTrue(passwordEncoder.matches("123456" , chuanzhi));
    }



}
