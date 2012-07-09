package org.umit.icm.mobile.throttling;

import java.net.URLConnection;
import java.util.Iterator;

import org.umit.icm.mobile.connectivity.Website;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;

public class Benchmark {
	
	static Iterator<Website> websiteList = Globals.websitesList.iterator();
	
	
	
	public static double DeviationFromAverageThroughput(String website){
		
		double throughput=WebsiteThrottling.Throughput(website);
		
		double averageThroughput= Globals.runtimeParameters.getAverageThroughput();
		
		double deviation = Math.abs(averageThroughput-throughput);
		
		return deviation;
		
	}

}
