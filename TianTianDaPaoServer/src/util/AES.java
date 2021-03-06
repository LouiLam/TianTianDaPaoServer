package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AES {
	private static SecretKey key;
	private static String keyStr;
	static {

	}

	public static String generateSessionKey() {
		try {
			key = KeyGenerator.getInstance("AES").generateKey();
			keyStr = IntegerToXXX.parseByte2HexStr(key.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keyStr;
	}

	public static void main(String[] args) {
		try {
			
				
			long uid = 10000001;
			int money=2;
			
			System.out.println(AES.getMD5Str(uid+""+money+"zjd.com"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	/**
	 * 加密
	 */
	public static String encryption(String str) {
		byte[] output = null;
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			output = cipher.doFinal(str.getBytes());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return IntegerToXXX.parseByte2HexStr(output);
	}

	/**
	 * 解密
	 */
	public static String decryption(String str) {
		byte[] output = null;

		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			output = cipher.doFinal(IntegerToXXX.parseHexStr2Byte(str));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(output);
	}

}
