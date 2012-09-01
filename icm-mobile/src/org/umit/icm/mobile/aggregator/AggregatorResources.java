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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import org.umit.icm.mobile.utils.AESCrypto;
import org.umit.icm.mobile.utils.AggregatorCrypto;
import org.umit.icm.mobile.utils.CryptoKeyReader;
import org.umit.icm.mobile.utils.RSACrypto;

/**
 * Encodes the passed message using {@link Base64} and POSTs it to corresponding
 * webservice ClientResource. 
 */
public class AggregatorResources {
	
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
	 public synchronized static ClientResource getClientResource(String url) {	
		 return new ClientResource(Constants.AGGREGATOR_URL + url);
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
			 RegisterAgent registerAgent,
			 ClientResource clientResource) 
	 throws Exception{
		 try{
//		 Form form = new Form();
		 
		 String msg = AggregatorCrypto.aesEncrypt(registerAgent.toByteArray());
		 
		 String key=AggregatorCrypto.rsaAggregatorPublicKeyEncypt(Base64.encodeBase64(Globals.keyManager.getAESKey()));
		 
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost= new HttpPost(Constants.AGGREGATOR_URL + Constants.AGGR_REGISTER_AGENT);
		 
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("key", key ));
		 pairs.add(new BasicNameValuePair("agentID",Globals.runtimeParameters.getAgentID()));
		 pairs.add(new BasicNameValuePair("msg", msg));
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 
		 HttpResponse response = httpclient.execute(httppost);
		 
		 System.out.println("Sending key : " + key);
		 System.out.println("Sending agentID : " + Globals.runtimeParameters.getAgentID());
		 System.out.println("Sending msg : " + msg);
		 
		 
/*		 form.add("key", key);
		 form.add("agentID", Long.toString(Constants.DEFAULT_AGENT_ID));
		 form.add(Constants.AGGR_MSG_KEY, msg );
		 
		 Representation response=null;
		 try{
			 response= clientResource.post(form.getWebRepresentation(null));
			 System.out.println("Got response !! , response : " + response.getText());
			 System.out.println( "Got Context: " + clientResource.getContext() );
			 System.out.println( "Got Response: " + clientResource.getResponse());
			 System.out.println( "Got Resonse Attribute : " + clientResource.getResponseAttributes() );
			 System.out.println( "Got Resonse Entity: " + clientResource.getResponseEntity() );
		 }catch(ResourceException e){
			 System.out.println("Got here?!");
			 System.out.println(e.toString());
			 System.out.println("Status Below:");
			 System.out.println(e.getMessage());
			 e.printStackTrace();
		 }*/
		 
		String responseBody = EntityUtils.toString(response.getEntity());
		
		System.out.println("--------------------------------------------GOT THIS AS RESPONSE : "+ responseBody);
		 
		 
		byte[] final_response= AggregatorCrypto.aesDecrypt(responseBody.getBytes());
		
		for(int i=0;i<final_response.length;i++)
		{
			System.out.println(final_response[i]);
		}
		
		return RegisterAgentResponse.parseFrom(final_response);
		 }catch(Exception e){
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
	 public static GetPeerListResponse getPeerList(GetPeerList getPeerList, ClientResource clientResource) throws Exception {
		 
//		 Form form = new Form();
		 
		 String msg = AggregatorCrypto.aesEncrypt(getPeerList.toByteArray());
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost= new HttpPost(Constants.AGGREGATOR_URL+ Constants.AGGR_GET_PEER_SUPER_LIST);
		 
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("agentID", Globals.runtimeParameters.getAgentID()));
		 pairs.add(new BasicNameValuePair("msg", msg));
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 
		 HttpResponse response = httpclient.execute(httppost);
		 String responseBody = EntityUtils.toString(response.getEntity());
		 
		 
/*		 form.add("agentID", Globals.runtimeParameters.getAgentID());
		 form.add(Constants.AGGR_MSG_KEY, msg);
		 
		 Representation response= null;
		 
		try{
			 response = clientResource.post(form.getWebRepresentation(null));
		}catch(Exception e){
			 e.printStackTrace();
		}*/
		
		byte[] final_response= AggregatorCrypto.aesDecrypt(responseBody.getBytes());
		 
		return GetPeerListResponse.parseFrom(final_response);
		 
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
	 public static GetSuperPeerListResponse getSuperPeerList(GetSuperPeerList getSuperPeerList, ClientResource clientResource) throws Exception {
//		 Form form = new Form();
		 String msg = AggregatorCrypto.aesEncrypt(getSuperPeerList.toByteArray());
		 
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost= new HttpPost(Constants.AGGREGATOR_URL+ Constants.AGGR_GET_PEER_SUPER_LIST);
		 
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("agentID", Globals.runtimeParameters.getAgentID()));
		 pairs.add(new BasicNameValuePair("msg", msg));
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 
		 HttpResponse response = httpclient.execute(httppost);
		 String responseBody = EntityUtils.toString(response.getEntity());
		 
		 
/*		 form.add("agentID", Globals.runtimeParameters.getAgentID());
		 form.add(Constants.AGGR_MSG_KEY, msg);
		 
		 Representation response= null;
		 
		try{
			 response = clientResource.post(form.getWebRepresentation(null));
		}catch(Exception e){
			 e.printStackTrace();
		}*/
		
		byte[] final_response= AggregatorCrypto.aesDecrypt(responseBody.getBytes());
		 
		return GetSuperPeerListResponse.parseFrom(final_response);
		 
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
	 public static GetEventsResponse getEvents(
			 GetEvents getEvents, 
			 ClientResource clientResource) 
	 throws Exception {
		 Form form = new Form();
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] cipherBytes = AESCrypto.encrypt(symmetricKey, getEvents.toByteArray());
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(cipherBytes)));
		 } else {
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(getEvents.toByteArray()))); 
		 }		 
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null));
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] plainBytes = AESCrypto.decrypt(symmetricKey, 
					 Base64.decodeBase64(response.getText().getBytes()));
			 return GetEventsResponse.parseFrom(plainBytes);
		 } else {
			 return GetEventsResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
		 }
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
	 public static SendReportResponse sendWebsiteReport(SendWebsiteReport sendWebsiteReport, ClientResource clientResource) throws Exception {
//		Form form = new Form();
		 
		 String msg = AggregatorCrypto.aesEncrypt(sendWebsiteReport.toByteArray());
		
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost= new HttpPost(Constants.AGGREGATOR_URL + Constants.AGGR_SEND_WEBSITE_REPORT);
		 
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("agentID", Globals.runtimeParameters.getAgentID()));
		 pairs.add(new BasicNameValuePair("msg", msg));
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 
		 HttpResponse response = httpclient.execute(httppost);
		 String responseBody = EntityUtils.toString(response.getEntity());
			
		byte[] final_response= AggregatorCrypto.aesDecrypt(responseBody.getBytes());
			 
		return SendReportResponse.parseFrom(final_response);
		 
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
	 public static SendReportResponse sendServiceReport(
			 SendServiceReport sendServiceReport, 
			 ClientResource clientResource) 
	 throws Exception {
		 String msg = AggregatorCrypto.aesEncrypt(sendServiceReport.toByteArray());
			
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost= new HttpPost(Constants.AGGREGATOR_URL + Constants.AGGR_SEND_SERVICE_REPORT);
		 
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("agentID", Globals.runtimeParameters.getAgentID()));
		 pairs.add(new BasicNameValuePair("msg", msg));
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 
		 HttpResponse response = httpclient.execute(httppost);
		 String responseBody = EntityUtils.toString(response.getEntity());
			
		byte[] final_response= AggregatorCrypto.aesDecrypt(responseBody.getBytes());
			 
		return SendReportResponse.parseFrom(final_response);
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
	 public static NewVersionResponse checkVersion(
			 NewVersion newVersion, 
			 ClientResource clientResource) 
	 throws Exception {
		 Form form = new Form();
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] cipherBytes = AESCrypto.encrypt(symmetricKey, newVersion.toByteArray());
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(cipherBytes)));
		 } else {
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(newVersion.toByteArray())));
		 }
		 
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null));
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] plainBytes = AESCrypto.decrypt(symmetricKey, 
					 Base64.decodeBase64(response.getText().getBytes()));
			 return NewVersionResponse.parseFrom(plainBytes);
		 } else {
			 return NewVersionResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
		 }
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
	 public static NewTestsResponse checkTests(NewTests newTests, ClientResource clientResource) throws Exception {

//		 Form form = new Form();
		 
		 String msg = AggregatorCrypto.aesEncrypt(newTests.toByteArray());
		 
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost= new HttpPost(Constants.AGGREGATOR_URL + Constants.AGGR_CHECK_TESTS);
		 
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("agentID", Globals.runtimeParameters.getAgentID()));
		 pairs.add(new BasicNameValuePair("msg", msg));
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 
		 HttpResponse response = httpclient.execute(httppost);
		 String responseBody = EntityUtils.toString(response.getEntity());
		 
		 
