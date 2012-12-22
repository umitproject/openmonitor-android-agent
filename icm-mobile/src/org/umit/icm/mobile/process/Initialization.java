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
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Random;

import org.umit.icm.mobile.aggregator.AggregatorRetrieve;
import org.umit.icm.mobile.connectivity.ConnectivityService;
import org.umit.icm.mobile.connectivity.TCPServer;
import org.umit.icm.mobile.notifications.NotificationService;
import org.umit.icm.mobile.proto.MessageProtos.Event;
import org.umit.icm.mobile.proto.MessageProtos.GetBanlist;
import org.umit.icm.mobile.proto.MessageProtos.GetBannets;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerList;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerList;
import org.umit.icm.mobile.proto.MessageProtos.Location;
import org.umit.icm.mobile.proto.MessageProtos.Login;
import org.umit.icm.mobile.proto.MessageProtos.LoginCredentials;
import org.umit.icm.mobile.proto.MessageProtos.NewTests;
import org.umit.icm.mobile.proto.MessageProtos.RSAKey;
import org.umit.icm.mobile.proto.MessageProtos.RegisterAgent;
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
			Globals.runtimeParameters.setAgentID(Integer.toString(10));
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
		}
		if ((SDCardReadWrite.fileExists(Constants.ACCESS_TOKEN_FILE
				, Constants.KEYS_DIR) == true )
				&& (SDCardReadWrite.fileNotEmpty(Constants.ACCESS_TOKEN_FILE
					, Constants.KEYS_DIR) == false )) {					
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
//		context.startService(new Intent(context, NotificationService.class));
		context.startService(new Intent(context, CommunicationService.class));		
	}
	
	public static void checkProfiler() {
		if(Constants.RUN_PROFILER == true)
			ProfilerRun.run();
	}

	private static void initializeTests() {
		try {
			if ((SDCardReadWrite.fileExists(Constants.WEBSITES_LIST_FILE, 
					Constants.WEBSITES_DIR) == true) &&
					(SDCardReadWrite.fileExists(Constants.SERVICES_LIST_FILE, 
							Constants.SERVICES_DIR) == true)){	
				Globals.runtimeList.websitesList 
				= SDCardReadWrite.readWebsitesList(Constants.WEBSITES_DIR);	

				Globals.runtimeList.servicesList 
				= SDCardReadWrite.readServicesList(Constants.SERVICES_DIR);	
				
			// No tests on SD Card
			} else {
				Globals.runtimeList.websitesList = Constants.WEBSITE_LIST;	
				Globals.runtimeList.servicesList = Constants.SERVICE_LIST;	
				NewTests newTests = NewTests.newBuilder()
						.setCurrentTestVersionNo(Globals.versionManager.getTestsVersion())
						.build();

				try {
					AggregatorRetrieve.checkTests(newTests);
					SDCardReadWrite.writeServicesList(Constants.SERVICES_DIR,
								Globals.runtimeList.servicesList);
					SDCardReadWrite.writeWebsitesList(Constants.WEBSITES_DIR,
								Globals.runtimeList.websitesList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void initializeBanlist(){
		GetBanlist getBanlist = GetBanlist.newBuilder()
				.setCount(100)
				.build();
		try {
			AggregatorRetrieve.getBanlist(getBanlist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static void initializeBannets(){
		GetBannets getBannets = GetBannets.newBuilder()
				.setCount(100)
				.build();
		try {
			AggregatorRetrieve.getBannets(getBannets);
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void intializeLists() {
		initializeTests();
		initializeBanlist();
		initializeBannets();
		Globals.runtimeList.readEventList();
		Globals.runtimeList.readPeerList();
		Globals.runtimeList.readSuperPeerList();
		//initializerPeersList();
		initializeEventsList();
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
	
	/** Initializes the global IP.
	 * 
	 */
	public static void initializeIP(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Globals.myIP = Integer.toString(wifiInfo.getIpAddress());
	}
	
	/*Only used for testing
	 * Should be deprecated eventually.
	 */
	private static void initializeEventsList() {
		Calendar calendar = Calendar.getInstance();
		
		Location location = Location.newBuilder()
		.setLatitude(0)
		.setLongitude(0)
		.build();
		
		Event eventA = Event.newBuilder()
		.setTestType("WEB")
		.setEventType("CENSOR")
		.setTimeUTC(calendar.getTimeInMillis())
		.setSinceTimeUTC(calendar.getTimeInMillis())
		.addLocations(location)
		.build();
		
		Event eventB = Event.newBuilder()
		.setTestType("SERVICE")
		.setEventType("CENSOR")
		.setTimeUTC(calendar.getTimeInMillis())
		.setSinceTimeUTC(calendar.getTimeInMillis())
		.addLocations(location)
		.build();
		
		Globals.runtimeList.addEvent(eventA);
		Globals.runtimeList.addEvent(eventB);
	}	
	
	/*Only used for testing
	 * Should be deprecated eventually.
	 */
	private static void initializerPeersList() {
		
		GetPeerList getPeerList = GetPeerList.newBuilder()
				.setCount(10)
				.build();
		
		GetSuperPeerList getSuperPeerList = GetSuperPeerList.newBuilder()
				.setCount(10)
				.build();
		
		try {
			AggregatorRetrieve.getPeerList(getPeerList);
			AggregatorRetrieve.getSuperPeerList(getSuperPeerList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public static boolean login() {
		Random random = new Random();
		String challenge = Double.toString(random.nextDouble());
		Globals.challenge = challenge;
		
		if(Constants.DEBUG_MODE) {
			System.out.println("Setting the login protobuf");
			System.out.println("THIS IS THE AGENT ID BEING SEND : " +Globals.runtimeParameters.getAgentID());
		}
		
		Login login = Login.newBuilder()
		.setAgentID(Globals.runtimeParameters.getAgentID())
		.setPort(80)
		.setChallenge(Globals.challenge)
		.setIp(Globals.myIP)
		.build();
		
		if(Constants.DEBUG_MODE) 
			System.out.println("Login protobuf formed : "  + login.toString());
				
		boolean success = false;
		try {
			success = AggregatorRetrieve.login(login);
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Globals.isLoggedIn = success;
		return success;
	}
	
	public static boolean registration(LoginCredentials loginCredentials) {
		boolean success = false;
			
		try {	
			if(Constants.DEBUG_MODE) {
				System.out.println("This is from inside Initialization#registration");
				System.out.println("MY MOD : " + Globals.keyManager.getMyCipheredKeyMod());
				System.out.println("MY EXP : " + Globals.keyManager.getMyCipheredKeyExp());
			}
			
			BigInteger BiMod = new BigInteger(Globals.keyManager.getMyCipheredKeyMod());
			BigInteger BiExp = new BigInteger(Globals.keyManager.getMyCipheredKeyExp());
			
			if(Constants.DEBUG_MODE) {
				System.out.println("MY HEX MOD : " + BiMod.toString(16));
				System.out.println("MY HEX EXP : " + BiExp.toString(10));
			}
						
			RSAKey rsaKey = RSAKey.newBuilder()
			.setMod(BiMod.toString(16))
			.setExp(BiExp.toString(10))
			.build();
			
			Globals.versionManager.setTestsVersion(1);
			
			RegisterAgent registerAgent = RegisterAgent.newBuilder()
			.setAgentType(Constants.AGENT_TYPE)
			.setCredentials(loginCredentials)
			.setIp(Globals.myIP)
			.setAgentPublicKey(rsaKey)
			.setVersionNo(Globals.versionManager.getTestsVersion())
			.build();		
			
			success = AggregatorRetrieve.registerAgent(registerAgent);				  
		  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Globals.isRegistered = success;
		return success;
	}
	
}		