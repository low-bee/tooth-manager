package com.xiaolong.toothmanager.entity.userinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用户所属医院信息
 * @Author xiaolong
 * @Date 2022/1/17 8:21 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHospitalDetail {

    private Long id;
    private String username;
    private String hospital;
    private String address;
    private String addressDetail;
}
