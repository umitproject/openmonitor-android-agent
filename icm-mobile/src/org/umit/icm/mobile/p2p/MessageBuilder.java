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

import android.util.Log;

/**
 * Holds helper methods to generate a byte[] P2P message.
 */

public class MessageBuilder {
	
	/**
	 * Returns the byte[] equivalent of an int
	 *
	 *
	 
	 @param integer Parameter of type int
	 *
	 
	 @return byte[] 
	 */
	public static byte[] intToByteArray(int integer) {
	        return new byte[] {
	                (byte)(integer >>> 24),
	                (byte)(integer >>> 16),
	                (byte)(integer >>> 8),
	                (byte)integer
	                };
	 }
	 
	/**
	 * Returns the int equivalent of a byte[]
	 *
	 *
	 
	 @param byteArray Parameter of type byte[]
	 *
	 
	 @return int 
	 */
	 public static int byteArrayToInt(byte[] byteArray) {
	        return    ((byteArray[0] << 24)
	                + ((byteArray[1] & 0xFF) << 16)
	                + ((byteArray[2] & 0xFF) << 8)
	                + (byteArray[3] & 0xFF)
	                );
	}
	 
	/**
	 * Returns a byte[] which is a concatenation of the input byte arrays.
	 *
	 *
	 
	 @param arrayA Parameter of type byte[]
	 *
	 
	 @param arrayB Parameter of type byte[]
	 *
	 
	 @return byte[] 
	 */
	 public static byte[] byteArrayAppend(byte[] arrayA, byte[] arrayB) {
		 byte[] newArray = new byte[arrayA.length + arrayB.length];		 		 		 
		 System.arraycopy(arrayA, 0, newArray, 0, arrayA.length);
		 System.arraycopy(arrayB, 0, newArray, arrayA.length, arrayB.length);		
		 return newArray;
	 }
	 
	 /**
		 * Returns a byte[] which is a concatenation of the three input byte arrays.
		 *
		 *
		 
		 @param arrayA Parameter of type byte[]
		 *
		 
		 @param arrayB Parameter of type byte[]
		 *
		 
		 @param arrayC Parameter of type byte[]
		 *
		 		 
		 @return byte[] 
		 */
		 public static byte[] byteArrayAppendThree(byte[] arrayA, byte[] arrayB, byte[] arrayC) {
			 byte[] newArray = new byte[arrayA.length + arrayB.length + arrayC.length];		 		 		 
			 System.arraycopy(arrayA, 0, newArray, 0, arrayA.length);
			 System.arraycopy(arrayB, 0, newArray, arrayA.length, arrayB.length);
			 System.arraycopy(arrayC, 0, newArray, arrayA.length + arrayB.length, arrayC.length);
			 return newArray;
		 }
	 
	 public static byte[] generateMessage(int id, byte[] message) {
		 byte[] idByte = intToByteArray(id);
		 int length = idByte.length + message.length;
		 byte[] lengthByte = intToByteArray(length);
		 Log.w("###length", Integer.toString(byteArrayToInt(lengthByte)));
		 Log.w("###id", Integer.toString(byteArrayToInt(idByte)));
		 return byteArrayAppendThree(lengthByte, idByte, message);
		 
	 }
}