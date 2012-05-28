#include <org_umit_icm_mobile_Main.h>

JNIEXPORT jstring JNICALL Java_org_umit_icm_mobile_Main_getDateTime
  (JNIEnv * env, jobject jObj){
	return env->NewStringUTF("NDK Works");
}

