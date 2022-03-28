package com.xiaolong.toothmanager.service.impl;

import com.xiaolong.toothmanager.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Description: 用于清理 用户登录信息缓存，为防止Spring循环依赖与安全考虑 ，单独构成工具类
 * @Author xiaolong
 * @Date 2022/3/28 23:43
 */
@Component
@AllArgsConstructor
public class UserCacheClean {

    private final UserCacheManager userCacheManager;

    /**
     * 清理特定用户缓存信息<br>
     * 用户信息变更时
     *
     * @param userName /
     */
    public void cleanUserCache(String userName) {
        if (StringUtils.isNotEmpty(userName)) {
            userCacheManager.remove(userName);
        }
    }

    /**
     * 清理所有用户的缓存信息<br>
     * ,如发生角色授权信息变化，可以简便的全部失效缓存
     */
    public void cleanAll() {
        userCacheManager.clear();
    }
}
