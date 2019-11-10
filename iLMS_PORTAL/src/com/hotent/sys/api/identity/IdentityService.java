package com.hotent.sys.api.identity;

import java.text.ParseException;

/**
 * 流水号服务。
 * <pre> 
 * 构建组：x5-sys-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014年8月24日-上午10:42:57
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface IdentityService {
	
	/**
	 * 根据别名获取下一个流水号。
	 * @param alias
	 * @return 
	 * String
	 * @throws ParseException 
	 */
	String genNextNo(String alias) ;
	
	/**
	 * 
	 * 根据别名取得预览的流水号。
	 * @param alias
	 * @return String
	 */
	String getPreviewNo(String alias);

}
