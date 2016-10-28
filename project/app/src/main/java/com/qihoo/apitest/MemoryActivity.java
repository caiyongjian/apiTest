package com.qihoo.apitest;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qihoo.apitest.iterator.IteratorTest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemoryActivity extends Activity {

    @BindView(R.id.testSize)
    protected EditText mEdit;

    @BindView(R.id.iterator)
    protected Button mIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iterator)
    protected void testIterator(Button button) {
        Editable editable = mEdit.getText();
        String inputStr = String.valueOf(editable != null ? editable : "200000");
        if (TextUtils.isEmpty(inputStr)) {
            inputStr = "20000";
        }
        int value = Integer.valueOf(inputStr);
        if (value == 0) {
            value = 20000;
        }

        IteratorTest.AsyncTest(new IteratorTest.TestDelegate() {
            @Override
            public void onPreExec() {
                ButterKnife.apply(mIterator, DISABLE);
            }

            @Override
            public void onPostExec() {
                ButterKnife.apply(mIterator, ENABLED, true);
            }
        }, value);
    }

    static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override public void apply(View view, int index) {
            view.setEnabled(false);
        }
    };

    static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override public void set(View view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };
}
