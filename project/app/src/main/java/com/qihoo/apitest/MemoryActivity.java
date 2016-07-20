package com.qihoo.apitest;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qihoo.apitest.iterator.IteratorTest;
import com.qihoo.apitest.utils.ActivityUtils;

public class MemoryActivity extends Activity implements View.OnClickListener {

    private EditText mEdit = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        ActivityUtils.installAllButtonListener(this, this);
        mEdit = (EditText)findViewById(R.id.testSize);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iterator:
                testIterator();
                break;
        }
    }

    private void testIterator() {
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
                findViewById(R.id.iterator).setEnabled(false);
            }

            @Override
            public void onPostExec() {
                findViewById(R.id.iterator).setEnabled(true);
            }
        }, value);
    }
}
