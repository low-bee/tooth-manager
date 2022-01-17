package com.xiaolong.toothmanager.entity.ddo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用户医院do类
 * @Author xiaolong
 * @Date 2022/1/17 10:09 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHospitalDetailCheckDo {

    private Long id;
    private String username;
}
