package com.library.base.util.mainrun;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/28
 * @description 描述类的功能
 */
public class HandlerPoster extends Handler {
    private final int ASYNC = 0x1;
    private final int SYNC = 0x2;
    private final Queue<Runnable> asyncPool;
    private final Queue<SyncPost> syncPool;
    private final int maxMillisInsideHandleMessage;
    private boolean asyncActive = false;
    private boolean syncActive = false;

    public HandlerPoster(Looper looper, int maxMillisInsideHandleMessage) {
        super(looper);
        this.asyncPool = new LinkedList<>();
        this.syncPool = new LinkedList<>();
        this.maxMillisInsideHandleMessage = maxMillisInsideHandleMessage;
    }

    public void dispose() {
        this.removeCallbacksAndMessages(null);
        this.asyncPool.clear();
        this.syncPool.clear();
    }

    public void async(Runnable runnable) throws Exception {
        synchronized (asyncPool) {
            asyncPool.offer(runnable);
            if (!asyncActive) {
                asyncActive = true;
                if (!this.sendMessage(obtainMessage(ASYNC))) {
                    throw new Exception("Could not send handler message");
                }
            }

        }
    }

    public void sync(SyncPost post) throws Exception {
        synchronized (syncPool) {
            syncPool.offer(post);
            if (!syncActive) {
                syncActive = true;
                if (!sendMessage(obtainMessage(SYNC))) {
                    throw new Exception("Could not send handler message");
                }
            }
        }
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == ASYNC) {
            boolean rescheduled = false;
            try {
                long started = SystemClock.uptimeMillis();
                while (true) {
                    Runnable runnable = asyncPool.poll();
                    if (runnable == null) {
                        synchronized (asyncPool) {
                            runnable = asyncPool.poll();
                            if (runnable == null) {
                                asyncActive = false;
                                return;
                            }
                        }
                    }
                    runnable.run();
                    long timeInMethod = SystemClock.uptimeMillis() - started;
                    if (timeInMethod >= maxMillisInsideHandleMessage) {
                        if (!sendMessage(obtainMessage(ASYNC))) {
                            throw new Exception("Could not send handler message");
                        }
                        rescheduled = true;
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                asyncActive = rescheduled;
            }
        } else if (msg.what == SYNC) {
            boolean rescheduled = false;
            try {
                long started = SystemClock.uptimeMillis();
                while (true) {
                    SyncPost post = syncPool.poll();
                    if (post == null) {
                        synchronized (syncPool) {
                            // Check again, this time in synchronized
                            post = syncPool.poll();
                            if (post == null) {
                                syncActive = false;
                                return;
                            }
                        }
                    }
                    post.run();
                    long timeInMethod = SystemClock.uptimeMillis() - started;
                    if (timeInMethod >= maxMillisInsideHandleMessage) {
                        if (!sendMessage(obtainMessage(SYNC))) {
                            throw new Exception("Could not send handler message");
                        }
                        rescheduled = true;
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                syncActive = rescheduled;
            }
        } else {
            super.handleMessage(msg);
        }
    }
}

final class SyncPost {
    boolean end = false;
    Runnable runnable;

    public SyncPost(Runnable runnable) {
        this.runnable = runnable;
    }

    public void run() {
        synchronized (this) {
            runnable.run();
            end = true;
            try {
                this.notifyAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void waitRun() {
        if (!end) {
            synchronized (this) {
                if (!end) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}