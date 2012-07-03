package org.umit.icm.mobile.throttling;

import java.net.URLConnection;
import java.util.Iterator;

import org.umit.icm.mobile.connectivity.Website;
import org.umit.icm.mobile.process.Globals;

public class Benchmark {
	
	static Iterator<Website> websiteList = Globals.websitesList.iterator();
	
	public static double AverageThroughput(){
		
		double average=0;
	
		Website website = new Website();
		String websiteURL = new String();
		long totalSizeofContent=0;
		long totalTime=0;
		long sizeContent=0;
		long time=0;
		String content= new String();
		
		while(websiteList.hasNext()){
			
			website = websiteList.next();
			websiteURL = website.getUrl();
			
			content=WebsiteThrottling.Download(websiteURL);
			sizeContent=content.getBytes().length;
			totalSizeofContent += sizeContent;
			
			time=WebsiteThrottling.TimeTakenToDownload(websiteURL);
			totalTime += time;
			
		}
		average = totalSizeofContent/totalTime;
		
		
		return average;
		
		
	}
	
	public static double DeviationFromAverageThroughput(String website){
		
		double throughput=WebsiteThrottling.Throughput(website);
		
		double averageThroughput= AverageThroughput();
		
		double deviation = Math.abs(averageThroughput-throughput);
		
		return deviation;
		
	}

}
