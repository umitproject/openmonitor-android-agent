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
import java.util.ArrayList;
import java.util.List;

import org.umit.icm.mobile.utils.Constants;
import org.umit.icm.mobile.utils.SDCardReadWrite;

public class Website {
	private String url;
	private String content;
	private List<String> header;
	
	public Website(String url, String content, List<String> header) {
		super();
		this.url = url;
		this.content = content;
		this.header = header;
	}
	
	public Website() {
		super();
		this.url = new String();
		this.content = new String();
		this.header = new ArrayList<String>();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}
	
	public void writeWebsite() throws IOException {
		Website website = new Website(this.url, this.content, this.header);
		SDCardReadWrite.writeWebsite(Constants.WEBSITES_DIR, website);
	}
	
	public Website readWebsite(String url) throws IOException {
		return SDCardReadWrite.readWebsite(Constants.WEBSITES_DIR, url);
	}
}