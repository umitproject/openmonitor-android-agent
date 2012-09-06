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
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.proto.MessageProtos.ServiceReport;
import org.umit.icm.mobile.utils.SDCardReadWrite;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ServiceActivity extends Activity{
    /** Called when the activity is first created. */
	private ListView listView;
	private Button backButton;
	private String service;
	private ProgressDialog progressDialog;
	
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog = ProgressDialog.show(this, 
        		getString(R.string.loading)	, getString(R.string.retrieving_service)
        		, true, false);
        Bundle bundle = this.getIntent().getExtras();
        service = bundle.getString("serviceclicked");
        
        setContentView(R.layout.serviceactivity);
        listView = (ListView)findViewById(R.id.ListView01);                
        
        backButton = (Button) findViewById(R.id.backButton);
        
        backButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	       				            		 
	       		ServiceActivity.this.finish();
	       	}

	   	}  );
        
        new UpdateFavicon().execute(service);
    }
    
    private class UpdateFavicon extends AsyncTask<String,String,WebsiteTextBitmapAdapter> {
    	  
    	protected void onPostExecute(WebsiteTextBitmapAdapter websiteData) {
    		listView.setAdapter(websiteData);
    		progressDialog.dismiss();
    	}
         
		protected WebsiteTextBitmapAdapter doInBackground(String... urls) {			
			WebsiteTextBitmapAdapter websiteTextBitmapAdapter 
			= new WebsiteTextBitmapAdapter(ServiceActivity.this);							
			websiteTextBitmapAdapter.addItem(
					new WebsiteTextBitmap(service, getResources().getDrawable(R.drawable.bluedot)));
			String serviceFilename 
			= service + Constants.SERVICE_FILE;
			try {
				if(SDCardReadWrite.fileExists(serviceFilename, Constants.SERVICES_DIR)) {
					
					ServiceReport serviceReport = SDCardReadWrite.readServiceReport(Constants.SERVICES_DIR, service);
					
					if(serviceReport.getReport().getStatusCode() == 0) {
						
						websiteTextBitmapAdapter.addItem(new WebsiteTextBitmap(getString(R.string.normal_status), 
								getResources().getDrawable(R.drawable.greendot)));
						
						websiteTextBitmapAdapter.addItem(new WebsiteTextBitmap(getString(R.string.host) + " " 
						+ serviceReport.getReport().getServiceName(), getResources().getDrawable(R.drawable.greendot)));
						
						websiteTextBitmapAdapter.addItem(new WebsiteTextBitmap(getString(R.string.port) + " "
						+ serviceReport.getReport().getPort(), getResources().getDrawable(R.drawable.greendot)));
						
						websiteTextBitmapAdapter.addItem(new WebsiteTextBitmap(getString(R.string.status) + " " 
						+ serviceReport.getReport().getStatusCode(), getResources().getDrawable(R.drawable.greendot)));
						
						
					} else {
						
						websiteTextBitmapAdapter.addItem(new WebsiteTextBitmap( getString(R.string.differentation_status), getResources().getDrawable(R.drawable.reddot)));
						
					}
					
				} else {
					
					websiteTextBitmapAdapter.addItem(new WebsiteTextBitmap(getString(R.string.no_scan), getResources().getDrawable(R.drawable.bluedot)));
					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			return websiteTextBitmapAdapter;
			 						
		}			
    }
}