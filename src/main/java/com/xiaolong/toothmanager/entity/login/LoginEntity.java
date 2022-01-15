package com.xiaolong.toothmanager.entity.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 登录校验实体类
 * @Author xiaolong
 * @Date 2022/1/15 8:55 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginEntity {

    private String username;
    private String password;
    private String code;
    private String key;
    private String token;

}
