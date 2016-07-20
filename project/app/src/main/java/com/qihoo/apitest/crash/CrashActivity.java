package com.qihoo.apitest.crash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qihoo.apitest.R;
import com.qihoo.apitest.utils.ActivityUtils;

import java.util.HashMap;
import java.util.Map;

public class CrashActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "CrashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        ActivityUtils.installAllButtonListener(this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_crash_test: {
                crashTest();
            }
                break;
            case R.id.btn_crash_test_ui: {
                crashTestUI();
            }
            break;
            case R.id.crash_two_thread: {
                crashTwoThread();
            }
            break;
            case R.id.print_all_thread: {
                printAllThread();
            }
            break;
            case R.id.anr_test: {
                testANR();
                break;
            }
            case R.id.native_crash_test:
                testNativeCrash();
                break;
        }
    }

    private void testNativeCrash() {
        NativeCrashHandler.getInstance().testNativeCrash();
    }

    private void testANR() {
        sendBroadcast(new Intent(AnrReceiver.ACTION1));
    }

    private void printAllThread() {
        printThreadStacks(Thread.getAllStackTraces());
        Log.i(TAG, "-----------------------------------------------------------------------------");
        printThreadStacks(getCurrentGroupStack());
    }

    private void printThreadStacks(Map<Thread, StackTraceElement[]> stacks) {
        for (Map.Entry<Thread, StackTraceElement[]> entry : stacks.entrySet()) {
            Thread thread = entry.getKey();
            StackTraceElement[] stackTraceElements = entry.getValue();
            String detailStr = String.format("ThreadName:%s, ThreadStack:", thread.getName());
            for (StackTraceElement element : stackTraceElements) {
                detailStr += "\t" + element + "\n";
            }

            Log.i(TAG, detailStr);
        }
    }

    private Map<Thread, StackTraceElement[]> getCurrentGroupStack() {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        Map<Thread, StackTraceElement[]> map = new HashMap<Thread, StackTraceElement[]>();

        // Find out how many live threads we have. Allocate a bit more
        // space than needed, in case new ones are just being created.
        int count = threadGroup.activeCount();
        Thread[] threads = new Thread[count + count / 2];

        // Enumerate the threads and collect the stacktraces.
        count = threadGroup.enumerate(threads);
        for (int i = 0; i < count; i++) {
            map.put(threads[i], threads[i].getStackTrace());
        }

        return map;
    }

    private void crashTwoThread() {
        CrashTest.crashOnUIThread();
        CrashTest.crashOnNewThread();
    }

    private void crashTestUI() {
        CrashTest.crashOnUIThread();
    }

    private void crashTest() {
        CrashTest.crashOnNewThread();
    }
}
