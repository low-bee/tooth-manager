package com.xiaolong.toothmanager.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 查询帮助类
 * @Author xiaolong
 * @Date 2022/4/10 09:44
 */
public class QueryHelp {


    public static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }
}
