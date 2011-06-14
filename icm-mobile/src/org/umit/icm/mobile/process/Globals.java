/**
 * Copyright (C) 2011 Adriano Monteiro Marques
 *
 * Author:  Zubair Nabi <zn.zubairnabi@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either Test 2 of the License, or
 * (at your option) any later Test.
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

package org.umit.icm.mobile.process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.umit.icm.mobile.connectivity.Website;
import org.umit.icm.mobile.connectivity.WebsiteConnectivity;
import org.umit.icm.mobile.gui.WebsiteTextCheckbox;
import org.umit.icm.mobile.proto.MessageProtos.RequestHeader;
import org.umit.icm.mobile.utils.Constants;


public class Globals {
	public static RuntimeParameters runtimeParameters 
	= new RuntimeParameters();
	public static VersionManager versionManager 
	= new VersionManager();
	public static WebsiteConnectivity websiteTest 
	= new WebsiteConnectivity();
	/*public static RequestHeader requestHeader
	= RequestHeader.newBuilder()
	.setAgentID(1)
	.setToken("1")
	.build();*/
	public static String scanStatus = " ";
	public static List<Website> websitesList = new ArrayList<Website>();
	
	public static void intialize() {
		Iterator<String> iterator = Constants.WEBSITE_LIST.iterator();
		while(iterator.hasNext()){               
			Globals.websitesList.add(new Website(iterator.next(), "false", "false"));						       			
        }  
		
	}
}