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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;


import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.umit.icm.mobile.R;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.Event;



/**
 * Implementation of OSM. This class extends AbstractMap.
 */

public class OSMMaps implements AbstractMap {
	MapController mapController;
	GeoPoint geoPoint;
	ItemizedOverlay<OverlayItem> osmOverlay;
	ResourceProxy resourceProxy;
	Context context;
	
	public OSMMaps() {
		super();		
	}
		
	public MapView getView(Context context, double lat, double lon){
		final  MapView osmMapView = new MapView(context, 256);
		this.context = context;
		this.geoPoint = OSMMaps.getGeoPoint(lat, lon);
		this.resourceProxy = new DefaultResourceProxyImpl(context);
		Drawable drawable = context.getResources().getDrawable(R.drawable.dot);
		
		
		mapController = osmMapView.getController();
		osmMapView.setBuiltInZoomControls(true);
		osmMapView.setMultiTouchControls(true);
		mapController.setZoom(14);
		mapController.setCenter(this.geoPoint);  		
        osmMapView.invalidate();
        
        List<Overlay> overlayList = osmMapView.getOverlays();
        this.osmOverlay = new ItemizedIconOverlay<OverlayItem>(getOSMOverlayList(context),
                drawable, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index,
                            final OverlayItem item) {
                    	AlertDialog.Builder dialog 
                    	= new AlertDialog.Builder(OSMMaps.this.context);
        		        dialog.setTitle(item.getTitle());
        		        dialog.setMessage(item.getSnippet());
        		        dialog.setPositiveButton("Okay", new OnClickListener() {    
        		            public void onClick(DialogInterface dialog, int which) {
        		                dialog.dismiss();
        		            }
        		        });
        		        dialog.show();
                        return true; 
                    }
                    @Override
                    public boolean onItemLongPress(final int index,
                            final OverlayItem item) {    
                    	AlertDialog.Builder dialog 
                    	= new AlertDialog.Builder(OSMMaps.this.context);
        		        dialog.setTitle(item.getTitle());
        		        dialog.setMessage(item.getSnippet());
        		        dialog.setPositiveButton("Okay", new OnClickListener() {    
        		            public void onClick(DialogInterface dialog, int which) {
        		                dialog.dismiss();
        		            }
        		        });
        		        dialog.show();
                    	return true;
                    }
                }, resourceProxy);
	     
        overlayList.add(osmOverlay);
        osmMapView.invalidate();
		return osmMapView;
	}
	
	public void updateView(MapView osmMapView, Context context, double lat, double lon){		
		this.context = context;
		this.geoPoint = OSMMaps.getGeoPoint(lat, lon);
		this.resourceProxy = new DefaultResourceProxyImpl(context);
		Drawable drawable = context.getResources().getDrawable(R.drawable.dot);
		
		
		mapController = osmMapView.getController();
		osmMapView.setBuiltInZoomControls(true);
		osmMapView.setMultiTouchControls(true);
		mapController.setZoom(14);
		//to ensure that the map is always centered on the current position
		//mapController.setCenter(this.geoPoint);  		
        osmMapView.invalidate();
        
        List<Overlay> overlayList = osmMapView.getOverlays();
        this.osmOverlay = new ItemizedIconOverlay<OverlayItem>(getOSMOverlayList(context),
                drawable, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index,
                            final OverlayItem item) {
                    	AlertDialog.Builder dialog 
                    	= new AlertDialog.Builder(OSMMaps.this.context);
        		        dialog.setTitle(item.getTitle());
        		        dialog.setMessage(item.getSnippet());
        		        dialog.setPositiveButton("Okay", new OnClickListener() {    
        		            public void onClick(DialogInterface dialog, int which) {
        		                dialog.dismiss();
        		            }
        		        });
        		        dialog.show();
                        return true; 
                    }
                    @Override
                    public boolean onItemLongPress(final int index,
                            final OverlayItem item) {    
                    	AlertDialog.Builder dialog 
                    	= new AlertDialog.Builder(OSMMaps.this.context);
        		        dialog.setTitle(item.getTitle());
        		        dialog.setMessage(item.getSnippet());
        		        dialog.setPositiveButton("Okay", new OnClickListener() {    
        		            public void onClick(DialogInterface dialog, int which) {
        		                dialog.dismiss();
        		            }
        		        });
        		        dialog.show();
                    	return true;
                    }
                }, resourceProxy);
	     
        overlayList.add(osmOverlay);
        osmMapView.invalidate();	
	}
	
	public List<OverlayItem> getOSMOverlayList(Context context) {
		List<OverlayItem> overlayList = new ArrayList<OverlayItem>();
		
		Iterator<Event> iterator 
		= Globals.runtimeList.getEventsList().iterator();		
		while(iterator.hasNext()){
			Event event = iterator.next();
			Drawable drawable = context.getResources().getDrawable(R.drawable.greendot);
			double lat = getLat(geoPoint);
			double lon = getLon(geoPoint);
			GeoPoint gpt = getGeoPoint(lat+0.01,lon);
			
			if(event.getEventType().equals("CENSOR")
					||event.getEventType().equals("THROTTLING") 
					|| event.getEventType().equals("OFF_LINE")) {
				drawable = context.getResources().getDrawable(R.drawable.reddot);
			}
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			
			OverlayItem overlayItem 
			= new OverlayItem(event.getTestType(), event.getEventType() + "\n" +
					simpleDateFormat.format(event.getTimeUTC()) + "\n", gpt);
			drawable.setBounds(0,0, 10, 10);
			overlayItem.setMarker(drawable);
			overlayList.add(overlayItem);	
		}
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

	@Override
	public List<com.google.android.maps.OverlayItem> getOverlayList(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
