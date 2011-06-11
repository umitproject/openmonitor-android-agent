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


import java.io.IOException;

import org.umit.icm.mobile.R;
import org.umit.icm.mobile.gui.ControlActivity;
import org.umit.icm.mobile.gui.InformationActivity;
import org.umit.icm.mobile.gui.MapActivityTab;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.utils.Constants;
import org.umit.icm.mobile.utils.SDCardReadWrite;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

public class Main extends TabActivity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Resources resources = getResources(); 
        TabHost tabHost = getTabHost();
        
        Intent intent = new Intent().setClass(this, InformationActivity.class);
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(getString(R.string.tab_information)).setIndicator(getString(R.string.tab_information),
                          resources.getDrawable(R.drawable.tabs_icons)).setContent(intent);
        tabHost.addTab(tabSpec);
        
        intent = new Intent().setClass(this, MapActivityTab.class);
        tabSpec = tabHost.newTabSpec(getString(R.string.tab_map)).setIndicator(getString(R.string.tab_map),
                          resources.getDrawable(R.drawable.tabs_icons)).setContent(intent);
        tabHost.addTab(tabSpec);

        intent = new Intent().setClass(this, ControlActivity.class);
        tabSpec = tabHost.newTabSpec(getString(R.string.tab_control)).setIndicator(getString(R.string.tab_control),
                          resources.getDrawable(R.drawable.tabs_icons)).setContent(intent);
        tabHost.addTab(tabSpec);
    
        tabHost.setCurrentTab(0);       
        
        if(!SDCardReadWrite.checkSDCard()) {
        	String text = getString(R.string.no_sdcard);
        	int duration = Toast.LENGTH_LONG;
    		Toast toast = Toast.makeText(this, text, duration);
    		toast.show();
    		moveTaskToBack(true);        	
        } else {        	            	      			                         
	        try {
				if ((SDCardReadWrite.fileExists(Constants.INTERVAL_FILE
						, Constants.PARAMETERS_DIR) == false) 
					|| (SDCardReadWrite.fileNotEmpty(Constants.INTERVAL_FILE
				        		, Constants.PARAMETERS_DIR) == false )) {					
					Globals.runtimeParameters.setScanInterval(Constants.DEFAULT_SCAN_INTERVAL);
				}
				if ((SDCardReadWrite.fileExists(Constants.SCAN_FILE
				        		, Constants.PARAMETERS_DIR) == false )
						|| (SDCardReadWrite.fileNotEmpty(Constants.SCAN_FILE
				        		, Constants.PARAMETERS_DIR) == false )) {					
					Globals.runtimeParameters.setScanStatus(Constants.DEFAULT_SCAN_STATUS);					
				}
				if ((SDCardReadWrite.fileExists(Constants.AGENT_VERSION_FILE
						, Constants.VERSIONS_DIR) == false) 
					|| (SDCardReadWrite.fileNotEmpty(Constants.AGENT_VERSION_FILE
				        		, Constants.VERSIONS_DIR) == false )) {					
					Globals.versionManager.setAgentVersion(Constants.DEFAULT_AGENT_VERSION);
				}
				if ((SDCardReadWrite.fileExists(Constants.TESTS_VERSION_FILE
						, Constants.VERSIONS_DIR) == false) 
					|| (SDCardReadWrite.fileNotEmpty(Constants.TESTS_VERSION_FILE
				        		, Constants.VERSIONS_DIR) == false )) {					
					Globals.versionManager.setTestsVersion(Constants.DEFAULT_TESTS_VERSION);
				}
					
		        Globals.websiteTest.scan();		        
		        				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
	            
        }
    }
}