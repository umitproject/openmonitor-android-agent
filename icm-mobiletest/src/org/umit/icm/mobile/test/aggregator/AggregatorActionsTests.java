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

package org.umit.icm.mobile.test.aggregator;

import java.io.IOException;

import junit.framework.Assert;

import org.umit.icm.mobile.aggregator.AggregatorActions;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.*;
import org.umit.icm.mobile.utils.AESCrypto;

import android.test.AndroidTestCase;


public class AggregatorActionsTests extends AndroidTestCase {
	
	protected void setUp() {
		try {
			Globals.keyManager.setMySecretKey(AESCrypto.generateKey("password".getBytes()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public void testRegisterAgent() throws Throwable {
    	ResponseHeader responseHeader = ResponseHeader.newBuilder()
    	.setCurrentTestVersionNo(20)
    	.setCurrentVersionNo(20)
    	.build();
    	
    	RegisterAgentResponse registerAgentResponse 
    	= RegisterAgentResponse.newBuilder()
    	.setToken("token")
    	.setAgentID(11)
    	.setCipheredPublicKey("cipheredKey")
    	.setHeader(responseHeader)
    	.setPrivateKey("privateKey")
    	.setPublicKey("publicKey")
    	.setAggregatorPublicKey("aggregatorPublicKey")    	
    	.build();
    	
    	AggregatorActions.registerAgentAction(registerAgentResponse);
    	                
        Assert.assertEquals(Globals.runtimeParameters.getAgentID(), 11);
        Assert.assertTrue(Globals.runtimeParameters.getToken().equals("token"));
                        
    }    
    
    public void testGetPeerList() throws Throwable {
    	ResponseHeader responseHeader = ResponseHeader.newBuilder()
    	.setCurrentTestVersionNo(21)
    	.setCurrentVersionNo(21)
    	.build();

    	AgentData agent1 = AgentData.newBuilder()
    	.setAgentIP("IP1")
    	.setAgentPort(11)
    	.setPeerStatus("On")
    	.setPublicKey("publicKey1")
    	.setToken("token1")
    	.setAgentID(1)
    	.build();
    	
    	AgentData agent2 = AgentData.newBuilder()
    	.setAgentIP("IP2")
    	.setAgentPort(12)
    	.setPeerStatus("On")
    	.setAgentID(2)
    	.setPublicKey("publicKey2")
    	.setToken("token2")
    	.build();
    	
    	GetPeerListResponse getPeerListResponse 
    	= GetPeerListResponse.newBuilder()
    	.setHeader(responseHeader)
    	.addKnownPeers(agent1)
    	.addKnownPeers(agent2)
    	.build();
    	
    	AggregatorActions.getPeerListAction(getPeerListResponse);
    	                
        Assert.assertTrue(compareAgentData(Globals.peersList.get(0), agent1));
        Assert.assertTrue(compareAgentData(Globals.peersList.get(1), agent2));
                        
    }        
    
    private boolean compareAgentData(AgentData agent1, AgentData agent2) {
    	if(agent1.getAgentIP().equals(agent2.getAgentIP())
    			&& agent1.getAgentPort() == agent2.getAgentPort()
    			&& agent1.getPeerStatus().equals(agent2.getPeerStatus())
    			&& agent1.getPublicKey().equals(agent2.getPublicKey())
    			&& agent1.getAgentID() == agent2.getAgentID()
    			&& agent1.getToken().equals(agent2.getToken()))
    		return true;
    	return false;
    }
    
    public void testGetSuperPeerList() throws Throwable {
    	ResponseHeader responseHeader = ResponseHeader.newBuilder()
    	.setCurrentTestVersionNo(22)
    	.setCurrentVersionNo(22)
    	.build();

    	AgentData agent1 = AgentData.newBuilder()
    	.setAgentIP("IP3")
    	.setAgentID(3)
    	.setAgentPort(13)
    	.setPeerStatus("On")
    	.setPublicKey("publicKey3")
    	.setToken("token3")
    	.build();
    	
    	AgentData agent2 = AgentData.newBuilder()
    	.setAgentIP("IP4")
    	.setAgentID(4)
    	.setAgentPort(14)
    	.setPeerStatus("On")
    	.setPublicKey("publicKey4")
    	.setToken("token4")
    	.build();
    	
    	GetSuperPeerListResponse getSuperPeerListResponse 
    	= GetSuperPeerListResponse.newBuilder()
    	.setHeader(responseHeader)
    	.addKnownSuperPeers(agent1)
    	.addKnownSuperPeers(agent2)
    	.build();
    	
    	AggregatorActions.getSuperPeerListAction(getSuperPeerListResponse);
    	                
        Assert.assertTrue(compareAgentData(Globals.superPeersList.get(0), agent1));
        Assert.assertTrue(compareAgentData(Globals.superPeersList.get(1), agent2));
                        
    } 
    
    public void testCheckNewTests() throws Throwable {
    	ResponseHeader responseHeader = ResponseHeader.newBuilder()
    	.setCurrentTestVersionNo(23)
    	.setCurrentVersionNo(23)
    	.build();

    	Test test1 = Test.newBuilder()
    	.setExecuteAtTimeUTC(11)
    	.setServideCode(21)
    	.setTestID(31)
    	.setWebsiteURL("url1")
    	.setTestType("WEB")
    	.build();
    	
    	Test test2 = Test.newBuilder()
    	.setExecuteAtTimeUTC(12)
    	.setServideCode(22)
    	.setTestID(32)
    	.setWebsiteURL("url2")
    	.setTestType("SERVICE")
    	.build();
    	
    	NewTestsResponse newTestsResponse = NewTestsResponse.newBuilder()
    	.setHeader(responseHeader)
    	.setTestVersionNo(23)
    	.addTests(test1)
    	.addTests(test2)
    	.build();
    	
    	AggregatorActions.newTestsAction(newTestsResponse);
    	
        Assert.assertTrue(compareTests(Globals.testsList.get(0), test1));
        Assert.assertTrue(compareTests(Globals.testsList.get(1), test2));
                        
    } 
    
    private boolean compareTests(Test test1 , Test test2) {
    	if(test1.getExecuteAtTimeUTC() == test2.getExecuteAtTimeUTC()
    			&& test1.getServideCode() == test2.getServideCode()
    			&& test1.getTestID() == test2.getTestID()
    			&& test1.getWebsiteURL().equals(test2.getWebsiteURL())
    			&& test1.getTestType().equals(test2.getTestType()))
    		return true;
    	return false;
    }
    
    public void testGetEvents() throws Throwable {
    	ResponseHeader responseHeader = ResponseHeader.newBuilder()
    	.setCurrentTestVersionNo(23)
    	.setCurrentVersionNo(23)
    	.build();

    	Event event1 = Event.newBuilder()
    	.setEventType("CENSOR")
    	.addLocations("Islamabad")
    	.setSinceTimeUTC(100)
    	.setTimeUTC(1000)
    	.setTestType("SERVICE")
    	.build();
    	
    	Event event2 = Event.newBuilder()
    	.setEventType("OFF_LINE")
    	.addLocations("Islamabad2")
    	.setSinceTimeUTC(101)
    	.setTimeUTC(1001)
    	.setTestType("WEB")
    	.build();
    	
    	GetEventsResponse getEventsResponse 
    	= GetEventsResponse.newBuilder()
    	.setHeader(responseHeader)
    	.addEvents(event1)
    	.addEvents(event2)
    	.build();
    	
    	AggregatorActions.getEventsAction(getEventsResponse);
    	
        Assert.assertEquals(Globals.versionManager.getAgentVersion(), 23);
        Assert.assertEquals(Globals.versionManager.getTestsVersion(), 23);
        Assert.assertTrue(compareEvent(Globals.eventsList.get(0), event1));
        Assert.assertTrue(compareEvent(Globals.eventsList.get(1), event2));
                        
    } 
    
    private boolean compareEvent(Event event1, Event event2) {
    	if(event1.getEventType().equals(event2.getEventType())
    			&& event1.getLocationsList().equals(event2.getLocationsList())
    			&& event1.getSinceTimeUTC() == event2.getSinceTimeUTC()
    			&& event1.getTimeUTC() == event2.getTimeUTC()
    			&& event1.getTestType().equals(event2.getTestType()))
    		return true;
    	return false;
    }

}