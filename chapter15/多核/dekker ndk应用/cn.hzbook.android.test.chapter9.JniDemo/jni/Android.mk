LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := mylib
LOCAL_SRC_FILES := mylib.c
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
