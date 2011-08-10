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
import org.umit.icm.mobile.process.Constants;
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

/**
 * Generates a ClientResource for each webservice and calls the relevant 
 * AggregatorResources and AggregatorActions methods.
 */

public class AggregatorRetrieve {
	
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link AggregatorResources#getClientResource}
	 * and {@link AggregatorResources#registerAgent} on the passed message and 
	 * {@link AggregatorActions#registerAgentAction} on the response message.
	 * 
	 *	 
	                          
	@param  registerAgent  An object of the type RegisterAgent
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         AggregatorResources
	 */
	 public synchronized static boolean registerAgent(RegisterAgent registerAgent) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
			ClientResource clientResource 
			= AggregatorResources.getClientResource( Constants.AGGR_REGISTER_AGENT);
			RegisterAgentResponse registerAgentResponse
			= AggregatorResources.registerAgent(registerAgent, clientResource); 
			return AggregatorActions.registerAgentAction(registerAgentResponse);			
	 }
	
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link AggregatorResources#getClientResource}
	 * and {@link AggregatorResources#getPeerList} on the passed message and 
	 * {@link AggregatorActions#getPeerListAction} on the response message.
	 * 
	 *	 
	                          
	@param  getPeerList  An object of the type GetPeerList
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         AggregatorResources
	 */	
	 public synchronized static boolean getPeerList(GetPeerList getPeerList) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_GET_PEER_LIST);
		 	GetPeerListResponse getPeerListResponse
		 	= AggregatorResources.getPeerList(getPeerList, clientResource);
		 	return AggregatorActions.getPeerListAction(getPeerListResponse);		 	
	 }
	 
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link AggregatorResources#getClientResource}
	 * and {@link AggregatorResources#getSuperPeerList} on the passed message and 
	 * {@link AggregatorActions#getSuperPeerListAction} on the response message.
	 * 
	 *	 
	                          
	@param  getSuperPeerList  An object of the type GetSuperPeerList
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         AggregatorResources
	 */	 
	 public synchronized static boolean getSuperPeerList(GetSuperPeerList getSuperPeerList) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_GET_PEER_SUPER_LIST);
		 	GetSuperPeerListResponse getSuperPeerListResponse
		 	= AggregatorResources.getSuperPeerList(getSuperPeerList, clientResource);
		 	return AggregatorActions.getSuperPeerListAction(getSuperPeerListResponse);		 	
	 }
	 
	 /**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link AggregatorResources#getClientResource}
	 * and {@link AggregatorResources#getEvents} on the passed message and 
	 * {@link AggregatorActions#getEventsAction} on the response message.
	 * 
	 *	 
	                          
	@param  getEvents  An object of the type GetEvents
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         AggregatorResources
	 */
	 public synchronized static boolean getEvents(GetEvents getEvents) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_GET_EVENTS);
		 	GetEventsResponse getEventsResponse 
		 	= AggregatorResources.getEvents(getEvents, clientResource);	 	
		 	return AggregatorActions.getEventsAction(getEventsResponse);
	 }
	 
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link AggregatorResources#getClientResource}
	 * and {@link AggregatorResources#sendWebsiteReport} on the passed message and 
	 * {@link AggregatorActions#sendReportAction} on the response message.
	 * 
	 *	 
	                          
	@param  sendWebsiteReport  An object of the type SendWebsiteReport
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         AggregatorResources
	 */
	 public synchronized static boolean sendWebsiteReport(SendWebsiteReport sendWebsiteReport) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_SEND_WEBSITE_REPORT);
		 	SendReportResponse sendReportResponse
		 	= AggregatorResources.sendWebsiteReport(sendWebsiteReport, clientResource);		 	
			return AggregatorActions.sendReportAction(sendReportResponse);
	 }
	 
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link AggregatorResources#getClientResource}
	 * and {@link AggregatorResources#sendServiceReport} on the passed message and 
	 * {@link AggregatorActions#sendReportAction} on the response message.
	 * 
	 *	 
	                          
	@param  sendServiceReport  An object of the type SendServiceReport
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         AggregatorResources
	 */
	 public synchronized static boolean sendServiceReport(SendServiceReport sendServiceReport) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_SEND_SERVICE_REPORT);
		 	SendReportResponse sendReportResponse 
		 	= AggregatorResources.sendServiceReport(sendServiceReport, clientResource);
		 	return AggregatorActions.sendReportAction(sendReportResponse);			
	 }	
	 
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link AggregatorResources#getClientResource}
	 * and {@link AggregatorResources#checkVersion} on the passed message and 
	 * {@link AggregatorActions#checkVersionAction} on the response message.
	 * 
	 *	 
	                          
	@param  newVersion  An object of the type NewVersion
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         AggregatorResources
	 */
	 public synchronized static boolean checkVersion(NewVersion newVersion) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_CHECK_VERSION);
		 	NewVersionResponse newVersionResponse
		 	= AggregatorResources.checkVersion(newVersion, clientResource);		 	
			return AggregatorActions.checkVersionAction(newVersionResponse);
	 }
	 
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link AggregatorResources#getClientResource}
	 * and {@link AggregatorResources#checkTests} on the passed message and 
	 * {@link AggregatorActions#newTestsAction} on the response message.
	 * 
	 *	 
	                          
	@param  newTests  An object of the type NewTests
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         AggregatorResources
	 */
	 public synchronized static boolean checkTests(NewTests newTests) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_CHECK_TESTS);
		 	NewTestsResponse newTestsResponse 
		 	= AggregatorResources.checkTests(newTests, clientResource);		 	
			return AggregatorActions.newTestsAction(newTestsResponse);
	 }
	 
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link AggregatorResources#getClientResource}
	 * and {@link AggregatorResources#sendWebsiteSuggestion} on the passed message and 
	 * {@link AggregatorActions#sendSuggestionAction} on the response message.
	 * 
	 *	 
	                          
	@param  websiteSuggestion  An object of the type WebsiteSuggestion
	 *  	                          	
	                          
	@return      boolean
	 * @throws RuntimeException 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         AggregatorResources
	 */
	 public synchronized static boolean sendWebsiteSuggestion(WebsiteSuggestion websiteSuggestion) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_WEBSITE_SUGGESTION);
		 	TestSuggestionResponse testSuggestionResponse
		 	= AggregatorResources.sendWebsiteSuggestion(websiteSuggestion, clientResource);		 	
			return AggregatorActions.sendSuggestionAction(testSuggestionResponse);
	 }

    /**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link AggregatorResources#getClientResource}
	 * and {@link AggregatorResources#sendServiceSuggestion} on the passed message and 
	 * {@link AggregatorActions#sendSuggestionAction} on the response message.
	 * 
	 *	 
	                          
	@param  serviceSuggestion  An object of the type ServiceSuggestion
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         AggregatorResources
	 */
	 public synchronized static boolean sendServiceSuggestion(ServiceSuggestion serviceSuggestion) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_SERVICE_SUGGESTION);
		 	TestSuggestionResponse testSuggestionResponse
		 	= AggregatorResources.sendServiceSuggestion(serviceSuggestion, clientResource);
		 	return AggregatorActions.sendSuggestionAction(testSuggestionResponse);			
	 }
	 
    /**
	 * Returns a boolean object from {@link AggregatorActions} method. 
	 * Calls {@link AggregatorResources#checkAggregatorStatus(org.umit.icm.mobile.proto.MessageProtos.CheckAggregator, ClientResource)}
	 * on the passed message 
	 * 
	 *	 
	                          
	@param  checkAggregator  An object of the type CheckAggregator
	 *  	                          	
	                          
	@return      boolean
	 *  		                          		
	 
	@see         AggregatorResources
	 */
	 public synchronized static boolean checkAggregatorStatus(CheckAggregator checkAggregator) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_CHECK_AGGREGATOR);
		 	CheckAggregatorResponse checkAggregatorResponse
		 	= AggregatorResources.checkAggregatorStatus(checkAggregator, clientResource);
		 	return AggregatorActions.checkAggregatorAction(checkAggregatorResponse);			
	 }
	 
	/**
	 * Returns a boolean object from {@link AggregatorActions} method. 
	 * Calls {@link AggregatorResources#login(org.umit.icm.mobile.proto.MessageProtos.Login, ClientResource)}
	 * on the passed message 
	 * 
	 *	 
	                          
	@param  login  An object of the type Login
	 *  	                          	
	                          
	@return      boolean
	 *  		                          		
	 
	@see         AggregatorResources
	 */
	 public synchronized static boolean login(Login login) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_LOGIN);
		 	LoginResponse loginResponse
		 	= AggregatorResources.login(login, clientResource);
		 	return AggregatorActions.loginAction(loginResponse);			
	 }
	 
	/**
	 * Calls {@link AggregatorResources#logout(org.umit.icm.mobile.proto.MessageProtos.Logout, ClientResource)}
	 * on the passed message 
	 * 
	 *	 
	                          
	@param  logout  An object of the type Logout
	 *  	                          	

	@see         AggregatorResources
	 */
	 public synchronized static void logout(Logout logout) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= AggregatorResources.getClientResource(Constants.AGGR_LOGOUT);
		 	AggregatorResources.logout(logout, clientResource);		 				
	 }
}