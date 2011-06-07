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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.restlet.representation.Representation;
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

public class AggregatorResources {

	 public ClientResource getClientResource(String url) {
		 return new ClientResource(Constants.AGGREGATOR_URL + url);
	 }
	 
	 public RegisterAgentResponse registerAgent(
			 RegisterAgent registerAgent,
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Representation response = clientResource.post(registerAgent); 
		 return RegisterAgentResponse.parseFrom(
				 new ByteArrayInputStream(
						 response.getText().getBytes("UTF-8")));
	 }
	 
	 public GetPeerListResponse getPeerList(
			 GetPeerList getPeerList, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Representation response = clientResource.post(getPeerList); 
		 return GetPeerListResponse.parseFrom(
				 new ByteArrayInputStream(
						 response.getText().getBytes("UTF-8")));
	 }
	 
	 public GetSuperPeerListResponse getSuperPeerList(
			 GetSuperPeerList getSuperPeerList, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Representation response = clientResource.post(getSuperPeerList); 
		 return GetSuperPeerListResponse.parseFrom(
				 new ByteArrayInputStream(
						 response.getText().getBytes("UTF-8")));
	 }
	 
	 public GetEventsResponse getEvents(
			 GetEvents getEvents, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Representation response = clientResource.post(getEvents); 
		 return GetEventsResponse.parseFrom(
				 new ByteArrayInputStream(
						 response.getText().getBytes("UTF-8")));
	 }
	 
	 public SendReportResponse sendWebsiteReport(
			 SendWebsiteReport sendWebsiteReport, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Representation response = clientResource.post(sendWebsiteReport); 
		 return SendReportResponse.parseFrom(
				 new ByteArrayInputStream(
						 response.getText().getBytes("UTF-8")));
	 }
	 
	 public SendReportResponse sendServiceReport(
			 SendServiceReport sendServiceReport, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Representation response = clientResource.post(sendServiceReport); 
		 return SendReportResponse.parseFrom(
				 new ByteArrayInputStream(
						 response.getText().getBytes("UTF-8")));
	 }
	 
	 public NewVersionResponse checkVersion(
			 NewVersion newVersion, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Representation response = clientResource.post(newVersion); 
		 return NewVersionResponse.parseFrom(
				 new ByteArrayInputStream(
						 response.getText().getBytes("UTF-8")));
	 }
	 
	 public NewTestsResponse checkTests(
			 NewTests newTests, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Representation response = clientResource.post(newTests); 
		 return NewTestsResponse.parseFrom(
				 new ByteArrayInputStream(
						 response.getText().getBytes("UTF-8")));
	 }
	 
	 public TestSuggestionResponse sendWebsiteSuggestion(
			 WebsiteSuggestion websiteSuggestion, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Representation response = clientResource.post(websiteSuggestion); 
		 return TestSuggestionResponse.parseFrom(
				 new ByteArrayInputStream(
						 response.getText().getBytes("UTF-8")));
	 }
	 
	 public TestSuggestionResponse sendServiceSuggestion(
			 ServiceSuggestion serviceSuggestion, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Representation response = clientResource.post(serviceSuggestion); 
		 return TestSuggestionResponse.parseFrom(
				 new ByteArrayInputStream(
						 response.getText().getBytes("UTF-8")));
	 }

}