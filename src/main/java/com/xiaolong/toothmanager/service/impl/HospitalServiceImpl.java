package com.xiaolong.toothmanager.service.impl;

import com.xiaolong.toothmanager.mapper.UserHospitalMapper;
import com.xiaolong.toothmanager.service.HospitalService;
import com.xiaolong.toothmanager.service.dto.UserHospitalDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Description: 医院服务实现类
 * @Author xiaolong
 * @Date 2022/4/10 22:39
 */
@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final UserHospitalMapper userHospitalMapper;

    @Override
    public UserHospitalDetailDto queryUserHospitalDetailDtoByHospitalId(Long hospitalId) {
        return userHospitalMapper.selectById(hospitalId);
    }
}
