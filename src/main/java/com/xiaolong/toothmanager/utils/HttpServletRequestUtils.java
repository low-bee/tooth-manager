package com.xiaolong.toothmanager.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Description: HttpServletRequest 工具类
 * @Author xiaolong
 * @Date 2022/1/15 9:34 下午
 */
@Slf4j
@Component
public class HttpServletRequestUtils {

    public JSONObject getJsonRequest(HttpServletRequest request) {
        JSONObject result = null;

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader();) {
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
            request.setAttribute("isChange", sb.toString());
            result = JSONUtil.parseObj(sb.toString());
        } catch (IOException e) {

        }
        return result;
    }
}
