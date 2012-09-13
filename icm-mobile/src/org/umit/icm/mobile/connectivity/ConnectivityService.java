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

package org.umit.icm.mobile.connectivity;

import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;

import org.apache.http.HttpException;
import org.umit.icm.mobile.R;
import org.umit.icm.mobile.aggregator.AggregatorRetrieve;
import org.umit.icm.mobile.notifications.NotificationHelper;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.process.RuntimeParameters;
import org.umit.icm.mobile.proto.MessageProtos.NewTests;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * This is the WebsiteConnectivityService class which extends {@link Service}.
 */

public class ConnectivityService extends Service {

	private Timer timer = new Timer();
	private ConnectivityManager connectivityManager;
	private LocationManager locationManager;
	private LocationListener locationListenerGPS;
	private android.location.Location currentLocationGPS;	
	private LocationListener locationListenerNetwork;
	private android.location.Location currentLocationNetwork;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}		
	
	@Override
	public void onCreate() {

		super.onCreate();
		connectivityManager
	    = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			getCurrentLocationGPS();
		} else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			getCurrentLocationNetwork();
		}
		
		startScan();							
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopScan();
	}
	
	/**
	 * Starts a new {@link Timer} and runs a {@link TimerTask} at the default
	 * scan interval. Starts {@link WebsiteConnectivity#scan()}. Exists if
	 * there is no Internet access check by {@link WebsiteOpen#checkInternetAccess(ConnectivityManager)}.
	 * 
	 * 
	 
	 @see         RuntimeParameters
	 *
	 
	@see         Timer
	 */	 	 
	private void startScan() {
		
		
		 int interval = Constants.DEFAULT_SCAN_INTERVAL;
		 RuntimeParameters runtimeParameters = new RuntimeParameters();
		 try {
				interval = runtimeParameters.getScanInterval();
		 } catch (Exception e) {
				e.printStackTrace();
		 }	
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				
					NewTests newTests = NewTests.newBuilder()
							.setCurrentTestVersionNo(Constants.DEFAULT_TESTS_VERSION)
							.build();
					try {
						AggregatorRetrieve.checkTests(newTests);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				
				
				try {			
					if(Globals.scanStatus.equals(getString(R.string.scan_off)))
						stopScan();
					if(Constants.DEBUG_MODE)
						System.out.println("STARTING SERVICES SCAN ------------------------------------");
					Globals.serviceTest.scan();
					
				} catch (IOException e) {
					if(!WebsiteOpen.checkInternetAccess(connectivityManager))						
						stopScanNotify();					
				} catch (HttpException e) {
					if(!WebsiteOpen.checkInternetAccess(connectivityManager))
						stopScanNotify();					
				} catch (MessagingException e) {
					if(!WebsiteOpen.checkInternetAccess(connectivityManager))						
						stopScanNotify();
				} 
				
				try {
					if(Constants.DEBUG_MODE)
						System.out.println("STARTING WEBSITES SCAN ------------------------------------");
					Globals.websiteTest.scan();
				} catch (IOException e) {
					if(!WebsiteOpen.checkInternetAccess(connectivityManager))						
						stopScanNotify();					
				} catch (HttpException e) {
					if(!WebsiteOpen.checkInternetAccess(connectivityManager))						
						stopScanNotify();					
				}
				
				
				
				Context context = getApplicationContext();
				Calendar calendar = Calendar.getInstance();
				NotificationHelper.sendNotification(
						getString(R.string.scan_complete_id)
						, getString(R.string.scan_complete)
						, context);
				double lat = 0.0;
				double lon = 0.0;
				if(currentLocationGPS != null) {
					lat = currentLocationGPS.getLatitude();
					lon = currentLocationGPS.getLongitude();
				} else if(currentLocationNetwork != null) {
					lat = currentLocationNetwork.getLatitude();
					lon = currentLocationNetwork.getLongitude();
				}
				String latitude = "";
				String longitude = "";
				if(lat != 0.0 && lon != 0.0) {
					latitude = Double.toString(lat);
					longitude = Double.toString(lon);
				}
				Globals.twitterUpdate.sendTweet(
						getString(R.string.scan_complete_at) + " "	
						+ String.valueOf(calendar.getTime()) + " Lat: "
						+ latitude + " Lon: " + longitude
						, context);	
											
			}	
		}, 0, interval * 60 * 1000); 
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
	
	
	/**
	 * Broadcasts an {@link Intent} message to {@link ControlActivity} to
	 * change the scan status.
	 * 
	 * 
	 
	 @see         Intent
	 *
	 
	 @see         Context
	 *
	 
	@see         Timer
	 */	 
	void stopScanNotify() {
		if (timer != null){
			timer.cancel();
			Globals.scanStatus = getString(R.string.scan_off);
			Context context = getApplicationContext();			
			Intent intent = new Intent("org.umit.icm.mobile.CONTROL_ACTIVITY");			
			context.sendBroadcast(intent);		
			stopSelf();
			
		}
				
	}	
	

	private void getCurrentLocationGPS() {		
		locationListenerGPS = new LocationListener() {

			@Override
			public void onLocationChanged(android.location.Location location) {
				currentLocationGPS = location;
				
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
				currentLocationNetwork = location;
				
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