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

import java.net.URL;

public class WebsiteClass {
		
	private URL websiteURL;
	private String websiteMetaData;
	private boolean websiteStatus;
		
	public WebsiteClass() {
		super();
		
	}
	
			
	public URL getWebsiteURL() {
		return websiteURL;
	}


	public void setWebsiteURL(URL websiteURL) {
		this.websiteURL = websiteURL;
	}


	public String getWebsiteMetaData() {
		return websiteMetaData;
	}


	public void setWebsiteMetaData(String websiteMetaData) {
		this.websiteMetaData = websiteMetaData;
	}


	public boolean isWebsiteStatus() {
		return websiteStatus;
	}


	public void setWebsiteStatus(boolean websiteStatus) {
		this.websiteStatus = websiteStatus;
	}


	public WebsiteClass(URL websiteURL, String websiteMetaData,
			boolean websiteStatus) {
		super();
		this.websiteURL = websiteURL;
		this.websiteMetaData = websiteMetaData;
		this.websiteStatus = websiteStatus;
	}

	
	
}
