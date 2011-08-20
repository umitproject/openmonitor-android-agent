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
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.umit.icm.mobile.aggregator.AggregatorRetrieve;
import org.umit.icm.mobile.connectivity.WebsiteOpen;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.process.IDGenerator;
import org.umit.icm.mobile.proto.MessageProtos.Event;
import org.umit.icm.mobile.proto.MessageProtos.ICMReport;
import org.umit.icm.mobile.proto.MessageProtos.Location;
import org.umit.icm.mobile.proto.MessageProtos.RequestHeader;
import org.umit.icm.mobile.proto.MessageProtos.SendWebsiteReport;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReport;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReportDetail;
import org.umit.icm.mobile.utils.SDCardReadWrite;


import android.util.Log;

/**
 * This is the WebsiteConnectivity class which extends {@link AbstractConnectivity}.
 */

public class WebsiteConnectivity extends AbstractConnectivity{		

	/**
	 * This is the default constructor. Populates the Websites list with the
	 * list from {@link Constants}.
	 */
	public WebsiteConnectivity() {
		super();		
	}
	
	/**
	 * scan method which overrides the {@link AbstractConnectivity#scan}. 
	 * Iterates through the websites list and for each website calls
	 * {@link WebsiteOpen#openURLConnection(String)},
	 * {@link WebsiteOpen#getHeaders(URLConnection)} and
	 * {@link WebsiteOpen#getContent(URLConnection)}. Passes the website content
	 * to {@link WebsiteConnectivity#clean(String, String, Map, long)} 
	 * 
	 * 
	 *
	 @see         WebsiteOpen
	 */
	@Override()
	public void scan() throws IOException, HttpException {
											
		Iterator<Website> iterator = Globals.websitesList.iterator();
		String websiteContent = new String();
		Map<String, String> websiteHeader = new HashMap <String, String>();
		String currentURL = new String();
		Website website = new Website();
		WebsiteReport websiteReport = WebsiteReport.getDefaultInstance();
		URLConnection urlConnection = null;
		long startTimeContent, elapsedTimeContent;
			
		while(iterator.hasNext()){               
			startTimeContent = elapsedTimeContent = 0;
			website = iterator.next();
			currentURL = website.getUrl(); 
			urlConnection = WebsiteOpen.openURLConnection(currentURL);
			websiteHeader = WebsiteOpen.getHeaders(urlConnection);
			
			if(WebsiteOpen.httpOrHttps(websiteHeader).equalsIgnoreCase("http")) {
				startTimeContent = System.currentTimeMillis();
				websiteContent = WebsiteOpen.getContent(urlConnection);
				elapsedTimeContent = System.currentTimeMillis() - startTimeContent;																																
			} else {
				String newURL = websiteHeader.get("location");
				urlConnection = WebsiteOpen.openURLConnection(newURL);
				startTimeContent = System.currentTimeMillis();
				websiteContent = WebsiteOpen.getContent(urlConnection);
				elapsedTimeContent = System.currentTimeMillis() - startTimeContent;																					
			}
			try {
				websiteReport = (WebsiteReport) clean(website
						, websiteContent, websiteHeader, elapsedTimeContent);
				SDCardReadWrite.writeWebsiteReport(Constants.WEBSITES_DIR, websiteReport);									
			
				Log.w("######BW", Integer.toString(websiteReport.getReport().getBandwidth()));
				Log.w("######ResponseTime", Integer.toString(websiteReport.getReport().getResponseTime()));
				Log.w("######Code", Integer.toString(websiteReport.getReport().getStatusCode()));
				Log.w("######URL", websiteReport.getReport().getWebsiteURL());
				
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(Globals.runtimeParameters.getAgentID())
				.setToken(Globals.runtimeParameters.getToken())
				.build();
				SendWebsiteReport sendWebsiteReport = SendWebsiteReport.newBuilder()
				.setHeader(requestHeader)
				.setReport(websiteReport)
				.build();				
				if(Globals.aggregatorCommunication != false) {
					AggregatorRetrieve.sendWebsiteReport(sendWebsiteReport);
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
																		
	};
		 
	/**
	 * Returns a {@link WebsiteReport}. Uses the passed parameters to generate
	 * {@link ICMReport}, {@link WebsiteReportDetail} and
	 * {@link WebsiteReport} messages. 
	 * 
	 *	 
	                          
	@param  websiteURL  An object of the type {@link String}
	 *  	                          	
	
	@param  websiteContent  An object of the type {@link String}
	 *  	                          	
	
	@param  websiteHeader  An object of the type {@link Map}
	 *  	                          		              
	            
	@return      WebsiteReport
	 * @throws NoSuchAlgorithmException 
	 */	
	public WebsiteReport clean(Website website, String websiteContent
			, Map<String, String> websiteHeader, long responseTime) 
	throws IOException, RuntimeException, NoSuchAlgorithmException {
		
		int statusCode = WebsiteOpen.getStatusCode(websiteHeader);
					
		WebsiteReportDetail websiteReportDetail = WebsiteReportDetail.newBuilder()
		.setBandwidth((int)getThroughput(websiteContent, responseTime))
		.setResponseTime((int)responseTime)
		.setStatusCode(statusCode)
		.setHtmlResponse(websiteContent)
		.setWebsiteURL(website.getUrl())		
		.build();			
		
		List<String> listNodes = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		listNodes.add(Long.toString(Globals.runtimeParameters.getAgentID()));
		long timeUTC = (calendar.getTimeInMillis()/1000);
		ICMReport icmReport = ICMReport.newBuilder()
		.setReportID(IDGenerator.generateReportID(Globals.runtimeParameters.getAgentID()
				, timeUTC
				, website.getTestID()))
		.setAgentID(Globals.runtimeParameters.getAgentID())
		.setTestID((int)website.getTestID())
		.setTimeZone(Calendar.ZONE_OFFSET)
		.setTimeUTC(timeUTC)
		.addAllPassedNode(listNodes)
		.build();
				
		WebsiteReport websiteReport = WebsiteReport.newBuilder()		
		.setReport(websiteReportDetail)
		.setHeader(icmReport)	
		.build();
		checkStatus(websiteReport);
		return websiteReport;
	}
	
	private long getThroughput(String stringResponse, long stringTime) {		
		return (stringResponse.getBytes().length / stringTime);
	}
	
	private void checkStatus(WebsiteReport websiteReport) {
		if(websiteReport.getReport().getStatusCode() != 200) {
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
			.setTimeUTC(websiteReport.getHeader().getTimeUTC())
			.setSinceTimeUTC(websiteReport.getHeader().getTimeUTC())
			.setWebsiteReport(websiteReport.getReport())
			.addLocations(location)			
			.build();
			
			Globals.runtimesList.addEvent(event);
		}
	}
		
}