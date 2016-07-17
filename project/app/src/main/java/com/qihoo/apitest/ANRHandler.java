package com.qihoo.apitest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import java.util.Map;
import java.util.Set;

/**
 * Created by caiyongjian on 16-7-15.
 */
public class ANRHandler {
    private static final String TAG = "ANRHandler";

    private static MyReceiver myReceiver;

    public static void registerANRReceiver(Context context) {
        myReceiver = new MyReceiver();
        context.registerReceiver(myReceiver, new IntentFilter(ACTION_ANR));
    }

    public static void unregisterANRReceiver(Context context) {
        if (myReceiver == null) {
            return;
        }
        context.unregisterReceiver(myReceiver);
    }

    private static final String ACTION_ANR = "android.intent.action.ANR";

    private static class MyReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(ACTION_ANR)) {
                System.out.println("MyReceiver -----ANR ---------");

                System.out.println("package:"+intent.getPackage());

                System.out.println("dataString:"+intent.getDataString());

                System.out.println("component:"+intent.getComponent());

                for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
                    Thread thread = stackTrace.getKey();
                    StackTraceElement[] stack = stackTrace.getValue();
                    System.out.print("Thread：" + thread.getName() + "\n");
                    for (StackTraceElement element : stack) {
                        System.out.print("\t" + element + "\n");
                    }
                    System.out.print("\n");
                }

//                Bundle bundle = intent.getExtras();
//                Set<String> keySet = bundle.keySet();
//                for(String key : keySet){
//                    System.out.println(String.valueOf(bundle.get(key)));
//                }

                Log.i(TAG, String.format("handle ANR:%s", intent.toString()));
            }

        }
    }
}
