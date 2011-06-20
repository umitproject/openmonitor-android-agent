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


import org.apache.commons.codec.binary.Base64;
import org.restlet.data.Form;
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

	 public static ClientResource getClientResource(String url) {	
		 return new ClientResource(Constants.AGGREGATOR_URL + url);
	 }
	 
	 public static RegisterAgentResponse registerAgent(
			 RegisterAgent registerAgent,
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {		 		 
		 Form form = new Form();
		 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(registerAgent.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 		 
		 return RegisterAgentResponse.parseFrom((Base64.decodeBase64(response.getText().getBytes())));
	 }
	 
	 public static GetPeerListResponse getPeerList(
			 GetPeerList getPeerList, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Form form = new Form();
		 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(getPeerList.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 		 
		 return GetPeerListResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	 public static GetSuperPeerListResponse getSuperPeerList(
			 GetSuperPeerList getSuperPeerList, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Form form = new Form();
		 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(getSuperPeerList.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null));  
		 return GetSuperPeerListResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	 public static GetEventsResponse getEvents(
			 GetEvents getEvents, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Form form = new Form();
		 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(getEvents.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 
		 return GetEventsResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	 public static SendReportResponse sendWebsiteReport(
			 SendWebsiteReport sendWebsiteReport, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Form form = new Form();
		 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(sendWebsiteReport.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 
		 return SendReportResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	 public static SendReportResponse sendServiceReport(
			 SendServiceReport sendServiceReport, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Form form = new Form();
		 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(sendServiceReport.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 
		 return SendReportResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	 public static NewVersionResponse checkVersion(
			 NewVersion newVersion, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Form form = new Form();
		 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(newVersion.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 
		 return NewVersionResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	 public static NewTestsResponse checkTests(
			 NewTests newTests, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Form form = new Form();
		 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(newTests.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 
		 return NewTestsResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	 public static TestSuggestionResponse sendWebsiteSuggestion(
			 WebsiteSuggestion websiteSuggestion, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Form form = new Form();
		 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(websiteSuggestion.toByteArray())));		 		 		 
		 Representation response 
			 = clientResource.post(form.getWebRepresentation(null));  
		 return TestSuggestionResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	 public static TestSuggestionResponse sendServiceSuggestion(
			 ServiceSuggestion serviceSuggestion, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Form form = new Form();
		 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(serviceSuggestion.toByteArray())));		 		 		 
		 Representation response 
			 = clientResource.post(form.getWebRepresentation(null)); 
		 return TestSuggestionResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	 public static String testSuggestion(
			 String serviceSuggestion, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException {
		 Form form = new Form();
		 form.add(Constants.AGGR_MSG_KEY
				 , serviceSuggestion);		 		 			 		  		 
		 Representation response 
			 = clientResource.post(form.getWebRepresentation(null)); 
		 return new String(response.getText());
	 }

}