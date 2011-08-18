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
import java.util.ArrayList;
import java.util.List;

import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.utils.SDCardReadWrite;

public class AuthenticatedPeers {
	private List<String> authenticated;
	
	AuthenticatedPeers() {
		authenticated 
		= new ArrayList<String>();
	}
	
	public synchronized void addAuthenticatedPeer(AgentData agentData) throws IOException, RuntimeException {
		authenticated.add(Long.toString(agentData.getAgentID()));
		writeAuthenticatePeers();
		
	}
	
	public synchronized boolean checkPeer(AgentData agentData) {
		if(authenticated.contains(Long.toString(agentData.getAgentID())))
			return true;
		return false;
	}
	
	public synchronized void readAuthenticatePeers() throws IOException, RuntimeException {
		this.authenticated = SDCardReadWrite.readStringList(Constants.PARAMETERS_DIR, 
				Constants.AUTHENTICATED_PEERS_FILE);
	}
	
	public synchronized void writeAuthenticatePeers() throws IOException, RuntimeException {
		SDCardReadWrite.writeStringList(Constants.PARAMETERS_DIR, 
				Constants.AUTHENTICATED_PEERS_FILE, this.authenticated);
	}
	
}