package com.xiaolong.toothmanager.entity.system;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Description: 菜单查询条件
 * @Author xiaolong
 * @Date 2022/4/10 23:23
 */
public class MenuQueryCriteria {
//    @Query(blurry = "title,component,permission")
    private String blurry;

//    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

//    @Query(type = Query.Type.IS_NULL, propName = "pid")
    private Boolean pidIsNull;

//    @Query
    private Long pid;
}
