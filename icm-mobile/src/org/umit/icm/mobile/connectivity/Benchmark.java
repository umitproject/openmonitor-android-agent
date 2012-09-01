package org.umit.icm.mobile.connectivity;

import java.util.Iterator;

import org.umit.icm.mobile.process.Globals;

public class Benchmark {
	
	static Iterator<Website> websiteList = Globals.websitesList.iterator();
	
	public static double DeviationFromAverageThroughput(String website) {
		
		double throughput = WebsiteThrottling.Throughput(website);	
		double averageThroughput = Globals.runtimeParameters.getAverageThroughput();
		return Math.abs(averageThroughput - throughput);
	
	}

}
