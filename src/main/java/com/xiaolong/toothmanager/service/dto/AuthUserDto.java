package com.xiaolong.toothmanager.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 用户校验账号密码类
 * @Author xiaolong
 * @Date 2022/3/29 19:57
 */
@Data
public class AuthUserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";
}
