package com.xiaolong.toothmanager.utils;

import java.io.Closeable;

/**
 * @Description: 关闭工具类
 * @Author xiaolong
 * @Date 2022/3/28 23:39
 */
public class CloseUtil {

    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }

    public static void close(AutoCloseable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }
}
