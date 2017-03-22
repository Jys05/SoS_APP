package com.library.base.util;

import android.app.Activity;

import com.library.base.dialog.LoadingDialog;
import com.library.base.frame.AppManager;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/27
 * @description 显示加载框工具类
 */

public class LoadingUtil {

    private static Activity mCurrentActivity = null;
    private static LoadingDialog mDialog = null;
    private static boolean mLock = false;

    public static void lock() {
        mLock = true;
    }

    public static void unLock() {
        mLock = false;
    }

    public static void show() {
        Activity temp = AppManager.getInstance().currentActivity();
        if (temp == null) {
            return;
        }
        if (temp != mCurrentActivity) {
            mCurrentActivity = temp;
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mDialog = null;
        }
        if (mDialog == null) {
            mDialog = new LoadingDialog(temp);
        }
        try {
            mDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hidden() {
        if (mLock) {
            return;
        }
        if (mDialog != null && mDialog.isShowing()) {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
