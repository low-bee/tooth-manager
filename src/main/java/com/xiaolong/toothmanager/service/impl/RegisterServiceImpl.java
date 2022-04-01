package com.xiaolong.toothmanager.service.impl;

import cn.hutool.core.lang.Assert;
import com.xiaolong.toothmanager.service.RegisterService;
import com.xiaolong.toothmanager.service.UserService;
import com.xiaolong.toothmanager.service.dto.AuthRegisterDto;
import com.xiaolong.toothmanager.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @Description: 注册服务实现类
 * @Author xiaolong
 * @Date 2022/3/31 08:46
 */
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public boolean register(AuthRegisterDto authRegisterDto) {

        String encodePassword = bCryptPasswordEncoder.encode(authRegisterDto.getPassword());
        Assert.isTrue(bCryptPasswordEncoder.matches(authRegisterDto.getPassword(), encodePassword));
        authRegisterDto.setPassword(encodePassword);

        UserDto userDto = authRegisterDto.toUserDto();
        userDto.setLevel(1);
        userDto.setPercentage(0);
        userDto.setIsAdmin(false);
        userDto.setEnabled(true);

        try {
            userService.insertUser(userDto);
        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }
}
