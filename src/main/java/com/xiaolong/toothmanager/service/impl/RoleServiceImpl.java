package com.xiaolong.toothmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xiaolong.toothmanager.entity.system.Menu;
import com.xiaolong.toothmanager.entity.system.Role;
import com.xiaolong.toothmanager.entity.system.RoleSmallDto;
import com.xiaolong.toothmanager.mapper.system.RoleMapper;
import com.xiaolong.toothmanager.mapper.system.RoleSmallMapper;
import com.xiaolong.toothmanager.service.RoleService;
import com.xiaolong.toothmanager.service.dto.RoleDto;
import com.xiaolong.toothmanager.service.dto.UserDto;
import com.xiaolong.toothmanager.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletResponse;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 角色服务类
 * @Author xiaolong
 * @Date 2022/3/29 22:46
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "role")
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleRepository;
    private final RoleSmallMapper roleSmallMapper;

    /**
     * 返回所有的角色
     * @return 角色集合
     */
    @Override
    public List<RoleDto> queryAll() {
        List<Role> all = roleRepository.findAll();
        return all.stream().map(Role::toDo).collect(Collectors.toList());
    }

    @Override
    public RoleDto findById(long id) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(Role resources) {
        try {
            roleRepository.insert(resources);
        } catch (Exception e) {
            // 设置仅回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }

    @Override
    public Boolean update(Role resources) {
        // 通过id更新
        // resoueces 非空
        if (Objects.isNull(resources) || resources.getRoleId() == null) {
            throw new InvalidParameterException("对象为空或id为空");
        }


        UpdateWrapper<Role> roleUpdateWrapper = new UpdateWrapper<>();
        // mybatis plus 通过id更新
        try {
            // 通过id更新
            roleUpdateWrapper
                    .setEntity(resources)
                    .eq("id", resources.getRoleId());
            roleRepository.update(null, roleUpdateWrapper);
        } catch (Exception e) {
            // 设置仅回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }

    @Override
    public Boolean delete(Set<Long> ids) {
        try {
            roleRepository.deleteBatchIds(ids);
        } catch (Exception e) {
            // 设置仅回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public List<RoleSmallDto> findByUsersId(Long id) {
        return roleSmallMapper.toDto(new ArrayList<>(roleRepository.findByUserId(id)));
    }

    @Override
    public Integer findByRoles(Set<Role> roles) {
        return null;
    }

    @Override
    public void updateMenu(Role resources, RoleDto roleDTO) {

    }

    @Override
    public void untiedMenu(Long id) {

    }

    @Override
    public void download(List<RoleDto> queryAll, HttpServletResponse response){

    }

    @Override
    public void verification(Set<Long> ids) {

    }

    @Override
    public List<Role> findInMenuId(List<Long> menuIds) {
        return null;
    }

    @Override
    public Set<Menu> listMenuIdsByRoleId(Long id) {
        return roleRepository.findMenuByRoleId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createMenu(Role role, List<Long> menuIds) {

        for (Long menuId : menuIds) {
            try {
                roleRepository.addRoleMenuMap(role.getRoleId(), menuId);
            } catch (Exception e) {
                // 设置仅回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMenu(Role role) {
        if (Objects.isNull(role.getRoleId()) || role.getMenus().isEmpty()) {
            return true;
        }
        List<Long> menuIds = role.getMenus().stream().map(Menu::getMenuId).collect(Collectors.toList());

        for (Long menuId : menuIds) {
            try {
                roleRepository.deleteRoleMenuMap(role.getRoleId(), menuId);
            } catch (Exception e) {
                // 设置仅回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }
        return true;
    }

    @Override
    @Cacheable(key = "'auth:' + #p0.id")
    public List<GrantedAuthority> mapToGrantedAuthorities(UserDto user) {
        Set<String> permissions = new HashSet<>();
        // 如果是管理员直接返回
        if (user.getIsAdmin()) {
            permissions.add("admin");
            return permissions.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        Set<Role> roles = roleRepository.findByUserId(user.getId());
        permissions = roles.stream()
                .flatMap(role -> role.getMenus().stream())
                .map(Menu::getPermission)
                .filter(StringUtils::isNotBlank).collect(Collectors.toSet());

        return permissions.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
