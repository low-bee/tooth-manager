package com.xiaolong.toothmanager.service;

import com.xiaolong.toothmanager.service.dto.UserHospitalDetailDto;

/**
 * @Description: 医生信息服务类
 * @Author xiaolong
 * @Date 2022/4/10 22:33
 */
public interface HospitalService {

    /**
     * query UserHospitalDetailDto by Long hospitalId
     */
    UserHospitalDetailDto queryUserHospitalDetailDtoByHospitalId(Long hospitalId);

}
