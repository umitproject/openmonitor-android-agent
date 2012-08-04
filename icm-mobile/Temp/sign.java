import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
class Sign{
	public static void main(String[] args) throws Exception{
		String m ="This is my message";
		System.out.println(m);
		Signature s = Signature.getInstance("SHA1withRSA");
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair kp = keyPairGen.generateKeyPair();
		Privatekey p = kp.getPrivate();

	}
}
