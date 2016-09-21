// IChildProcessService.aidl
package com.qfix.qchildconn;

import com.qfix.qchildconn.IChildProcessCallback;

import android.os.Bundle;

interface IChildProcessService {
    /**
    * return child process PID.
    * */
    int setupConnection(in Bundle args, IChildProcessCallback callback);

    void crashForTest();
}
