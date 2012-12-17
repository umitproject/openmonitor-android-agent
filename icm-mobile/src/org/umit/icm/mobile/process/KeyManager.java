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
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.umit.icm.mobile.proto.MessageProtos.RSAKey;
import org.umit.icm.mobile.utils.CryptoKeyWriter;
import org.umit.icm.mobile.utils.RSACrypto;

public class KeyManager {	
	private PrivateKey myPrivateKey;
	private PublicKey myPublicKey;
	private String myCipheredKeyMod;
	private String myCipheredKeyExp;
	private PublicKey aggrPublicKey;
	private byte[] aesKey;
	
	public KeyManager(PrivateKey myPrivateKey,
			PublicKey myPublicKey, String myCipheredKeyMod,
			String myCipheredKeyExp) {
		super();		
		this.myPrivateKey = myPrivateKey;
		this.myPublicKey = myPublicKey;
		this.myCipheredKeyMod = myCipheredKeyMod;
		this.myCipheredKeyExp = myCipheredKeyExp;
		
	}

	public KeyManager() {
		super();
		//generate all keys
		try {
			if(Constants.DEBUG_MODE)
				System.out.println("Setting up KeyManager");
			//generate RSA key pair
			KeyPair keypair = RSACrypto.generateKey();
			this.setMyPublicKey(keypair.getPublic());
			this.setMyPrivateKey(keypair.getPrivate());
			//now separate public key into its mod and exp
			RSAKey rsaKey = RSACrypto.getPublicKeyIntegers(keypair.getPublic());
			this.setMyCipheredKeyMod(rsaKey.getMod());
			this.setMyCipheredKeyExp(rsaKey.getExp());
			//generate aggregator's public key
			this.setAggregatorPublicKey(RSACrypto.generatePublicKey(Constants.AGGR_PUB_KEY_MOD,
					Constants.AGGR_PUB_KEY_EXP));
			if(Constants.DEBUG_MODE)
				System.out.println("AGGRPUBLICKEY inside KeyManager : "+ aggrPublicKey);
			//generate AES symmetric key
			byte[] bits = new byte[Constants.AES_BLOCK_SIZE];
			new Random().nextBytes(bits);
			this.setAESKey(Base64.encodeBase64(bits));	
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public synchronized PublicKey getAggregatorPublicKey() {
		return this.aggrPublicKey;
	}
	
	public synchronized void setAggregatorPublicKey(PublicKey publicKey) {
		this.aggrPublicKey = publicKey;
	}
	
	public synchronized byte[] getAESKey() {
		return this.aesKey;
	}
	
	public synchronized void setAESKey(byte[] aesKey) {
		this.aesKey = aesKey;
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