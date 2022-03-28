package com.xiaolong.toothmanager.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author xiaolong
 * @Date 2022/3/28 23:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends BaseDTO implements Serializable {

    private Long id;

//    private Set<RoleSmallDto> roles;
//
//    private Set<JobSmallDto> jobs;
//
//    private DeptSmallDto dept;

    private Long deptId;

    private String username;

    private String nickName;

    private String email;

    private String phone;

    private String gender;

    private String avatarName;

    private String avatarPath;

    @JSONField(serialize = false)
    private String password;

    private Boolean enabled;

    @JSONField(serialize = false)
    private Boolean isAdmin = false;

    private Date pwdResetTime;
}