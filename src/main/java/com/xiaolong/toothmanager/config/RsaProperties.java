package com.xiaolong.toothmanager.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: Rsa 配置类
 * @Author xiaolong
 * @Date 2022/3/29 19:53
 */
@Data
@Component
public class RsaProperties {

    public static String privateKey;
    public static String publicKey;

    @Value("${rsa.private_key}")
    public void setPrivateKey(String privateKey) {
        RsaProperties.privateKey = privateKey;
    }

    @Value("${rsa.public_key}")
    public void setPublicKey(String publicKey) {
        RsaProperties.publicKey = publicKey;
    }
}
