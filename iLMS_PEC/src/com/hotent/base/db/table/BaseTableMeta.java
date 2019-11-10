package com.hotent.base.db.table;


/**
 * 数据表元数据抽象类。 用于读取数据库表的元数据信息。 1.查询数据库中的表列表。 2.取得表的详细数据。
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
public abstract class BaseTableMeta  extends BaseDbType implements ITableMeta {
	public abstract String getAllTableSql();
}