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

package org.umit.icm.mobile;

import org.umit.icm.mobile.R;
import org.umit.icm.mobile.connectivity.WebsiteOpen;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.text.format.Formatter;

public class InformationActivity extends Activity{
    /** Called when the activity is first created. */
	private CheckBox cbFilter;
	private ListView lv1;
	private TextView ipTextView;
	ArrayAdapter<String> lvAdapter;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informationactivity);
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        ipTextView = (TextView) findViewById(R.id.ipTextView);
        ipTextView.append(Formatter.formatIpAddress(ipAddress));
        
        cbFilter = (CheckBox) findViewById(R.id.check1);
        lv1 = (ListView)findViewById(R.id.ListView01);
                      
        new DownloadWebsite().execute("http://www.google.com");                                   
    }
    private class DownloadWebsite extends AsyncTask<String,String,String> {
    	  

    	protected void onPostExecute(String result) {
    		 if (result.equals(null)){
    			 String lv_arr[] = {getString(R.string.list_websites)
    					 , getString(R.string.list_services)
    					 , "No Internet Connection"};
    			 lvAdapter = new ArrayAdapter<String>(InformationActivity.this,android.R.layout.simple_list_item_1 , lv_arr);
        	     lv1.setAdapter(lvAdapter);
    		 } else {
    			 String lv_arr[] = {getString(R.string.list_websites)
    					 , getString(R.string.list_services)
    					 , result};
    			 lvAdapter = new ArrayAdapter<String>(InformationActivity.this,android.R.layout.simple_list_item_1 , lv_arr);
        	     lv1.setAdapter(lvAdapter);
    		 }
    		     		        		 
    	   }
         
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			try {
				return WebsiteOpen.getContent(urls[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}

			
    }
      
	
}