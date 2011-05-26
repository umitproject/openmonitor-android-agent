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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.restlet.engine.http.connector.ServerConnection;

import android.os.Environment;

public class RSACrypto {
	
	private static int keySize = 1024;

	public static String encryptPublic(PublicKey publicKey, String plainText) throws Exception {
		byte[] cipherText = encryptPublic(publicKey, plainText.getBytes());
		return toHex(cipherText);
	}
	
	public static String decryptPrivate(PrivateKey privateKey, String cipherText) throws Exception {
		byte[] cipherTextBytes = toByte(cipherText);
		return new String(decryptPrivate(privateKey, cipherTextBytes));
	}
	
	public static String encryptPrivate(PrivateKey privateKey, String plainText) throws Exception {
		byte[] cipherText = encryptPrivate(privateKey, plainText.getBytes());
		return toHex(cipherText);
	}
	
	public static String decryptPublic(PublicKey publicKey, String cipherText) throws Exception {
		byte[] cipherTextBytes = toByte(cipherText);
		return new String(decryptPublic(publicKey, cipherTextBytes));
	}

	public static KeyPair generateKey() throws Exception {
		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(keySize);
		return keyPairGen.generateKeyPair();   
	}

	private static byte[] encryptPublic(PublicKey publicKey, byte[] plainBytes) throws Exception {
	    Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	    return cipher.doFinal(plainBytes);
	}

	private static byte[] decryptPrivate(PrivateKey privateKey, byte[] cipherBytes) throws Exception {
	   	Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.DECRYPT_MODE, privateKey);
	    return cipher.doFinal(cipherBytes);
	}
	
	private static byte[] encryptPrivate(PrivateKey privateKey, byte[] plainBytes) throws Exception {
	    Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	    return cipher.doFinal(plainBytes);
	}
	
	private static byte[] decryptPublic(PublicKey publicKey, byte[] cipherBytes) throws Exception {
	   	Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.DECRYPT_MODE, publicKey);
	    return cipher.doFinal(cipherBytes);
	}
	
	private static String toHex (byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;

        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10)
            strbuf.append("0");

            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }

        return strbuf.toString();
     }

    private static byte[] toByte (String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }

        return bts;
    }
    
    public static void saveKey(String fileName, BigInteger modulus, BigInteger exponential) 
    throws IOException{
    	
    	ObjectOutputStream objOutStream = null;
    	File sdCard = Environment.getExternalStorageDirectory();
    	//File keyDir = new File (sdCard.getAbsolutePath() + "/keys");
    	//keyDir.mkdirs();
    	//File file = new File(keyDir, fileName);
    	File file = new File(sdCard, fileName);
    	try {
    			objOutStream = new ObjectOutputStream(
    				    new BufferedOutputStream(new FileOutputStream(file)));
    			objOutStream.writeObject(modulus);
    			objOutStream.writeObject(exponential);    		
    	} finally {
    		objOutStream.close();
    	}
    	
    }
    
    public static PublicKey readPublicKey(String fileName) throws IOException{
    	
    	File sdCard = Environment.getExternalStorageDirectory();
    	//File keyDir = new File (sdCard.getAbsolutePath() + "/keys");
    	//File file = new File(keyDir, fileName);
    	File file = new File(sdCard, fileName);
    	InputStream inputStream = ServerConnection.class.getResourceAsStream(file.toString());
    	  ObjectInputStream objInputStream =
    	    new ObjectInputStream(new BufferedInputStream(inputStream));
    	  try {
	    	    BigInteger modulus = (BigInteger) objInputStream.readObject();
	    	    BigInteger exponential = (BigInteger) objInputStream.readObject();
	    	    RSAPublicKeySpec rsaKeySpec = new RSAPublicKeySpec(modulus, exponential);
	    	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    	    return keyFactory.generatePublic(rsaKeySpec);
    	  } catch (Exception e) {
    		    throw new RuntimeException("readPublicKey exception", e);
    	  } finally {
    		  objInputStream.close();
    	  }
    	    	
    }
    
    public static PrivateKey readPrivateKey(String fileName) throws IOException{
    	
    	File sdCard = Environment.getExternalStorageDirectory();
    	File keyDir = new File (sdCard.getAbsolutePath() + "/keys");
    	File file = new File(keyDir, fileName);
    	InputStream inputStream = ServerConnection.class.getResourceAsStream(file.toString());
    	  ObjectInputStream objInputStream =
    	    new ObjectInputStream(new BufferedInputStream(inputStream));
    	  try {
	    	    BigInteger modulus = (BigInteger) objInputStream.readObject();
	    	    BigInteger exponential = (BigInteger) objInputStream.readObject();
	    	    RSAPrivateKeySpec rsaKeySpec = new RSAPrivateKeySpec(modulus, exponential);
	    	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    	    return keyFactory.generatePrivate(rsaKeySpec);
    	  } catch (Exception e) {
    		    throw new RuntimeException("readPrivateKey exception", e);
    	  } finally {
    		  objInputStream.close();
    	  }
    	    	
    }


}