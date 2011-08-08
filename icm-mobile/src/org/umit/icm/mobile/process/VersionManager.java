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
 * Holds agent version number and tests version number. Provides setters and getters.
 */

public class VersionManager {
	private int agentVersion;
	private int testsVersion;
	
	public VersionManager() {
		agentVersion = 0;
		testsVersion = 0;
	}

	public synchronized int getAgentVersion() throws IOException {
		agentVersion = readAgentVersion();
		return agentVersion;
	}

	public synchronized void setAgentVersion(int agentVersion) throws IOException {
		this.agentVersion = agentVersion;
		writeAgentVersion(agentVersion);
	}

	public synchronized int getTestsVersion() throws IOException {
		testsVersion = readTestsVersion();
		return testsVersion;
	}

	public synchronized void setTestsVersion(int testsVersion) throws IOException {
		this.testsVersion = testsVersion;
		writeTestsVersion(testsVersion);
	}
	
	private synchronized int readAgentVersion() throws IOException {
		return Integer.parseInt(SDCardReadWrite.readString(Constants.AGENT_VERSION_FILE
				, Constants.VERSIONS_DIR));
	}

	private synchronized void writeAgentVersion(int agentVersion) throws IOException {
		SDCardReadWrite.writeString(Constants.AGENT_VERSION_FILE
				, Constants.VERSIONS_DIR, Integer.toString(agentVersion));
	}
	
	private synchronized int readTestsVersion() throws IOException {
		return Integer.parseInt(SDCardReadWrite.readString(Constants.TESTS_VERSION_FILE
				, Constants.VERSIONS_DIR));
	}

	private synchronized void writeTestsVersion(int testsVersion) throws IOException {
		SDCardReadWrite.writeString(Constants.TESTS_VERSION_FILE
				, Constants.VERSIONS_DIR, Integer.toString(testsVersion));
	} 
}