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
import java.util.Timer;
import java.util.TimerTask;


import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.GetPeerList;
import org.umit.icm.mobile.proto.MessageProtos.GetSuperPeerList;
import org.umit.icm.mobile.proto.MessageProtos.NewTests;
import org.umit.icm.mobile.utils.Constants;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * This is the GetUpdatesService class which extends {@link Service}.
 */

public class GetUpdatesService extends Service {

	private Timer testsTimer = new Timer();
	private Timer peersTimer = new Timer();
	private Timer superPeersTimer = new Timer();
		
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}		
	
	@Override
	public void onCreate() {

		super.onCreate();		
		startTests();
		startPeers();
		startSuperPeers();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopTests();
		stopPeers();
		stopSuperPeers();
	}
	
	/**
	 * Starts a new {@link Timer} and runs a {@link TimerTask} at the default
	 * interval. Sends GetTestsList, GetPeerList and GetSuperPeerList messages 
	 * to the aggregator.
	 * 
	 * 

	@see         Timer
	 */	 	 
	private void startTests() {
		 int interval = Constants.DEFAULT_GET_UPDATES_INTERVAL;	
		testsTimer = new Timer();
		testsTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {			
				try {
					Thread.sleep(300000);
					NewTests getTestList = NewTests.newBuilder()
					.setHeader(Globals.requestHeader)
					.setCurrentTestVersionNo(Globals.versionManager.getTestsVersion())
					.build();
					AggregatorRetrieve.checkTests(getTestList);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}	
		}, 0, interval * 1000); 
	}
	
	/**
	 * Cancels the {@link Timer}.
	 * 
	 * 
	 
	@see         Timer
	 */	 
	void stopTests() {
		if (testsTimer != null){
			testsTimer.cancel();		
		}					
	}
	
	/**
	 * Starts a new {@link Timer} and runs a {@link TimerTask} at the default
	 * interval. Sends GetTestsList, GetPeerList and GetSuperPeerList messages 
	 * to the aggregator.
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
				try {
					Thread.sleep(600000);
					GetPeerList getPeerList = GetPeerList.newBuilder()
					.setHeader(Globals.requestHeader)
					.build();
					AggregatorRetrieve.getPeerList(getPeerList);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}	
		}, 0, interval+30 * 1000); 
	}
	
	/**
	 * Starts a new {@link Timer} and runs a {@link TimerTask} at the default
	 * interval. Sends GetTestsList, GetPeerList and GetSuperPeerList messages 
	 * to the aggregator.
	 * 
	 * 

	@see         Timer
	 */	 	 
	private void startSuperPeers() {
		 int interval = Constants.DEFAULT_GET_UPDATES_INTERVAL;	
		peersTimer = new Timer();
		peersTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {			
				try {
					Thread.sleep(900000);
					GetSuperPeerList getSuperPeerList = GetSuperPeerList.newBuilder()
					.setHeader(Globals.requestHeader)
					.build();
					AggregatorRetrieve.getSuperPeerList(getSuperPeerList);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}	
		}, 0, interval+60 * 1000); 
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
	 * Cancels the {@link Timer}.
	 * 
	 * 
	 
	@see         Timer
	 */	 
	void stopSuperPeers() {
		if (superPeersTimer != null){
			superPeersTimer.cancel();		
		}
							
	}
}