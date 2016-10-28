package com.qihoo.apitest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.qihoo.apitest.butterknife.ButterKnifeActivity;
import com.qihoo.apitest.crash.CrashActivity;
import com.qihoo.apitest.dagger.CoffeeApp;
import com.qihoo.apitest.notification.NotificationTest;
import com.qihoo.apitest.service.TestService;
import com.qihoo.apitest.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity {
    @BindView(R.id.testEdit)
    protected EditText mEditText;

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
        ButterKnife.bind(this);

        CoffeeApp.main(null);
    }

    @OnClick(R.id.enterButterKnife)
    protected void onClickEnterButterKnife(Button button) {
        enterActivity(ButterKnifeActivity.class);
    }


    @OnClick(R.id.system_settings)
    protected void enterSettingActivity(Button button) {
        enterActivity(SettingsActivity.class);
    }

    @OnClick(R.id.MemoryTest)
    protected void enterMemoryActivity(Button button) {
            startActivity(new Intent(this, MemoryActivity.class));
    }
    private void enterActivity(Class cls) {
        startActivity(new Intent(this, cls));
    }

    @OnClick(R.id.crashTest)
    protected void testCrash(Button button) {
        startActivity(new Intent(this, CrashActivity.class));
    }

    @OnClick(R.id.buttonNotification)
    protected void testNotication(Button button) {
        new NotificationTest(this).doNotify();
    }

    @OnClick(R.id.buttonService)
    protected void testServiceLaunch(Button button) {
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

    @OnClick(R.id.buttonProvider)
    protected void testProviderLaunch(Button button) {
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.qihoo.apitest.provider.TestProvider/test"),
                null, null, null, null);
        Log.i(Global.STEP_LOG, "testProviderLaunch", new Throwable("printStack"));
    }

    @OnClick(R.id.buttonAssert)
    protected void testAssert(Button button) {
        Assert.setEnable(true);
        Assert.assertTrue(!TextUtils.isEmpty(mEditText.getText()), "TETTTTTT");
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
    }

    @Override
    public void setTheme(int resid) {
        super.setTheme(resid);
    }
}
