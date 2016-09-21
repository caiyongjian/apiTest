package com.qfix.qchildconn.host;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.qfix.qchildconn.common.Common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cyj on 16-9-13.
 */
public class ChildProcessLauncher {
    private interface ChildConnectionAllocator {
        ChildProcessConnection alloc(
                Context context,
                int presetIndex,
                ChildProcessConnection.DeathCallback deathCallback,
                boolean alwaysInForeground);

        void free(ChildProcessConnection connection);
    }

    public static class NormalChildConnectionAllocator implements ChildConnectionAllocator {
        private final ChildProcessConnection[] mChildProcessConnections;
        private final Object mAllocLock = new Object();

        public NormalChildConnectionAllocator(int childServicesCount) {
            mChildProcessConnections = new ChildProcessConnectionImpl[childServicesCount];
        }

        @Override
        public ChildProcessConnection alloc(
                Context context,
                int presetIndex,
                ChildProcessConnection.DeathCallback deathCallback,
                boolean alwaysInForeground) {
            synchronized (mAllocLock) {
                if (presetIndex < 0 || presetIndex > mChildProcessConnections.length - 1) {
                    return null;
                }

                if (mChildProcessConnections[presetIndex] != null) {
                    return null;
                }

                mChildProcessConnections[presetIndex] = new ChildProcessConnectionImpl(
                        context, presetIndex, alwaysInForeground, deathCallback);
                return mChildProcessConnections[presetIndex];
            }
        }

        @Override
        public void free(ChildProcessConnection connection) {
            synchronized (mAllocLock) {
                int index = connection.getServiceIndex();
                if (mChildProcessConnections[index] != connection) {

                } else {
                    mChildProcessConnections[index] = null;
                }
            }
        }
    }

    private static ChildConnectionAllocator sNormalChildConnectionAllocator = null;

    private static ChildConnectionAllocator getConnectionAllocator() {
        synchronized (ChildProcessLauncher.class) {
            if (sNormalChildConnectionAllocator == null) {
                sNormalChildConnectionAllocator = new NormalChildConnectionAllocator(
                        Common.TOTAL_SERVICE_NUMBER);
            }
            return sNormalChildConnectionAllocator;
        }
    }

    private static ChildProcessConnection allocateConnection(
            Context context, int index, boolean alwaysInForeground) {
        ChildProcessConnection.DeathCallback deathCallback =
                new ChildProcessConnection.DeathCallback() {
                    @Override
                    public void onChildProcessDied(ChildProcessConnection connection) {
                        if (connection.getPid() != 0) {
                            stop(connection.getPid());
                        } else {
                            freeConnection(connection);
                        }
                    }
                };
        return getConnectionAllocator().alloc(context, index, deathCallback, alwaysInForeground);
    }

    private static void stop(int pid) {
        ChildProcessConnection connection = sServiceMap.remove(pid);
        if (connection == null) {
            return;
        }
        connection.stop();
        freeConnection(connection);
    }

    private static void freeConnection(final ChildProcessConnection connection) {
        final ChildProcessConnection conn = connection;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                getConnectionAllocator().free(conn);
            }
        }, 5);
    }

    private static Map<Integer, ChildProcessConnection> sServiceMap =
            new ConcurrentHashMap<Integer, ChildProcessConnection>();

    private static ChildProcessConnection allocateBoundConnection(
            Context context, String[] commandLne, int index, boolean alwaysInForeground) {
        ChildProcessConnection connection = allocateConnection(context, index, alwaysInForeground);
        if (connection != null) {
            connection.start(commandLne);
        }
        return connection;
    }

    private static void start(Context context, final String[] commandLine, int index) {

    }
}
