package com.hotent.form.persistence.manager;


import java.util.Map;

import net.sf.json.JSONObject;

import com.hotent.base.manager.api.Manager;
import com.hotent.form.persistence.model.BpmDataTemplate;

/**
 * 
 * <pre> 
 * 描述：业务数据模板 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-03-15 14:52:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BpmDataTemplateManager extends Manager<String, BpmDataTemplate>{
	
	/**
	 * 根据formKey 获取业务数据模板相关信息
	 * @param formKey
	 * @return
	 */
	JSONObject getByFormKey(String formKey);
	
	/**
	 * 根据formKey 获取业务数据模板
	 * @param formKey
	 * @return
	 */
	BpmDataTemplate getTemplateByFormKey(String formKey);
	/**
	 * 根据formKey 获取业务数据模板导出信息
	 * @param formKey
	 * @return
	 */
	BpmDataTemplate getExportDisplay(String formKey);
    
	/**
	 * 保存业务数据模板
	 * @param bpmDataTemplate
	 * @param resetTemp
	 * @throws Exception
	 */
	void save(BpmDataTemplate bpmDataTemplate, boolean resetTemp) throws Exception;

	String getDisplay(String alias, Map<String, Object> params,
			Map<String, Object> queryParams) throws Exception;
	/**
	 * 获取表单
	 * @param formKey
	 * @return
	 */
	Map<String,Object> getFormByFormKey(String formKey);
	
	/**
	 * 保存bo业务对象数据
	 * @param jsonObject
	 * @param boAlias 
	 */
	void boSave(com.alibaba.fastjson.JSONObject jsonObject, String boAlias);
	
	/**
	 * 删除
	 * @param ids
	 * @param boAlias
	 */
	void boDel(String[] ids, String boAlias);

	/**
	 * 获取列表显示sql语句
	 * @param filterField
	 * @param filterKey
	 * @param dsName
	 * @param tableName
	 * @param param
	 * @return
	 */
	String getShowSql(String filterField, String filterKey, String dsName,
			String tableName, Map<String, Object> param);
}
