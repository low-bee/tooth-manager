package com.xiaolong.toothmanager.security;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xiaolong.toothmanager.common.lang.Result;
import com.xiaolong.toothmanager.utils.HttpServletRequestUtils;
import com.xiaolong.toothmanager.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Description: 登录成功跳转类
 * @Author xiaolong
 * @Date 2022/1/15 6:08 下午
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    HttpServletRequestUtils httpServletRequestUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 生成jwt并返回

        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
//        JSONObject param = httpServletRequestUtils.getJsonRequest(request);

        String jwt = jwtUtils.generateToken(authentication.getName());
        response.setHeader(jwtUtils.getHeader(),  jwt);

        Result<Object> success = Result.success(jwt);
        // 写入并刷新
        outputStream.write(JSONUtil.toJsonStr(success).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
