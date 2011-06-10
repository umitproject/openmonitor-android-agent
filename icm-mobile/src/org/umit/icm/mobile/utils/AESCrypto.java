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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.os.Environment;

public class AESCrypto {


	public static String encrypt(String key, String plainText) throws Exception {
		byte[] generatedKey = generateKey(key.getBytes());
		byte[] cipherText = encrypt(generatedKey, plainText.getBytes());
		return CryptoHelper.toHex(cipherText);
	}
	
	public static String decrypt(String key, String cipherText) throws Exception {
		byte[] generatedKey = generateKey(key.getBytes());
		return new String(decrypt(generatedKey, CryptoHelper.toByte(cipherText)));
	}

	public static byte[] generateKey(byte[] key) throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		SecureRandom randomGen = SecureRandom.getInstance("SHA1PRNG");
		randomGen.setSeed(key);
	    keyGen.init(Constants.AES_KEY_SIZE, randomGen); 
	    SecretKey secretKey = keyGen.generateKey();
	    return secretKey.getEncoded();
	}

	public static byte[] encrypt(byte[] byteKey, byte[] plainBytes) throws Exception {
	    SecretKeySpec secretkeySpec = new SecretKeySpec(byteKey, "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, secretkeySpec);
	    return cipher.doFinal(plainBytes);		
	}

	public static byte[] decrypt(byte[] byteKey, byte[] cipherBytes) throws Exception {
	    SecretKeySpec secretkeySpec = new SecretKeySpec(byteKey, "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, secretkeySpec);
	    return cipher.doFinal(cipherBytes);
	}
	
	 public static void saveKey(String fileName, byte[] secretKey) 
	    throws IOException{
	    	
	    	ObjectOutputStream objOutStream = null;
	    	File sdCard = Environment.getExternalStorageDirectory();
	    	File keyDir = new File (sdCard.getAbsolutePath() 
	    			+ Constants.KEYS_DIR);
	    	keyDir.mkdirs();
	    	File file = new File(keyDir, fileName);
	    	try {
	    			objOutStream = new ObjectOutputStream(
	    				    new BufferedOutputStream(new FileOutputStream(file)));
	    			objOutStream.writeObject(secretKey);    		
	    	} catch (Exception e) {
    		    throw new RuntimeException("AES writeKey exception", e);
    	    } finally {
	    		objOutStream.close();
	    	}
	    	
	    }
	 
	 public static byte[] readKey(String fileName) throws IOException{
	    	
	    	File sdCard = Environment.getExternalStorageDirectory();
	    	File keyDir = new File (sdCard.getAbsolutePath() 
	    			+ Constants.KEYS_DIR);
	    	File file = new File(keyDir, fileName);
	    	InputStream inputStream = new FileInputStream(file.toString());
	    	  ObjectInputStream objInputStream =
	    	    new ObjectInputStream(new BufferedInputStream(inputStream));
	    	  try {
		    	    return (byte[]) objInputStream.readObject();
	    	  } catch (Exception e) {
	    		    throw new RuntimeException("AES readKey exception", e);
	    	  } finally {
	    		  objInputStream.close();
	    	  }
	    	    	
	    }
}