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

import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.utils.AESCrypto;
import org.umit.icm.mobile.utils.CryptoKeyReader;
import org.umit.icm.mobile.utils.RSACrypto;

public class P2PCommunication {
	public static byte[] sendMessage(AgentData agentInfo, byte[] message) throws Exception {
		byte [] symmetricKey = CryptoKeyReader.getMySecretKey();
		byte [] cipherBytes = AESCrypto.encrypt(symmetricKey, message);	
		
		Globals.tcpClient.openConnection(agentInfo.getAgentIP()
				, agentInfo.getAgentPort());
		Globals.tcpClient.writeLine(cipherBytes);
		Globals.tcpClient.closeConnection();
				
		String responseString = Globals.tcpServer.getRequest();
		byte [] response = responseString.getBytes();	
		byte[] peerSecretKey = CryptoKeyReader.getPeerSecretKey(agentInfo.getAgentIP());
		return AESCrypto.decrypt(peerSecretKey, response);
	}
	
	public static byte[] sendMessagePublic(AgentData agentInfo, byte[] message) throws Exception {
		PrivateKey privateKey = CryptoKeyReader.getMyPrivateKey();
		byte [] cipherBytes = RSACrypto.encryptPrivate(privateKey, message);
		
		Globals.tcpClient.openConnection(agentInfo.getAgentIP()
				, agentInfo.getAgentPort());
		Globals.tcpClient.writeLine(cipherBytes);
		Globals.tcpClient.closeConnection();
				
		String responseString = Globals.tcpServer.getRequest();
		byte [] response = responseString.getBytes();
		return RSACrypto.decryptPublic(
				RSACrypto.stringToPublicKey(agentInfo.getPublicKey()), response);
	}	
		
}