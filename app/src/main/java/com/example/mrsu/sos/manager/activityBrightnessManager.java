package com.example.mrsu.sos.manager;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Mr.Su on 2017-3-11.
 * 当前屏幕亮度，仅作用于当前的activity。
 * Android调整屏幕亮度和改变屏幕亮度调整模式的代码
 */

public class activityBrightnessManager {

    /**
     * 设置当前activity的屏幕亮度
     *
     * @param paramFloat 0-1.0f
     * @param activity   需要调整亮度的activity
     */
    public static void setActivityBrightness(float paramFloat, Activity activity) {
        Window localWindow = activity.getWindow();
        WindowManager.LayoutParams params = localWindow.getAttributes();
        params.screenBrightness = paramFloat;
        localWindow.setAttributes(params);
    }

    /**
     * 获取当前activity的屏幕亮度
     *
     * @param activity 当前的activity对象
     * @return 亮度值范围为0-0.1f，如果为-1.0，则亮度与全局同步。
     */
    public static float getActivityBrightness(Activity activity) {
        Window localWindow = activity.getWindow();
        WindowManager.LayoutParams params = localWindow.getAttributes();
        return params.screenBrightness;
    }
}
