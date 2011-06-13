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

import org.apache.http.HttpException;
import org.umit.icm.mobile.Main;
import org.umit.icm.mobile.R;
import org.umit.icm.mobile.connectivity.WebsiteOpen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class WebsiteActivity extends Activity{
    /** Called when the activity is first created. */
	private ListView listView;
	private Button backButton;
	private String website;
	private ProgressDialog progressDialog;
	
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        progressDialog = ProgressDialog.show(this, 
        		getString(R.string.loading)	, getString(R.string.retrieving_website)
        		, true, false);
        Bundle bundle = this.getIntent().getExtras();
        website = bundle.getString("websiteclicked");
        
        setContentView(R.layout.websiteactivity);
        listView = (ListView)findViewById(R.id.ListView01);                
        
        backButton = (Button) findViewById(R.id.backButton);
        
        backButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	       				            		 
	       		Intent intent = new Intent(WebsiteActivity.this, Main.class);	       		
	            startActivity(intent); 
	       	}

	   	}  );
        
        new UpdateFavicon().execute(website);
    }
    
    private class UpdateFavicon extends AsyncTask<String,String,WebsiteTextBitmapAdapter> {
    	  
    	protected void onPostExecute(WebsiteTextBitmapAdapter itla) {
    		listView.setAdapter(itla);
    		progressDialog.dismiss();
    	}
         
		protected WebsiteTextBitmapAdapter doInBackground(String... urls) {
			Bitmap favicon = null;
			WebsiteTextBitmapAdapter itla = new WebsiteTextBitmapAdapter(WebsiteActivity.this);
			Resources resources = getResources(); 
			try {
				 favicon = WebsiteOpen.getFavicon(website);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				favicon 
				= BitmapFactory.decodeResource(resources,
						R.drawable.tabs_icons);
				e.printStackTrace();
			} catch (HttpException e) {
				favicon 
				= BitmapFactory.decodeResource(resources,
						R.drawable.tabs_icons);
				e.printStackTrace();
			}
			Drawable d =new BitmapDrawable(favicon);
			itla.addItem(new WebsiteTextBitmap(website, d));
			return itla;
			 						
		}
			
    }
}

