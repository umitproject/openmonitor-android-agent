LOCAL_PATH := $(call my-dir)
APP_STL := stlport_static
include $(CLEAR_VARS)
LOCAL_CFLAGS += --sysroot=$SYSROOT -fpermissive -I$(LOCAL_PATH)/boost/include/ -I$(LOCAL_PATH)/libcage/src -I$(LOCAL_PATH)/libcage/src/openssl -I$(LOCAL_PATH)/libcage/src/event -I$(LOCAL_PATH)/libcage/src/event/event2 -I/usr/include/python2.7
LOCAL_LDLIBS += -L$(LOCAL_PATH)/boost/lib/ -lboost_date_time -lboost_program_options -lboost_regex -lboost_filesystem -lboost_system -lboost_thread -lboost_signals -llog -L$(LOCAL_PATH)/libcage/lib -lcrypto -levent 
LOCAL_CPPFLAGS := --sysroot=$SYSROOT -fexceptions -frtti -fpermissive -I/usr/include/python2.7
LOCAL_MODULE    := cage-lib
LOCAL_SRC_FILES := \
	libcage/src/advertise.cpp \
	libcage/src/cage.cpp \
	libcage/src/cagetime.cpp \
	libcage/src/cagetypes.cpp \
	libcage/src/dgram.cpp \
	libcage/src/dht.cpp \
	libcage/src/dtun.cpp \
	libcage/src/natdetector.cpp \
	libcage/src/packetbuf.cpp \
	libcage/src/peers.cpp \
	libcage/src/proxy.cpp \
	libcage/src/rdp.cpp \
	libcage/src/rttable.cpp \
	libcage/src/timer.cpp \
	libcage/src/udphandler.cpp \
	cage_start.cpp
include $(BUILD_SHARED_LIBRARY)  