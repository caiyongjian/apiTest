LOCAL_PATH := $(call my-dir)
TOP_LOCAL_PATH := $(LOCAL_PATH)

include $(CLEAR_VARS)

LOCAL_MODULE    := NativeCrashHandler
LOCAL_SRC_FILES := NativeCrashHandler.cpp \
                   ExceptionHandler.cpp

#LOCAL_CPP_FEATURES := rtti exceptions
LOCAL_LDLIBS    := -lm -llog -landroid -lc

include $(BUILD_SHARED_LIBRARY)
