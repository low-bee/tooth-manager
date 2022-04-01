package com.xiaolong.toothmanager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author xiaolong
 * @Date 2022/3/29 22:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginCacheTest {

    private  PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private  PasswordEncoder bCryptPasswordEncoder2 = new BCryptPasswordEncoder();

    private String test;
    private String chuanzhi;

    @Before
    public void init(){
        chuanzhi = bCryptPasswordEncoder.encode("chuanzhi");
        test = chuanzhi;
    }

    @Test
    public void test(){
        Assert.assertTrue(bCryptPasswordEncoder.matches("chuanzhi", chuanzhi));
        Assert.assertTrue(bCryptPasswordEncoder2.matches("chuanzhi", chuanzhi));
        Assert.assertTrue(bCryptPasswordEncoder.matches("chuanzhi", test));
        Assert.assertTrue(bCryptPasswordEncoder2.matches("chuanzhi", test));
    }


}
