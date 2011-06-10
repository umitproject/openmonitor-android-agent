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
	
	 public static RegisterAgentResponse registerAgent(
			RegisterAgent registerAgent) 
	 throws UnsupportedEncodingException, IOException {
			 ClientResource clientResource 
			 = AggregatorResources.getClientResource(Constants.AGGR_REGISTER_AGENT);
			return AggregatorResources.registerAgent(
					registerAgent, clientResource); 
	 }
	
	 public static GetPeerListResponse getPeerList(
			 GetPeerList getPeerList) 
	 throws UnsupportedEncodingException, IOException {
		 ClientResource clientResource 
		 = AggregatorResources.getClientResource(Constants.AGGR_GET_PEER_LIST);
		return AggregatorResources.getPeerList(
				getPeerList, clientResource); 
	 }
	 
	 public static GetSuperPeerListResponse getSuperPeerList(
			 GetSuperPeerList getSuperPeerList) 
	 throws UnsupportedEncodingException, IOException {
		 ClientResource clientResource 
		 = AggregatorResources.getClientResource(Constants.AGGR_GET_PEER_SUPER_LIST);
		return AggregatorResources.getSuperPeerList(
				getSuperPeerList, clientResource); 
	 }
	 
	 public static GetEventsResponse getEvents(
			 GetEvents getEvents) 
	 throws UnsupportedEncodingException, IOException {
		 ClientResource clientResource 
		 = AggregatorResources.getClientResource(Constants.AGGR_GET_EVENTS);
		return AggregatorResources.getEvents(
				getEvents, clientResource); 
	 }
	 
	 public static SendReportResponse sendWebsiteReport(
			 SendWebsiteReport sendWebsiteReport) 
	 throws UnsupportedEncodingException, IOException {
		 ClientResource clientResource 
		 = AggregatorResources.getClientResource(Constants.AGGR_SEND_WEBSITE_REPORT);
		return AggregatorResources.sendWebsiteReport(
				sendWebsiteReport, clientResource); 
	 }
	 
	 public static SendReportResponse sendServiceReport(
			 SendServiceReport sendServiceReport) 
	 throws UnsupportedEncodingException, IOException {
		 ClientResource clientResource 
		 = AggregatorResources.getClientResource(Constants.AGGR_SEND_SERVICE_REPORT);
		return AggregatorResources.sendServiceReport(
				sendServiceReport, clientResource); 
	 }
	 
	 public static NewVersionResponse checkVersion(
			 NewVersion newVersion) 
	 throws UnsupportedEncodingException, IOException {
		 ClientResource clientResource 
		 = AggregatorResources.getClientResource(Constants.AGGR_CHECK_VERSION);
		return AggregatorResources.checkVersion(
				newVersion, clientResource); 
	 }
	 
	 public static NewTestsResponse checkTests(
			 NewTests newTests) 
	 throws UnsupportedEncodingException, IOException {
		 ClientResource clientResource 
		 = AggregatorResources.getClientResource(Constants.AGGR_CHECK_TESTS);
		return AggregatorResources.checkTests(
				newTests, clientResource); 
	 }
	 
	 public static TestSuggestionResponse sendWebsiteSuggestion(
			 WebsiteSuggestion websiteSuggestion) 
	 throws UnsupportedEncodingException, IOException {
		 ClientResource clientResource 
		 = AggregatorResources.getClientResource(Constants.AGGR_WEBSITE_SUGGESTION);
		return AggregatorResources.sendWebsiteSuggestion(
				websiteSuggestion, clientResource); 
	 }
	 
	 public static TestSuggestionResponse sendServiceSuggestion(
			 ServiceSuggestion serviceSuggestion) 
	 throws UnsupportedEncodingException, IOException {
		 ClientResource clientResource 
		 = AggregatorResources.getClientResource(Constants.AGGR_SERVICE_SUGGESTION);
		return AggregatorResources.sendServiceSuggestion(
				serviceSuggestion, clientResource); 
	 }
}