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

import java.util.ArrayList;
import java.util.List;

import org.umit.icm.mobile.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * Implementation of GoogleMaps. This class extends AbstractMap.
 */

public class GoogleMaps implements AbstractMap {
	
	MapController mapController;
	GeoPoint geoPoint;
				
	public GoogleMaps() {
		super();		
	}
		
	public MapView getView(final Context context, GeoPoint geoPoint){
		final  MapView googleMapView 
		= new MapView(context, context.getString(R.string.google_maps_api_key));			
		
		this.geoPoint = geoPoint;				
		
	    class GoogleMapMarker extends ItemizedOverlay<OverlayItem> {

	    	private List<OverlayItem> overlayList;
	    	private Context context;
	    
	    	public GoogleMapMarker(Drawable defaultMarker) {
	    		   super(boundCenterBottom(defaultMarker));
	    	}
	    	

	    	public GoogleMapMarker(Drawable drawable, Context context, List<OverlayItem> overlayList) {
	    		super(drawable);
	    		this.context = context;
	    		this.overlayList = overlayList;
	    		this.
	    		populate();
	    		
	    	}	 

	    	@Override
	    	protected OverlayItem createItem(int i) {
	    		return overlayList.get(i);
	    	}
	    	
	    	@Override
	    	public int size() {
	    		return overlayList.size();
	    	}
	    	
	    	@Override
	    	protected boolean onTap(int index) {
		        OverlayItem overlayItem = overlayList.get(index);
		        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		        dialog.setTitle(overlayItem.getTitle());
		        dialog.setMessage(overlayItem.getSnippet());
		        dialog.setPositiveButton("Okay", new OnClickListener() {    
		            public void onClick(DialogInterface dialog, int which) {
		                dialog.dismiss();
		            }
		        });
		        dialog.show();
		        return true;
	    	}
	    }
		mapController = googleMapView.getController();
		googleMapView.setBuiltInZoomControls(true);
        
        mapController.animateTo(geoPoint);     
        mapController.setZoom(15);
        googleMapView.invalidate();
        
        List<Overlay> overlayList = googleMapView.getOverlays();
        Drawable drawable = context.getResources().getDrawable(R.drawable.dot);
        GoogleMapMarker googleMapOverlay 
        = new GoogleMapMarker(drawable, context, getOverlayList(context));        
        overlayList.add(googleMapOverlay);
        googleMapView.invalidate();       
 
        return googleMapView;
	}
	
	public List<OverlayItem> getOverlayList(Context context) {
		List<OverlayItem> overlayList = new ArrayList<OverlayItem>();
		Drawable drawable = context.getResources().getDrawable(R.drawable.reddot);
		OverlayItem overlayItem 
		= new OverlayItem(geoPoint, "Title", "Info");
		drawable.setBounds(0,0, 10, 10);
		overlayItem.setMarker(drawable);
		overlayList.add(overlayItem);
		
		double lat = getLat(geoPoint);
		double lon = getLon(geoPoint);
		GeoPoint gpt = getGeoPoint(lat+0.01,lon); 		
		Drawable drawable2 = context.getResources().getDrawable(R.drawable.greendot);
		OverlayItem overlayItem2 
		= new OverlayItem(gpt, "Title2", "Info2");
		drawable2.setBounds(0,0, 10, 10);
		overlayItem2.setMarker(drawable2);
		overlayList.add(overlayItem2);
		
		return overlayList;
	}
	
	public static GeoPoint getGeoPoint(double lat, double lon)	{
		                 
        GeoPoint geoPoint = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lon * 1E6));
        
        return geoPoint;
	}
	
	public static double getLat(GeoPoint geoPoint)	{                        
            return (geoPoint.getLatitudeE6()/1E6);
	}
	
	public static double getLon(GeoPoint geoPoint)	{                        
        return (geoPoint.getLongitudeE6()/1E6);
	}
		
}