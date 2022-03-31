package com.xiaolong.toothmanager.service;

import com.xiaolong.toothmanager.entity.userinfo.User;
import com.xiaolong.toothmanager.service.dto.UserDto;

/**
 * @Description: userService 接口
 * @Author xiaolong
 * @Date 2022/1/17 8:29 下午
 */

public interface UserService {
    /**
     * 通过id查询用户
     */
    User selectByUserId(Long id);
    /**
     * 通过用户名查询用户
     */
    User selectByUsername(String username);

    /**
     * 通过用户医院查询用户
     */
    User selectByHospital(String hospital);

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    UserDto findByUsername(String username);

    boolean insertUser(UserDto userDto);
}
