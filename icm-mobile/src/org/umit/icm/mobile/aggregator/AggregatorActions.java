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

import org.umit.icm.mobile.proto.MessageProtos.GetEventsResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerListResponse;
import org.umit.icm.mobile.proto.MessageProtos.NewTestsResponse;
import org.umit.icm.mobile.proto.MessageProtos.NewVersionResponse;
import org.umit.icm.mobile.proto.MessageProtos.RegisterAgentResponse;
import org.umit.icm.mobile.proto.MessageProtos.SendReportResponse;
import org.umit.icm.mobile.proto.MessageProtos.TestSuggestionResponse;

public class AggregatorActions {
	
	public static boolean registerAgentAction(RegisterAgentResponse registerAgentResponse) {
		return true;
	}
	
	public static boolean getPeerListAction(GetPeerListResponse getPeerListResponse) {
		return true;
	}
	
	public static boolean getSuperPeerListAction(GetSuperPeerListResponse getSuperPeerListResponse) {
		return true;
	}
	
	public static boolean getEventsAction(GetEventsResponse getEventsResponse) {
		return true;
	}
	
	public static boolean sendReportAction(SendReportResponse sendReportResponse) {
		return true;
	}
	
	public static boolean checkVersionAction(NewVersionResponse newVersionResponse) {
		return true;
	}
	
	public static boolean newTestsAction(NewTestsResponse newTestsResponse) {
		return true;
	}
	
	public static boolean sendSuggestionAction(TestSuggestionResponse testSuggestionResponse) {
		return true;
	}
}