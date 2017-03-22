package com.library.base.util.mainrun;

import android.os.Looper;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/28
 * @description 描述类的功能
 */
public class MainRunUtil {

    private static HandlerPoster mainPoster = null;

    private static HandlerPoster getMainPoster() {
        if (mainPoster == null) {
            synchronized (MainRunUtil.class) {
                if (mainPoster == null) {
                    mainPoster = new HandlerPoster(Looper.getMainLooper(), 20);
                }
            }
        }
        return mainPoster;
    }

    // 异步进入主线程,无需等待
    public static void async(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
            return;
        }
        try {
            getMainPoster().async(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 同步进入主线程,等待主线程处理完成后继续执行子线程
    public static void sync(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
            return;
        }
        try {
            SyncPost poster = new SyncPost(runnable);
            getMainPoster().sync(poster);
            poster.waitRun();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void dispose() {
        if (mainPoster != null) {
            mainPoster.dispose();
            mainPoster = null;
        }
    }

}
