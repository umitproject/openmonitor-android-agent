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

package org.umit.icm.mobile.connectivity;

import java.io.IOException;

import org.umit.icm.mobile.utils.Constants;
import org.umit.icm.mobile.utils.SDCardReadWrite;

public class Website {
	private String url;
	private String status;
	private String check;			
	
	public Website() {
		super();
		url = "";
		status = "";
		check = "";
	}		
	
	public Website(String url, String status, String check) {
		super();
		this.url = url;
		this.status = status;
		this.check = check;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public void writeWebsite() throws IOException {
		Website website = new Website(this.url, this.status, this.check);
		SDCardReadWrite.writeWebsite(Constants.WEBSITES_DIR, website);
	}
	
	public Website readWebsite(String url) throws IOException {
		return SDCardReadWrite.readWebsite(Constants.WEBSITES_DIR, url);
	}
	
	public boolean equals(Website website) {
		if(website.getUrl().equals(this.getUrl())
				&& website.getStatus().equals(this.getStatus())
				&& website.getCheck().equals(this.getCheck()))
			return true;
		return false;
	}
}