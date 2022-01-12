package com.xiaolong.toothmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 基本Controller
 * @Author xiaolong
 * @Date 2022/1/12 4:47 下午
 */
public class BaseController {
    @Autowired
    HttpServletRequest req;
}
