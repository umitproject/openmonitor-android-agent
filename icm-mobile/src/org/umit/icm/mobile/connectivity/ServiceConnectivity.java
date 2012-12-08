/**
 * Copyright (C) 2011 Adriano Monteiro Marques
 *
 * Author:  Zubair Nabi <zn.zubairnabi@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package org.umit.icm.mobile.connectivity;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.http.HttpException;
import org.umit.icm.mobile.aggregator.AggregatorRetrieve;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.Event;
import org.umit.icm.mobile.proto.MessageProtos.ICMReport;
import org.umit.icm.mobile.proto.MessageProtos.Location;
import org.umit.icm.mobile.proto.MessageProtos.SendServiceReport;
import org.umit.icm.mobile.proto.MessageProtos.ServiceReport;
import org.umit.icm.mobile.proto.MessageProtos.ServiceReportDetail;
import org.umit.icm.mobile.proto.MessageProtos.Trace;
import org.umit.icm.mobile.proto.MessageProtos.TraceRoute;
import org.umit.icm.mobile.utils.SDCardReadWrite;

import android.util.Log;

/**
 * This is the ServiceConnectivity class which extends {@link AbstractConnectivity}.
 */

public class ServiceConnectivity extends AbstractConnectivity{
	
	/**
	 * scan method which overrides the {@link AbstractConnectivity#scan}. 
	 * Calls the scan method of each service. 	
	 * 	  
	 *
	 */
	@Override()
	public void scan() throws IOException, HttpException, MessagingException {
		
		if(Constants.DEBUG_MODE)
			System.out.println("Inside Services scan() ---------------------------");
		
		List<Class<? extends AbstractServiceTest>> servicesList 
		= new ArrayList<Class<? extends AbstractServiceTest>>();
		
		servicesList.add(ServiceHTTP.class);
		servicesList.add(ServiceHTTPS.class);
		servicesList.add(ServiceFTP.class);
		servicesList.add(ServicePOP3.class);
		servicesList.add(ServiceIMAP.class);
		servicesList.add(ServiceGtalk.class);
		servicesList.add(ServiceMSN.class);
		
		Iterator<Class<? extends AbstractServiceTest>> iterator = servicesList.iterator();
		while(iterator.hasNext()) {
			connect(iterator.next());	
		}
	};
		 
	/**
	 * Returns a {@link ServiceReport}. Uses the passed parameters to generate
	 * {@link ICMReport}, {@link ServiceReportDetail} and
	 * {@link ServiceReport} messages. 
	 * 
	 *	 
	                          
	@param  service  An object of the type {@link Service}
	 *  	                          	
	
	@param  serviceContent  An object of the type {@link String}
	 *  	                          	
	
	@param  bytes  An object of the type byte[]
	 *  	                          		              
	            pending list of checkTests to my hardcoded list.

	@return      ServiceReport
	 * @throws NoSuchAlgorithmException 
	 */	
	public ServiceReport clean(Service service, String serviceContent
			, byte[] bytes) 
	throws IOException, RuntimeException, NoSuchAlgorithmException {
		int statusCode = 0;
		if(serviceContent.equals("blocked"))
			statusCode = 1;
		if((bytes == null) && serviceContent.equals("blocked"))
			statusCode = 1;
		else if ((bytes != null) && serviceContent.equals("blocked"))
			statusCode = 1;
		ServiceReportDetail serviceReportDetail = ServiceReportDetail.newBuilder()
		.setServiceName(service.getName())
		.setStatusCode(statusCode)
		.setPort(service.getPort())
		.build();
		
		String ip = "255.255.255.0";
		Trace trace = Trace.newBuilder()
		.setHop(1)
		.setIp(ip) //TODO: fix
		.addPacketsTiming(1)
		.build();
		
		TraceRoute traceRoute = TraceRoute.newBuilder()
		.setTarget(ip) //TODO: fix
		.setHops(1)
		.setPacketSize(1)
		.addTraces(trace)
		.build();
		
		List<String> listNodes = new ArrayList<String>();
		
		Calendar calendar = Calendar.getInstance();
		listNodes.add(Globals.runtimeParameters.getAgentID());
		
		long timeUTC = (calendar.getTimeInMillis()/1000);
		
		ICMReport icmReport = ICMReport.newBuilder()
		.setAgentID(Globals.runtimeParameters.getAgentID())
		.setTestID(service.getTestID())
		.setTimeZone(Calendar.ZONE_OFFSET)
		.setTimeUTC(timeUTC)
		.setTraceroute(traceRoute)
		.addAllPassedNode(listNodes)
		.build();				
				
		ServiceReport serviceReport = ServiceReport.newBuilder()
		.setReport(serviceReportDetail)		
		.setHeader(icmReport)		
		.build();
		
		checkStatus(serviceReport);
		return serviceReport;
	}
	
