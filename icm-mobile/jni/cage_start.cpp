#include <org_umit_icm_mobile_Main.h>

/*
 * test_boost.cpp
 *
 *  Created on: May 13, 2012
 *      Author: narendran
 */

#include <jni.h>
#include <iostream>
#include <event.h>
#include <stdio.h>
#include <stdlib.h>
#include <exception>
#include <string>
#include <vector>
#include <libcage/src/cage.hpp>
#include <boost/include/boost/thread.hpp>
#include <android/log.h>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG , "libcage_logs", __VA_ARGS__)

libcage::cage *cage;

void printPeers(){
	cage->print_state();
}

void openCage_firstnode(char * portnum){
		event_init();
		int port = atoi(portnum);
		cage = new libcage::cage;
		LOGD("About to start cage on port %d",port);
		try
		{
			if(!cage->open(PF_INET,port))
			{
				LOGD("Could not open port for P2P routing communication");
				sprintf(portnum,"%d",port+1);
				openCage_firstnode(portnum);
				return;
			}
		}catch(std::exception &e){
			LOGD("Port cannot be opened due to an exception. Message : %s",e.what());
		}
		LOGD("Cage instance listening on port %d",port);
		cage->set_global();
		LOGD("Log : Out of createCage");
		//event_dispatch();
}

void createCage_firstnode(char * portnum){
	std::cout<<"\nLog : Into createCage\n";
	boost::thread workerThread(openCage_firstnode,portnum);
	return;
}

void join_callback(bool result){
	if(result)
		std::cout<<"Join Success";
	else
		std::cout<<"join Failure";
	printPeers();
}

void openCage_joinnode(char * portnum,char * host, char * destination_port){
		event_init();
		int port = atoi(portnum);
		cage = new libcage::cage;
		std::cout<<"\nAbout to start cage on port "<<port<<"\n";
		// Open a port for other nodes to communicate
		/*
		 * TODO : Get IP, port number and if it should be stored in DTUN
		 * for now make it default to 10000. Later get the parameters from ICM agent
		 */
		if(!cage->open(PF_INET,port))
		{
			std::cout<<"Could not open port for P2P routing communication\n";
			sprintf(portnum,"%d",port+1);
			openCage_joinnode(portnum,host,destination_port);
		}
		std::cout<<"Cage instance listening on port "<<port;
		cage->set_global();
		std::cout<<"\nLog : Enter Join network";
		int dest_port = atoi(destination_port);
		cage->join(host,dest_port,&join_callback);
		event_dispatch();
}

void createCage_joinnode(char * portnum,char * host, char * destination_port){
	std::cout<<"\nLog : Into createCage\n";
	boost::thread workerThread(openCage_joinnode,portnum,host,destination_port);
	std::cout<<"\nLog : OUt of createCage\n";

}

int getPort()
{
	return cage->get_port();
}

std::vector<std::string>  getPeers()
{
	std::cout<<"Log : Entered into getPeers function";
	printf("Size of peer list in libcage from getPeerlist function is %d",cage->get_Peerlist().size());
	return cage->get_Peerlist();
}


#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jstring JNICALL Java_org_umit_icm_mobile_Main_startLibcage
  (JNIEnv * env, jobject jObj){
  		std::cout<<"\nLog : Into createCage\n";
		openCage_firstnode("20000");
		boost::this_thread::sleep(boost::posix_time::seconds(2));
		char * message;
	    message = malloc(100);
	    using namespace boost::posix_time;
	    ptime now = microsec_clock::local_time();
	    //sprintf(message,"%d",now.time_of_day().hours());
	    sprintf(message,"%d",getPort());
        return env->NewStringUTF(message);
}
#ifdef __cplusplus
}
#endif

#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT void JNICALL Java_org_umit_icm_mobile_Main_printRoutingTab
  (JNIEnv * env, jobject jObj){
  		printPeers();
}
#ifdef __cplusplus
}
#endif

/*	char string[30];
	using namespace boost::posix_time;
	ptime now = microsec_clock::local_time();
	sprintf(string,"%d",now.time_of_day().hours());
	return env->NewStringUTF(string);*/
