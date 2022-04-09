package com.xiaolong.toothmanager.entity.system;

import lombok.Data;

/**
 * @Description: 用lombox注解来描述实体类
 * @Author xiaolong
 * @Date 2022/4/9 21:27
 */
@Data
public class RoleSmallDto {
    private Long id;
    private String roleName;
    private Integer level;
    private String dataScope;

}
