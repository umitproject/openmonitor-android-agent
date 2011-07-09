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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpException;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.process.IDGenerator;
import org.umit.icm.mobile.proto.MessageProtos.ICMReport;
import org.umit.icm.mobile.proto.MessageProtos.ServiceReport;
import org.umit.icm.mobile.proto.MessageProtos.ServiceReportDetail;
import org.umit.icm.mobile.utils.Constants;
import org.umit.icm.mobile.utils.SDCardReadWrite;



import android.util.Log;

/**
 * This is the ServiceConnectivity class which extends {@link AbstractConnectivity}.
 */

public class ServiceConnectivity extends AbstractConnectivity{
	
	private List<Service> listServices;

	/**
	 * This is the default constructor. Populates the Services list with the
	 * list from {@link Constants}.
	 */
	public ServiceConnectivity() {
		super();
		listServices = Constants.SERVICE_LIST;
	}
	
	/**
	 * scan method which overrides the {@link AbstractConnectivity#scan}. 
	 * Iterates through the services list and for each services calls
	 * {@link TCPClient#writeLine(String)},
	 * {@link TCPClient#readLines()},
	 * {@link TCPClient#writeLine(byte[])}, and
	 * {@link TCPClient#readBytes()}. Passes the services content
	 * to {@link ServiceConnectivity#clean(Service, String, byte[])} 
	 * 
	 * 
	 *
	 @see         TCPClient
	 */
	@Override()
	public void scan() throws IOException, HttpException {													
		Iterator<Service> iterator = listServices.iterator();
		Service currentService = new Service();	
		String serviceResponsePacket = "";
		byte[] serviceResponseBytes = null;
		ServiceReport serviceReport = ServiceReport.getDefaultInstance();		
			
		while(iterator.hasNext()){               
			currentService = iterator.next();			
			Globals.tcpClientConnectivity.openConnection(
					currentService.getIp(), currentService.getPorts().get(0));
			Globals.tcpClientConnectivity.writeLine(ServicePackets.HTTP_GET);			
			serviceResponsePacket			
			= Globals.tcpClientConnectivity.readLines();
			Globals.tcpClientConnectivity.closeConnection();
			Log.w("#####packet", serviceResponsePacket);
			Globals.tcpClientConnectivity.openConnection(
					currentService.getIp(), currentService.getPorts().get(0));
			Globals.tcpClientConnectivity.writeLine(
					ServicePackets.generatedRandomBytes(ServicePackets.HTTP_GET));
			serviceResponseBytes
			= Globals.tcpClientConnectivity.readBytes();
			if(!serviceResponseBytes.equals(null))
				Log.w("#####bytes", "bytes");
			Globals.tcpClientConnectivity.closeConnection();
			
				try {
				serviceReport = (ServiceReport) clean(currentService
							, serviceResponsePacket, serviceResponseBytes);
					SDCardReadWrite.writeServiceReport(Constants.SERVICES_DIR
							, serviceReport);						
						
				Log.w("######Code", Integer.toString(serviceReport.getReport().getStatusCode()));
				Log.w("######name", serviceReport.getReport().getServiceName());
				} catch (RuntimeException e) {
					e.printStackTrace();
			}	catch (IOException e) {
					e.printStackTrace();
			}												
									
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
	            
	@return      ServiceReport
	 */	
	public ServiceReport clean(Service service, String serviceContent
			, byte[] bytes) 
	throws IOException, RuntimeException {
		List<String> listNodes = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		listNodes.add("node1");
		listNodes.add("node2");
		ICMReport icmReport = ICMReport.newBuilder()
		.setReportID(IDGenerator.generateReportID())
		.setAgentID(Globals.runtimeParameters.getAgentID())
		.setTestID(10)
		.setTimeZone(Calendar.ZONE_OFFSET)
		.setTimeUTC(calendar.getTimeInMillis())
		.addAllPassedNode(listNodes)
		.build();
		
		int statusCode = 0;
		if(bytes.equals(null) && serviceContent.equals(""))
			statusCode = 1;
		else if (!bytes.equals(null) && serviceContent.equals(""))
			statusCode = 1;
		ServiceReportDetail serviceReportDetail = ServiceReportDetail.newBuilder()
		.setServiceName(service.getName())
		.setStatusCode(statusCode)
		.build();
				
		ServiceReport serviceReport = ServiceReport.newBuilder()
		.setReport(serviceReportDetail)		
		.setHeader(icmReport)		
		.build();
		
		return serviceReport;
	}
		
}