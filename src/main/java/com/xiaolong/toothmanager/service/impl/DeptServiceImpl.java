package com.xiaolong.toothmanager.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.xiaolong.toothmanager.entity.system.Dept;
import com.xiaolong.toothmanager.entity.system.DeptQueryCriteria;
import com.xiaolong.toothmanager.mapper.system.DeptMapper;
import com.xiaolong.toothmanager.service.DeptService;
import com.xiaolong.toothmanager.service.dto.DeptDto;
import com.xiaolong.toothmanager.utils.DataScopeEnum;
import com.xiaolong.toothmanager.utils.QueryHelp;
import com.xiaolong.toothmanager.utils.SecurityUtils;
import com.xiaolong.toothmanager.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 部门Service
 * @Author xiaolong
 * @Date 2022/4/9 18:47
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

    private final DeptMapper deptRepository;

    @Override
    public Set<Dept> findByRoleId(Long id) {
        return deptRepository.findByRoleId(id);
    }

    @Override
    public List<Dept> findByPid(long pid) {
        return deptRepository.findByPid(pid);
    }

    @Override
    public List<Long> getDeptChildren(List<Dept> deptList) {
        List<Long> list = new ArrayList<>();


        deptList.forEach(dept -> {
                    if (dept != null && dept.getEnabled()) {
                        List<Dept> depts = deptRepository.findByPid(dept.getId());
                        if (depts.size() != 0) {
                            list.addAll(getDeptChildren(depts));
                        }
                        list.add(dept.getId());
                    }
                }
        );
        return list;

    }

    @Override
    public int countByPid(Long pid) {
        return 0;
    }

    @Override
    public List<DeptDto> queryAll(DeptQueryCriteria criteria, boolean isQuery) throws IllegalAccessException {
        String dataScopeType = SecurityUtils.getDataScopeType();

        if (isQuery) {
            if(dataScopeType.equals(DataScopeEnum.ALL.getValue())){
                criteria.setPidIsNull(true);
            }
            List<Field> fields = QueryHelp.getAllFields(criteria.getClass(), new ArrayList<>());
            List<String> fieldNames = new ArrayList<String>(){{ add("pidIsNull");add("enabled");}};
            for (Field field : fields) {
                //设置对象的访问权限，保证对private的属性的访问
                field.setAccessible(true);
                Object val = field.get(criteria);
                if(fieldNames.contains(field.getName())){
                    continue;
                }
                if (ObjectUtil.isNotNull(val)) {
                    criteria.setPidIsNull(null);
                    break;
                }
            }
        }
        List<Dept> all = deptRepository.findAll();
        List<DeptDto> list = all.stream().map(Dept::toDo).collect(Collectors.toList());
        // 如果为空，就代表为自定义权限或者本级权限，就需要去重，不理解可以注释掉，看查询结果
        if(StringUtils.isBlank(dataScopeType)){
            return deduplication(list);
        }
        return list;
    }

    private List<DeptDto> deduplication(List<DeptDto> list) {
        List<DeptDto> deptDtos = new ArrayList<>();
        for (DeptDto deptDto : list) {
            boolean flag = true;
            for (DeptDto dto : list) {
                if (dto.getId().equals(deptDto.getPid())) {
                    flag = false;
                    break;
                }
            }
            if (flag){
                deptDtos.add(deptDto);
            }
        }
        return deptDtos;
    }
}
