package com.apetkova.tm.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
//import org.apache.commons.codec.binary.Base64;

public class PasswordEncryptor {

	private static final int ITERATION_NUMBER = 1000;
	private static final String RANDOM_ALGORITHM = "SHA1PRNG";
	private static final String DIGEST_ALGORITHM = "SHA-1";
	private static final String ENCODING = "UTF-8";
	private static final int B_SALT_SIZE = 8;
	private static final int B_MSSG_SIZE = 20;	
	private static final int B_FULL_SIZE = 28;

	public PasswordEncryptor(){
	}

	public byte[] generateSalt(){

		SecureRandom random;
		byte[] bSalt = new byte[B_SALT_SIZE];
		try {
			random = SecureRandom.getInstance(RANDOM_ALGORITHM);		
			random.nextBytes(bSalt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return bSalt;
	}

	public String encryptPassword(String password, byte[] salt){
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(DIGEST_ALGORITHM);
			digest.reset();
			digest.update(salt);

			byte[] input = digest.digest(password.getBytes(ENCODING));

			for (int i = 0; i < ITERATION_NUMBER; i++) {
				digest.reset();
				input = digest.digest(input);
			}

			byte[] passAndSalt = new byte[B_FULL_SIZE];

			for (int i = 0; i < B_MSSG_SIZE; i++)
				passAndSalt[i] = input[i];

			for (int i = B_MSSG_SIZE; i < B_FULL_SIZE; i++)
				passAndSalt[i] = salt[i - B_MSSG_SIZE];

			return null; // java.util.Base64.encodeBase64String(passAndSalt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public byte[] extractSalt(String encryptedPassword){
		byte [] passAndSalt = null; //java.util.Base64.decodeBase64(encryptedPassword);
		byte [] salt = new byte[B_SALT_SIZE];
		int j = B_SALT_SIZE - 1;

		for(int i = passAndSalt.length - 1; i >= passAndSalt.length - B_SALT_SIZE; i--){
			salt[j]  = passAndSalt[i];
			j--;
		}

		return salt;
	}

	public String encryptPasswordOnReg(String password){

		byte[] salt = generateSalt();
		return encryptPassword(password, salt);
	}


	public String encryptPasswordOnLogin(String enteredPassword, String password){

		byte [] salt = extractSalt(password);
		return encryptPassword(enteredPassword, salt);		
	}

}
