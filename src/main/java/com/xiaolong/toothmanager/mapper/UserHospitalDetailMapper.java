package com.xiaolong.toothmanager.mapper;

import com.xiaolong.toothmanager.entity.ddo.UserHospitalDetailCheckDo;
import com.xiaolong.toothmanager.entity.userinfo.UserHospitalDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 用户 医院详情 Mapper
 * @Author xiaolong
 * @Date 2022/1/17 10:04 下午
 */
@Repository
public interface UserHospitalDetailMapper {

    List<UserHospitalDetail> select(UserHospitalDetailCheckDo userHospitalDetailCheckDo);
}
