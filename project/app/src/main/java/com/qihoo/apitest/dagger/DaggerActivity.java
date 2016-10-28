package com.qihoo.apitest.dagger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.qihoo.apitest.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DaggerActivity extends Activity {
    @Inject
    ThemeMode mThemeMode;

    @Inject
    CoffeeMaker mCoffeeMaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        ButterKnife.bind(this);

        DaggerCoffee.getInstance().inject(this);

        if (mThemeMode.isNight()) {
            Toast.makeText(this, "isNight", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.enterDagger2)
    protected void onClickDagger2(Button button) {
        startActivity(new Intent(this, Dagger2Activity.class));
    }
}
