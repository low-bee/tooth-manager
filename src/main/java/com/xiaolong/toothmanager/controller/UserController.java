package com.xiaolong.toothmanager.controller;

import com.xiaolong.toothmanager.common.exception.BadRequestException;
import com.xiaolong.toothmanager.common.lang.Result;
import com.xiaolong.toothmanager.service.HospitalService;
import com.xiaolong.toothmanager.service.UserService;
import com.xiaolong.toothmanager.service.dto.UserHospitalDetailDto;
import com.xiaolong.toothmanager.utils.RedisUtil;
import com.xiaolong.toothmanager.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Description: 用户Controller
 * @Author xiaolong
 * @Date 2022/1/15 6:56 下午
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Api(tags = "首页：首页信息展示")
public class UserController {

    private final UserService userService;
    private final HospitalService hospitalService;
    private final RedisUtil redisUtil;
    private static final String PRE = "override";
    private static final String HOSPITAL = "override";

    @ApiOperation("首页展示")
    @GetMapping(value = "/override")
    public Result<Object> getUserNumber() {
        UserDetails currentUser = SecurityUtils.getCurrentUser();

        Object o = redisUtil.get(PRE + userService.getUserNumber());

        if (Objects.isNull(o)){
            // 查数据库，存redis;
            Integer userNumber = userService.getUserNumber();
        }

        return Result.success(currentUser);
    }

    @ApiOperation("插入用户的医生信息")
    @GetMapping(value = "/update/hospital")
    public Result<Object> updateHospital(UserHospitalDetailDto userHospitalDetailDto){
        // 如果是医生的话
        if (Objects.isNull(userHospitalDetailDto.getUserId())){
            throw new BadRequestException("传入参数userId为空");
        }
        Long userId = userHospitalDetailDto.getUserId();
        // find UserHospitalDetailDto by hospitalService id
        UserHospitalDetailDto userDto = hospitalService.queryUserHospitalDetailDtoByHospitalId(userId);

        // if userDto is null insert 用户医生信息
        // else update userHospitalDetailDto
        if (Objects.isNull(userDto)){
            // 插入
            userService.insertHospital(userHospitalDetailDto);
        } else {
            // 更新
            userService.updateHospitalByUserId(userHospitalDetailDto.getUserId(), userHospitalDetailDto);
        }
        return Result.success(true);
    }

}
