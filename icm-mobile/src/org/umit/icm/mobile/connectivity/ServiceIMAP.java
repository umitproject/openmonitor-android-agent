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


import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import org.umit.icm.mobile.process.Globals;

import com.sun.mail.imap.IMAPSSLStore;

/**
 * This is the IMAP Service class. Holds {@link ServiceIMAP#connect()},
 * {@link ServiceIMAP#getService()} and {@link ServiceIMAP#getService()} methods.
 */

public class ServiceIMAP implements AbstractServiceTest {	

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
	@Override
	public String connect() {      
        Properties properties = new Properties();        
        properties.setProperty("mail.imap.socketFactory.class",
        		"javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.port",  "993");
        properties.setProperty("mail.imap.socketFactory.port", "993");
        
        URLName urlName = new URLName("imap", this.getService().getIp(), this.getService().getPort(), 
        		"", "umiticmmobile", "umiticmmobile2011");
        
        Session session = Session.getInstance(properties, null);
        Store store = new IMAPSSLStore(session, urlName);
        try {
			store.connect();
			 if(store.isConnected()) {
		        	store.close();
		        	return "normal";
		     }
		} catch (AuthenticationFailedException e) {
			// Considering that we're using a fake username and password, it
			// should result in an Authentication Failure but that means that
			// at least the service is connecting
			return "normal";
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	@Override
	public Service getService() {
		return Globals.runtimeList.servicesList.get(4);
	}
	
	/**
	 * Returns a String for service scanning URL. 
	 * 
	 *	  	                          		              
	            
	@return      String
	 */	
	@Override
	public String getServiceURL() {
		return this.getService().getIp();
	}

	@Override
	public String getServicePacket() {
		return ServicePackets.HTTP_GET;
	}
}
