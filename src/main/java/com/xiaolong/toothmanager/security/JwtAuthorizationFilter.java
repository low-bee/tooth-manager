package com.xiaolong.toothmanager.security;

import cn.hutool.core.util.StrUtil;
import com.xiaolong.toothmanager.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: Jwt 权限过滤器
 * @Author xiaolong
 * @Date 2022/1/16 12:07 下午
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtils jwtUtils;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String jwt  = request.getHeader(jwtUtils.getHeader());

        // jwt 为空或者没有，需要进行登录
        if (StrUtil.isBlankOrUndefined(jwt)){
            chain.doFilter(request, response);
            return ;
        }

        Claims claimByToken = jwtUtils.getClaimByToken(jwt);
        if  (claimByToken == null){
            throw new JwtException("Token异常，无法解析！");
        }
        if  (jwtUtils.isTokenExpired(claimByToken)){
            throw new JwtException("Token异常，已经过期！");
        }

        String username = claimByToken.getSubject();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username, null, null
        );

        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }


}
