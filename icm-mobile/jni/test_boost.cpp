#include <org_umit_icm_mobile_Main.h>
#include <boost/date_time/posix_time/posix_time.hpp>

JNIEXPORT jstring JNICALL Java_org_umit_icm_mobile_Main_getDateTime
  (JNIEnv * env, jobject jObj){
	char string[30];
	using namespace boost::posix_time;
	ptime now = microsec_clock::local_time();
	sprintf(string,"%d",now.time_of_day().hours());
	return env->NewStringUTF(string);
}

