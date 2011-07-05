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

import org.umit.icm.mobile.utils.Constants;
import org.umit.icm.mobile.utils.SDCardReadWrite;

/**
 * Writes various lists to disk.
 */

public class ParametersReadWrite {
	
	/**
	 * Writes {@link Globals#peersList} to disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void writePeerList() {
			try {
				SDCardReadWrite.writePeersList(Constants.PARAMETERS_DIR
						, Globals.peersList);
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
			Globals.peersList 
			= SDCardReadWrite.readPeersList(Constants.PARAMETERS_DIR);
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
				SDCardReadWrite.writeSuperPeersList(Constants.PARAMETERS_DIR
						, Globals.superPeersList);
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
			Globals.superPeersList 
			= SDCardReadWrite.readSuperPeersList(Constants.PARAMETERS_DIR);
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
				SDCardReadWrite.writeEventsList(Constants.PARAMETERS_DIR
						, Globals.eventsList);
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
			Globals.eventsList
			= SDCardReadWrite.readEventsList(Constants.PARAMETERS_DIR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes {@link Globals#testsList} to disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void writeTestList() {
			try {
				SDCardReadWrite.writeTestsList(Constants.PARAMETERS_DIR
						, Globals.testsList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	
	/**
	 * Reads {@link Globals#testsList} from disk.
	 * 
	
	 @see		SDCardReadWrite
	 */
	public void readTestList() {
		try {
			Globals.testsList 
			= SDCardReadWrite.readTestsList(Constants.PARAMETERS_DIR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}