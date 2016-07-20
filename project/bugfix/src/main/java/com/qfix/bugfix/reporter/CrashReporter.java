package com.qfix.bugfix.reporter;

import java.io.File;

/**
 * Created by caiyongjian on 16-7-20.
 */
public class CrashReporter {
    private static final CrashReporter SINSTANCE = new CrashReporter();

    public static CrashReporter getInstance() {
        return SINSTANCE;
    }

    public void updateCrash(File crashFile) {

    }

    public void updateCrash(String crashContent) {

    }

    public void updateCrashs(File crashDir) {

    }
}
