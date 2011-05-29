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

import java.security.PrivateKey;
import java.security.PublicKey;

public class CryptoKeyReader {
	private static String MY_PUBLIC_KEY_FILE = "myPublicKey.pub";
	private static String MY_PRIVATE_KEY_FILE = "myPrivateKey.priv";
	private static String MY_SECRET_KEY_FILE = "mySecretKey.sec";
	private static String PEER_PUBLIC_KEY_FILE = "PublicKey.pub";
	
	public static PublicKey getMyPublicKey() throws Exception {
		return RSACrypto.readPublicKey(MY_PUBLIC_KEY_FILE);
	}
	
	public static PrivateKey getMyPrivateKey() throws Exception {
		return RSACrypto.readPrivateKey(MY_PRIVATE_KEY_FILE);
	}
	
	public static byte[] getMySecretKey() throws Exception {
		return AESCrypto.readKey(MY_SECRET_KEY_FILE);
	}
	
	public static PublicKey getPeerPublicKey(String peerIP) throws Exception {
		return RSACrypto.readPublicKey(peerIP+PEER_PUBLIC_KEY_FILE);
	}
}