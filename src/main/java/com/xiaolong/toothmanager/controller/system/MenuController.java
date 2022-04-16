package com.xiaolong.toothmanager.controller.system;

import com.xiaolong.toothmanager.annotation.Log;
import com.xiaolong.toothmanager.common.exception.BadRequestException;
import com.xiaolong.toothmanager.common.lang.Result;
import com.xiaolong.toothmanager.entity.system.Menu;
import com.xiaolong.toothmanager.entity.system.MenuQueryCriteria;
import com.xiaolong.toothmanager.service.MenuService;
import com.xiaolong.toothmanager.service.dto.MenuDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description:  用户菜单Controller
 * @Author xiaolong
 * @Date 2022/4/10 22:51
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "系统：菜单管理")
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    private static final String ENTITY_NAME = "menu";

    // 增加菜单
    @Log("新增菜单")
    @ApiOperation("新增菜单")
    @PostMapping("/add")
    @PreAuthorize("@el.check('menu:add')")
    public Result<Object> createMenu(@Validated @RequestBody Menu resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new" + ENTITY_NAME  +"cannot already have an ID");
        }
        MenuDto menuDto = Menu.toDo(resources);
        menuService.addMenu(menuDto);
        return Result.success(HttpStatus.CREATED);
    }

    // 修改菜单
    @Log("修改菜单")
    @ApiOperation("修改菜单")
    @PostMapping("/update")
    @PreAuthorize("@el.check('menu:add')")
    public Result<Object> updateMenu(@Validated @RequestBody Menu resources){
        if (resources.getId() == null) {
            throw new BadRequestException("A new" + ENTITY_NAME  +"must have an ID");
        }
        MenuDto menuDto = Menu.toDo(resources);
        menuService.updateMenu(menuDto);
        return Result.success(HttpStatus.OK);
    }
    // 删除菜单
    @Log("删除菜单")
    @ApiOperation("删除菜单")
    @GetMapping("/delete")
    @PreAuthorize("@el.check('menu:add')")
    public Result<Object> deleteMenu(@RequestParam Long id){
        if (id == null) {
            throw new BadRequestException("delete" + ENTITY_NAME  +"must have an ID");
        }
        menuService.deleteMenu(id);
        return Result.success(HttpStatus.OK);
    }

    // 通过Long id 查询菜单Dto
    @Log("查询菜单")
    @ApiOperation("查询菜单")
    @GetMapping("")
    @PreAuthorize("@el.check('menu:list')")
    public Result<MenuDto> findMenuById(@RequestParam Long id){
        if (id == null) {
            throw new BadRequestException("query" + ENTITY_NAME  +"must have an ID");
        }
        MenuDto menuDto = menuService.queryMenu(id);
        return Result.success(menuDto);
    }

    @ApiOperation("导出菜单数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('menu:list')")
    public void exportMenu(HttpServletResponse response, MenuQueryCriteria criteria) throws Exception {
        menuService.download(menuService.queryAll(criteria, false), response);
    }
}
