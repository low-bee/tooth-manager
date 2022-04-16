package com.xiaolong.toothmanager.mapper.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaolong.toothmanager.entity.system.Menu;
import com.xiaolong.toothmanager.entity.system.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Description: 角色映射表
 * @Author xiaolong
 * @Date 2022/4/9 19:18
 */
@Repository
@TableName("sys_role")
public interface RoleMapper extends BaseMapper<Role> {
    Set<Role> findByUserId(Long id);

    List<Role> findAll();

    Set<Menu> findMenuByRoleId(Long id);
}
