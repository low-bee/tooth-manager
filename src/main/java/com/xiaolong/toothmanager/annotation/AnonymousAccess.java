package com.xiaolong.toothmanager.annotation;

import java.lang.annotation.*;

/**
 * @Description: 用于标记匿名访问方法
 * @Author xiaolong
 * @Date 2022/3/28 23:58
 */
@Inherited
@Documented
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousAccess {

}
