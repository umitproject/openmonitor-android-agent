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

package org.umit.icm.mobile.p2p;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.codec.binary.Base64;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.proto.MessageProtos.ResponseHeader;
import org.umit.icm.mobile.utils.AESCrypto;
import org.umit.icm.mobile.utils.CryptoKeyReader;
import org.umit.icm.mobile.utils.RSACrypto;

public class P2PCommunication {
	public static byte[] sendMessage(AgentData agentInfo, byte[] message) throws Exception {
		byte [] symmetricKey = CryptoKeyReader.getMySecretKey();
		byte [] cipherBytes = AESCrypto.encrypt(symmetricKey, message);
		String cipherString =
			new String(Base64.encodeBase64(cipherBytes));
		// TODO HTTP send
		// TODO HTTP respond
		String responseString = "";
		byte [] response = Base64.decodeBase64(responseString.getBytes());		 
		return AESCrypto.decrypt(symmetricKey, response);
	}
	
	public static byte[] sendMessagePublic(AgentData agentInfo, byte[] message) throws Exception {
		PrivateKey privateKey = CryptoKeyReader.getMyPrivateKey();
		byte [] cipherBytes = RSACrypto.encryptPrivate(privateKey, message);
		String cipherString =
			new String(Base64.encodeBase64(cipherBytes));
		// TODO HTTP send
		// TODO HTTP respond
		PublicKey peerPublicKey = CryptoKeyReader.getPeerPublicKey(agentInfo.getAgentIP());
		String responseString = "";
		byte [] response = Base64.decodeBase64(responseString.getBytes());
		return RSACrypto.decryptPublic(peerPublicKey, response);
	}
	
	public static void checkResponse(ResponseHeader header) throws Exception {		
		if (header.getCurrentTestVersionNo() 
				> Globals.versionManager.getTestsVersion())
			;
			// TODO contact aggregator to update testversion
		if (header.getCurrentVersionNo() 
				> Globals.versionManager.getAgentVersion())
			;
			// TODO contact aggregator to update agentversion
	}
}