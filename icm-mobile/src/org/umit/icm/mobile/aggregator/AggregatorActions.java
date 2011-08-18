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

import org.umit.icm.mobile.process.ProcessActions;
import org.umit.icm.mobile.proto.MessageProtos.CheckAggregatorResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetEventsResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetTokenAndAsymmetricKeysResponse;
import org.umit.icm.mobile.proto.MessageProtos.LoginResponse;
import org.umit.icm.mobile.proto.MessageProtos.NewTestsResponse;
import org.umit.icm.mobile.proto.MessageProtos.NewVersionResponse;
import org.umit.icm.mobile.proto.MessageProtos.RegisterAgentResponse;
import org.umit.icm.mobile.proto.MessageProtos.SendReportResponse;
import org.umit.icm.mobile.proto.MessageProtos.TestSuggestionResponse;

/**
 * Takes actions based on the response messages received from the HTTP POSTs to
 * the aggregator.
 */

public class AggregatorActions {
	
	/**
	 * Returns a boolean object from   
	                          
	{@link ProcessActions} method. Calls {@link ProcessActions#updateAgentVersion} and 
	 * {@link ProcessActions#updateTestsVersion} on the ResponseHeader and passes the complete response
	 * message to {@link ProcessActions#registerAgent}.
	 * 
	 *	 
	                          
	@param  registerAgentResponse  An object of the type RegisterAgentResponse
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         ProcessActions
	*
	*
	@see         AggregatorRetrieve
	 */
	public static boolean registerAgentAction(RegisterAgentResponse registerAgentResponse) throws Exception {
		ProcessActions.updateAgentVersion(registerAgentResponse.getHeader());
		ProcessActions.updateTestsVersion(registerAgentResponse.getHeader());				
		return ProcessActions.registerAgent(registerAgentResponse);
	}
	
	/**
	 * Returns a boolean object from   
	                          
	{@link ProcessActions} method. Calls {@link ProcessActions#updateAgentVersion} and 
	 * {@link ProcessActions#updateTestsVersion} on the ResponseHeader and 
	 * passes the peer list to {@link ProcessActions#updatePeersList}.
	 * 
	 *	 
	                          
	@param  getPeerListResponse  An object of the type GetPeerListResponse
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         ProcessActions
	*
	*
	@see         AggregatorRetrieve
	 */	
	public static boolean getPeerListAction(GetPeerListResponse getPeerListResponse) throws Exception {
		ProcessActions.updateAgentVersion(getPeerListResponse.getHeader());
	 	ProcessActions.updateTestsVersion(getPeerListResponse.getHeader());
	 	return ProcessActions.updatePeersList(getPeerListResponse.getKnownPeersList());		
	}
	
	/**
	 * Returns a boolean object from   
	                          
	{@link ProcessActions} method. Calls {@link ProcessActions#updateAgentVersion} and 
	 * {@link ProcessActions#updateTestsVersion} on the ResponseHeader and 
	 * passes the super peer list to {@link ProcessActions#updateSuperPeersList}.
	 * 
	 *	 
	                          
	@param  getSuperPeerListResponse  An object of the type GetSuperPeerListResponse
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         ProcessActions
	*
	*
	@see         AggregatorRetrieve
	 */	
	public static boolean getSuperPeerListAction(GetSuperPeerListResponse getSuperPeerListResponse) throws Exception {
		ProcessActions.updateAgentVersion(getSuperPeerListResponse.getHeader());
	 	ProcessActions.updateTestsVersion(getSuperPeerListResponse.getHeader());
	 	return ProcessActions.updateSuperPeersList(getSuperPeerListResponse.getKnownSuperPeersList());		
	}
	
	/**
	 * Returns a boolean object from   
	                          
	{@link ProcessActions} method. Calls {@link ProcessActions#updateAgentVersion} and 
	 * {@link ProcessActions#updateTestsVersion} on the ResponseHeader and 
	 * passes the event list to {@link ProcessActions#updateEventsList}.
	 * 
	 *	 
	                          
	@param  getEventsResponse  An object of the type GetEventsResponse
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         ProcessActions
	*
	*
	@see         AggregatorRetrieve
	 */	
	public static boolean getEventsAction(GetEventsResponse getEventsResponse) throws Exception {
		ProcessActions.updateAgentVersion(getEventsResponse.getHeader());
	 	ProcessActions.updateTestsVersion(getEventsResponse.getHeader());
	 	return ProcessActions.updateEventsList(getEventsResponse.getEventsList());
	}
	
