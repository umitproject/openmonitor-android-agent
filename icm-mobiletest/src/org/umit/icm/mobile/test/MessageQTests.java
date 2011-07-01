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

package org.umit.icm.mobile.test;

import junit.framework.Assert;

import org.umit.icm.mobile.p2p.MessageQueue;
import org.umit.icm.mobile.p2p.QueueObject;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;

import android.test.AndroidTestCase;

public class MessageQTests extends AndroidTestCase {
	
	public void testMessageQ() throws Throwable {
		AgentData agent1 = AgentData.newBuilder()
		.setAgentIP("IP1")
		.setAgentPort(11)
		.setPeerStatus("On")
		.setPublicKey("publicKey1")
		.setToken("token1")
		.build();
		
		AgentData agent2 = AgentData.newBuilder()
		.setAgentIP("IP2")
		.setAgentPort(12)
		.setPeerStatus("On")
		.setPublicKey("publicKey2")
		.setToken("token2")
		.build();
		
		AgentData agent3 = AgentData.newBuilder()
		.setAgentIP("IP3")
		.setAgentPort(13)
		.setPeerStatus("On")
		.setPublicKey("publicKey3")
		.setToken("token3")
		.build();
		
		QueueObject obj1 = new QueueObject(agent1, "what".getBytes());
		QueueObject obj2 = new QueueObject(agent2, "what".getBytes());
		QueueObject obj3 = new QueueObject(agent3, "what".getBytes());
		
		MessageQueue messageQ = new MessageQueue();
		
		messageQ.addMessage(obj1);
		messageQ.addMessage(obj2);
		messageQ.addMessage(obj3);
		
		Assert.assertEquals(messageQ.removeMessage(), obj1);
		Assert.assertEquals(messageQ.removeMessage(), obj2);
		Assert.assertEquals(messageQ.removeMessage(), obj3);
	}
		
	private boolean compareAgentData(AgentData agent1, AgentData agent2) {
    	if(agent1.getAgentIP().equals(agent2.getAgentIP())
    			&& agent1.getAgentPort() == agent2.getAgentPort()
    			&& agent1.getPeerStatus().equals(agent2.getPeerStatus())
    			&& agent1.getPublicKey().equals(agent2.getPublicKey())
    			&& agent1.getToken().equals(agent2.getToken()))
    		return true;
    	return false;
    }
}