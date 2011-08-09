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
import org.umit.icm.mobile.p2p.P2PCommunication;

/**
 * Main p2p message sending class. Sends messages.
 */

public class MessageSender {
	
	/**
	 * Sends website report.
	 * 
	 * 
	 
	 @param websiteReport message of type {@link SendWebsiteReport}
	 *
	 
	 @param agentData message of type {@link AgentData}
	 *

	 @see P2PCommunication
	 */
	public static void sendWebsiteReport(AgentData agentData, SendWebsiteReport websiteReport) 
	throws Exception {
		P2PCommunication.sendMessage(agentData, websiteReport.toByteArray(),
				MessageID.SendWebsiteReport);		
	}
	
	/**
	 * Sends service report.
	 * 
	 * 
	 
	 @param serviceReport  message of type {@link SendServiceReport}
	 *
	 
	 @param agentData message of type {@link AgentData}
	 *
	 
	 @see P2PCommunication
	 */
	public static void sendServiceReport(AgentData agentData, SendServiceReport serviceReport) 
	throws Exception {
		P2PCommunication.sendMessage(agentData, serviceReport.toByteArray(),
				MessageID.SendServiceReport);		
	}
	
	/**
	 * Sends an event request.
	 * 
	 * 
	 
	 @param getEvents message of type {@link GetEvents}
	 *
	 
	 @param agentData message of type {@link AgentData}
	 *
	 	 
	 @see P2PCommunication
	 */
	public static void receiveEvents(AgentData agentData, GetEvents getEvents) 
	throws Exception {
		P2PCommunication.sendMessage(agentData, getEvents.toByteArray(),
				MessageID.GetEvents);		
	}
	
	/**
	 * Sends a {@link GetPeerList} request.
	 * 
	 * 
	 
	 @param getPeerList message of type {@link P2PGetPeerList}
	 *
	 
	 @param agentData message of type {@link AgentData}
	 *
	 	 
	 @see P2PCommunication
	 */
	public static void receivePeerList(AgentData agentData, P2PGetPeerList getPeerList) 
	throws Exception {
		P2PCommunication.sendMessage(agentData, getPeerList.toByteArray(),
				MessageID.P2PGetPeerList);				
	}
	
	/**
	 * Sends a {@link GetSuperPeerList} request.
	 * 
	 * 
	 
	 @param getSuperPeerList message of type {@link P2PGetSuperPeerList}
	 *
	 
	 @param agentData message of type {@link AgentData}
	 *	 
	 
	 @see P2PCommunication
	 */
	public static void receiveSuperPeerList(AgentData agentData, P2PGetSuperPeerList getSuperPeerList) 
	throws Exception {
		P2PCommunication.sendMessage(agentData, getSuperPeerList.toByteArray(),
				MessageID.P2PGetSuperPeerList);		
	}
	
	/**
	 * Sends a {@link NewTests} request.
	 * 
	 * 
	 
	 @param newTests message of type {@link NewTests}
	 *
	 
	 @param agentData message of type {@link AgentData}
	 *	
	 
	 @see P2PCommunication
	 */
	public static void receiveTaskList(AgentData agentData, NewTests newTests) 
	throws Exception {
		P2PCommunication.sendMessage(agentData, newTests.toByteArray(),
				MessageID.NewTests);		
	}				
	
	/**
	 * Sends a {link @WebsiteSuggestion}.
	 * 
	 * 
	 
	 @param websiteSuggestion message of type {@link WebsiteSuggestion}
	 *
	 
	 @param agentData message of type {@link AgentData}
	 *	 
	 
	 @see P2PCommunication
	 */
	public static void sendWebsiteSuggestion(AgentData agentData, WebsiteSuggestion websiteSuggestion) 
	throws Exception {
		P2PCommunication.sendMessage(agentData, websiteSuggestion.toByteArray(),
				MessageID.WebsiteSuggestion);		
	}
	
	/**
	 * Sends a {link @ServiceSuggestion}.
	 * 
	 * 
	 
	 @param serviceSuggestion message of type {@link ServiceSuggestion}
	 *
	 
	 @param agentData message of type {@link AgentData}
	 *	 
	 
	 @see P2PCommunication
	 */
	public static void sendServiceSuggestion(AgentData agentData, ServiceSuggestion serviceSuggestion) 
	throws Exception {
		P2PCommunication.sendMessage(agentData, serviceSuggestion.toByteArray(),
				MessageID.ServiceSuggestion);		
	}
	
	/**
	 * Sends a {@link AuthenticatePeer} message.
	 * 
	 * 
	 
	 @param authenticatePeer message of type {@link AuthenticatePeer}
	 *
	 
	 @param agentData message of type {@link AgentData}
	 *
	 	 
	 @see P2PCommunication
	 */
	public static void authenticatePeer(AgentData agentData, AuthenticatePeer authenticatePeer) 
	throws Exception {
		P2PCommunication.sendMessage(agentData, authenticatePeer.toByteArray(),
				MessageID.AuthenticatePeer);		
	}
	
}