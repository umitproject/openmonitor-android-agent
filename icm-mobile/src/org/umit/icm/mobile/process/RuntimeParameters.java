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

package org.umit.icm.mobile.process;

import java.io.IOException;

import org.umit.icm.mobile.utils.SDCardReadWrite;


/**
 * Holds parameters that are needed at runtime. Provides setters and getters.
 */

public class RuntimeParameters {
	private int scanInterval;
	private String scanStatus;
	private String token;
	private long agentID;
	private String twitter;
	
	public RuntimeParameters(int scanInterval, String scanStatus, String token,
			long agentID, String twitter) {
		super();
		this.scanInterval = scanInterval;
		this.scanStatus = scanStatus;
		this.token = token;
		this.agentID = agentID;
		this.twitter = twitter;
	}
	
	public RuntimeParameters() {
		super();
	}

	public synchronized String getToken() {
		try {
			token = readToken();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token;
	}

	public synchronized void setToken(String token) {
		this.token = token;
		try {
			writeToken(token);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}

	public synchronized long getAgentID() {
		System.out.println("Inside RuntimeParameter#getAgentID");
		try {
			agentID = readAgentID();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return agentID;
	}

	public synchronized void setAgentID(long agentID) {
		System.out.println("Inside RuntimeParameter#setAgentID");
		this.agentID = agentID;
		try {
			writeAgentID(agentID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public synchronized int getScanInterval() {
		try {
			scanInterval = readScanInterval();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scanInterval;
	}

	public synchronized void setScanInterval(int scanInterval) {
		this.scanInterval = scanInterval;
		try {
			writeScanInterval(scanInterval);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized String getScanStatus() {
		try {
			scanStatus = readScanStatus();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scanStatus;
	}

	public synchronized void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
		try {
			writeScanStatus(scanStatus);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}		
	
	public synchronized String getTwitter() {
		try {
			twitter = readTwitterStatus();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return twitter;
	}

	public synchronized void setTwitter(String twitter) {
		this.twitter = twitter;
		try {
			writeTwitterStatus(twitter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private synchronized String readScanStatus() throws IOException, RuntimeException {
		return SDCardReadWrite.readString(Constants.SCAN_FILE
				, Constants.PARAMETERS_DIR);
	}

	private synchronized void writeScanStatus(String scanStatus) throws IOException, RuntimeException {
		SDCardReadWrite.writeString(Constants.SCAN_FILE
				, Constants.PARAMETERS_DIR, scanStatus);
	}
	
	private synchronized int readScanInterval() throws IOException, RuntimeException {
		return Integer.parseInt(SDCardReadWrite.readString(Constants.INTERVAL_FILE
				, Constants.PARAMETERS_DIR));
	}

	private synchronized void writeScanInterval(int scanStatus) throws IOException, RuntimeException {
		SDCardReadWrite.writeString(Constants.INTERVAL_FILE
				, Constants.PARAMETERS_DIR, Integer.toString(scanStatus));
	}
	
	private synchronized long readAgentID() throws IOException, RuntimeException {
		return Long.parseLong(SDCardReadWrite.readString(Constants.AGENTID_FILE
				, Constants.PARAMETERS_DIR));
	}

	private synchronized void writeAgentID(long agentID) throws IOException, RuntimeException {
		SDCardReadWrite.writeString(Constants.AGENTID_FILE
				, Constants.PARAMETERS_DIR, Long.toString(agentID));
	}
	
	private synchronized String readToken() throws IOException, RuntimeException {
		return SDCardReadWrite.readString(Constants.TOKEN_FILE
				, Constants.PARAMETERS_DIR);
	}

	private synchronized void writeToken(String token) throws IOException, RuntimeException {
		SDCardReadWrite.writeString(Constants.TOKEN_FILE
				, Constants.PARAMETERS_DIR, token);
	}
	
	private synchronized String readTwitterStatus() throws IOException, RuntimeException {
		return SDCardReadWrite.readString(Constants.TWITTER_STATUS_FILE
				, Constants.PARAMETERS_DIR);
	}

	private synchronized void writeTwitterStatus(String token) throws IOException, RuntimeException {
		SDCardReadWrite.writeString(Constants.TWITTER_STATUS_FILE
				, Constants.PARAMETERS_DIR, token);
	}
}