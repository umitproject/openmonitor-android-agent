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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class WebsiteTextCheckboxAdapter extends BaseAdapter {

    private Context context;
    private List<WebsiteTextCheckbox> listContent 
    = new ArrayList<WebsiteTextCheckbox>();

    public WebsiteTextCheckboxAdapter(Context context) {
         this.context = context;
    }
        
    public void addItem(WebsiteTextCheckbox websiteTextCheckbox) { 
    	listContent.add(websiteTextCheckbox); 
    }
    
    public void setListItems(List<WebsiteTextCheckbox> list) { 
    	listContent = list; 
    }

    @Override
    public int getCount() { 
    	return listContent.size(); 
    }

    @Override
    public Object getItem(int position) { 
    	return listContent.get(position); 
    }
        
    public void setChecked(boolean value, int position){
        listContent.get(position).setCheck(value);
    }               

    @Override
    public long getItemId(int position) {
         return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
         WebsiteCheckView websiteCheckView;
         if (convertView == null) {
        	 websiteCheckView 
        	 = new WebsiteCheckView(context, listContent.get(position));
         } else { 
        	 WebsiteTextCheckbox websiteTextCheckbox = listContent.get(position);
        	 websiteCheckView 
        	 = (WebsiteCheckView) convertView;
        	 websiteCheckView.setCheckBoxState(websiteTextCheckbox.isCheck()); 
        	 websiteCheckView = (WebsiteCheckView) convertView;
        	 websiteCheckView.setText(listContent.get(position).getText());
         }
         return websiteCheckView;
    }
}