/**
 * Copyright (C) 2011 Adriano Monteiro Marques
 *
 * Author:  Gautam Bajaj <gautam1237@gmail.com>
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

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;

public class AggregatorCrypto {
	
	
	public static String aesEncrypt(byte[] data){
		byte[] encryptedData = null;
		try {
			encryptedData = AESCrypto.encrypt(Globals.keyManager.getAESKey(), data);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return AggregatorCrypto.encodeData(encryptedData);	
	}
	
	public static byte[] aesDecrypt(byte[] data){
		byte[] decodedData = null;
		if(Constants.DEBUG_MODE)
			System.out.println("-----------_______________---------------- Got this data: \n" + new String(data));	
		decodedData = Base64.decodeBase64(data);	
		if(Constants.DEBUG_MODE)
			System.out.println("-----------_______________---------------- Got this decoded_data: \n" + new String(decodedData));		
		byte[] decryptedDecodedData = null;
		try {
			decryptedDecodedData = AESCrypto.decrypt(Globals.keyManager.getAESKey(), decodedData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		if(Constants.DEBUG_MODE)
			System.out.println("-----------_______________---------------- Got this decrypted_decoded_data: " + new String(decryptedDecodedData));
		return decryptedDecodedData;
	}
	
	public static String rsaAggregatorPublicKeyEncypt(byte[] data){
		byte[] encryptedData = null;
		try {
			encryptedData =  RSACrypto.encryptPublic(Globals.keyManager.getAggregatorPublicKey(), data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return AggregatorCrypto.encodeData(encryptedData);
	}
	
	public static String encodeData(byte[] data){
		return new String(Base64.encodeBase64(data));
	}
}
