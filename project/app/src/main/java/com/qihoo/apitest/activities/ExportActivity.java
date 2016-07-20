package com.qihoo.apitest.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Log;

public class ExportActivity extends Activity {

    private static final String TAG = "ExportActivity";

    String formatIntent(Intent intent) {
        if (intent == null) {
            return "null";
        }

        intent.getStringExtra("test");

        return intent.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_export);

        Log.i(TAG, String.format("onCreate.printIntent:%s", formatIntent(getIntent())));

        String dataStr = getIntent().getDataString();
        String[] patterns = new String[] {
                ".*\\.html",
                ".*\\.htm",
                ".*\\..*\\.htm",
                ".*\\..*\\.html",
        };
        for (String pat : patterns) {
            check(pat, dataStr);
        }
        finish();
    }

    private void check(String pattern, String testStr) {
        PatternMatcher patternMatcher = new PatternMatcher(pattern, PatternMatcher.PATTERN_SIMPLE_GLOB);
        if (patternMatcher.match(testStr)) {
            Log.i(TAG, String.format("%s Match pattern:%s", testStr, pattern));
        } else {
//            Log.i(TAG, String.format("%s NMatch pattern:%s", testStr, pattern);
        }
    }
}
