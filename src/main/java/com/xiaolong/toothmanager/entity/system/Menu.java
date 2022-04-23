package com.xiaolong.toothmanager.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xiaolong.toothmanager.service.dto.BaseDTO;
import com.xiaolong.toothmanager.service.dto.MenuDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @Description: 菜单
 * @Author xiaolong
 * @Date 2022/4/9 19:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Menu extends BaseDTO  {


//    @Column(name = "menu_id")
//    @ApiModelProperty(value = "ID", hidden = true)
    @TableId("menu_id")
    private Long menuId;

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

    public static MenuDto toDo(Menu menu){
    	MenuDto dto = new MenuDto();
    	dto.setMenuId(menu.getMenuId());
    	dto.setTitle(menu.getTitle());
    	dto.setComponentName(menu.getComponentName());
    	dto.setMenuSort(menu.getMenuSort());
    	dto.setComponent(menu.getComponent());
    	dto.setPath(menu.getPath());
    	dto.setType(menu.getType());
    	dto.setPermission(menu.getPermission());
    	dto.setIcon(menu.getIcon());
    	dto.setCache(menu.getCache());
    	dto.setHidden(menu.getHidden());
    	dto.setPid(menu.getPid());
    	dto.setIFrame(menu.getIFrame());
    	dto.setSubCount(menu.getSubCount());
    	return dto;
    }
}
