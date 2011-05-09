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

package org.umit.imc.mobile.connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.umit.imc.mobile.R;
import android.content.Context;


public class WebsiteOpen {

	static public StringBuffer getContent(String str, Context context){
	InputStreamReader isr = null;

    BufferedReader in = null;
	
	 StringBuffer result = new StringBuffer();
	    try{
	        URL websiteUrl = new URL(str); 
	        	

	        isr  = new InputStreamReader(websiteUrl.openStream());

	        in = new BufferedReader(isr);

	        String inputLine;

	        while ((inputLine = in.readLine()) != null){
	            result.append(inputLine);
	        }
	    }catch(Exception ex){
	        result = new StringBuffer(context.getString(R.string.exception_timeout));
	        
	    }
	        try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				isr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
	}	
}
    

