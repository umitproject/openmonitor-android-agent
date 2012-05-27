LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := boost-library
LOCAL_SRC_FILES := test_boost.cpp
LOCAL_STATIC_LIBRARIES := boost_thread boost_iostreams boost_date_time

include $(BUILD_SHARED_LIBRARY)

#$(call import-module,boost_thread)
#$(call import-module,boost_program_options)


