package com.hotent.base.db.table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hotent.base.api.db.IDbType;
import com.hotent.base.api.db.IDialect;
import com.hotent.base.db.mybatis.Dialect;

public class BaseDbType implements IDbType {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	// JdbcTemplate
	protected JdbcTemplate jdbcTemplate;
	// 方言
	protected Dialect dialect;

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void setDialect(IDialect dialect) {
		this.dialect = (Dialect) dialect;
	}
}
