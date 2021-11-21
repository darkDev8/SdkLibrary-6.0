package org.sdk6.security;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class SecurityTools {
	private static Cipher ecipher;
	private static Cipher dcipher;
	private static int iterationCount;
	private final static byte[] SALT = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35,
			(byte) 0xE3, (byte) 0x03 };

	static {
		iterationCount = 19;
	}

	/**
	 * Encrypts a text With DES algorithm.
	 * 
	 * @param secretKey The key want to encrypt data.
	 * @param plainText The text want to encrypt.
	 * @return The encrypted text.
	 * @throws Exception
	 */
	public static String encrypt(String secretKey, String plainText) throws Exception {
		// Key generation for enc and desc
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), SALT, iterationCount);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
		// Prepare the parameter to the ciphers
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, iterationCount);

		// Enc process
		ecipher = Cipher.getInstance(key.getAlgorithm());
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		String charSet = "UTF-8";
		byte[] in = plainText.getBytes(charSet);
		byte[] out = ecipher.doFinal(in);
		String encStr = new String(Base64.getEncoder().encode(out));
		return encStr;
	}

	/**
	 * Decrypts a text With DES algorithm.
	 * 
	 * @param secretKey The key want to decrypt data.
	 * @param plainText The encrypted text want to decrypt.
	 * @return The decrypted text.
	 * @throws Exception
	 */
	public static String decrypt(String secretKey, String encryptedText) throws Exception {
		// Key generation for enc and desc
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), SALT, iterationCount);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
		// Prepare the parameter to the ciphers
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, iterationCount);
		// Decryption process; same key will be used for decr
		dcipher = Cipher.getInstance(key.getAlgorithm());
		dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		byte[] enc = Base64.getDecoder().decode(encryptedText);
		byte[] utf8 = dcipher.doFinal(enc);
		String charSet = "UTF-8";
		String plainStr = new String(utf8, charSet);
		return plainStr;
	}

	/**
	 * Convert plain text to hash.
	 * 
	 * @param text The input text from user.
	 * @param toUpperCase Convert result hash to upper case.
	 * @return The hash text.
	 */
	public static String hashPlainText(String text, boolean toUpperCase) {
		if (toUpperCase) {
			return DigestUtils.md5Hex(text).toUpperCase();
		} else {
			return DigestUtils.md5Hex(text);
		}
	}
}
