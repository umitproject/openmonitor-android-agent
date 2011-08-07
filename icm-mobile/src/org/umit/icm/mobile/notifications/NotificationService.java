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

package org.umit.icm.mobile.notifications;

import java.io.IOException;

import org.umit.icm.mobile.R;
import org.umit.icm.mobile.gui.InformationActivity;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;

import twitter4j.TwitterException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

/**
 * The Notification Service which notifies the user of any event.
 */

public class NotificationService extends Service {

	private NotificationManager mNotificationManager;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Initializes the notification manager and calls 
	 * {@link NotificationService#showNotification()}
	 */
	
	@Override
	public void onCreate() {
		super.onCreate();

		mNotificationManager 
		= (NotificationManager)getSystemService(NOTIFICATION_SERVICE);		
		BroadcastReceiver receiver = new BroadcastReceiver() {
			   
			@Override
			public void onReceive(Context context, Intent intent) {	
				Bundle bundle = intent.getExtras();
		        String message = bundle.getString("notification");
				if(intent.getAction().equals("org.umit.icm.mobile.NOTIFICATION_SERVICE")) {
					showNotification(message);	
			      }				
			}
		  };

		registerReceiver(receiver
				, new IntentFilter("org.umit.icm.mobile.NOTIFICATION_SERVICE"));
		
		BroadcastReceiver twitterReceiver = new BroadcastReceiver() {
			   
			@Override
			public void onReceive(Context context, Intent intent) {	
				Bundle bundle = intent.getExtras();
		        String message = bundle.getString("twitter");
				if(intent.getAction().equals("org.umit.icm.mobile.TWITTER_SERVICE")) {
					try {
						Globals.twitterUpdate.sendTweet(message + " " + Constants.TWITTER_HASHTAG);
					} catch (TwitterException e) {
						// TODO Auto-generated catch block
						String text = "Twitter: " + e.getErrorMessage();
			        	int duration = Toast.LENGTH_LONG;
			    		Toast toast = Toast.makeText(context, text, duration);
			    		toast.show();						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RuntimeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			      }				
			}
		  };

		registerReceiver(twitterReceiver
				, new IntentFilter("org.umit.icm.mobile.TWITTER_SERVICE"));	
		
	}
	
	/**
	 * Stops the service
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();       		
	
	}
	
	/**
	 * Shows dummy notification
	 *
	 * 
	 @see Notification
	 *
	 
	 @see NotificationManager
	 */
    private void showNotification(String message) {    	
    	String[] tokens = message.split(":");    	
    	String text = tokens[0];
    	int id = Integer.parseInt(tokens[1]);
    	
        Notification notification = new Notification(R.drawable.yellowdot, text,
                System.currentTimeMillis());

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, InformationActivity.class), 0);

        notification.setLatestEventInfo(this, text,
                       text, contentIntent);

        mNotificationManager.notify(id, notification);
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        mNotificationManager.cancel(id);
    }
}