package com.xiaolong.toothmanager.entity.system;

import com.xiaolong.toothmanager.annotation.DataPermission;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Description: 部门分页查询条件
 * @Author xiaolong
 * @Date 2022/4/10 09:24
 */
@Data
@DataPermission
public class DeptQueryCriteria {
    private String name;

    private Boolean enabled;

    private Long pid;

    private Boolean pidIsNull;

    private List<Timestamp> createTime;
}
