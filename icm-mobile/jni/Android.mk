LOCAL_PATH := $(call my-dir)
MY_PATH := $(LOCAL_PATH)
include $(call all-subdir-makefiles)

include $(CLEAR_VARS)

LOCAL_PATH := $(MY_PATH)

LOCAL_CFLAGS += -I$(LOCAL_PATH)/boost/include/
LOCAL_LDLIBS += -L$(LOCAL_PATH)/boost/lib/ -lboost_date_time -lboost_program_options -lboost_regex

LOCAL_CPPFLAGS += -fexceptions
LOCAL_CPPFLAGS += -frtti

LOCAL_LDLIBS := -llog -ldl
  
LOCAL_MODULE    := boost-lib  
LOCAL_SRC_FILES := test_boost.cpp 
  
include $(BUILD_SHARED_LIBRARY)  