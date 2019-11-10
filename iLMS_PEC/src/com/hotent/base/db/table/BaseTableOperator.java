package com.hotent.base.db.table;

import com.hotent.base.api.db.ITableOperator;

/**
 * 操作数据表基类。
 * 
 * <pre>
 * 构建组：x5-base-db
 * 作者：hugh zhuang
 * 邮箱:zhuangxh@jee-soft.cn
 * 日期:2014-01-25-上午11:35:40
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 * 
 */
public abstract class BaseTableOperator extends BaseDbType implements ITableOperator {
	
	protected String replaceLineThrough(String partition){
		return partition.toUpperCase().replaceAll("-", "");
	}

	

}
