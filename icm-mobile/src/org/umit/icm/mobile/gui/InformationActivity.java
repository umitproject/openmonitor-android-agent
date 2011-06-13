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


import java.util.List;

import org.umit.icm.mobile.R;
import org.umit.icm.mobile.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.text.format.Formatter;

public class InformationActivity extends Activity{
   	
	private ListView listView;
	private TextView ipTextView;
	ArrayAdapter<String> arrayAdapter;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informationactivity);
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        ipTextView = (TextView) findViewById(R.id.ipTextView);
        ipTextView.append(Formatter.formatIpAddress(ipAddress));    
        listView = (ListView)findViewById(R.id.ListView01);  
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

          @Override
          public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        	  String item = (String) listView.getItemAtPosition(position);
        	  Bundle bundle = new Bundle();	
        	  bundle.putString("websiteclicked",item);         		 
        	  Intent intent = new Intent(InformationActivity.this, WebsiteActivity.class);
        	  intent.putExtras(bundle);
        	  startActivity(intent); 
          }
        });
        
        new UpdateList().execute("");
    }
    private class UpdateList extends AsyncTask<String,String,List<String>> {
  	  
    	protected void onPostExecute(List<String> result) {
   		 arrayAdapter = new ArrayAdapter<String>(InformationActivity.this
   				 ,android.R.layout.simple_list_item_1 
   				 , result);
   		 listView.setAdapter(arrayAdapter);	
    	}
         
		protected List<String> doInBackground(String... urls) {		
			return Constants.WEBSITE_LIST;
			 						
		}
			
    }    
      	
}