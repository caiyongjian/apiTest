package com.qihoo.apitest.butterknife;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.qihoo.apitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButterKnifeActivity extends Activity {

    @BindView(R.id.btn_butter_knife)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife);
        ButterKnife.bind(this);

        mButton.setText("test");
    }

    @OnClick(R.id.btn_butter_knife)
    protected void onClickButtonKnife(Button button) {
        button.setText("hello");
    }

}
