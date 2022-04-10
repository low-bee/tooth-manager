package com.xiaolong.toothmanager.service;

import com.xiaolong.toothmanager.entity.system.MenuQueryCriteria;
import com.xiaolong.toothmanager.service.dto.MenuDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author xiaolong
 * @version v1.0
 * desc: MenuService
 */
public interface MenuService {

    // 查询菜单
    MenuDto queryMenu(Long menuId);
    // 添加菜单
    Boolean addMenu(MenuDto menuDto);
    // 删除菜单
    Boolean deleteMenu(Long menuId);
    // 修改菜单
    Boolean updateMenu(MenuDto menuDto);

    List<MenuDto> queryAll(MenuQueryCriteria criteria, boolean isQuery);

    void download(List<MenuDto> queryAll, HttpServletResponse response) throws IOException;

}

