package com.xiaolong.toothmanager.security;

import cn.hutool.json.JSONObject;
import com.xiaolong.toothmanager.common.exception.CaptchaException;
import com.xiaolong.toothmanager.common.lang.Const;
import com.xiaolong.toothmanager.utils.HttpServletRequestUtils;
import com.xiaolong.toothmanager.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 验证码过滤器
 * @Author xiaolong
 * @Date 2022/1/15 8:04 下午
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaptureFilter extends OncePerRequestFilter {

    private final RedisUtil redisUtil;
    
    private final LoginFailureHandler loginFailureHandler;

    private final HttpServletRequestUtils httpServletRequestUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if ("/login".equals(requestURI) && "POST".equals(request.getMethod())) {
            // 校验验证码
            try {
                validate(request);
            } catch (CaptchaException e) {
                // 不正确跳转到认证失败过滤器
                loginFailureHandler.onAuthenticationFailure(request, response, e );
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) {

        JSONObject param = httpServletRequestUtils.getJsonRequest(request);
        String userCode = param.getStr("code");
        String key = param.getStr("key");


        if  (StringUtils.isBlank(userCode) || StringUtils.isBlank(key)){
            throw new CaptchaException("验证码错误❎! ");
        }

        if (!redisUtil.hget(Const.CAPTCHA_KEY, key).toString().equals(userCode)){
            throw new CaptchaException("验证码错误❎! ");
        }

        redisUtil.hdel(Const.CAPTCHA_KEY, key);
    }


}
