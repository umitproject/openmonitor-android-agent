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

package org.umit.icm.mobile.utils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpException;
import org.umit.icm.mobile.aggregator.AggregatorRetrieve;
import org.umit.icm.mobile.connectivity.Website;
import org.umit.icm.mobile.connectivity.WebsiteOpen;
import org.umit.icm.mobile.proto.MessageProtos.*;

import android.util.Log;

public class ProfilerRun {
	
	
	
	public static void run() {	
		profileTraceBuild();
		profileTraceRouteBuild();
		profileICMReport();		
		profileRSAGenerateKey();
		profileRSAEncrypt();
		profileRSADecrypt();
		profileRSAPrivateEncrypt();
		profileRSAPrivateDecrypt();
		profileAESEncrypt();
		profileAESDecrypt();
		profileSDCardWriteString();
		profileSDCardReadString();
		profileWebsiteReportDetailBuild();
		profileWebsiteReportBuild();
		profileServiceReportDetailBuild();
		profileServiceReportBuild();
		profileRequestHeader();
		profileResponseHeader();
		profileRegisterAgent();
		profileRegisterAgentResponse();
		profileWebsiteOpen();
		profileWebsiteReadWrite();
		profileWebsiteListReadWrite();
		profileBase64Encode();
		profileBase64Decode();
		profileAggrSendServiceSuggestion();
		profileAggrSendWebsiteSuggestion();
		profileAggrCheckTests();
		profileAggrCheckVersion();
	}
		
	private static void profileTraceBuild (){
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				Trace trace = Trace.newBuilder()
				.setHop(10)
				.setIp("IP")
				.addPacketsTiming(10)
				.build();
			}
			
