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

package org.umit.icm.mobile.gui;

import java.io.IOException;

import org.umit.icm.mobile.R;
import org.umit.icm.mobile.gui.dialogs.IntervalDialog;
import org.umit.icm.mobile.gui.dialogs.SuggestionDialog;
//import org.umit.icm.mobile.notifications.NotificationService;
import org.umit.icm.mobile.process.Globals;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class ControlActivity extends Activity {
    /** Called when the activity is first created. */
	private Button sendButton, intervalButton, scanButton;
	private String scanStatus;
		

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.controlactivity);
        sendButton = (Button) this.findViewById(R.id.selected);
        intervalButton = (Button) this.findViewById(R.id.intervalButton);
        scanButton = (Button) this.findViewById(R.id.scanButton);
        try {
			scanStatus = Globals.runtimeParameters.getScanStatus();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		
		if(scanStatus.compareTo(getString(R.string.scan_on)) == 0){
   			scanButton.setText(getString(R.string.scan_text)
       				+" "+ getString(R.string.scan_on));
   			scanStatus = getString(R.string.scan_off);
   		}
   			
   		else{
   			scanButton.setText(getString(R.string.scan_text)
       				+" "+ getString(R.string.scan_off));
   			scanStatus = getString(R.string.scan_on);
   		}
        
        sendButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	       		
	       		SuggestionDialog suggestionDialog = 
	       			new SuggestionDialog(ControlActivity.this, "", new OnReadyListener());
	            suggestionDialog.show();	        		
	       	}

	    }  );
        
        intervalButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  
	       		
	       		//startService(new Intent(ControlActivity.this,NotificationService.class));
	       		//stopService(new Intent(ControlActivity.this,NotificationService.class));
	       		
	       		IntervalDialog intervalDialog = 
	       			new IntervalDialog(ControlActivity.this, "", new OnReadyIntervalListener());
	            intervalDialog.show();
	       		
	       	}

	   	}  );
        
        scanButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {
	       		if(scanStatus.compareTo(getString(R.string.scan_on)) == 0){
	       			scanButton.setText(getString(R.string.scan_text)
		       				+" "+ getString(R.string.scan_on));
	       			scanStatus = getString(R.string.scan_off);
	       			Globals.websiteTest.stopScan();
	       		}
	       			
	       		else{
	       			scanButton.setText(getString(R.string.scan_text)
		       				+" "+ getString(R.string.scan_off));
	       			scanStatus = getString(R.string.scan_on);
	       			Globals.websiteTest.scan();
	       		}
	       			
	       		try {
					Globals.runtimeParameters.setScanStatus(scanStatus);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (RuntimeException e) {
						e.printStackTrace();
				}
	       		
	       		Context context = getApplicationContext();
        		CharSequence text = getString(R.string.toast_scan_change) 
        		+ " " + scanStatus;
        		int duration = Toast.LENGTH_SHORT;

        		Toast toast = Toast.makeText(context, text, duration);
        		toast.show();
	       	}

	   	}  );
              
    }
    
    private class OnReadyListener implements SuggestionDialog.ReadyListener {
        @Override
        public void ready(String selection) {
            Toast.makeText(ControlActivity.this, selection, Toast.LENGTH_LONG).show();
        }
    }
    
    private class OnReadyIntervalListener implements IntervalDialog.ReadyIntervalListener {
        @Override
        public void ready(String interval) {
            Toast.makeText(ControlActivity.this, interval, Toast.LENGTH_LONG).show();
        }
    }
   
}