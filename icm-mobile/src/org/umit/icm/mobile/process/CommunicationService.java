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

package org.umit.icm.mobile.process;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


import org.umit.icm.mobile.aggregator.AggregatorAccess;
import org.umit.icm.mobile.aggregator.AggregatorRetrieve;
import org.umit.icm.mobile.p2p.MessageForwardingAggregator;
import org.umit.icm.mobile.p2p.MessageSender;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.proto.MessageProtos.GetEvents;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerList;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerList;
import org.umit.icm.mobile.proto.MessageProtos.Location;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * This is the AggregatorService class which extends {@link Service}.
 * Runs timerTasks for the various AggregatorRetrieve calls.
 */

public class CommunicationService extends Service {
	
	private Timer peersTimer = new Timer();	
	private Timer eventsTimer = new Timer();
	private Timer accessTimer = new Timer();
			
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}		
	
	@Override
	public void onCreate() {

		super.onCreate();				
		startPeers();		
		startEvents();
		startAccess();		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();		
		stopPeers();
		stopEvents();
		stopAccess();
	}		

	/**
	 * Starts a new {@link Timer} and runs a {@link TimerTask} at the default
	 * interval. Sends GetPeerList messages to the aggregator.
	 * 
	 * 

	@see         Timer
	 */	 	 
	private void startPeers() {
		 int interval = Constants.DEFAULT_GET_UPDATES_INTERVAL;	
		peersTimer = new Timer();
		peersTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(Globals.aggregatorCommunication != false) {
					try {					
						GetPeerList getPeerList = GetPeerList.newBuilder()
						.setHeader(Globals.requestHeader)
						.build();
						AggregatorRetrieve.getPeerList(getPeerList);
						GetSuperPeerList getSuperPeerList = GetSuperPeerList.newBuilder()
						.setHeader(Globals.requestHeader)
						.build();
						AggregatorRetrieve.getSuperPeerList(getSuperPeerList);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(Globals.p2pCommunication != false) {	
					Iterator<AgentData> iterator = Globals.superPeersList.iterator();
					GetPeerList getPeerList = GetPeerList.newBuilder()
					.setHeader(Globals.requestHeader)
					.setCount(Constants.MAX_PEERS)
					.build();
					
					GetSuperPeerList getSuperPeerList = GetSuperPeerList.newBuilder()
					.setHeader(Globals.requestHeader)
					.setCount(Constants.MAX_SUPER_PEERS)
					.build();
					while(iterator.hasNext()) {
						try {
							MessageSender.receivePeerList(iterator.next(), getPeerList);
							MessageSender.receiveSuperPeerList(iterator.next(), getSuperPeerList);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
					
			}
		}, 0, interval * 60 * 1000); 
	}
	
	
	/**
	 * Cancels the {@link Timer}.
	 * 
	 * 
	 
	@see         Timer
	 */	 
	void stopPeers() {
		if (peersTimer != null){
			peersTimer.cancel();		
		}					
	}
	
	/**
	 * Starts a new {@link Timer} and runs a {@link TimerTask} at the default
	 * interval. Sends GetEvents messages to the aggregator.
	 * 
	 * 

	@see         Timer
	 */	 	 
	private void startEvents() {
		 int interval = Constants.DEFAULT_GET_EVENTS_INTERVAL;	
		eventsTimer = new Timer();
		eventsTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(Globals.aggregatorCommunication != false) {
					Location location = Location.newBuilder()
					.setLatitude(0.0)
					.setLongitude(0.0)
					.build();
					
					GetEvents getEvents = GetEvents.newBuilder()
					.setHeader(Globals.requestHeader)
					.addLocations(location)
					.build();
					
					try {
						AggregatorRetrieve.getEvents(getEvents);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RuntimeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}							
				}	else if(Globals.p2pCommunication != false) {	
						Iterator<AgentData> iterator = Globals.superPeersList.iterator();
						Location location = Location.newBuilder()
						.setLatitude(0.0)
						.setLongitude(0.0)
						.build();
						
						GetEvents getEvents = GetEvents.newBuilder()
						.setHeader(Globals.requestHeader)
						.addLocations(location)
						.build();
						while(iterator.hasNext()) {							
							try {
								MessageForwardingAggregator.forwardGetEvents(iterator.next(), getEvents);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}	
				}
			}			
		}, 0, interval * 60 * 1000); 
	}
	
	/**
	 * Cancels the {@link Timer}.
	 * 
	 * 
	 
	@see         Timer
	 */	 
	void stopEvents() {
		if (eventsTimer != null){
			eventsTimer.cancel();		
		}
				
	}
	
	/**
	 * Starts a new {@link Timer} and runs a {@link TimerTask} at the default
	 * interval. Sends GetEvents messages to the aggregator.
	 * 
	 * 

	@see         Timer
	 */	 	 
	private void startAccess() {
		 int interval = Constants.DEFAULT_AGGREGATOR_ACCESS_INTERVAL;
		 final AggregatorAccess aggregatorAccess = new AggregatorAccess(CommunicationService.this);
		 accessTimer = new Timer();
		 accessTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				aggregatorAccess.aggregatorCheck();
			}	
		}, 0, interval * 60 * 1000); 
	}
	
	/**
	 * Cancels the {@link Timer}.
	 * 
	 * 
	 
	@see         Timer
	 */	 
	void stopAccess() {
		if (accessTimer != null){
			accessTimer.cancel();		
		}
				
	}
}