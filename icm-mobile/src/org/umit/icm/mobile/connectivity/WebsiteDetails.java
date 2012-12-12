package org.umit.icm.mobile.connectivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
	
	private Website website;
	private String ip;
	
	private ICMReport icmReport;
	private WebsiteReportDetail websiteReportDetail;
	private WebsiteReport websiteReport;
	private String aDNSRecord[];
	private String nsDNSRecord[];
	private String soaDNSRecord[];
	
	public WebsiteDetails(Website website) {
		this.setWebsite(website);
		this.getWebsite().setContent("");
		this.getWebsite().setTimeTakentoDownload(0);
		this.setIp("");
		this.icmReport = null;
		this.websiteReportDetail = null;
		this.setWebsiteReport(null);
		this.setaDNSRecord(null);
		this.setNsDNSRecord(null);
		this.setSoaDNSRecord(null);
	}
	
	public WebsiteDetails(String websiteURL) {
		this.setWebsite(new Website(websiteURL, "false", "true", "0000", 0, "", 0));
		this.setIp("");
		this.icmReport = null;
		this.websiteReportDetail = null;
		this.setWebsiteReport(null);
		this.setaDNSRecord(null);
		this.setNsDNSRecord(null);
		this.setSoaDNSRecord(null);
	}
	
	public void setup() throws IOException {
		setupDetails();
		setupDNSRecords();
		setupProtobufs();
		checkStatus();
	}
	
	private synchronized void setupDetails() throws IOException {
		if(Constants.DEBUG_MODE)
			System.out.println("Opening URL Connection to this website : " + this.getWebsite().getUrl());
		URLConnection urlConnection = WebsiteOpen.openURLConnection(this.getWebsite().getUrl());	
		Map<String,String> header = WebsiteOpen.getHeaders(urlConnection);
		this.getWebsite().setStatus(String.valueOf(WebsiteOpen.getStatusCode(header)));
		long startFetchTime = System.currentTimeMillis();
		this.getWebsite().setContent(WebsiteOpen.getContent(urlConnection));
		this.getWebsite().setTimeTakentoDownload(System.currentTimeMillis() - startFetchTime);
   	}
	
	private synchronized void setupDNSRecords() {
		String url = this.getWebsite().getUrl().split("/+")[1];
		try {
			this.setIp(DNSLookup.getIPString(url));
			this.setNsDNSRecord(DNSLookup.getDNSRecordNSString(url.substring(4)));
			this.setaDNSRecord(DNSLookup.getDNSRecordAString(url.substring(4)));
			this.setSoaDNSRecord(DNSLookup.getDNSRecordSOAString(url.substring(4)));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TextParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private synchronized void setupProtobufs() throws IOException {
		Trace trace = Trace.newBuilder()
				.setHop(1)
				.setIp("255.255.255.0")		//TODO: fix
				.addPacketsTiming(1)
				.build();
		
		String ip = "255.255.255.0";
		InetAddress address = InetAddress.getByName(new URL(this.getWebsite().getUrl()).getHost());
		ip = address.getHostAddress();

		TraceRoute traceRoute = TraceRoute.newBuilder()
				.setTarget(ip)
				.setHops(1)
				.setPacketSize(1)
				.addTraces(trace)
				.build();
		
		double throughput = (this.getWebsite().getContent().getBytes().length / this.getWebsite().getTimeTakentoDownload());
		this.websiteReportDetail = WebsiteReportDetail.newBuilder()
				.setBandwidth((int)throughput)
				.setResponseTime((int)this.getWebsite().getTimeTakentoDownload())
				.setStatusCode(Integer.parseInt(this.getWebsite().getStatus()))
				.setHtmlResponse(this.getWebsite().getContent())
				.setWebsiteURL(this.getWebsite().getUrl())		
				.build();
		
		List<String> listNodes = new ArrayList<String>();
		listNodes.add(Globals.runtimeParameters.getAgentID());
		Calendar calendar = Calendar.getInstance();
		long timeUTC = (calendar.getTimeInMillis()/1000);
			
		this.icmReport = ICMReport.newBuilder()
				.setAgentID(Globals.runtimeParameters.getAgentID())
				.setTestID(getWebsite().getTestID())
				.setTimeZone(Calendar.ZONE_OFFSET)
				.setTimeUTC(timeUTC)
				.addAllPassedNode(listNodes)
				.setTraceroute(traceRoute)
				.build();
		
		this.setWebsiteReport(WebsiteReport.newBuilder()		
				.setReport(websiteReportDetail)
				.setHeader(icmReport)	
				.build());
	}
	
	private void checkStatus() {
		if(!this.getWebsite().getStatus().equalsIgnoreCase("200")) {
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
			
			Globals.runtimeList.addEvent(event);
		}
	}

	public String getIp() {
		return ip;
	}

	private void setIp(String ip) {
		this.ip = ip;
	}

	public Website getWebsite() {
		return website;
	}

	private void setWebsite(Website website) {
		this.website = website;
	}

	public String[] getNsDNSRecord() {
		return nsDNSRecord;
	}

	private void setNsDNSRecord(String nsDNSRecord[]) {
		this.nsDNSRecord = nsDNSRecord;
	}

	public String[] getaDNSRecord() {
		return aDNSRecord;
	}

	private void setaDNSRecord(String aDNSRecord[]) {
		this.aDNSRecord = aDNSRecord;
	}

	public String[] getSoaDNSRecord() {
		return soaDNSRecord;
	}

	private void setSoaDNSRecord(String soaDNSRecord[]) {
		this.soaDNSRecord = soaDNSRecord;
	}

	public WebsiteReport getWebsiteReport() {
		return websiteReport;
	}

	private void setWebsiteReport(WebsiteReport websiteReport) {
		this.websiteReport = websiteReport;
	}
}