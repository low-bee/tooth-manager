package com.xiaolong.toothmanager.entity.userinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: user info
 * @Author xiaolong
 * @Date 2022/1/17 8:17 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 账户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像地址
     */
    @JsonProperty("avatar")
    private String avatarUrl;
    private Integer level;
    private String gender;
    private String phone;
    private String email;
    private String telephone;
    private String percentage;
    /**
     * 用户类型 医生、管理员、病人
     */
    private String nickName;
    /**
     * 用户地址详情
     */
    @JsonProperty("detail")
    private List<UserHospitalDetail> userHospitalDetails;

}
