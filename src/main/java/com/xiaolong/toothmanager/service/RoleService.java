package com.xiaolong.toothmanager.service;

import com.xiaolong.toothmanager.entity.system.Menu;
import com.xiaolong.toothmanager.entity.system.Role;
import com.xiaolong.toothmanager.entity.system.RoleSmallDto;
import com.xiaolong.toothmanager.service.dto.RoleDto;
import com.xiaolong.toothmanager.service.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface RoleService {

    /**
     * 查询全部数据
     * @return /
     */
    List<RoleDto> queryAll();

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    RoleDto findById(long id);

    /**
     * 创建
     * @param resources /
     */
    Boolean create(Role resources);

    /**
     * 编辑
     * @param resources /
     */
    Boolean update(Role resources);

    /**
     * 删除
     * @param ids /
     */
    Boolean delete(Set<Long> ids);

    /**
     * 根据用户ID查询
     * @param id 用户ID
     * @return /
     */
    List<RoleSmallDto> findByUsersId(Long id);

    /**
     * 根据角色查询角色级别
     * @param roles /
     * @return /
     */
    Integer findByRoles(Set<Role> roles);

    /**
     * 修改绑定的菜单
     * @param resources /
     * @param roleDTO /
     */
    void updateMenu(Role resources, RoleDto roleDTO);

    /**
     * 解绑菜单
     * @param id /
     */
    void untiedMenu(Long id);

    /**
     * 待条件分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
//    Object queryAll(RoleQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部
     * @param criteria 条件
     * @return /
     */
//    List<RoleDto> queryAll(RoleQueryCriteria criteria);

    /**
     * 导出数据
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<RoleDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 获取用户权限信息
     * @param user 用户信息
     * @return 权限信息
     */
    List<GrantedAuthority> mapToGrantedAuthorities(UserDto user);

    /**
     * 验证是否被用户关联
     * @param ids /
     */
    void verification(Set<Long> ids);

    /**
     * 根据菜单Id查询
     * @param menuIds /
     * @return /
     */
    List<Role> findInMenuId(List<Long> menuIds);


    Set<Menu> listMenuIdsByRoleId(Long id);

    /**
     * 创建角色菜单
     * @param roleSmallDto /
     * @return /
     */
    Boolean createMenu(Role roleSmallDto);

    /**
     * 删除角色菜单
     * @param role 待删除角色
     * @return 是否删除成功
     */
    Boolean deleteMenu(Role role);
}
