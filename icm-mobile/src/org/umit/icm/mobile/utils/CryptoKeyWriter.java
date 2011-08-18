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

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import org.umit.icm.mobile.process.Constants;

/**
 * Helper class which writes Crypto keys to disk.
 */

public class CryptoKeyWriter {
	
	/**
	 * Writes the passed {@link PublicKey} to disk. Calls {@link KeyFactory},
	 * {@link RSAPublicKeySpec} and {@link RSACrypto}.
	 * 
	 *	 
	                          
	@param  publicKey  An object of the type {@link PublicKey}
	 *  	                          	
	                          
	@see         KeyFactory
	 *

	@see         RSAPublicKeySpec
	 *
	
	@see         RSACrypto
	 */
	public static void writeMyPublicKey(PublicKey publicKey) 
		throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec publicKeySpec 
		= keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
    	RSACrypto.saveKey(Constants.MY_PUBLIC_KEY_FILE
    			, publicKeySpec.getModulus()
    			, publicKeySpec.getPublicExponent());
	}
	
	/**
	 * Writes the passed {@link PrivateKey} to disk. Calls {@link KeyFactory},
	 * {@link RSAPrivateKeySpec} and {@link RSACrypto}.
	 * 
	 *	 
	                          
	@param  privateKey  An object of the type {@link PrivateKey}
	 *  	                          	
	                          
	@see         KeyFactory
	 *

	@see         RSAPrivateKeySpec
	 *
	
	@see         RSACrypto
	 */
	public static void writeMyPrivateKey(PrivateKey privateKey) 
	throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
	KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	RSAPrivateKeySpec privateKeySpec 
	= keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
	RSACrypto.saveKey(Constants.MY_PRIVATE_KEY_FILE
			, privateKeySpec.getModulus()
			, privateKeySpec.getPrivateExponent());
	}
	
	/**
	 * Writes the passed {@link PublicKey} to disk. Calls {@link AESCrypto}.
	 * 
	 *	 
	                          
	@param  secretKey  An object of the type byte[]
	 *  	                          	

	@see         AESCrypto
	 */
	public static void writeMySecretKey(byte[] secretKey) throws IOException {
		AESCrypto.saveKey(Constants.MY_SECRET_KEY_FILE, secretKey);
	}
	
	/**
	 * Writes the passed byte[] to disk. Calls {@link AESCrypto}.
	 * 
	 *	 
	                          
	@param  myCipheredKey  An object of the type byte[]
	 *  	                          	

	@see         AESCrypto
	 */
	public static void writeMyCipheredKey(byte[] myCipheredKey) throws IOException, RuntimeException {
		AESCrypto.saveKey(Constants.MY_CIPHERED_KEY_FILE, myCipheredKey);
	}
	
	/**
	 * Writes the passed byte[] to disk and appends the {@link String} peerIP
	 * to the filename. Calls {@link AESCrypto}.
	 * 
	 *	 
	                          
	@param  secretKey  An object of the type byte[]
	 *  
	  
	@param  peerIP  An object of the type {@link String}
	 *	                          	

	@see         AESCrypto
	 */
	public static void writePeerSecretKey(byte[] secretKey, String peerIP) throws IOException {
		AESCrypto.saveKey(peerIP+Constants.PEER_SECRET_KEY_FILE, secretKey);
	}
	
	/**
	 * Writes the passed {@link PublicKey} to disk. Calls {@link KeyFactory},
	 * {@link RSAPublicKeySpec} and {@link RSACrypto}.
	 * 
	 *	 
	                          
	@param  publicKey  An object of the type {@link PublicKey}
	 *  	                          	
	                          
	@see         KeyFactory
	 *

	@see         RSAPublicKeySpec
	 *
	
	@see         RSACrypto
	 */
	public static void writeAggregatorPublicKey(PublicKey publicKey) 
		throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec publicKeySpec 
		= keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
    	RSACrypto.saveKey(Constants.AGGR_PUBLIC_KEY_FILE
    			, publicKeySpec.getModulus()
    			, publicKeySpec.getPublicExponent());
	}
}