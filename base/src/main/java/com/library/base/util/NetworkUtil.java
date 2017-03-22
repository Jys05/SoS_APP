package com.library.base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Summary ：用于检测网络的状态和打开网络状态
 * Created by zhangdm on 2016/1/13.
 */
public class NetworkUtil {
    private static ConnectivityManager connectivityManager;
    private static WifiManager wifiManager;

    /**
     * 初始化工具类
     *
     * @param context
     */
    public static void initUtil(Context context) {
        connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 检测当前是否有网络可用
     *
     * @return
     */
    public static boolean isConnection() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前网络是否为wifi网络
     *
     * @return
     */
    public static boolean isWifiConnection() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前网络是否为手机网络
     *
     * @return
     */
    public static boolean isGpsConnection() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()
                && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前网络状态的类型
     *
     * @return 若没有网络，则返回-1
     */
    public static int getConnectionType() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return networkInfo.getType();
        } else {
            return -1;
        }
    }

    /**
     * 设置WIFI的是否开启
     *
     * @param enabled
     */
    public static void setWifiStatus(boolean enabled) {
        if (wifiManager != null) {
            wifiManager.setWifiEnabled(enabled);
        }
    }

}
