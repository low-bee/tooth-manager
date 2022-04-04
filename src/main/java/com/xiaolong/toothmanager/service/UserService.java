package com.xiaolong.toothmanager.service;

import com.xiaolong.toothmanager.service.dto.UserDto;
import com.xiaolong.toothmanager.service.dto.UserHospitalDetailDto;

/**
 * @Description: userService 接口
 * @Author xiaolong
 * @Date 2022/1/17 8:29 下午
 */

public interface UserService {

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    UserDto findByUsername(String username);

    boolean insertUser(UserDto userDto);

    Integer getUserNumber();

    UserDto findByUserId(Long id);

    void insertHospital(UserHospitalDetailDto userHospitalDetailDto);

    void updateHospitalByUserId(Long userId, UserHospitalDetailDto userHospitalDetailDto);
}
