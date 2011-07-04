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
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.os.Environment;

/**
 * Provides methods for AES symmetric cryptography
 */

public class AESCrypto {
	
	/**
	 * Returns an encrypted {@link String} of the Plain text passed. 
	 * Calls {@link AESCrypto#encrypt(byte[], byte[])}. Uses 
	 * {@link CryptoHelper#toHex(byte[])}.
	 *	 
	                          
	@param  plainText  An object of the type {@link String}
	 *  	                          	
	                          
	@param  key  An object of the type {@link String}
	 *   	
	
    @return {@link String}	                          
	 *
	 
	@see         CryptoHelper
	 */
	public static String encrypt(String key, String plainText) throws Exception {
		byte[] generatedKey = generateKey(key.getBytes());
		byte[] cipherText = encrypt(generatedKey, plainText.getBytes());
		return CryptoHelper.toHex(cipherText);
	}
	
	/**
	 * Returns an decrypted {@link String} of the cipher text passed. 
	 * Calls {@link AESCrypto#decrypt(byte[], byte[])}. Uses 
	 * {@link CryptoHelper#toByte(String)}.
	 *	 
	                          
	@param  cipherText  An object of the type {@link String}
	 *  	                          	
	                          
	@param  key  An object of the type {@link String}
	 *   	                          
	
	@return {@link String}	                          
	 *	 
	
	@see         CryptoHelper
	 */
	public static String decrypt(String key, String cipherText) throws Exception {
		byte[] generatedKey = generateKey(key.getBytes());
		return new String(decrypt(generatedKey, CryptoHelper.toByte(cipherText)));
	}
	
	/**
	 * Returns an AES secret key using the passed byte[] as seed. 
	 *	 
	 *
	                          
	@param  key  An object of the type byte[]
	 *  	                          	 	                          
	
	@return byte[]                       
	 *	 
	
	@see         SecureRandom
	 *
	 
	@see         KeyGenerator
	 */
	public static byte[] generateKey(byte[] key) throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		SecureRandom randomGen = SecureRandom.getInstance("SHA1PRNG");
		randomGen.setSeed(key);
	    keyGen.init(Constants.AES_KEY_SIZE, randomGen); 
	    SecretKey secretKey = keyGen.generateKey();
	    return secretKey.getEncoded();
	}
	
	/**
	 * Returns an encrypted byte[] of the Plain bytes passed. 
	 * Encrypts using {@link Cipher}.
	 *
	 *	 
	                          
	@param  plainBytes  An object of the type byte[]
	 *  	                          	
	                          
	@param  byteKey  An object of the type byte[]
	 *   	
	
    @return byte[]                        
	 *
	 
	@see         Cipher
	 */
	public static byte[] encrypt(byte[] byteKey, byte[] plainBytes) throws Exception {
	    SecretKeySpec secretkeySpec = new SecretKeySpec(byteKey, "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, secretkeySpec);
	    return cipher.doFinal(plainBytes);		
	}

	/**
	 * Returns an decrypted byte[] of the cipher bytes passed. 
	 * Uses {@link Cipher}.
	 *	 
	                          
	@param  cipherBytes  An object of the type byte[]
	 *  	                          	
	                          
	@param  byteKey  An object of the type byte[]
	 *   	                          
	
	@return byte[]	                          
	 *	 
	
	@see         Cipher
	 */
	public static byte[] decrypt(byte[] byteKey, byte[] cipherBytes) throws Exception {
	    SecretKeySpec secretkeySpec = new SecretKeySpec(byteKey, "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, secretkeySpec);
	    return cipher.doFinal(cipherBytes);
	}
	
	/**
	 * Writes an AES key to disk.
	 *	 
	                          
	@param  fileName  An object of the type {@link String}
	 *  	                          	
	                          
	@param  secretKey  An object of the type byte[]
	 *   	                          

	@see         SDCardReadWrite
	 */
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
	 
		/**
		 * Reads an AES key from disk.
		 *	 
		                          
		@param  fileName  An object of the type {@link String}
		 *  	                          	
		
		@return  byte[]
		 * 

		@see         SDCardReadWrite
		 */
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