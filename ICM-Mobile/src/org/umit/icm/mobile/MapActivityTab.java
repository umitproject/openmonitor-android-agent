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

import java.util.List;

import org.umit.icm.mobile.R;
import org.umit.icm.mobile.maps.GoogleMaps;
import org.umit.icm.mobile.maps.OSMMaps;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.google.android.maps.Overlay;

public class MapActivityTab extends MapActivity{
    /** Called when the activity is first created. */
	private Button ISPButton;
	String package1;
	GeoPoint geoPoint;
	MapController mapController;
	MapView mapView;
	
	class MapActivtyTabOverlay extends Overlay 
    {
		
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
            
            Point point = new Point();
            mapView.getProjection().toPixels(geoPoint, point);
 
            Bitmap bitMap = BitmapFactory.decodeResource(
                getResources(), R.drawable.dot);            
            canvas.drawBitmap(bitMap, point.x, point.y, null);         
            return true;
        }
    } 

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);
        ISPButton = (Button) findViewById(R.id.ISPButton);
        mapView = (MapView) findViewById(R.id.mapView);
        GoogleMaps googleMap = new GoogleMaps();
        geoPoint = googleMap.getGeoPoint(52.212077, 0.091496);
        
        
        mapController = mapView.getController();
        
        
        mapController.animateTo(geoPoint);
        mapController.setZoom(17); 
        MapActivtyTabOverlay mapOverlay = new MapActivtyTabOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);        
 
        mapView.invalidate();
        
       
        
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

