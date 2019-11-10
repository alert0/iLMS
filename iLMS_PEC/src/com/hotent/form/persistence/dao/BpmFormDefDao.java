package com.hotent.form.persistence.dao;
import java.util.List;
import java.util.Map;

import com.hotent.base.db.api.Dao;
import com.hotent.form.persistence.model.BpmFormDef;

/**
 * 
 * <pre> 
 * 描述：bpm_form_def DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:苗继方
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-03-17 10:10:05
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmFormDefDao extends Dao<String, BpmFormDef> {
 
	/**
	 * 通过表单ID删除 表单BO关联表中的记录
	 * @param formId
	 */
	void deleteBpmFormBo(String formId);
	
	/**
	 * 创建表单BO关联记录
	 * @param boDefId
	 * @param formId
	 */
	void createBpmFormBo(String boDefId,String formId);

	
	/**
	 * 通过表单ID获取关联的BODefID
	 * @param formId
	 * @return
	 */
	List<String> getBODefIdByFormId(String formId);
	
	/**
	 * 根据formId获取bocode 列表。
	 * @param formId
	 * @return
	 */
	List<String> getBOCodeByFormId(String formId);

	/**
	 * 根据BODefId 获取相关的表单定义。
	 * @param code
	 * @return
	 */
	List<BpmFormDef> getByBODefId(String BODefId);
	
	/**
	 * 根据formKey 获取表单定义。
	 * @param formKey
	 * @return
	 */
	BpmFormDef getByKey(String formKey);

	/**
	 * 更新表单元素书的意见定义。
	 * @param id
	 * @param opinionJson
	 */
	void updateOpinionConf(String id, String opinionJson);
	
	/**
	 * 根据表单ID获取bo实体信息数据。
	 * 获取实体名称和实例关系。
	 * @param formId
	 * @return
	 */
	List<Map<String,String>> getEntInfoByFormId(String formId);
	
	/**
	 * 根据实体名获取表单列表。
	 * @param name
	 * @return
	 */
	List<BpmFormDef> getByEntName(String name);
	
	/**
	 * 根据表单获取表单元数据key。
	 * @param formKey
	 * @return
	 */
	String getMetaKeyByFormKey(String formKey);
	
	/**
	 * 根据entId 获取关联的表单定义。
	 * @param entId
	 * @return
	 */
	List<BpmFormDef> getByEntId(String entId); 
}
