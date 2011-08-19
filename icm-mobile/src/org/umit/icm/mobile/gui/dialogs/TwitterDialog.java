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

package org.umit.icm.mobile.gui.dialogs;


import java.io.IOException;

import org.apache.http.HttpException;
import org.umit.icm.mobile.R;
import org.umit.icm.mobile.process.Globals;

import twitter4j.TwitterException;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class TwitterDialog extends Dialog {
	
    private EditText etEnable;
    private Context context;
    private Button buttonSet;
    private ProgressDialog progressDialog;
    
    public TwitterDialog(Context context, String interval 
            ) {
        super(context);    
        this.context = context;
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitterdialog);
        buttonSet = (Button) findViewById(R.id.pinButton);
        buttonSet.setOnClickListener(new intervalListener());                                
        etEnable = (EditText) findViewById(R.id.etPin);
        progressDialog = ProgressDialog.show(context, 
        		context.getString(R.string.loading)	, context.getString(R.string.retrieving_website)
        		, true, false);
        new LaunchBrowser().execute();                
    }
        
    private class intervalListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (!etEnable.getText().toString().equals("")) {
				String pin = etEnable.getText().toString();
				try {
					Globals.twitterUpdate.enterPin(pin);
					Globals.runtimeParameters.setTwitter("On");
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				TwitterDialog.this.dismiss();
			}
					        	                 		
		}			

    }   
    
    private class LaunchBrowser extends AsyncTask<String,String,String> {
    	  
    	protected void onPostExecute(String str) {  
    		try {
    			Globals.runtimeParameters.setTwitter("Off");
    		} catch (RuntimeException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		Globals.twitterUpdate.reset();
    		try {
    			Globals.twitterUpdate.requestToken(context);
    		} catch (TwitterException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (HttpException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}		
    		
    		progressDialog.dismiss();
    	}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
         										
    }
    
}