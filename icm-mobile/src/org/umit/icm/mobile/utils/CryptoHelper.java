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


/**
 * Helper class with methods to convert byte[] to {@link String} hex and 
 * vice versa.
 */
public class CryptoHelper {
	
	/**
	 * Returns a Hex {@link String} of the passed byte[].
	 * 
	 *	 
	                          
	@param  buf  An object of the type byte[]
	 * 
	
	@return {@link String}	                          	
	 *
	 
	@see         StringBuffer
	 */
	public static String toHex (byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;

        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10)
            strbuf.append("0");

            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }

        return strbuf.toString();
     }
	
	/**
	 * Returns byte[] of the passed {@link String}.
	 * 
	 *	 
	                          
	@param  hex  An object of the type {@link String}
	 * 
	
	@return byte[]                          	
	 */
    public static byte[] toByte (String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }

        return bts;
    }
}