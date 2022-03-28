package com.xiaolong.toothmanager.service.impl;

import com.xiaolong.toothmanager.entity.bo.LoginUserDetailsImpl;
import com.xiaolong.toothmanager.entity.userinfo.User;
import com.xiaolong.toothmanager.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Description: 登录Service
 * @Author xiaolong
 * @Date 2022/1/15 10:49 下午
 */
@Service("loginService")
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        User user = userMapper.selectByUserName(username);
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("用户名或密码错误！");
        }

        // 封装为UserDetail返回
        LoginUserDetailsImpl userDetails = LoginUserDetailsImpl.builder().user(user).build();


        return userDetails;
    }
}
