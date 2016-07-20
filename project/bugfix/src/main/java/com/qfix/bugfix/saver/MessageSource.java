package com.qfix.bugfix.saver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by caiyongjian on 16-7-20.
 */
public interface MessageSource {
    void print(JSONObject jsonObject) throws JSONException;
}
