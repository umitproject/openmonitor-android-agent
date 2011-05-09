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

import org.umit.imc.mobile.connectivity.WebsiteOpen;

import android.os.Handler;
import android.view.View;

public class WebsiteTest extends AbstractTest{
	
	private Handler scanHandler;
	private WebsiteClass websiteResult;

	
	@Override
	public void scan(final View view) {
		
		scanHandler = new Handler();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				
					final String metaData = "Hello";
					String websiteURL = "http://www.google.com/";
					final String websiteReturn = WebsiteOpen.getContent(websiteURL, view.getContext()).toString();
					final int status = 0;
										
					scanHandler.post(new Runnable() {
						@Override
						public void run() {
							websiteResult.setWebsiteContent(websiteReturn);
							websiteResult.setWebsiteMetaData(metaData);
							websiteResult.setWebsiteStatus(status);
						}
					});
			};
		};
		
		new Thread(runnable).start();
	}
		
}

