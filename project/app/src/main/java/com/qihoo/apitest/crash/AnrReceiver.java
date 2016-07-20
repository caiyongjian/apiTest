package com.qihoo.apitest.crash;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AnrReceiver extends BroadcastReceiver {
    public static final String ACTION1 = "com.qihoo.apitest.crash.Anr";
    public AnrReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
//        switch (action) {
//            case ACTION1:
//                try {
//                    Thread.sleep(20000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
    }
}
