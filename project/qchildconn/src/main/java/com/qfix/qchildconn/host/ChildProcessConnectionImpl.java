package com.qfix.qchildconn.host;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.RemoteException;

import com.qfix.qchildconn.child.ChildProcessService;
import com.qfix.qchildconn.IChildProcessCallback;
import com.qfix.qchildconn.IChildProcessService;
import com.qfix.qchildconn.common.Common;

/**
 * Created by cyj on 16-9-12.
 */
public class ChildProcessConnectionImpl implements ChildProcessConnection {

    private static class ConnectionParams {
        final String[] mCommandLine;
        final IChildProcessCallback mCallback;
        final Bundle mArgs;

        public ConnectionParams(String[] commandLine, IChildProcessCallback callback, Bundle args) {
            mCommandLine = commandLine;
            mCallback = callback;
            mArgs = args;
        }
    }

    private class ChildServiceConnection implements ServiceConnection {
        private boolean mBound = false;
        private final int mBindFlags;

        public ChildServiceConnection(int bindFlags) {
            mBindFlags = bindFlags;
        }

        boolean bind(String[] commandLine) {
            if (!mBound) {
                final Intent intent = createServiceBindIntent();
                if (commandLine != null) {
                    intent.putExtra(Common.EXTRA_COMMAND_LINE, commandLine);
                }
                mBound = mContext.bindService(intent, this, mBindFlags);
            }
            return mBound;
        }

        void unbind() {
            if (mBound) {
                mContext.unbindService(this);
                mBound = false;
            }
        }

        boolean isBound() {
            return mBound;
        }

        private Intent createServiceBindIntent() {
            Intent intent = new Intent();
            intent.setClassName(mContext, ChildProcessService.class.getName() + mServiceIndex);
            intent.setPackage(mContext.getPackageName());
            return intent;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            synchronized (mLock) {
                if (mServiceConnectComplete) {
                    return;
                }
                mServiceConnectComplete = true;
                mChildService = IChildProcessService.Stub.asInterface(service);

                if (mConnectionParams != null) {
                    doConnectionSetup();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (mServiceDisconnected) {
                return;
            }
            mServiceDisconnected = true;
            stop();
            mDeathCallback.onChildProcessDied(ChildProcessConnectionImpl.this);

            if (mConnectionCallback != null) {
                mConnectionCallback.onConnected(0);
            }
            mConnectionCallback = null;
        }
    }

    private final Object mLock = new Object();

    ConnectionParams mConnectionParams;
    Context mContext;
    int mServiceIndex = 0;
    boolean mAlwaysInForeground;
    DeathCallback mDeathCallback;
    ConnectionCallback mConnectionCallback;

    int mPid = 0;
    IChildProcessService mChildService;
    ChildServiceConnection mStrongBinding;
    ChildServiceConnection mInitialBinding;
    private boolean mServiceConnectComplete = false;
    private boolean mServiceDisconnected = false;
    private int mStrongBindingCount = 0;

    public ChildProcessConnectionImpl(
            Context context,
            int serviceIndex,
            boolean alwaysInForeground,
            DeathCallback deathCallback) {
        mContext = context;
        mServiceIndex = serviceIndex;
        mAlwaysInForeground = alwaysInForeground;
        mDeathCallback = deathCallback;

        int initialFlags = Context.BIND_AUTO_CREATE;
        if (alwaysInForeground) initialFlags |= Context.BIND_AUTO_CREATE;

        mInitialBinding = new ChildServiceConnection(initialFlags);
        mStrongBinding = new ChildServiceConnection(
                Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT);
    }

    private void doConnectionSetup() {
        Bundle bundle = new Bundle();
        bundle.putStringArray(Common.EXTRA_COMMAND_LINE, mConnectionParams.mCommandLine);
        bundle.putBundle(Common.EXTRA_BUNDLE, mConnectionParams.mArgs);

        try {
            mPid = mChildService.setupConnection(bundle, mConnectionParams.mCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mConnectionParams = null;

        if (mConnectionCallback != null) {
            mConnectionCallback.onConnected(mPid);
            mConnectionCallback = null;
        }
    }

    @Override
    public int getServiceIndex() {
        return mServiceIndex;
    }

    @Override
    public IChildProcessService getService() {
        synchronized (mLock) {
            return mChildService;
        }
    }

    @Override
    public int getPid() {
        synchronized (mLock) {
            return mPid;
        }
    }

    @Override
    public void start(String[] commandLine) {
        synchronized (mLock) {
            if (!mInitialBinding.bind(commandLine)) {
                mDeathCallback.onChildProcessDied(ChildProcessConnectionImpl.this);
            }
        }
    }

    @Override
    public void setupConnection(
            String[] commandLine,
            IChildProcessCallback processCallback,
            ConnectionCallback connectionCallback) {
        synchronized (mLock) {
            mConnectionCallback = connectionCallback;
            mConnectionParams = new ConnectionParams(
                    commandLine, processCallback, null);
            if (mServiceConnectComplete) {
                doConnectionSetup();
            }
        }
    }

    @Override
    public void stop() {
        mInitialBinding.unbind();
        mStrongBinding.unbind();
        mStrongBindingCount = 0;
        if (mChildService != null) {
            mChildService = null;
        }
        mConnectionParams = null;
    }

    @Override
    public void addStrongBinding() {
        synchronized (mLock) {
            if (mChildService == null) {
                return;
            }

            if (mStrongBindingCount == 0) {
                mStrongBinding.bind(null);
            }
            mStrongBindingCount++;
        }
    }

    @Override
    public void removeStrongBinding() {
        synchronized (mLock) {
            if (mChildService == null) {
                return;
            }

            mStrongBindingCount--;
            if (mStrongBindingCount == 0) {
                mStrongBinding.unbind();
            }
        }
    }

    @Override
    public boolean isStrongBindingBound() {
        synchronized (mLock) {
            return mStrongBinding.isBound();
        }
    }

    @Override
    public boolean isInitialBindingBound() {
        synchronized (mLock) {
            return mInitialBinding.isBound();
        }
    }

    @Override
    public boolean isConnected() {
        return mChildService != null;
    }

    @Override
    public boolean crashServiceForTesting() throws RemoteException {
        try {
            mChildService.crashForTest();
        } catch (DeadObjectException e) {
            return true;
        }
        return false;
    }
}
