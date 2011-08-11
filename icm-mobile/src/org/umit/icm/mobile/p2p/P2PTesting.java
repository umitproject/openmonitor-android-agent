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

package org.umit.icm.mobile.p2p;

import java.io.IOException;

import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.proto.MessageProtos.AuthenticatePeer;
import org.umit.icm.mobile.proto.MessageProtos.AuthenticatePeerResponse;

import android.util.Log;

public class P2PTesting {
	public static void testRequestResponse() throws IOException {
		String ip = "202.206.64.11";
		int port = 3128;
		Globals.tcpClientConnectivity.openConnection(ip, port);		
		Globals.tcpClientConnectivity.writeLine(MessageBuilder.generateMessage(
				getTestMessage().getMessageID(), getTestMessage().getMessage()));
		/*read total length*/
		byte [] response = Globals.tcpClientConnectivity.readBytes(4);
		Log.w("###Len ResponseSize: ", Integer.toString(response.length));	
		if(response.length!=0) {
			byte [] sizeBytes = MessageBuilder.getSubArray(response, 0, 3);
			Log.w("###Len sizeBytes: ", Integer.toString(sizeBytes.length));									
			int size = MessageBuilder.byteArrayToInt(sizeBytes);						
			Log.w("###size: ", Integer.toString(size));				
			
			/*read actual message now*/
			response = Globals.tcpClientConnectivity.readBytes(size);
			Log.w("###Len ResponseMessage: ", Integer.toString(response.length));	
			if(response.length!=0) {
				byte[] idBytes = MessageBuilder.getSubArray(response, 0, 3);
				Log.w("###Len idBytes: ", Integer.toString(idBytes.length));																
				int id = MessageBuilder.byteArrayToInt(idBytes);				
				Log.w("###id ", Integer.toString(id));
				byte [] msg = MessageBuilder.getSubArray(response, 4, size - 1);
				
				AuthenticatePeerResponse authenticatePeerResponse
				= AuthenticatePeerResponse.parseFrom(msg);
				Log.w("###msg ", authenticatePeerResponse.getSecretKey());
				
				} else {
					Log.w("### ", "Blank response");
				}					
	        			
			} else {
				Log.w("### ", "Blank response");
		}
		Globals.tcpClientConnectivity.closeConnection();
	}
	
	private static QueueObject getTestMessage() {		
		AuthenticatePeer authenticatePeer = AuthenticatePeer.newBuilder()
		.setAgentID(10)
		.setAgentPort(8000)
		.setAgentType(3)
		.setCipheredPublicKey("cipheredPublicKey")
		.build();
		
		AgentData agentData = AgentData.newBuilder()
		.setAgentID(10)
		.setAgentIP("")
		.setAgentPort(20)
		.setPeerStatus("On")
		.setPublicKey("Key")
		.setToken("token")
		.build();
		
		return new QueueObject(agentData, authenticatePeer.toByteArray(), MessageID.AuthenticatePeer);		
	}
}