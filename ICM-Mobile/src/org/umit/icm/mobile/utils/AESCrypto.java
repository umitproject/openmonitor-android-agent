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

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypto {
	
	private static int keySize = 128;

	public static String encrypt(String key, String plainText) throws Exception {
		byte[] generatedKey = generateKey(key.getBytes());
		byte[] cipherText = encrypt(generatedKey, plainText.getBytes());
		return toHex(cipherText);
	}
	
	public static String decrypt(String key, String cipherText) throws Exception {
		byte[] generatedKey = generateKey(key.getBytes());
		return new String(decrypt(generatedKey, toByte(cipherText)));
	}

	public static byte[] generateKey(byte[] key) throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		SecureRandom randomGen = SecureRandom.getInstance("SHA1PRNG");
		randomGen.setSeed(key);
	    keyGen.init(keySize, randomGen); 
	    SecretKey secretKey = keyGen.generateKey();
	    return secretKey.getEncoded();
	}

	public static byte[] encrypt(byte[] byteKey, byte[] plainBytes) throws Exception {
	    SecretKeySpec secretkeySpec = new SecretKeySpec(byteKey, "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, secretkeySpec);
	    return cipher.doFinal(plainBytes);		
	}

	public static byte[] decrypt(byte[] byteKey, byte[] cipherBytes) throws Exception {
	    SecretKeySpec secretkeySpec = new SecretKeySpec(byteKey, "AES");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, secretkeySpec);
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

}