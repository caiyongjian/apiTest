package com.qfix.bugfix.logger;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class QLogFilePrinter implements QLog.QLogPrinter {

    private static SimpleDateFormat sMsgHeaderFormat = null;
    private static SimpleDateFormat sLogFileNameFormat = null;

    private HandlerThread sHandlerThread;
    private Handler sHandler;
    private boolean mFileLog = false;
    private File mLogFile = null;

    public QLogFilePrinter(boolean fileLog) {
        mFileLog = fileLog;
    }

    private boolean isFileLog() {
        return mFileLog;
    }

    @Override
    public void print(final QLog.LogLevel level, final String tag, final String format, final Object[] args, final Throwable tr) {
        if (!isFileLog()) {
            return;
        }

        final int threadId = Process.myTid();
        getLogHandler().post(new Runnable() {
            @Override
            public void run() {
                logToFileInner(level, threadId, tag, format, args, tr);
            }
        });
    }

    private Handler getLogHandler() {
        if (sHandler == null) {
            synchronized (QLogFilePrinter.this) {
                if (sHandler == null) {
                    sHandlerThread = new HandlerThread("QLogFilePrinter");
                    sHandlerThread.start();
                    sHandler = new Handler(sHandlerThread.getLooper());
                }
            }
        }
        return sHandler;
    }

    private static SimpleDateFormat getMsgHeaderFormat() {
        if (sMsgHeaderFormat == null) {
            sMsgHeaderFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        }
        return sMsgHeaderFormat;
    }

    private static SimpleDateFormat getLogFileNameFormat() {
        if (sLogFileNameFormat == null) {
            sLogFileNameFormat = new SimpleDateFormat("yyyyMMdd");
        }
        return sLogFileNameFormat;
    }

    private File getLogFile() {
        if (mLogFile != null) return mLogFile;
        File file = new File(Constants.getLogDir(),
                String.format("Log_%s_%s.log",
                        getLogFileNameFormat().format(new Date()),
                        Process.myPid()));
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return file;
    }

    private static String getProcessName() {
        return "?";
    }

    private void logToFileInner(QLog.LogLevel level, int threadId, String tag, String format, Object[] args, Throwable tr) {
        PrintWriter writer = null;
        try {
            File logFile = getLogFile();
            if (logFile.length() > Constants.MAX_LOG_FILE) {
                logFile.delete();
            }

            writer = new PrintWriter(new FileWriter(logFile, true));
            String msg = String.format(format, args);
            String log = String.format("%s %s-%s/%s %s/%s %s",
                    getMsgHeaderFormat().format(new Date()),
                    Process.myPid(),
                    threadId,
                    getProcessName(),
                    level.toString(),
                    tag,
                    msg);
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
}
