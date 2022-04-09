package com.xiaolong.toothmanager.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaolong.toothmanager.entity.system.Role;
import com.xiaolong.toothmanager.entity.system.RoleSmall;
import com.xiaolong.toothmanager.entity.system.RoleSmallDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 角色小映射类型
 * @Author xiaolong
 * @Date 2022/4/9 21:41
 */
public interface RoleSmallMapper extends BaseMapper<RoleSmall> {

    /**
     * @Description: 查询所有角色
     * @Author xiaolong
     * @Date 2020/4/9 21:42
     */


    default List<RoleSmallDto> toDto(ArrayList<Role> roles){
        // 将Role对象转为RoleSmallDto
        List<RoleSmallDto> roleSmallDtos = new ArrayList<>();
        for (Role role : roles) {
            RoleSmallDto roleSmallDto = new RoleSmallDto();
            roleSmallDto.setId(role.getId());
            roleSmallDto.setRoleName(role.getName());
            roleSmallDtos.add(roleSmallDto);
        }
        return roleSmallDtos;
    }
}
