package com.chirag;

import java.security.SecureRandom;

public class CipherKeyGenrator {

	public static String generateCipherKey() {
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";
		final int keyLength = 16;
		SecureRandom random = new SecureRandom();
		StringBuilder key = new StringBuilder(keyLength);
		for (int i = 0; i < keyLength; i++) {
			key.append(chars.charAt(random.nextInt(chars.length())));
		}
		return key.toString();
	}

	public static void main(String[] args) {
		System.out.println(generateCipherKey());
	}
}
