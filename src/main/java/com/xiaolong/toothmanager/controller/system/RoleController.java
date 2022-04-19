package com.xiaolong.toothmanager.controller.system;

import com.xiaolong.toothmanager.annotation.Log;
import com.xiaolong.toothmanager.common.exception.BadRequestException;
import com.xiaolong.toothmanager.common.lang.Result;
import com.xiaolong.toothmanager.controller.BaseController;
import com.xiaolong.toothmanager.entity.system.Menu;
import com.xiaolong.toothmanager.entity.system.Role;
import com.xiaolong.toothmanager.entity.system.RoleSmallDto;
import com.xiaolong.toothmanager.service.MenuService;
import com.xiaolong.toothmanager.service.RoleService;
import com.xiaolong.toothmanager.service.dto.RoleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 角色控制器, 角色和菜单是一对多的关系，一个角色有多个菜单
 * @Author xiaolong
 * @Date 2022/4/16 10:59
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：角色管理")
@RequestMapping("/api/role")
public class RoleController extends BaseController {

    private final MenuService menuService;
    private final RoleService roleService;

    private static final String ROLE_NAME = "role";


    // 增加角色
    @Log("新增角色")
    @ApiOperation("新增角色")
    @PostMapping("/add")
    @PreAuthorize("@el.check('role:add')")
    public Result<Object> createMenu(@Validated @RequestBody RoleDto resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new" + ROLE_NAME  +"cannot already have an ID");
        }
        Role role = RoleDto.toRole(resources);
        Boolean flag = roleService.create(role);
        return Result.success(flag);
    }

    // 修改角色
    @Log("修改角色")
    @ApiOperation("修改角色")
    @PostMapping("/update")
    @PreAuthorize("@el.check('role:edit')")
    public Result<Object> updateMenu(@Validated @RequestBody RoleDto resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ROLE_NAME  +"ID cannot be empty");
        }
        Role role = RoleDto.toRole(resources);
        Boolean flag = roleService.update(role);
        return Result.success(flag);
    }
    // 删除角色
    @Log("删除角色")
    @ApiOperation("删除角色")
    @PostMapping("/delete")
    @PreAuthorize("@el.check('role:del')")
    public Result<Object> deleteMenu(@RequestBody HashSet<Long> roleIds){
        if (roleIds.isEmpty()) {
            throw new BadRequestException("roleIds cannot be empty");
        }

        Boolean flag = roleService.delete(roleIds);
        return Result.success(flag);
    }

    // 查询角色
    @Log("查询角色")
    @ApiOperation("查询所有的角色通过用户id列出所有的角色")
    @GetMapping("/list/{id}")
//    @PreAuthorize("@el.check('role:list')")
    public Result<Object> listMenusByUser(@PathVariable Long id){
        List<RoleSmallDto> byUsersId = roleService.findByUsersId(id);
        return Result.success(byUsersId);
    }

    // 查询角色
    @Log("查询角色")
    @ApiOperation("查询所有的角色")
    @PostMapping("/list/all")
    @PreAuthorize("@el.check('role:list')")
    public Result<Object> listMenus(){
        List<RoleDto> roleDtos = roleService.queryAll();
        return Result.success(roleDtos);
    }

    // 查询角色菜单
    @Log("查询角色菜单")
    @ApiOperation("查询角色菜单")
    @GetMapping("/menu/{id}")
    @PreAuthorize("@el.check('role:list')")
    public Result<Object> listMenusByRoleId(@PathVariable Long id){
        // 通过角色id查询菜单
        Set<Menu> result = roleService.listMenuIdsByRoleId(id);
        return Result.success(result);
    }

    // 增加角色菜单
    @Log("新增角色菜单")
    @ApiOperation("新增角色菜单")
    @PostMapping("/menu/add")
    @PreAuthorize("@el.check('role:edit')")
    public Result<Object> createMenu(@RequestBody RoleSmallDto roleSmallDto){
        if (roleSmallDto.getId() == null) {
            throw new BadRequestException(ROLE_NAME  +"ID cannot be empty");
        }
        Role role = Role.builder()
                .menus(roleSmallDto.getMenus())
                .dataScope(roleSmallDto.getDataScope())
                .name(roleSmallDto.getRoleName())
                .build();

        Boolean flag = roleService.createMenu(role, roleSmallDto.getMenus().stream().map(Menu::getMenuId).collect(Collectors.toList()));

        return Result.success(flag);
    }

    // 删除角色菜单
    @Log("删除角色菜单")
    @ApiOperation("删除角色菜单")
    @PostMapping("/menu/delete")
    @PreAuthorize("@el.check('role:edit')")
    public Result<Object> deleteMenu(@RequestBody RoleSmallDto roleSmallDto){
        if (roleSmallDto.getId() == null) {
            throw new BadRequestException(ROLE_NAME  +"ID cannot be empty");
        }
        Role role = Role.builder()
                .menus(roleSmallDto.getMenus())
                .dataScope(roleSmallDto.getDataScope())
                .name(roleSmallDto.getRoleName())
                .build();
        Boolean flag = roleService.deleteMenu(role);
        return Result.success(flag);
    }
}


