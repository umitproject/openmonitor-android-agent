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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class WebsiteOpen {
	
	static public URLConnection openURLConnection(String str) throws IOException, HttpException {
		URL url = new URL(str);
		return url.openConnection();
	}

	static public String getContent(URLConnection urlConnection) throws IOException, HttpException {

        InputStream inputStream = urlConnection.getInputStream();
     	return convertStreamToString(inputStream);
    }
	
	static public InputStream getContentStream(URLConnection urlConnection) throws IOException, HttpException {

        return urlConnection.getInputStream();
    }
	
	static public Bitmap getFavicon(String str) throws IOException, HttpException {
		InputStream inputStream = getContentStream(openURLConnection(str + "/favicon.ico"));
		return BitmapFactory.decodeStream(inputStream);
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
			headerMap.put(key, value);
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
	
	public static int getStatusCode(Map <String, String> websiteHeader) {
		int statusCode = websiteHeader.size();
		if(websiteHeader.size()!=0) {
			Pattern httpCodePattern = 
				Pattern.compile("10[0-1]|20[0-6]|30[0-7]|40[0-9]|41[0-7]|50[0-5]");
			Matcher httpCodeMatcher = 
				httpCodePattern.matcher(websiteHeader.get("status"));
			while (httpCodeMatcher.find()) {
			    statusCode = Integer.parseInt(httpCodeMatcher.group());
			}
		}
		return statusCode;
	}
	
	public static String httpOrHttps(Map <String, String> websiteHeader) {
		String connectionType = "http";
		int statusCode = getStatusCode(websiteHeader);
		if (statusCode >= 300 && statusCode <= 307 && statusCode != 306)
			connectionType = "https";
		return connectionType;
	}
    
}