package com.xiaolong.toothmanager.entity.system;

import com.xiaolong.toothmanager.service.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * @Description: 用户实体类
 * @Author xiaolong
 * @Date 2022/4/9 18:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseDTO {


//    @Id
//    @NotNull(groups = Update.class)
    @ApiModelProperty(value = "ID", hidden = true)
    private Long id;

    @ApiModelProperty(value = "用户角色")
//    @JoinTable(name = "sys_users_roles",
//            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")})
    private Set<Role> roles;

    @ApiModelProperty(value = "用户岗位")
//    @JoinTable(name = "sys_users_jobs",
//            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "job_id",referencedColumnName = "job_id")})
    private Set<Job> jobs;

    @ApiModelProperty(value = "用户部门")
    private Dept dept;

    @NotBlank
    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "用户性别")
    private String gender;

    @ApiModelProperty(value = "头像真实名称",hidden = true)
    private String avatarName;

    @ApiModelProperty(value = "头像存储的路径", hidden = true)
    private String avatarPath;

    @ApiModelProperty(value = "密码")
    private String password;

    @NotNull
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "是否为admin账号", hidden = true)
    private Boolean isAdmin = false;

    @ApiModelProperty(value = "最后修改密码的时间", hidden = true)
    private Date pwdResetTime;
}
