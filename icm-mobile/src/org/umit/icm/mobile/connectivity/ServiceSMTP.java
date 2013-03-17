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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.umit.icm.mobile.process.Globals;


/**
 * This is the SMTP Service class. Holds {@link ServiceSMTP#connect()},
 * {@link ServiceSMTP#getService()} and {@link ServiceSMTP#getService()} methods.
 */

public class ServiceSMTP implements AbstractServiceTest {	

	/**
	 * Returns an SMTP Response String.
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
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", this.getService().getIp());
        properties.put("mail.smtp.port", this.getService().getPort());

        Session session = Session.getInstance(properties,
      		  new javax.mail.Authenticator() {
      			protected PasswordAuthentication getPasswordAuthentication() {
      				return new PasswordAuthentication("umiticmmobile", "umiticmmobile2011");
      			}
      		  });
        Message message = new MimeMessage(session);
        try {
        	message.setFrom(new InternetAddress("umiticmmobile@gmail.com"));
    		message.setRecipients(Message.RecipientType.TO,
    			InternetAddress.parse("umiticmmobile@gmail.com"));
    		message.setSubject("SMTP test");
    		message.setText("SMTP test");
			Transport.send(message);
		} catch (MessagingException e) {
			return "normal";
		}
       
        return "blocked";
	}
	
	/**
	 * Returns a {@link Service} object for SMTP. 
	 * 
	 *	 

	@see  Service
	 *  	                          		              
	            
	@return      Service
	 */
	@Override
	public Service getService() {
		return Globals.runtimeList.servicesList.get(7);
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
