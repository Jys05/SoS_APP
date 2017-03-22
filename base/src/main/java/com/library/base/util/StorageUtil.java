package com.library.base.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;

/**
 * Summary ：用对外部储存进行操作
 * Created by zhangdm on 2016/1/12.
 */
public class StorageUtil {
    public static final String TAG = "StorageUtil";

    public static String getCaremaPath(Context context) {
        return getExternalCacheDir(context) + "carema.jpg";
    }

    public static String getExternalCacheDir(Context context) {
        if (!checkStorageState())
            return null;
        StringBuilder sb = new StringBuilder();
        File file = context.getExternalCacheDir();
        if (file != null) {
            sb.append(file.getAbsolutePath()).append(File.separator);
        } else {
            sb.append(Environment.getExternalStorageDirectory().getPath()).append("/Android/data/").
                    append(context.getPackageName()).append("/cache/").append(File.separator).
                    toString();
        }
        return sb.toString();
    }

    /**
     * 检测是否有外部存储设备
     *
     * @return
     */
    public static boolean checkStorageState() {
        String state = Environment.getExternalStorageState();
        if (state != null && state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取外部存储设备中的文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            LogUtil.e(TAG, "文件不存在");
        }
        return size;
    }

    /**
     * 获取文件夹的总文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getDirectorySize(File file) throws Exception {
        long size = 0;
        if (file.isDirectory()) {
            File fileList[] = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size += getDirectorySize(fileList[i]);
                } else {
                    size += getFileSize(fileList[i]);
                }
            }
        } else {
            size = getFileSize(file);
        }
        return size;
    }

}
