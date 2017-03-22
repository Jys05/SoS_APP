package com.library.base.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Summary ：获取屏幕的大小尺寸
 * Created by zhangdm on 2016/2/20.
 */
public class ScreenSizeUtil {
    /**
     * 获取屏幕的宽度
     *
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
