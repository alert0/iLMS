package com.hanthink.util.excel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.StringUtils;

/**
 * @ClassName: ExcelCheckUtil
 * @Description: 公用excel导入校验函数
 * @author dtp
 * @date 2018年12月9日
 */
public class ExcelCheckUtil {

	/**
     * 利用正则表达式判断字符串是否非数字
     * 
     * @param str
     * @return
     */
    public static boolean isNotNumeric(String str) {
    	if(!StringUtils.isNullOrEmpty(str)) {
    		Pattern pattern = Pattern.compile("[0-9]*");
    		Matcher isNum = pattern.matcher(str);
    		if (!isNum.matches()) {
    			return true;
    		}
    	}
        return false;
    }
    
    /**
     * 利用正则表达式判断字符串是否数字
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
