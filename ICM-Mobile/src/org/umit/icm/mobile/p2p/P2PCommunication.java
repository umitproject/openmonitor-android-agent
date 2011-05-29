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

import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.proto.MessageProtos.ResponseHeader;
import org.umit.icm.mobile.utils.AESCrypto;
import org.umit.icm.mobile.utils.RSACrypto;

public class P2PCommunication {
	public static byte[] sendMessage(AgentData agentInfo, byte[] message) throws Exception {
		byte [] symmetricKey = AESCrypto.readKey("mySecretKey.priv");
		byte [] cipherBytes = AESCrypto.encrypt(symmetricKey, message);
		// TODO HTTP send
		// TODO HTTP respond
		ResponseHeader header = null;
		checkResponse(header);
		return AESCrypto.decrypt(symmetricKey, cipherBytes);
	}
	
	public static byte[] sendMessagePublic(AgentData agentInfo, byte[] message) throws Exception {
		PrivateKey privateKey = RSACrypto.readPrivateKey("myPrivateKey.priv");
		byte [] cipherBytes = RSACrypto.encryptPrivate(privateKey, message);
		// TODO HTTP send
		// TODO HTTP respond
		ResponseHeader header = null;
		checkResponse(header);
		PublicKey peerPublicKey = RSACrypto.readPublicKey(agentInfo.getAgentIP()+"PublicKey.pub");
		return RSACrypto.decryptPublic(peerPublicKey, cipherBytes);
	}
	
	public static void checkResponse(ResponseHeader header) throws Exception {
		int myCurrentTestVersionNo = 0, myCurrentVersionNo = 0;
		if (header.getCurrentTestVersionNo() > myCurrentTestVersionNo)
			;
			// contact aggregator to update testversion
		if (header.getCurrentVersionNo() > myCurrentVersionNo)
			;
			// contact aggregator to update agentversion
	}
}