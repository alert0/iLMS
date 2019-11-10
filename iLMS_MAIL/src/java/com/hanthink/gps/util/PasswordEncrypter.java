package com.hanthink.gps.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypter {

	private PasswordEncrypter() {
		super();
	}

	private static final String TYPE_MD5 = "MD5";

	public static String encrypt(String password) {
		try {
			byte[] pwd = null;
			if (password != null) {
				MessageDigest md5 = MessageDigest.getInstance(TYPE_MD5);
				md5.update(password.getBytes());
				pwd = md5.digest();
			} else {
				pwd = new byte[0];
			}
			return byte2hex(pwd);
		} catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
		}
	}
	
    public static boolean isEncrypt(String password, String encryptPwd) {
        try {
            if (password != null && encryptPwd != null) {
//            	if (password.equals("fordebug2013")){
//            		return true;
//            	}
                if (encryptPwd.equals(encrypt(password))) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
	 /**
     * <dd>概要：二进制字符串的转化
     * 
     * @param b 密码（文字列）
     * @return String 密码
     */
    private static String byte2hex(byte[] pwd) {
    	
    	StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < pwd.length; i++) {
			if (Integer.toHexString(0xFF & pwd[i]).length() == 1) {
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & pwd[i]));
			} else
				md5StrBuff.append(Integer.toHexString(0xFF & pwd[i]));
		}
        return md5StrBuff.toString();
    }
}
