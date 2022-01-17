package com.xiaolong.toothmanager.entity.ddo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用户查询类
 * @Author xiaolong
 * @Date 2022/1/17 8:52 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCheckDo {

    private Long id;
    private String username;
    private String hospital;
}
