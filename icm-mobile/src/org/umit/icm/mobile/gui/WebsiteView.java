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
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WebsiteView extends LinearLayout {
       
        private TextView textView;
        private ImageView imageView;
       
        public WebsiteView(Context context, WebsiteTextBitmap websiteTextBitmap) {
                super(context);
 
                this.setOrientation(HORIZONTAL);
 
                imageView = new ImageView(context);
                Drawable drawable = websiteTextBitmap.getDrawable();
                drawable.setBounds(0, 0, 100, 100);
                imageView.setImageDrawable(drawable);
                imageView.setPadding(0, 0, 4, 0);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                     
                textView = new TextView(context);
                textView.setText(websiteTextBitmap.getText());
                textView.setTextSize(20);
                
                addView(imageView,  new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));                
                addView(textView, new LinearLayout.LayoutParams(
                                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
 
        public void setText(String text) {
                textView.setText(text);
        }
       
        public void setBitmap(Drawable favicon) {
                imageView.setImageDrawable(favicon);
        }
}