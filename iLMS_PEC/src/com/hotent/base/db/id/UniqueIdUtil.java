package com.hotent.base.db.id;

import com.hotent.base.db.api.IdGenerator;

/**
 * 唯一ID获取类。
 * <pre> 
 * 描述：用于产生唯一ID，需要配置spring文件，具体参考
 * x5-base-db.xml文件。
 * 构建组：x5-base-db
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-2-13-上午11:56:23
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class UniqueIdUtil {

	private static IdGenerator idGenerator;
	
	public void setIdGenerator(IdGenerator idGenerator_){
		idGenerator=idGenerator_;
	}
	
	/**
	 * 
	 * 获取long型的ID.
	 * @return 
	 * Long
	 * @exception 
	 * @since  1.0.0
	 */
	public static Long getUId(){
		return idGenerator.getUId();
	}
	
    /**
     * 获取字符型的ID
     * @return 
     */
    public static String getSuid(){
    	return idGenerator.getSuid();
    }
}
