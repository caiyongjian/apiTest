package com.qfix.qchildconn.child;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

import com.qfix.qchildconn.IChildProcessCallback;
import com.qfix.qchildconn.IChildProcessService;
import com.qfix.qchildconn.common.Common;

public class ChildProcessService extends Service {
    public ChildProcessService() {
    }

    IChildProcessCallback mChildProcessCallback = null;
    Bundle mArgs = null;
    private String[] mCommandLineParams = null;

    IChildProcessService.Stub mChildProcessService = new IChildProcessService.Stub() {

        @Override
        public int setupConnection(Bundle args, IChildProcessCallback callback) throws RemoteException {
            mChildProcessCallback = callback;
            mArgs = args;
            if (mCommandLineParams == null) {
                mCommandLineParams = args.getStringArray(Common.EXTRA_COMMAND_LINE);
            }
            return Process.myPid();
        }

        @Override
        public void crashForTest() throws RemoteException {
            Process.killProcess(Process.myPid());
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mChildProcessService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
