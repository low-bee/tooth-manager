package com.xiaolong.toothmanager.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 菜单元数据vo类
 * @Author xiaolong
 * @Date 2022/4/17 11:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuMetaVo {
    private String title;

    private String icon;

    private Boolean noCache;
}
