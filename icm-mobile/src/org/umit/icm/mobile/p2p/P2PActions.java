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

package org.umit.icm.mobile.p2p;

import java.io.IOException;

import org.umit.icm.mobile.process.ProcessActions;
import org.umit.icm.mobile.proto.MessageProtos.AuthenticatePeerResponse;
import org.umit.icm.mobile.proto.MessageProtos.ForwardingMessageResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetEventsResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.NewTestsResponse;
import org.umit.icm.mobile.proto.MessageProtos.P2PGetPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.P2PGetSuperPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.SendReportResponse;
import org.umit.icm.mobile.proto.MessageProtos.TestSuggestionResponse;
import org.umit.icm.mobile.utils.CryptoKeyWriter;

/**
 * Performs actions on p2p response messages.
 */

public class P2PActions {
	
	/**
	 * Called after a {@link SendReportResponse} message is received.
	 * Calls {@link ProcessActions} methods.
	 * 
	 * 
	 
	 @param sendReportResponse Response message of type {@link SendReportResponse}
	 *
	 
	 @see ProcessActions
	 */
	public static void sendReportAction(SendReportResponse sendReportResponse) {
		try {
			ProcessActions.updateAgentVersion(sendReportResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ProcessActions.updateTestsVersion(sendReportResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Called after a {@link GetEventsResponse} message is received.
	 * Calls {@link ProcessActions} methods. Also updates the global
	 * events list
	 * 
	 * 
	 
	 @param getEventsResponse Response message of type {@link GetEventsResponse}
	 *
	 
	 @see ProcessActions
	 */
	public static void receiveEventsAction(GetEventsResponse getEventsResponse) {
		try {
			ProcessActions.updateAgentVersion(getEventsResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ProcessActions.updateTestsVersion(getEventsResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ProcessActions.updateEventsList(getEventsResponse.getEventsList());
	}
	
	/**
	 * Called after a {@link GetPeerListResponse} message is received.
	 * Calls {@link ProcessActions} methods. Also updates the global
	 * peers list.
	 * 
	 * 
	 
	 @param getPeerListResponse Response message of type {@link GetPeerListResponse}
	 *
	 
	 @see ProcessActions
	 */
	public static void getPeerListAction(P2PGetPeerListResponse getPeerListResponse) {	
		ProcessActions.updatePeersList(getPeerListResponse.getPeersList());
	}
	
	/**
	 * Called after a {@link GetSuperPeerListResponse} message is received.
	 * Calls {@link ProcessActions} methods. Also updates the global
	 * super peers list.
	 * 
	 * 
	 
	 @param getSuperPeerListResponse Response message of type {@link GetSuperPeerListResponse}
	 *
	 
	 @see ProcessActions
	 */
	public static void getSuperPeerListAction(P2PGetSuperPeerListResponse getSuperPeerListResponse) {		
		ProcessActions.updateSuperPeersList(getSuperPeerListResponse.getPeersList());
	}
	
	/**
	 * Called after a {@link NewTestsResponse} message is received.
	 * Calls {@link ProcessActions} methods. Also updates the global
	 * test list.
	 * 
	 * 
	 
	 @param newTestsResponse Response message of type {@link NewTestsResponse}
	 *
	 
	 @see ProcessActions
	 */
	public static void receiveTaskListAction(NewTestsResponse newTestsResponse) {
		try {
			ProcessActions.updateAgentVersion(newTestsResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ProcessActions.updateTestsVersion(newTestsResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ProcessActions.updateTests(newTestsResponse.getTestsList());
	}
	
	/**
	 * Called after a {@link TestSuggestionResponse} message is received.
	 * Calls {@link ProcessActions} methods. 
	 * 
	 * 
	 
	 @param testSuggestionResponse Response message of type {@link TestSuggestionResponse}
	 *
	 
	 @see ProcessActions
	 */
	public static void sendSuggestionAction(TestSuggestionResponse testSuggestionResponse) {
		try {
			ProcessActions.updateAgentVersion(testSuggestionResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ProcessActions.updateTestsVersion(testSuggestionResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Called after a {@link AuthenticatePeerResponse} message is received.
	 * Calls {@link CryptoKeyWriter#writePeerSecretKey(byte[], String)}
	 * 
	 * 
	 
	 @param authenticatePeerResponse Response message of type {@link AuthenticatePeerResponse}
	 *
	 
	 @param peerIP Response message of type {@link String}
	 *
	 
	 @see CryptoKeyWriter
	 */
	public static void authenticatePeerAction(AuthenticatePeerResponse authenticatePeerResponse, String peerIP) {
		try {
			CryptoKeyWriter.writePeerSecretKey(authenticatePeerResponse.getSecretKey().getBytes(), peerIP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void forwardMessageAction(ForwardingMessageResponse forwardingMessageResponse) {
		
	}
}