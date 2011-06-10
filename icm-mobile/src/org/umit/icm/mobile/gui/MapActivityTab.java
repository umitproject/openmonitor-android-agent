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



import org.umit.icm.mobile.R;
import org.umit.icm.mobile.R.id;
import org.umit.icm.mobile.R.layout;
import org.umit.icm.mobile.maps.GoogleMaps;
import org.umit.icm.mobile.maps.OSMMaps;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MapActivityTab extends MapActivity{
    /** Called when the activity is first created. */
	private Button ISPButton;
	String package1;
	MapView mapView;
		
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);
        ISPButton = (Button) findViewById(R.id.ISPButton);
        mapView = (MapView) findViewById(R.id.mapView);
        GoogleMaps googleMap = new GoogleMaps();
        mapView = googleMap.getView(this, mapView);
        
                  
        //OSMMaps osmMap = new OSMMaps();
        //setContentView(osmMap.getView(this));
        
        ISPButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  
	       		Bundle bundle = new Bundle();	
	       	 	bundle.putString("package1","Blank");         		 
	       		Intent i = new Intent(MapActivityTab.this, ISPActivity.class);
	       		i.putExtras(bundle);
	             startActivity(i); 
	       	}

	   	}  );
    }
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}

