package com.practo.urlshortener;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.tomcat.util.codec.binary.Base64;

import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

public class Utility {
	public static final String ALPHABET = "123456789abcdfghjkmnpqrstvwxyzABCDFGHJKLMNPQRSTVWXYZ";
	public static final int BASE = ALPHABET.length();
	public static final String key = "RANDOM-KEY-123456789";
	public static final String UserID_Session = "userID";
	private static byte[] sharedvector = { 0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11 };
	public static String URL_Prefix = "";
	public static UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();

	public static String encode(Long num) {
		StringBuilder str = new StringBuilder();
		while (num > 0) {
			str.insert(0, ALPHABET.charAt((int) (num % BASE)));
			num = num / BASE;
		}
		return str.toString();
	}

	public static Long decode(String str) {
		Long num = 0l;
		for (int i = 0; i < str.length(); i++) {
			num = num * BASE + ALPHABET.indexOf(str.charAt(i));
		}
		return num;
	}

	public static boolean isValidShortLink(String shortLink) {
		if (shortLink == null || shortLink.trim().isEmpty())
			return false;
		return true;
	}

	public static String encriptPassword(String password) throws Exception {
		byte[] keyArray = new byte[24];
		byte[] temporaryKey;
		byte[] toEncryptArray = null;

		toEncryptArray = password.getBytes("UTF-8");
		MessageDigest m = MessageDigest.getInstance("MD5");
		temporaryKey = m.digest(key.getBytes("UTF-8"));

		if (temporaryKey.length < 24) // DESede require 24 byte length key
		{
			int index = 0;
			for (int i = temporaryKey.length; i < 24; i++) {
				keyArray[i] = temporaryKey[index];
			}
		}

		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
		byte[] encrypted = c.doFinal(toEncryptArray);
		return Base64.encodeBase64String(encrypted);
	}

	public static String decryptPassword(String password) throws Exception {
		byte[] keyArray = new byte[24];
		byte[] temporaryKey;
		MessageDigest m = MessageDigest.getInstance("MD5");
		temporaryKey = m.digest(key.getBytes("UTF-8"));

		if (temporaryKey.length < 24) // DESede require 24 byte length key
		{
			int index = 0;
			for (int i = temporaryKey.length; i < 24; i++) {
				keyArray[i] = temporaryKey[index];
			}
		}

		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
		byte[] decrypted = c.doFinal(Base64.decodeBase64(password));

		return new String(decrypted, "UTF-8");

	}

	public static boolean isValidEMailID(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public static String getBrowserName(String agent) {
		return parser.parse(agent).getName();
	}
}
