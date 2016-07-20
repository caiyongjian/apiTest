LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := NativeCrashHandler
LOCAL_SRC_FILES := NativeCrashHandler.cpp
TARGET_PLATFORM := android-19
TARGET_ARCH     := arm
TARGET_ARCH_ABI := armeabi-v7a
LOCAL_LDLIBS    :=  -lc -lm -llog -landroid
LOCAL_STATIC_LIBRARIES += breakpad_client

include $(BUILD_SHARED_LIBRARY)

include $(LOCAL_PATH)/../../../../breakpad/android/google_breakpad/Android.mk