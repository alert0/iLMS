package com.hanthink.gps.util;

import java.util.Random;

/**
 * @author Administrator
 *
 */
public class CommUtil {

	
	private final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
		'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
		'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
		'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
		'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
		'Z' };
	
	/**
	 * 生成指定长度的由字母和数字组成的随机字符串
	 * @param length
	 * @return
	 */
	public static String genRandomStr(int length){
		char[] cs = new char[length];
		Random r = new Random();
		for (int i = 0; i < cs.length; i++) {
			cs[i] = digits[r.nextInt(digits.length)];
		}
		return new String(cs);
	}
	
	

}
