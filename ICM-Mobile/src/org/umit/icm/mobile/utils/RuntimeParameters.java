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

package org.umit.icm.mobile.utils;

public class RuntimeParameters {
	private int scanInterval;
	private String scanStatus;
	
	public RuntimeParameters(int scanInterval, String scanStatus) {
		super();
		this.scanInterval = scanInterval;
		this.scanStatus = scanStatus;
	}
	
	public RuntimeParameters() {
		super();
	}

	public int getScanInterval() throws Exception {
		scanInterval = readScanInterval();
		return scanInterval;
	}

	public void setScanInterval(int scanInterval) throws Exception {
		this.scanInterval = scanInterval;
		writeScanInterval(scanInterval);
	}

	public String getScanStatus() throws Exception {
		scanStatus = readScanStatus();
		return scanStatus;
	}

	public void setScanStatus(String scanStatus) throws Exception {
		this.scanStatus = scanStatus;
		writeScanStatus(scanStatus);
	}
	
	private String readScanStatus() throws Exception {
		return SDCardReadWrite.readString(Constants.SCAN_FILE
				, Constants.PARAMETERS_DIR);
	}

	private void writeScanStatus(String scanStatus) throws Exception {
		SDCardReadWrite.writeString(Constants.SCAN_FILE
				, Constants.PARAMETERS_DIR, scanStatus);
	}
	
	private int readScanInterval() throws Exception {
		return Integer.parseInt(SDCardReadWrite.readString(Constants.INTERVAL_FILE
				, Constants.PARAMETERS_DIR));
	}

	private void writeScanInterval(int scanStatus) throws Exception {
		SDCardReadWrite.writeString(Constants.INTERVAL_FILE
				, Constants.PARAMETERS_DIR, Integer.toString(scanStatus));
	}
}