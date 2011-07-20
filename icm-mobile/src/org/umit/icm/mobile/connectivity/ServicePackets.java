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

package org.umit.icm.mobile.connectivity;

import java.util.Random;

import org.umit.icm.mobile.process.Globals;


/**
 * This is the ServicePackets class. Holds {@link String} packets of different
 * {@link Service} type Services.
 */

public class ServicePackets {
	
	/**
	 * This is the HTTP_GET packet.
	 */
	public static String HTTP_GET 
	= "GET / HTTP/1.1\r\n\r\n";
	
	public static String HTTP_GET_DETAILED 
	= "GET / HTTP/1.1..User-Agent: Java0..Host: www.google.com..Connection: Keep-Alive..\r\n\r\n";
	
	/**
	 * Returns a byte[] object. Takes as parameter a {@link String} and returns 
	 * a byte[] of the same size as the {@link String} but with random bytes.
	 * 
	 *	 
	                          
	@param  str  An object of the type String
	 *  	                          	
	                          
	@return      byte[]
	 */	 
	public static byte[] generatedRandomBytes(String str) {		
		byte[] byteArray = new byte[str.length()];
		new Random().nextBytes(byteArray);
		return byteArray;
	}
	
	public static void populateServicesMap() {
		Globals.servicePacketsMap.put("http_old", HTTP_GET);
		Globals.servicePacketsMap.put("http", HTTP_GET_DETAILED);
	}
}