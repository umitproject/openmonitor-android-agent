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
import org.umit.icm.mobile.utils.Constants;
import org.umit.icm.mobile.utils.RuntimeParameters;
import org.umit.icm.mobile.utils.SDCardReadWrite;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class Main extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Resources res = getResources(); 
        TabHost tabHost = getTabHost(); 
        TabHost.TabSpec spec;
        Intent intent;  
        
        try {
			if ((SDCardReadWrite.fileExists(Constants.INTERVAL_FILE
					, Constants.PARAMETERS_DIR) == false )
					&& (SDCardReadWrite.fileExists(Constants.SCAN_FILE
			        		, Constants.PARAMETERS_DIR) == false )) {
				RuntimeParameters runtimeParameters = new RuntimeParameters();
				runtimeParameters.setScanInterval(Constants.DEFAULT_SCAN_INTERVAL);
				runtimeParameters.setScanStatus(Constants.DEFAULT_SCAN_STATUS);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        intent = new Intent().setClass(this, InformationActivity.class);

        spec = tabHost.newTabSpec(getString(R.string.tab_information)).setIndicator(getString(R.string.tab_information),
                          res.getDrawable(R.drawable.tabs_icons)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, MapActivityTab.class);
        spec = tabHost.newTabSpec(getString(R.string.tab_map)).setIndicator(getString(R.string.tab_map),
                          res.getDrawable(R.drawable.tabs_icons)).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ControlActivity.class);
        spec = tabHost.newTabSpec(getString(R.string.tab_control)).setIndicator(getString(R.string.tab_control),
                          res.getDrawable(R.drawable.tabs_icons)).setContent(intent);
        tabHost.addTab(spec);
    

        tabHost.setCurrentTab(0);
    }
}