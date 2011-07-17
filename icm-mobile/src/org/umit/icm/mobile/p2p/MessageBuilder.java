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

package org.umit.icm.mobile.p2p;

public class MessageBuilder {
	
	 public static byte[] intToByteArray(int integer) {
	        return new byte[] {
	                (byte)(integer >>> 24),
	                (byte)(integer >>> 16),
	                (byte)(integer >>> 8),
	                (byte)integer};
	 }
	 
	 public static byte[] byteArrayAppend(byte[] arrayA, byte[] arrayB) {
		 byte[] newArray = new byte[arrayA.length + arrayB.length];
		 int offset = 0;
		 for (int i = 0 ; i < arrayA.length ; i++) {
			 newArray[i] = arrayA[i];
			 offset++;
		 }		 
		 for (int i = 0 ; i < arrayB.length ; i++) {
			 newArray[offset] = arrayB[i];
			 offset++;
		 }
		 return newArray;
	 }
}