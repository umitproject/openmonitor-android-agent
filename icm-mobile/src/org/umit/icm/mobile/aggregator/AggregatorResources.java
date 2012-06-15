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
import java.math.BigInteger;
import java.security.PublicKey;

import javax.crypto.SecretKey;
import javax.net.ssl.KeyManager;

import org.apache.commons.codec.binary.Base64;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
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
import org.umit.icm.mobile.proto.MessageProtos.RegisterAgent;
import org.umit.icm.mobile.proto.MessageProtos.RegisterAgentResponse;
import org.umit.icm.mobile.proto.MessageProtos.SendReportResponse;
import org.umit.icm.mobile.proto.MessageProtos.SendServiceReport;
import org.umit.icm.mobile.proto.MessageProtos.SendWebsiteReport;
import org.umit.icm.mobile.proto.MessageProtos.ServiceSuggestion;
import org.umit.icm.mobile.proto.MessageProtos.TestSuggestionResponse;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteSuggestion;
import org.umit.icm.mobile.utils.AESCrypto;
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
		 
		 System.out.println("IS IT EVEN GETTING HERE?!");
		 Form form = new Form();
		 
		 BigInteger mod =  new BigInteger("93740173714873692520486809225128030132198461438147249362129501889664779512410440220785650833428588898698591424963196756217514115251721698086685512592960422731696162410024157767288910468830028582731342024445624992243984053669314926468760439060317134193339836267660799899385710848833751883032635625332235630111");
		 BigInteger exp = new BigInteger("65537");
			
		 PublicKey aggrPublicKey= RSACrypto.generatePublicKey(mod,exp);
		
		 String key = "THis is my key";
		 byte[] aggkey = AESCrypto.generateKey(key.getBytes());
		 byte[] enc_key = RSACrypto.encryptPublic(aggrPublicKey,Base64.encodeBase64(aggkey));
		 byte[] send_key = Base64.encodeBase64(enc_key);
		 
		 String send_key_string = new String(send_key);
		 String enc_key_string = new String(enc_key);
		 
		 System.out.println("This is the encoded data ,length ("+ send_key_string.length()+") , encoded_data : "+send_key_string);
		 System.out.println("This is the decoded data : "+ enc_key_string);
		 
		 	
		 form.add("key", send_key_string);
		 form.add("agentID", Long.toString(Constants.DEFAULT_AGENT_ID));
		 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(registerAgent.toByteArray())));
		 
		 
		 Representation response= clientResource.post(form.getWebRepresentation(null));
		 
		
		
		 
		 return RegisterAgentResponse.parseFrom((Base64.decodeBase64(response.getText().getBytes())));
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
	 public static GetPeerListResponse getPeerList(
			 GetPeerList getPeerList, 
			 ClientResource clientResource) 
	 throws Exception {
		 Form form = new Form();
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] cipherBytes = AESCrypto.encrypt(symmetricKey, getPeerList.toByteArray());
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(cipherBytes)));
		 } else {
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(getPeerList.toByteArray())));
		 }		 
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null));
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] plainBytes = AESCrypto.decrypt(symmetricKey, 
					 Base64.decodeBase64(response.getText().getBytes()));
			 return GetPeerListResponse.parseFrom(plainBytes);
		 } else {
			 return GetPeerListResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
		 }
		 
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
	 public static GetSuperPeerListResponse getSuperPeerList(
			 GetSuperPeerList getSuperPeerList, 
			 ClientResource clientResource) 
	 throws Exception {
		 Form form = new Form();
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] cipherBytes = AESCrypto.encrypt(symmetricKey, getSuperPeerList.toByteArray());
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(cipherBytes)));
		 } else {			 		
			 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(getSuperPeerList.toByteArray())));
		 }
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null));
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] plainBytes = AESCrypto.decrypt(symmetricKey, 
					 Base64.decodeBase64(response.getText().getBytes()));
			 return GetSuperPeerListResponse.parseFrom(plainBytes);
		 } else {
			 return GetSuperPeerListResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
		 }
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
	 public static SendReportResponse sendWebsiteReport(
			 SendWebsiteReport sendWebsiteReport, 
			 ClientResource clientResource) 
	 throws Exception {
		 Form form = new Form();
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] cipherBytes = AESCrypto.encrypt(symmetricKey, sendWebsiteReport.toByteArray());
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(cipherBytes)));
		 } else {
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(sendWebsiteReport.toByteArray())));
		 }
		 
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null));
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] plainBytes = AESCrypto.decrypt(symmetricKey, 
					 Base64.decodeBase64(response.getText().getBytes()));
			 return SendReportResponse.parseFrom(plainBytes);
		 } else {
			 return SendReportResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
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
		 Form form = new Form();
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] cipherBytes = AESCrypto.encrypt(symmetricKey, sendServiceReport.toByteArray());
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(cipherBytes)));
		 } else {
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(sendServiceReport.toByteArray()))); 
		 }		 
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null));
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] plainBytes = AESCrypto.decrypt(symmetricKey, 
					 Base64.decodeBase64(response.getText().getBytes()));
			 return SendReportResponse.parseFrom(plainBytes);
		 } else {
			 return SendReportResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
		 }
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
	 public static NewTestsResponse checkTests(
			 NewTests newTests, 
			 ClientResource clientResource) 
	 throws Exception {
		 Form form = new Form();
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] cipherBytes = AESCrypto.encrypt(symmetricKey, newTests.toByteArray());
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(cipherBytes)));
		 } else {
			 form.add(Constants.AGGR_MSG_KEY
				 , new String(Base64.encodeBase64(newTests.toByteArray())));
		 }
		 Representation response 
		 = clientResource.post(form.getWebRepresentation(null));
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] plainBytes = AESCrypto.decrypt(symmetricKey, 
					 Base64.decodeBase64(response.getText().getBytes()));
			 return NewTestsResponse.parseFrom(plainBytes);
		 } else {
			 return NewTestsResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
		 }
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
		 Form form = new Form();
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] cipherBytes = AESCrypto.encrypt(symmetricKey, websiteSuggestion.toByteArray());
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(cipherBytes)));
		 } else {
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(websiteSuggestion.toByteArray())));
		 }
		 Representation response 
			 = clientResource.post(form.getWebRepresentation(null));
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] plainBytes = AESCrypto.decrypt(symmetricKey, 
					 Base64.decodeBase64(response.getText().getBytes()));
			 return TestSuggestionResponse.parseFrom(plainBytes);
		 } else {
			 return TestSuggestionResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
		 }
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
		 Form form = new Form();
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] cipherBytes = AESCrypto.encrypt(symmetricKey, serviceSuggestion.toByteArray());
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(cipherBytes)));
		 } else {
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(serviceSuggestion.toByteArray())));
		 }
		 Representation response 
			 = clientResource.post(form.getWebRepresentation(null));
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] plainBytes = AESCrypto.decrypt(symmetricKey, 
					 Base64.decodeBase64(response.getText().getBytes()));
			 return TestSuggestionResponse.parseFrom(plainBytes);
		 } else {
			 return TestSuggestionResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
		 }
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
	 public static LoginResponse login(
			 Login login, 
			 ClientResource clientResource) 
	 throws Exception {
		 Form form = new Form();
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] cipherBytes = AESCrypto.encrypt(symmetricKey, login.toByteArray());
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(cipherBytes)));
		 } else {
			 form.add(Constants.AGGR_MSG_KEY
					 , new String(Base64.encodeBase64(login.toByteArray())));
		 }
		 Representation response 
			 = clientResource.post(form.getWebRepresentation(null));
		 if(Constants.AGGR_ENCRYPTION == true) {
			 byte [] symmetricKey = CryptoKeyReader.getPeerSecretKey("aggregator");
			 byte[] plainBytes = AESCrypto.decrypt(symmetricKey, 
					 Base64.decodeBase64(response.getText().getBytes()));
			 return LoginResponse.parseFrom(plainBytes);
		 } else {
			 return LoginResponse.parseFrom(Base64.decodeBase64(response.getText().getBytes()));
		 }
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
	 


}