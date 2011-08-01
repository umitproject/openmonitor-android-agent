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
import org.umit.icm.mobile.process.Constants;

public class BingSearch {
	
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
	private static String WEB_COUNT_VALUE = "1";
	private static String JSON_TYPE_KEY = "JsonType=";
	private static String JSON_TYPE_VALUE = "raw";
	private String version;
	private String market;
	private String sources;
	private String webCount;
	private String jsonType;
	
	public BingSearch() {
		version = VERSION_VALUE;
		market = MARKET_VALUE;
		sources = SOURCES_VALUE;
		webCount = WEB_COUNT_VALUE;
		jsonType = JSON_TYPE_VALUE;
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
		+ JSON_TYPE_KEY
		+ jsonType
		;
	}
	
	private String retrieveQuery(String query) throws IOException, HttpException {
		String queryString = buildQuery(query);
		return WebsiteOpen.getContent(
				WebsiteOpen.openURLConnection(queryString));
	}
	
	public String search(String query) throws IOException, HttpException {
		return retrieveQuery(query);
	}
	
}