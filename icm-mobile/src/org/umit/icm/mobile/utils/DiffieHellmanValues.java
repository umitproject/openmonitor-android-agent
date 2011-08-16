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

package org.umit.icm.mobile.utils;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.spec.DHParameterSpec;

/**
 * Used to generate DiffieHellman key agreement values
 */

public class DiffieHellmanValues {
	
	/**
	 * Generates and returns {@link DHParameterSpec}.
	 *	 
	 *
	 
    @return {@link String}	                          
	 *
	 
	@see		AlgorithmParameters
	 *
	
	@see        AlgorithmParameterGenerator
	 */
	public static DHParameterSpec generateDiffieHellmanValues () 
	throws NoSuchAlgorithmException, InvalidParameterSpecException {
		 	        
        AlgorithmParameterGenerator parameterGenerator 
        = AlgorithmParameterGenerator.getInstance("DH");
        parameterGenerator.init(1024);
        
        AlgorithmParameters parameters 
        = parameterGenerator.generateParameters();
        return (DHParameterSpec)parameters.getParameterSpec(DHParameterSpec.class);		 
	}
}