package com.xiaolong.toothmanager.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaolong.toothmanager.service.dto.UserHospitalDetailDto;
import org.springframework.stereotype.Repository;

/**
 * @Description: 用户医院mapper实现类
 * @Author xiaolong
 * @Date 2022/4/4 21:55
 */

@TableName("user_hospital_detail")
@Repository
public interface UserHospitalMapper extends BaseMapper<UserHospitalDetailDto>  {
    void updateByUserId(UserHospitalDetailDto userHospitalDetailDto);
}
