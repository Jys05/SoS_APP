package com.library.base.util;

import android.util.Log;

/**
 * Summary ：负责程序中log数据的控制
 * Created by zhangdm on 2015/12/14.
 */
public class LogUtil {
    private static int v = 0;
    private static int d = 1;
    private static int i = 2;
    private static int w = 3;
    private static int e = 4;
    private static int TAG = -1;

    public static void v(String tag, String msg) {
        if (v > TAG) {
            Log.v(tag, "" + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (d > TAG) {
            Log.d(tag, "" + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (i > TAG) {
            Log.i(tag, "" + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (w > TAG) {
            Log.w(tag, "" + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (e > TAG) {
            Log.e(tag, "" + msg);
        }
    }
}
