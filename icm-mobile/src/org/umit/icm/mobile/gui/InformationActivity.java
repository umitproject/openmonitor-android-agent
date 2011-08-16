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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.umit.icm.mobile.R;
import org.umit.icm.mobile.connectivity.Service;
import org.umit.icm.mobile.connectivity.Website;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.process.Initialization;
import org.umit.icm.mobile.utils.SDCardReadWrite;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * InformationActivity that constitutes the Information tab. 
 * Displays the Websites list and Services list.
 */

public class InformationActivity extends Activity{
   	
	private ListView listView, listViewServices;
	private TextView goToServices, goToWebsites;
	ArrayAdapter<String> arrayAdapter, arrayAdapterServices;
	ViewFlipper viewFlipper;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informationactivity);                
        goToServices = (TextView) findViewById(R.id.goToServices);       
        goToWebsites = (TextView) findViewById(R.id.goToWebsites);         
        listView = (ListView)findViewById(R.id.ListView01);
        listViewServices = (ListView)findViewById(R.id.ListView02);
        viewFlipper = (ViewFlipper)findViewById(R.id.flipper);
        
        goToServices.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	                		 
	       		viewFlipper.showNext();
	       	}

	   	}  );
        
        goToWebsites.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	                		 
	       		viewFlipper.showPrevious();
	       	}

	   	}  );
        
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
        
        listViewServices.setClickable(true);
        listViewServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {

          @Override
          public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        	  String item = (String) listViewServices.getItemAtPosition(position);
        	  Bundle bundle = new Bundle();	
        	  bundle.putString("serviceclicked",item);         		 
        	  Intent intent = new Intent(InformationActivity.this, ServiceActivity.class);
        	  intent.putExtras(bundle);
        	  startActivity(intent); 
          }
        });
        
        try {
			if ((SDCardReadWrite.fileExists(Constants.WEBSITES_LIST_FILE
					, Constants.WEBSITES_DIR) == true)){					
				Globals.websitesList 
				= SDCardReadWrite.readWebsitesList(Constants.WEBSITES_DIR);									

			} else {
				Initialization.intializeWebsitesList();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if ((SDCardReadWrite.fileExists(Constants.SERVICES_LIST_FILE
					, Constants.SERVICES_DIR) == true)){					
				Globals.servicesList 
				= SDCardReadWrite.readServicesList(Constants.SERVICES_DIR);									
			} else {
				Initialization.intializeServicesList();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		new UpdateList().execute("");
		new UpdateListServices().execute("");
    }
    private class UpdateList extends AsyncTask<String,String,List<Website>> {
  	  
    	protected void onPostExecute(List<Website> result) {
    		List<String> list = new ArrayList<String>();
    		Iterator<Website> iterator = result.iterator();
    		Website website = new Website();
    		 while(iterator.hasNext()){  
    			 website = iterator.next();
    			 if(website.getCheck().equals("true"))
    				 list.add(website.getUrl());
    		 }
   		 	arrayAdapter = new ArrayAdapter<String>(InformationActivity.this
   				 ,android.R.layout.simple_list_item_1 
   				 , list);   		 	
   		 	listView.setAdapter(arrayAdapter);	
    	}
         
		protected List<Website> doInBackground(String... urls) {		
			return Globals.websitesList;			 						
		}
			
    }    
    
    private class UpdateListServices extends AsyncTask<String,String,List<Service>> {
    	  
    	protected void onPostExecute(List<Service> result) {
    		List<String> list = new ArrayList<String>();
    		Iterator<Service> iterator = result.iterator();
    		Service service = new Service();
    		 while(iterator.hasNext()){  
    			 service = iterator.next();
    			 if(service.getCheck().equals("true"))
    				 list.add(service.getName());
    		 }
   		 	arrayAdapterServices = new ArrayAdapter<String>(InformationActivity.this
   				 ,android.R.layout.simple_list_item_1 
   				 , list);   		 	
   		 	listViewServices.setAdapter(arrayAdapterServices);	
    	}
         
		protected List<Service> doInBackground(String... urls) {		
			return Globals.servicesList;
			 						
		}
			
    }          	
      	
}