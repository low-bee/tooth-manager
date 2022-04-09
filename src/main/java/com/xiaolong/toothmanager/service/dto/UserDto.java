package com.xiaolong.toothmanager.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaolong.toothmanager.entity.system.RoleSmallDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author xiaolong
 * @Date 2022/3/28 23:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseDTO implements Serializable {

    private Long id;

    private Set<RoleSmallDto> roles;

    private Set<JobSmallDto> jobs;

    private DeptSmallDto dept;

    private Integer level;

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

    private Timestamp pwdResetTime;

    private Integer percentage;

    private UserHospitalDetailDto userHospitalDetail;
}
