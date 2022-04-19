package com.xiaolong.toothmanager.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.xiaolong.toothmanager.common.exception.BadRequestException;
import com.xiaolong.toothmanager.entity.system.Menu;
import com.xiaolong.toothmanager.entity.system.MenuQueryCriteria;
import com.xiaolong.toothmanager.entity.system.RoleSmallDto;
import com.xiaolong.toothmanager.mapper.system.MenuMapper;
import com.xiaolong.toothmanager.service.MenuService;
import com.xiaolong.toothmanager.service.RoleService;
import com.xiaolong.toothmanager.service.dto.MenuDto;
import com.xiaolong.toothmanager.service.vo.MenuMetaVo;
import com.xiaolong.toothmanager.service.vo.MenuVo;
import com.xiaolong.toothmanager.utils.FileUtil;
import com.xiaolong.toothmanager.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    private final RoleService roleService;
//    private final MenuRepository menuRepository;

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

    @Override
    public List<MenuDto> findByUser(Long currentUserId) {
        List<RoleSmallDto> roles = roleService.findByUsersId(currentUserId);
        // 获取当前用户的角色
        Set<Long> roleIds = roles.stream().map(RoleSmallDto::getId).collect(Collectors.toSet());
        // 根据角色Id获取菜单
        LinkedHashSet<Menu> menus = menuMapper.findByRoleIdsAndTypeNot(roleIds, 2);

        return menus.stream().map(Menu::toDo).collect(Collectors.toList());
    }

    @Override
    public List<MenuDto> buildTree(List<MenuDto> menuDtos) {
        List<MenuDto> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (MenuDto menuDTO : menuDtos) {
            if (menuDTO.getPid() == null) {
                trees.add(menuDTO);
            }
            for (MenuDto it : menuDtos) {
                if (menuDTO.getMenuId().equals(it.getPid())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getMenuId());
                }
            }
        }
        if(trees.size() == 0){
            trees = menuDtos.stream().filter(s -> !ids.contains(s.getMenuId())).collect(Collectors.toList());
        }
        return trees;
    }

    @Override
    public List<MenuVo> buildMenus(List<MenuDto> menuDtos) {
        List<MenuVo> list = new LinkedList<>();
        menuDtos.forEach(menuDTO -> {
                    if (menuDTO!=null){
                        List<MenuDto> menuDtoList = menuDTO.getChildren();
                        MenuVo menuVo = new MenuVo();
                        menuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName())  ? menuDTO.getComponentName() : menuDTO.getTitle());
                        // 一级目录需要加斜杠，不然会报警告
                        menuVo.setPath(menuDTO.getPid() == null ? "/" + menuDTO.getPath() :menuDTO.getPath());
                        menuVo.setHidden(menuDTO.getHidden());
                        // 如果不是外链
                        if(!menuDTO.getIFrame()){
                            if(menuDTO.getPid() == null){
                                menuVo.setComponent(StringUtils.isEmpty(menuDTO.getComponent())?"Layout":menuDTO.getComponent());
                                // 如果不是一级菜单，并且菜单类型为目录，则代表是多级菜单
                            }else if(menuDTO.getType() == 0){
                                menuVo.setComponent(StringUtils.isEmpty(menuDTO.getComponent())?"ParentView":menuDTO.getComponent());
                            }else if(StringUtils.isNoneBlank(menuDTO.getComponent())){
                                menuVo.setComponent(menuDTO.getComponent());
                            }
                        }
                        menuVo.setMeta(new MenuMetaVo(menuDTO.getTitle(),menuDTO.getIcon(),!menuDTO.getCache()));
                        if(CollectionUtil.isNotEmpty(menuDtoList)){
                            menuVo.setAlwaysShow(true);
                            menuVo.setRedirect("noredirect");
                            menuVo.setChildren(buildMenus(menuDtoList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if(menuDTO.getPid() == null){
                            MenuVo menuVo1 = new MenuVo();
                            menuVo1.setMeta(menuVo.getMeta());
                            // 非外链
                            if(!menuDTO.getIFrame()){
                                menuVo1.setPath("index");
                                menuVo1.setName(menuVo.getName());
                                menuVo1.setComponent(menuVo.getComponent());
                            } else {
                                menuVo1.setPath(menuDTO.getPath());
                            }
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVo> list1 = new ArrayList<>();
                            list1.add(menuVo1);
                            menuVo.setChildren(list1);
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }
}
