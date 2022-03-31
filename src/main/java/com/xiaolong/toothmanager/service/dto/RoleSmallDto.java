package com.xiaolong.toothmanager.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author xiaolong
 * @Date 2022/3/29 20:20
 */
@Data
public class RoleSmallDto implements Serializable {
    private Long id;

    private String name;

    private Integer level;

    private String dataScope;
}
