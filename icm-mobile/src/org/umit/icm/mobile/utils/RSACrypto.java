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
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.proto.MessageProtos.RSAKey;

import android.os.Environment;

/**
 * Provides methods for RSA asymmetric cryptography
 */

public class RSACrypto {
	
	/**
	 * Returns an encrypted {@link String} of the Plain text passed. 
	 * Calls {@link RSACrypto#encryptPublic(PublicKey, byte[])}. Uses 
	 * {@link PublicKey} and {@link CryptoHelper#toHex(byte[])}.
	 *	 
	                          
	@param  plainText  An object of the type {@link String}
	 *  	                          	
	                          
	@param  publicKey  An object of the type {@link PublicKey}
	 *   	
	
    @return {@link String}	                          
	 *
	 
	@see         CryptoHelper
	 */
	public static String encryptPublic(PublicKey publicKey, String plainText) throws Exception {
		byte[] cipherText = encryptPublic(publicKey, plainText.getBytes());
		return CryptoHelper.toHex(cipherText);
	}
	
	/**
	 * Returns an decrypted {@link String} of the cipher text passed. 
	 * Calls {@link RSACrypto#decryptPrivate(PrivateKey, byte[])}. Uses 
	 * {@link PrivateKey} and {@link CryptoHelper#toByte(String)}.
	 *	 
	                          
	@param  cipherText  An object of the type {@link String}
	 *  	                          	
	                          
	@param  privateKey  An object of the type {@link PrivateKey}
	 *   	                          
	
	@return {@link String}	                          
	 *	 
	
	@see         CryptoHelper
	 */
	public static String decryptPrivate(PrivateKey privateKey, String cipherText) throws Exception {
		byte[] cipherTextBytes = CryptoHelper.toByte(cipherText);
		return new String(decryptPrivate(privateKey, cipherTextBytes));
	}
	
	/**
	 * Returns an encrypted {@link String} of the Plain text passed. 
	 * Calls {@link RSACrypto#encryptPrivate(PrivateKey, byte[])}. Uses 
	 * {@link PrivateKey} and {@link CryptoHelper#toHex(byte[])}.
	 *	 
	                          
	@param  plainText  An object of the type {@link String}
	 *  	                          	
	                          
	@param  privateKey  An object of the type {@link PrivateKey}
	 *   	                          
	
	@return {@link String}	                          
	 *
	 
	@see         CryptoHelper
	 */
	public static String encryptPrivate(PrivateKey privateKey, String plainText) throws Exception {
		byte[] cipherText = encryptPrivate(privateKey, plainText.getBytes());
		return CryptoHelper.toHex(cipherText);
	}
	
	/**
	 * Returns a decrypted {@link String} of the cipher text passed. 
	 * Calls {@link RSACrypto#decryptPublic(PublicKey, byte[])}. Uses 
	 * {@link PublicKey} and {@link CryptoHelper#toByte(String)}.
	 *	 
	                          
	@param  cipherText  An object of the type {@link String}
	 *  	                          	
	                          
	@param  publicKey  An object of the type {@link PublicKey}
	 *   	                          
	
	@return {@link String}	                          
	 *
	 
	@see         CryptoHelper
	 */
	public static String decryptPublic(PublicKey publicKey, String cipherText) throws Exception {
		byte[] cipherTextBytes = CryptoHelper.toByte(cipherText);
		return new String(decryptPublic(publicKey, cipherTextBytes));
	}
	
	/**
	 * Returns an RSA KeyPair generated using 
	 * {@link KeyPairGenerator#generateKeyPair()}. 
	 * 
	 *	 
	                          
	@return {@link KeyPair}	                          
	 *
	 	                          	
	@see         KeyPairGenerator
	 */
	public static KeyPair generateKey() throws Exception {
		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(Constants.RSA_KEY_SIZE);
		return keyPairGen.generateKeyPair();   
	}
	
	/**
	 * Returns an encrypted byte[] of the Plain text passed. 
	 * Uses{@link PublicKey} and {@link Cipher}.
	 *	 
	 *	                          		
	
	@param  plainBytes  An object of the type byte[]
	 *  	                          	
	                          
	@param  publicKey  An object of the type {@link PublicKey}
	 * 
	
    @return byte[]	                          
	 *
	 
	@see         Cipher
	 */
	public static byte[] encryptPublic(PublicKey publicKey, byte[] plainBytes) throws Exception {
	    Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
	    cipher.init(Cipher.ENCRYPT_MODE, (Key) publicKey);
	    return cipher.doFinal(plainBytes);
	}
	
	/**
	 * Returns an decrypted byte[] of the cipher bytes passed. 
	 * Uses {@link PrivateKey} and {@link Cipher}.
	 *	 
	                          
	@param  cipherBytes  An object of the type byte[]
	 *  	                          	
	                          
	@param  privateKey  An object of the type {@link PrivateKey}
	 *   	                          
	
	@return byte[]	                          
	 *	 
	
	@see         Cipher
	 */
	public static byte[] decryptPrivate(PrivateKey privateKey, byte[] cipherBytes) throws Exception {
	   	Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
	    cipher.init(Cipher.DECRYPT_MODE, privateKey);
	    return cipher.doFinal(cipherBytes);
	}
	
