package com.qfix.bugfix.logger;

/**
 * Created by caiyongjian on 16-7-20.
 */
public class AndroidLogPrinter implements QLog.QLogPrinter {

    @SuppressWarnings("WrongConstant")
    @Override
    public void print(QLog.LogLevel level, String tag, String format, Object[] args, Throwable tr) {
        String message;
        if (args != null && args.length > 0) {
            message = String.format(format, args);
        } else {
            message = format;
        }

        if (tr != null) {
            message += android.util.Log.getStackTraceString(tr);
        }
        android.util.Log.println(level.getValue(), tag, message);
    }

}
