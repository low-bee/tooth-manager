package com.xiaolong.toothmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: 本地ip配置对象
 * @Author xiaolong
 * @Date 2022/3/28 23:22
 */
@Component
public class ToothManagerProperties {

    public static Boolean ipLocal;

    @Value("${ip.local-parsing}")
    public void setIpLocal(Boolean ipLocal) {
        ToothManagerProperties.ipLocal = ipLocal;
    }
}
