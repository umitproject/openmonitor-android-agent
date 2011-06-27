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
	
	 public static boolean registerAgent(
			RegisterAgent registerAgent) 
	 throws UnsupportedEncodingException, IOException {
			ClientResource clientResource 
			= AggregatorResources.getClientResource( Constants.AGGR_REGISTER_AGENT);
			RegisterAgentResponse registerAgentResponse
			= AggregatorResources.registerAgent(registerAgent, clientResource); 
			return AggregatorActions.registerAgentAction(registerAgentResponse);			
	 }
	
	 public static boolean getPeerList(
			GetPeerList getPeerList) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_GET_PEER_LIST);
		 	GetPeerListResponse getPeerListResponse
		 	= AggregatorResources.getPeerList(getPeerList, clientResource);
		 	return AggregatorActions.getPeerListAction(getPeerListResponse);		 	
	 }
	 
	 public static boolean getSuperPeerList(
			GetSuperPeerList getSuperPeerList) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_GET_PEER_SUPER_LIST);
		 	GetSuperPeerListResponse getSuperPeerListResponse
		 	= AggregatorResources.getSuperPeerList(getSuperPeerList, clientResource);
		 	return AggregatorActions.getSuperPeerListAction(getSuperPeerListResponse);		 	
	 }
	 
	 public static boolean getEvents(
			GetEvents getEvents) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_GET_EVENTS);
		 	GetEventsResponse getEventsResponse 
		 	= AggregatorResources.getEvents(getEvents, clientResource);	 	
		 	return AggregatorActions.getEventsAction(getEventsResponse);
	 }
	 
	 public static boolean sendWebsiteReport(
			SendWebsiteReport sendWebsiteReport) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_SEND_WEBSITE_REPORT);
		 	SendReportResponse sendReportResponse
		 	= AggregatorResources.sendWebsiteReport(sendWebsiteReport, clientResource);		 	
			return AggregatorActions.sendReportAction(sendReportResponse);
	 }
	 
	 public static boolean sendServiceReport(
			SendServiceReport sendServiceReport) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_SEND_SERVICE_REPORT);
		 	SendReportResponse sendReportResponse 
		 	= AggregatorResources.sendServiceReport(sendServiceReport, clientResource);
		 	return AggregatorActions.sendReportAction(sendReportResponse);			
	 }	
	 
	 public static boolean checkVersion(
			NewVersion newVersion) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_CHECK_VERSION);
		 	NewVersionResponse newVersionResponse
		 	= AggregatorResources.checkVersion(newVersion, clientResource);		 	
			return AggregatorActions.checkVersionAction(newVersionResponse);
	 }
	 
	 public static boolean checkTests(
			NewTests newTests) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_CHECK_TESTS);
		 	NewTestsResponse newTestsResponse 
		 	= AggregatorResources.checkTests(newTests, clientResource);		 	
			return AggregatorActions.newTestsAction(newTestsResponse);
	 }
	 
	 public static boolean sendWebsiteSuggestion(
			WebsiteSuggestion websiteSuggestion) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_WEBSITE_SUGGESTION);
		 	TestSuggestionResponse testSuggestionResponse
		 	= AggregatorResources.sendWebsiteSuggestion(websiteSuggestion, clientResource);		 	
			return AggregatorActions.sendSuggestionAction(testSuggestionResponse);
	 }
	 
	 public static boolean sendServiceSuggestion(
			ServiceSuggestion serviceSuggestion) 
	 throws UnsupportedEncodingException, IOException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_SERVICE_SUGGESTION);
		 	TestSuggestionResponse testSuggestionResponse
		 	= AggregatorResources.sendServiceSuggestion(serviceSuggestion, clientResource);
		 	return AggregatorActions.sendSuggestionAction(testSuggestionResponse);			
	 }
	 
	 public static String testSuggestion(
			 String serviceSuggestion) 
	 throws UnsupportedEncodingException, IOException {
		 ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_WEBSITE_SUGGESTION);
		 	String testSuggestionResponse
		 	= AggregatorResources.testSuggestion(serviceSuggestion, clientResource);
		 	return testSuggestionResponse;
	 }
}