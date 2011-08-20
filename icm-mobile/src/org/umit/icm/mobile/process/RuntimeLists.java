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
import java.util.ArrayList;
import java.util.List;

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
	private Object eventLock;
	private Object peerLock;
	private Object superPeerLock;
	
	public RuntimeLists() {
		eventsList = new ArrayList<Event>();
		peersList = new ArrayList<AgentData>();
		superPeersList = new ArrayList<AgentData>();
		eventLock = new Object();
		peerLock = new Object();
		superPeerLock = new Object();
	}		
	
	public List<Event> getEventsList() {
		synchronized(eventLock) {
			return eventsList;
		}
	}

	public void setEventsList(List<Event> eventsList) {
		synchronized(eventLock) {
			this.eventsList.addAll(eventsList);
			writeEventList();
		}
	}
	
	public void addEvent(Event event) {
		synchronized(eventLock) {
			this.eventsList.add(event);
			writeEventList();
		}
	}
	
	public void addPeer(AgentData agentData) {
		synchronized(peerLock) {
			this.peersList.add(agentData);
			writePeerList();
		}
	}
	
	public void addSuperPeer(AgentData agentData) {
		synchronized(superPeerLock) {
			this.superPeersList.add(agentData);
			writeSuperPeerList();
		}
	}

	public List<AgentData> getPeersList() {
		synchronized(peerLock) {
			return peersList;
		}
	}

	public void setPeersList(List<AgentData> peersList) {
		synchronized(peerLock) {
			this.peersList.addAll(peersList);
			writePeerList();
		}
	}

	public List<AgentData> getSuperPeersList() {
		synchronized(superPeerLock) {
			return superPeersList;
		}
	}

	public void setSuperPeersList(List<AgentData> superPeersList) {
		synchronized(superPeerLock) {
			this.superPeersList.addAll(superPeersList);
			writeSuperPeerList();
		}
	}

	/**
	 * Writes {@link Globals#peersList} to disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void writePeerList() {
		try {
			synchronized(peerLock) {
				SDCardReadWrite.writePeersList(Constants.PARAMETERS_DIR
						, peersList);
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
	 * Reads {@link Globals#peersList} from disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void readPeerList() {		
		try {			
			if(SDCardReadWrite.fileExists(Constants.PEERS_FILE, Constants.PARAMETERS_DIR)) {
				synchronized(peerLock) {
					peersList.addAll(SDCardReadWrite.readPeersList(Constants.PARAMETERS_DIR));
				}
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
				synchronized(superPeerLock) {
					SDCardReadWrite.writeSuperPeersList(Constants.PARAMETERS_DIR
							, superPeersList);
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
	 * Reads {@link Globals#superPeersList} from disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void readSuperPeerList() {
		try {
			if(SDCardReadWrite.fileExists(Constants.SUPER_PEERS_FILE, Constants.PARAMETERS_DIR)) {
				synchronized(superPeerLock) {
					superPeersList.addAll(SDCardReadWrite.readSuperPeersList(Constants.PARAMETERS_DIR));
				}
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
			synchronized(eventLock) {
				SDCardReadWrite.writeEventsList(Constants.PARAMETERS_DIR
						, eventsList);
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
	 * Reads {@link Globals#eventsList} from disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void readEventList() {
		try {
			if(SDCardReadWrite.fileExists(Constants.EVENTS_FILE, Constants.PARAMETERS_DIR)) {
				synchronized(eventLock) {
					eventsList.addAll(SDCardReadWrite.readEventsList(Constants.PARAMETERS_DIR));
				}
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