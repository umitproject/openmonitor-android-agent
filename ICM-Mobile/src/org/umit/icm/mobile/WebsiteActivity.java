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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class WebsiteActivity extends Activity{
    /** Called when the activity is first created. */
	private ListView listView;
	
	private Button backButton;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Bundle bundle = this.getIntent().getExtras();
        String website = bundle.getString("websiteclicked");
        
        setContentView(R.layout.websiteactivity);
        listView = (ListView)findViewById(R.id.ListView01);
        
        String listViewItems[] = {getString(R.string.website_details), website};
        listView.setAdapter(new ArrayAdapter<String>(this
        		, android.R.layout.simple_list_item_1 
        		, listViewItems));
        
        backButton = (Button) findViewById(R.id.backButton);
        
        backButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	       				            		 
	       		Intent intent = new Intent(WebsiteActivity.this, Main.class);	       		
	            startActivity(intent); 
	       	}

	   	}  );
    }
}

