package org.umit.icm.mobile.throttling;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;

public class WebsiteThrottling {
	
	public static String Download(String website){
	
		String webpage="";
		try{
			URL url=new URL(website);
			InputStream is=url.openStream();
			DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
			String line;
			webpage = "";
		    while ((line = dis.readLine()) != null) {
		        webpage+=line;
		    }
		    
			return webpage;
		}catch(Exception e){
			System.err.println(e.toString());
			e.printStackTrace();
			return null;
		}

	}
	
	public static long TimeTakenToDownload(String website){
		long time_taken;
		long start_time=System.nanoTime();
		Download(website);
		long end_time=System.nanoTime();
		time_taken=end_time-start_time;
		return time_taken;
	}
	
	public static double Throughput(String website){
		
		double throughput;
		
		String content=WebsiteThrottling.Download(website);
		long size= content.getBytes().length;
		
		long time= WebsiteThrottling.TimeTakenToDownload(website);
		
		throughput=size/time;
		
		return throughput;
	}
	

}
