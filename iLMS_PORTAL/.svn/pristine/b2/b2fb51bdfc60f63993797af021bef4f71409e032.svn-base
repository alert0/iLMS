package com.hotent.sys.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.MsgTemplate;


public interface MsgTemplateDao extends Dao<String, MsgTemplate> {
	/**
	 * 根据类型获取默认的模版
	 * @param typeKey
	 * @return  MsgTemplate
	 */
	public MsgTemplate getDefault(String typeKey);

	/**
	 * 根据键获取模版。
	 * @param key
	 * @return MsgTemplate
	 */
	public MsgTemplate getByKey(String key);
}
