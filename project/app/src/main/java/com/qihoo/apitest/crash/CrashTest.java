package com.qihoo.apitest.crash;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by caiyongjian on 16-6-30.
 */
public class CrashTest {
    public static void crashOnNewThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Thread.currentThread().getName().equals("crashTest")) {
                    throw new RuntimeException("testCrash");
                }
            }
        }, "crashTest").start();
    }

    public static void crashOnUIThread() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("UI crash!");
            }
        });
    }
}
