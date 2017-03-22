package com.library.base.util;

import android.content.Context;

/**
 * Summary ：用于尺寸的转换
 * Created by zhangdm on 2016/2/15.
 */
public class DisplayUtil {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * dp值转px值
     *
     * @param dpValue
     * @return
     */
    public static int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px值转dp值
     *
     * @param pxValue
     * @return
     */
    public static int px2dp(float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
