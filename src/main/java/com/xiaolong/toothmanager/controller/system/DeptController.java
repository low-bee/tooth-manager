package com.xiaolong.toothmanager.controller.system;

import com.xiaolong.toothmanager.common.lang.Result;
import com.xiaolong.toothmanager.entity.system.DeptQueryCriteria;
import com.xiaolong.toothmanager.service.DeptService;
import com.xiaolong.toothmanager.service.dto.DeptDto;
import com.xiaolong.toothmanager.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 部门管理
 * @Author xiaolong
 * @Date 2022/4/10 09:19
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：部门管理")
@RequestMapping("/api/dept")
public class DeptController {

    private final DeptService deptService;

    @ApiOperation("查询部门")
    @GetMapping
    @PreAuthorize("@el.check('user:list','dept:list')")
    public Result<Object> queryDept(DeptQueryCriteria criteria) throws Exception {
        List<DeptDto> deptDtos = deptService.queryAll(criteria, true);
        return Result.success(PageUtil.toPage(deptDtos, deptDtos.size()));
    }
}
