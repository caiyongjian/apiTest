package com.qihoo.apitest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.qihoo.apitest.crash.CrashActivity;
import com.qihoo.apitest.notification.NotificationTest;
import com.qihoo.apitest.service.TestService;
import com.qihoo.apitest.settings.SettingsActivity;
import com.qihoo.apitest.utils.ActivityUtils;

import java.sql.Time;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEditText;
    IBinder mService;
    ServiceConnection mConnect =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = service;
            String target = null;
            try {
                target = String.format("%d", service);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            Log.i(Global.STEP_LOG, "onServiceConnect:" + target, new Throwable("printStack"));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityUtils.installAllButtonListener(this, this);

        mEditText = (EditText)findViewById(R.id.testEdit);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAssert:
                testAssert();
                break;
            case R.id.buttonProvider:
                testProviderLaunch();
                break;
            case R.id.buttonService:
                testServiceLaunch();
                break;
            case R.id.buttonNotification:
                testNotication();
                break;
            case R.id.crashTest:
                testCrash();
                break;
            case R.id.MemoryTest:
                enterMemoryActivity();
                break;
            case R.id.system_settings:
                enterActivity(SettingsActivity.class);
                break;
            default:
                break;
        }
    }

    private void enterMemoryActivity() {
        startActivity(new Intent(this, MemoryActivity.class));
    }
    private void enterActivity(Class cls) {
        startActivity(new Intent(this, cls));
    }

    private void testCrash() {
        startActivity(new Intent(this, CrashActivity.class));
    }

    private void testNotication() {
        new NotificationTest(this).doNotify();
    }

    private void testServiceLaunch() {
        Intent intent = new Intent(this, TestService.class);
        boolean bindres = getApplicationContext().bindService(intent,
                new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        mService = service;
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        mService = null;
                    }
                }
                , BIND_AUTO_CREATE);
        Log.i(Global.STEP_LOG, String.format("testServiceLaunch bind result:%b", bindres));
    }

    private void testProviderLaunch() {
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.qihoo.apitest.provider.TestProvider/test"),
                null, null, null, null);
        Log.i(Global.STEP_LOG, "testProviderLaunch", new Throwable("printStack"));
    }

    private void testAssert() {
        Assert.setEnable(true);
        Assert.assertTrue(!TextUtils.isEmpty(mEditText.getText()), "TETTTTTT");
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
    }

    @Override
    public void setTheme(@StyleRes int resid) {
        super.setTheme(resid);
    }
}
