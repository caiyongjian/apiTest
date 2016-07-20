package com.qfix.bugfix.utils;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by caiyongjian on 16-7-20.
 */
public class Utils {

    public static final String DATE_TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static String currentFormatTime() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT_STRING);
        return format.format(new Date(System.currentTimeMillis()));
    }

    public static void sparePrint(JSONObject jsonObject, String key, String value) throws JSONException {
        if (!TextUtils.isEmpty(value)) {
            jsonObject.put(key, value);
        }
    }
}
