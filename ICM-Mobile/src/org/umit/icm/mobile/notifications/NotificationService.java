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

import org.umit.icm.mobile.InformationActivity;
import org.umit.icm.mobile.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class NotificationService extends Service {

	private NotificationManager nManager;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();

		nManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Toast.makeText(this,"Service created", Toast.LENGTH_LONG).show();
		showNotification();		
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
       	
        nManager.cancel(R.string.start_service);
		Toast.makeText(this, "Service stopped" , Toast.LENGTH_LONG).show();
	
	}
	
    
    private void showNotification() {
    
        CharSequence text = getText(R.string.start_service);

    
        Notification notification = new Notification(R.drawable.icon, text,
                System.currentTimeMillis());


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, InformationActivity.class), 0);


        notification.setLatestEventInfo(this, getText(R.string.service_name),
                       text, contentIntent);


        nManager.notify(R.string.start_service, notification);
    }
       
}
