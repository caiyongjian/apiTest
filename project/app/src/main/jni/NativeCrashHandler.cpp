//
// Created by caiyongjian on 16-7-15.
//

#include <stddef.h>
#include "com_qihoo_apitest_crash_NativeCrashHandler.h"

#include "client/linux/handler/exception_handler.h"

//ExceptionHandler* g_ExceptionHandler = NULL;

static bool dumpCallback(const google_breakpad::MinidumpDescriptor& descriptor,
                         void* context, bool succeeded) {
  printf("Dump path: %s\n", descriptor.path());
  return succeeded;
}

JNIEXPORT jint JNICALL Java_com_qihoo_apitest_crash_NativeCrashHandler_getVersion(JNIEnv *env, jobject) {
    return 10000;
}

JNIEXPORT jint JNICALL Java_com_qihoo_apitest_crash_NativeCrashHandler_invideCrash(JNIEnv *, jobject, jint input) {
    int in = input;
    return 100 / in;
}

JNIEXPORT void JNICALL
Java_com_qihoo_apitest_crash_NativeCrashHandler_nativeInit(JNIEnv *env, jobject instance) {
  google_breakpad::MinidumpDescriptor descriptor("/sdcard/dmp");
  google_breakpad::ExceptionHandler eh(descriptor, NULL, dumpCallback, NULL, true, -1);
//    g_ExceptionHandler = new ExceptionHandler();
//    g_ExceptionHandler->init();
}