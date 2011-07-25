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

import org.umit.icm.mobile.proto.MessageProtos.AgentUpdate;
import org.umit.icm.mobile.proto.MessageProtos.AgentUpdateResponse;
import org.umit.icm.mobile.proto.MessageProtos.AuthenticatePeer;
import org.umit.icm.mobile.proto.MessageProtos.AuthenticatePeerResponse;
import org.umit.icm.mobile.proto.MessageProtos.P2PGetPeerList;
import org.umit.icm.mobile.proto.MessageProtos.P2PGetPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.P2PGetSuperPeerList;
import org.umit.icm.mobile.proto.MessageProtos.P2PGetSuperPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.TestModuleUpdate;
import org.umit.icm.mobile.proto.MessageProtos.TestModuleUpdateResponse;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Message Translation class that decides the Protobuf message based on the ID.
 */

public class MessageTranslation {
	
	/**
	 * Takes as input id and byte[] and builds the corresponding Protobuf message
	 * and then takes action based on it.
	 * 
	 @param	id	message id of type int
	 *
	 
	 @param message message content of type byte[]	 
	 */
	public static void translateMessage(int id, byte[] message) throws InvalidProtocolBufferException {
		
		switch(id) {
		case 1: 
			AuthenticatePeer.parseFrom(message);
			break;
		case 2:
			AuthenticatePeerResponse.parseFrom(message);
			break;
		case 3:
			P2PGetSuperPeerList.parseFrom(message);
			break;
		case 4:
			P2PGetSuperPeerListResponse.parseFrom(message);
			break;
		case 5:
			P2PGetPeerList.parseFrom(message); 
			break;
		case 6:
			P2PGetPeerListResponse.parseFrom(message);
			break;
		case 7:
			AgentUpdate.parseFrom(message);
			break;
		case 8:
			AgentUpdateResponse.parseFrom(message);
			break;
		case 9:
			TestModuleUpdate.parseFrom(message);
			break;
		case 10:
			TestModuleUpdateResponse.parseFrom(message);
			break;
		default:
			throw new RuntimeException("Invalid message");
		}				
	}
}