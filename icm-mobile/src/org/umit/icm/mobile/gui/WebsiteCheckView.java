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

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WebsiteCheckView extends LinearLayout {
     
     private TextView text;
     private CheckBox checkBox;
     private WebsiteTextCheckbox websiteTextCheckbox;
     
     public WebsiteCheckView(Context context, WebsiteTextCheckbox websiteTextCheckbox) {
          super(context);          

          this.setOrientation(HORIZONTAL);
          this.websiteTextCheckbox = websiteTextCheckbox;
          checkBox = new CheckBox(context);
          checkBox.setPadding(0, 0, 4, 0);                    
          checkBox.setChecked(websiteTextCheckbox.isCheck());
          text = new TextView(context);
          text.setText(websiteTextCheckbox.getText());
          text.setTextSize(20);                    
          
          addView(checkBox,  new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));                    
          addView(text, new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));          
          
          text.setOnClickListener( new OnClickListener()
          {
                @Override
				public void onClick(View v) {
					if (checkBox.isChecked())
                   	 setCheckBoxState(false);
                    else
                   	 setCheckBoxState(true);
							
				}
  
          });
          
          checkBox.setOnClickListener( new OnClickListener()
          {
                @Override
				public void onClick(View v) {
                	setCheckBoxState(checkBox.isChecked());
							
				}
  
          });
     }

     public void setText(String website) {
          text.setText(website);
     }
     public void setCheckBoxState(boolean check)
     {
    	 checkBox.setChecked(check);
    	 websiteTextCheckbox.setCheck(check);
     }
}