/*		 form.add("agentID", Globals.runtimeParameters.getAgentID());
		 form.add(Constants.AGGR_MSG_KEY, msg);
		 
		 Representation response= null;
		 
		try{
			 response = clientResource.post(form.getWebRepresentation(null));
		}catch(Exception e){
			 e.printStackTrace();
		}*/
		 
		 
		byte[] final_response= AggregatorCrypto.aesDecrypt(responseBody.getBytes());
		
		 
		 return NewTestsResponse.parseFrom(final_response);
		 
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
	 public static TestSuggestionResponse sendWebsiteSuggestion(
			 WebsiteSuggestion websiteSuggestion, 
			 ClientResource clientResource) 
	 throws Exception {
		 
		 String msg = AggregatorCrypto.aesEncrypt(websiteSuggestion.toByteArray());
			
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost= new HttpPost(Constants.AGGREGATOR_URL + Constants.AGGR_WEBSITE_SUGGESTION);
		 
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("agentID", Globals.runtimeParameters.getAgentID()));
		 pairs.add(new BasicNameValuePair("msg", msg));
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 
		 HttpResponse response = httpclient.execute(httppost);
		 String responseBody = EntityUtils.toString(response.getEntity());
			
		byte[] final_response= AggregatorCrypto.aesDecrypt(responseBody.getBytes());
			 
		return TestSuggestionResponse.parseFrom(final_response);
		 
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
	 public static TestSuggestionResponse sendServiceSuggestion(
			 ServiceSuggestion serviceSuggestion, 
			 ClientResource clientResource) 
	 throws Exception {
		 String msg = AggregatorCrypto.aesEncrypt(serviceSuggestion.toByteArray());
			
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost= new HttpPost(Constants.AGGREGATOR_URL + Constants.AGGR_SERVICE_SUGGESTION);
		 
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("agentID", Globals.runtimeParameters.getAgentID()));
		 pairs.add(new BasicNameValuePair("msg", msg));
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 
		 HttpResponse response = httpclient.execute(httppost);
		 String responseBody = EntityUtils.toString(response.getEntity());
			
		byte[] final_response= AggregatorCrypto.aesDecrypt(responseBody.getBytes());
			 
		return TestSuggestionResponse.parseFrom(final_response);
	 }
	 
	/**itna 
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
	 public static CheckAggregatorResponse checkAggregatorStatus(
			 CheckAggregator checkAggregator, 
			 ClientResource clientResource) 
	 throws Exception {
		 Form form = new Form();
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] cipherBytes = AESCrypto.encrypt(symmetricKey, checkAggregator.toByteArray());
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(cipherBytes)));
		 } else {
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(checkAggregator.toByteArray())));
		 }
		 Representation response 
			 = clientResource.post(form.getWebRepresentation(null));
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] plainBytes = AESCrypto.decrypt(symmetricKey, 
					 Base64.decodeBase64(response.getText().getBytes()));
			 return CheckAggregatorResponse.parseFrom(plainBytes);
		 } else {
			 return CheckAggregatorResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
		 }
	 }
	 
	 
	 
	 public static LoginResponse login(Login login,ClientResource clientResource,ClientResource clientResource2) throws Exception
	 {
		 LoginStep1 loginStep1 = loginStep1(login,clientResource);
		 
		 LoginResponse loginResponse = loginStep2(loginStep1,clientResource2);
		 
		 return loginResponse;
	 }
	 
	 
	 
	 
	 public static LoginResponse loginStep2(LoginStep1 loginStep1, ClientResource clientResource) throws Exception {
		 
		 String message =loginStep1.getChallenge();
		 
		 System.out.println("Challenge Received : " + message);
		 
//		 Form form = new Form();
		 
		 byte[] message_byte=message.getBytes();
		 
		 byte[] encryptedChallenge =  RSACrypto.Sign(Globals.keyManager.getMyPrivateKey(), message_byte);
		 
		 byte[] encodedEncryptedChallenge = Base64.encodeBase64(encryptedChallenge);
		 
		 String encodedEncryptedChallenge_string = new String(encodedEncryptedChallenge);
		 
		 System.out.println("Signed Challenge Send : " + encodedEncryptedChallenge_string);
		 
		 
		 LoginStep2 loginStep2 = LoginStep2.newBuilder()
				 .setProcessID(loginStep1.getProcessID())
				 .setCipheredChallenge(encodedEncryptedChallenge_string)
				 .build();
		 
		 
		 
		 byte[] msg = Base64.encodeBase64(loginStep2.toByteArray());
		 
		 String msg_string = new String(msg);
		 
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost= new HttpPost(Constants.AGGREGATOR_URL+ Constants.AGGR_LOGIN_2);
		 
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("msg", msg_string));
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 
		 HttpResponse response = httpclient.execute(httppost);
		 String responseBody = EntityUtils.toString(response.getEntity());
		 
		 
/*		 form.add(Constants.AGGR_MSG_KEY,msg_string);
		 
		 
		 Representation response=null;
		 try{
		 response= clientResource.post(form.getWebRepresentation(null));
		 System.out.println("loginStep2 Response: " + clientResource.getResponse().toString());
		 }catch(ResourceException e){
			 e.printStackTrace();
		 }*/
		 
		 
		 
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
	 public static LoginStep1 loginStep1(
			 Login login, 
			 ClientResource clientResource) 
	 throws Exception {
//		 Form form = new Form();
		 
		 String msg = AggregatorCrypto.encodeData(login.toByteArray());
		 
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost= new HttpPost(Constants.AGGREGATOR_URL + Constants.AGGR_LOGIN_1);
		 
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("msg", msg));
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 
		 HttpResponse response = httpclient.execute(httppost);
		 String responseBody = EntityUtils.toString(response.getEntity());
		 byte[] response_byte = Base64.decodeBase64(responseBody.getBytes());		 
		 
		 return LoginStep1.parseFrom(response_byte);

