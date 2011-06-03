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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class WebsiteOpen {
	
	static public URLConnection openURLConnection(String str) throws IOException, HttpException {
		URL url = new URL(str);
		return url.openConnection();
	}
	
	static public int getResponseCode(String str) throws IOException, HttpException {

		HttpGet httpGet = new HttpGet(str);
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
		return httpResponse.getStatusLine().getStatusCode();
          
	}

	static public String getContent(URLConnection urlConnection) throws IOException, HttpException {

        InputStream inputStream = urlConnection.getInputStream();
     	return convertStreamToString(inputStream);
          
	}
	
	static public Map<String, String> getHeaders(URLConnection urlConnection) throws IOException, HttpException {

		Map<String, String> headerMap = new HashMap <String, String>();
		String key = new String();
		String value = new String();
		
		for (int i=0 ;	; i++) {
			key = urlConnection.getHeaderFieldKey(i);
			value = urlConnection.getHeaderField(i);
			if (key == null && value == null)
				break;
			if (key == null) 
				key = "status";
			headerMap.put(key
					, value);
			key = "";
			value = "";
		}
        
     	return headerMap;
          
	}
	
	private static String convertStreamToString(InputStream inputStream) throws IOException {
	   
	    BufferedReader bufferedReader 
	    = new BufferedReader(new InputStreamReader(inputStream));
	    StringBuilder stringBuilder = new StringBuilder();

	    String line = null;
	    
	        while ((line = bufferedReader.readLine()) != null) {
	            stringBuilder.append(line + "\n");
	        }	   
	    inputStream.close();	    	    
	    return stringBuilder.toString();
	}
    
}
