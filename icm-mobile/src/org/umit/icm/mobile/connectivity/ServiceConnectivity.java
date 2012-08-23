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
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.http.HttpException;
import org.umit.icm.mobile.aggregator.AggregatorRetrieve;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.process.IDGenerator;
import org.umit.icm.mobile.proto.MessageProtos.Event;
import org.umit.icm.mobile.proto.MessageProtos.ICMReport;
import org.umit.icm.mobile.proto.MessageProtos.Location;
import org.umit.icm.mobile.proto.MessageProtos.RequestHeader;
import org.umit.icm.mobile.proto.MessageProtos.SendServiceReport;
import org.umit.icm.mobile.proto.MessageProtos.ServiceReport;
import org.umit.icm.mobile.proto.MessageProtos.ServiceReportDetail;
import org.umit.icm.mobile.proto.MessageProtos.Trace;
import org.umit.icm.mobile.proto.MessageProtos.TraceRoute;
import org.umit.icm.mobile.utils.CopyNative;
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
		
		System.out.println("Scanning SOME SERVICES ---------------------------");
		
		HTTPScan();
		HTTPSScan();
		FTPScan();
		POP3Scan();
		IMAPScan();
		GtalkScan();
		MSNScan();
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
		
		Trace trace = Trace.newBuilder()
		.setHop(1)
		.setIp("193.136.175.1")
		.addPacketsTiming(1)
		.build();
		
		TraceRoute traceRoute = TraceRoute.newBuilder()
		.setTarget("193.136.175.1")
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
		if(HTTPResponse != null) {			
			byte[] serviceResponseBytes = null;
			ServiceReport serviceReport = ServiceReport.getDefaultInstance();
			
			Globals.tcpClientConnectivity.openConnection(ServiceHTTP.getService().getIp(), ServiceHTTP.getService().getPort());
			
			Globals.tcpClientConnectivity.writeLine(ServicePackets.generatedRandomBytes(Globals.servicePacketsMap.get("http")));
			
			serviceResponseBytes= Globals.tcpClientConnectivity.readBytes();
			
			if(!serviceResponseBytes.equals(null))
				Log.w("#####bytes", "bytes");
			
			Globals.tcpClientConnectivity.closeConnection();
			
			try {
				
				serviceReport = (ServiceReport) clean(ServiceHTTP.getService(), HTTPResponse, serviceResponseBytes);
				
				SDCardReadWrite.writeServiceReport(Constants.SERVICES_DIR, serviceReport);						
					
				Log.w("######Code", Integer.toString(serviceReport.getReport().getStatusCode()));
				Log.w("######name", serviceReport.getReport().getServiceName());
				Log.w("######port", Integer.toString(ServiceHTTP.getService().getPort()));
				
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(Globals.runtimeParameters.getAgentID())
				.build();
				SendServiceReport sendServiceReport = SendServiceReport.newBuilder()
				.setReport(serviceReport)
				.build();
				if(Globals.aggregatorCommunication != false) {
					System.out.println("Sending HTTP SERVICE REPORT \n");
					System.out.println("Sending Service Report : \n" + sendServiceReport.toString());
					
					AggregatorRetrieve.sendServiceReport(sendServiceReport);
				}
				} catch (RuntimeException e) {
					e.printStackTrace();
				}	catch (IOException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
		if(HTTPSResponse != null) {	
			byte[] httpsServiceResponseBytes = null;
			ServiceReport serviceReportHTTPS = ServiceReport.getDefaultInstance();						         
			Globals.tcpClientConnectivity.openConnection(
					ServiceHTTPS.getService().getIp()
					, ServiceHTTPS.getService().getPort());
			Globals.tcpClientConnectivity.writeLine(
					ServicePackets.generatedRandomBytes(ServicePackets.HTTP_GET));
			httpsServiceResponseBytes = Globals.tcpClientConnectivity.readBytes();
			if(!httpsServiceResponseBytes.equals(null))
				Log.w("#####bytes", "bytes");
			Globals.tcpClientConnectivity.closeConnection();
			
			try {
				serviceReportHTTPS = (ServiceReport) clean(ServiceHTTPS.getService(), HTTPSResponse, httpsServiceResponseBytes);
				
				SDCardReadWrite.writeServiceReport(Constants.SERVICES_DIR, serviceReportHTTPS);						
					
				Log.w("######Code", Integer.toString(serviceReportHTTPS.getReport().getStatusCode()));
				Log.w("######name", serviceReportHTTPS.getReport().getServiceName());
				Log.w("######port", Integer.toString(ServiceHTTPS.getService().getPort()));
				
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(Globals.runtimeParameters.getAgentID())
				.build();
				
				SendServiceReport sendServiceReport = SendServiceReport.newBuilder()
				.setReport(serviceReportHTTPS)
				.build();
				
				if(Globals.aggregatorCommunication != false) {
					
					System.out.println("Sending HTTPS SERVICE REPORT \n");
					System.out.println("Sending Service Report : \n" + sendServiceReport.toString());
					
					AggregatorRetrieve.sendServiceReport(sendServiceReport);
				}
				} catch (RuntimeException e) {
					e.printStackTrace();
				}	catch (IOException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		if(FTPResponse != null) {			
			byte[] ftpServiceResponseBytes = null;
			ServiceReport serviceReportFTP = ServiceReport.getDefaultInstance();						         
			Globals.tcpClientConnectivity.openConnection(
					ServiceFTP.getService().getIp()
					, ServiceFTP.getService().getPort());
			Globals.tcpClientConnectivity.writeLine(
					ServicePackets.generatedRandomBytes(ServicePackets.HTTP_GET));
			ftpServiceResponseBytes	= Globals.tcpClientConnectivity.readBytes();
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
				Log.w("######port", Integer.toString(ServiceFTP.getService().getPort()));
				
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(Globals.runtimeParameters.getAgentID())
				.build();
				SendServiceReport sendServiceReport = SendServiceReport.newBuilder()
				.setReport(serviceReportFTP)
				.build();
				if(Globals.aggregatorCommunication != false) {
					
					
					System.out.println("Sending FTP SERVICE REPORT \n");
					System.out.println("Sending Service Report : \n" + sendServiceReport.toString());
					
					AggregatorRetrieve.sendServiceReport(sendServiceReport);
				}
				} catch (RuntimeException e) {
					e.printStackTrace();
				}	catch (IOException e) {
					e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		if(POP3Response != null) {			
			byte[] pop3ServiceResponseBytes = null;
			ServiceReport serviceReportPOP3 = ServiceReport.getDefaultInstance();						         
			Globals.tcpClientConnectivity.openConnection(
					ServicePOP3.getService().getIp()
					, ServicePOP3.getService().getPort());
			Globals.tcpClientConnectivity.writeLine(
					ServicePackets.generatedRandomBytes(ServicePackets.HTTP_GET));
			pop3ServiceResponseBytes = Globals.tcpClientConnectivity.readBytes();
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
				Log.w("######port", Integer.toString(ServicePOP3.getService().getPort()));
				
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(Globals.runtimeParameters.getAgentID())
				.build();
				SendServiceReport sendServiceReport = SendServiceReport.newBuilder()
				.setReport(serviceReportPOP3)
				.build();
				if(Globals.aggregatorCommunication != false) {
					
					System.out.println("Sending POP3 SERVICE REPORT \n");
					System.out.println("Sending Service Report : \n" + sendServiceReport.toString());
					
					AggregatorRetrieve.sendServiceReport(sendServiceReport);
				}
				} catch (RuntimeException e) {
					e.printStackTrace();
				}	catch (IOException e) {
					e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		if(IMAPResponse != null) {		
			byte[] imapServiceResponseBytes = null;
			ServiceReport serviceReportIMAP = ServiceReport.getDefaultInstance();						         
			Globals.tcpClientConnectivity.openConnection(
					ServiceIMAP.getService().getIp()
					, ServiceIMAP.getService().getPort());
			Globals.tcpClientConnectivity.writeLine(
					ServicePackets.generatedRandomBytes(ServicePackets.HTTP_GET));
			imapServiceResponseBytes = Globals.tcpClientConnectivity.readBytes();
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
				Log.w("######port", Integer.toString(ServiceIMAP.getService().getPort()));
				
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(Globals.runtimeParameters.getAgentID())
				.build();
				SendServiceReport sendServiceReport = SendServiceReport.newBuilder()
				.setReport(serviceReportIMAP)
				.build();
				if(Globals.aggregatorCommunication != false) {
					
					System.out.println("Sending IMAP SERVICE REPORT \n");
					System.out.println("Sending Service Report : \n" + sendServiceReport.toString());
					
					AggregatorRetrieve.sendServiceReport(sendServiceReport);
				}
				} catch (RuntimeException e) {
					e.printStackTrace();
				}	catch (IOException e) {
					e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
	
	/**
	 * For Gtalk service calls
	 * {@link ServiceGtalk#connect()}	 	
	 * {@link TCPClient#writeLine(byte[])}, and
	 * {@link TCPClient#readBytes()}. Passes the services content
	 * to {@link ServiceConnectivity#clean(Service, String, byte[])}	 
	 * 
	 */
	public void GtalkScan() throws IOException, MessagingException {		
		String GtalkResponse = ServiceGtalk.connect();
		if(GtalkResponse != null) {			
			byte[] gtalkServiceResponseBytes = null;
			ServiceReport serviceReportGtalk = ServiceReport.getDefaultInstance();						         
			Globals.tcpClientConnectivity.openConnection(
					ServiceGtalk.getService().getIp()
					, ServiceGtalk.getService().getPort());
			Globals.tcpClientConnectivity.writeLine(
					ServicePackets.generatedRandomBytes(ServicePackets.HTTP_GET));
			gtalkServiceResponseBytes = Globals.tcpClientConnectivity.readBytes();
			if(!gtalkServiceResponseBytes.equals(null))
				Log.w("#####bytes", "bytes");
			Globals.tcpClientConnectivity.closeConnection();
			
			try {
				serviceReportGtalk = (ServiceReport) clean(ServiceGtalk.getService()
						, GtalkResponse, gtalkServiceResponseBytes);
				SDCardReadWrite.writeServiceReport(Constants.SERVICES_DIR
						, serviceReportGtalk);						
						
				Log.w("######Code", Integer.toString(serviceReportGtalk.getReport().getStatusCode()));
				Log.w("######name", serviceReportGtalk.getReport().getServiceName());
				Log.w("######port", Integer.toString(ServiceGtalk.getService().getPort()));
				
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(Globals.runtimeParameters.getAgentID())
				.build();
				SendServiceReport sendServiceReport = SendServiceReport.newBuilder()
				.setReport(serviceReportGtalk)
				.build();
				if(Globals.aggregatorCommunication != false) {
					
					System.out.println("Sending Gtalk SERVICE REPORT \n");
					System.out.println("Sending Service Report : \n" + sendServiceReport.toString());
					
					AggregatorRetrieve.sendServiceReport(sendServiceReport);
				}
				} catch (RuntimeException e) {
					e.printStackTrace();
				}	catch (IOException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
		}		
	
	/**
	 * For MSN service calls
	 * {@link ServiceMSN#connect()}	 	
	 * {@link TCPClient#writeLine(byte[])}, and
	 * {@link TCPClient#readBytes()}. Passes the services content
	 * to {@link ServiceConnectivity#clean(Service, String, byte[])}	 
	 * 
	 */
	public void MSNScan() throws IOException, MessagingException {		
		String msnResponse = ServiceMSN.connect();
		if(msnResponse != null) {				
			byte[] msnServiceResponseBytes = null;
			ServiceReport serviceReportMSN = ServiceReport.getDefaultInstance();						         
			Globals.tcpClientConnectivity.openConnection(ServiceMSN.getService().getIp(), ServiceMSN.getService().getPort());
			Globals.tcpClientConnectivity.writeLine(ServicePackets.generatedRandomBytes(ServicePackets.MSN_VER));
			msnServiceResponseBytes	= Globals.tcpClientConnectivity.readBytes();
			if(!msnServiceResponseBytes.equals(null))
				Log.w("#####bytes", "bytes");
			Globals.tcpClientConnectivity.closeConnection();
			
			try {
				serviceReportMSN = (ServiceReport) clean(ServiceMSN.getService()
						, msnResponse, msnServiceResponseBytes);
				SDCardReadWrite.writeServiceReport(Constants.SERVICES_DIR
						, serviceReportMSN);						
					
				Log.w("######Code", Integer.toString(serviceReportMSN.getReport().getStatusCode()));
				Log.w("######name", serviceReportMSN.getReport().getServiceName());
				Log.w("######port", Integer.toString(ServiceMSN.getService().getPort()));
				
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(Globals.runtimeParameters.getAgentID())
				.build();
				SendServiceReport sendServiceReport = SendServiceReport.newBuilder()
				.setReport(serviceReportMSN)
				.build();
				if(Globals.aggregatorCommunication != false) {
					
					System.out.println("Sending MSN SERVICE REPORT \n");
					System.out.println("Sending Service Report : \n" + sendServiceReport.toString());
					
					AggregatorRetrieve.sendServiceReport(sendServiceReport);
				}					
				} catch (RuntimeException e) {
				e.printStackTrace();
				}	catch (IOException e) {
				e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
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
			
			Globals.runtimesList.addEvent(event);
		}
	}
	}