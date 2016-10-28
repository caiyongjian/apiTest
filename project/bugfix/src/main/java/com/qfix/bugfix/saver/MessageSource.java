package com.qfix.bugfix.saver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by caiyongjian on 16-7-20.
 */
public interface MessageSource {
    void print(JSONObject jsonObject) throws JSONException;
    void getSign(HashMap<String, String> signMaps);
}
