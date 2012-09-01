/**
 * Copyright (C) 2011 Adriano Monteiro Marques
 *
 * Author:  Gautam Bajaj <gautam1237@gmail.com>
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

package org.umit.icm.mobile.debug;

import android.app.Activity;
import android.widget.Toast;

public class Show {
	
	
	public static void Error(final Activity activity,final String msg){
		
		System.err.println(msg);
		activity.runOnUiThread(new Runnable() {
		    public void run() {
		        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
		    }
		});
		
	}
	public static void Info(final Activity activity,final String msg){
		
		System.out.println(msg);
		activity.runOnUiThread(new Runnable() {
		    public void run() {
		        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
		    }
		});
		
	}
	

}
