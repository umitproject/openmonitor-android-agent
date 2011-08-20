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

package org.umit.icm.mobile.process;

/**
 * Manages the SecretKey, PrivateKey, PublicKey and CipheredKey.
 */

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;


import org.umit.icm.mobile.utils.CryptoKeyWriter;

public class KeyManager {
	private byte[] mySecretKey;
	private PrivateKey myPrivateKey;
	private PublicKey myPublicKey;
	private String myCipheredKeyMod;
	private String myCipheredKeyExp;
	
	public KeyManager(byte[] mySecretKey, PrivateKey myPrivateKey,
			PublicKey myPublicKey, String myCipheredKeyMod,
			String myCipheredKeyExp) {
		super();
		this.mySecretKey = mySecretKey;
		this.myPrivateKey = myPrivateKey;
		this.myPublicKey = myPublicKey;
		this.myCipheredKeyMod = myCipheredKeyMod;
		this.myCipheredKeyExp = myCipheredKeyExp;
	}

	public KeyManager() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public synchronized byte[] getMySecretKey() {
		return mySecretKey;
	}
	
	/**
	 * Writes the SecretKey to disk.
	 *
	 *
	 
	 @param mySecretKey SecretKey of type byte[]
	 *
	 
	 @see CryptoKeyWriter
	 */
	public synchronized void setMySecretKey(byte[] mySecretKey) throws IOException {
		this.mySecretKey = mySecretKey;
		CryptoKeyWriter.writeMySecretKey(mySecretKey);
	}

	public synchronized PrivateKey getMyPrivateKey() {
		return myPrivateKey;
	}
	
	/**
	 * Writes the PrivateKey to disk.
	 *
	 *
	 
	 @param myPrivateKey PrivateKey of type {@link PrivateKey}
	 *
	 
	 @see CryptoKeyWriter
	 */
	public synchronized void setMyPrivateKey(PrivateKey myPrivateKey) {		
		this.myPrivateKey = myPrivateKey;
		try {
			CryptoKeyWriter.writeMyPrivateKey(this.myPrivateKey);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized PublicKey getMyPublicKey() {
		return myPublicKey;
	}
	
	/**
	 * Writes the PublicKey to disk.
	 *
	 *
	 
	 @param myPublicKey PublicKey of type {@link PublicKey}
	 *
	 
	 @see CryptoKeyWriter
	 */
	public synchronized void setMyPublicKey(PublicKey myPublicKey) {
		this.myPublicKey = myPublicKey;
		try {
			CryptoKeyWriter.writeMyPublicKey(this.myPublicKey);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMyCipheredKeyMod() {
		return myCipheredKeyMod;
	}

	public void setMyCipheredKeyMod(String myCipheredKeyMod) {
		this.myCipheredKeyMod = myCipheredKeyMod;
		try {
			CryptoKeyWriter.writeMyCipheredKeyMod(myCipheredKeyMod);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMyCipheredKeyExp() {
		return myCipheredKeyExp;
	}

	public void setMyCipheredKeyExp(String myCipheredKeyExp) {
		this.myCipheredKeyExp = myCipheredKeyExp;
		try {
			CryptoKeyWriter.writeMyCipheredKeyExp(myCipheredKeyExp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
}