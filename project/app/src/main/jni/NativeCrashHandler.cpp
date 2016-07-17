//
// Created by caiyongjian on 16-7-15.
//

#include <stddef.h>
#include "com_qihoo_apitest_crash_NativeCrashHandler.h"
#include "ExceptionHandler.h"

ExceptionHandler* g_ExceptionHandler = NULL;

JNIEXPORT jint JNICALL Java_com_qihoo_apitest_crash_NativeCrashHandler_getVersion(JNIEnv *env, jobject) {
    return 10000;
}

JNIEXPORT jint JNICALL Java_com_qihoo_apitest_crash_NativeCrashHandler_invideCrash(JNIEnv *, jobject, jint input) {
    int in = input;
    return 100 / in;
}

JNIEXPORT void JNICALL
Java_com_qihoo_apitest_crash_NativeCrashHandler_nativeInit(JNIEnv *env, jobject instance) {
    g_ExceptionHandler = new ExceptionHandler();
    g_ExceptionHandler->init();
}