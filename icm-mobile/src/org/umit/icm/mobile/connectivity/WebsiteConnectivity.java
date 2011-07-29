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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.umit.icm.mobile.connectivity.WebsiteOpen;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.process.IDGenerator;
import org.umit.icm.mobile.proto.MessageProtos.ICMReport;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReport;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReportDetail;
import org.umit.icm.mobile.utils.SDCardReadWrite;

import com.google.protobuf.ByteString;


import android.util.Log;

/**
 * This is the WebsiteConnectivity class which extends {@link AbstractConnectivity}.
 */

public class WebsiteConnectivity extends AbstractConnectivity{
	
	private List<String> listWebsites;

	/**
	 * This is the default constructor. Populates the Websites list with the
	 * list from {@link Constants}.
	 */
	public WebsiteConnectivity() {
		super();
		listWebsites = Constants.WEBSITE_LIST;
	}
	
	/**
	 * scan method which overrides the {@link AbstractConnectivity#scan}. 
	 * Iterates through the websites list and for each website calls
	 * {@link WebsiteOpen#openURLConnection(String)},
	 * {@link WebsiteOpen#getHeaders(URLConnection)} and
	 * {@link WebsiteOpen#getContent(URLConnection)}. Passes the website content
	 * to {@link WebsiteConnectivity#clean(String, String, Map)} 
	 * 
	 * 
	 *
	 @see         WebsiteOpen
	 */
	@Override()
	public void scan() throws IOException, HttpException {
											
		Iterator<String> iterator = listWebsites.iterator();
		String websiteContent = new String();
		Map<String, String> websiteHeader = new HashMap <String, String>();
		String currentURL = new String();
		WebsiteReport websiteReport = WebsiteReport.getDefaultInstance();
		URLConnection urlConnection = null;
		long startTimeContent, elapsedTimeContent;
			
		while(iterator.hasNext()){               
			startTimeContent = elapsedTimeContent = 0;
			currentURL = iterator.next(); 
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
				websiteReport = (WebsiteReport) clean(currentURL
							, websiteContent, websiteHeader, elapsedTimeContent);
					SDCardReadWrite.writeWebsiteReport
					(Constants.WEBSITES_DIR, websiteReport);
				if (websiteReport.getReport().getHtmlResponse().length()!=0) {
					if(websiteReport.getReport().getHtmlResponse().length()>100)
						Log.w("#####Content"
								, websiteReport.getReport().getHtmlResponse().substring(1, 100));
					else
						Log.w("#####Content"
								, websiteReport.getReport().getHtmlResponse().substring(1
										, websiteReport.getReport().getHtmlResponse().length()));
				}
				Log.w("######BW", Integer.toString(websiteReport.getReport().getBandwidth()));
				Log.w("######ResponseTime", Integer.toString(websiteReport.getReport().getResponseTime()));
				Log.w("######Code", Integer.toString(websiteReport.getReport().getStatusCode()));
				Log.w("######URL", websiteReport.getReport().getWebsiteURL());
				} catch (RuntimeException e) {
					e.printStackTrace();
			}	catch (IOException e) {
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
	 */	
	public WebsiteReport clean(String websiteURL, String websiteContent
			, Map<String, String> websiteHeader, long responseTime) 
	throws IOException, RuntimeException {
		
		int statusCode = WebsiteOpen.getStatusCode(websiteHeader);
					
		WebsiteReportDetail websiteReportDetail = WebsiteReportDetail.newBuilder()
		.setBandwidth((int)getThroughput(websiteContent, responseTime))
		.setResponseTime((int)responseTime)
		.setStatusCode(statusCode)
		.setHtmlResponse(websiteContent)
		.setWebsiteURL(websiteURL)		
		.build();
		
		List<String> listNodes = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		listNodes.add(Long.toString(Globals.runtimeParameters.getAgentID()));		
		ICMReport icmReport = ICMReport.newBuilder()
		.setReportID(Integer.toString(websiteReportDetail.hashCode()))
		.setAgentID(Globals.runtimeParameters.getAgentID())
		.setTestID(10)
		.setTimeZone(Calendar.ZONE_OFFSET)
		.setTimeUTC(calendar.getTimeInMillis())
		.addAllPassedNode(listNodes)
		.build();
				
		WebsiteReport websiteReport = WebsiteReport.newBuilder()		
		.setReport(websiteReportDetail)
		.setHeader(icmReport)	
		.build();
		return websiteReport;
	}
	
	private long getThroughput(String stringResponse, long stringTime) {		
		return (stringResponse.getBytes().length / stringTime);
	}
		
}