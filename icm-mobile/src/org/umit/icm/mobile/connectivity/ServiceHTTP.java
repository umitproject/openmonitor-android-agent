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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.util.Log;

/**
 * This is the HTTP Service class. Holds {@link ServiceHTTP#connect()},
 * {@link ServiceHTTP#getService()} and {@link ServiceHTTP#getService()} methods.
 */

public class ServiceHTTP {
	
	/**
	 * Returns a an HTTP Response String.
	 * 
	 *	 	                           	                          		             	            
	           
	@return      String
	 *
	 
	@see HttpClient
	 */	
	public static String connect() {
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(getServiceURL());		

		try {			
		    HttpResponse httpResponse = httpClient.execute(httpGet);
            StatusLine statusLine = httpResponse.getStatusLine();
		      if (statusLine.getStatusCode() != 200) {
		          return "blocked";
		      }			      
		      HttpEntity httpEntity = httpResponse.getEntity();
		      InputStream inputStream = httpEntity.getContent();	
		      ByteArrayOutputStream byteArrayOutputStream 
		      = new ByteArrayOutputStream();			      
		      int bytes = 0;
		      byte[] buffer = new byte[1024];
		      while ((bytes = inputStream.read(buffer)) != -1) {
		    	  byteArrayOutputStream.write(buffer, 0, bytes);
		      }
		      if(new String(byteArrayOutputStream.toByteArray()) != null)
		    	  return "normal"; 
		      else
		    	  return "blocked";
		  }
		  catch (IOException e) {
			     Log.w("ServiceHTTPException: ", e.getLocalizedMessage());
			     return "blocked";
		}
	}
	
	/**
	 * Returns a {@link Service} object from HTTP. 
	 * 
	 *	 

	@see  Service
	 *  	                          		              
	            
	@return      Service
	 */	
	public static Service getService() {
		List<Integer> ports = new ArrayList<Integer>();
		ports.add(80);												
		return new Service("http", ports, "www.google.com" , "open", "true", "0", 0);
	}
	
	/**
	 * Returns a String for service scanning URL. 
	 * 
	 *	  	                          		              
	            
	@return      String
	 */	
	public static String getServiceURL() {
		return "http://www.google.com";
	}
}