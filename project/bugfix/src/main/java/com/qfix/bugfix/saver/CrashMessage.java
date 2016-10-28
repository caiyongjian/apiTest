package com.qfix.bugfix.saver;

import com.qfix.bugfix.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

/**
 * Created by caiyongjian on 16-7-20.
 */
public class CrashMessage implements MessageSource {
    private final static String TAG_THREAD_NAME = "threadName";
    private final static String TAG_CRASH_MSG = "crashMsg";

    private String mThreadName;
    private String mCrashMsg;

    public CrashMessage(Thread thread, Throwable ex) {
        mThreadName = thread.getName();
        mCrashMsg = getStackString(ex);
    }

    public String getThreadName() {
        return mThreadName;
    }

    public String getCrashMsg() {
        return mCrashMsg;
    }

    private String getStackString(Throwable throwable) {
        final Writer stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);

        Throwable cause = throwable;
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        final String stackString = stringWriter.toString();
        printWriter.close();
        return stackString;
    }

    @Override
    public void print(JSONObject jsonObject) throws JSONException {
        Utils.sparePrint(jsonObject, TAG_THREAD_NAME, mThreadName);
        Utils.sparePrint(jsonObject, TAG_CRASH_MSG, mCrashMsg);
    }

    @Override
    public void getSign(HashMap<String, String> signMaps) {
    }
}
