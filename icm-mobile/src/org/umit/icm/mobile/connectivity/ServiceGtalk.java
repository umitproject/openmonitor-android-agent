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
import java.net.UnknownHostException;

import org.umit.icm.mobile.process.Globals;

/**
 * This is the Gtalk Service class. Holds {@link ServiceGtalk#connect()},
 * {@link ServiceGtalk#getService()} and {@link ServiceGtalk#getService()} methods.
 */

public class ServiceGtalk implements AbstractServiceTest {
	

	/**
	 * Returns a Gtalk Response String.
	 * 
	 *	 	                           	                          		             	            
	           
	@return      String
	 *
	 
	@see TCPClient
	 */
	@Override
	public String connect() {
		try {
			Globals.tcpClientConnectivity.openConnection("talk.google.com"
					, 5222);
			Globals.tcpClientConnectivity.writeLine("hello");
			String reply = Globals.tcpClientConnectivity.readLines();
			Globals.tcpClientConnectivity.closeConnection();
			if(reply != null)
				return "normal";
			return "blocked";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns a {@link Service} object for Gtalk. 
	 * 
	 *	 

	@see  Service
	 *  	                          		              
	            
	@return      Service
	 */	
	@Override
	public Service getService() {
		Integer port = 5222;
														
		return new Service("gtalk", port, "talk.google.com" , "open", "true", "0", 0);
	}
	
	/**
	 * Returns a String for service scanning URL. 
	 * 
	 *	  	                          		              
	            
	@return      String
	 */	
	@Override
	public String getServiceURL() {
		return "talk.google.com";
	}

	@Override
	public String getServicePacket() {
		return ServicePackets.HTTP_GET;
	}
}
