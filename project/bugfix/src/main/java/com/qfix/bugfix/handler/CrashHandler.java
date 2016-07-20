package com.qfix.bugfix.handler;

import com.qfix.bugfix.logger.QLog;

/**
 * Created by caiyongjian on 16-7-20.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";

    private CrashHandlerClient mCrashHandlerClient = null;
    private Thread.UncaughtExceptionHandler mParent = null;

    public CrashHandler(CrashHandlerClient client) {
        mCrashHandlerClient = client;
    }

    @Override
    public synchronized void uncaughtException(Thread thread, Throwable ex) {
        QLog.i(TAG, "uncaughtException Enter. currentThread:%s, CrashThread:%s, throwable:%s",
                ex, Thread.currentThread().getName(), thread.getName(), ex.getMessage());

        if (mCrashHandlerClient != null) {
            mCrashHandlerClient.handleCrash(thread, ex);
        }

        if (mParent != null) {
            mParent.uncaughtException(thread, ex);
        }
    }

    public void init() {
        installHandler();
    }

    private void installHandler() {
        mParent = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}