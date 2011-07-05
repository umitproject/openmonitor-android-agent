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

import org.umit.icm.mobile.utils.Constants;
import org.umit.icm.mobile.utils.SDCardReadWrite;


/**
 * Holds parameters that are needed at runtime. Provides setters and getters.
 */

public class RuntimeParameters {
	private int scanInterval;
	private String scanStatus;
	private String token;
	private long agentID;
	
	public RuntimeParameters(int scanInterval, String scanStatus, String token,
			long agentID) {
		super();
		this.scanInterval = scanInterval;
		this.scanStatus = scanStatus;
		this.token = token;
		this.agentID = agentID;
	}
	
	public RuntimeParameters() {
		super();
	}

	public String getToken() throws IOException, RuntimeException {
		token = readToken();
		return token;
	}

	public void setToken(String token) throws IOException, RuntimeException {
		this.token = token;
		writeToken(token);		
		Globals.initializeRequestHeader();
	}

	public long getAgentID() throws IOException, RuntimeException {
		agentID = readAgentID();
		return agentID;
	}

	public void setAgentID(long agentID) throws IOException, RuntimeException {
		this.agentID = agentID;
		writeAgentID(agentID);
		Globals.initializeRequestHeader();
	}

	public int getScanInterval() throws IOException, RuntimeException {
		scanInterval = readScanInterval();
		return scanInterval;
	}

	public void setScanInterval(int scanInterval) throws IOException, RuntimeException {
		this.scanInterval = scanInterval;
		writeScanInterval(scanInterval);
	}

	public String getScanStatus() throws IOException, RuntimeException {
		scanStatus = readScanStatus();
		return scanStatus;
	}

	public void setScanStatus(String scanStatus) throws IOException, RuntimeException {
		this.scanStatus = scanStatus;
		writeScanStatus(scanStatus);
	}
	
	private String readScanStatus() throws IOException, RuntimeException {
		return SDCardReadWrite.readString(Constants.SCAN_FILE
				, Constants.PARAMETERS_DIR);
	}

	private void writeScanStatus(String scanStatus) throws IOException, RuntimeException {
		SDCardReadWrite.writeString(Constants.SCAN_FILE
				, Constants.PARAMETERS_DIR, scanStatus);
	}
	
	private int readScanInterval() throws IOException, RuntimeException {
		return Integer.parseInt(SDCardReadWrite.readString(Constants.INTERVAL_FILE
				, Constants.PARAMETERS_DIR));
	}

	private void writeScanInterval(int scanStatus) throws IOException, RuntimeException {
		SDCardReadWrite.writeString(Constants.INTERVAL_FILE
				, Constants.PARAMETERS_DIR, Integer.toString(scanStatus));
	}
	
	private long readAgentID() throws IOException, RuntimeException {
		return Long.parseLong(SDCardReadWrite.readString(Constants.AGENTID_FILE
				, Constants.PARAMETERS_DIR));
	}

	private void writeAgentID(long agentID) throws IOException, RuntimeException {
		SDCardReadWrite.writeString(Constants.AGENTID_FILE
				, Constants.PARAMETERS_DIR, Long.toString(agentID));
	}
	
	private String readToken() throws IOException, RuntimeException {
		return SDCardReadWrite.readString(Constants.TOKEN_FILE
				, Constants.PARAMETERS_DIR);
	}

	private void writeToken(String token) throws IOException, RuntimeException {
		SDCardReadWrite.writeString(Constants.TOKEN_FILE
				, Constants.PARAMETERS_DIR, token);
	}
}