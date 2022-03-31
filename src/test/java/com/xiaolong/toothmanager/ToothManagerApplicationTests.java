package com.xiaolong.toothmanager;

import com.xiaolong.toothmanager.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@RequiredArgsConstructor
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToothManagerApplicationTests {

    private final RedisUtil redisUtil;

    @Test
    void contextLoads() {
        // 创建一个uuid为1 的验证码
        redisUtil.set("1","1");
    }

}
