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
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpException;
import org.umit.icm.mobile.connectivity.WebsiteOpen;
import org.umit.icm.mobile.process.RuntimeParameters;
import org.umit.icm.mobile.proto.MessageProtos.ICMReport;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReport;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReportDetail;
import org.umit.icm.mobile.utils.Constants;
import org.umit.icm.mobile.utils.SDCardReadWrite;

import com.google.protobuf.ByteString;


import android.util.Log;

// TODO Add sanity checks to all message fields. They may raise exceptions.
// TODO Catch Runtime exceptions.
public class WebsiteTest extends AbstractTest{
	
	private List<String> listWebsites;

	public WebsiteTest() {
		super();
		listWebsites = Constants.WEBSITE_LIST;
	}
	
	@Override()
	public void scan() {
		 int interval = Constants.DEFAULT_SCAN_INTERVAL;
		 RuntimeParameters runtimeParameters = new RuntimeParameters();
		 try {
				interval = runtimeParameters.getScanInterval();
		 } catch (Exception e) {
				e.printStackTrace();
		 }

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
									
				Iterator<String> iterator = listWebsites.iterator();
				String websiteContent = new String();
				Map<String, String> websiteHeader = new HashMap <String, String>();
				String currentURL = new String();
				WebsiteReport websiteReport = WebsiteReport.getDefaultInstance();
				URLConnection urlConnection = null;
					
				while(iterator.hasNext()){               
					currentURL = iterator.next(); 
    				try {
    						urlConnection = WebsiteOpen.openURLConnection(currentURL);
					} catch (IOException e) {
							websiteContent = e.getMessage(); 
							e.printStackTrace();
					} catch (HttpException e) {
							websiteContent = e.getMessage(); 
							e.printStackTrace();
					} catch (RuntimeException e) {							
							websiteContent = e.getMessage(); 
							e.printStackTrace();
					}
					
					try {
						websiteHeader = WebsiteOpen.getHeaders(urlConnection);
					} catch (IOException e) {
						websiteHeader.put("exception", e.getMessage()); 
						e.printStackTrace();
					} catch (HttpException e) {
						websiteHeader.put("exception", e.getMessage());
						e.printStackTrace();
					} catch (RuntimeException e) {
						websiteHeader.put("exception", e.getMessage()); 
						e.printStackTrace();
					}
					
					if(WebsiteOpen.httpOrHttps(websiteHeader).equalsIgnoreCase("http")) {
					
						try {
								websiteContent = WebsiteOpen.getContent(urlConnection);
						} catch (IOException e) {
								websiteContent = e.getMessage(); 
								e.printStackTrace();
						} catch (HttpException e) {
								websiteContent = e.getMessage(); 
								e.printStackTrace();
						} catch (RuntimeException e) {
								websiteContent = e.getMessage(); 
								e.printStackTrace();
						}
					} else {
						String newURL = websiteHeader.get("location");
						try {
    						urlConnection = WebsiteOpen.openURLConnection(newURL);
						} catch (IOException e) {
							websiteContent = e.getMessage(); 
							e.printStackTrace();
						} catch (HttpException e) {
							websiteContent = e.getMessage(); 
							e.printStackTrace();
						} catch (RuntimeException e) {							
							websiteContent = e.getMessage(); 
							e.printStackTrace();
						}
						
						try {
							websiteContent = WebsiteOpen.getContent(urlConnection);
						} catch (IOException e) {
							websiteContent = e.getMessage(); 
							e.printStackTrace();
						} catch (HttpException e) {
							websiteContent = e.getMessage(); 
							e.printStackTrace();
						} catch (RuntimeException e) {
							websiteContent = e.getMessage(); 
							e.printStackTrace();
						}
					}
						
					try {
							websiteReport = (WebsiteReport) clean(currentURL
									, websiteContent, websiteHeader);
							SDCardReadWrite.writeWebsiteReport
							(Constants.WEBSITES_DIR, websiteReport);
					} catch (IOException e) {
							e.printStackTrace();
					} catch (RuntimeException e) {
							e.printStackTrace();
					}
					if (websiteReport.getHtmlResponse().length()!=0) {
						if(websiteReport.getHtmlResponse().length()>100)
							Log.w("#####Content", websiteReport.getHtmlResponse().substring(1, 100));
						else
							Log.w("#####Content", websiteReport.getHtmlResponse().substring(1, websiteReport.getHtmlResponse().length()));
					}
							
					Log.w("######Code", Integer.toString(websiteReport.getReport().getStatusCode()));
					Log.w("######URL", websiteReport.getReport().getWebsiteURL());
											
				}
																				
			};
		}, 0, interval * 1000); 

	}
	
	public WebsiteReport clean(String websiteURL, String websiteContent
			, Map<String, String> websiteHeader) 
	throws IOException, RuntimeException {
		List<String> listNodes = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		listNodes.add("node1");
		listNodes.add("node2");
		ICMReport icmReport = ICMReport.newBuilder()
		.setReportID(10)
		.setAgentID(10)
		.setTestID(10)
		.setTimeZone(Calendar.ZONE_OFFSET)
		.setTimeUTC(calendar.getTimeInMillis())
		.addAllPassedNode(listNodes)
		.build();
		
		int statusCode = WebsiteOpen.getStatusCode(websiteHeader);
					
		WebsiteReportDetail websiteReportDetail = WebsiteReportDetail.newBuilder()
		.setBandwidth(0)
		.setResponseTime(0)
		.setStatusCode(statusCode)
		.setWebsiteURL(websiteURL)
		.build();
		
		WebsiteReport websiteReport = WebsiteReport.newBuilder()
		.setHtmlResponse(websiteContent)
		.setReport(websiteReportDetail)
		.setHeader(icmReport)
		.setHtmlMedia(ByteString.copyFromUtf8("media"))
		.build();
		return websiteReport;
	}
		
}