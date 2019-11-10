package com.hotent.form.persistence.dao;

import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.form.persistence.model.BpmFormField;

public interface BpmFormFieldDao extends Dao<String, BpmFormField> {
	/**
	 * 根据外键获取子表明细列表
	 * @param formId
	 * @return
	 */
	List<BpmFormField> getByFormId(String formId);
	
	/**
	 * 通过表单ID获取字段列表(不包含属于分组的字段)
	 * @param formId
	 * @return
	 */
	List<BpmFormField> getOnlyByFormId(String formId);
	
	
	
	/**
	 * 通过分组ID获取字段列表
	 * @param groupId
	 * @return
	 */
	List<BpmFormField> getByGroupId(String groupId);
	
	/**
	 * 根据外键删除子表记录
	 * @param formId
	 * @return
	 */
	void delByMainId(String formId);
	
	/**
	 * 根据formId 获取列表。
	 * @param formId
	 * @return
	 */
	List<BpmFormField> getExtByFormId(String formId);
	
	/**
	 * 根据业务对象id, 获取主对象的字段信息
	 * @param boDefId
	 * @return
	 */
	List<BpmFormField> getByboDefId(String boDefId);
	
	/**
	 * 根据 表单id 和  业务对象id, 获取主对象的字段信息
	 * @param formId
	 * @param boDefId
	 * @return
	 */
	List<BpmFormField> getByFormIdAndBoDefId(String formId,String boDefId);
	
	void removeByAttrId(String attrId);
	
}
