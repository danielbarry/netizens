
package netizens.bank.utils.encryption;

import java.util.Set;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
* Symmetric key encryption utility class.
* 
* @author robertnorthard
*/
public class SymmetricEncryption{

	// class logger
	private static final Logger LOGGER = Logger.getLogger(
		SymmetricEncryption.class.getName());

	private static final String HASH_ALGORITHM = "SHA-256";
	private static final String SYMMETRIC_ALGORITHM = "AES";
	private static final String CHARACTER_ENCODING = "UTF-8";

	/**
	* Create composite symmetric encryption key from set of credentials.
	*
	* @param credentials.
	* @return generate symmetric key, null if error.
	* @throws IllegalArgumentException 
	*		if credentials is null or size is less than or equal 0.
	*/
	public static Key generateKey(Set<String> credentials){

		if(credentials == null || credentials.size() <= 0){
			throw new IllegalArgumentException("Credentials cannot be null and must contain at least one type of biometric.");
		}

		Key symmetricKey = null;

		StringBuffer key = new StringBuffer();

		for(String k: credentials){
			key.append(k);
		}

		try{
			MessageDigest md = MessageDigest.getInstance(
				SymmetricEncryption.HASH_ALGORITHM);

			md.update(
				key.toString().getBytes(
					Charset.forName(
						SymmetricEncryption.CHARACTER_ENCODING)));

			 byte[] compositeDigest = md.digest();

			symmetricKey = new SecretKeySpec(compositeDigest, SymmetricEncryption.SYMMETRIC_ALGORITHM);

			return null;

		}catch(NoSuchAlgorithmException ex){
			LOGGER.log(Level.SEVERE, null, ex);
			return null;
		}
	}

	/**
	* Encrypt a message.
	*
	* @param message message to encrypt.
	* @param key key to use for encryption.
	* @return encrypted message, null if error.
	*/
	public static byte[] encrypt(byte[] message, Key key){

		try{
 			Cipher c = Cipher.getInstance(SymmetricEncryption.SYMMETRIC_ALGORITHM);
 			c.init(Cipher.ENCRYPT_MODE, key);
            
    	    return c.doFinal(message);

	    }catch(
	    	NoSuchAlgorithmException | 
	    	InvalidKeyException |
	    	IllegalBlockSizeException |
	    	BadPaddingException |
	    	NoSuchPaddingException ex){

			LOGGER.log(Level.SEVERE, null, ex);
			return null;
		}

	}

	/**
	* Decrypt a message.
	*
	* @param message message to decrypt.
	* @param key key to use for decryption.
	* @return decrypted message, null if error.
	*/
	public static byte[] decrypt(byte[] message, Key key){

		try{
 			Cipher c = Cipher.getInstance(SymmetricEncryption.SYMMETRIC_ALGORITHM);
 			c.init(Cipher.DECRYPT_MODE, key);
            
    	    return c.doFinal(message);

	    }catch(
	    	NoSuchAlgorithmException | 
	    	InvalidKeyException |
	    	IllegalBlockSizeException |
	    	BadPaddingException |
	    	NoSuchPaddingException ex){

			LOGGER.log(Level.SEVERE, null, ex);
			return null;
		}

	}
}