	/**
	 * Returns an encrypted byte[] of the Plain text passed. 
	 * Uses{@link PrivateKey} and {@link Cipher}.
	 *	 
	 *	                          		
	
	@param  plainBytes  An object of the type byte[]
	 *  	                          	
	                          
	@param  privateKey  An object of the type {@link PrivateKey}
	 * 
	
    @return byte[]	                          
	 *
	 
	@see         Cipher
	 */
	public static byte[] encryptPrivate(PrivateKey privateKey, byte[] plainBytes) throws Exception {
	    Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
	    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	    return cipher.doFinal(plainBytes);
	}
	
	/**
	 * Returns an decrypted byte[] of the cipher bytes passed. 
	 * Uses {@link PublicKey} and {@link Cipher}.
	 *	 
	                          
	@param  cipherBytes  An object of the type byte[]
	 *  	                          	
	                          
	@param  publicKey  An object of the type {@link PublicKey}
	 *   	                          
	
	@return byte[]	                          
	 *	 
	
	@see         Cipher
	 */
	public static byte[] decryptPublic(PublicKey publicKey, byte[] cipherBytes) throws Exception {
	   	Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
	    cipher.init(Cipher.DECRYPT_MODE, publicKey);
	    return cipher.doFinal(cipherBytes);
	}
	
	/**
	 * Writes an RSA key to disk.
	 *	 
	                          
	@param  fileName  An object of the type {@link String}
	 *  	                          	
	                          
	@param  modulus  An object of the type {@link BigInteger}
	 *   	                          
	 
	@param  exponential  An object of the type {@link BigInteger}
	 *   	                             

	@see         SDCardReadWrite
	 */
    public static void saveKey(String fileName, BigInteger modulus, BigInteger exponential) 
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
    			objOutStream.writeObject(modulus);
    			objOutStream.writeObject(exponential);    		
    	} finally {
    		objOutStream.close();
    	}
    	
    }
    
	/**
	 * Reads an RSA {@link PublicKey} from disk.
	 *	 
	                          
	@param  fileName  An object of the type {@link String}
	 *  	                          	
	
	@return  {@link PublicKey}
	 * 

	@see         SDCardReadWrite
	 */
    public static PublicKey readPublicKey(String fileName) throws IOException{
    	
    	File sdCard = Environment.getExternalStorageDirectory();
    	File keyDir = new File (sdCard.getAbsolutePath() 
    			+ Constants.KEYS_DIR);
    	File file = new File(keyDir, fileName);
    	InputStream inputStream = new FileInputStream(file.toString());
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
    
    /**
	 * Reads an RSA {@link PrivateKey} from disk.
	 *	 
	 *
	                          
	@param  fileName  An object of the type {@link String}
	 *  	                          	
	
	@return  {@link PrivateKey}
	 * 

	@see         SDCardReadWrite
	 */
    public static PrivateKey readPrivateKey(String fileName) throws IOException{
    	
    	File sdCard = Environment.getExternalStorageDirectory();
    	File keyDir = new File (sdCard.getAbsolutePath() 
    			+ Constants.KEYS_DIR);
    	File file = new File(keyDir, fileName);
    	InputStream inputStream = new FileInputStream(file.toString());
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
    
    /**
	 * Converts a {@link String} to {@link PublicKey}.
	 *	 
	                          
	@param  publicKeyString  An object of the type {@link String}
	 *  	                          	
	
	@return  {@link PublicKey}
	 * 

	@see         X509EncodedKeySpec
	 *
	 
	@see         KeyFactory
	 */    
    public static PublicKey stringToPublicKey(String publicKeyString) 
    	throws NoSuchAlgorithmException, InvalidKeySpecException {
    	X509EncodedKeySpec spec =
    	      new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString.getBytes()));
    	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    	    return keyFactory.generatePublic(spec);
    }
    
    /**
	 * Converts a {@link String} to {@link PrivateKey}.
	 *	 
	                          
	@param  privateKeyString  An object of the type {@link String}
	 *  	                          	
	
	@return  {@link PrivateKey}
	 * 

	@see         PKCS8EncodedKeySpec
	 *
	 
	@see         KeyFactory
	 */    
    public static PrivateKey stringToPrivateKey(String privateKeyString) 
	throws NoSuchAlgorithmException, InvalidKeySpecException {    	
    	PKCS8EncodedKeySpec spec =
    	      new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString.getBytes()));	
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    return keyFactory.generatePrivate(spec);
    }
    
    public static PrivateKey generatePrivateKey(BigInteger modulus, BigInteger exponential) throws NoSuchAlgorithmException, InvalidKeySpecException {    	
	    RSAPrivateKeySpec rsaKeySpec = new RSAPrivateKeySpec(modulus, exponential);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    return keyFactory.generatePrivate(rsaKeySpec);
    }
    
    public static PublicKey generatePublicKey(BigInteger modulus, BigInteger exponential) throws NoSuchAlgorithmException, InvalidKeySpecException {    	
    	RSAPublicKeySpec rsaKeySpec = new RSAPublicKeySpec(modulus, exponential);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    return keyFactory.generatePublic(rsaKeySpec);
    }
    
    public static RSAKey getPublicKeyIntegers(PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
    	KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec publicKeySpec 
		= keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
		RSAKey rsaKey = RSAKey.newBuilder()
		.setExp(publicKeySpec.getPublicExponent().toString())
		.setMod(publicKeySpec.getModulus().toString())
		.build();
		
    	return rsaKey;
    }

}