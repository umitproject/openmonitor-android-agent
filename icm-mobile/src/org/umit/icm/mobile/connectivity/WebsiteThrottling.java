package org.umit.icm.mobile.connectivity;

import java.net.URLConnection;


public class WebsiteThrottling {
	
	public static long TimeTakenToDownload(String website) {
		
		long timeTaken = 0;
		try {
			URLConnection urlConnection = WebsiteOpen.openURLConnection(website);
			long startTime = System.currentTimeMillis();
			WebsiteOpen.getContent(urlConnection);
			long endTime = System.currentTimeMillis();
			timeTaken = endTime - startTime;
		} catch(Exception e){
			e.printStackTrace();
		}
		return timeTaken;
	}
	
	public static double Throughput(String website){
	
		double throughput = 0;
		try{
			URLConnection urlConnection = WebsiteOpen.openURLConnection(website);
			String content = WebsiteOpen.getContent(urlConnection);
			long size = content.getBytes().length;
			long time = WebsiteThrottling.TimeTakenToDownload(website);		
			throughput = size/time;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return throughput;
	}	

}
