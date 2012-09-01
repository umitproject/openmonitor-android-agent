package org.umit.icm.mobile.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.umit.icm.mobile.process.Globals;

public class AggregatorCrypto {
	
	
	public static String aesEncrypt(byte[] data){
		byte[] encryptedData = null;
		try {
			encryptedData = AESCrypto.encrypt(Globals.keyManager.getAESKey(), data);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return AggregatorCrypto.encodeData(encryptedData);	
	}
	
	public static byte[] aesDecrypt(byte[] data){
		byte[] decodedData = null;
		//System.out.println("-----------_______________---------------- Got this data: \n" + new String(data));	
		decodedData = Base64.decodeBase64(data);		
		//System.out.println("-----------_______________---------------- Got this decoded_data: \n" + new String(decodedData));		
		byte[] decryptedDecodedData = null;
		try {
			decryptedDecodedData = AESCrypto.decrypt(Globals.keyManager.getAESKey(), decodedData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//System.out.println("-----------_______________---------------- Got this decrypted_decoded_data: " + new String(decryptedDecodedData));
		return decryptedDecodedData;
	}
	
	public static String rsaAggregatorPublicKeyEncypt(byte[] data){
		byte[] encryptedData = null;
		try {
			encryptedData =  RSACrypto.encryptPublic(Globals.keyManager.getAggregatorPublicKey(), data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return AggregatorCrypto.encodeData(encryptedData);
	}
	
	public static String encodeData(byte[] data){
		return new String(Base64.encodeBase64(data));
	}
}
