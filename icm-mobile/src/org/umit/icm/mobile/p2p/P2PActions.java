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
import org.umit.icm.mobile.proto.MessageProtos.AssignTaskResponse;
import org.umit.icm.mobile.proto.MessageProtos.AuthenticatePeerResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetEventsResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.SendReportResponse;
import org.umit.icm.mobile.proto.MessageProtos.TestSuggestionResponse;

public class P2PActions {
	
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
	
	public static void getPeerListAction(GetPeerListResponse getPeerListResponse) {
		try {
			ProcessActions.updateAgentVersion(getPeerListResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ProcessActions.updateTestsVersion(getPeerListResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ProcessActions.updatePeersList(getPeerListResponse.getKnownPeersList());
	}
	
	public static void getSuperPeerListAction(GetSuperPeerListResponse getSuperPeerListResponse) {
		try {
			ProcessActions.updateAgentVersion(getSuperPeerListResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ProcessActions.updateTestsVersion(getSuperPeerListResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ProcessActions.updateSuperPeersList(getSuperPeerListResponse.getKnownSuperPeersList());
	}
	
	public static void receiveTaskListAction(AssignTaskResponse assignTaskResponse) {
		try {
			ProcessActions.updateAgentVersion(assignTaskResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ProcessActions.updateTestsVersion(assignTaskResponse.getHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ProcessActions.updateTests(assignTaskResponse.getTestsList());
	}
	
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
	
	public static void authenticatePeerAction(AuthenticatePeerResponse authenticatePeerResponse) {
		
	}
}