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

public class MessageSender {
				
		public static void sendWebsiteReport(AgentData agentData, SendWebsiteReport websiteReport) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, websiteReport.toByteArray());
			SendReportResponse sendReportResponse = SendReportResponse.parseFrom(response);
			P2PActions.sendReportAction(sendReportResponse);	
		}
		
		public static void sendServiceReport(AgentData agentData, SendServiceReport serviceReport) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, serviceReport.toByteArray());
			SendReportResponse sendReportResponse = SendReportResponse.parseFrom(response);	  
			P2PActions.sendReportAction(sendReportResponse);
		}
		
		public static void receiveEvents(AgentData agentData, GetEvents getEvents) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, getEvents.toByteArray());
			GetEventsResponse getEventsResponse = GetEventsResponse.parseFrom(response);
			P2PActions.receiveEventsAction(getEventsResponse);			
		}
		
		public static void receivePeerList(AgentData agentData, GetPeerList getPeerList) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, getPeerList.toByteArray());
			GetPeerListResponse getPeerListResponse = GetPeerListResponse.parseFrom(response);
			P2PActions.getPeerListAction(getPeerListResponse);			
		}
		
		public static void receiveSuperPeerList(AgentData agentData, GetSuperPeerList getSuperPeerList) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, getSuperPeerList.toByteArray());
			GetSuperPeerListResponse getSuperPeerListResponse = GetSuperPeerListResponse.parseFrom(response);
			P2PActions.getSuperPeerListAction(getSuperPeerListResponse);
		}
		
		public static void receiveTaskList(AgentData agentData, AssignTask assignTask) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, assignTask.toByteArray());
			AssignTaskResponse assignTaskResponse = AssignTaskResponse.parseFrom(response);
			P2PActions.receiveTaskListAction(assignTaskResponse);
		}				
		
		public static void sendWebsiteSuggestion(AgentData agentData, WebsiteSuggestion websiteSuggestion) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, websiteSuggestion.toByteArray());
			TestSuggestionResponse testSuggestionResponse = TestSuggestionResponse.parseFrom(response);
			P2PActions.sendSuggestionAction(testSuggestionResponse);
		}
		
		public static void sendServiceSuggestion(AgentData agentData, ServiceSuggestion serviceSuggestion) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, serviceSuggestion.toByteArray());
			TestSuggestionResponse testSuggestionResponse = TestSuggestionResponse.parseFrom(response);
			P2PActions.sendSuggestionAction(testSuggestionResponse);
		}
		
		public static void authenticatePeer(AgentData agentData, AuthenticatePeer authenticatePeer) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, authenticatePeer.toByteArray());
			AuthenticatePeerResponse authenticatePeerResponse = AuthenticatePeerResponse.parseFrom(response);
			P2PActions.authenticatePeerAction(authenticatePeerResponse, agentData.getAgentIP());
		}
		
}