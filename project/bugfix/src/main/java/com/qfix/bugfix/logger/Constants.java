package com.qfix.bugfix.logger;

import android.os.Environment;

import java.io.File;

public class Constants {
    /**
     * path relative to Environment.getExternalStorageDirectory()
     *
     * mostly /storage/emulated/0/qfix/bugfix/log/.
     *
     * */
    public static final String LOG_DIR_PATH = "qfix/bugfix/log/";

    /**
     * If log file size beyond 8 MB. It will be deleted and a new file will be created.
     * */
    public static final long MAX_LOG_FILE = 1024 * 1024 * 8; //8MB

    public static File getLogDir() {
        return new File(Environment.getExternalStorageDirectory(), Constants.LOG_DIR_PATH);
    }

    public static boolean isLogDirExist() {
        File logDir = getLogDir();
        if (logDir.exists()) {
            return true;
        }
        return false;
    }
}
