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
import org.umit.icm.mobile.social.TwitterUpdate;

import twitter4j.TwitterException;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class TwitterDialog extends Dialog {
	
    EditText etEnable;
    private Context context;          
    
    public TwitterDialog(Context context, String interval 
            ) {
        super(context);    
        this.context = context;
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitterdialog);
        Button buttonSet = (Button) findViewById(R.id.pinButton);
        buttonSet.setOnClickListener(new intervalListener());    
        Button buttonEnable = (Button) findViewById(R.id.enableButton);
        buttonEnable.setOnClickListener(new enableListener());
        Button buttonDisable = (Button) findViewById(R.id.disableButton);
        buttonDisable.setOnClickListener(new disableListener());    
        etEnable = (EditText) findViewById(R.id.etPin);          
		
    }
        
    private class intervalListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (!etEnable.getText().toString().equals("")) {
				String pin = etEnable.getText().toString();
				try {
					Globals.twitterUpdate.enterPin(pin);
					Globals.twitterUpdate.sendTweet("First Tweet using app.");
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
    
    private class enableListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {
					
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
		
					        	                 		
		}			

    }
    
    private class disableListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {
		
			try {
				Globals.runtimeParameters.setTwitter("Off");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					        	                 		
		}			

    }        
    
}