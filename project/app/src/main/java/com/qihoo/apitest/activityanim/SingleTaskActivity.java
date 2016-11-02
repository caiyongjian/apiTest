package com.qihoo.apitest.activityanim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qihoo.apitest.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleTaskActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.enterSingleInstance)
    protected void enterSingleInstance(View view) {
        enterActivity(SingleInstanceActivity.class);
    }

    @OnClick(R.id.enterSingleTop)
    protected void enterSingleTop(View view) {
        enterActivity(SingleTopActivity.class);
    }

    @OnClick(R.id.enterNormal)
    protected void enterNormal(View view) {
        enterActivity(NormalActivity.class);
    }

    @OnClick(R.id.enterThisSingleTask)
    protected void enterSelf(View view) {
        enterActivity(SingleTaskActivity.class);
    }

    private void enterActivity(Class<?> c) {
        startActivity(new Intent(this, c));
        overridePendingTransition(R.anim.aoe, 0);
    }
}
