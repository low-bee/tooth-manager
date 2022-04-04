package com.xiaolong.toothmanager.common.lang;

/**
 * @Description: 应用常量
 * @Author xiaolong
 * @Date 2022/1/15 4:50 下午
 */
public class Const {
    /**
     * Redis 中保存的图片的key
     */
    public final static String CAPTCHA_KEY = "captcha";

    /**
     * 用于IP定位转换
     */
    public static final String REGION = "内网IP|内网IP";
    /**
     * win 系统
     */
    public static final String WIN = "win";

    /**
     * mac 系统
     */
    public static final String MAC = "mac";

    /**
     * 常用接口
     */
    public static class Url {
        // IP归属地查询
        public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";
    }

    public static final String PREFIX_HOSPITAL = "hospital";
    public static final String PRE = "pre";


}
