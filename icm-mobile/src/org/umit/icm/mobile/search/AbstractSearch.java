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

import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;


/**
 * AbstractSearch interface that needs to be implemented by every search engine
 * API.
 */

public interface AbstractSearch {
	

	/**
	 * returns a {@link JSONObject} object with the results of the query.
	 * 
	 * 
	 
	 @return JSONObject
	 *
	 
	 @param query 	An object of type {@link String}
	 */
	public JSONObject search(String query) throws IOException, HttpException, JSONException;
}