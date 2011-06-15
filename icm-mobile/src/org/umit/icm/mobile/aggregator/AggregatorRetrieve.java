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

package org.umit.icm.mobile.aggregator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.restlet.resource.ClientResource;
import org.umit.icm.mobile.process.MessageConversion;
import org.umit.icm.mobile.proto.MessageProtos.GetEvents;
import org.umit.icm.mobile.proto.MessageProtos.GetEventsResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerList;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerList;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.NewTests;
import org.umit.icm.mobile.proto.MessageProtos.NewTestsResponse;
import org.umit.icm.mobile.proto.MessageProtos.NewVersion;
import org.umit.icm.mobile.proto.MessageProtos.NewVersionResponse;
import org.umit.icm.mobile.proto.MessageProtos.RegisterAgent;
import org.umit.icm.mobile.proto.MessageProtos.RegisterAgentResponse;
import org.umit.icm.mobile.proto.MessageProtos.SendReportResponse;
import org.umit.icm.mobile.proto.MessageProtos.SendServiceReport;
import org.umit.icm.mobile.proto.MessageProtos.SendWebsiteReport;
import org.umit.icm.mobile.proto.MessageProtos.ServiceSuggestion;
import org.umit.icm.mobile.proto.MessageProtos.TestSuggestionResponse;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteSuggestion;
import org.umit.icm.mobile.utils.Constants;

public class AggregatorRetrieve {
	
	 public static RegisterAgentResponse registerAgent(
			RegisterAgent registerAgent) 
	 throws UnsupportedEncodingException, IOException {
			ClientResource clientResource 
			= AggregatorResources.getClientResource( Constants.AGGR_REGISTER_AGENT);
			RegisterAgentResponse registerAgentResponse
			= AggregatorResources.registerAgent(registerAgent, clientResource); 
			MessageConversion.updateAgentVersion(registerAgentResponse.getHeader());
			MessageConversion.updateTestsVersion(registerAgentResponse.getHeader());
			return registerAgentResponse;
	 }
	
	 public static GetPeerListResponse getPeerList(
			GetPeerList getPeerList) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_GET_PEER_LIST);
		 	GetPeerListResponse getPeerListResponse
		 	= AggregatorResources.getPeerList(getPeerList, clientResource);
		 	MessageConversion.updateAgentVersion(getPeerListResponse.getHeader());
		 	MessageConversion.updateTestsVersion(getPeerListResponse.getHeader());
		 	return getPeerListResponse;
	 }
	 
	 public static GetSuperPeerListResponse getSuperPeerList(
			GetSuperPeerList getSuperPeerList) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_GET_PEER_SUPER_LIST);
		 	GetSuperPeerListResponse getSuperPeerListResponse
		 	= AggregatorResources.getSuperPeerList(getSuperPeerList, clientResource);
		 	MessageConversion.updateAgentVersion(getSuperPeerListResponse.getHeader());
		 	MessageConversion.updateTestsVersion(getSuperPeerListResponse.getHeader());
		 	return getSuperPeerListResponse; 
	 }
	 
	 public static GetEventsResponse getEvents(
			GetEvents getEvents) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_GET_EVENTS);
		 	GetEventsResponse getEventsResponse 
		 	= AggregatorResources.getEvents(getEvents, clientResource);
		 	MessageConversion.updateAgentVersion(getEventsResponse.getHeader());
		 	MessageConversion.updateTestsVersion(getEventsResponse.getHeader());
		 	return getEventsResponse;
	 }
	 
	 public static boolean sendWebsiteReport(
			SendWebsiteReport sendWebsiteReport) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_SEND_WEBSITE_REPORT);
		 	SendReportResponse sendReportResponse
		 	= AggregatorResources.sendWebsiteReport(sendWebsiteReport, clientResource);
		 	MessageConversion.updateAgentVersion(sendReportResponse.getHeader());
			MessageConversion.updateTestsVersion(sendReportResponse.getHeader());
			return true;
	 }
	 
	 public static boolean sendServiceReport(
			SendServiceReport sendServiceReport) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_SEND_SERVICE_REPORT);
		 	SendReportResponse sendReportResponse 
		 	= AggregatorResources.sendServiceReport(sendServiceReport, clientResource);
		 	MessageConversion.updateAgentVersion(sendReportResponse.getHeader());
			MessageConversion.updateTestsVersion(sendReportResponse.getHeader());
			return true;
	 }	
	 
	 public static NewVersionResponse checkVersion(
			NewVersion newVersion) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_CHECK_VERSION);
		 	NewVersionResponse newVersionResponse
		 	= AggregatorResources.checkVersion(newVersion, clientResource);
		 	MessageConversion.updateAgentVersion(newVersionResponse.getHeader());
			MessageConversion.updateTestsVersion(newVersionResponse.getHeader());
			return newVersionResponse;
	 }
	 
	 public static NewTestsResponse checkTests(
			NewTests newTests) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_CHECK_TESTS);
		 	NewTestsResponse newTestsResponse 
		 	= AggregatorResources.checkTests(newTests, clientResource);
		 	MessageConversion.updateAgentVersion(newTestsResponse.getHeader());
			MessageConversion.updateTestsVersion(newTestsResponse.getHeader());
			return newTestsResponse;
	 }
	 
	 public static boolean sendWebsiteSuggestion(
			WebsiteSuggestion websiteSuggestion) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_WEBSITE_SUGGESTION);
		 	TestSuggestionResponse testSuggestionResponse
		 	= AggregatorResources.sendWebsiteSuggestion(websiteSuggestion, clientResource);
		 	MessageConversion.updateAgentVersion(testSuggestionResponse.getHeader());
		 	MessageConversion.updateTestsVersion(testSuggestionResponse.getHeader());
			return true;
	 }
	 
	 public static TestSuggestionResponse sendServiceSuggestion(
			ServiceSuggestion serviceSuggestion) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_SERVICE_SUGGESTION);
		 	TestSuggestionResponse testSuggestionResponse
		 	= AggregatorResources.sendServiceSuggestion(serviceSuggestion, clientResource);
		 	//MessageConversion.updateAgentVersion(testSuggestionResponse.getHeader());
		 	//MessageConversion.updateTestsVersion(testSuggestionResponse.getHeader());
			return testSuggestionResponse;
	 }
}