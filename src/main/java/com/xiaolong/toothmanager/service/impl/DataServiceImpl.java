package com.xiaolong.toothmanager.service.impl;

import com.xiaolong.toothmanager.service.DataService;
import com.xiaolong.toothmanager.service.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author xiaolong
 * @Date 2022/3/29 22:47
 */
@Component
public class DataServiceImpl implements DataService {
    @Override
    public List<Long> getDeptIds(UserDto user) {
        return null;
    }
}
