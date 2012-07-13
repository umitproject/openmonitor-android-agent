package org.umit.icm.mobile.aggregator;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.umit.icm.mobile.process.Globals;
import org.umit.icm.mobile.utils.AESCrypto;

public class AggregatorHelper {
	
	
	public static String AESEncrypt(byte[] data){
		byte[] encrypted_data =null;
		try {
			encrypted_data = AESCrypto.encrypt(Globals.AESKEY, data);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] encoded_encrypted_data= Base64.encodeBase64(encrypted_data);
		
		String encoded_encrypted_data_string = new String(encoded_encrypted_data);
		
		return encoded_encrypted_data_string;
		
	}

}
