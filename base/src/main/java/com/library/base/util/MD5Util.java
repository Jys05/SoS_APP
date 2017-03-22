package com.library.base.util;

import java.security.MessageDigest;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/27
 * @description MD5加密操作
 */
public class MD5Util {

    private static MD5Util instance = null;

    private MD5Util() {
    }

    public static MD5Util getInstance() {
        if (instance == null) {
            instance = new MD5Util();
        }
        return instance;
    }

    public String getMD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
