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

    void addRoleMenuMap(Long menuId, Long roleId);

    void deleteRoleMenuMap(Long roleId, Long menuId);

    /**
     * 通过用户 id 查询菜单
     * @param currentUserId 用户 id
     * @return 菜单列表
     */
    List<MenuDto> findByUser(Long currentUserId);
    /**
     * 构建角色菜单树
     * @param menuDtoList 菜单列表
     * @return 菜单列表
     */
    List<MenuDto> buildTree(List<MenuDto> menuDtoList);

    Object buildMenus(List<MenuDto> menuDtos);
}

