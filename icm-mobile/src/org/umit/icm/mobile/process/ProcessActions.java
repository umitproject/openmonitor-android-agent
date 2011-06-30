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
import java.util.List;

import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.proto.MessageProtos.Event;
import org.umit.icm.mobile.proto.MessageProtos.NewVersionResponse;
import org.umit.icm.mobile.proto.MessageProtos.RegisterAgentResponse;
import org.umit.icm.mobile.proto.MessageProtos.ResponseHeader;
import org.umit.icm.mobile.proto.MessageProtos.Test;
import org.umit.icm.mobile.utils.RSACrypto;

public class ProcessActions {	
	
	public static void updateAgentVersion(ResponseHeader header) 
		throws IOException {
		
		if (header.getCurrentVersionNo() 
				> Globals.versionManager.getAgentVersion()) {
			Globals.versionManager.setAgentVersion(header.getCurrentVersionNo());
			// TODO call Aggregator checkVersion webservice
		}
	}
	
	public static void updateTestsVersion(ResponseHeader header)
		throws IOException {
		
		if (header.getCurrentTestVersionNo() 
				> Globals.versionManager.getTestsVersion()) {
			Globals.versionManager.setTestsVersion(header.getCurrentTestVersionNo());
			// TODO call aggregator newTests webservice
		}
	}
			
	/*public static void initializeRequestHeader() throws IOException, RuntimeException {
		Globals.requestHeader = RequestHeader.newBuilder()
		.setAgentID(Globals.runtimeParameters.getAgentID())
		.setToken(Globals.runtimeParameters.getToken())
		.build();
	}*/
	
	public static boolean updateAgent(NewVersionResponse newVersionResponse) {
		// TODO patch current binary
		return true;
	}
	
	public static boolean updateTests(List<Test> tests) {
		for(int i = 0 ; i < tests.size(); i++)
			Globals.testsList.add(tests.get(i));
		return true;
	}
	
	public static boolean updateEventsList(List<Event> events) {
		for(int i = 0 ; i < events.size(); i++)
			Globals.eventsList.add(events.get(i));
		return true;
	}
	
	public static boolean updatePeersList(List<AgentData> peers) {
		for(int i = 0 ; i < peers.size(); i++)
			Globals.peersList.add(peers.get(i));
		return true;
	}
	
	public static boolean updateSuperPeersList(List<AgentData> superPeers) {
		for(int i = 0 ; i < superPeers.size(); i++)
			Globals.superPeersList.add(superPeers.get(i));
		return true;
	}
	
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