package com.library.base.util;

import android.util.Base64;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/27
 * @description 用于DES加密和解密操作
 */
public class DESUtil {
    private final String DES = "DES";
    private final String KEY = "des";

    private static DESUtil instance = null;

    private DESUtil() {
    }

    public static DESUtil getInstance() {
        if (instance == null) {
            instance = new DESUtil();
        }
        return instance;
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public String encrypt(String data, String key) {
        String strs = "null";
        try {
            byte[] bt = encrypt(data.getBytes(), key.getBytes());
            strs = new String(Base64.encode(bt, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs.trim();
    }

    public String encrypt(String data) {
        return encrypt(data, KEY);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public String decrypt(String data, String key) {
        if (data == null)
            return null;
        byte[] bt = null;
        try {
            byte[] buf = Base64.decode(data, Base64.DEFAULT);
            bt = decrypt(buf, key.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bt != null) {
            return new String(bt);
        } else {
            return "null";
        }
    }

    public String decrypt(String data) {
        return decrypt(data, KEY);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }


    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
}
