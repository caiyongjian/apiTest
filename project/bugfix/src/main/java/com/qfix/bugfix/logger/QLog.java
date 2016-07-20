package com.qfix.bugfix.logger;

import android.util.Log;

import java.util.ArrayList;

/**
 * @data:           16-7-20
 * @author:         caiyongjian
 * @Description:    Simple log helper.
 *
 * Just like {@link android.util.Log} but supply two log ways: normal android log & file log.
 *
 * 1.It delay String format if you do not enable log. see {@link android.util.Log}.
 * 2.You can create log dir to enable log. This help debug on release version.
 */
public class QLog {

    /**
     * @Description:    Log level. All enum value same as {@link android.util.Log}
     */
    public enum LogLevel {
        VERBOSE(Log.VERBOSE),
        DEBUG(Log.DEBUG),
        INFO(Log.INFO),
        WARN(Log.WARN),
        ERROR(Log.ERROR),
        ASSERT(Log.ASSERT);

        private final int value;
        LogLevel(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String toString() {
            return levelToStr(this);
        }

        public static String levelToStr(LogLevel level) {
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
    }

    /**
     * @Description:    All printers implements this. You can add you owner Printer.
     */
    
    public interface QLogPrinter {
        void print(LogLevel level, String tag, String format, Object[] args, Throwable tr);
    }

//    private static final String TAG = "QLog";

    private static boolean sDebug = false;

    private static ArrayList<QLogPrinter> sQLogPrinters = null;

    static {
        sDebug = Constants.isLogDirExist();
        if (sDebug) {
            getQLogPrinters().add(new AndroidLogPrinter());
            getQLogPrinters().add(new QLogFilePrinter(sDebug));
        }
    }

    private static synchronized ArrayList<QLogPrinter> getQLogPrinters() {
        if (sQLogPrinters == null) {
            sQLogPrinters = new ArrayList<QLogPrinter>(1);
        }
        return sQLogPrinters;
    }

    public static boolean isDebug() {
        return sDebug;
    }

    public static boolean isLoggable(LogLevel logLevel) {
        return isDebug();
    }

    private static void println(final LogLevel level, final String tag, final String format, final Object[] args, final Throwable tr) {
        if (!isLoggable(level) || sQLogPrinters == null) {
            return;
        }

        for (QLogPrinter logPrinter : sQLogPrinters) {
            logPrinter.print(level, tag, format, args, tr);
        }
    }

    /**
     * verbose part.
     */
    public static void v(String tag, String format, Object... args) {
        v(tag, format, null, args);
    }

    public static void v(String tag, String format, Throwable tr, Object... args) {
        println(LogLevel.VERBOSE, tag, format, args, tr);
    }


    /**
     * debug part.
     */
    public static void d(String tag, String format, Object... args) {
        d(tag, format, null, args);
    }

    public static void d(String tag, String format, Throwable tr, Object... args) {
        println(LogLevel.DEBUG, tag, format, args, tr);
    }

    /**
     * info part.
     */
    public static void i(String tag, String format, Object... args) {
        i(tag, format, null, args);
    }

    public static void i(String tag, String format, Throwable tr, Object... args) {
        println(LogLevel.INFO, tag, format, args, tr);
    }

    /**
     * warn part.
     */
    public static void w(String tag, String format, Object... args) {
        w(tag, format, null, args);
    }

    public static void w(String tag, String format, Throwable tr, Object... args) {
        println(LogLevel.WARN, tag, format, args, tr);
    }

    public static void w(String tag, Throwable tr) {
        w(tag, "Log.warn", tr);
    }

    /**
     * error part.
     */
    public static void e(String tag, String format, Object... args) {
        e(tag, format, null, args);
    }

    public static void e(String tag, String format, Throwable tr, Object... args) {
        println(LogLevel.ERROR, tag, format, args, tr);
    }

    /**
     * assert part.
     */
    public static void wtf(String tag, String format, Object... args) {
        wtf(tag, format, null, args);
    }

    public static void wtf(String tag, Throwable tr) {
        wtf(tag, "wtf", tr);
    }

    public static void wtf(String tag, String format, Throwable tr, Object... args) {
        println(LogLevel.ASSERT, tag, format, args, tr);
    }
}
