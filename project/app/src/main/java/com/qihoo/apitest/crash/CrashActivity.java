package com.qihoo.apitest.crash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qihoo.apitest.R;
import com.qihoo.apitest.utils.ActivityUtils;

public class CrashActivity extends AppCompatActivity implements View.OnClickListener {

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
        }
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
