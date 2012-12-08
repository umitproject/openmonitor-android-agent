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


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.umit.icm.mobile.R;
import org.umit.icm.mobile.connectivity.Service;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.utils.SDCardReadWrite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ServiceFilterActivity extends Activity{
   	
	private ListView listView;	
	private Button backButton, selectAllButton, unselectAllButton;
	private WebsiteTextCheckboxAdapter serviceTextCheckboxAdapter;	
	private List<WebsiteTextCheckbox> listServicesCheckbox;
	private String currentName;
	private Service currentService;
	private boolean currentCheck;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.servicefilteractivity);        
        listServicesCheckbox 
        = new ArrayList<WebsiteTextCheckbox>();
                        
        backButton = (Button) findViewById(R.id.backButton);        
        backButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	   
	       		Iterator<WebsiteTextCheckbox> iteratorCheck 
	       		= listServicesCheckbox.iterator();	       		
	       		int i = 0;
	       		String check = "";
	       		Service service = new Service();
	       		WebsiteTextCheckbox websiteTextCheckbox = null;
	    		while(iteratorCheck.hasNext()){ 
	    			websiteTextCheckbox = iteratorCheck.next();
	    			if(websiteTextCheckbox.isCheck() == true) {
	    				check = "true";	    				
	    			}    				
	    			else
    				check = "false";
	    			service = Globals.runtimeList.servicesList.get(i);
	    			service.setCheck(check);
	    			Globals.runtimeList.servicesList.set(i, 
	    					service);	
	    			i++;
	            } 
	    		try {
    				SDCardReadWrite.writeServicesList(Constants.SERVICES_DIR,
    						Globals.runtimeList.servicesList);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
	       		ServiceFilterActivity.this.finish();	       			             
	       	}

	   	}  );
                    
        listView = (ListView)findViewById(R.id.ListView01);
        serviceTextCheckboxAdapter 
        = new WebsiteTextCheckboxAdapter(ServiceFilterActivity.this);
		Iterator<Service> iterator = Globals.runtimeList.servicesList.iterator();
		while(iterator.hasNext()){               
			currentService = iterator.next();
			currentName = currentService.getName();
			if (currentService.getCheck().equals("true"))
				currentCheck = true;
			else 
				currentCheck = false;			
			listServicesCheckbox.add(new WebsiteTextCheckbox(currentName, currentCheck));						       			
        } 
     
        selectAllButton = (Button) findViewById(R.id.selectAllButton);        
        selectAllButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	   
	       		Iterator<WebsiteTextCheckbox> iteratorCheck 
	       		= listServicesCheckbox.iterator();	  	       		
	       		while(iteratorCheck.hasNext()) {
	       			iteratorCheck.next().setCheck(true);	       			
	       		}
	       		serviceTextCheckboxAdapter.setListItems(listServicesCheckbox);        	
	            listView.setAdapter(serviceTextCheckboxAdapter);
	       	}
	   	}  );
        
        unselectAllButton = (Button) findViewById(R.id.unselectAllButton);        
        unselectAllButton.setOnClickListener(new OnClickListener() { 
	       	public void onClick(View v) {  	   
	       		Iterator<WebsiteTextCheckbox> iteratorCheck 
	       		= listServicesCheckbox.iterator();	  	       		
	       		while(iteratorCheck.hasNext()) {
	       			iteratorCheck.next().setCheck(false);	       			
	       		}
	       		serviceTextCheckboxAdapter.setListItems(listServicesCheckbox);        	
	            listView.setAdapter(serviceTextCheckboxAdapter);	
	       	}
	   	}  );
        
        serviceTextCheckboxAdapter.setListItems(listServicesCheckbox);        	
        listView.setAdapter(serviceTextCheckboxAdapter);
                  		                       
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	Iterator<WebsiteTextCheckbox> iteratorCheck 
       		= listServicesCheckbox.iterator();	       		
       		int i = 0;
       		String check = "";
       		Service service = new Service();
       		WebsiteTextCheckbox websiteTextCheckbox = null;
    		while(iteratorCheck.hasNext()){ 
    			websiteTextCheckbox = iteratorCheck.next();
    			if(websiteTextCheckbox.isCheck() == true) {
    				check = "true";	    				
    			}    				
    			else
				check = "false";
    			service = Globals.runtimeList.servicesList.get(i);
    			service.setCheck(check);
    			Globals.runtimeList.servicesList.set(i, 
    					service);
    			i++;
            } 
    		try {
				SDCardReadWrite.writeServicesList(Constants.SERVICES_DIR,
						Globals.runtimeList.servicesList);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
       		ServiceFilterActivity.this.finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
 	          
}