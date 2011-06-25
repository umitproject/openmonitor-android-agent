/**
 * Copyright (C) 2011 Adriano Monteiro Marques
 *
 * Author:  Zubair Nabi <zn.zubairnabi@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package org.umit.icm.mobile.utils;


import java.security.KeyPair;

import org.umit.icm.mobile.proto.MessageProtos.*;

public class ProfilerRun {
	
	
	
	public static void run() {	
		profileTraceBuild();
		profileTraceRouteBuild();
		profileICMReport();
		profileRSAGenerateKey();
	}
		
	private static void profileTraceBuild (){
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				Trace trace = Trace.newBuilder()
				.setHop(10)
				.setIp("IP")
				.addPacketsTiming(10)
				.build();
			}
			
			public String taskName() {
				return "Trace Build";
			}
		});
	}
	
	private static void profileTraceRouteBuild (){
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				Trace trace = Trace.newBuilder()
				.setHop(10)
				.setIp("IP")
				.addPacketsTiming(10)
				.build();
				
				TraceRoute traceRoute = TraceRoute.newBuilder()
				.setHops(10)
				.setPacketSize(10)
				.setTarget("target")
				.addTraces(trace)
				.build();
			}
			
			public String taskName() {
				return "TraceRoute Build";
			}
		});
	}
	
	private static void profileICMReport() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				Trace trace = Trace.newBuilder()
				.setHop(10)
				.setIp("IP")
				.addPacketsTiming(10)
				.build();
				
				TraceRoute traceRoute = TraceRoute.newBuilder()
				.setHops(10)
				.setPacketSize(10)
				.setTarget("target")
				.addTraces(trace)
				.build();
				
				ICMReport icmReport = ICMReport.newBuilder()
				.setAgentID(10)
				.setReportID(10)
				.setTestID(10)
				.setTimeUTC(10)
				.setTimeZone(10)
				.setTraceroute(traceRoute)
				.addPassedNode("node1")
				.build();
			}
			
			public String taskName() {
				return "ICMReport Build";
			}
		});
	}
	
	private static void profileRSAGenerateKey() {
		Profiler profiler = new Profiler();
		profiler.runProfiler(new TaskInterface () {
			public void task (){
				try {
					KeyPair keyPair = RSACrypto.generateKey();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public String taskName() {
				return "RSA KeyPair Generate";
			}
		});
	}
}