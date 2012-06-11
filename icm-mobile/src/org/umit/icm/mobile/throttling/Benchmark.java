package org.umit.icm.mobile.throttling;

public class Benchmark {
	public String[] Benchmarks={
			"www.google.co.in",
			"www.facebook.com",
			"in.yahoo.com",
			"www.youtube.com"
			};
	
	public double Average(){
		
		long sum=0;
		for(int i=0;i<Benchmarks.length;i++){
			sum+=Website.TimeTakenToDownload(Benchmarks[i]);
		}
		
		long average=sum/Benchmarks.length;
		
		return average;
	}
	
	

}
