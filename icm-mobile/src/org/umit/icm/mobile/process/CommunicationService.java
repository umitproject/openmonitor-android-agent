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
import org.umit.icm.mobile.proto.MessageProtos.RequestHeader;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * This is the AggregatorService class which extends {@link Service}.
 * Runs timerTasks for the various AggregatorRetrieve calls.
 */

public class CommunicationService extends Service {
	
	private Timer peersTimer = new Timer();	
	private Timer eventsTimer = new Timer();
	private Timer accessTimer = new Timer();
	private LocationManager locationManager;
	private LocationListener locationListenerGPS;	
	private LocationListener locationListenerNetwork;	
			
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
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			getCurrentLocationGPS();
		} else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			getCurrentLocationNetwork();
		}
				
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
				if(Globals.aggregatorCommunication == true) {
					try {		
						RequestHeader requestHeader = RequestHeader.newBuilder()
						.setAgentID(Globals.runtimeParameters.getAgentID())
						.build();
						
						GetPeerList getPeerList = GetPeerList.newBuilder()
						.build();
						AggregatorRetrieve.getPeerList(getPeerList);
						/*GetSuperPeerList getSuperPeerList = GetSuperPeerList.newBuilder()
						.build();*/
						GetSuperPeerList.Builder getSuperPeerListBuilder = GetSuperPeerList.newBuilder();
						getSuperPeerListBuilder.setLocation("UN");
						GetSuperPeerList getSuperPeerList = getSuperPeerListBuilder.build();
						AggregatorRetrieve.getSuperPeerList(getSuperPeerList);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(Globals.p2pCommunication == true) {
					RequestHeader requestHeader = RequestHeader.newBuilder()
					.setAgentID(Globals.runtimeParameters.getAgentID())
					.build();
					
					Iterator<AgentData> iterator 
					= Globals.runtimesList.getSuperPeersList().iterator();
					GetPeerList getPeerList = GetPeerList.newBuilder()
					.setCount(Constants.MAX_PEERS)
					.build();
					
					GetSuperPeerList getSuperPeerList = GetSuperPeerList.newBuilder()
					.setCount(Constants.MAX_SUPER_PEERS)
					.build();
					
					AgentData peer = null;
					while(iterator.hasNext()) {
						try {							
							peer = iterator.next();
							if(Globals.authenticatedPeers.checkPeer(peer) == true) {
								MessageSender.receivePeerList(peer, getPeerList);
								MessageSender.receiveSuperPeerList(peer, getSuperPeerList);
							}
							
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
				double lat = 0.0;
				double lon = 0.0;
				if(Globals.currentLocationGPS != null) {
					lat = Globals.currentLocationGPS.getLatitude();
					lon = Globals.currentLocationGPS.getLongitude();
				} else if(Globals.currentLocationNetwork != null) {
					lat = Globals.currentLocationNetwork.getLatitude();
					lon = Globals.currentLocationNetwork.getLongitude();
				}
				if(Globals.aggregatorCommunication == true) {	
					RequestHeader requestHeader = RequestHeader.newBuilder()
					.setAgentID(Globals.runtimeParameters.getAgentID())
					.build();
					
					Location location = Location.newBuilder()
					.setLatitude(lat)
					.setLongitude(lon)
					.build();
					
					GetEvents getEvents = GetEvents.newBuilder()
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
				} else if(Globals.p2pCommunication == true) {
					RequestHeader requestHeader = RequestHeader.newBuilder()
					.setAgentID(Globals.runtimeParameters.getAgentID())
					.build();
					
					Iterator<AgentData> iterator 
					= Globals.runtimesList.getSuperPeersList().iterator();
					Location location = Location.newBuilder()
					.setLatitude(lat)
					.setLongitude(lon)
					.build();
					
					GetEvents getEvents = GetEvents.newBuilder()
					.addLocations(location)
					.build();
					
					AgentData peer = null;
					while(iterator.hasNext()) {
						peer = iterator.next();						
						try {
							if(Globals.authenticatedPeers.checkPeer(peer) == true) {
								MessageForwardingAggregator.forwardGetEvents(peer, getEvents);
							}							
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
	
	private void getCurrentLocationGPS() {		
		locationListenerGPS = new LocationListener() {

			@Override
			public void onLocationChanged(android.location.Location location) {
				Globals.currentLocationGPS = location;
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);
	}
	
	private void getCurrentLocationNetwork() {		
		locationListenerNetwork = new LocationListener() {

			@Override
			public void onLocationChanged(android.location.Location location) {
				Globals.currentLocationNetwork = location;
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
		};
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
	}
}