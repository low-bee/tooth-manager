package com.xiaolong.toothmanager.service.impl;

import com.xiaolong.toothmanager.common.exception.BadRequestException;
import com.xiaolong.toothmanager.entity.system.MenuQueryCriteria;
import com.xiaolong.toothmanager.mapper.system.MenuMapper;
import com.xiaolong.toothmanager.service.MenuService;
import com.xiaolong.toothmanager.service.dto.MenuDto;
import com.xiaolong.toothmanager.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Description: 菜单服务实现类
 * @Author xiaolong
 * @Date 2022/4/10 23:02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    @Override
    public MenuDto queryMenu(Long menuId) {
        return menuMapper.selectById(menuId);
    }

    @Override
    public Boolean addMenu(MenuDto menuDto) {
        return menuMapper.insert(menuDto) > 0;
    }

    @Override
    public Boolean deleteMenu(Long menuId) {
        return menuMapper.deleteById(menuId) > 0;
    }

    @Override
    public Boolean updateMenu(MenuDto menuDto) {
        MenuDto menuDto1 = menuMapper.selectById(menuDto.getMenuId());
        if (Objects.nonNull(menuDto1)) {
            throw new BadRequestException("菜单不存在");
        }
        return menuMapper.updateById(menuDto) > 0;
    }

    @Override
    public List<MenuDto> queryAll(MenuQueryCriteria criteria, boolean isQuery) {
        return menuMapper.selectList(null);
    }

    // 将查询到的数据写出到HttpServletResponse中
    @Override
    public void download(List<MenuDto> menuDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MenuDto menuDTO : menuDtos) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("菜单标题", menuDTO.getTitle());
            map.put("菜单类型", menuDTO.getType() == null ? "目录" : menuDTO.getType() == 1 ? "菜单" : "按钮");
            map.put("权限标识", menuDTO.getPermission());
            map.put("外链菜单", menuDTO.getIFrame() ? "是" : "否");
            map.put("菜单可见", menuDTO.getHidden() ? "否" : "是");
            map.put("是否缓存", menuDTO.getCache() ? "是" : "否");
            map.put("创建日期", menuDTO.getCreatedTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void addRoleMenuMap(Long menuId, Long roleId) {
        menuMapper.addRoleMenuMap(menuId, roleId);
    }

    @Override
    public void deleteRoleMenuMap(Long roleId, Long menuId) {
        menuMapper.deleteRoleMenuMap(roleId, menuId);
    }
}
