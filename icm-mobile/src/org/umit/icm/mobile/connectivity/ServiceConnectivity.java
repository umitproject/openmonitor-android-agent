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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;

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
	
	/**
	 * scan method which overrides the {@link AbstractConnectivity#scan}. 
	 * Calls the scan method of each service. 	
	 * 	  
	 *
	 */
	@Override()
	public void scan() throws IOException, HttpException, MessagingException {
		HTTPScan();
		HTTPSScan();
		FTPScan();
		POP3Scan();
		IMAPScan();
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
		if(bytes.equals(null) && serviceContent.equals(null))
			statusCode = 1;
		else if (!bytes.equals(null) && serviceContent.equals(null))
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
	
	/**
	 * For HTTP service calls
	 * {@link ServiceHTTP#connect()}	 	
	 * {@link TCPClient#writeLine(byte[])}, and
	 * {@link TCPClient#readBytes()}. Passes the services content
	 * to {@link ServiceConnectivity#clean(Service, String, byte[])}
	 * 
	 */
	public void HTTPScan() throws UnknownHostException, IOException {
		String HTTPResponse = ServiceHTTP.connect();
		if(!HTTPResponse.equals(null)) {
			Log.w("######httpResponse", HTTPResponse);
			byte[] serviceResponseBytes = null;
			ServiceReport serviceReport = ServiceReport.getDefaultInstance();						         
			Globals.tcpClientConnectivity.openConnection(
					ServiceHTTP.getService().getIp()
					, ServiceHTTP.getService().getPorts().get(0));
			Globals.tcpClientConnectivity.writeLine(
					ServicePackets.generatedRandomBytes(Globals.servicePacketsMap.get("http")));
			serviceResponseBytes
			= Globals.tcpClientConnectivity.readBytes();
			if(!serviceResponseBytes.equals(null))
				Log.w("#####bytes", "bytes");
			Globals.tcpClientConnectivity.closeConnection();
			
				try {
				serviceReport = (ServiceReport) clean(ServiceHTTP.getService()
							, HTTPResponse, serviceResponseBytes);
					SDCardReadWrite.writeServiceReport(Constants.SERVICES_DIR
							, serviceReport);						
						
				Log.w("######Code", Integer.toString(serviceReport.getReport().getStatusCode()));
				Log.w("######name", serviceReport.getReport().getServiceName());
				Log.w("######port", Integer.toString(ServiceHTTP.getService().getPorts().get(0)));
				} catch (RuntimeException e) {
					e.printStackTrace();
			}	catch (IOException e) {
					e.printStackTrace();
			}
		}
			
		
	}
	
	/**
	 * For HTTPS service calls
	 * {@link ServiceHTTPS#connect()}	 	
	 * {@link TCPClient#writeLine(byte[])}, and
	 * {@link TCPClient#readBytes()}. Passes the services content
	 * to {@link ServiceConnectivity#clean(Service, String, byte[])}
	 * 
	 */
	public void HTTPSScan() throws UnknownHostException, IOException {

		String HTTPSResponse = ServiceHTTPS.connect();
		Log.w("######httpsResponse", HTTPSResponse);
		byte[] httpsServiceResponseBytes = null;
		ServiceReport serviceReportHTTPS = ServiceReport.getDefaultInstance();						         
		Globals.tcpClientConnectivity.openConnection(
				ServiceHTTPS.getService().getIp()
				, ServiceHTTPS.getService().getPorts().get(0));
		Globals.tcpClientConnectivity.writeLine(
				ServicePackets.generatedRandomBytes(ServicePackets.HTTP_GET));
		httpsServiceResponseBytes
		= Globals.tcpClientConnectivity.readBytes();
		if(!httpsServiceResponseBytes.equals(null))
			Log.w("#####bytes", "bytes");
		Globals.tcpClientConnectivity.closeConnection();
		
			try {
			serviceReportHTTPS = (ServiceReport) clean(ServiceHTTPS.getService()
						, HTTPSResponse, httpsServiceResponseBytes);
				SDCardReadWrite.writeServiceReport(Constants.SERVICES_DIR
						, serviceReportHTTPS);						
					
			Log.w("######Code", Integer.toString(serviceReportHTTPS.getReport().getStatusCode()));
			Log.w("######name", serviceReportHTTPS.getReport().getServiceName());
			Log.w("######port", Integer.toString(ServiceHTTPS.getService().getPorts().get(0)));
			} catch (RuntimeException e) {
				e.printStackTrace();
		}	catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	/**
	 * For FTP service calls
	 * {@link ServiceFTP#connect()}	 	
	 * {@link TCPClient#writeLine(byte[])}, and
	 * {@link TCPClient#readBytes()}. Passes the services content
	 * to {@link ServiceConnectivity#clean(Service, String, byte[])}
	 * 
	 */
	public void FTPScan() throws IOException {
		
			String FTPResponse = ServiceFTP.connect();
			Log.w("######ftpResponse", FTPResponse);
			byte[] ftpServiceResponseBytes = null;
			ServiceReport serviceReportFTP = ServiceReport.getDefaultInstance();						         
			Globals.tcpClientConnectivity.openConnection(
					ServiceFTP.getService().getIp()
					, ServiceFTP.getService().getPorts().get(0));
			Globals.tcpClientConnectivity.writeLine(
					ServicePackets.generatedRandomBytes(ServicePackets.HTTP_GET));
			ftpServiceResponseBytes
			= Globals.tcpClientConnectivity.readBytes();
			if(!ftpServiceResponseBytes.equals(null))
				Log.w("#####bytes", "bytes");
			Globals.tcpClientConnectivity.closeConnection();
			
				try {
				serviceReportFTP = (ServiceReport) clean(ServiceFTP.getService()
							, FTPResponse, ftpServiceResponseBytes);
					SDCardReadWrite.writeServiceReport(Constants.SERVICES_DIR
							, serviceReportFTP);						
						
				Log.w("######Code", Integer.toString(serviceReportFTP.getReport().getStatusCode()));
				Log.w("######name", serviceReportFTP.getReport().getServiceName());
				Log.w("######port", Integer.toString(ServiceFTP.getService().getPorts().get(0)));
				} catch (RuntimeException e) {
					e.printStackTrace();
			}	catch (IOException e) {
					e.printStackTrace();
			}		
	}
	
	/**
	 * For POP3 service calls
	 * {@link ServicePOP3#connect()}	 	
	 * {@link TCPClient#writeLine(byte[])}, and
	 * {@link TCPClient#readBytes()}. Passes the services content
	 * to {@link ServiceConnectivity#clean(Service, String, byte[])}	 
	 * 
	 */
	public void POP3Scan() throws IOException, MessagingException {
		
			String POP3Response = ServicePOP3.connect();
			Log.w("######pop3Response", POP3Response);
			byte[] pop3ServiceResponseBytes = null;
			ServiceReport serviceReportPOP3 = ServiceReport.getDefaultInstance();						         
			Globals.tcpClientConnectivity.openConnection(
					ServicePOP3.getService().getIp()
					, ServicePOP3.getService().getPorts().get(0));
			Globals.tcpClientConnectivity.writeLine(
					ServicePackets.generatedRandomBytes(ServicePackets.HTTP_GET));
			pop3ServiceResponseBytes
			= Globals.tcpClientConnectivity.readBytes();
			if(!pop3ServiceResponseBytes.equals(null))
				Log.w("#####bytes", "bytes");
			Globals.tcpClientConnectivity.closeConnection();
			
				try {
				serviceReportPOP3 = (ServiceReport) clean(ServicePOP3.getService()
							, POP3Response, pop3ServiceResponseBytes);
					SDCardReadWrite.writeServiceReport(Constants.SERVICES_DIR
							, serviceReportPOP3);						
						
				Log.w("######Code", Integer.toString(serviceReportPOP3.getReport().getStatusCode()));
				Log.w("######name", serviceReportPOP3.getReport().getServiceName());
				Log.w("######port", Integer.toString(ServicePOP3.getService().getPorts().get(0)));
				} catch (RuntimeException e) {
					e.printStackTrace();
			}	catch (IOException e) {
					e.printStackTrace();
			}		
	}
	
	/**
	 * For IMAP service calls
	 * {@link ServiceIMAP#connect()}	 	
	 * {@link TCPClient#writeLine(byte[])}, and
	 * {@link TCPClient#readBytes()}. Passes the services content
	 * to {@link ServiceConnectivity#clean(Service, String, byte[])}	 
	 * 
	 */
	public void IMAPScan() throws IOException, MessagingException {
		
			String IMAPResponse = ServiceIMAP.connect();
			Log.w("######imapResponse", IMAPResponse);
			byte[] imapServiceResponseBytes = null;
			ServiceReport serviceReportIMAP = ServiceReport.getDefaultInstance();						         
			Globals.tcpClientConnectivity.openConnection(
					ServiceIMAP.getService().getIp()
					, ServiceIMAP.getService().getPorts().get(0));
			Globals.tcpClientConnectivity.writeLine(
					ServicePackets.generatedRandomBytes(ServicePackets.HTTP_GET));
			imapServiceResponseBytes
			= Globals.tcpClientConnectivity.readBytes();
			if(!imapServiceResponseBytes.equals(null))
				Log.w("#####bytes", "bytes");
			Globals.tcpClientConnectivity.closeConnection();
			
				try {
				serviceReportIMAP = (ServiceReport) clean(ServiceIMAP.getService()
							, IMAPResponse, imapServiceResponseBytes);
					SDCardReadWrite.writeServiceReport(Constants.SERVICES_DIR
							, serviceReportIMAP);						
						
				Log.w("######Code", Integer.toString(serviceReportIMAP.getReport().getStatusCode()));
				Log.w("######name", serviceReportIMAP.getReport().getServiceName());
				Log.w("######port", Integer.toString(ServiceIMAP.getService().getPorts().get(0)));
				} catch (RuntimeException e) {
					e.printStackTrace();
			}	catch (IOException e) {
					e.printStackTrace();
			}		
	}
		
}