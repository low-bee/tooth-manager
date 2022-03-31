package com.xiaolong.toothmanager.security.config;

import com.xiaolong.toothmanager.security.bean.LoginProperties;
import com.xiaolong.toothmanager.security.bean.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 配置文件转换Pojo类的 统一配置 类
 * @Author xiaolong
 * @Date 2022/3/28 22:35
 */
@Configuration
public class ConfigBeanConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "login")
    public LoginProperties loginProperties() {
        return new LoginProperties();
    }

    @Bean("securityProperties")
    @ConfigurationProperties(prefix = "jwt")
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }
}
