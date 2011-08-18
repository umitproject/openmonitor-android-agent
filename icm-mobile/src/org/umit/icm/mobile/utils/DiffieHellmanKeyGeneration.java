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
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;

/**
 * Uses DiffieHellman key agreement algorithm to generate a shared AES
 * secret key.
 */

public class DiffieHellmanKeyGeneration {
	
	public static KeyPair generateKeyPair() throws GeneralSecurityException, IOException  {
		DHParameterSpec dhParameterSpec
		= DiffieHellmanValues.generateDiffieHellmanValues();
		KeyPairGenerator keyPairGenerator 
		= KeyPairGenerator.getInstance("DH");		 
		keyPairGenerator.initialize(dhParameterSpec);
		return keyPairGenerator.generateKeyPair();
	}
	
	public static SecretKey generateSecretKey(PrivateKey privateKey, byte[] publicKeyBytesResponse) throws GeneralSecurityException {
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytesResponse);
		KeyFactory keyFact = KeyFactory.getInstance("DH");
		PublicKey publicKeyResponse = keyFact.generatePublic(x509KeySpec);
		 
		KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
		keyAgreement.init(privateKey);
		keyAgreement.doPhase(publicKeyResponse, true);
		 
		return keyAgreement.generateSecret("AES");		
		
	}
}