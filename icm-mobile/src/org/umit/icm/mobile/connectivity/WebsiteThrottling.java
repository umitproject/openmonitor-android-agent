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

package org.umit.icm.mobile.connectivity;

import java.net.URLConnection;


public class WebsiteThrottling {
	
	public static long TimeTakenToDownload(String website) {
		
		long timeTaken = 0;
		try {
			URLConnection urlConnection = WebsiteOpen.openURLConnection(website);
			long startTime = System.currentTimeMillis();
			WebsiteOpen.getContent(urlConnection);
			long endTime = System.currentTimeMillis();
			timeTaken = endTime - startTime;
		} catch(Exception e){
			e.printStackTrace();
		}
		return timeTaken;
	}
	
	public static double Throughput(String website){
	
		double throughput = 0;
		try{
			URLConnection urlConnection = WebsiteOpen.openURLConnection(website);
			String content = WebsiteOpen.getContent(urlConnection);
			long size = content.getBytes().length;
			long time = WebsiteThrottling.TimeTakenToDownload(website);		
			throughput = size/time;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return throughput;
	}	

}
