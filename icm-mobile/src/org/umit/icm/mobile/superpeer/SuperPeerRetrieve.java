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

import org.restlet.resource.ClientResource;
import org.umit.icm.mobile.aggregator.AggregatorActions;
import org.umit.icm.mobile.p2p.P2PActions;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.proto.MessageProtos.AuthenticatePeer;
import org.umit.icm.mobile.proto.MessageProtos.AuthenticatePeerResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetEvents;
import org.umit.icm.mobile.proto.MessageProtos.GetEventsResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerList;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerList;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.Login;
import org.umit.icm.mobile.proto.MessageProtos.LoginResponse;
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
 * Generates a ClientResource for each webservice and calls the relevant 
 * SuperPeerResources and AggregatorActions methods.
 */

public class SuperPeerRetrieve {
	
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link SuperPeerResources#getClientResource}
	 * and {@link SuperPeerResources#authenticatePeer} on the passed message and 
	 * {@link P2PActions#authenticatePeerAction} on the response message.
	 * 
	 *	 
	                          
	@param  authenticatePeer  An object of the type AuthenticatePeer
	 *  	                          	
	                          
	@see         AggregatorActions
	 *
	 
	@see         SuperPeerResources
	 */
	 public static void authenticatePeer(
			AuthenticatePeer authenticatePeer, String peerIP) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
			ClientResource clientResource 
			= SuperPeerResources.getClientResource(peerIP, Constants.SUPER_AUTHENTICATE_PEER);
			AuthenticatePeerResponse authenticatePeerResponse			
			= SuperPeerResources.authenticatePeer(authenticatePeer, clientResource); 
			P2PActions.authenticatePeerAction(authenticatePeerResponse, peerIP);			
	 }
	
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link SuperPeerResources#getClientResource}
	 * and {@link SuperPeerResources#getPeerList} on the passed message and 
	 * {@link AggregatorActions#getPeerListAction} on the response message.
	 * 
	 *	 
	                          
	@param  getPeerList  An object of the type GetPeerList
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         SuperPeerResources
	 */	
	 public static boolean getPeerList(
			GetPeerList getPeerList, String peerIP) 
	 throws Exception {
		 	ClientResource clientResource 
		 	= SuperPeerResources.getClientResource(peerIP, Constants.SUPER_GET_PEER_LIST);
		 	GetPeerListResponse getPeerListResponse
		 	= SuperPeerResources.getPeerList(getPeerList, clientResource);
		 	return AggregatorActions.getPeerListAction(getPeerListResponse);		 	
	 }
	 
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link SuperPeerResources#getClientResource}
	 * and {@link SuperPeerResources#getSuperPeerList} on the passed message and 
	 * {@link AggregatorActions#getSuperPeerListAction} on the response message.
	 * 
	 *	 
	                          
	@param  getSuperPeerList  An object of the type GetSuperPeerList
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         SuperPeerResources
	 */	 
	 public static boolean getSuperPeerList(
			GetSuperPeerList getSuperPeerList, String peerIP) 
	 throws Exception {
		 	ClientResource clientResource 
		 	= SuperPeerResources.getClientResource(peerIP, Constants.SUPER_GET_PEER_SUPER_LIST);
		 	GetSuperPeerListResponse getSuperPeerListResponse
		 	= SuperPeerResources.getSuperPeerList(getSuperPeerList, clientResource);
		 	return AggregatorActions.getSuperPeerListAction(getSuperPeerListResponse);		 	
	 }
	 
	 /**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link SuperPeerResources#getClientResource}
	 * and {@link SuperPeerResources#getEvents} on the passed message and 
	 * {@link AggregatorActions#getEventsAction} on the response message.
	 * 
	 *	 
	                          
	@param  getEvents  An object of the type GetEvents
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         SuperPeerResources
	 */
	 public static boolean getEvents(
			GetEvents getEvents, String peerIP) 
	 throws Exception {
		 	ClientResource clientResource 
		 	= SuperPeerResources.getClientResource(peerIP, Constants.SUPER_GET_EVENTS);
		 	GetEventsResponse getEventsResponse 
		 	= SuperPeerResources.getEvents(getEvents, clientResource);	 	
		 	return AggregatorActions.getEventsAction(getEventsResponse);
	 }
	 
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link SuperPeerResources#getClientResource}
	 * and {@link SuperPeerResources#sendWebsiteReport} on the passed message and 
	 * {@link AggregatorActions#sendReportAction} on the response message.
	 * 
	 *	 
	                          
	@param  sendWebsiteReport  An object of the type SendWebsiteReport
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         SuperPeerResources
	 */
	 public static boolean sendWebsiteReport(
			SendWebsiteReport sendWebsiteReport, String peerIP) 
	 throws Exception {
		 	ClientResource clientResource 
		 	= SuperPeerResources.getClientResource(peerIP, Constants.SUPER_SEND_WEBSITE_REPORT);
		 	SendReportResponse sendReportResponse
		 	= SuperPeerResources.sendWebsiteReport(sendWebsiteReport, clientResource);		 	
			return AggregatorActions.sendReportAction(sendReportResponse);
	 }
	 
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link SuperPeerResources#getClientResource}
	 * and {@link SuperPeerResources#sendServiceReport} on the passed message and 
	 * {@link AggregatorActions#sendReportAction} on the response message.
	 * 
	 *	 
	                          
	@param  sendServiceReport  An object of the type SendServiceReport
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         SuperPeerResources
	 */
	 public static boolean sendServiceReport(
			SendServiceReport sendServiceReport, String peerIP) 
	 throws Exception {
		 	ClientResource clientResource 
		 	= SuperPeerResources.getClientResource(peerIP, Constants.SUPER_SEND_SERVICE_REPORT);
		 	SendReportResponse sendReportResponse 
		 	= SuperPeerResources.sendServiceReport(sendServiceReport, clientResource);
		 	return AggregatorActions.sendReportAction(sendReportResponse);			
	 }	
	 
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link SuperPeerResources#getClientResource}
	 * and {@link SuperPeerResources#checkVersion} on the passed message and 
	 * {@link AggregatorActions#checkVersionAction} on the response message.
	 * 
	 *	 
	                          
	@param  newVersion  An object of the type NewVersion
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         SuperPeerResources
	 */
	 public static boolean checkVersion(
			NewVersion newVersion, String peerIP) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= SuperPeerResources.getClientResource(peerIP, Constants.SUPER_CHECK_VERSION);
		 	NewVersionResponse newVersionResponse
		 	= SuperPeerResources.checkVersion(newVersion, clientResource);		 	
			return AggregatorActions.checkVersionAction(newVersionResponse);
	 }
	 
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link SuperPeerResources#getClientResource}
	 * and {@link SuperPeerResources#checkTests} on the passed message and 
	 * {@link AggregatorActions#newTestsAction} on the response message.
	 * 
	 *	 
	                          
	@param  newTests  An object of the type NewTests
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         SuperPeerResources
	 */
	 public static boolean checkTests(
			NewTests newTests, String peerIP) 
	 throws UnsupportedEncodingException, IOException, RuntimeException {
		 	ClientResource clientResource 
		 	= SuperPeerResources.getClientResource(peerIP, Constants.SUPER_CHECK_TESTS);
		 	NewTestsResponse newTestsResponse 
		 	= SuperPeerResources.checkTests(newTests, clientResource);		 	
			return AggregatorActions.newTestsAction(newTestsResponse);
	 }
	 
	/**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link SuperPeerResources#getClientResource}
	 * and {@link SuperPeerResources#sendWebsiteSuggestion} on the passed message and 
	 * {@link AggregatorActions#sendSuggestionAction} on the response message.
	 * 
	 *	 
	                          
	@param  websiteSuggestion  An object of the type WebsiteSuggestion
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 * @see         AggregatorActions
	 *
	 
	@see         SuperPeerResources
	 */
	 public static boolean sendWebsiteSuggestion(
			WebsiteSuggestion websiteSuggestion, String peerIP) 
	 throws Exception {
		 	ClientResource clientResource 
		 	= SuperPeerResources.getClientResource(peerIP, Constants.SUPER_WEBSITE_SUGGESTION);
		 	TestSuggestionResponse testSuggestionResponse
		 	= SuperPeerResources.sendWebsiteSuggestion(websiteSuggestion, clientResource);		 	
			return AggregatorActions.sendSuggestionAction(testSuggestionResponse);
	 }

    /**
	 * Returns a boolean object from   
	                          
	{@link AggregatorActions} method. Calls {@link SuperPeerResources#getClientResource}
	 * and {@link SuperPeerResources#sendServiceSuggestion} on the passed message and 
	 * {@link AggregatorActions#sendSuggestionAction} on the response message.
	 * 
	 *	 
	                          
	@param  serviceSuggestion  An object of the type ServiceSuggestion
	 *  	                          	
	                          
	@return      boolean
     * @throws Exception 
	 *  
	                          
	@see         AggregatorActions
	 *
	 
	@see         SuperPeerResources
	 */
	 public static boolean sendServiceSuggestion(
			ServiceSuggestion serviceSuggestion, String peerIP) 
	 throws Exception {
		 	ClientResource clientResource 
		 	= SuperPeerResources.getClientResource(peerIP, Constants.SUPER_SERVICE_SUGGESTION);
		 	TestSuggestionResponse testSuggestionResponse
		 	= SuperPeerResources.sendServiceSuggestion(serviceSuggestion, clientResource);
		 	return AggregatorActions.sendSuggestionAction(testSuggestionResponse);			
	 }
	 
   
	/**
	 * Returns a boolean object from {@link AggregatorActions} method. 
	 * Calls {@link SuperPeerResources#login(org.umit.icm.mobile.proto.MessageProtos.Login, ClientResource)}
	 * on the passed message 
	 * 
	 *	 
	                          
	@param  login  An object of the type Login
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  		                          		
	 
	@see         SuperPeerResources
	 */
	 public static boolean login(
			Login login, String peerIP) 
	 throws Exception {
		 	ClientResource clientResource 
		 	= SuperPeerResources.getClientResource(peerIP, Constants.SUPER_LOGIN);
		 	LoginResponse loginResponse
		 	= SuperPeerResources.login(login, clientResource);
		 	return AggregatorActions.loginAction(loginResponse);			
	 }
}