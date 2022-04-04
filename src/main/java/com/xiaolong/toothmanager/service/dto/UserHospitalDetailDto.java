package com.xiaolong.toothmanager.service.dto;

import lombok.*;

/**
 * @Description: 用户 hospital detail详情类，通过username关联 one - one
 * @Author xiaolong
 * @Date 2022/4/4 11:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHospitalDetailDto extends BaseDTO{
    private Long id;
    private Long userId;
    private String username;
    private String hospital;
    private String province;
    private String city;
    // 类似于区/县等 成都 -> 金堂县  成都 -> 双流
    private String lowCity;
    private String addressDetail;
}
