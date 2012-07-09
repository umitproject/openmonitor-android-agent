package org.umit.icm.mobile.throttling;

import java.net.URLConnection;

import org.umit.icm.mobile.connectivity.WebsiteOpen;

public class WebsiteThrottling {
	
	
	public static long TimeTakenToDownload(String website){
		long time_taken=0;
		try{
			URLConnection urlConnection = WebsiteOpen.openURLConnection(website);
			long start_time=System.currentTimeMillis();
			WebsiteOpen.getContent(urlConnection);
			long end_time=System.currentTimeMillis();
			time_taken=end_time-start_time;
		}catch(Exception e){
			e.printStackTrace();
		}
		return time_taken;
	}
	
	public static double Throughput(String website){
		
		double throughput=0;
		try{
		URLConnection urlConnection = WebsiteOpen.openURLConnection(website);
		
		String content=WebsiteOpen.getContent(urlConnection);
		
		long size= content.getBytes().length;
		long time= WebsiteThrottling.TimeTakenToDownload(website);
		
		throughput=size/time;
		}catch(Exception e){
			
		}
		
		return throughput;
	}
	

}
