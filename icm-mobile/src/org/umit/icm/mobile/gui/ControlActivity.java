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
import java.util.StringTokenizer;

import org.umit.icm.mobile.R;
//import org.umit.icm.mobile.aggregator.AggregatorRetrieve;
import org.umit.icm.mobile.connectivity.ConnectivityService;
import org.umit.icm.mobile.gui.dialogs.IntervalDialog;
import org.umit.icm.mobile.gui.dialogs.MapSelectionDialog;
import org.umit.icm.mobile.gui.dialogs.SuggestionDialog;
import org.umit.icm.mobile.gui.dialogs.TwitterDialog;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.RequestHeader;
import org.umit.icm.mobile.proto.MessageProtos.ServiceSuggestion;
import org.umit.icm.mobile.proto.MessageProtos.WebsiteSuggestion;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class ControlActivity extends Activity {
    /** Called when the activity is first created. */
	private Button sendButton, intervalButton, scanButton
	, filterButton, servicesFilterButton, mapSelectionButton,
	enableTwitterButton;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.controlactivity);
        sendButton = (Button) this.findViewById(R.id.selected);
        intervalButton = (Button) this.findViewById(R.id.intervalButton);
        scanButton = (Button) this.findViewById(R.id.scanButton);
        filterButton = (Button) this.findViewById(R.id.filterButton);
        servicesFilterButton = (Button) this.findViewById(R.id.serviceFilterButton);
        mapSelectionButton = (Button) this.findViewById(R.id.mapSelectionButton);
        enableTwitterButton = (Button) this.findViewById(R.id.enableTwitterButton);
		scanButton.setText(getString(R.string.scan_text)
       				+" "+ getString(R.string.scan_off));
		try {
			if(Globals.runtimeParameters.getTwitter().equals("Off")) {
				enableTwitterButton.setText(getString(R.string.enable_twitter_button));
			} else {
				enableTwitterButton.setText(getString(R.string.disable_twitter_button));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RuntimeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		BroadcastReceiver receiver = new BroadcastReceiver() {
		   
			@Override
			public void onReceive(Context context, Intent intent) {				
				if(intent.getAction().equals("org.umit.icm.mobile.CONTROL_ACTIVITY")) {
					scanButton.setText(getString(R.string.scan_text)
		       				+" "+ getString(R.string.scan_on));
			      }				
			}
		  };

		registerReceiver(receiver
				, new IntentFilter("org.umit.icm.mobile.CONTROL_ACTIVITY"));
   	   	   	        
        sendButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	       		
	       		SuggestionDialog suggestionDialog = 
	       			new SuggestionDialog(ControlActivity.this, "", new OnReadyListener());
	            suggestionDialog.show();	        		
	       	}

	    }  );
        
        enableTwitterButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  
	       		try {
					if(Globals.runtimeParameters.getTwitter().equals("Off")) {
						TwitterDialog twitterDialog = 
							new TwitterDialog(ControlActivity.this, "");
						twitterDialog.show();	     
						enableTwitterButton.setText(getString(R.string.disable_twitter_button));
					} else {
						Globals.runtimeParameters.setTwitter("Off");
						enableTwitterButton.setText(getString(R.string.enable_twitter_button));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       		  
	       	}

	    }  );
        
        mapSelectionButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	       		
	       		MapSelectionDialog MapSelectionDialog = 
	       			new MapSelectionDialog(ControlActivity.this, "");
	            MapSelectionDialog.show();	        		
	       	}

	    }  );
        
        filterButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	       				            		 
	       		Intent intent = new Intent(ControlActivity.this, WebsiteFilterActivity.class);	       		
	            startActivity(intent); 
	       	}

	   	}  );
        
        servicesFilterButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	       				            		 
	       		Intent intent = new Intent(ControlActivity.this, ServiceFilterActivity.class);	       		
	            startActivity(intent); 
	       	}

	   	}  );
        
        
        intervalButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	       			       		       			
	       		IntervalDialog intervalDialog = 
	       			new IntervalDialog(ControlActivity.this, "", new OnReadyIntervalListener());
	            intervalDialog.show(); 
	       		
	       	}

	   	}  );
        
        scanButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {
	       		if(Globals.scanStatus.equalsIgnoreCase(getString(R.string.scan_on))){
	       			scanButton.setText(getString(R.string.scan_text)
		       				+" "+ getString(R.string.scan_on));
	       			Globals.scanStatus = getString(R.string.scan_off);
	       			stopService(new Intent(ControlActivity.this, ConnectivityService.class));
	       		}
	       			
	       		else{
	       			scanButton.setText(getString(R.string.scan_text)
		       				+" "+ getString(R.string.scan_off));
	       			Globals.scanStatus = getString(R.string.scan_on);
	       			startService(new Intent(ControlActivity.this, ConnectivityService.class));
	       		}
	       			
	       		try {
					Globals.runtimeParameters.setScanStatus(Globals.scanStatus);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (RuntimeException e) {
						e.printStackTrace();
				}
	       		
	       		Context context = getApplicationContext();
        		CharSequence text = getString(R.string.toast_scan_change) 
        		+ " " + Globals.scanStatus;
        		int duration = Toast.LENGTH_SHORT;

        		Toast toast = Toast.makeText(context, text, duration);
        		toast.show();
	       	}

	   	}  );
              
    }
    
    private class OnReadyListener implements SuggestionDialog.ReadyListener {
        @Override
        public void ready(String selection) {            
            StringTokenizer stringTokenizer 
            = new StringTokenizer(selection, "&");
            String option = stringTokenizer.nextToken();
            String suggestion = stringTokenizer.nextToken();
            String email = stringTokenizer.nextToken();
            String host = stringTokenizer.nextToken();
            String ip = stringTokenizer.nextToken();          
            if(option.equals("Website")) {          
            	  Toast.makeText(ControlActivity.this
                  		, getString(R.string.text_selected) 
                  		+ " " + option + " " + suggestion + " " + email
                  		, Toast.LENGTH_LONG).show();
            	new SendWebsiteTask().execute(email, suggestion);
            }
            else {
            	  Toast.makeText(ControlActivity.this
                  		, getString(R.string.text_selected) 
                  		+ " " + option + " " + suggestion + " " + email 
                  		+ " " + host + " " + ip
                  		, Toast.LENGTH_LONG).show();
            	new SendServiceTask().execute(email, suggestion
            			,host, ip);
            }
            
        }
    }
    
    private class OnReadyIntervalListener implements IntervalDialog.ReadyIntervalListener {
        @Override
        public void ready(String interval) {
            Toast.makeText(ControlActivity.this, interval, Toast.LENGTH_LONG).show();
        }
    }
    
    private class SendWebsiteTask extends AsyncTask<String,String,String> {
    	  
    	protected void onPostExecute(String result) {
    		if (result.equals("true")) {
    			int duration = Toast.LENGTH_SHORT;
        		Toast toast = Toast.makeText(ControlActivity.this
        				, "Website Suggestion Sent", duration);
        		toast.show();	
    		}
    	}
         
		protected String doInBackground(String... args) {	
			RequestHeader requestHeader
			= RequestHeader.newBuilder()
			.setAgentID(1)
			.setToken("1")
			.build();
        	WebsiteSuggestion websiteSuggestion
        	= WebsiteSuggestion.newBuilder()
        	.setEmailAddress(args[0])
        	.setHeader(requestHeader)
        	.setWebsiteURL(args[1])
        	.build();
        	//return AggregatorRetrieve.sendWebsiteSuggestion(websiteSuggestion);
        	return "true";			 						
		}
			
    }  
    
    private class SendServiceTask extends AsyncTask<String,String,String> {
  	  
    	protected void onPostExecute(String result) {
    		if (result.equals("true")) {
    			int duration = Toast.LENGTH_SHORT;
        		Toast toast = Toast.makeText(ControlActivity.this
        				, "Service Suggestion Sent", duration);
        		toast.show();	
    		}
    	}
         
		protected String doInBackground(String... args) {
			RequestHeader requestHeader
			= RequestHeader.newBuilder()
			.setAgentID(1)
			.setToken("1")
			.build();
        	ServiceSuggestion serviceSuggestion
        	= ServiceSuggestion.newBuilder()
        	.setEmailAddress(args[0])
        	.setServiceName(args[1])
        	.setHeader(requestHeader)
        	.setHostName(args[2])
        	.setIp(args[3])      
        	.build();
        	//AggregatorRetrieve.sendServiceSuggestion(serviceSuggestion);
        	return "true";			 						
		}
			
    }
      	 
}