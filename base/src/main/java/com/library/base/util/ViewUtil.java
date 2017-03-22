package com.library.base.util;

import android.view.View;

/**
 * Summary ：对控件进行操作工具类
 * Created by zhangdm on 2016/2/20.
 */
public class ViewUtil {
    /**
     * 设置控件是否可见
     *
     * @param view
     * @param visibility true为可见，false为不可见
     */
    public static void setViewVisibility(View view, boolean visibility) {
        if (view == null) {
            return;
        }
        if (visibility) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
