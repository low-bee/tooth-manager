package com.xiaolong.toothmanager.controller;


import com.xiaolong.toothmanager.common.lang.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaolong
 * @since 2022-01-12
 */
@RestController
public class SysUserController extends BaseController {
    @GetMapping("/sys/userInfo")
    Result getUserInfo(){
        return Result.success("");
    }
}
