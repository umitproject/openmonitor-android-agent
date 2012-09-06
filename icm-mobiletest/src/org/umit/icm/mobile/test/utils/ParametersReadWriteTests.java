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

package org.umit.icm.mobile.test.utils;



import java.util.ArrayList;
import java.util.List;

import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.proto.MessageProtos.Event;
import org.umit.icm.mobile.proto.MessageProtos.Location;
import org.umit.icm.mobile.proto.MessageProtos.RSAKey;
import org.umit.icm.mobile.proto.MessageProtos.Service;
import org.umit.icm.mobile.proto.MessageProtos.Test;
import org.umit.icm.mobile.proto.MessageProtos.Website;
import org.umit.icm.mobile.utils.SDCardReadWrite;

import junit.framework.Assert;
import android.test.AndroidTestCase;


public class ParametersReadWriteTests extends AndroidTestCase {

    public void testPeersList() throws Throwable {
    	RSAKey rsaKey1 = RSAKey.newBuilder()
		.setExp("exp1")
		.setMod("mod1")
		.build();
    	AgentData agent1 = AgentData.newBuilder()
    	.setAgentIP("IP1")
    	.setAgentID("1")
    	.setAgentPort(11)
    	.setPeerStatus("On")
    	.setPublicKey(rsaKey1)
    	.setToken("token1")
    	.build();
    	
    	RSAKey rsaKey2 = RSAKey.newBuilder()
		.setExp("exp2")
		.setMod("mod2")
		.build();
    	AgentData agent2 = AgentData.newBuilder()
    	.setAgentIP("IP2")
    	.setAgentID("2")
    	.setAgentPort(12)
    	.setPeerStatus("On")
    	.setPublicKey(rsaKey2)
    	.setToken("token2")
    	.build();
    	
    	List<AgentData> peerList = new ArrayList<AgentData>();
    	peerList.add(agent1);
    	peerList.add(agent2);
        
    	SDCardReadWrite.writePeersList(Constants.PARAMETERS_DIR, peerList);
    	
    	List<AgentData> peerList2 
    	= SDCardReadWrite.readPeersList(Constants.PARAMETERS_DIR);
    	
    	Assert.assertEquals(peerList, peerList2);
    }        
    
    public void testSuperPeersList() throws Throwable {
    	RSAKey rsaKey1 = RSAKey.newBuilder()
		.setExp("exp1")
		.setMod("mod1")
		.build();
    	AgentData agent1 = AgentData.newBuilder()
    	.setAgentIP("IP1")
    	.setAgentID("1")
    	.setAgentPort(11)
    	.setPeerStatus("On")
    	.setPublicKey(rsaKey1)
    	.setToken("token1")
    	.build();
    	
    	RSAKey rsaKey2 = RSAKey.newBuilder()
		.setExp("exp2")
		.setMod("mod2")
		.build();    	
    	AgentData agent2 = AgentData.newBuilder()
    	.setAgentIP("IP2")
    	.setAgentID("2")
    	.setAgentPort(12)
    	.setPeerStatus("On")
    	.setPublicKey(rsaKey2)
    	.setToken("token2")
    	.build();
    	
    	List<AgentData> peerList = new ArrayList<AgentData>();
    	peerList.add(agent1);
    	peerList.add(agent2);
        
    	SDCardReadWrite.writeSuperPeersList(Constants.PARAMETERS_DIR, peerList);
    	
    	List<AgentData> peerList2 
    	= SDCardReadWrite.readSuperPeersList(Constants.PARAMETERS_DIR);
    	
    	Assert.assertEquals(peerList, peerList2);
    }  
    
    public void testEventsList() throws Throwable {
    	
    	Location location = Location.newBuilder()
    	.setLatitude(10.1)
    	.setLongitude(10.1)
    	.build();
    	
    	Event event1 = Event.newBuilder()
    	.setEventType("CENSOR")
    	.addLocations(location)
    	.setSinceTimeUTC(100)
    	.setTimeUTC(1000)
    	.setTestType("SERVICE")
    	.build();
    	
    	Event event2 = Event.newBuilder()
    	.setEventType("OFF_LINE")
    	.addLocations(location)
    	.setSinceTimeUTC(101)
    	.setTimeUTC(1001)
    	.setTestType("WEB")
    	.build();
    	
    	List<Event> eventList = new ArrayList<Event>();
    	eventList.add(event1);
    	eventList.add(event2);
        
    	SDCardReadWrite.writeEventsList(Constants.PARAMETERS_DIR, eventList);
    	
    	List<Event> eventList2 
    	= SDCardReadWrite.readEventsList(Constants.PARAMETERS_DIR);
    	
    	Assert.assertEquals(eventList, eventList2);
    } 
    
public void testTestsList() throws Throwable {
    	
	Website website = Website.newBuilder()
	.setUrl("url1")
	.build();
	
	Test test1 = Test.newBuilder()
	.setExecuteAtTimeUTC(11)
	.setWebsite(website)
	.setTestID("31")    	
	.setTestType(1)
	.build();
	
	Service service = Service.newBuilder()
	.setIp("ip")
	.setName("name")
	.setPort(1000)
	.build();
	
	Test test2 = Test.newBuilder()
	.setExecuteAtTimeUTC(12)    	
	.setTestID("32")
	.setService(service)
	.setTestType(2)
	.build();
    	
    	List<Test> testList = new ArrayList<Test>();
    	testList.add(test1);
    	testList.add(test2);
        
    	SDCardReadWrite.writeTestsList(Constants.TESTS_DIR, testList);
    	
    	List<Test> testList2 
    	= SDCardReadWrite.readTestsList(Constants.TESTS_DIR);
    	
    	Assert.assertEquals(testList, testList2);
    } 

}