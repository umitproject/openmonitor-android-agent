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

import org.umit.icm.mobile.proto.MessageProtos.*;
import org.umit.icm.mobile.p2p.P2PCommunication;

public class MessageSender {
		public static byte [] sendICMReport(AgentData agentData, ICMReport icmReport) throws Exception {
			return P2PCommunication.sendMessage(agentData, icmReport.toByteArray());
		}
		
		public static byte [] receiveEvents(AgentData agentData, GetEvents getEvents) throws Exception {
			return P2PCommunication.sendMessage(agentData, getEvents.toByteArray());
		}
		
		public static byte [] receivePeerList(AgentData agentData, GetPeerList getPeerList) throws Exception {
			return P2PCommunication.sendMessage(agentData, getPeerList.toByteArray());
		}
		
		public static byte [] receiveSuperPeerList(AgentData agentData, GetSuperPeerList getSuperPeerList) throws Exception {
			return P2PCommunication.sendMessage(agentData, getSuperPeerList.toByteArray());
		}
		
		public static byte [] sendSymmetricKey(AgentData agentData, SendPrivateKey sendPrivateKey) throws Exception {
			return P2PCommunication.sendMessagePublic(agentData, sendPrivateKey.toByteArray());
		}
		
}