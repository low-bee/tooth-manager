package com.xiaolong.toothmanager.service.impl;

import com.xiaolong.toothmanager.entity.ddo.UserHospitalDetailCheckDo;
import com.xiaolong.toothmanager.entity.userinfo.UserHospitalDetail;
import com.xiaolong.toothmanager.mapper.UserHospitalDetailMapper;
import com.xiaolong.toothmanager.service.UserHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: UserHospitalServiceImpl
 * @Author xiaolong
 * @Date 2022/1/17 10:11 下午
 */
@Service
public class UserHospitalServiceImpl implements UserHospitalService {

    @Autowired
    UserHospitalDetailMapper userHospitalDetailMapper;

    @Override
    public List<UserHospitalDetail> selectById(Long id) {
        return userHospitalDetailMapper.select(UserHospitalDetailCheckDo.builder().id(id).build());
    }

    @Override
    public List<UserHospitalDetail> selectByUsername(String username) {
        return userHospitalDetailMapper.select(UserHospitalDetailCheckDo.builder().username(username).build());
    }

}
