package com.qfix.bugfix.saver;

import com.qfix.bugfix.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by caiyongjian on 16-7-20.
 */
public class AppMessage implements MessageSource {
    private final static String TAG_VERSION_CODE = "verCode";
    private final static String TAG_VERSION_NAME = "verName";
    private final static String TAG_START_TIME = "startTime";
    private final static String TAG_CRASH_TIME = "crashTime";

    private String mVersionCode;
    private String mVersionName;
    private String mStartTime;
    private String mCrashTime;

    public AppMessage(String versionCode, String versionName) {
        mVersionCode = versionCode;
        mVersionName = versionName;
        mStartTime = Utils.currentFormatTime();
    }

    public void setCrashTime() {
        mCrashTime = Utils.currentFormatTime();
    }

    @Override
    public void print(JSONObject jsonObject) throws JSONException {
        Utils.sparePrint(jsonObject, TAG_VERSION_CODE, mVersionCode);
        Utils.sparePrint(jsonObject, TAG_VERSION_NAME, mVersionName);
        Utils.sparePrint(jsonObject, TAG_START_TIME, mStartTime);
        Utils.sparePrint(jsonObject, TAG_CRASH_TIME, mCrashTime);
    }

    @Override
    public void getSign(HashMap<String, String> signMaps) {
        signMaps.put(TAG_VERSION_CODE, mVersionCode);
        signMaps.put(TAG_VERSION_NAME, mVersionName);
    }
}
