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
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.umit.icm.mobile.aggregator.AggregatorRetrieve;
import org.umit.icm.mobile.connectivity.Service;
import org.umit.icm.mobile.connectivity.Website;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.proto.MessageProtos.Event;
import org.umit.icm.mobile.proto.MessageProtos.NewTests;
import org.umit.icm.mobile.proto.MessageProtos.NewVersion;
import org.umit.icm.mobile.proto.MessageProtos.NewVersionResponse;
import org.umit.icm.mobile.proto.MessageProtos.RegisterAgentResponse;
import org.umit.icm.mobile.proto.MessageProtos.ResponseHeader;
import org.umit.icm.mobile.proto.MessageProtos.Test;
import org.umit.icm.mobile.utils.RSACrypto;

/**
 * Performs actions based on aggregator and P2P communication responses.
 */

public class ProcessActions {	
	/**
	 * Checks if the response agent version is higher than the current
	 * agent number. If yes, updates the agent version and makes a webservice
	 * call to the aggregator to be the latest version.
	 * 
	 *
	 
	 @param header Response header of type {@link ResponseHeader}
	 *
	 
	 @see VersionManager
	 */
	public static void updateAgentVersion(ResponseHeader header) 
		throws IOException {
		
		if (header.getCurrentVersionNo() 
				> Globals.versionManager.getAgentVersion()) {
			Globals.versionManager.setAgentVersion(header.getCurrentVersionNo());
			NewVersion newVersion = NewVersion.newBuilder()
			.setHeader(Globals.requestHeader)
			.setAgentVersionNo(Globals.versionManager.getAgentVersion())
			.setAgentType(Constants.AGENT_TYPE)
			.build();
			AggregatorRetrieve.checkVersion(newVersion);
		}
	}
	
	/**
	 * Checks if the response tests version is higher than the current
	 * tests number. If yes, updates the tests version and makes a webservice
	 * call to the aggregator to get the latest tests version.
	 * 
	 *
	 
	 @param header Response header of type {@link ResponseHeader}
	 *
	 
	 @see VersionManager
	 */
	public static void updateTestsVersion(ResponseHeader header)
		throws IOException {
		
		if (header.getCurrentTestVersionNo() 
				> Globals.versionManager.getTestsVersion()) {
			Globals.versionManager.setTestsVersion(header.getCurrentTestVersionNo());
			NewTests newTests = NewTests.newBuilder()
			.setHeader(Globals.requestHeader)
			.setCurrentTestVersionNo(Globals.versionManager.getTestsVersion())
			.build();
			AggregatorRetrieve.checkTests(newTests);
		}
	}
			
	/**
	 * Patches the current agent
	 * 
	 *
	 
	 @param newVersionResponse Response header of type {@link NewVersionResponse}
	 */
	public static boolean updateAgent(NewVersionResponse newVersionResponse) {
		// TODO patch current binary
		return true;
	}
	
	/**
	 * Adds the new tests to {@link Globals#testsList}.
	 * 
	 *
	 
	 @param tests	{@link List} of {@link Test}
	 *
	 
	 @return boolean
	 *
	 
	 @see Test
	 */
	public synchronized static boolean updateTests(List<Test> tests) {
		List<Integer> ports = new ArrayList<Integer>();
		for(int i = 0 ; i < tests.size(); i++) {
			if(tests.get(i).getTestType().equals("WEB")) {
				Globals.websitesList.add(
						new Website(tests.get(i).getWebsite().getUrl(), 
								"false", 
								"true", 
								tests.get(i).getTestID(), 
								tests.get(i).getExecuteAtTimeUTC()));
			} else if(tests.get(i).getTestType().equals("SERVICE")) {
				ports.clear();
				ports.add(tests.get(i).getService().getPort());
				Globals.servicesList.add(
						new Service(tests.get(i).getService().getName(), 
								ports,
								tests.get(i).getService().getIp(), 
								"open", 
								"true", 
								tests.get(i).getTestID(), 
								tests.get(i).getExecuteAtTimeUTC()));
			}
			
		}			
		return true;
	}
	
	/**
	 * Adds new events to {@link Globals#eventsList}.
	 * 
	 *
	 
	 @param events	{@link List} of {@link Event}
	 *
	 
	 @return boolean
	 *
	 
	 @see Event
	 */
	public synchronized static boolean updateEventsList(List<Event> events) {
		for(int i = 0 ; i < events.size(); i++)
			Globals.eventsList.add(events.get(i));
		return true;
	}
	
	/**
	 * Adds new peers to {@link Globals#peersList}.
	 * 
	 *
	 
	 @param peers	{@link List} of {@link AgentData}
	 *
	 
	 @return boolean
	 *
	 
	 @see AgentData
	 */
	public synchronized static boolean updatePeersList(List<AgentData> peers) {
		for(int i = 0 ; i < peers.size(); i++)
			Globals.peersList.add(peers.get(i));
		return true;
	}
	
	/**
	 * Adds new super peers to {@link Globals#superPeersList}.
	 * 
	 *
	 
	 @param superPeers	{@link List} of {@link AgentData}
	 *
	 
	 @return boolean
	 *
	 
	 @see AgentData
	 */
	public synchronized static boolean updateSuperPeersList(List<AgentData> superPeers) {
		for(int i = 0 ; i < superPeers.size(); i++)
			Globals.superPeersList.add(superPeers.get(i));
		return true;
	}
	
	/**
	 * Adds the various parameters: {agentID, token, cipheredKey, publicKey
	 * and privateKey} received from the aggregator after registration.
	 * 
	 *
	 
	 @param registerAgentResponse	Response message of {@link RegisterAgentResponse}
	 *
	 
	 @return boolean
	 *
	 
	 @see KeyManager
	 *
	 
	 @see RuntimeParameters
	 */
	public static boolean registerAgent(RegisterAgentResponse registerAgentResponse) {
		try {
			Globals.runtimeParameters.setAgentID(registerAgentResponse.getAgentID());
			Globals.runtimeParameters.setToken(registerAgentResponse.getToken());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Globals.keyManager.setMyCipheredKey(
				registerAgentResponse.getCipheredPublicKey().getBytes());		 
		try {
			PrivateKey privateKey = RSACrypto.stringToPrivateKey(registerAgentResponse.getPrivateKey());
			Globals.keyManager.setMyPrivateKey(privateKey);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			PublicKey publicKey = RSACrypto.stringToPublicKey(registerAgentResponse.getPublicKey());
			Globals.keyManager.setMyPublicKey(publicKey);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return true;
	}
}