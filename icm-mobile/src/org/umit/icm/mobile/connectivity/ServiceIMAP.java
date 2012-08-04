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
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import com.sun.mail.imap.IMAPSSLStore;

/**
 * This is the IMAP Service class. Holds {@link ServiceIMAP#connect()},
 * {@link ServiceIMAP#getService()} and {@link ServiceIMAP#getService()} methods.
 */

public class ServiceIMAP {	

	/**
	 * Returns an IMAP Response String.
	 * 
	 *	 	                           	                          		             	            
	           
	@return      String	 
	 *
	 
	@see Session
	 *
	
	@see Store
	 */
	public static String connect() throws SocketException, IOException, MessagingException {
					       
        Properties properties = new Properties();        
        properties.setProperty("mail.imap.socketFactory.class",
        		"javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.port",  "993");
        properties.setProperty("mail.imap.socketFactory.port", "993");
        
        URLName urlName = new URLName("imap", "imap.gmail.com", 995, "",
                "umiticmmobile", "umiticmmobile2011");
        
        Session session = Session.getInstance(properties, null);
        Store store = new IMAPSSLStore(session, urlName);
        store.connect();
        if(store.isConnected()) {
        	store.close();
        	return "normal";
        }
        return "blocked";
	}
	
	/**
	 * Returns a {@link Service} object for IMAP. 
	 * 
	 *	 

	@see  Service
	 *  	                          		              
	            
	@return      Service
	 */	
	public static Service getService() {
		List<Integer> ports = new ArrayList<Integer>();
		ports.add(993);												
		return new Service("imap", ports, "imap.gmail.com" , "open", "true", "0", 0);
	}
	
	/**
	 * Returns a String for service scanning URL. 
	 * 
	 *	  	                          		              
	            
	@return      String
	 */	
	public static String getServiceURL() {
		return "imap.gmail.com";
	}
}
