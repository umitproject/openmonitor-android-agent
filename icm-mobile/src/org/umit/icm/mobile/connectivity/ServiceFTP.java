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

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 * This is the FTP Service class. Holds {@link ServiceFTP#connect()},
 * {@link ServiceFTP#getService()} and {@link ServiceFTP#getService()} methods.
 */

public class ServiceFTP {
	

	/**
	 * Returns an FTP Response String.
	 * 
	 *	 	                           	                          		             	            
	           
	@return      String
	 *
	 
	@see FTPClient
	 */
	public static String connect() throws SocketException, IOException {
		
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(getServiceURL());
		ftpClient.login("test", "test");		
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);	                				
		ftpClient.enterLocalPassiveMode();		
		String reply =  ftpClient.getReplyString();
		ftpClient.logout();
		ftpClient.disconnect();
		if(reply != null)			
			return "normal";
		return "blocked";
	}
	
	/**
	 * Returns a {@link Service} object for FTP. 
	 * 
	 *	 

	@see  Service
	 *  	                          		              
	            
	@return      Service
	 */	
	public static Service getService() {
		List<Integer> ports = new ArrayList<Integer>();
		ports.add(21);												
		return new Service("ftp", ports, "ftp.secureftp-test.com" , "open", "true", "0", 0);
	}
	
	/**
	 * Returns a String for service scanning URL. 
	 * 
	 *	  	                          		              
	            
	@return      String
	 */	
	public static String getServiceURL() {
		return "ftp.secureftp-test.com";
	}
}
