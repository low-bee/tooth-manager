package com.xiaolong.toothmanager.security;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xiaolong.toothmanager.utils.HttpServletRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 西定义权限校验器
 * @Author xiaolong
 * @Date 2022/1/15 10:06 下午
 */
@Slf4j
public class UsernamePasswordJsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    HttpServletRequestUtils httpServletRequestUtils;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) { // 必须以post方式提交
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = "";
        String password = "";
        String isChange = (String) request.getAttribute("isChange");
        JSONObject jsonRequest;
        if (isChange != null && isChange.length() > 0){
            jsonRequest = JSONUtil.parseObj(isChange);
        }else {
            jsonRequest = httpServletRequestUtils.getJsonRequest(request);
        }
        username = jsonRequest.getStr("username");
        password = jsonRequest.getStr("password");

        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
