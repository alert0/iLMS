package com.hotent.base.db.table.util;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hotent.base.api.db.IViewOperator;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.db.mybatis.Dialect;
import com.hotent.base.db.table.BaseTableMeta;
import com.hotent.base.db.table.factory.DatabaseFactory;

public class MetaDataUtil {

	/**
	 * 获取一个BaseTableMeta，已经设置好方言和jdbcTemplate
	 * 
	 * @param dbType
	 * @return BaseTableMeta
	 * @exception
	 * @since 1.0.0
	 */
	public static BaseTableMeta getBaseTableMetaAfterSetDT(String dbType) {
		JdbcTemplate jdbcTemplate=AppUtil.getBean(JdbcTemplate.class);
		BaseTableMeta baseTableMeta = null;
		try {
			baseTableMeta = DatabaseFactory.getTableMetaByDbType(dbType);
			
			Dialect dialect = DatabaseFactory.getDialect(dbType);

			/**
			 * 配置文件中的对象
			 * 
			 * @Resource JdbcTemplate jdbcTemplate;
			 */
			baseTableMeta.setJdbcTemplate(jdbcTemplate);

			baseTableMeta.setDialect(dialect);
		} catch (Exception e) {
		}
		return baseTableMeta;
	}
	
	/**
	 * 获取一个IViewOperator，已经设置好方言和jdbcTemplate
	 * 
	 * @param dbType
	 * @return IViewOperator
	 * @exception
	 * @since 1.0.0
	 */
	public static IViewOperator getIViewOperatorAfterSetDT(String dbType) {
		JdbcTemplate jdbcTemplate=AppUtil.getBean(JdbcTemplate.class);
		IViewOperator iViewOperator = null;
		try {
			iViewOperator = DatabaseFactory.getViewOperator(dbType);

			Dialect dialect = DatabaseFactory.getDialect(dbType);

			/**
			 * 配置文件中的对象
			 * 
			 * @Resource JdbcTemplate jdbcTemplate;
			 */
			iViewOperator.setJdbcTemplate(jdbcTemplate);

			iViewOperator.setDialect(dialect);
		} catch (Exception e) {
		}

		return iViewOperator;
	}
}
