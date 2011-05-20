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

package org.umit.icm.mobile.maps;

import java.util.List;

import org.umit.icm.mobile.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

/**
 * Implementation of GoogleMaps. This class extends AbstractMap.
 */

public class GoogleMaps extends AbstractMap {
	
		
	public GoogleMaps() {
		super();
		
	}
	
	
	public MapView getView(final Context context, MapView mapView){
		final  MapView googleMapView = mapView;
		MapController mapController;
		final GeoPoint geoPoint = GoogleMaps.getGeoPoint(52.212077, 0.091496); 
		
		class MapActivtyTabOverlay extends Overlay 
	    {
			
	        public boolean draw(Canvas canvas, MapView mapView, 
	        boolean shadow, long when) 
	        {
	            super.draw(canvas, mapView, shadow);                   
	            
	            Point point = new Point();
	            mapView.getProjection().toPixels(geoPoint, point);
	 
	            Bitmap bitMap = BitmapFactory.decodeResource(
	                context.getResources(), R.drawable.dot);            
	            canvas.drawBitmap(bitMap, point.x, point.y, null);         
	            return true;
	        }
	    } 
		mapController = googleMapView.getController();
        
        
        mapController.animateTo(geoPoint);
        mapController.setZoom(17); 
        MapActivtyTabOverlay mapOverlay = new MapActivtyTabOverlay();
        List<Overlay> listOfOverlays = googleMapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);
        googleMapView.invalidate();
 
		return googleMapView;
	}
	
		
	public static GeoPoint getGeoPoint(double lat, double lon)	{
		                 
        GeoPoint geoPoint = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lon * 1E6));
        
        return geoPoint;
	}
	
}
