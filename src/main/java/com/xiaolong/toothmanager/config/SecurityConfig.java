package com.xiaolong.toothmanager.config;

import com.xiaolong.toothmanager.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Description: Security Config
 * @Author xiaolong
 * @Date 2022/1/15 5:11 下午
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] URL_WHITELIST = {

            "/login",
            "/logout",
            "/captcha",
            "/favicon.ico",
            "/v2/api-docs",
            "/swagger-resources/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };


    private final LoginFailureHandler loginFailureHandler;

    private final LoginSuccessHandler loginSuccessHandler;

    private final CaptureFilter captureFilter;

    @Bean
    JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(authenticationManager());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                // 登录配置
                .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                // 禁用Session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //配置规则拦截器
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest().authenticated()

                // 异常处理器

                // 配置自定义规则过滤器
                .and()
                .addFilter(jwtAuthorizationFilter())
                .addFilterBefore(captureFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterAt(usernamePasswordJsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        // 自定义账号密码过滤器
        http.addFilterAt(usernamePasswordJsonAuthenticationBean(), UsernamePasswordJsonAuthenticationFilter.class);
    }

    @Bean
    UsernamePasswordJsonAuthenticationFilter usernamePasswordJsonAuthenticationBean() throws Exception {
        UsernamePasswordJsonAuthenticationFilter userAuthenticationFilter = new UsernamePasswordJsonAuthenticationFilter();
        userAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        userAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        return userAuthenticationFilter;
    }
}