			public String taskName() {
				return "Trace Build";
			}
		});
	}
	
	private static void profileTraceRouteBuild (){
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				Trace trace = Trace.newBuilder()
				.setHop(10)
				.setIp("IP")
				.addPacketsTiming(10)
				.build();
				
				TraceRoute traceRoute = TraceRoute.newBuilder()
				.setHops(10)
				.setPacketSize(10)
				.setTarget("target")
				.addTraces(trace)
				.build();
			}
			
			public String taskName() {
				return "TraceRoute Build";
			}
		});
	}
	
	private static void profileICMReport() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				Trace trace = Trace.newBuilder()
				.setHop(10)
				.setIp("IP")
				.addPacketsTiming(10)
				.build();
				
				TraceRoute traceRoute = TraceRoute.newBuilder()
				.setHops(10)
				.setPacketSize(10)
				.setTarget("target")
				.addTraces(trace)
				.build();
				
				ICMReport icmReport = ICMReport.newBuilder()
				.setAgentID(10)
				.setReportID(10)
				.setTestID(10)
				.setTimeUTC(10)
				.setTimeZone(10)
				.setTraceroute(traceRoute)
				.addPassedNode("node1")
				.build();
			}
			
			public String taskName() {
				return "ICMReport Build";
			}
		});
	}
	
	private static void profileRSAGenerateKey() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				try {
					KeyPair keyPair = RSACrypto.generateKey();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public String taskName() {
				return "RSA KeyPair Generate";
			}
		});
	}
	
	private static void profileRSAEncrypt() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				try {
					KeyPair keyPair = RSACrypto.generateKey();
					String cipherText = RSACrypto.encryptPublic(keyPair.getPublic(), "This is a test string");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public String taskName() {
				return "RSA Public Encryption";
			}
		});
	}
	
	private static void profileRSADecrypt() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				try {
					KeyPair keyPair = RSACrypto.generateKey();
					String cipherText = RSACrypto.encryptPublic(keyPair.getPublic(), "This is a test string");
					String str = RSACrypto.decryptPrivate(keyPair.getPrivate(), cipherText);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public String taskName() {
				return "RSA Public Decryption";
			}
		});
	}
	
	private static void profileRSAPrivateEncrypt() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				try {
					KeyPair keyPair = RSACrypto.generateKey();
					String cipherText = RSACrypto.encryptPrivate(keyPair.getPrivate(), "This is a test string");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public String taskName() {
				return "RSA Private Encryption";
			}
		});
	}
	
	private static void profileRSAPrivateDecrypt() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				try {
					KeyPair keyPair = RSACrypto.generateKey();
					String cipherText = RSACrypto.encryptPrivate(keyPair.getPrivate(), "This is a test string");
					String str = RSACrypto.decryptPublic(keyPair.getPublic(), cipherText);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public String taskName() {
				return "RSA Private Decryption";
			}
		});
	}
	
	private static void profileAESEncrypt() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				try {
					String cipherText = AESCrypto.encrypt("secretICMMobilePassword"
			    			, "This is a test string");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public String taskName() {
				return "AES Encryption";
			}
		});
	}
	
	private static void profileAESDecrypt() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				try {
					String cipherText = AESCrypto.encrypt("secretICMMobilePassword"
			    			, "This is a test string");
					String str = AESCrypto.decrypt("secretICMMobilePassword", cipherText);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public String taskName() {
				return "AES Decryption";
			}
		});
	}
	
	private static void profileSDCardWriteString() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				try {
					SDCardReadWrite.writeString("sdtest.txt", "/test" , "This is a test string");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public String taskName() {
				return "SDCard Write String";
			}
		});
	}
	
	private static void profileSDCardReadString() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				try {
					String readString = SDCardReadWrite.readString("sdtest.txt", "/test");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public String taskName() {
				return "SDCard Read String";
			}
		});
	}
	
	private static void profileWebsiteReportBuild() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				WebsiteReportDetail websiteReportDetail = WebsiteReportDetail.newBuilder()
				.setBandwidth(10)
				.setResponseTime(10)
				.setStatusCode(10)
				.setWebsiteURL("url")
				.build();
				
				Trace trace = Trace.newBuilder()
				.setHop(10)
				.setIp("IP")
				.addPacketsTiming(10)
				.build();
				
				TraceRoute traceRoute = TraceRoute.newBuilder()
				.setHops(10)
				.setPacketSize(10)
				.setTarget("target")
				.addTraces(trace)
				.build();
				
				ICMReport icmReport = ICMReport.newBuilder()
				.setAgentID(10)
				.setReportID(10)
				.setTestID(10)
				.setTimeUTC(10)
				.setTimeZone(10)
				.setTraceroute(traceRoute)
				.addPassedNode("node1")
				.build();
				
				WebsiteReport websiteReport = WebsiteReport.newBuilder()
				.setHeader(icmReport)
				.setHtmlResponse("response")
				.setReport(websiteReportDetail)
				.setRedirectLink("link")			
				.build();
				}
			
			public String taskName() {
				return "WebsiteReport Build";
			}
		});
	}
	
	private static void profileWebsiteReportDetailBuild() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				WebsiteReportDetail websiteReportDetail = WebsiteReportDetail.newBuilder()
				.setBandwidth(10)
				.setResponseTime(10)
				.setStatusCode(10)
				.setWebsiteURL("url")
				.build();
			}
			
			public String taskName() {
				return "WebsiteReportDetail Build";
			}
		});
	}
	
	private static void profileServiceReportDetailBuild() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				ServiceReportDetail serviceReportDetail = ServiceReportDetail.newBuilder()
				.setBandwidth(10)
				.setResponseTime(10)
				.setServiceName("service")
				.setStatusCode(10)
				.build();
			}
			
			public String taskName() {
				return "ServiceReportDetail Build";
			}
		});
	}
	
	private static void profileServiceReportBuild() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				ServiceReportDetail serviceReportDetail = ServiceReportDetail.newBuilder()
				.setBandwidth(10)
				.setResponseTime(10)
				.setServiceName("service")
				.setStatusCode(10)
				.build();
				
				Trace trace = Trace.newBuilder()
				.setHop(10)
				.setIp("IP")
				.addPacketsTiming(10)
				.build();
				
				TraceRoute traceRoute = TraceRoute.newBuilder()
				.setHops(10)
				.setPacketSize(10)
				.setTarget("target")
				.addTraces(trace)
				.build();
				
				ICMReport icmReport = ICMReport.newBuilder()
				.setAgentID(10)
				.setReportID(10)
				.setTestID(10)
				.setTimeUTC(10)
				.setTimeZone(10)
				.setTraceroute(traceRoute)
				.addPassedNode("node1")
				.build();
				
				ServiceReport serviceReport = ServiceReport.newBuilder()
				.setHeader(icmReport)
				.setReport(serviceReportDetail)				
				.build();
				}
			
			public String taskName() {
				return "ServiceReport Build";
			}
		});
	}
	
	private static void profileRequestHeader() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(10)
				.setToken("token")
				.build();
			}
			
			public String taskName() {
				return "RequestHeader Build";
			}
		});
	}
	
	private static void profileResponseHeader() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				ResponseHeader responseHeader = ResponseHeader.newBuilder()
				.setCurrentTestVersionNo(10)
				.setCurrentVersionNo(10)
				.build();
			}
			
			public String taskName() {
				return "ResponseHeader Build";
			}
		});
	}
	
	private static void profileRegisterAgent() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				RegisterAgent registerAgent = RegisterAgent.newBuilder()
				.setIp("ip")
				.setVersionNo(10)
				.build();
			}
			
			public String taskName() {
				return "RegisterAgent Build";
			}
		});
	}
	
	private static void profileRegisterAgentResponse() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				ResponseHeader responseHeader = ResponseHeader.newBuilder()
				.setCurrentTestVersionNo(10)
				.setCurrentVersionNo(10)
				.build();
				
				RegisterAgentResponse registerAgentResponse 
				= RegisterAgentResponse.newBuilder()
				.setAgentID(10)
				.setCipheredPublicKey("key")
				.setHeader(responseHeader)
				.setPrivateKey("key")
				.setPublicKey("key")
				.setToken("token")
				.build();
			}
			
			public String taskName() {
				return "RegisterAgentResponse Build";
			}
		});
	}
	
	private static void profileWebsiteOpen() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				try {
					WebsiteOpen.getContent(
							WebsiteOpen.openURLConnection(
									"http://www.google.com"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public String taskName() {
				return "WebsiteOpen";
			}
		});
	}	
	
	private static void profileWebsiteReadWrite() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				Website website = new Website("url", "status", "check");
				try {
					website.writeWebsite();
					Website newWebsite = website.readWebsite("url");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			public String taskName() {
				return "WebsiteReadWrite";
			}
		});
	}	
	
	private static void profileWebsiteListReadWrite() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				Website website1 = new Website("url1","1","1");
		    	Website website2 = new Website("url2","2","2");
		    	List<Website> websiteList = new ArrayList<Website>();
		    	websiteList.add(website1);
		    	websiteList.add(website2);
		    	
		    	try {
					SDCardReadWrite.writeWebsitesList(Constants.WEBSITES_DIR
							, websiteList);
					List<Website> newWebsiteList 
			    	= SDCardReadWrite.readWebsitesList(Constants.WEBSITES_DIR);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
				
			}
			
			public String taskName() {
				return "WebsiteListReadWrite";
			}
		});
	}
	
	private static void profileBase64Encode() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){				
		    	
		    	String testString = "ICMMobileAgent";
		    	String encodedString = new String(Base64.encodeBase64(testString.getBytes()));
		    					
			}
			
			public String taskName() {
				return "Base64Encode";
			}
		});
	}	
	
	private static void profileBase64Decode() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){				
		    	
		    	String testString = "ICMMobileAgent";
		    	String encodedString = new String(Base64.encodeBase64(testString.getBytes()));
		    	String str = new String(Base64.decodeBase64(encodedString.getBytes()));
		    					
			}
			
			public String taskName() {
				return "Base64Decode";
			}
		});
	}	
	
	private static void profileAggrSendServiceSuggestion() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){					
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(10)
				.setToken("token")
				.build();
				
				ServiceSuggestion serviceSuggestion =
					ServiceSuggestion.newBuilder()
					.setEmailAddress("email")
					.setHostName("name")
					.setIp("ip")
					.setServiceName("name")
					.setHeader(requestHeader)
					.build();
		    	try {
					TestSuggestionResponse testSuggestionResponse = 
						AggregatorRetrieve.sendServiceSuggestion(serviceSuggestion);
					String str = Integer.toString(testSuggestionResponse.getHeader().getCurrentVersionNo());
					Log.w(taskName(), str);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		    	
		    					
			}
			
			public String taskName() {
				return "AggrComm SendServiceSuggestion";
			}
		});
	}
	
	private static void profileAggrSendWebsiteSuggestion() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){					
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(10)
				.setToken("token")
				.build();
				
				WebsiteSuggestion websiteSuggestion 
				= WebsiteSuggestion.newBuilder()
				.setEmailAddress("email")
				.setHeader(requestHeader)
				.setWebsiteURL("website")
				.build();
		    	try {
					TestSuggestionResponse testSuggestionResponse = 
						AggregatorRetrieve.sendWebsiteSuggestion(websiteSuggestion);
					String str = Integer.toString(testSuggestionResponse.getHeader().getCurrentVersionNo());
					Log.w(taskName(), str);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		    	
		    					
			}
			
			public String taskName() {
				return "AggrComm SendServiceSuggestion";
			}
		});
	}
	
	private static void profileAggrCheckTests() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){					
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(10)
				.setToken("token")
				.build();
				
				NewTests newTests = NewTests.newBuilder()
				.setHeader(requestHeader)
				.setCurrentTestVersionNo(10)
				.build();
				
				try {
					NewTestsResponse newTestsResponse = 
						AggregatorRetrieve.checkTests(newTests);
					String str = Integer.toString(newTestsResponse.getHeader().getCurrentVersionNo());
					Log.w(taskName(), str);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    					
			}
			
			public String taskName() {
				return "AggrComm NewTests";
			}
		});
	}
	
	private static void profileAggrCheckVersion() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){					
				RequestHeader requestHeader = RequestHeader.newBuilder()
				.setAgentID(10)
				.setToken("token")
				.build();
				
				NewVersion newVersion = NewVersion.newBuilder()
				.setHeader(requestHeader)
				.setAgentType("mobile")
				.setAgentVersionNo(10)
				.build();
				
				try {
					NewVersionResponse newVersionResponse = 
						AggregatorRetrieve.checkVersion(newVersion);
					String str = Integer.toString(newVersionResponse.getHeader().getCurrentVersionNo());
					Log.w(taskName(), str);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    					
			}
			
			public String taskName() {
				return "AggrComm NewVersion";
			}
		});
	}
}