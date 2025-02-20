package com.chirag.encryption;
//Java program to demonstrate the creation 
//of Encryption and Decryption with Java AES 
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec; 

class AESDemo { 
	// Class private variables 
	private static final String SECRET_KEY 
		= "phrase"; 
	
	private static final String SALT = "ssshhhhhhhhhhh!!!!"; 

	public static void main(String[] args) throws Exception 
	{ 
		// Create String variables 
		String originalString = "kpmg.admin"; 
		System.out.println(originalString.subSequence(0, originalString.length()-1));
		
		// Call encryption method 
		/*String encryptedString 
			= AESDemo.encrypt(originalString);*/ 
		
		// Call decryption method 
		String decryptedString 
			= AESDemo.decrypt2("U2FsdGVkX1/EPBbRSzqYGkmtu5oYuX5bAi3JiG7OCk4="); 

		// Print all strings 
		System.out.println(originalString); 
		//System.out.println("encrypted : "+encrypt2("Kpmg@3690"));
		System.out.println("decrupted : "+decryptedString); 
	} 
	
	// This method use to encrypt to string 
	public static String encrypt(String strToEncrypt) 
	{ 
		try { 

			// Create default byte array 
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 0, 0, 0, 0, 0 }; 
			IvParameterSpec ivspec 
				= new IvParameterSpec(iv); 

			// Create SecretKeyFactory object 
			SecretKeyFactory factory 
				= SecretKeyFactory.getInstance( 
					"PBKDF2WithHmacSHA256"); 
			
			// Create KeySpec object and assign with 
			// constructor 
			KeySpec spec = new PBEKeySpec( 
				SECRET_KEY.toCharArray()); 
			SecretKey tmp = factory.generateSecret(spec); 
			SecretKeySpec secretKey = new SecretKeySpec( 
				tmp.getEncoded(), "AES"); 

			Cipher cipher = Cipher.getInstance( 
				"AES/CBC/PKCS5Padding"); 
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, 
						ivspec); 
			// Return encrypted string 
			return Base64.getEncoder().encodeToString( 
				cipher.doFinal(strToEncrypt.getBytes( 
					StandardCharsets.UTF_8))); 
		} 
		catch (Exception e) { 
			System.out.println("Error while encrypting: "
							+ e.toString()); 
		} 
		return null; 
	} 

	// This method use to decrypt to string 
	public static String decrypt(String strToDecrypt) 
	{ 
		try { 

			// Default byte array 
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 0, 0, 0, 0, 0 }; 
			// Create IvParameterSpec object and assign with 
			// constructor 
			IvParameterSpec ivspec 
				= new IvParameterSpec(iv); 

			// Create SecretKeyFactory Object 
			SecretKeyFactory factory 
				= SecretKeyFactory.getInstance( 
					"PBKDF2WithHmacSHA256"); 

			// Create KeySpec object and assign with 
			// constructor 
			byte[] cipherData = Base64.getDecoder().decode(strToDecrypt);
			byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);
			KeySpec spec = new PBEKeySpec( 
				SECRET_KEY.toCharArray(), saltData, 
				65536, 256); 
			SecretKey tmp = factory.generateSecret(spec); 
			SecretKeySpec secretKey = new SecretKeySpec( 
				tmp.getEncoded(), "AES"); 

			Cipher cipher = Cipher.getInstance( 
				"AES/CBC/PKCS5PADDING"); 
			cipher.init(Cipher.DECRYPT_MODE, secretKey, 
						ivspec); 
			// Return decrypted string 
			return new String(cipher.doFinal( 
				Base64.getDecoder().decode(strToDecrypt))); 
		} 
		catch (Exception e) { 
			System.out.println("Error while decrypting: "
							+ e.toString()); 
		} 
		return null; 
	} 
	
	public static String decrypt2(String cipherText) throws Exception {
		String secret = SECRET_KEY;
		//String cipherText = "U2FsdGVkX1+tsmZvCEFa/iGeSA0K7gvgs9KXeZKwbCDNCs2zPo+BXjvKYLrJutMK+hxTwl/hyaQLOaD7LLIRo2I5fyeRMPnroo6k8N9uwKk=";

		byte[] cipherData = Base64.getDecoder().decode(cipherText);
		byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
		SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
		IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

		byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
		Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] decryptedData = aesCBC.doFinal(encrypted);
		String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);
		return decryptedText;
	}
	
	public static String encrypt2(String cipherText) throws Exception {
		String secret = SECRET_KEY;
		//String cipherText = "U2FsdGVkX1+tsmZvCEFa/iGeSA0K7gvgs9KXeZKwbCDNCs2zPo+BXjvKYLrJutMK+hxTwl/hyaQLOaD7LLIRo2I5fyeRMPnroo6k8N9uwKk=";

		byte[] cipherData = Base64.getEncoder().encode(cipherText.getBytes());
		byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
		SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
		IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

		byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
		Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aesCBC.init(Cipher.ENCRYPT_MODE, key, iv);
		byte[] encryptedData = aesCBC.doFinal(encrypted);
		String encryptedText = new String(encryptedData, StandardCharsets.UTF_8);
		return encryptedText;
	}
	
	/**
	 * Generates a key and an initialization vector (IV) with the given salt and password.
	 * <p>
	 * This method is equivalent to OpenSSL's EVP_BytesToKey function
	 * (see https://github.com/openssl/openssl/blob/master/crypto/evp/evp_key.c).
	 * By default, OpenSSL uses a single iteration, MD5 as the algorithm and UTF-8 encoded password data.
	 * </p>
	 * @param keyLength the length of the generated key (in bytes)
	 * @param ivLength the length of the generated IV (in bytes)
	 * @param iterations the number of digestion rounds 
	 * @param salt the salt data (8 bytes of data or <code>null</code>)
	 * @param password the password data (optional)
	 * @param md the message digest algorithm to use
	 * @return an two-element array with the generated key and IV
	 */
	public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

	    int digestLength = md.getDigestLength();
	    int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
	    byte[] generatedData = new byte[requiredLength];
	    int generatedLength = 0;

	    try {
	        md.reset();

	        // Repeat process until sufficient data has been generated
	        while (generatedLength < keyLength + ivLength) {

	            // Digest data (last digest if available, password data, salt if available)
	            if (generatedLength > 0)
	                md.update(generatedData, generatedLength - digestLength, digestLength);
	            md.update(password);
	            if (salt != null)
	                md.update(salt, 0, 8);
	            md.digest(generatedData, generatedLength, digestLength);

	            // additional rounds
	            for (int i = 1; i < iterations; i++) {
	                md.update(generatedData, generatedLength, digestLength);
	                md.digest(generatedData, generatedLength, digestLength);
	            }

	            generatedLength += digestLength;
	        }

	        // Copy key and IV into separate byte arrays
	        byte[][] result = new byte[2][];
	        result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
	        if (ivLength > 0)
	            result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

	        return result;

	    } catch (DigestException e) {
	        throw new RuntimeException(e);

	    } finally {
	        // Clean out temporary data
	        Arrays.fill(generatedData, (byte)0);
	    }
	}
} 

