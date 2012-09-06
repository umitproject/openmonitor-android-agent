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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.umit.icm.mobile.utils.CryptoHelper;


/**
 * Provides methods for generating IDs for various entities.
 */

public class IDGenerator {
	
	/**
	 * Returns a long object with an ID for the ICMReport
	 * 
	 *	 
	                    
	@return        	long
	 * @throws NoSuchAlgorithmException 
	 */
	public static String generateReportID(String agentID, long timeUTC, String testID) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");		
		messageDigest.update(String.valueOf((agentID)).getBytes());
		messageDigest.update(String.valueOf((timeUTC)).getBytes());
		messageDigest.update(String.valueOf((testID)).getBytes());
		return CryptoHelper.toHex(messageDigest.digest());		
	}
}