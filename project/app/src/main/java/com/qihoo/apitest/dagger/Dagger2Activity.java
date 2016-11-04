package com.qihoo.apitest.dagger;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.qihoo.apitest.R;

import javax.inject.Inject;

public class Dagger2Activity extends Activity {
    @Inject
    ThemeMode mThemeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);

        DaggerCoffee.getInstance().inject(this);

        if (mThemeMode.isNight()) {
            Toast.makeText(this, "isNight", Toast.LENGTH_LONG).show();
        }
    }
}
