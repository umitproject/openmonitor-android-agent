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
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;


import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.GetEvents;
import org.umit.icm.mobile.utils.Constants;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * This is the GetEventsService class which extends {@link Service}.
 */

public class GetEventsService extends Service {

	private Timer timer = new Timer();
		
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}		
	
	@Override
	public void onCreate() {

		super.onCreate();				
		startEventsService();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopScan();
	}
	
	/**
	 * Starts a new {@link Timer} and runs a {@link TimerTask} at the default
	 * interval. Sends GetEvents messages to the aggregator.
	 * 
	 * 

	@see         Timer
	 */	 	 
	private void startEventsService() {
		 int interval = Constants.DEFAULT_GET_EVENTS_INTERVAL;	
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				GetEvents getEvents = GetEvents.newBuilder()
				.setHeader(Globals.requestHeader)
				.addLocations("location1")
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
	void stopScan() {
		if (timer != null){
			timer.cancel();		
		}
				
	}
					
}