/*		 form.add(Constants.AGGR_MSG_KEY, msg);
		 
		 Representation response=null;
		 try{
		 response= clientResource.post(form.getWebRepresentation(null));
		 }catch(ResourceException e){
			 e.printStackTrace();
		 }*/
		 
		 
		 
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
	 public static void logout(
			 Logout logout, 
			 ClientResource clientResource) 
	 throws Exception {
		 Form form = new Form();
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] cipherBytes = AESCrypto.encrypt(symmetricKey, logout.toByteArray());
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(cipherBytes)));
		 } else {
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(logout.toByteArray())));
		 }
			 clientResource.post(form.getWebRepresentation(null)); 				 
	 }
	 
	 
	 
	 public static GetBanlistResponse getBanlist(GetBanlist getBanlists,ClientResource clientResource) throws Exception{
		 
//		 Form form = new Form();
		 
		 String msg= AggregatorCrypto.aesEncrypt(getBanlists.toByteArray());
		 
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost= new HttpPost(Constants.AGGREGATOR_URL + Constants.AGGR_GET_BANLIST);
		 
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("agentID", Globals.runtimeParameters.getAgentID()));
		 pairs.add(new BasicNameValuePair("msg", msg));
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 
		 HttpResponse response = httpclient.execute(httppost);
		 String responseBody = EntityUtils.toString(response.getEntity());
		 
