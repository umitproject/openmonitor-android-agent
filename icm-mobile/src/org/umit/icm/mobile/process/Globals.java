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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.umit.icm.mobile.connectivity.Service;
import org.umit.icm.mobile.connectivity.ServiceConnectivity;
import org.umit.icm.mobile.connectivity.TCPClient;
import org.umit.icm.mobile.connectivity.TCPServer;
import org.umit.icm.mobile.connectivity.Website;
import org.umit.icm.mobile.connectivity.WebsiteConnectivity;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.proto.MessageProtos.Event;
import org.umit.icm.mobile.proto.MessageProtos.RequestHeader;
import org.umit.icm.mobile.proto.MessageProtos.Test;
import org.umit.icm.mobile.social.TwitterUpdate;
import org.umit.icm.mobile.utils.Constants;
import org.umit.icm.mobile.utils.SDCardReadWrite;

/**
 * Holds application wide Globals.
 */

public class Globals {
	/**
	 * Global {@link RuntimeParameters}
	 */
	public static RuntimeParameters runtimeParameters 
	= new RuntimeParameters();
	
	/**
	 * Global {@link TwitterUpdate}
	 */
	public static TwitterUpdate twitterUpdate 
	= new TwitterUpdate();		
	
	/**
	 * Global {@link VersionManager}
	 */
	public static VersionManager versionManager 
	= new VersionManager();
	
	/**
	 * Global {@link KeyManager}
	 */
	public static KeyManager keyManager
	= new KeyManager();
	
	/**
	 * Global {@link WebsiteConnectivity}
	 */
	public static WebsiteConnectivity websiteTest 
	= new WebsiteConnectivity();
	
	/**
	 * Global {@link RequestHeader}
	 */
	public static RequestHeader requestHeader;
	
	/**
	 * Global {@link String} to hold scan status
	 */
	public static String scanStatus = " ";
	
	/**
	 * Global {@link List} of {@link Website}
	 */
	public static List<Website> websitesList = new ArrayList<Website>();
	
	/**
	 * Global {@link List} of {@link Service}
	 */
	public static List<Service> servicesList = new ArrayList<Service>();
	
	/**
	 * Global {@link List} of {@link Event}
	 */
	public static List<Event> eventsList = new ArrayList<Event>();
	
	/**
	 * Global {@link List} of {@link AgentData}
	 */
	public static List<AgentData> peersList = new ArrayList<AgentData>();
	
	/**
	 * Global {@link List} of {@link AgentData}
	 */
	public static List<AgentData> superPeersList = new ArrayList<AgentData>();
	
	/**
	 * Global {@link List} of {@link Test}
	 */
	public static List<Test> testsList = new ArrayList<Test>();
	
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
		servicesList = Constants.SERVICE_LIST;				
	}
	
	/**
	 * Global {@link TCPServer}
	 */
	public static TCPServer tcpServer;
	
	/**
	 * Initializes the {@link Globals#tcpServer} with 
	 * {@link Constants#MY_TCP_PORT}
	 * 
	 *	 
	                    
	@see         TCPServer
	 */
	public static void intializeTCPServer() throws IOException {		
		tcpServer = new TCPServer(Constants.MY_TCP_PORT);				
	}
	
	/**
	 * Global {@link TCPClient}
	 */
	public static TCPClient tcpClient
	= new TCPClient();
	
	/**
	 * Global {@link TCPClient} used for Connectivity Tests
	 */
	public static TCPClient tcpClientConnectivity
	= new TCPClient();
	
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
	
	/**
	 * Global {@link String} to select map view
	 */
	public static String mapView = "Google";
	
	/**
	 * Global {@link ServiceConnectivity}
	 */
	public static ServiceConnectivity serviceTest 
	= new ServiceConnectivity();
	
	public static Map<String, String> servicePacketsMap 
	= new HashMap <String, String>();
}