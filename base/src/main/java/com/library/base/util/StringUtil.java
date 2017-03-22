package com.library.base.util;

/**
 * Summary ：负责字符串的转换以及对字符串的判断处理
 * Created by zhangdm on 2015/12/14.
 */
public class StringUtil {

    /**
     * 判断字符串是否为null或字符串是否为空
     *
     * @param s
     * @return 若为空或null，则返回true
     */
    public static boolean isEmpty(String s) {
        return (s == null || s.length() == 0);
    }

    /**
     * 将字符串转换为int
     *
     * @param s
     * @return 若字符串为null或不符合int格式，则返回-1
     */
    public static int toInt(String s) {
        if (null == s) {
            return -1;
        }
        String regex = "^\\d{0,11}$";
        return s.matches(regex) ? Integer.valueOf(s) : -1;
    }

    /**
     * 将字符串转换为long
     *
     * @param s
     * @return 若字符串为null或不符合long格式，则返回-1
     */
    public static long toLong(String s) {
        if (null == s) {
            return -1;
        }
        String regex = "^\\d{0,20}$";
        return s.matches(regex) ? Long.valueOf(s) : -1;
    }


}
