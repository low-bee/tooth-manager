package com.xiaolong.toothmanager.service;

import com.xiaolong.toothmanager.service.dto.AuthRegisterDto;

/**
 * @Description: 注册Service
 * @Author xiaolong
 * @Date 2022/3/31 08:43
 */
public interface RegisterService {

    // 注册
    boolean register(AuthRegisterDto authRegisterDto) ;
}
