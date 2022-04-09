package com.xiaolong.toothmanager.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 用户注册实体类
 * @Author xiaolong
 * @Date 2022/3/29 23:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthRegisterDto extends BaseDTO{

    private Long id;

    private String avatarUrl;
    private Integer level;
    private String gender;
    private String nickName;
    private boolean enable;
    private DeptSmallDto deptSmallDto;

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String phone;

    private String email;

    private String dept;




    public UserDto toUserDto() {
        return UserDto.builder()
                .username(this.username)
                .password(this.password)
                .phone(this.phone)
                .email(this.email)
                .avatarPath(this.avatarUrl)
                .level(this.level)
                .gender(this.gender)
                .nickName(this.nickName)
                .enabled(this.enable).build();

    }
}
