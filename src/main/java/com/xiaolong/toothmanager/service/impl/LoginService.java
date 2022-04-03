package com.xiaolong.toothmanager.service.impl;

import com.xiaolong.toothmanager.common.exception.BadRequestException;
import com.xiaolong.toothmanager.security.bean.LoginProperties;
import com.xiaolong.toothmanager.service.DataService;
import com.xiaolong.toothmanager.service.RoleService;
import com.xiaolong.toothmanager.service.UserService;
import com.xiaolong.toothmanager.service.dto.JwtUserDto;
import com.xiaolong.toothmanager.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 登录Service
 * @Author xiaolong
 * @Date 2022/1/15 10:49 下午
 */
@Service("loginService")
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserService userService;
    private final RoleService roleService;
    private final DataService dataService;
    private final LoginProperties loginProperties;

    private final UserCacheManager USER_DTO_CACHE;

    public void setEnableCache(boolean enableCache) {
        this.loginProperties.setCacheEnable(enableCache);
    }

    public static ExecutorService executor = newThreadPool();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUserDto jwtUserDto = null;
        Future<JwtUserDto> future = USER_DTO_CACHE.get(username);
        if (!loginProperties.isCacheEnable()) {
            UserDto user;
            try {
                user = userService.findByUsername(username);
            } catch (Exception e) {
                // SpringSecurity会自动转换UsernameNotFoundException为BadCredentialsException
                throw new UsernameNotFoundException(username, e);
            }
            if (user == null) {
                throw new UsernameNotFoundException("");
            } else {
                if (!user.getEnabled()) {
                    throw new BadRequestException("账号未激活！");
                }
                jwtUserDto = new JwtUserDto(
                        user
//                        ,
//                        dataService.getDeptIds(user),
//                        roleService.mapToGrantedAuthorities(user)
                );
            }
            return jwtUserDto;
        }

        if (future == null) {
            Callable<JwtUserDto> call = () -> getJwtBySearchDb(username);
            FutureTask<JwtUserDto> ft = new FutureTask<>(call);
            future = USER_DTO_CACHE.putIfAbsent(username, ft);
            if (future == null) {
                future = ft;
                executor.submit(ft);
            }
            try {
                return future.get();
            } catch (CancellationException e) {
                USER_DTO_CACHE.remove(username);
                System.out.println("error" + Thread.currentThread().getName());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            try {
                jwtUserDto = future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e.getMessage());
            }
            // 检查dataScope是否修改
//            List<Long> dataScopes = jwtUserDto.getDataScopes();
//            dataScopes.clear();
//            dataScopes.addAll(dataService.getDeptIds(jwtUserDto.getUser()));

        }
        return jwtUserDto;
    }

    private JwtUserDto getJwtBySearchDb(String username) {
        UserDto user;
        try {
            user = userService.findByUsername(username);
        } catch (Exception e) {
            // SpringSecurity会自动转换UsernameNotFoundException为BadCredentialsException
            throw new UsernameNotFoundException("", e);
        }
        if (user == null) {
            throw new UsernameNotFoundException("");
        } else {
            if (!user.getEnabled()) {
                throw new BadRequestException("账号未激活！");
            }
            return new JwtUserDto(
                    user
//                    ,
//                    dataService.getDeptIds(user),
//                    roleService.mapToGrantedAuthorities(user)
            );
        }

    }

    public static ExecutorService newThreadPool() {
        ThreadFactory namedThreadFactory = new ThreadFactory() {
            final AtomicInteger sequence = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                int seq = this.sequence.getAndIncrement();
                thread.setName("future-task-thread" + (seq > 1 ? "-" + seq : ""));
                if (!thread.isDaemon()) {
                    thread.setDaemon(true);
                }

                return thread;
            }
        };
        return new ThreadPoolExecutor(10, 100,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }
}
