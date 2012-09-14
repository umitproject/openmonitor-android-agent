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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.CheckAggregator;
import org.umit.icm.mobile.proto.MessageProtos.CheckAggregatorResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetBanlist;
import org.umit.icm.mobile.proto.MessageProtos.GetBanlistResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetBannets;
import org.umit.icm.mobile.proto.MessageProtos.GetBannetsResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetEvents;
import org.umit.icm.mobile.proto.MessageProtos.GetEventsResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerList;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerList;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.Login;
import org.umit.icm.mobile.proto.MessageProtos.LoginResponse;
import org.umit.icm.mobile.proto.MessageProtos.LoginStep1;
import org.umit.icm.mobile.proto.MessageProtos.LoginStep2;
import org.umit.icm.mobile.proto.MessageProtos.Logout;
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
import org.umit.icm.mobile.utils.AggregatorCrypto;
import org.umit.icm.mobile.utils.RSACrypto;

/**
 * Encodes the passed message using {@link Base64} and POSTs it to corresponding
 * webservice ClientResource. 
 */
public class AggregatorResources {
	
	public static List<NameValuePair> getPairs(byte[] bytes) {
		String msg;
		if(Constants.AGGR_ENCRYPTION)
			msg = AggregatorCrypto.aesEncrypt(bytes);	 
		else
			msg = new String(bytes);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("agentID", Globals.runtimeParameters.getAgentID()));
		pairs.add(new BasicNameValuePair("msg", msg));
		return pairs;
	}
	
	public static byte[] getResponse(String call, byte[] msgBytes) 
			throws ClientProtocolException, IOException {
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost = new HttpPost(Constants.AGGREGATOR_URL + call);
		 httppost.setEntity(new UrlEncodedFormEntity(getPairs(msgBytes)));
		 
		 HttpResponse response = httpclient.execute(httppost);
		 if(Constants.DEBUG_MODE) { 
			 System.out.println(call + " response code: " + response.getStatusLine());
		 }
		 String responseBody = EntityUtils.toString(response.getEntity());	
		 if(Constants.AGGR_ENCRYPTION)
			 return AggregatorCrypto.aesDecrypt(responseBody.getBytes());
		 else
			 return responseBody.getBytes();
	}
	
	public static String getResponse(String call, List<NameValuePair> pairs) 
			throws ClientProtocolException, IOException {
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost = new HttpPost(Constants.AGGREGATOR_URL + call);
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 HttpResponse response = httpclient.execute(httppost);
		 if(Constants.DEBUG_MODE) { 
			 System.out.println(call + " response code: " + response.getStatusLine());
		 }
		 return EntityUtils.toString(response.getEntity());	 
	}
	 
	/**
	 * Returns a RegisterAgentResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a RegisterAgentResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  registerAgent  An object of the type RegisterAgent
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      RegisterAgentResponse
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static RegisterAgentResponse registerAgent(
			 RegisterAgent registerAgent) 
	 throws Exception{
		 try{
			 String msg;
			 if(Constants.AGGR_ENCRYPTION)
				 msg = AggregatorCrypto.aesEncrypt(registerAgent.toByteArray());	
			 else
				 msg = new String(registerAgent.toByteArray());
			 String key = AggregatorCrypto.rsaAggregatorPublicKeyEncypt(
				 Base64.encodeBase64(Globals.keyManager.getAESKey()));	 
	 	 
			 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			 pairs.add(new BasicNameValuePair("key", key ));
			 pairs.add(new BasicNameValuePair("agentID",Globals.runtimeParameters.getAgentID()));
			 pairs.add(new BasicNameValuePair("msg", msg));
		 
			 if(Constants.DEBUG_MODE) {
				 System.out.println("Sending key : " + key);
				 System.out.println("Sending agentID : " + Globals.runtimeParameters.getAgentID());
				 System.out.println("Sending msg : " + msg);	 
			 }
		 
			 String responseBody = getResponse(Constants.AGGR_REGISTER_AGENT, pairs); 

			 if(Constants.DEBUG_MODE)
				 System.out.println("------------------------------GOT THIS AS RESPONSE : " + responseBody);
			 
			 byte[] finalResponse;
			 if(Constants.AGGR_ENCRYPTION)
				 finalResponse = AggregatorCrypto.aesDecrypt(responseBody.getBytes());
			 else
				 finalResponse = responseBody.getBytes();
		
			 if(Constants.DEBUG_MODE) {
				 for(int i = 0; i < finalResponse.length; i++) {
					 System.out.println(finalResponse[i]);
				 }
			 }
		
			 return RegisterAgentResponse.parseFrom(finalResponse);
		} catch(Exception e){
			e.printStackTrace();
		}
		 
		return null;
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
	 * @throws Exception 
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static GetPeerListResponse getPeerList(GetPeerList getPeerList) 
	 throws Exception {
		 return GetPeerListResponse.parseFrom(getResponse(Constants.AGGR_GET_PEER_SUPER_LIST, 
				 getPeerList.toByteArray()));		 
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
	 * @throws Exception 
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static GetSuperPeerListResponse getSuperPeerList(GetSuperPeerList getSuperPeerList) 
	 throws Exception {
		 return GetSuperPeerListResponse.parseFrom(getResponse(Constants.AGGR_GET_PEER_SUPER_LIST,
				 getSuperPeerList.toByteArray()));		 
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
	 * @throws Exception 
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static GetEventsResponse getEvents(GetEvents getEvents) 
	 throws Exception {
		 return GetEventsResponse.parseFrom(getResponse(Constants.AGGR_GET_EVENTS,
				 getEvents.toByteArray()));	
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
	 * @throws Exception 
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static SendReportResponse sendWebsiteReport(SendWebsiteReport sendWebsiteReport) 
	 throws Exception {					 
		 return SendReportResponse.parseFrom(getResponse(Constants.AGGR_SEND_WEBSITE_REPORT,
				 sendWebsiteReport.toByteArray()));		 
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
	 * @throws Exception 
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */		
	 public static SendReportResponse sendServiceReport(SendServiceReport sendServiceReport) 
	 throws Exception {	 
		 return SendReportResponse.parseFrom(getResponse(Constants.AGGR_SEND_SERVICE_REPORT,
				 sendServiceReport.toByteArray()));
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
	 * @throws Exception 
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */		
	 public static NewVersionResponse checkVersion(NewVersion newVersion) 
	 throws Exception {
		 return NewVersionResponse.parseFrom(getResponse(Constants.AGGR_CHECK_VERSION,
				 newVersion.toByteArray()));		
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
	 * @throws Exception 
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */		
	 public static NewTestsResponse checkTests(NewTests newTests) 
	 throws Exception { 
		 return NewTestsResponse.parseFrom(getResponse(Constants.AGGR_CHECK_TESTS,
				 newTests.toByteArray()));		 
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
	 * @throws Exception 
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */		
	 public static TestSuggestionResponse sendWebsiteSuggestion(WebsiteSuggestion websiteSuggestion) 
	 throws Exception {		 
		 return TestSuggestionResponse.parseFrom(getResponse(Constants.AGGR_WEBSITE_SUGGESTION,
				 websiteSuggestion.toByteArray()));		 
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
	 * @throws Exception 
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */	
	 public static TestSuggestionResponse sendServiceSuggestion(ServiceSuggestion serviceSuggestion) 
	 throws Exception {			 
		 return TestSuggestionResponse.parseFrom(getResponse(Constants.AGGR_SERVICE_SUGGESTION,
				 serviceSuggestion.toByteArray()));
	 }
	 
	/** 
	 * Returns a CheckAggregatorResponse object. Encodes the passed message to
	 * {@link Base64} and generates a {@link Form} object for it. POSTs the 
	 * WebRepresentation of the {@link Form} object to the passed 
	 * {@link ClientResource}. Generates a CheckAggregatorResponse object from 
	 * the POST {@link Representation} response.
	 * 
	 *	 
	                          
	@param  checkAggregator  An object of the type CheckAggregator
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 *                           	
	                          
	@return      CheckAggregatorResponse
	 * @throws Exception 
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */					 
	 public static CheckAggregatorResponse checkAggregatorStatus(CheckAggregator checkAggregator) 
	 throws Exception {
		 return CheckAggregatorResponse.parseFrom(getResponse(Constants.AGGR_CHECK_AGGREGATOR,
				 checkAggregator.toByteArray()));
	 } 
	 
	 public static LoginResponse login(Login login) 
	 throws Exception {
		 LoginStep1 loginStep1 = loginStep1(login);
		 LoginResponse loginResponse = loginStep2(loginStep1);	 
		 return loginResponse;
	 } 
	 
	 public static LoginResponse loginStep2(LoginStep1 loginStep1)
	 throws Exception {
		 
		 String message = loginStep1.getChallenge();
		 if(Constants.DEBUG_MODE)
			 System.out.println("Challenge Received : " + message);
		 
		 byte[] messageByte = message.getBytes();		 
		 byte[] encryptedChallenge =  RSACrypto.Sign(Globals.keyManager.getMyPrivateKey(), messageByte); 
		 byte[] encodedEncryptedChallenge = Base64.encodeBase64(encryptedChallenge);
		 String encodedEncryptedChallengeString = new String(encodedEncryptedChallenge);
		 
		 if(Constants.DEBUG_MODE)
			 System.out.println("Signed Challenge Send : " + encodedEncryptedChallengeString); 
		 
		 LoginStep2 loginStep2 = LoginStep2.newBuilder()
				 .setProcessID(loginStep1.getProcessID())
				 .setCipheredChallenge(encodedEncryptedChallengeString)
				 .build();
		 	 
		 byte[] msg = Base64.encodeBase64(loginStep2.toByteArray());
		 String msg_string = new String(msg);	 
		 	 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("msg", msg_string));

		 String responseBody = getResponse(Constants.AGGR_LOGIN_2, pairs);		 
		 return LoginResponse.parseFrom(Base64.decodeBase64(responseBody.getBytes()));
		 
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
	 * @throws Exception 
	 *  
	                          
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static LoginStep1 loginStep1(Login login) 
	 throws Exception {
		 
		 String msg = AggregatorCrypto.encodeData(login.toByteArray());
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("msg", msg));

		 String responseBody = getResponse(Constants.AGGR_LOGIN_1, pairs);
		 byte[] responseByte = Base64.decodeBase64(responseBody.getBytes());		 
		 
		 return LoginStep1.parseFrom(responseByte);

	 }
	 
	/**
	 * Encodes the passed message to {@link Base64} and generates a 
	 * {@link Form} object for it. POSTs the WebRepresentation of 
	 * the {@link Form} object to the passed
	 * {@link ClientResource}. 
	 * 
	 *	 
	                          
	@param  logout  An object of the type Logout
	 *  	
	 
	@param  clientResource  An object of the type ClientResource
	 * @throws Exception 
	 *                           	
             
	@see         Base64
	 *
	 
	@see         ClientResource
	 */
	 public static void logout(Logout logout) 
	 throws Exception {
		 getResponse(Constants.AGGR_LOGOUT, logout.toByteArray());		 
	 }
	  
	 
	 public static GetBanlistResponse getBanlist(GetBanlist getBanlists) 
	 throws Exception{	 
		 return GetBanlistResponse.parseFrom(getResponse(Constants.AGGR_GET_BANLIST, 
				 getBanlists.toByteArray()));		 
	 }
	 
	 
	 public static GetBannetsResponse getBannets(GetBannets getBannets) 
	 throws Exception{
		 return GetBannetsResponse.parseFrom(getResponse(Constants.AGGR_GET_BANNETS,
				 getBannets.toByteArray()));
	 }

}