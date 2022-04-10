package com.xiaolong.toothmanager.service;

import com.xiaolong.toothmanager.entity.system.Dept;
import com.xiaolong.toothmanager.entity.system.DeptQueryCriteria;
import com.xiaolong.toothmanager.service.dto.DeptDto;

import java.util.List;
import java.util.Set;

public interface DeptService {

    /**
     * 根据角色ID查询
     * @param id /
     * @return /
     */
    Set<Dept> findByRoleId(Long id);

    /**
     * 根据PID查询
     * @param pid /
     * @return /
     */
    List<Dept> findByPid(long pid);


    /**
     * 获取
     * @param deptList 部门集合
     * @return /
     */
    List<Long> getDeptChildren(List<Dept> deptList);

    /**
     * 判断是否存在子节点
     * @param pid 用户部门pid
     * @return /
     */
    int countByPid(Long pid);

    List<DeptDto> queryAll(DeptQueryCriteria criteria, boolean isQuery) throws IllegalAccessException;
}
