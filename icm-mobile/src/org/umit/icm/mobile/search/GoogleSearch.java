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

package org.umit.icm.mobile.search;

import java.io.IOException;

import org.apache.http.HttpException;
import org.umit.icm.mobile.connectivity.WebsiteOpen;

import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

public class GoogleSearch {	
	
	private static String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/web?";	
	private static String SEPARATOR = "&";
	private static String VERSION_KEY = "v=";
	private static String VERSION_VALUE = "1.0";	
	private static String QUERY_KEY = "q=";	
	private static String WEB_COUNT_KEY = "rsz=";
	private static String WEB_COUNT_VALUE = "3";
	private static String WEB_OFFSET_KEY = "start=";
	private static String WEB_OFFSET_VALUE = "0";
	private String version;		
	private String webCount;	
	private String offset;
	
	public GoogleSearch() {
		version = VERSION_VALUE;				
		webCount = WEB_COUNT_VALUE;		
		offset = WEB_OFFSET_VALUE;
	}
	
	public String getWebCount() {
		return webCount;
	}



	public void setWebCount(String webCount) {
		this.webCount = webCount;
	}
	
	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}


	private String buildQuery(String query) {
		return BASE_URL 		
		+ SEPARATOR
		+ VERSION_KEY
		+ version		
		+ SEPARATOR
		+ QUERY_KEY
		+ query
		+ SEPARATOR		
		+ WEB_COUNT_KEY
		+ webCount
		+ SEPARATOR
		+ WEB_OFFSET_KEY
		+ offset		
		;
	}
	
	private String retrieveQuery(String query) throws IOException, HttpException {
		String queryString = buildQuery(query);
		return WebsiteOpen.getContent(
				WebsiteOpen.openURLConnection(queryString));
	}
	
	public JSONObject search(String query) throws IOException, HttpException, JSONException {
		return clean(retrieveQuery(query));
	}
	
	private JSONObject clean(String response) throws JSONException {
		JSONObject jsonObjectResponse = new JSONObject(response);						
		return jsonObjectResponse.getJSONObject("responseData");		
	}
	
}