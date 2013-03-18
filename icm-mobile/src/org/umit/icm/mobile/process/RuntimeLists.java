/**
 * Copyright (C) 2011 Adriano Monteiro Marques
 *
 * Author:  Zubair Nabi <zn.zubairnabi@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either Test 2 of the License, or
 * (at your option) any later Test.
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

package org.umit.icm.mobile.process;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.umit.icm.mobile.connectivity.Service;
import org.umit.icm.mobile.connectivity.Website;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.proto.MessageProtos.Event;
import org.umit.icm.mobile.utils.SDCardReadWrite;

/**
 * Holds various lists and associated methods.
 */

public class RuntimeLists {

	private List<Event> eventsList;
	private List<AgentData> peersList;
	private List<AgentData> superPeersList;
	public List<Website> websitesList;
	public List<Service> servicesList;
	
	public RuntimeLists() {
		eventsList = new CopyOnWriteArrayList<Event>();
		peersList = new CopyOnWriteArrayList<AgentData>();
		superPeersList = new CopyOnWriteArrayList<AgentData>();
		websitesList = new CopyOnWriteArrayList<Website>();
		servicesList = new CopyOnWriteArrayList<Service>();	
	}		
	
	public List<Event> getEventsList() {
		return eventsList;
	}

	public void setEventsList(List<Event> eventsList) {
		this.eventsList.addAll(eventsList);			
		writeEventList();
	}
	
	public void addEvent(Event event) {
		this.eventsList.add(event);			
		writeEventList();
	}
	
	public void addPeer(AgentData agentData) {
		this.peersList.add(agentData);			
		writePeerList();
	}
	
	public void addSuperPeer(AgentData agentData) {
		this.superPeersList.add(agentData);			
		writeSuperPeerList();
	}

	public List<AgentData> getPeersList() {
		return peersList;
	}

	public void setPeersList(List<AgentData> peersList) {
		this.peersList.addAll(peersList);			
		writePeerList();
	}

	public List<AgentData> getSuperPeersList() {
		return superPeersList;
	}

	public void setSuperPeersList(List<AgentData> superPeersList) {
		this.superPeersList.addAll(superPeersList);			
		writeSuperPeerList();
	}

	/**
	 * Writes {@link Globals#peersList} to disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void writePeerList() {
		try {
			SDCardReadWrite.writePeersList(Constants.PARAMETERS_DIR,
						peersList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Reads {@link Globals#peersList} from disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void readPeerList() {		
		try {			
			if(SDCardReadWrite.fileExists(Constants.PEERS_FILE, Constants.PARAMETERS_DIR)) {
				peersList.addAll(SDCardReadWrite.readPeersList(Constants.PARAMETERS_DIR));
				}	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	
	/**
	 * Writes {@link Globals#superPeersList} to disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void writeSuperPeerList() {
			try {
				SDCardReadWrite.writeSuperPeersList(Constants.PARAMETERS_DIR,
							superPeersList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	
	/**
	 * Reads {@link Globals#superPeersList} from disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void readSuperPeerList() {
		try {
			if(SDCardReadWrite.fileExists(Constants.SUPER_PEERS_FILE, Constants.PARAMETERS_DIR)) {
				superPeersList.addAll(SDCardReadWrite.readSuperPeersList(Constants.PARAMETERS_DIR));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes {@link Globals#eventsList} to disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void writeEventList() {
		try {
				SDCardReadWrite.writeEventsList(Constants.PARAMETERS_DIR,
						eventsList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Reads {@link Globals#eventsList} from disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void readEventList() {
		try {
			if(SDCardReadWrite.fileExists(Constants.EVENTS_FILE, Constants.PARAMETERS_DIR)) {
				eventsList.addAll(SDCardReadWrite.readEventsList(Constants.PARAMETERS_DIR));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}