	/**
	 * Returns a boolean true. Calls {@link ProcessActions#updateAgentVersion} and 
	 * {@link ProcessActions#updateTestsVersion} on the ResponseHeader.
	 * 
	 *	 
	                          
	@param  sendReportResponse  An object of the type SendReportResponse
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         ProcessActions
	*
	*
	@see         AggregatorRetrieve
	 */	
	public static boolean sendReportAction(SendReportResponse sendReportResponse) throws Exception {
		ProcessActions.updateAgentVersion(sendReportResponse.getHeader());
		ProcessActions.updateTestsVersion(sendReportResponse.getHeader());
		return true;
	}
	
	/**
	 * Returns a boolean object from   
	                          
	{@link ProcessActions} method. Calls {@link ProcessActions#updateAgent} on 
	 * the ResponseHeader.
	 * 
	 *	 
	                          
	@param  newVersionResponse  An object of the type NewVersionResponse
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         ProcessActions
	*
	*
	@see         AggregatorRetrieve
	 */	
	public static boolean checkVersionAction(NewVersionResponse newVersionResponse) throws IOException {
		return ProcessActions.updateAgent(newVersionResponse);				
	}
	
	/**
	 * Returns a boolean object from   
	                          
	{@link ProcessActions} method. Calls {@link ProcessActions#updateTests} on 
	 * the ResponseHeader.
	 * 
	 *	 
	                          
	@param  newTestsResponse  An object of the type NewTestsResponse
	 *  	                          	
	                          
	@return      boolean
	 *  
	                          
	@see         ProcessActions
	*
	*
	@see         AggregatorRetrieve
	 */	
	public static boolean newTestsAction(NewTestsResponse newTestsResponse) throws IOException {
		return ProcessActions.updateTests(newTestsResponse.getTestsList());		
	}
	
	/**
	 * Returns a boolean true. Calls {@link ProcessActions#updateAgentVersion} and 
	 * {@link ProcessActions#updateTestsVersion} on the ResponseHeader.
	 * 
	 *	 
	                          
	@param  testSuggestionResponse  An object of the type TestSuggestionResponse
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         ProcessActions
	*
	*
	@see         AggregatorRetrieve
	 */
	public static boolean sendSuggestionAction(TestSuggestionResponse testSuggestionResponse) throws Exception {
		ProcessActions.updateAgentVersion(testSuggestionResponse.getHeader());
	 	ProcessActions.updateTestsVersion(testSuggestionResponse.getHeader());
		return true;
	}
	
	/**
	 * Returns a boolean object. Calls {@link ProcessActions#updateAgentVersion} and 
	 * {@link ProcessActions#updateTestsVersion} on the ResponseHeader.
	 * 
	 *	 
	                          
	@param  checkAggregatorResponse  An object of the type CheckAggregatorResponse
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         ProcessActions
	*
	*
	@see         AggregatorRetrieve
	 */
	public static boolean checkAggregatorAction(CheckAggregatorResponse checkAggregatorResponse) throws Exception {
		ProcessActions.updateAgentVersion(checkAggregatorResponse.getHeader());
	 	ProcessActions.updateTestsVersion(checkAggregatorResponse.getHeader());
	 	if(checkAggregatorResponse.getStatus().equalsIgnoreCase("ON"))
	 		return true;
	 	else
	 		return false;		
	}
	
	/**
	 * Returns a boolean true. Calls {@link ProcessActions#updateAgentVersion} and 
	 * {@link ProcessActions#updateTestsVersion} on the ResponseHeader.
	 * 
	 *	 
	                          
	@param  loginResponse  An object of the type LoginResponse
	 *  	                          	
	                          
	@return      boolean
	 * @throws Exception 
	 *  
	                          
	@see         ProcessActions
	*
	*
	@see         AggregatorRetrieve
	 */
	public static boolean loginAction(LoginResponse loginResponse) throws Exception {
		ProcessActions.updateAgentVersion(loginResponse.getHeader());
	 	ProcessActions.updateTestsVersion(loginResponse.getHeader());
		return true;
	}
	
	/**
	 * Calls {@link ProcessActions#getTokenAndAsymmetricKeys(GetTokenAndAsymmetricKeysResponse)}
	 * 
	 *	 
	                          
	@param  getTokenAndAsymmetricKeysResponse  An object of the type GetTokenAndAsymmetricKeysResponse
	 *  	                          	
	                          
	@see         ProcessActions
	*
	*
	@see         AggregatorRetrieve
	 */
	public static void getTokenAndAsymmetricKeysAction(GetTokenAndAsymmetricKeysResponse getTokenAndAsymmetricKeysResponse) 
	throws Exception {
		ProcessActions.getTokenAndAsymmetricKeys(getTokenAndAsymmetricKeysResponse);	 			
	}
}