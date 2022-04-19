package com.xiaolong.toothmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaolong.toothmanager.common.lang.Const;
import com.xiaolong.toothmanager.entity.system.RoleSmallDto;
import com.xiaolong.toothmanager.mapper.UserHospitalMapper;
import com.xiaolong.toothmanager.mapper.UserMapper;
import com.xiaolong.toothmanager.service.RoleService;
import com.xiaolong.toothmanager.service.UserService;
import com.xiaolong.toothmanager.service.dto.UserDto;
import com.xiaolong.toothmanager.service.dto.UserHospitalDetailDto;
import com.xiaolong.toothmanager.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: UserServices 实现类
 * @Author xiaolong
 * @Date 2022/1/17 8:43 下午
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserHospitalMapper userHospitalMapper;
    private final RedisUtil redisUtil;

    private final RoleService roleService;


    @Override
    public UserDto findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public boolean insertUser(UserDto userDto) {
        return userMapper.insertUserDto(userDto);
    }

    @Override
    public Integer getUserNumber() {
        Wrapper<UserDto> userDtoWrapper = new QueryWrapper<>();
        return userMapper.selectCount(userDtoWrapper);
    }

    @Override
    public UserDto findByUserId(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void insertHospital(UserHospitalDetailDto userHospitalDetailDto) {
        try {
            userHospitalMapper.insert(userHospitalDetailDto);
            redisUtil.set(Const.PREFIX_HOSPITAL + userHospitalDetailDto.getUsername(), userHospitalDetailDto);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void updateHospitalByUserId(Long userId, UserHospitalDetailDto userHospitalDetailDto) {
        try {

            userHospitalMapper.updateByUserId(userHospitalDetailDto);
            redisUtil.set(Const.PREFIX_HOSPITAL + userHospitalDetailDto.getUsername(), userHospitalDetailDto);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public Boolean addUserRole(Long userId, Long roleId) {
        Set<Long> collect = roleService.findByUsersId(userId).stream().map(RoleSmallDto::getId).collect(Collectors.toSet());
        UserDto userDto = userMapper.selectById(userId);

        if (collect.size() > 0 && Objects.nonNull(userDto) && collect.contains(roleId)) {
            // 更新映射关系
            userMapper.insertUserRole(userId, roleId);
            return true;
        }

        return false;
    }

    @Override
    public Boolean deleteUserRole(Long userId, Long roleId) {
        Set<Long> collect = roleService.findByUsersId(userId).stream().map(RoleSmallDto::getId).collect(Collectors.toSet());
        UserDto userDto = userMapper.selectById(userId);

        if (collect.size() > 0 && Objects.nonNull(userDto) && collect.contains(roleId)) {
            // 更新映射关系
            userMapper.deleteUserRole(userId, roleId);
            return true;
        }
        return false;
    }
}
