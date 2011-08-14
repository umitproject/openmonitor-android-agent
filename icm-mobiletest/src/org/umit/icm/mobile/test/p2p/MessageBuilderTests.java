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

package org.umit.icm.mobile.test.p2p;

import junit.framework.Assert;

import org.umit.icm.mobile.p2p.MessageBuilder;

import android.test.AndroidTestCase;

public class MessageBuilderTests extends AndroidTestCase {
	
	public void testByteArrayConversion() throws Throwable {
		int integer = 35;
		byte[] byteArray = MessageBuilder.intToByteArray(integer);
		Assert.assertEquals(integer, MessageBuilder.byteArrayToInt(byteArray));
	}
	
	public void testByteArrayAppend() throws Throwable {
		byte[] arrayA = new byte[2];
		byte[] arrayB = new byte[2];
		byte[] arrayC = new byte[4];
		
		arrayA[0] = (byte) 1;
		arrayA[1] = (byte) 2;
		arrayB[0] = (byte) 3;
		arrayB[1] = (byte) 4;
		
		arrayC[0] = (byte) 1;
		arrayC[1] = (byte) 2;
		arrayC[2] = (byte) 3;
		arrayC[3] = (byte) 4;

		Assert.assertEquals(arrayC.length, arrayA.length + arrayB.length);
		Assert.assertTrue(byteArrayEquals(arrayC, MessageBuilder.byteArrayAppend(arrayA, arrayB)));
	}
	
	public void testByteArrayAppendThree() throws Throwable {
		byte[] arrayA = new byte[2];
		byte[] arrayB = new byte[2];
		byte[] arrayC = new byte[2];
		byte[] arrayD = new byte[6];
		
		arrayA[0] = (byte) 1;
		arrayA[1] = (byte) 2;
		arrayB[0] = (byte) 3;
		arrayB[1] = (byte) 4;
		arrayC[0] = (byte) 5;
		arrayC[1] = (byte) 6;
		
		arrayD[0] = (byte) 1;
		arrayD[1] = (byte) 2;
		arrayD[2] = (byte) 3;
		arrayD[3] = (byte) 4;
		arrayD[4] = (byte) 5;
		arrayD[5] = (byte) 6;

		Assert.assertEquals(arrayD.length, arrayA.length + arrayB.length + arrayC.length);
		Assert.assertTrue(byteArrayEquals(arrayD, MessageBuilder.byteArrayAppendThree(arrayA, arrayB, arrayC)));
	}
	
	public void testGenerateMessage() throws Throwable {
		int id = 10;
		
		byte[] arrayD = new byte[6];
		arrayD[0] = (byte) 1;
		arrayD[1] = (byte) 2;
		arrayD[2] = (byte) 3;
		arrayD[3] = (byte) 4;
		arrayD[4] = (byte) 5;
		arrayD[5] = (byte) 6;
		
		byte[] message = MessageBuilder.generateMessage(id, arrayD);
		
		byte[] byteID = MessageBuilder.intToByteArray(id);
		byte[] bytelength = MessageBuilder.intToByteArray(10);
		
		byte[] message2 = MessageBuilder.byteArrayAppendThree(bytelength, byteID, arrayD);
		Assert.assertEquals(message.length, arrayD.length + 8);		
		Assert.assertTrue(byteArrayEquals(message, message2));
	}
	
	public void testGetSubArray() throws Throwable {		
		
		byte[] arrayD = new byte[6];
		arrayD[0] = (byte) 1;
		arrayD[1] = (byte) 2;
		arrayD[2] = (byte) 3;
		arrayD[3] = (byte) 4;
		arrayD[4] = (byte) 5;
		arrayD[5] = (byte) 6;
		
		byte[] subArrayD = new byte[4];
		subArrayD[0] = (byte) 3;
		subArrayD[1] = (byte) 4;
		subArrayD[2] = (byte) 5;
		subArrayD[3] = (byte) 6;
		
		byte[] newSubArray = MessageBuilder.getSubArray(arrayD, 2, 5);
										
		Assert.assertTrue(byteArrayEquals(subArrayD, newSubArray));
	}
	
	public void testGenerateMessageWithoutLength() throws Throwable {
		int id = 10;
		
		byte[] arrayD = new byte[6];
		arrayD[0] = (byte) 1;
		arrayD[1] = (byte) 2;
		arrayD[2] = (byte) 3;
		arrayD[3] = (byte) 4;
		arrayD[4] = (byte) 5;
		arrayD[5] = (byte) 6;
		
		byte[] message = MessageBuilder.generateMessage(id, arrayD);
		
		byte[] byteLength = MessageBuilder.generateMessageLength(id, arrayD);				
		byte[] message2 = MessageBuilder.byteArrayAppend(byteLength, 
				MessageBuilder.generateMessageWithoutLength(id, arrayD));
		Assert.assertEquals(message.length, message2.length);		
		Assert.assertTrue(byteArrayEquals(message, message2));
	}
		
	private boolean byteArrayEquals(byte[] arrayA, byte[] arrayB) {
		if(arrayA.length != arrayB.length)
			return false;
		for(int i = 0; i < arrayA.length ; i++) {
			if(arrayA[i] != arrayB[i])
				return false;
		}
		return true;					
	}
}