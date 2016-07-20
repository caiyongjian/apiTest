package com.qfix.bugfix;

import android.content.Context;

import com.qfix.bugfix.handler.CrashHandler;
import com.qfix.bugfix.handler.CrashHandlerClient;

/**
 * Created by caiyongjian on 16-7-20.
 */
public class CrashHelper extends CrashHandlerClient {
    private static CrashHelper ourInstance = new CrashHelper();

    public static CrashHelper getInstance() {
        return ourInstance;
    }

    private Context mContext = null;

    private CrashHelper() {
    }

    public void init(Context context) {
        mContext = context;
        new CrashHandler(this).init();
    }

    @Override
    public void handleCrash(Thread thread, Throwable ex) {
        super.handleCrash(thread, ex);
    }
}
