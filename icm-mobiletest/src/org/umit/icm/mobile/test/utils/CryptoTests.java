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


import org.umit.icm.mobile.utils.AESCrypto;

import junit.framework.Assert;
import android.test.AndroidTestCase;


public class CryptoTests extends AndroidTestCase {

    public void testEncryptDecrypt() throws Throwable {
    	String cipherText = AESCrypto.encrypt("secretICMMobilePassword"
    			, "This is a test string");
        Assert.assertEquals("This is a test string"
        		, AESCrypto.decrypt("secretICMMobilePassword", cipherText));
    }
    
    public void testReadWrite() throws Throwable {
    	byte[] key = AESCrypto.generateKey(
    			"secretICMMobilePassword".getBytes());
    	AESCrypto.saveKey("mySecretKey.priv", key);
        Assert.assertTrue(byteArrayEquals(AESCrypto.readKey(
        		"mySecretKey.priv"),key));
    }
    
    public boolean byteArrayEquals(byte[] array1, byte[] array2) {
    	if (array1.length != array2.length)
    		return false;
    	for(int i = 0; i < array1.length; i++)
    		if(array1[i] != array2[i])
    			return false;
    	return true;
    }

}