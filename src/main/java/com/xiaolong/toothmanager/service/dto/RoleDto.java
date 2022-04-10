package com.xiaolong.toothmanager.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;

/**
 * @Description: 角色DTO
 * @Author xiaolong
 * @Date 2022/3/29 20:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDto extends BaseDTO implements Serializable {

    private Long id;

    private Set<MenuDto> menus;

    private Set<DeptDto> depts;

    private String name;

    private String dataScope;

    private Integer level;

    private String description;
}
