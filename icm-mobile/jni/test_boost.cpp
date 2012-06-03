#include <org_umit_icm_mobile_Main.h>

#include <iostream>
#include <stdlib.h>
#include <stdio.h>
// include libevent's header
#include <event.h>
// include libcage's header
#include <cage.hpp>
#include <android/log.h>
#include <boost/thread/thread.hpp>


libcage::cage *cage;

void printPeers(){
	cage->print_state();
}

void openCage_firstnode(char * portnum){
		event_init();
		int port = atoi(portnum);
		cage = new libcage::cage;
		std::cout<<"\nAbout to start cage on port "<<port<<"\n";
		__android_log_print(ANDROID_LOG_INFO, "openCage_firstnode", "About to start cage on port");
		try
		{
			if(!cage->open(PF_INET,port))
			{
				std::cout<<"Could not open port for P2P routing communication. Trying next port\n";
				/*itoa(port+1,portnum,10);
				openCage_firstnode(portnum);*/
				std::cout<<"\nError : Port is being used already\n";
				__android_log_print(ANDROID_LOG_INFO, "openCage_firstnode", "Error : Port is being used already");
				return;
			}
		}catch(std::exception &e){
			std::cout<<"Port cannot be opened due to an exception. Message : "<<e.what()<<"\n";
		}
		std::cout<<"Cage instance listening on port "<<port;
		__android_log_print(ANDROID_LOG_INFO, "openCage_firstnode", "Cage instance listening on port %s", port);
		cage->set_global();
		std::cout<<"\nLog : Out of createCage\n";
		event_dispatch();
}

void
join_callback(bool result)
{
        if (result)
        {
                std::cout << "join: succeeded" << std::endl;
                __android_log_print(ANDROID_LOG_INFO, "join_callback", "join: succeeded");
        }

        else
        {
                std::cout << "join: failed" << std::endl;
                __android_log_print(ANDROID_LOG_INFO, "join_callback", "join: failed");
        }

        /*std::vector<nodeinfo> peerlist = cage->getRoutingTable();
        __android_log_print(ANDROID_LOG_INFO, "join_callback","Peerlist :");
        int i;
                for(i=0;i<peerlist.size();i++){
                	__android_log_print(ANDROID_LOG_INFO, "join_callback",(char *)peerlist[i].id);
        }
        if(peerlist.size()<1){
        	__android_log_print(ANDROID_LOG_INFO, "join_callback","The peerlist is empty since its the first node");
        }*/


        cage->print_state();
}


JNIEXPORT jstring JNICALL Java_org_umit_icm_mobile_Main_getDateTime
  (JNIEnv * env, jobject jObj){
  		std::cout<<"\nLog : Into createCage\n";
		boost::thread workerThread(openCage_firstnode,"10001");
	    const char string[50];
	    sprintf(string,"%s","Returned from Libcage code");
        return env->NewStringUTF(string);
}

/*	char string[30];
	using namespace boost::posix_time;
	ptime now = microsec_clock::local_time();
	sprintf(string,"%d",now.time_of_day().hours());
	return env->NewStringUTF(string);*/
