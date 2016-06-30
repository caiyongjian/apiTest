package com.qihoo.apitest;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.qihoo.apitest.crash.CrashHandler;

/**
 * Created by caiyongjian on 16-6-19.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(Global.STEP_LOG, "MyApp.onCreate", new Throwable("printStack"));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        CrashHandler.init();
        Log.i(Global.STEP_LOG, "MyApp.attachBaseContext", new Throwable("printStack"));
    }
}
