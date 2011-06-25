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

import org.umit.icm.mobile.process.MessageConversion;
import org.umit.icm.mobile.proto.MessageProtos.*;
import org.umit.icm.mobile.p2p.P2PCommunication;

public class MessageSender {
				
		public static void sendWebsiteReport(AgentData agentData, SendWebsiteReport websiteReport) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, websiteReport.toByteArray());
			SendReportResponse sendReportResponse = SendReportResponse.parseFrom(response);
			MessageConversion.updateAgentVersion(sendReportResponse.getHeader());
			MessageConversion.updateTestsVersion(sendReportResponse.getHeader());			
		}
		
		public static void sendServiceReport(AgentData agentData, SendServiceReport serviceReport) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, serviceReport.toByteArray());
			SendReportResponse sendReportResponse = SendReportResponse.parseFrom(response);	  
			MessageConversion.updateAgentVersion(sendReportResponse.getHeader());
			MessageConversion.updateTestsVersion(sendReportResponse.getHeader());
		}
		
		public static GetEventsResponse receiveEvents(AgentData agentData, GetEvents getEvents) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, getEvents.toByteArray());
			GetEventsResponse getEventsResponse = GetEventsResponse.parseFrom(response);
			MessageConversion.updateAgentVersion(getEventsResponse.getHeader());
			MessageConversion.updateTestsVersion(getEventsResponse.getHeader());
			return getEventsResponse;
		}
		
		public static GetPeerListResponse receivePeerList(AgentData agentData, GetPeerList getPeerList) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, getPeerList.toByteArray());
			GetPeerListResponse getPeerListResponse = GetPeerListResponse.parseFrom(response);
			MessageConversion.updateAgentVersion(getPeerListResponse.getHeader());
			MessageConversion.updateTestsVersion(getPeerListResponse.getHeader());
			return getPeerListResponse;
		}
		
		public static GetSuperPeerListResponse receiveSuperPeerList(AgentData agentData, GetSuperPeerList getSuperPeerList) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, getSuperPeerList.toByteArray());
			GetSuperPeerListResponse getSuperPeerListResponse = GetSuperPeerListResponse.parseFrom(response);
			MessageConversion.updateAgentVersion(getSuperPeerListResponse.getHeader());
			MessageConversion.updateTestsVersion(getSuperPeerListResponse.getHeader());
			return getSuperPeerListResponse;
		}
		
		public static AssignTaskResponse receiveTaskList(AgentData agentData, AssignTask assignTask) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, assignTask.toByteArray());
			AssignTaskResponse assignTaskResponse = AssignTaskResponse.parseFrom(response);
			MessageConversion.updateAgentVersion(assignTaskResponse.getHeader());
			MessageConversion.updateTestsVersion(assignTaskResponse.getHeader());
			return assignTaskResponse;
		}
		
		public static void sendSymmetricKey(AgentData agentData, SendPrivateKey sendPrivateKey) throws Exception {
			byte[] response = P2PCommunication.sendMessagePublic(agentData, sendPrivateKey.toByteArray());
			SendPrivateKeyResponse sendPrivateKeyResponse = SendPrivateKeyResponse.parseFrom(response);
			MessageConversion.updateAgentVersion(sendPrivateKeyResponse.getHeader());
			MessageConversion.updateTestsVersion(sendPrivateKeyResponse.getHeader());
		}
		
		public static void sendWebsiteSuggestion(AgentData agentData, WebsiteSuggestion websiteSuggestion) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, websiteSuggestion.toByteArray());
			TestSuggestionResponse testSuggestionResponse = TestSuggestionResponse.parseFrom(response);
			MessageConversion.updateAgentVersion(testSuggestionResponse.getHeader());
			MessageConversion.updateTestsVersion(testSuggestionResponse.getHeader());
		}
		
		public static void sendServiceSuggestion(AgentData agentData, ServiceSuggestion serviceSuggestion) throws Exception {
			byte[] response = P2PCommunication.sendMessage(agentData, serviceSuggestion.toByteArray());
			TestSuggestionResponse testSuggestionResponse = TestSuggestionResponse.parseFrom(response);
			MessageConversion.updateAgentVersion(testSuggestionResponse.getHeader());
			MessageConversion.updateTestsVersion(testSuggestionResponse.getHeader());
		}
		
}