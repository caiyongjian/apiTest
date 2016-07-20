package com.qihoo.apitest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.qihoo.apitest.Global;

/**
 * Created by caiyongjian on 16-6-19.
 */
public class TestService extends Service {


    private static class IT extends ITestService.Stub {
        long count = 0;

        @Override
        public void count() throws RemoteException {
            count++;
        }
    }

    IT itttt = new IT();

    com.qihoo.apitest.service.ITestService it;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(Global.STEP_LOG, "TestService.onCreate", new Throwable("printStack"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(Global.STEP_LOG, "TestService.onBind", new Throwable("printStack"));
        return itttt;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(Global.STEP_LOG, "TestService.onStartCommand", new Throwable("printStack"));
        return super.onStartCommand(intent, flags, startId);
    }
}
