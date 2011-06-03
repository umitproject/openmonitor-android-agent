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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpException;
import org.umit.icm.mobile.connectivity.WebsiteOpen;
import org.umit.icm.mobile.proto.MessageProtos.ICMReport;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReport;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReportDetail;
import org.umit.icm.mobile.utils.Constants;

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

		Runnable runnable = new Runnable() {
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
								// TODO Auto-generated catch block
								websiteContent = e.getMessage(); 
								e.printStackTrace();
						}
						
						catch (HttpException e) {
							// TODO Auto-generated catch block
							websiteContent = e.getMessage(); 
							e.printStackTrace();
						} catch (RuntimeException e) {
							// TODO Auto-generated catch block
							websiteContent = e.getMessage(); 
							e.printStackTrace();
						}
						
						try {
							Log.w("#####responsecode",  Integer.toString(WebsiteOpen.getResponseCode(urlConnection)));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (HttpException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							websiteContent = WebsiteOpen.getContent(urlConnection);
					} catch (IOException e) {
							// TODO Auto-generated catch block
							websiteContent = e.getMessage(); 
							e.printStackTrace();
					}
					
					catch (HttpException e) {
						// TODO Auto-generated catch block
						websiteContent = e.getMessage(); 
						e.printStackTrace();
					} catch (RuntimeException e) {
						// TODO Auto-generated catch block
						websiteContent = e.getMessage(); 
						e.printStackTrace();
					}
						
						try {
							websiteHeader = WebsiteOpen.getHeaders(urlConnection);
						} catch (IOException e) {
								// TODO Auto-generated catch block
								websiteHeader.put("exception", e.getMessage()); 
								e.printStackTrace();
						} catch (HttpException e) {
							// TODO Auto-generated catch block
							websiteHeader.put("exception", e.getMessage());
							e.printStackTrace();
						} catch (RuntimeException e) {
							// TODO Auto-generated catch block
							websiteHeader.put("exception", e.getMessage()); 
							e.printStackTrace();
						}
						
						try {
							websiteReport = (WebsiteReport) clean(currentURL
									, websiteContent, websiteHeader);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (RuntimeException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (websiteReport.getHtmlResponse().length()!=0) {
							if(websiteReport.getHtmlResponse().length()>100)
								Log.w("############", websiteReport.getHtmlResponse().substring(1, 100));
							else
								Log.w("############", websiteReport.getHtmlResponse().substring(1, websiteReport.getHtmlResponse().length()));
						}
							
						Log.w("############", Integer.toString(websiteReport.getReport().getStatusCode()));
						Log.w("############", websiteReport.getReport().getWebsiteURL());
					
						
					}
																				
			};
		};		
		new Thread(runnable).start();
	}
	
	public WebsiteReport clean(String websiteURL, String websiteContent
			, Map<String, String> websiteHeader) 
	throws IOException, RuntimeException {
		List<String> listNodes = new ArrayList<String>();
		listNodes.add("node1");
		listNodes.add("node2");
		ICMReport icmReport = ICMReport.newBuilder()
		.setReportID(10)
		.setAgentID(10)
		.setTestID(10)
		.setTimeZone(10)
		.setTimeUTC(10)
		.addAllPassedNode(listNodes)
		.build();
		
		int statusCode = websiteHeader.size();
		if(websiteHeader.size()!=0) {
			Pattern httpCodePattern = 
				Pattern.compile("10[0-1]|20[0-6]|30[0-7]|40[0-9]|41[0-7]|50[0-5]");
			Matcher httpCodeMatcher = 
				httpCodePattern.matcher(websiteHeader.get("status"));
			while (httpCodeMatcher.find()) {
			    statusCode = Integer.parseInt(httpCodeMatcher.group());
			}
		}
			
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

