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

package org.umit.icm.mobile.aggregator;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpException;
import org.umit.icm.mobile.connectivity.WebsiteOpen;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;

import android.content.Context;
import android.net.ConnectivityManager;

public class AggregatorAccess {
	
	private ConnectivityManager connectivityManager;
	
	public AggregatorAccess(Context context) {
		connectivityManager
	    = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);		
	}
	
	public void aggregatorCheck() {
		
		Globals.aggregatorCommunication = false;
		if(WebsiteOpen.checkInternetAccess(connectivityManager)) {			
			try {
				Map<String, String> header = 
					WebsiteOpen.getHeaders(WebsiteOpen.openURLConnection(Constants.AGGREGATOR_URL));
				if(WebsiteOpen.getStatusCode(header) == 200) {
					Globals.aggregatorCommunication = true;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}