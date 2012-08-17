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


import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.umit.icm.mobile.process.Globals;

/**
 * This is the Gtalk Service class. Holds {@link ServiceGtalk#connect()},
 * {@link ServiceGtalk#getService()} and {@link ServiceGtalk#getService()} methods.
 */

public class ServiceGtalk {
	

	/**
	 * Returns a Gtalk Response String.
	 * 
	 *	 	                           	                          		             	            
	           
	@return      String
	 *
	 
	@see TCPClient
	 */
	public static String connect() throws SocketException, IOException {
		
		Globals.tcpClientConnectivity.openConnection("talk.google.com"
				, 5222);
		Globals.tcpClientConnectivity.writeLine("hello");
		String reply = Globals.tcpClientConnectivity.readLines();
		Globals.tcpClientConnectivity.closeConnection();
		if(reply != null)
			return "normal";
		return "blocked";
	}
	
	/**
	 * Returns a {@link Service} object for Gtalk. 
	 * 
	 *	 

	@see  Service
	 *  	                          		              
	            
	@return      Service
	 */	
	public static Service getService() {
		Integer port = 5222;
														
		return new Service("gtalk", port, "talk.google.com" , "open", "true", "0", 0);
	}
	
	/**
	 * Returns a String for service scanning URL. 
	 * 
	 *	  	                          		              
	            
	@return      String
	 */	
	public static String getServiceURL() {
		return "talk.google.com";
	}
}
