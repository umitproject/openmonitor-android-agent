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

import org.apache.http.HttpException;
import org.umit.icm.mobile.aggregator.AggregatorActions;
import org.umit.icm.mobile.aggregator.AggregatorResources;

/**
 * This is the AbstractConnectivity Class. Provides base constructor and abstract
 * implementations of clean and scan methods.
 */

public class AbstractConnectivity {
	
	/**
	 * This is the default base constructor.
	 */
	public AbstractConnectivity() {
		super();		
	}
	
	/**
	 * Cleans the scanned data and returns a corresponding message.
	 *	 
	                                                
	@return      Object
	 */
	public Object clean() throws IOException {
		return true;
	}
	
	/**
	 * Performs the connectivity test.
	 */
	public void scan() throws IOException, HttpException, RuntimeException {
		
	}
	
}