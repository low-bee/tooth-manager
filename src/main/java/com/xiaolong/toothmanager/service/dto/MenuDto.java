package com.xiaolong.toothmanager.service.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 菜单DTO
 * @Author xiaolong
 * @Date 2022/4/9 22:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_menu")
public class MenuDto  extends BaseDTO implements Serializable {

    @TableId("menu_id")
    private Long menuId;

    private Integer type;

    private String permission;

    private String title;
    @TableField(value = "menu_sort")
    private Integer menuSort;

    private String path;

    private String component;

    private Long pid;

    private Integer subCount;

    private Boolean iFrame;

    private Boolean cache;

    private Boolean hidden;

    private String icon;

    @TableField(exist = false)
    private String componentName;
    @TableField(exist = false)
    private List<MenuDto> children;

    public Boolean getHasChildren() {
        return subCount > 0;
    }

    public Boolean getLeaf() {
        return subCount <= 0;
    }

    public String getLabel() {
        return title;
    }
}
