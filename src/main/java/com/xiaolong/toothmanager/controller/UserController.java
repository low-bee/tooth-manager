package com.xiaolong.toothmanager.controller;

import com.xiaolong.toothmanager.common.lang.Result;
import com.xiaolong.toothmanager.entity.userinfo.User;
import com.xiaolong.toothmanager.service.UserService;
import com.xiaolong.toothmanager.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 用户Controller
 * @Author xiaolong
 * @Date 2022/1/15 6:56 下午
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/sys/userInfo")
    Result<User> getUserInfo(HttpServletRequest request){

        String username = jwtUtils.getUsername(request);

        User user = userService.selectByUsername(username);

        return Result.success(
            user
        );
    }



}