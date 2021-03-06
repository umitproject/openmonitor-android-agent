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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.umit.icm.mobile.connectivity.WebsiteOpen;
import org.umit.icm.mobile.process.Constants;


/**
 * BingSearch class that implements the interface {@link AbstractSearch}.
 */

public class BingSearch implements AbstractSearch {
	
	private static String BASE_URL = "http://api.bing.net/json.aspx?";
	private static String APP_ID_KEY = "AppId=";
	private static String SEPARATOR = "&";
	private static String VERSION_KEY = "Version=";
	private static String VERSION_VALUE = "2.2";
	private static String MARKET_KEY = "Market=";
	private static String MARKET_VALUE = "en-US";
	private static String QUERY_KEY = "Query=";
	private static String SOURCES_KEY = "Sources=";
	private static String SOURCES_VALUE = "web+spell";
	private static String WEB_COUNT_KEY = "Web.Count=";
	private static String WEB_COUNT_VALUE = "3";
	private static String WEB_OFFSET_KEY = "Web.Offset=";
	private static String WEB_OFFSET_VALUE = "0";
	private static String JSON_TYPE_KEY = "JsonType=";
	private static String JSON_TYPE_VALUE = "raw";
	private String version;
	private String market;
	private String sources;
	private String webCount;
	private String jsonType;
	private String offset;
	
	public BingSearch() {
		version = VERSION_VALUE;
		market = MARKET_VALUE;
		sources = SOURCES_VALUE;
		webCount = WEB_COUNT_VALUE;
		jsonType = JSON_TYPE_VALUE;
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



	public String getMarket() {
		return market;
	}



	public void setMarket(String market) {
		this.market = market;
	}



	public String getSources() {
		return sources;
	}



	public void setSources(String sources) {
		this.sources = sources;
	}



	public String getJsonType() {
		return jsonType;
	}



	public void setJsonType(String jsonType) {
		this.jsonType = jsonType;
	}

	/**
	 * Returns a String object that is the query string url.
	 * 
	 * 
	 @param	query	An object of type {@link String}
	 */
	private String buildQuery(String query) {
		return BASE_URL 
		+ APP_ID_KEY
		+ Constants.BING_SEARCH_API
		+ SEPARATOR
		+ VERSION_KEY
		+ version
		+ SEPARATOR
		+ MARKET_KEY
		+ market
		+ SEPARATOR
		+ QUERY_KEY
		+ query
		+ SEPARATOR
		+ SOURCES_KEY
		+ sources
		+ SEPARATOR
		+ WEB_COUNT_KEY
		+ webCount
		+ SEPARATOR
		+ WEB_OFFSET_KEY
		+ offset
		+ SEPARATOR
		+ JSON_TYPE_KEY
		+ jsonType
		;
	}
	
	/**
	 * Returns a String object that is the query response.
	 * 
	 * 
	 @param	query	An object of type {@link String}
	 *
	 
	 @see WebsiteOpen
	 */
	private String retrieveQuery(String query) throws IOException, HttpException {
		String queryString = buildQuery(query);
		return WebsiteOpen.getContent(
				WebsiteOpen.openURLConnection(queryString));
	}
	
	/**
	 * Returns a {@link JSONObject} that is the cleaned query result.
	 * Calls {@link BingSearch#clean(String)}
	 * 
	 * 
	 
	 @param	query	An object of type {@link String}
	 */
	public JSONObject search(String query) throws IOException, HttpException, JSONException {
		return clean(retrieveQuery(query));
	}
	
	/**
	 * Returns a {@JSONObject} that is the cleaned query result.
	 * 
	 * 
	 
	 @param	query	An object of type {@link String}
	 *
	 
	 @see JSONObject
	 */
	private JSONObject clean(String response) throws JSONException {
		JSONObject jsonObjectResponse = new JSONObject(response);				
		JSONArray jsonArrayResponse = jsonObjectResponse.toJSONArray(jsonObjectResponse.names());
		return jsonArrayResponse.getJSONObject(0).getJSONObject("Web");
	}
	
}