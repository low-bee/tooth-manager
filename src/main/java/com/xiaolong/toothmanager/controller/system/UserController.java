package com.xiaolong.toothmanager.controller.system;

import com.xiaolong.toothmanager.annotation.Log;
import com.xiaolong.toothmanager.common.exception.BadRequestException;
import com.xiaolong.toothmanager.common.lang.Result;
import com.xiaolong.toothmanager.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 用户控制器
 * @Author xiaolong
 * @Date 2022/4/17 11:36
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：用户系统接口")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 添加用户角色
    @Log("新增用户角色")
    @ApiOperation("新增用户角色")
    @PostMapping("/add")
    @PreAuthorize("@el.check('role:add')")
    public Result<Object> createRole(@Validated @RequestBody Long userId, @Validated @RequestBody Long roleId) {
        if (userId == null || roleId == null) {
            throw new BadRequestException("用户id或角色id不能为空");
        }
        Boolean flag = userService.addUserRole(userId, roleId);
        return flag ? Result.success(true) : Result.fail("新增用户角色失败");
    }

    // 删除用户角色
    @Log("删除用户角色")
    @ApiOperation("删除用户角色")
    @PostMapping("/delete")
    @PreAuthorize("@el.check('role:add')")
    public Result<Object> deleteRole(@Validated @RequestBody Long userId, @Validated @RequestBody Long roleId) {
        if (userId == null || roleId == null) {
            throw new BadRequestException("用户id或角色id不能为空");
        }
        Boolean flag = userService.deleteUserRole(userId, roleId);
        return flag ? Result.success(true) : Result.fail("新增用户角色失败");
    }
    // 修改用户角色

    // 查询用户角色
}
