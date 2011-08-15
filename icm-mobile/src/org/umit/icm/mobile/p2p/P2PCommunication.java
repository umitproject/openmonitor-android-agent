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

import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.proto.MessageProtos.AgentData;
import org.umit.icm.mobile.utils.AESCrypto;
import org.umit.icm.mobile.utils.CryptoKeyReader;
import org.umit.icm.mobile.utils.RSACrypto;
import org.umit.icm.mobile.connectivity.TCPClient;


/**
 * Provides sender functions for P2P messages.
 */

public class P2PCommunication {
	
	/**
	 * Sends the byte[] message to the {@link AgentData} agentInfo.
	 * Uses symmetric key cryptography and opens a TCP connection to the peer
	 * using {@link TCPClient}.
	 * 
	 * 
	
	 @param agentInfo recipient peer data of type {@link AgentData}
	 *

	 @see CryptoKeyReader
	 *
	 
	 @see AESCrypto
	 *
	 
	 @see TCPClient
	 */
	public static void sendMessage(AgentData agentInfo, byte[] rawMessage, int messageID) 
	throws Exception {
		byte [] cipherBytes;
		byte [] totalBytes;
		byte [] message;
		byte [] completeMessage;		
		message = MessageBuilder.generateMessageWithoutLength(messageID, rawMessage);
		if(Constants.P2P_ENCRYPTION == true) {
			byte [] symmetricKey = CryptoKeyReader.getMySecretKey();
			cipherBytes = AESCrypto.encrypt(symmetricKey, message);
		} else {
			cipherBytes = message;
		}
		
		completeMessage = MessageBuilder.byteArrayAppend(
				MessageBuilder.intToByteArray(cipherBytes.length), 
				cipherBytes);
		Globals.tcpClient.openConnection(agentInfo.getAgentIP()
				, agentInfo.getAgentPort());
		Globals.tcpClient.writeLine(completeMessage);		
		byte[] messageSizeBytes = Globals.tcpClient.readBytes(4);
		int messageSize = MessageBuilder.byteArrayToInt(messageSizeBytes);
		byte[] totalBytesEncrypted =  Globals.tcpClient.readBytes(messageSize);
		
		if(Constants.P2P_ENCRYPTION == true) {
			byte[] peerSecretKey = CryptoKeyReader.getPeerSecretKey(agentInfo.getAgentIP());
			totalBytes = AESCrypto.decrypt(peerSecretKey, totalBytesEncrypted);
		} else {
			totalBytes = totalBytesEncrypted;
		}
		
		byte[] idBytes = MessageBuilder.getSubArray(totalBytes, 0, 3);
		int id = MessageBuilder.byteArrayToInt(idBytes);
		byte [] msgBytes = MessageBuilder.getSubArray(totalBytes, 4, messageSize - 1);
		MessageTranslation.translateMessage(id, msgBytes, agentInfo);
		Globals.tcpClient.closeConnection();

	}
	
	/**
	 * Sends the byte[] message to the {@link AgentData} agentInfo.
	 * Uses asymmetric key cryptography and opens a TCP connection to the peer
	 * using {@link TCPClient}.
	 * 
	 * 
	
	 @param agentInfo recipient peer data of type {@link AgentData}
	 *
	 
	 @return byte[]
	 *
	 
	 @see CryptoKeyReader
	 *
	 
	 @see RSACrypto
	 *
	 
	 @see TCPClient
	 */
	public static void sendMessagePublic(AgentData agentInfo, byte[] rawMessage, int messageID) 
	throws Exception {
		byte [] cipherBytes;
		byte [] totalBytes;
		byte [] message;
		byte [] completeMessage;
		message = MessageBuilder.generateMessageWithoutLength(messageID, rawMessage);
		if(Constants.P2P_ENCRYPTION == true) {
			PrivateKey privateKey = CryptoKeyReader.getMyPrivateKey();
			cipherBytes = RSACrypto.encryptPrivate(privateKey, message);
		} else {
			cipherBytes = message;
		}		
		completeMessage = MessageBuilder.byteArrayAppend(
				MessageBuilder.intToByteArray(cipherBytes.length), 
				cipherBytes);		
		Globals.tcpClient.openConnection(agentInfo.getAgentIP()
				, agentInfo.getAgentPort());
		Globals.tcpClient.writeLine(completeMessage);		
		byte[] messageSizeBytes = Globals.tcpClient.readBytes(4);		
		int messageSize = MessageBuilder.byteArrayToInt(messageSizeBytes);
		byte[] totalBytesEncrypted =  Globals.tcpClient.readBytes(messageSize);
		
		if(Constants.P2P_ENCRYPTION == true) {		
			totalBytes = 
				RSACrypto.decryptPublic(
						RSACrypto.stringToPublicKey(agentInfo.getPublicKey()), totalBytesEncrypted);
		}
		else {
			totalBytes = totalBytesEncrypted;
		}
		
		byte[] idBytes = MessageBuilder.getSubArray(totalBytes, 0, 3);
		int id = MessageBuilder.byteArrayToInt(idBytes);
		byte [] msgBytes = MessageBuilder.getSubArray(totalBytes, 4, messageSize - 1);
		MessageTranslation.translateMessage(id, msgBytes, agentInfo);
		Globals.tcpClient.closeConnection();								
	}	
		
}