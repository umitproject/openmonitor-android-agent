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

package org.umit.icm.mobile.superpeer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


import org.apache.commons.codec.binary.Base64;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.proto.MessageProtos.AuthenticatePeer;
import org.umit.icm.mobile.proto.MessageProtos.AuthenticatePeerResponse;
import org.umit.icm.mobile.proto.MessageProtos.CheckAggregator;
import org.umit.icm.mobile.proto.MessageProtos.CheckAggregatorResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetEvents;
import org.umit.icm.mobile.proto.MessageProtos.GetEventsResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerList;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerList;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.Login;
import org.umit.icm.mobile.proto.MessageProtos.LoginResponse;
import org.umit.icm.mobile.proto.MessageProtos.Logout;
import org.umit.icm.mobile.proto.MessageProtos.NewTests;
import org.umit.icm.mobile.proto.MessageProtos.NewTestsResponse;
import org.umit.icm.mobile.proto.MessageProtos.NewVersion;
import org.umit.icm.mobile.proto.MessageProtos.NewVersionResponse;
import org.umit.icm.mobile.proto.MessageProtos.SendReportResponse;
import org.umit.icm.mobile.proto.MessageProtos.SendServiceReport;
import org.umit.icm.mobile.proto.MessageProtos.SendWebsiteReport;
import org.umit.icm.mobile.proto.MessageProtos.ServiceSuggestion;
import org.umit.icm.mobile.proto.MessageProtos.TestSuggestionResponse;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteSuggestion;

/**
 * Encodes the passed message using {@link Base64} and POSTs it to corresponding
 * webservice ClientResource. 
 */
public class SuperPeerResources {
	
	/**
	 * Returns a ClientResource object. url is passed to the ClientResource
	 * constructor.
	 * 
	 *	 
	                          
	@param  url  An object of the type String
	 *  	                          	
	                          
	@return      ClientResource
	 *  

	@see         ClientResource
	 */
	 public static ClientResource getClientResource(String url) {	
		 return new ClientResource(Constants.AGGREGATOR_URL + url);
	 }
	 
	/**
	 * Returns a AuthenticatePeerResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a AuthenticatePeerResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  authenticatePeer  An object of the type authenticatePeer
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      AuthenticatePeerResponse
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static AuthenticatePeerResponse authenticatePeer(
			 AuthenticatePeer authenticatePeer,
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {		 		 
		 Form form = new Form();
		 form.add(Constants.SUPER_MSG_KEY
				 , new String(Base64.encodeBase64(authenticatePeer.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 		 
		 return AuthenticatePeerResponse.parseFrom((Base64.decodeBase64(response.getText().getBytes())));
	 }
	 
	/**
	 * Returns a GetPeerListResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a GetPeerListResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  getPeerList  An object of the type GetPeerList
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      GetPeerListResponse
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static GetPeerListResponse getPeerList(
			 GetPeerList getPeerList, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 Form form = new Form();
		 form.add(Constants.SUPER_MSG_KEY
				 , new String(Base64.encodeBase64(getPeerList.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 		 
		 return GetPeerListResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	/**
	 * Returns a GetSuperPeerListResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a GetSuperPeerListResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  getSuperPeerList  An object of the type GetSuperPeerList
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      GetsuperPeerListResponse
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static GetSuperPeerListResponse getSuperPeerList(
			 GetSuperPeerList getSuperPeerList, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 Form form = new Form();
		 form.add(Constants.SUPER_MSG_KEY
				 , new String(Base64.encodeBase64(getSuperPeerList.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null));  
		 return GetSuperPeerListResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	/**
	 * Returns a GetEventsResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a GetEventsResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  getEvents  An object of the type GetEvents
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      GetEventsResponse
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static GetEventsResponse getEvents(
			 GetEvents getEvents, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 Form form = new Form();
		 form.add(Constants.SUPER_MSG_KEY
				 , new String(Base64.encodeBase64(getEvents.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 
		 return GetEventsResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	/**
	 * Returns a SendReportResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a SendReportResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  sendWebsiteReport  An object of the type SendWebsiteReport
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      SendReportResponse
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static SendReportResponse sendWebsiteReport(
			 SendWebsiteReport sendWebsiteReport, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 Form form = new Form();
		 form.add(Constants.SUPER_MSG_KEY
				 , new String(Base64.encodeBase64(sendWebsiteReport.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 
		 return SendReportResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	/**
	 * Returns a SendReportResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a SendReportResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  sendServiceReport  An object of the type SendServiceReport
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      SendReportResponse
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */		
	 public static SendReportResponse sendServiceReport(
			 SendServiceReport sendServiceReport, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 Form form = new Form();
		 form.add(Constants.SUPER_MSG_KEY
				 , new String(Base64.encodeBase64(sendServiceReport.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 
		 return SendReportResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	/**
	 * Returns a NewVersionResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a NewVersionResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  newVersion  An object of the type NewVersion
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      NewVersionResponse
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */		
	 public static NewVersionResponse checkVersion(
			 NewVersion newVersion, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 Form form = new Form();
		 form.add(Constants.SUPER_MSG_KEY
				 , new String(Base64.encodeBase64(newVersion.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 
		 return NewVersionResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	/**
	 * Returns a NewTestsResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a NewTestsResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  newTests  An object of the type NewTests
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      NewTestsResponse
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */		
	 public static NewTestsResponse checkTests(
			 NewTests newTests, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 Form form = new Form();
		 form.add(Constants.SUPER_MSG_KEY
				 , new String(Base64.encodeBase64(newTests.toByteArray())));
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null)); 
		 return NewTestsResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	/**
	 * Returns a TestSuggestionResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a TestSuggestionResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  websiteSuggestion  An object of the type WebsiteSuggestion
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      TestSuggestionResponse
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */		
	 public static TestSuggestionResponse sendWebsiteSuggestion(
			 WebsiteSuggestion websiteSuggestion, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 Form form = new Form();
		 form.add(Constants.SUPER_MSG_KEY
				 , new String(Base64.encodeBase64(websiteSuggestion.toByteArray())));		 		 		 
		 Representation response 
			 = clientResource.post(form.getWebRepresentation(null));  
		 return TestSuggestionResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	 
	/**
	 * Returns a TestSuggestionResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a TestSuggestionResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  serviceSuggestion  An object of the type ServiceSuggestion
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      TestSuggestionResponse
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */	
	 public static TestSuggestionResponse sendServiceSuggestion(
			 ServiceSuggestion serviceSuggestion, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 Form form = new Form();
		 form.add(Constants.SUPER_MSG_KEY
				 , new String(Base64.encodeBase64(serviceSuggestion.toByteArray())));		 		 		 
		 Representation response 
			 = clientResource.post(form.getWebRepresentation(null)); 
		 return TestSuggestionResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }
	/**
	 * Returns a LoginResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a LoginResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  login  An object of the type Login
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      LoginResponse
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static LoginResponse login(
			 Login login, 
			 ClientResource clientResource) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 Form form = new Form();
		 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(login.toByteArray())));		 		 		 
		 Representation response 
			 = clientResource.post(form.getWebRepresentation(null)); 
		 return LoginResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
	 }		 		

}