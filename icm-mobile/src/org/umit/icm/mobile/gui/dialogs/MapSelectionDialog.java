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

package org.umit.icm.mobile.gui.dialogs;

import java.util.regex.Pattern;

import org.umit.icm.mobile.R;
import org.umit.icm.mobile.process.Globals;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.RadioButton;



public class MapSelectionDialog extends Dialog {
	
	private RadioButton googleRB, osmRB;
    
    public MapSelectionDialog(Context context, String selection) {
        super(context);
  
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapselectiondialog);
        Button buttonOK = (Button) findViewById(R.id.backButton);
        buttonOK.setOnClickListener(new sendListener());
        googleRB = (RadioButton)findViewById(R.id.googlerb);
        osmRB = (RadioButton)findViewById(R.id.osmrb);
       
    }    
    
    private class sendListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {				        	        	  	    		
    	    if(googleRB.isChecked() == true
    	    		&& !Globals.mapView.equals("Google")) {
    	    	Globals.mapView = "Google";
    	    }
    	    
    	    else if(osmRB.isChecked() == true
    	    		&& !Globals.mapView.equals("OSMDroid")) {
    	    	Globals.mapView = "OSMDroid";
    	    }
    	    	    	    
			MapSelectionDialog.this.dismiss(); 
        		               
       		
		}

    }    
    
}