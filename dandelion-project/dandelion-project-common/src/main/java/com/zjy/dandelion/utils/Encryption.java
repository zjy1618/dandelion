package com.zjy.dandelion.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.apache.commons.codec.CharEncoding.UTF_8;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString;
import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;
import static org.apache.commons.codec.binary.StringUtils.newStringUtf8;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 *
 */
public class Encryption {

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String sha1(String str) {
		String s = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.update(str.getBytes());
			byte[] bytes = messageDigest.digest();
			int len = bytes.length;
			StringBuilder buf = new StringBuilder(len * 2);
			// 把密文转换成十六进制的字符串形式
			for (int j = 0; j < len; j++) {
				buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
				buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
			}
			s = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String encrypt(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes(UTF_8));
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

	public static String urlEncode(String url) {
		return isNotBlank(url) ? encodeBase64URLSafeString(getBytesUtf8(url))
				: url;
	}

	public static String urlDecode(String url) {
		return isNotBlank(url) ? newStringUtf8(decodeBase64(getBytesUtf8(url)))
				: url;
	}

	/**
	 * 混淆
	 * 
	 * @param mobile
	 * @return
	 */
	public static String obfuscated(String str, int prefix, int suffix) {
		if (isNotBlank(str) && prefix >= 0 && suffix >= 0
				&& str.length() > (prefix + suffix)) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < str.length(); i++) {
				if (i < prefix || (str.length() - i) <= suffix) {
					sb.append(str.charAt(i));
				} else {
					sb.append('*');
				}
			}
			str = sb.toString();
		}
		return str;
	}

	/**
	 * 混淆姓名
	 * 
	 * @param str
	 * @return
	 */
	public static String obfuscatedName(String str) {
		if (isNotBlank(str)) {
			if (str.length() == 2) {
				str = "*" + str.substring(1);
			} else if (str.length() > 2) {
				str = Encryption.obfuscated(str, 1, 1);
			}
		}
		return str;
	}

	public static String obfuscatedMobile(String str) {
		if (isNotBlank(str)) {
			StringBuilder sb = new StringBuilder(str);
			if (sb.length() > 7) {
				sb = sb.replace(3, 7, "****");
			}
			str = sb.toString();
		}
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(Encryption.encrypt(Encryption.encrypt("123456") + "20130806102030"));
	}
}
