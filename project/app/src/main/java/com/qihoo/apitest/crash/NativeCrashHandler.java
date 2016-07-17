package com.qihoo.apitest.crash;


/**
 * Created by caiyongjian on 16-7-15.
 */
public class NativeCrashHandler {
    static{
        try {
            System.loadLibrary("NativeCrashHandler");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static final NativeCrashHandler SINSTANCE = new NativeCrashHandler();

    private native void nativeInit();

    private native int getVersion();

    private native int invideCrash(int input);

    public void init() {
        nativeInit();
        getVersion();
    }

    public void testNativeCrash() {
        invideCrash(0);
    }

    public static NativeCrashHandler getInstance() {
        return SINSTANCE;
    }
}
