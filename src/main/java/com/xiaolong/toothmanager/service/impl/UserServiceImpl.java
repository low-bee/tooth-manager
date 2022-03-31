package com.xiaolong.toothmanager.service.impl;

import com.xiaolong.toothmanager.entity.ddo.UserCheckDo;
import com.xiaolong.toothmanager.entity.userinfo.User;
import com.xiaolong.toothmanager.mapper.UserMapper;
import com.xiaolong.toothmanager.service.UserService;
import com.xiaolong.toothmanager.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Description: UserServices 实现类
 * @Author xiaolong
 * @Date 2022/1/17 8:43 下午
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;


    @Override
    public User selectByUserId(Long id) {
        return userMapper.select(UserCheckDo.builder().id(id).build());
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.select(UserCheckDo.builder().username(username).build());
    }

    @Override
    public User selectByHospital(String hospital) {
        return userMapper.select(UserCheckDo.builder().hospital(hospital).build());
    }

    @Override
    public UserDto findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public boolean insertUser(UserDto userDto) {
        return userMapper.insertUserDto(userDto);
    }
}
