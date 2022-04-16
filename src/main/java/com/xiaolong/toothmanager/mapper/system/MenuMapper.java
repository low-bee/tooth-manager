package com.xiaolong.toothmanager.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaolong.toothmanager.service.dto.MenuDto;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 菜单映射器
 * @Author xiaolong
 * @Date 2022/4/10 23:04
 */

public interface MenuMapper extends BaseMapper<MenuDto> {
    void addRoleMenuMap(@Param("menuId") Long menuId, @Param("roleId") Long roleId);

    void deleteRoleMenuMap(@Param("roleId")  Long roleId,  @Param("menuId")  Long menuId);
}
