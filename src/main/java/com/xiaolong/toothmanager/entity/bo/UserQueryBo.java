package com.xiaolong.toothmanager.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用来查询用户的类
 * @Author xiaolong
 * @Date 2022/3/28 21:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryBo {

    private Long id;
    private String username;
    private String password;
}
