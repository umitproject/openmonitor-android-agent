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

package org.umit.icm.mobile.test.p2p;


import junit.framework.Assert;

import org.umit.icm.mobile.p2p.AuthenticatedPeers;
import org.umit.icm.mobile.proto.MessageProtos.*;

import android.test.AndroidTestCase;


public class AuthenticatedPeersTests extends AndroidTestCase {

    
    public void testReadWriteAdd() throws Throwable {
    	AuthenticatedPeers authenticatedPeers
    	= new AuthenticatedPeers();
    	
    	AgentData agentData = AgentData.newBuilder()
    	.setAgentID(10)
    	.setAgentIP("IP")
    	.setAgentPort(8000)
    	.setPeerStatus("On")
    	.setPublicKey("Key")
    	.setToken("token")
    	.build();
    	    	
    	authenticatedPeers.addAuthenticatedPeer(agentData);
    	
    	AgentData agentData2 = AgentData.newBuilder()
    	.setAgentID(20)
    	.setAgentIP("IP")
    	.setAgentPort(8000)
    	.setPeerStatus("On")
    	.setPublicKey("Key")
    	.setToken("token")
    	.build();
    	    	
    	authenticatedPeers.addAuthenticatedPeer(agentData2); 
    	authenticatedPeers.writeAuthenticatePeers();
    	
    	AuthenticatedPeers authenticatedPeers2 = new AuthenticatedPeers();
    	authenticatedPeers2.readAuthenticatePeers();
    	    	
        Assert.assertTrue(authenticatedPeers.checkPeer(agentData));
        Assert.assertTrue(authenticatedPeers.checkPeer(agentData2));
        Assert.assertTrue(authenticatedPeers.equals(authenticatedPeers2));
                        
    }  
}