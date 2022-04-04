package com.xiaolong.toothmanager.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaolong.toothmanager.service.dto.UserDto;
import com.xiaolong.toothmanager.service.dto.UserHospitalDetailDto;
import org.springframework.stereotype.Repository;

/**
 * @Description: 用户查询类
 * @Author xiaolong
 * @Date 2022/1/17 8:40 下午
 */
@TableName("user_info")
@Repository
public interface UserMapper extends BaseMapper<UserDto> {


    UserDto findByUsername(String username);

    boolean insertUserDto(UserDto userDto);


    boolean insertHospital(UserHospitalDetailDto userHospitalDetailDto);

}
