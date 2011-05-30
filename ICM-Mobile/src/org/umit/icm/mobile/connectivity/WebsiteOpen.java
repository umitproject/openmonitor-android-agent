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
import java.net.URL;
import java.net.URLConnection;


public class WebsiteOpen {

	static public String getContent(String str) throws Exception {

		URL url = new URL(str);
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        try{
        	return convertStreamToString(inputStream);
        }
        catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
        finally {
        	inputStream.close();
        }
	        	       
	}
	
	private static String convertStreamToString(InputStream inputStream) throws IOException {
	   
		if (inputStream.equals(null))
			return null;
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
