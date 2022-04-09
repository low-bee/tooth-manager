package com.xiaolong.toothmanager.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @Description: 菜单
 * @Author xiaolong
 * @Date 2022/4/9 19:32
 */
@Data
public class Menu {


//    @Column(name = "menu_id")
    @ApiModelProperty(value = "ID", hidden = true)
    private Long id;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "菜单角色")
    private Set<Role> roles;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "菜单组件名称")
    private String componentName;

    @ApiModelProperty(value = "排序")
    private Integer menuSort = 999;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "菜单类型，目录、菜单、按钮")
    private Integer type;

    @ApiModelProperty(value = "权限标识")
    private String permission;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

//    @Column(columnDefinition = "bit(1) default 0")
    @ApiModelProperty(value = "缓存")
    private Boolean cache;

//    @Column(columnDefinition = "bit(1) default 0")
    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden;

    @ApiModelProperty(value = "上级菜单")
    private Long pid;

    @ApiModelProperty(value = "子节点数目", hidden = true)
    private Integer subCount = 0;

    @ApiModelProperty(value = "外链菜单")
    private Boolean iFrame;
}