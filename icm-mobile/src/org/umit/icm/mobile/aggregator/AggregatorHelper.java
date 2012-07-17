package org.umit.icm.mobile.aggregator;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.umit.icm.mobile.process.Constants;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.utils.AESCrypto;
import org.umit.icm.mobile.utils.RSACrypto;

public class AggregatorHelper {
	
	
	public static String aesEncrypt(byte[] data){
		byte[] data_padded=AggregatorHelper.addPadding(data);
		byte[] encrypted_data =null;
		try {
			encrypted_data = AESCrypto.encrypt(Globals.keyManager.getAESKey(), data_padded);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String encoded_encrypted_data_string = AggregatorHelper.encodeData(encrypted_data);
		
		return encoded_encrypted_data_string;
		
	}
	
	public static byte[] aesDecrypt(String data){
		byte[] decoded_data=null;
		decoded_data=Base64.decodeBase64(data.getBytes());
		
		byte[] decrypted_decoded_data =null;
		try {
			decrypted_decoded_data = AESCrypto.decrypt(Globals.keyManager.getAESKey(), decoded_data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] unpadded_decrypted_decoded_data=AggregatorHelper.removePadding(decrypted_decoded_data);
		
		return unpadded_decrypted_decoded_data;
		
	}
	
	public static byte[] addPadding(byte[] data){
		byte[] data_padded =null;
		if(data.length % Constants.AES_BLOCK_SIZE!=0){
			byte[] padding = new byte[Constants.AES_BLOCK_SIZE - (data.length % Constants.AES_BLOCK_SIZE)];
			
			data_padded = new byte[padding.length+data.length];
			
			Arrays.fill(padding, Constants.AES_DEFAULT_PADDING);
			
			System.arraycopy(data, 0, data_padded, 0, data.length);
			System.arraycopy(padding, 0, data_padded, data.length, padding.length);
		}else{
			data_padded=data;
		}
		return data_padded;
	}
	
	public static byte[] removePadding(byte[] data){
		
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		for(int i=0;i<data.length;i++)
		{
			if(data[i]!=123){
				baos.write(data[i]);
			}
		}
		byte[] unpaded_data= baos.toByteArray();
		
		return unpaded_data;
	}
	
	
	public static String rsaAggregatorPublicKeyEncypt(byte[] data){
		byte[] encrypted_data = null;
		try {
			encrypted_data =  RSACrypto.encryptPublic(Globals.keyManager.getAggregatorPublicKey(), data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String encoded_encrypted_data_string = AggregatorHelper.encodeData(encrypted_data);
		
		return encoded_encrypted_data_string;
	}
	
	public static String encodeData(byte[] data){
		byte [] encoded_data = Base64.encodeBase64(data);
		String encoded_data_string = new String(encoded_data);
		return encoded_data_string;
	}

}
