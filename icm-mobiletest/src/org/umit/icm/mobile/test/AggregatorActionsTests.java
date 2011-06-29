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

import org.umit.icm.mobile.aggregator.AggregatorActions;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.*;

import android.test.AndroidTestCase;


public class AggregatorActionsTests extends AndroidTestCase {

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
    	.build();
    	
    	AggregatorActions.registerAgentAction(registerAgentResponse);
    	
        Assert.assertEquals(Globals.versionManager.getAgentVersion(), 20);
        Assert.assertEquals(Globals.versionManager.getTestsVersion(), 20);
        Assert.assertEquals(Globals.runtimeParameters.getAgentID(), 11);
        Assert.assertTrue(Globals.runtimeParameters.getToken().equals("token"));
        
        
    }        

}