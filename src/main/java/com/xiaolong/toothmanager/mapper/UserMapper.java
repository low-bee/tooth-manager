package com.xiaolong.toothmanager.mapper;

import com.xiaolong.toothmanager.entity.ddo.UserCheckDo;
import com.xiaolong.toothmanager.entity.userinfo.User;
import com.xiaolong.toothmanager.service.dto.UserDto;
import org.springframework.stereotype.Repository;

/**
 * @Description: 用户查询类
 * @Author xiaolong
 * @Date 2022/1/17 8:40 下午
 */
@Repository
public interface UserMapper {

    /**
     * 通过UserCheckDo查询用户
     */
    User select(UserCheckDo userCheck);

    /**
     * 通过用户名查询用户
     */
    User selectByUserName(String username);


    UserDto findByUsername(String username);

    boolean insertUserDto(UserDto userDto);
}
