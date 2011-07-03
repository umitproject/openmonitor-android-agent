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

package org.umit.icm.mobile.test.utils;


import java.security.KeyPair;

import org.umit.icm.mobile.utils.AESCrypto;
import org.umit.icm.mobile.utils.CryptoKeyReader;
import org.umit.icm.mobile.utils.CryptoKeyWriter;
import org.umit.icm.mobile.utils.RSACrypto;

import junit.framework.Assert;
import android.test.AndroidTestCase;


public class CryptoReadWriteTests extends AndroidTestCase {
	
	private KeyPair keyPair;

    public void testPublicKeyReadWrite() throws Throwable {
    	keyPair = RSACrypto.generateKey();
    	CryptoKeyWriter.writeMyPublicKey(keyPair.getPublic());    	    	
        Assert.assertEquals(CryptoKeyReader.getMyPublicKey() 
        		,keyPair.getPublic());
    }
    
    public void testPrivateKeyReadWrite() throws Throwable {
    	keyPair = RSACrypto.generateKey();
    	CryptoKeyWriter.writeMyPrivateKey(keyPair.getPrivate());    	    	
        Assert.assertEquals(CryptoKeyReader.getMyPrivateKey() 
        		,keyPair.getPrivate());
    }
    
    public void testSecretKeyReadWrite() throws Throwable {
    	byte[] key = AESCrypto.generateKey(
    			"secretICMMobilePassword".getBytes());
    	CryptoKeyWriter.writeMySecretKey(key);
        Assert.assertTrue(byteArrayEquals(CryptoKeyReader.getMySecretKey(), key));
    }
    
    public void testCipheredKeyReadWrite() throws Throwable {
    	byte[] key = AESCrypto.generateKey(
    			"secretICMMobilePassword".getBytes());
    	CryptoKeyWriter.writeMyCipheredKey(key);
        Assert.assertTrue(byteArrayEquals(CryptoKeyReader.getMyCipheredKey(), key));
    }
    
    public boolean byteArrayEquals(byte[] array1, byte[] array2) {
    	if (array1.length != array2.length)
    		return false;
    	for(int i = 0; i < array1.length; i++)
    		if(array1[i] != array2[i])
    			return false;
    	return true;
    }
    
    public void testPeerSecretKeyReadWrite() throws Throwable {
    	byte[] key = AESCrypto.generateKey(
    			"secretICMMobilePassword".getBytes());
    	CryptoKeyWriter.writePeerSecretKey(key, "Peer1");
        Assert.assertTrue(byteArrayEquals(CryptoKeyReader.getPeerSecretKey("Peer1"), key));
    }
    
}