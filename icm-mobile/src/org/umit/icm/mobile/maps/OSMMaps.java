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

import android.content.Context;
import android.view.View;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import com.google.android.maps.OverlayItem;

/**
 * Implementation of OSM. This class extends AbstractMap.
 */

public class OSMMaps implements AbstractMap {
		
	
	
	public OSMMaps() {
		super();
		
	}
	
	
	public View getView(Context context){
		final  MapView osmMapView = new MapView(context, 256);
		return osmMapView;
	}
	
	
	
	public GeoPoint getGeoPoint(double lat, double lon)	{
        
        GeoPoint geoPoint = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lon * 1E6));
        
        return geoPoint;
	}

	@Override
	public List<OverlayItem> getOverlayList(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getView(Context context,
			com.google.android.maps.MapView mapView,
			com.google.android.maps.GeoPoint geoPoint) {
		// TODO Auto-generated method stub
		return null;
	}
}