/*		 form.add("agentID", Globals.runtimeParameters.getAgentID());
		 form.add(Constants.AGGR_MSG_KEY, msg);
		 Representation response =null;
		 try{
		 response = clientResource.post(form.getWebRepresentation(null));
		 }catch(Exception e){
			 e.printStackTrace();
		 }*/
		 
		 byte[] final_response= AggregatorCrypto.aesDecrypt(responseBody.getBytes());
		 
		 return GetBanlistResponse.parseFrom(final_response);
		 
	 }
	 
	 
	 public static GetBannetsResponse getBannets(GetBannets getBannets, ClientResource clientResource)throws Exception{
		 
//		 Form form = new Form();
		 
		 String msg = AggregatorCrypto.aesEncrypt(getBannets.toByteArray());
		 
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpPost httppost= new HttpPost(Constants.AGGREGATOR_URL + Constants.AGGR_GET_BANLIST);
		 
		 
		 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		 pairs.add(new BasicNameValuePair("agentID", Globals.runtimeParameters.getAgentID()));
		 pairs.add(new BasicNameValuePair("msg", msg));
		 httppost.setEntity(new UrlEncodedFormEntity(pairs));
		 
		 
		 HttpResponse response = httpclient.execute(httppost);
		 String responseBody = EntityUtils.toString(response.getEntity());
		 
		 
		 
/*		 form.add("agentID", Globals.runtimeParameters.getAgentID());
		 form.add(Constants.AGGR_MSG_KEY, msg);
		 
		 Representation response= null;
		 
		try{
			 response = clientResource.post(form.getWebRepresentation(null));
		}catch(Exception e){
			 e.printStackTrace();
		}*/
		 
		byte[] final_response= AggregatorCrypto.aesDecrypt(responseBody.getBytes());
		
		 
		 return GetBannetsResponse.parseFrom(final_response);
	 }

}