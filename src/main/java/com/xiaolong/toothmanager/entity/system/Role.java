package com.xiaolong.toothmanager.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiaolong.toothmanager.service.dto.BaseDTO;
import com.xiaolong.toothmanager.utils.DataScopeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @Description: 部门角色表
 * @Author xiaolong
 * @Date 2022/4/9 18:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends BaseDTO {
//    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "ID", hidden = true)
    private Long id;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "用户", hidden = true)
    private Set<User> users;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "sys_roles_menus",
//            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")},
//            inverseJoinColumns = {@JoinColumn(name = "menu_id",referencedColumnName = "menu_id")})
    @ApiModelProperty(value = "菜单", hidden = true)
    private Set<Menu> menus;

//    @ManyToMany
//    @JoinTable(name = "sys_roles_depts",
//            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")},
//            inverseJoinColumns = {@JoinColumn(name = "dept_id",referencedColumnName = "dept_id")})
    @ApiModelProperty(value = "部门", hidden = true)
    private Set<Dept> depts;

    @NotBlank
    @ApiModelProperty(value = "名称", hidden = true)
    private String name;

    @ApiModelProperty(value = "数据权限，全部 、 本级 、 自定义")
    private String dataScope = DataScopeEnum.THIS_LEVEL.getValue();

    @ApiModelProperty(value = "级别，数值越小，级别越大")
    private Integer level = 3;

    @ApiModelProperty(value = "描述")
    private String description;
}