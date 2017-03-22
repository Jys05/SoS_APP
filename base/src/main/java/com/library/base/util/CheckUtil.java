package com.library.base.util;

import java.util.regex.Pattern;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/27
 * @description 主要验证数据是否符合要求
 */
public class CheckUtil {
    /**
     * 判断一个字符串类型是否为手机号码
     *
     * @param mobile
     * @return 若为手机号码，则返回true
     */
    public static boolean isMobile(String mobile) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([6-8]|0))|(18[0-9]))\\d{8}$";
        return mobile.matches(regex);
    }

    /**
     * 判断一个字符串数据是否为邮箱地址
     *
     * @param email
     * @return 若为邮箱，则返回ture
     */
    public static boolean isEmail(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        if (email.matches(regex)) {
            return true;
        }
        return false;
    }

    /**
     * 判断一组数据中是否有null数据
     *
     * @param array
     * @return 若其中有null数据，则返回ture
     */
    public static boolean isNull(Object... array) {
        for (Object item : array) {
            if (null == item) {
                return true;
            }
            if (item instanceof String && "".equals(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkURL(String url) {
        String regular = "(http|https)://[\\S]*";
        return Pattern.matches(regular, url);
    }

    /**
     * 判断一个数据是否为null
     *
     * @param obj
     * @return 若数据为Null, 则返回true
     */
    public static boolean isNull(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof String && "".equals(obj)) {
            return true;
        } else {
            return false;
        }
    }
}
