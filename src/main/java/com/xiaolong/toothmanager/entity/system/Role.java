package com.xiaolong.toothmanager.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.xiaolong.toothmanager.service.dto.BaseDTO;
import com.xiaolong.toothmanager.service.dto.DeptDto;
import com.xiaolong.toothmanager.service.dto.MenuDto;
import com.xiaolong.toothmanager.service.dto.RoleDto;
import com.xiaolong.toothmanager.utils.DataScopeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 部门角色表
 * @Author xiaolong
 * @Date 2022/4/9 18:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseDTO {
//    @NotNull(groups = {Update.class})
//    @ApiModelProperty(value = "ID", hidden = true)
//    @TableId("role_id")
    @TableField("role_id")
    private Long roleId;

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

    public static RoleDto toDo(Role role) {

        Set<MenuDto> menus = role.getMenus().stream().map(Menu::toDo).collect(Collectors.toSet());
        Set<DeptDto> depts = role.getDepts().stream().map(Dept::toDo).collect(Collectors.toSet());

        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getRoleId());
        roleDto.setName(role.getName());
        roleDto.setMenus(menus);
//        roleDto.setDepts(depts);
        roleDto.setDescription(role.getDescription());
        roleDto.setDataScope(role.getDataScope());
        roleDto.setLevel(role.getLevel());
        return roleDto;
    }
}
