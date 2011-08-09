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

public class MessageID {
	/* Messages from AG to SA,DA,MA*/

	/* Messages from SA,DA,MA to AG */
	
	static final int CheckAggregator = 2001;
	static final int CheckAggregatorResponse = 2002;
	static final int RegisterAgent = 2003;
	static final int RegisterAgentResponse = 2004;
	static final int Login = 2005;
	static final int LoginResponse = 2006;
	static final int Logout = 2007;
	static final int GetPeerList = 2009;
	static final int GetPeerListResponse = 2010;
	static final int GetSuperPeerList = 2011;
	static final int GetSuperPeerListResponse = 2012;
	static final int GetEvents = 2013;
	static final int GetEventsResponse = 2014;
	static final int SendWebsiteReport = 2015;
	static final int SendReportResponse = 2016;
	static final int SendServiceReport = 2017;
	static final int NewVersion = 2019;
	static final int NewVersionResponse = 2020;
	static final int NewTests = 2021;
	static final int NewTestsResponse = 2022;
	static final int UpgradeToSuper = 2023;
	static final int UpgradeToSuperResponse = 2024;
	static final int SendPrivateKey = 2025;
	static final int SendPrivateKeyResponse = 2026;
	static final int WebsiteSuggestion = 2027;
	static final int TestSuggestionResponse = 2028;
	static final int ServiceSuggestion = 2029;

	/* Messages from SA to DA,MA */

	/* Messages from DA,MA to SA */
	
	static final int AgentUpdate = 4001;
	static final int AgentUpdateResponse = 4002;
	static final int TestModuleUpdate = 4003;
	static final int TestModuleUpdateResponse = 4004;
	static final int ForwardingMessage = 4005;
	static final int ForwardingMessageResponse = 4006;

	/* Messages between DA and MA */
	
	static final int AuthenticatePeer = 5001;
	static final int AuthenticatePeerResponse = 5002;
	static final int P2PGetSuperPeerList = 5003;
	static final int P2PGetSuperPeerListResponse = 5004;
	static final int P2PGetPeerList = 5005;
	static final int P2PGetPeerListResponse = 5006;
}