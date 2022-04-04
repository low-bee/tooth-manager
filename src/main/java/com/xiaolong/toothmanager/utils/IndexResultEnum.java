package com.xiaolong.toothmanager.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 首页展示信息
 * @Author xiaolong
 * @Date 2022/4/4 10:39
 */
@Slf4j
@AllArgsConstructor
public enum IndexResultEnum {
    USER_NUMBER("user_num");

    private final String key;


    public String getKey(){
        return key;
    }
}
