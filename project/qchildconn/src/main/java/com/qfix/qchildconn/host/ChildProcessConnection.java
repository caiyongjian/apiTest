package com.qfix.qchildconn.host;

import android.os.RemoteException;

import com.qfix.qchildconn.IChildProcessCallback;
import com.qfix.qchildconn.IChildProcessService;

/**
 * Created by cyj on 16-9-11.
 */
public interface ChildProcessConnection {

    /**
     * 通知链接断开.
     * 1.未链接成功
     * 2.链接成功但还未交互数据
     * 3.正常使用后断开
     * */
    interface DeathCallback {
        void onChildProcessDied(ChildProcessConnection connection);
    }

    /**
     * 通知链接建立
     * */
    interface ConnectionCallback {
        /**
         * @param pid 子进程PID
         * */
        void onConnected(int pid);
    }

    int getServiceIndex();

    IChildProcessService getService();

    /**
     * @return 子进程PID, 0则表示还未建立链接
     * */
    int getPid();

    void start(String[] commandLine);

    void setupConnection(
            String[] commandLine,
            IChildProcessCallback processCallback,
            ConnectionCallback connectionCallback);

    void stop();

    void addStrongBinding();

    void removeStrongBinding();

    boolean isStrongBindingBound();

    boolean isInitialBindingBound();

    boolean isConnected();

    boolean crashServiceForTesting() throws RemoteException;
}
