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
import java.security.PrivateKey;
import java.security.PublicKey;

import org.umit.icm.mobile.process.Constants;

/**
 * Helper class which reads Crypto keys from disk.
 */

public class CryptoKeyReader {
	
	/**
	 * Returns {@link PublicKey} read from disk using 
	 * {@link RSACrypto#readPublicKey(String)}.
	 * 
	 *
	
	@return PublicKey
     *		

	@see         PublicKey
	 *
	
	@see         RSACrypto
	 */
	public static PublicKey getMyPublicKey() throws IOException {
		return RSACrypto.readPublicKey(Constants.MY_PUBLIC_KEY_FILE);
	}
	
	/**
	 * Returns {@link PrivateKey} read from disk using 
	 * {@link RSACrypto#readPrivateKey(String)}.
	 * 
	 *
	
	@return PrivateKey
     *		

	@see         PrivateKey
	 *
	
	@see         RSACrypto
	 */
	public static PrivateKey getMyPrivateKey() throws IOException {
		return RSACrypto.readPrivateKey(Constants.MY_PRIVATE_KEY_FILE);
	}
	
	/**
	 * Returns secret key byte[] read from disk using 
	 * {@link AESCrypto#readKey(String)}.
	 * 
	 *
	
	@return byte[]
     *		
	
	@see         AESCrypto
	 */
	public static byte[] getMySecretKey() throws IOException {
		return AESCrypto.readKey(Constants.MY_SECRET_KEY_FILE);
	}
	
	public static String getMyCipheredKeyMod() throws IOException {
		return SDCardReadWrite.readString(Constants.MY_CIPHERED_KEY_FILE_MOD, 
				Constants.KEYS_DIR);
	}
	
	public static String getMyCipheredKeyExp() throws IOException {
		return SDCardReadWrite.readString(Constants.MY_CIPHERED_KEY_FILE_EXP, 
				Constants.KEYS_DIR);
	}
			
	/**
	 * Returns secret key byte[] read from disk using 
	 * {@link AESCrypto#readKey(String)} and filename appended with {@link String}
	 * peerIP.
	 * 
	 *
	
	@return byte[]
     *		
	
	@see         AESCrypto
	 */
	public static byte[] getPeerSecretKey(String peerIP) throws IOException {
		return AESCrypto.readKey(peerIP+Constants.PEER_SECRET_KEY_FILE);
	}
	
	/**
	 * Checks if peer key exists.
	 * 
	 *
	
	@return boolean
     *		
	
	@see         AESCrypto
	 */
	public static boolean checkPeerSecretKey(String peerIP) throws IOException {
		return SDCardReadWrite.fileExists(peerIP+Constants.PEER_SECRET_KEY_FILE,
				Constants.KEYS_DIR);
	}
	
	/**
	 * Returns {@link PublicKey} read from disk using 
	 * {@link RSACrypto#readPublicKey(String)}.
	 * 
	 *
	
	@return PublicKey
     *		

	@see         PublicKey
	 *
	
	@see         RSACrypto
	 */
	public static PublicKey getAggregatorPublicKey() throws IOException {
		return RSACrypto.readPublicKey(Constants.AGGR_PUBLIC_KEY_FILE);
	}
}