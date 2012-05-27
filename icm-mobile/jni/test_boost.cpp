#include <org_umit_icm_mobile_Main.h>
#include <stdio.h>
#include <boost/date_time/posix_time/posix_time.hpp>

JNIEXPORT jstring JNICALL Java_org_umit_icm_mobile_Main_getBoostTime
  (JNIEnv * env, jobject jObj){
	using namespace boost::posix_time;
	ptime now = microsec_clock::local_time();
	return env->NewStringUTF(now.time_of_day().hours());
}
