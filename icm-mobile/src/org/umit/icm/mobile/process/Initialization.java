/**
 * Copyright (C) 2011 Adriano Monteiro Marques
 *
 * Author:  Zubair Nabi <zn.zubairnabi@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either Test 2 of the License, or
 * (at your option) any later Test.
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

package org.umit.icm.mobile.process;

import java.io.IOException;
import java.util.Iterator;

import org.umit.icm.mobile.connectivity.ConnectivityService;
import org.umit.icm.mobile.connectivity.Service;
import org.umit.icm.mobile.connectivity.TCPServer;
import org.umit.icm.mobile.connectivity.Website;
import org.umit.icm.mobile.notifications.NotificationService;
import org.umit.icm.mobile.proto.MessageProtos.RequestHeader;
import org.umit.icm.mobile.utils.Constants;
import org.umit.icm.mobile.utils.ProfilerRun;
import org.umit.icm.mobile.utils.SDCardReadWrite;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Holds initialization methods.
 */

public class Initialization {
	
	/**
	 * Checks for existence of parameter files. If they don't exist,
	 * loads default values from Constants.
	 * 
	 @see		SDCardReadWrite
	 *
	 
	 @see		Constants
	 */
	public static void checkFiles() throws IOException, RuntimeException {
		if ((SDCardReadWrite.fileExists(Constants.INTERVAL_FILE
				, Constants.PARAMETERS_DIR) == false) 
			|| (SDCardReadWrite.fileNotEmpty(Constants.INTERVAL_FILE
		        		, Constants.PARAMETERS_DIR) == false )) {					
			Globals.runtimeParameters.setScanInterval(Constants.DEFAULT_SCAN_INTERVAL);
		}
		if ((SDCardReadWrite.fileExists(Constants.SCAN_FILE
		        		, Constants.PARAMETERS_DIR) == false )
				|| (SDCardReadWrite.fileNotEmpty(Constants.SCAN_FILE
		        		, Constants.PARAMETERS_DIR) == false )) {					
			Globals.runtimeParameters.setScanStatus(Constants.DEFAULT_SCAN_STATUS);					
		}
		if ((SDCardReadWrite.fileExists(Constants.AGENTID_FILE
					, Constants.PARAMETERS_DIR) == false )
				|| (SDCardReadWrite.fileNotEmpty(Constants.AGENTID_FILE
						, Constants.PARAMETERS_DIR) == false )) {					
			Globals.runtimeParameters.setAgentID(Constants.DEFAULT_AGENT_ID);					
		}
		if ((SDCardReadWrite.fileExists(Constants.TOKEN_FILE
				, Constants.PARAMETERS_DIR) == false )
				|| (SDCardReadWrite.fileNotEmpty(Constants.TOKEN_FILE
					, Constants.PARAMETERS_DIR) == false )) {					
			Globals.runtimeParameters.setToken(Constants.DEFAULT_TOKEN);					
		}
		if ((SDCardReadWrite.fileExists(Constants.TWITTER_STATUS_FILE
				, Constants.PARAMETERS_DIR) == false )
				|| (SDCardReadWrite.fileNotEmpty(Constants.TWITTER_STATUS_FILE
					, Constants.PARAMETERS_DIR) == false )) {					
			Globals.runtimeParameters.setTwitter(Constants.DEFAULT_TWITTER_STATUS);					
		} else {
			Globals.twitterUpdate.setAccessToken(
					SDCardReadWrite.readAccessToken(Constants.KEYS_DIR));
		}
		if ((SDCardReadWrite.fileExists(Constants.AGENT_VERSION_FILE
				, Constants.VERSIONS_DIR) == false) 
			|| (SDCardReadWrite.fileNotEmpty(Constants.AGENT_VERSION_FILE
		        		, Constants.VERSIONS_DIR) == false )) {					
			Globals.versionManager.setAgentVersion(Constants.DEFAULT_AGENT_VERSION);
		}
		if ((SDCardReadWrite.fileExists(Constants.TESTS_VERSION_FILE
				, Constants.VERSIONS_DIR) == false) 
			|| (SDCardReadWrite.fileNotEmpty(Constants.TESTS_VERSION_FILE
		        		, Constants.VERSIONS_DIR) == false )) {					
			Globals.versionManager.setTestsVersion(Constants.DEFAULT_TESTS_VERSION);
		}			
	}
	
	/**
	 * Starts the various background Services.
	 * 
	 
	 @see		ConnectivityService
	 *
	 
	 @see		NotificationService
	 *
	 
	 @param	context		Object of type {@link Context}
	 */
	public static void startServices(Context context) {
		context.startService(new Intent(context, ConnectivityService.class));
		context.startService(new Intent(context, NotificationService.class));
	}
	
	public static void checkProfiler() {
		if(Constants.runProfiler == true)
			ProfilerRun.run();
	}
	
	/**
	 * Initializes the {@link Globals#websitesList} with 
	 * {@link Constants#WEBSITE_LIST}
	 * 
	 *	 
	                    
	@see         Website
	 *
	
	@see         Constants
	 */
	public static void intializeWebsitesList() {
		Iterator<String> iterator = Constants.WEBSITE_LIST.iterator();
		while(iterator.hasNext()){               
			Globals.websitesList.add(new Website(iterator.next(), "false", "false"));						       			
        }  
		
	}
	
	/**
	 * Initializes the {@link Globals#servicesList} with 
	 * {@link Constants#SERVICE_LIST}
	 * 
	 *	 
	                    
	@see         Service
	 *
	
	@see         Constants
	 */
	public static void intializeServicesList() {		
		Globals.servicesList = Constants.SERVICE_LIST;				
	}
	
	/**
	 * Initializes the {@link Globals#tcpServer} with 
	 * {@link Constants#MY_TCP_PORT}
	 * 
	 *	 
	                    
	@see         TCPServer
	 */
	public static void intializeTCPServer() throws IOException {		
		Globals.tcpServer = new TCPServer(Constants.MY_TCP_PORT);				
	}
	
	/**
	 * Initializes the {@link Globals#requestHeader} with 
	 * {@link Globals#runtimeParameters}
	 * 
	 *	 
	                    
	@see         RequestHeader
	 *
	
	@see         RuntimeParameters
	 */
	public static void initializeRequestHeader() throws IOException, RuntimeException {
		if ((SDCardReadWrite.fileExists(Constants.TOKEN_FILE
				, Constants.PARAMETERS_DIR) == false )
				|| (SDCardReadWrite.fileNotEmpty(Constants.TOKEN_FILE
					, Constants.PARAMETERS_DIR) == false )) {					
			Globals.runtimeParameters.setToken(Constants.DEFAULT_TOKEN);					
		}
		Globals.requestHeader = RequestHeader.newBuilder()
		.setAgentID(Globals.runtimeParameters.getAgentID())
		.setToken(Globals.runtimeParameters.getToken())
		.build();
	}
	
	public static void initializeIP(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Globals.myIP = wifiInfo.getIpAddress();
	}
	
}