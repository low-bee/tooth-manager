package com.xiaolong.toothmanager.service;

import com.xiaolong.toothmanager.entity.ddo.UserHospitalDetailCheckDo;
import com.xiaolong.toothmanager.entity.userinfo.UserHospitalDetail;

import java.util.List;

public interface UserHospitalService {

    List<UserHospitalDetail> selectById(Long id);

    List<UserHospitalDetail> selectByUsername(String username);
}
