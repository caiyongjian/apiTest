
package com.qihoo.apitest.utils;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangyong on 14/10/18.
 */
public class PLog {

//    private static final String TAG = "PLog";

    private static final int VERBOSE = android.util.Log.VERBOSE;
    private static final int DEBUG = android.util.Log.DEBUG;
    private static final int INFO = android.util.Log.INFO;
    private static final int WARN = android.util.Log.WARN;
    private static final int ERROR = android.util.Log.ERROR;
    private static final int ASSERT = android.util.Log.ASSERT;
    private static final long MAX_LOG_FILE = 1024 * 1024 * 8; //8MB

    private static boolean sDebug = false;
    private static boolean sFileLog = false;
    private static SimpleDateFormat sFormat = null;
    private static SimpleDateFormat getFormat() {
        if (sFormat == null) {
            sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        }
        return sFormat;
    }

    private static SimpleDateFormat sFormat1 = null;

    private static SimpleDateFormat getFormat1() {
        if (sFormat1 == null) {
            sFormat1 = new SimpleDateFormat("yyyyMMdd");
        }
        return sFormat1;
    }

    private PLog() {
    }

    private static final String LOG_DIR_PATH = "360Browser/Log/Plugin/";

    private static final File sDir = new File(Environment.getExternalStorageDirectory(), LOG_DIR_PATH);

    private static HandlerThread sHandlerThread;
    private static Handler sHandler;

    static {
        sFileLog = false;
        sDebug = true;
        if (sDebug) {
            sHandlerThread = new HandlerThread("PluginFramework@FileLogThread");
            sHandlerThread.start();
            sHandler = new Handler(sHandlerThread.getLooper());
        }
    }

    public static boolean isDebug() {
        return sDebug;
    }

    private static boolean isFileLog() {
        return sFileLog;
    }

    public static boolean isLoggable(int i) {
        return isDebug();
    }

    public static boolean isLoggable() {
        return isDebug();
    }

    private static String levelToStr(int level) {
        switch (level) {
            case VERBOSE:
                return "V";
            case DEBUG:
                return "D";
            case INFO:
                return "I";
            case WARN:
                return "W";
            case ERROR:
                return "E";
            case ASSERT:
                return "A";
            default:
                return "UNKNOWN";
        }
    }

    private static File getLogFile() {
        File file = new File(Environment.getExternalStorageDirectory(),
                String.format("%s/Log_%s_%s.log", LOG_DIR_PATH, getFormat1().format(new Date()), Process.myPid()));
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return file;
    }

    private static void logToFile(final int level, final String tag, final String format, final Object[] args, final Throwable tr) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                logToFileInner(level, tag, format, args, tr);
            }
        });
    }

    private static void logToFileInner(int level, String tag, String format, Object[] args, Throwable tr) {
        PrintWriter writer = null;
        try {
            if (!isFileLog()) {
                return;
            }

            File logFile = getLogFile();
            if (logFile.length() > MAX_LOG_FILE) {
                logFile.delete();
            }
            writer = new PrintWriter(new FileWriter(logFile, true));
            String msg = String.format(format, args);
            String log = String.format("%s %s-%s/%s %s/%s %s",
                    getFormat().format(new Date()), Process.myPid(), Process.myUid(), getProcessName(), levelToStr(level), tag, msg);
            writer.println(log);
            if (tr != null) {
                tr.printStackTrace(writer);
                writer.println();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Throwable e) {
                }
            }
        }
    }

    private static String getProcessName() {
        return "?";
    }

    private static void println(final int level, final String tag, final String format, final Object[] args, final Throwable tr) {
        logToFile(level, tag, format, args, tr);
        String message;
        if (args != null && args.length > 0) {
            message = String.format(format, args);
        } else {
            message = format;
        }

        if (tr != null) {
            message += android.util.Log.getStackTraceString(tr);
        }
        android.util.Log.println(level, tag, message);
    }

    public static void v(String tag, String format, Object... args) {
        v(tag, format, null, args);
    }

    public static void v(String tag, String format, Throwable tr, Object... args) {
        if (!isLoggable(VERBOSE)) {
            return;
        }

        println(VERBOSE, tag, format, args, tr);
    }


    public static void d(String tag, String format, Object... args) {
        d(tag, format, null, args);
    }

    public static void d(String tag, String format, Throwable tr, Object... args) {
        if (!isLoggable(DEBUG)) {
            return;
        }
        println(DEBUG, tag, format, args, tr);
    }

    public static void i(String tag, String format, Object... args) {
        i(tag, format, null, args);
    }

    public static void i(String tag, String format, Throwable tr, Object... args) {
        if (!isLoggable(INFO)) {
            return;
        }
        println(INFO, tag, format, args, tr);
    }

    public static void w(String tag, String format, Object... args) {
        w(tag, format, null, args);
    }

    public static void w(String tag, String format, Throwable tr, Object... args) {
        if (!isLoggable(WARN)) {
            return;
        }
        println(WARN, tag, format, args, tr);
    }

    public static void w(String tag, Throwable tr) {
        w(tag, "Log.warn", tr);
    }

    public static void e(String tag, String format, Object... args) {
        e(tag, format, null, args);
    }

    public static void e(String tag, String format, Throwable tr, Object... args) {
        if (!isLoggable(ERROR)) {
            return;
        }
        println(ERROR, tag, format, args, tr);
    }

    public static void wtf(String tag, String format, Object... args) {
        wtf(tag, format, null, args);
    }

    public static void wtf(String tag, Throwable tr) {
        wtf(tag, "wtf", tr);
    }

    public static void wtf(String tag, String format, Throwable tr, Object... args) {
        if (!isLoggable()) {
            return;
        }
        println(ASSERT, tag, format, args, tr);
    }
}
