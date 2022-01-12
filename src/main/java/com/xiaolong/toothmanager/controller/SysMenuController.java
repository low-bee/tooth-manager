package com.xiaolong.toothmanager.controller;


import com.xiaolong.toothmanager.entity.SysUser;
import com.xiaolong.toothmanager.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaolong
 * @since 2022-01-12
 */
@RestController
@RequestMapping("/sys-menu")
public class SysMenuController extends BaseController {

    @Autowired
    SysUserService service;

    @GetMapping("test")
    List<SysUser> getList(){
        return service.list();
    }
}
