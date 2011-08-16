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

import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.proto.MessageProtos.GetEvents;
import org.umit.icm.mobile.proto.MessageProtos.NewTests;
import org.umit.icm.mobile.proto.MessageProtos.ServiceSuggestion;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteSuggestion;

/**
 * MessageForwardingAggregator class which enables forwarding to Aggregator for P2P messages.
 */

public class MessageForwardingAggregator {
	
	/**
	 * Sends {@link GetEvents} message to super peers for forwarding to Aggregator.
	 */
	public static void forwardGetEvents(AgentData agentData, GetEvents getEvents) 
	throws Exception {
		MessageForwarding.ForwardMessage(agentData, getEvents.toByteArray(),
				MessageID.GetEvents, 0);
	}
	
	/**
	 * Sends {@link NewTests} message to super peers for forwarding to Aggregator.
	 */
	public static void forwardGetTests(AgentData agentData, NewTests newTests) 
	throws Exception {
		MessageForwarding.ForwardMessage(agentData, newTests.toByteArray(),
				MessageID.NewTests, 0);
	}
	
	/**
	 * Sends {@link WebsiteSuggestion} message to super peers for forwarding to Aggregator.
	 */
	public static void forwardWebsiteSuggestion(AgentData agentData, WebsiteSuggestion websiteSuggestion) 
	throws Exception {
		MessageForwarding.ForwardMessage(agentData, websiteSuggestion.toByteArray(),
				MessageID.WebsiteSuggestion, 0);
	}
	
	/**
	 * Sends {@link ServiceSuggestion} message to super peers for forwarding to Aggregator.
	 */
	public static void forwardServiceSuggestion(AgentData agentData, ServiceSuggestion serviceSuggestion) 
	throws Exception {
		MessageForwarding.ForwardMessage(agentData, serviceSuggestion.toByteArray(),
				MessageID.ServiceSuggestion, 0);
	}		
	
}