package org.umit.icm.mobile.connectivity;

public class ServiceDetails {
	
	Service service;
	String serviceName;
	long statusCode;
	long port;
	double responseTime;
	double bandwidth;
	
	
	public ServiceDetails(Service service){
		this.service=service;
		this.serviceName=service.getName();
	}

}
