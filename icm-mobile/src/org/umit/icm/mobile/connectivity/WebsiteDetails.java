package org.umit.icm.mobile.connectivity;

import java.io.IOException;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.process.IDGenerator;
import org.umit.icm.mobile.proto.MessageProtos.Event;
import org.umit.icm.mobile.proto.MessageProtos.ICMReport;
import org.umit.icm.mobile.proto.MessageProtos.Location;
import org.umit.icm.mobile.proto.MessageProtos.Trace;
import org.umit.icm.mobile.proto.MessageProtos.TraceRoute;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReport;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteReportDetail;
import org.umit.icm.mobile.utils.CopyNative;

public class WebsiteDetails {
	
	String website;
	URLConnection urlConnection;
	String content;
	int status;
	long fetchTime;
	double throughput;
	Map<String,String> header;
	
	
	Trace trace;
	TraceRoute traceRoute;
	ICMReport icmReport;
	WebsiteReportDetail websiteReportDetail;
	WebsiteReport websiteReport;
	
	
	
	public WebsiteDetails(String website){
		this.website = website;
		this.urlConnection=null;
		this.content="";
		this.fetchTime=0;
		this.trace=null;
		this.traceRoute=null;
		this.icmReport=null;
		this.websiteReportDetail=null;
		this.websiteReport=null;
		setup();
	}
	
	public void setup(){
		setupVariables();
		setupProtobufs();
		checkStatus();
	}
	
	private synchronized void setupVariables(){
		try{
			//URLConnection urlConnection = WebsiteOpen.openURLConnection(this.website);
			
			setupURLConnection();
			setupHeaders();
			setupStatus();
			setupFetchTimeContentThroughput();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private synchronized void setupFetchTimeContentThroughput(){
		try{
			long startFetchTime = System.currentTimeMillis();
			this.content = WebsiteOpen.getContent(this.urlConnection);
			this.fetchTime = System.currentTimeMillis()-startFetchTime;
			this.throughput = (this.content.getBytes().length /this.fetchTime) ;
		}catch(Exception e){
			
		}
		
		
	}
	
	
	private synchronized void setupURLConnection(){
		
		try {
			this.urlConnection=WebsiteOpen.openURLConnection(this.website);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private synchronized void setupHeaders() {
		
		try {
			this.header=WebsiteOpen.getHeaders(this.urlConnection);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

   	}
	
	private synchronized void setupStatus(){
		this.status= WebsiteOpen.getStatusCode(this.header);
	}
	
	private synchronized void setupProtobufs(){
		
		this.trace = Trace.newBuilder()
				.setHop(1)
				.setIp(CopyNative.traceRoute(this.website.substring(7)))		
				.addPacketsTiming(1)
				.build();
		
		this.traceRoute=TraceRoute.newBuilder()
				.setTarget(this.website)
				.setHops(1)
				.setPacketSize(1)
				.addTraces(this.trace)
				.build();
		
		this.websiteReportDetail = WebsiteReportDetail.newBuilder()
				.setBandwidth((int)this.throughput)
				.setResponseTime((int)this.fetchTime)
				.setStatusCode(this.status)
				.setHtmlResponse(this.content)
				.setWebsiteURL(this.website)		
				.build();
		
		List<String> listNodes = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		listNodes.add(Globals.runtimeParameters.getAgentID());
		long timeUTC = (calendar.getTimeInMillis()/1000);
		
		
		try {
			this.icmReport = ICMReport.newBuilder()
			.setReportID(IDGenerator.generateReportID(Globals.runtimeParameters.getAgentID(), timeUTC, "0"))
			.setAgentID(Globals.runtimeParameters.getAgentID())
			.setTestID(Integer.toString(0))
			.setTimeZone(Calendar.ZONE_OFFSET)
			.setTimeUTC(timeUTC)
			.addAllPassedNode(listNodes)
			.setTraceroute(this.traceRoute)
			.build();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.websiteReport = WebsiteReport.newBuilder()		
				.setReport(websiteReportDetail)
				.setHeader(icmReport)	
				.build();
		
		
	}
	
	private void checkStatus() {
		if(this.status != 200) {
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