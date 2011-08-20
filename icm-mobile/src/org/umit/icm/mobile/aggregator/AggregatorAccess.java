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
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpException;
import org.umit.icm.mobile.connectivity.WebsiteOpen;
import org.umit.icm.mobile.p2p.MessageSender;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.proto.MessageProtos.AuthenticatePeer;
import org.umit.icm.mobile.utils.CryptoKeyReader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * To check if the aggregator can be reached.
 */

public class AggregatorAccess {
	
	private ConnectivityManager connectivityManager;
	
	public AggregatorAccess(Context context) {
		connectivityManager
	    = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);		
	}
	
	/**
	 * Sets the {@link Globals#aggregatorCommunication} object to false if the 
	 * aggregator can't be reached.
	 * 
	 * 
	 
	 @see WebsiteOpen 
	 */
	public void aggregatorCheck() {
		
		if(!WebsiteOpen.checkInternetAccess(connectivityManager)) {
			Globals.p2pCommunication = false;
			Globals.aggregatorCommunication = false;
			Log.w("Aggregator Access", "No Internet");
		} else {			
			try {
				Map<String, String> header = 
					WebsiteOpen.getHeaders(WebsiteOpen.openURLConnection(Constants.AGGREGATOR_URL));
				if(WebsiteOpen.getStatusCode(header) == 200) {
					Globals.aggregatorCommunication = true;
					Globals.p2pCommunication = false;
				} else {
					Globals.aggregatorCommunication = false;
					Globals.p2pCommunication = true;
					AuthenticatePeer authenticatePeer = AuthenticatePeer.newBuilder()
					.setAgentID(Globals.runtimeParameters.getAgentID())
					.setAgentType(Constants.AGENT_TYPE_NUMBER)
					.setAgentPort(Constants.MY_TCP_PORT)		
					.setCipheredPublicKey(new String(Globals.keyManager.getMyCipheredKey()))
					.build();
					Iterator<AgentData> iterator 
					= Globals.runtimesList.getSuperPeersList().iterator(); {
						while(iterator.hasNext()) {
							if(CryptoKeyReader.checkPeerSecretKey(iterator.next().getAgentIP()) == false) {
								try {
									MessageSender.authenticatePeer(iterator.next(), authenticatePeer);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
					Log.w("Aggregator Access", "Aggregator can't be reached");
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