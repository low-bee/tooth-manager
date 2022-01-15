package com.xiaolong.toothmanager.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Description: 验证码错误异常
 * @Author xiaolong
 * @Date 2022/1/15 8:13 下午
 */
public class CaptchaException extends AuthenticationException {
    public CaptchaException(String msg) {
        super(msg);
    }
}
