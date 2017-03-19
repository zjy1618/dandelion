package com.zjy.dandelion.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhuangjy on 16/8/30.
 */
public class Md5Util {
    public Md5Util() {
    }

    public static String encrypt(String plaintext) {
        if(plaintext == null) {
            return null;
        } else {
            MessageDigest messageDigest = null;

            try {
                messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.reset();
                messageDigest.update(plaintext.getBytes("UTF-8"));
            } catch (NoSuchAlgorithmException var8) {
                System.out.println("NoSuchAlgorithmException caught!");
            } catch (UnsupportedEncodingException var9) {
                var9.printStackTrace();
            }

            byte[] byteArray = messageDigest.digest();
            StringBuffer md5StrBuff = new StringBuffer();
            byte[] var4 = byteArray;
            int var5 = byteArray.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                byte element = var4[var6];
                if(Integer.toHexString(255 & element).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(255 & element));
                } else {
                    md5StrBuff.append(Integer.toHexString(255 & element));
                }
            }

            return md5StrBuff.toString();
        }
    }
}
