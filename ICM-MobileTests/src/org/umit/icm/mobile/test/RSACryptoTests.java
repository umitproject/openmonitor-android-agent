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

package org.umit.icm.mobile.test;


import java.security.KeyPair;

import org.umit.icm.mobile.utils.RSACrypto;

import junit.framework.Assert;
import android.test.AndroidTestCase;


public class RSACryptoTests extends AndroidTestCase {
	private KeyPair keyPair;
	
	protected void setup() throws Exception {
		keyPair = RSACrypto.generateKey();
	}
	
    public void publicEncryptDecrypt() throws Throwable {
    	String cipherText = RSACrypto.encryptPublic(keyPair.getPublic(), "This is a test string");
        Assert.assertEquals("This is a test string", RSACrypto.decryptPrivate(keyPair.getPrivate(), cipherText));
    }
    
    public void privateEncryptDecrypt() throws Throwable {
    	String cipherText = RSACrypto.encryptPrivate(keyPair.getPrivate(), "This is a test string");
        Assert.assertEquals("This is a test string", RSACrypto.decryptPublic(keyPair.getPublic(), cipherText));
    }

}