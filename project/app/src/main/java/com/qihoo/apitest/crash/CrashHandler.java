package com.qihoo.apitest.crash;

import android.text.TextUtils;
import android.util.Log;

import com.qihoo.apitest.utils.PLog;

/**
 * Created by caiyongjian on 16-6-30.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";

    private Thread.UncaughtExceptionHandler mParent;

    CrashHandler(Thread.UncaughtExceptionHandler parent) {
        mParent = parent;
    }

    @Override
    public synchronized void uncaughtException(Thread thread, Throwable ex) {
        PLog.i(TAG, "uncaughtException Enter");

        String currentThreadName = Thread.currentThread().getName();
        String crashThreadName = thread.getName();
        if (TextUtils.equals(currentThreadName, crashThreadName)) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        PLog.i(TAG, "uncaughtException. currentThread:%s, CrashThread:%s, throwable:%s\n",
                ex, currentThreadName, crashThreadName, ex.getMessage());
        if (mParent != null) {
            mParent.uncaughtException(thread, ex);
        }
    }

    public static void init() {
        Thread.setDefaultUncaughtExceptionHandler(
                new CrashHandler(Thread.getDefaultUncaughtExceptionHandler()));
    }
}
