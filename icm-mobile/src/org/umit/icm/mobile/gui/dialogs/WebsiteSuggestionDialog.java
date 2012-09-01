/**
 * Copyright (C) 2011 Adriano Monteiro Marques
 *
 * Author:  Gautam Bajaj <gautam1237@gmail.com>
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


import org.umit.icm.mobile.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WebsiteSuggestionDialog extends Dialog{
	
	String selection;
	Context contextControl;
    private ReadyListener readyListener;
    EditText WebsiteURL;
	
	 public WebsiteSuggestionDialog(Context context, String selection, 
	            ReadyListener readyListener) {
	        super(context);
	        this.selection = selection;
	        this.readyListener = readyListener;
	        this.contextControl = context;
	    }
	 
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.suggestwebsitedialog);
	        Button buttonOK = (Button) findViewById(R.id.SendWebsiteSuggestion);
	        buttonOK.setOnClickListener(new sendListener());
	        WebsiteURL = (EditText) findViewById(R.id.WebsiteURL);
	    }
	 
	 
	 public interface ReadyListener {
	        public void ready(String selection);
	    }
	 
	 private class sendListener implements android.view.View.OnClickListener {

			@Override
			public void onClick(View arg0) {	
				Context context = WebsiteSuggestionDialog.this.getContext();
				if((WebsiteURL.getText().toString().equals(""))){
	        		
	        		CharSequence text = context.getString(R.string.suggested_website_name_blank);
	        		int duration = Toast.LENGTH_SHORT;

	        		Toast toast = Toast.makeText(context, text, duration);
	        		toast.show();        		
	        	} 
	        	else{
	        		readyListener.ready("Website" 
	        				+ "&" + WebsiteURL.getText().toString()     				    	    			
		    	    	+ "&" + "host" 
		    	    	+ "&" + "ip"
		    	    	+ "&" + "port");
		    	        WebsiteSuggestionDialog.this.dismiss(); 
	        		}                
	       		
				}

	    	}
}
