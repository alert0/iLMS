package com.hotent.form.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.form.persistence.model.BpmFormTemplate;


public interface BpmFormTemplateDao extends Dao<String, BpmFormTemplate> {
	/**
	 * 删除所有的数据
	 */
	public void delSystem();
	
	/**
	 * 根据别名获取模版。
	 * @param alias
	 * @return
	 */
	public BpmFormTemplate getByTemplateAlias(String alias);
	
	/**
	 * 获取模版是否有数据。
	 * @return
	 */
	public Integer getHasData();
	/**
	 * 根据模版类型取得模版列表。
	 * @param type
	 * @return
	 */
	public List<BpmFormTemplate> getTemplateType(String type);
}
