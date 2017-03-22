package com.library.base.util;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.library.base.frame.AppManager;

import java.io.File;
import java.math.BigDecimal;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/28
 * @description 管理缓存的工具类
 */
public class CacheUtil {
    private static CacheUtil instance = null;

    private CacheUtil() {

    }

    public static CacheUtil getInstance() {
        if (instance == null) {
            instance = new CacheUtil();
        }
        return instance;
    }

    /**
     * 清除图片磁盘缓存
     */
    public void clearDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    public void clearMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片所有缓存
     */
    public void clearAllCache(Context context) {
        String exCatchDir = null;
        String catchDir = null;
        if (context.getCacheDir() != null) {
            catchDir = context.getCacheDir().getAbsolutePath();
        }
        if (context.getExternalCacheDir() != null) {
            exCatchDir = context.getExternalCacheDir().getAbsolutePath();
        }
        clearDiskCache(context);
        clearMemoryCache(context);
        if (catchDir != null) {
            deleteFolderFile(catchDir, false);
        }
        if (exCatchDir != null) {
            deleteFolderFile(exCatchDir, false);
        }
    }

    /**
     * 清理缓存，带回调函数
     *
     * @param context
     * @param callback
     */
    public void clearAllCache(Context context, ClearCacheCallback callback) {
        String exCatchDir = null;
        String catchDir = null;
        if (context.getCacheDir() != null) {
            catchDir = context.getCacheDir().getAbsolutePath();
        }
        if (context.getExternalCacheDir() != null) {
            exCatchDir = context.getExternalCacheDir().getAbsolutePath();
        }
        clearDiskCache(context);
        clearMemoryCache(context);
        if (catchDir != null) {
            deleteFolderFile(catchDir, false);
        }
        if (exCatchDir != null) {
            deleteFolderFile(exCatchDir, false);
        }
        if (callback != null) {
            callback.onEnd();
        }
    }

    public interface ClearCacheCallback {
        void onEnd();
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        String exCatchDir = null;
        String catchDir = null;
        //判断内部存储是否有问题
        if (context.getCacheDir() != null) {
            catchDir = context.getCacheDir().getAbsolutePath();
        }
        //判断外部存储是否存在
        if (context.getExternalCacheDir() != null) {
            exCatchDir = context.getExternalCacheDir().getAbsolutePath();
        }
        try {
            long size = 0;
            if (exCatchDir != null) {
                size += getFolderSize(exCatchDir);
            }
            if (catchDir != null) {
                size += getFolderSize(catchDir);
            }
            return getFormatSize(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    public long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                    LogUtil.e("cache", aFileList.getAbsolutePath() + "size=" + aFileList.length());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * @param filePath
     * @return
     * @throws Exception
     */
    public long getFolderSize(String filePath) throws Exception {
        if (filePath == null || filePath.length() == 0) {
            return 0;
        }
        LogUtil.e("cache", filePath);
        File file = new File(filePath);
        return getFolderSize(file);
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    public void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                        LogUtil.e("delete", file.getAbsolutePath());
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                            LogUtil.e("delete", file.getAbsolutePath());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte == 0) {
            return "0MB";
        }
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
