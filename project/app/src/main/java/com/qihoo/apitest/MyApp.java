package com.qihoo.apitest;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.qihoo.apitest.crash.CrashHandler;
import com.qihoo.apitest.crash.NativeCrashHandler;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by caiyongjian on 16-6-19.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(getApplicationContext(), "900040350", true);
        ANRHandler.registerANRReceiver(this);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(Global.STEP_LOG, "MyApp.onCreate", new Throwable("printStack"));
        NormalTest.Test();
        updateDimension(getResources());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        CrashHandler.init();
        NativeCrashHandler.getInstance().init();
        Log.i(Global.STEP_LOG, "MyApp.attachBaseContext", new Throwable("printStack"));
    }

    public final static void updateDimension(Resources resource) {
        final DisplayMetrics displayMetrics = resource.getDisplayMetrics();
        float density = displayMetrics.density;
        int densityDpi = displayMetrics.densityDpi;
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
    }
}
