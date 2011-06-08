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

import java.io.IOException;

import org.umit.icm.mobile.R;
import org.umit.icm.mobile.process.RuntimeParameters;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class IntervalDialog extends Dialog {
	
    private ReadyIntervalListener readyListener;
    EditText etInterval;
    private int newInterval; 
    private RuntimeParameters runtimeParameters;
   
    
    public IntervalDialog(Context context, String interval, 
            ReadyIntervalListener readyListener) {
        super(context);
        this.readyListener = readyListener;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intervaldialog);
        Button buttonSet = (Button) findViewById(R.id.intervalButton);
        buttonSet.setOnClickListener(new intervalListener());
        Button buttonInc = (Button) findViewById(R.id.tickerButtonUp);
        buttonInc.setOnClickListener(new IncButton());
        Button buttonDec = (Button) findViewById(R.id.tickerButtonDown);
        buttonDec.setOnClickListener(new DecButton());
        etInterval = (EditText) findViewById(R.id.etInterval);
        runtimeParameters = new RuntimeParameters();
        try {
			newInterval = runtimeParameters.getScanInterval();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		
    }
    
    public interface ReadyIntervalListener {
        public void ready(String interval);
    }
    
    private class intervalListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (!etInterval.getText().toString().equals(""))
				newInterval = Integer.parseInt(etInterval.getText().toString());
			readyListener.ready(Integer.toString(newInterval));
			try {
				runtimeParameters.setScanInterval(newInterval);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			IntervalDialog.this.dismiss();
					        	                 		
			}

    	}
  
    private class IncButton implements android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (!etInterval.getText().toString().equals(""))
				newInterval = Integer.parseInt(etInterval.getText().toString());
			newInterval++;
       		etInterval.setText(Integer.toString(newInterval));		
        	                 		
			}

    	}  
    
    private class DecButton implements android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (!etInterval.getText().toString().equals(""))
				newInterval = Integer.parseInt(etInterval.getText().toString());
			newInterval--;
       		etInterval.setText(Integer.toString(newInterval));		
        	                 		
			}

    	} 
}