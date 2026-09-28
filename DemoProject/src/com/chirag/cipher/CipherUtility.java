package com.chirag.cipher;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.raapid.service.apigateway.audit.util.CipherUtility;
import com.raapid.service.apigateway.audit.util.Logger;
import com.raapid.service.apigateway.audit.util.SystemException;

public class CipherUtility {
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		CipherUtility obj = new CipherUtility();
		obj.init();
		String encryptedData ="5ta5SEDHlY3YN0HdM6z8s0vGVz58K1gloSRNdF7OjExu_I1W6bxImq_-PEaf9tUKXDTRywLQjh3ZCmyEBX0S6hKy_j310Onuu5Zdb1nQbck="; 
				//obj.encrypt("Hello hi");
		System.out.println("encData : "+encryptedData);
		String decryptData = obj.decrypt(encryptedData);
		System.out.println("Dyc data : "+ decryptData);
	}
	
	//@Value("${cipher.key}")
	private String cipherKey ="e*jM;%kk|:F%vJ{t"; 
			//"EI;Sm}Q):_2Il?_Z";

	//private final String key = "aesEncryptionKey";
	private SecretKeySpec skeySpec;
	private Cipher encryptionCipher;
	private Cipher decryptionCipher;
	
	private static final String ENCRYPTION_ALOG = "AES";
	
	@PostConstruct
	protected void init() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException
	{
		skeySpec = new SecretKeySpec(cipherKey.getBytes(StandardCharsets.UTF_8), ENCRYPTION_ALOG);
		encryptionCipher = Cipher.getInstance(ENCRYPTION_ALOG);
		encryptionCipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		decryptionCipher = Cipher.getInstance(ENCRYPTION_ALOG);
		decryptionCipher.init(Cipher.DECRYPT_MODE, skeySpec);
	}
	
	public String encrypt(String plainText)
	{
		try {
			
	        byte[] encrypted = encryptionCipher.doFinal(plainText.getBytes());
	        return Base64.getUrlEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			System.out.println(ex);
			return "";
		} 
        
	}
	
	public String decrypt(String encryptedText)
	{
		try {
			
	        byte[] original = decryptionCipher.doFinal(Base64.getUrlDecoder().decode(encryptedText));
	 
	        return new String(original);
	    } catch (Exception ex) {
	    	System.out.println(ex);
	    	return "";
	    }
	}
}
