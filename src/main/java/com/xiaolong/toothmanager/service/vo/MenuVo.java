package com.xiaolong.toothmanager.service.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @Description: 前端构建时用到的实体
 * @Author xiaolong
 * @Date 2022/4/17 11:28
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVo {
    private String name;

    private String path;

    private Boolean hidden;

    private String redirect;

    private String component;

    private Boolean alwaysShow;

    private MenuMetaVo meta;

    private List<MenuVo> children;
}
