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

import org.umit.icm.mobile.proto.MessageProtos.*;

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
	public static void translateMessage(int id, byte[] message, AgentData agentData) 
	throws InvalidProtocolBufferException {
		
		switch(id) {
		case MessageID.AgentUpdate: 
			AgentUpdate.parseFrom(message);
			break;
		
		case MessageID.AgentUpdateResponse: 
			AgentUpdateResponse.parseFrom(message);
			break;
		
		case MessageID.AuthenticatePeer: 
			AuthenticatePeer.parseFrom(message);
			break;
		
		case MessageID.AuthenticatePeerResponse: 		
			P2PActions.authenticatePeerAction(
					AuthenticatePeerResponse.parseFrom(message), 
					agentData.getAgentIP());				
			break;
		
		case MessageID.CheckAggregator: 
			CheckAggregator.parseFrom(message);
			break;
		
		case MessageID.CheckAggregatorResponse: 
			CheckAggregatorResponse.parseFrom(message);
			break;
		
		case MessageID.ForwardingMessage: 
			ForwardingMessage.parseFrom(message);
			break;
		
		case MessageID.ForwardingMessageResponse: 
			P2PActions.forwardMessageAction(
					ForwardingMessageResponse.parseFrom(message), agentData);
			break;
		
		case MessageID.GetEvents: 
			GetEvents.parseFrom(message);
			break;
		
		case MessageID.GetEventsResponse: 
			P2PActions.receiveEventsAction(
					GetEventsResponse.parseFrom(message));			
			break;
		
		case MessageID.GetPeerList: 
			GetPeerList.parseFrom(message);
			break;
		
		case MessageID.GetPeerListResponse: 
			GetPeerListResponse.parseFrom(message);
			break;
		
		case MessageID.GetSuperPeerList: 
			GetSuperPeerList.parseFrom(message);
			break;
			
		case MessageID.GetSuperPeerListResponse: 
			GetSuperPeerListResponse.parseFrom(message);
			break;
		
		case MessageID.Login: 
			Login.parseFrom(message);
			break;
		
		case MessageID.LoginResponse: 
			LoginResponse.parseFrom(message);
			break;
		
		case MessageID.Logout: 
			Logout.parseFrom(message);
			break;
		
		case MessageID.NewTests: 
			NewTests.parseFrom(message);
			break;
		
		case MessageID.NewTestsResponse: 
			P2PActions.receiveTaskListAction(
					NewTestsResponse.parseFrom(message));			
			break;
			
		case MessageID.NewVersion: 
			NewVersion.parseFrom(message);
			break;
		
		case MessageID.NewVersionResponse: 
			NewVersionResponse.parseFrom(message);
			break;
		
		case MessageID.P2PGetPeerList: 
			P2PGetPeerList.parseFrom(message);
			break;
		
		case MessageID.P2PGetPeerListResponse: 
			P2PActions.getPeerListAction(
					P2PGetPeerListResponse.parseFrom(message));			
			break;
		
		case MessageID.P2PGetSuperPeerList: 
			P2PGetSuperPeerList.parseFrom(message);
			break;
		
		case MessageID.P2PGetSuperPeerListResponse:
			P2PActions.getSuperPeerListAction(
					P2PGetSuperPeerListResponse.parseFrom(message));			
			break;
		
		case MessageID.RegisterAgent: 
			RegisterAgent.parseFrom(message);
			break;
		
		case MessageID.RegisterAgentResponse: 
			RegisterAgentResponse.parseFrom(message);
			break;
		
		case MessageID.SendPrivateKey: 
			SendPrivateKey.parseFrom(message);
			break;
		
		case MessageID.SendPrivateKeyResponse: 
			SendPrivateKeyResponse.parseFrom(message);
			break;
		
		case MessageID.SendServiceReport: 
			SendServiceReport.parseFrom(message);
			break;
		
		case MessageID.SendWebsiteReport: 
			SendWebsiteReport.parseFrom(message);
			break;
		
		case MessageID.SendReportResponse: 
			P2PActions.sendReportAction(
					SendReportResponse.parseFrom(message));			
			break;
		
		case MessageID.ServiceSuggestion: 
			ServiceSuggestion.parseFrom(message);
			break;
		
		case MessageID.TestModuleUpdate: 
			TestModuleUpdate.parseFrom(message);
			break;
		
		case MessageID.TestModuleUpdateResponse: 
			TestModuleUpdateResponse.parseFrom(message);
			break;
		
		case MessageID.TestSuggestionResponse: 
			P2PActions.sendSuggestionAction(
					TestSuggestionResponse.parseFrom(message));			
			break;
		
		case MessageID.UpgradeToSuper: 
			UpgradeToSuper.parseFrom(message);
			break;
		
		case MessageID.UpgradeToSuperResponse: 
			UpgradeToSuperResponse.parseFrom(message);
			break;
		
		case MessageID.WebsiteSuggestion: 
			WebsiteSuggestion.parseFrom(message);
			break;
	
		default:
			throw new RuntimeException("Invalid message");
		}				
	}
}