	void sendReport(Service service, String response, byte[] responseBytes) {
		
		try {
			ServiceReport serviceReport 
			= (ServiceReport) clean(service, response, responseBytes);
			
			SDCardReadWrite.writeServiceReport(Constants.SERVICES_DIR, serviceReport);						
			
			if(Constants.DEBUG_MODE) {
				Log.w("######Code", Integer.toString(serviceReport.getReport().getStatusCode()));
				Log.w("######name", serviceReport.getReport().getServiceName());
				Log.w("######port", Integer.toString(service.getPort()));
			}

			SendServiceReport sendServiceReport = SendServiceReport.newBuilder()
			.setReport(serviceReport)
			.build();
			if(Globals.aggregatorCommunication != false) {
				if(Constants.DEBUG_MODE) {
					System.out.println("Sending " + service.getName() + " SERVICE REPORT \n");
					System.out.println("Sending Service Report : \n" + sendServiceReport.toString());
				}
				
				AggregatorRetrieve.sendServiceReport(sendServiceReport);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	void connect(Class<? extends AbstractServiceTest> classObj) {
		AbstractServiceTest service;
		try {
			service = classObj.newInstance();
			String response = service.connect();
			if(response != null) {			
				byte[] serviceResponseBytes = null;
				
				try {
					Globals.tcpClientConnectivity.openConnection(service.getService().getIp(), service.getService().getPort());
					Globals.tcpClientConnectivity.writeLine(ServicePackets.generatedRandomBytes(service.getServicePacket()));
					serviceResponseBytes = Globals.tcpClientConnectivity.readBytes();
					if(Constants.DEBUG_MODE) {
						if(!serviceResponseBytes.equals(null))
							Log.w("#####bytes", "bytes");
					}
					Globals.tcpClientConnectivity.closeConnection();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
						
				sendReport(service.getService(), response, serviceResponseBytes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	private void checkStatus(ServiceReport serviceReport) {
		if(serviceReport.getReport().getStatusCode() == 1) {
			double lat = 0.0;
			double lon = 0.0;
			if(Globals.currentLocationGPS != null) {
				lat = Globals.currentLocationGPS.getLatitude();
				lon = Globals.currentLocationGPS.getLongitude();
			} else if(Globals.currentLocationNetwork != null) {
				lat = Globals.currentLocationNetwork.getLatitude();
				lon = Globals.currentLocationNetwork.getLongitude();
			}
			
			Location location = Location.newBuilder()
			.setLatitude(lat)
			.setLongitude(lon)
			.build();
			
			Event event = Event.newBuilder()
			.setTestType("WEB")
			.setEventType("CENSOR")
			.setTimeUTC(serviceReport.getHeader().getTimeUTC())
			.setSinceTimeUTC(serviceReport.getHeader().getTimeUTC())
			.setServiceReport(serviceReport.getReport())
			.addLocations(location)			
			.build();
			
			Globals.runtimeList.addEvent(event);
		}
	}
	
}