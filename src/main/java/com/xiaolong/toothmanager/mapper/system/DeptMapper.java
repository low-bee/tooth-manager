package com.xiaolong.toothmanager.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaolong.toothmanager.entity.system.Dept;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Description: 通过id查询用户
 * @Author xiaolong
 * @Date 2022/4/9 18:52
 */
@Repository
public interface DeptMapper extends BaseMapper<Dept> {
    Set<Dept> findByRoleId(Long id);

    List<Dept> findByPid(long pid);

    List<Dept> findAll();
}
