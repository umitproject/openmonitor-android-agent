package org.umit.icm.mobile.connectivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.Event;
import org.umit.icm.mobile.proto.MessageProtos.ICMReport;
import org.umit.icm.mobile.proto.MessageProtos.Location;
import org.umit.icm.mobile.proto.MessageProtos.Trace;
import org.umit.icm.mobile.proto.MessageProtos.TraceRoute;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReport;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReportDetail;
import org.xbill.DNS.TextParseException;

public class WebsiteDetails {
	
	Website website;
	URLConnection urlConnection;
	double throughput;
	Map<String,String> header;
	
	Trace trace;
	TraceRoute traceRoute;
	ICMReport icmReport;
	WebsiteReportDetail websiteReportDetail;
	public WebsiteReport websiteReport;
	String ip;
	String aDNSRecord[];
	String nsDNSRecord[];
	String soaDNSRecord[];
	
	public WebsiteDetails(Website website) throws Exception{
		this.website = website;
		this.urlConnection = null;
		this.website.setContent("");
		this.website.setTimeTakentoDownload(0);
		this.trace = null;
		this.traceRoute = null;
		this.icmReport = null;
		this.websiteReportDetail = null;
		this.websiteReport = null;
		ip = "";
		aDNSRecord = null;
		nsDNSRecord = null;
		soaDNSRecord = null;
		setup();
	}
	
	public WebsiteDetails(String websiteURL) throws Exception{
		this.website = new Website(websiteURL, "false", "true", "0000", 0, "", 0);
		this.urlConnection = null;
		this.trace = null;
		this.traceRoute = null;
		this.icmReport = null;
		this.websiteReportDetail = null;
		this.websiteReport = null;
		ip = "";
		aDNSRecord = null;
		nsDNSRecord = null;
		soaDNSRecord = null;
		setup();
	}
	
	public void setup() throws Exception{
		try {
			setupVariables();
			setupProtobufs();
			checkStatus();
		} catch (Exception e) {
			throw new Exception("Exception while setting up variables");
		}
	}
	
	private synchronized void setupVariables() throws IOException, HttpException{
		setupURLConnection();
		setupHeaders();
		setupStatus();
		setupFetchTimeContentThroughput();
		setupDNSRecords();
	}
	
	private synchronized void setupURLConnection() throws IOException, HttpException{
		if(Constants.DEBUG_MODE)
			System.out.println("Opening URL Connection to this website : " + this.website.getUrl());
		this.urlConnection = WebsiteOpen.openURLConnection(this.website.getUrl());		
	}
	
	private synchronized void setupHeaders() throws IOException, HttpException {
		this.header = WebsiteOpen.getHeaders(this.urlConnection);
   	}
	
	private synchronized void setupStatus(){
		this.website.setStatus(String.valueOf(WebsiteOpen.getStatusCode(this.header)));
	}
	
	private synchronized void setupFetchTimeContentThroughput() throws IOException, HttpException{
		long startFetchTime = System.currentTimeMillis();
		this.website.setContent(WebsiteOpen.getContent(this.urlConnection));
		this.website.setTimeTakentoDownload(System.currentTimeMillis() - startFetchTime);
		this.throughput = (this.website.getContent().getBytes().length / this.website.getTimeTakentoDownload()) ;
	}
	
	private synchronized void setupDNSRecords(){
		String url = this.website.getUrl().split("/+")[1];
		try {
			this.ip = DNSLookup.getIPString(url);
			this.nsDNSRecord = DNSLookup.getDNSRecordNSString(url.substring(4));
			this.aDNSRecord = DNSLookup.getDNSRecordAString(url.substring(4));
			this.soaDNSRecord = DNSLookup.getDNSRecordSOAString(url.substring(4));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TextParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private synchronized void setupProtobufs(){
		
		this.trace = Trace.newBuilder()
				.setHop(1)
				.setIp("255.255.255.0")		//TODO: fix
				.addPacketsTiming(1)
				.build();
		
		String ip = "255.255.255.0";
		InetAddress address;
		try {
			address = InetAddress.getByName(new URL(this.website.getUrl()).getHost());
			ip = address.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.traceRoute = TraceRoute.newBuilder()
				.setTarget(ip)
				.setHops(1)
				.setPacketSize(1)
				.addTraces(this.trace)
				.build();
		
		this.websiteReportDetail = WebsiteReportDetail.newBuilder()
				.setBandwidth((int)this.throughput)
				.setResponseTime((int)this.website.getTimeTakentoDownload())
				.setStatusCode(Integer.parseInt(this.website.getStatus()))
				.setHtmlResponse(this.website.getContent())
				.setWebsiteURL(this.website.getUrl())		
				.build();
		
		List<String> listNodes = new ArrayList<String>();
		listNodes.add(Globals.runtimeParameters.getAgentID());
		Calendar calendar = Calendar.getInstance();
		long timeUTC = (calendar.getTimeInMillis()/1000);
			
		this.icmReport = ICMReport.newBuilder()
				.setAgentID(Globals.runtimeParameters.getAgentID())
				.setTestID(website.getTestID())
				.setTimeZone(Calendar.ZONE_OFFSET)
				.setTimeUTC(timeUTC)
				.addAllPassedNode(listNodes)
				.setTraceroute(this.traceRoute)
				.build();
		
		this.websiteReport = WebsiteReport.newBuilder()		
				.setReport(websiteReportDetail)
				.setHeader(icmReport)	
				.build();
		
	}
	
	private void checkStatus() {
		if(!this.website.getStatus().equalsIgnoreCase("200")) {
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
				.setTimeUTC(this.icmReport.getTimeUTC())
				.setSinceTimeUTC(this.icmReport.getTimeUTC())
				.setWebsiteReport(this.websiteReportDetail)
				.addLocations(location)			
				.build();
			
			Globals.runtimesList.addEvent(event);
		}
	}
}