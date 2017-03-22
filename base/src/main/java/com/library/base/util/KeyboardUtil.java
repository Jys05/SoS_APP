package com.library.base.util;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Summary ：键盘的工具类
 * Created by zhangdm on 2016/04/13.
 */
public class KeyboardUtil {
    public static final String TAG = "KeyboardUtil";

    private boolean isShowKeyboard = false;

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    private void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                // 应用可以显示的区域。此处包括应用占用的区域，
                // 以及ActionBar和状态栏，但不含设备底部的虚拟按键。
                Rect r = new Rect();
                root.getWindowVisibleDisplayFrame(r);

                // 屏幕高度。这个高度不含虚拟按键的高度
                int screenHeight = root.getRootView().getHeight();

                int heightDiff = screenHeight - (r.bottom - r.top);
                int keyboardHeight = 0;
                int statusBarHeight = 0;


                // 在不显示软键盘时，heightDiff等于状态栏的高度
                // 在显示软键盘时，heightDiff会变大，等于软键盘加状态栏的高度。
                // 所以heightDiff大于状态栏高度时表示软键盘出现了，
                // 这时可算出软键盘的高度，即heightDiff减去状态栏的高度
                if (keyboardHeight == 0 && heightDiff > statusBarHeight) {
                    keyboardHeight = heightDiff - statusBarHeight;
                }

                if (isShowKeyboard) {
                    // 如果软键盘是弹出的状态，并且heightDiff小于等于状态栏高度，
                    // 说明这时软键盘已经收起
                    if (heightDiff <= statusBarHeight) {
                        isShowKeyboard = false;
                        root.scrollTo(0, 0);
                        LogUtil.i(TAG, "isShowKeyboard=" + isShowKeyboard);
                    }
                } else {
                    // 如果软键盘是收起的状态，并且heightDiff大于状态栏高度，
                    // 说明这时软键盘已经弹出
                    if (heightDiff > statusBarHeight) {
                        isShowKeyboard = true;
                        int[] location = new int[2];
                        //获取scrollToView在窗体的坐标
                        scrollToView.getLocationInWindow(location);
                        //计算root滚动高度，使scrollToView在可见区域
                        int srollHeight = (location[1] + scrollToView.getHeight() + 20) - r.bottom;
                        root.scrollTo(0, srollHeight);
                        LogUtil.i(TAG, "isShowKeyboard=" + isShowKeyboard);
                    }
                }
            }
        });
    }
}
