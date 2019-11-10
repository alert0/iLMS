package com.hotent.form.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.form.persistence.model.BpmFormTemplate;

public interface BpmFormTemplateManager extends Manager<String, BpmFormTemplate>{
	
	/**
	 * 根据模版别名取得模版。
	 * @param alias
	 * @return
	 */
	public BpmFormTemplate getByTemplateAlias(String alias);
	
	/**
	 * 获取所有的系统原始模板
	 * @return
	 * @throws Exception 
	 */
	public void initAllTemplate() throws Exception;
	
	/**
	 * 当模版数据为空时，将form目录下的模版添加到数据库中。
	 */
	public void init()  throws Exception;
	
	
	/**
	 * 检查模板别名是否唯一
	 * @param alias
	 * @return
	 */
	public boolean isExistAlias(String alias);
	
	
	
	
	/**
	 * 将用户自定义模板备份
	 * @param id
	 */
	public void backUpTemplate(String id);
	
	/**
	 * 根据模版类型取得模版列表。
	 * @param type
	 * @return
	 */
	public List<BpmFormTemplate> getTemplateType(String type);
	
	/**
	 * 获取主表模版
	 * @return
	 */
	public List<BpmFormTemplate> getAllMainTableTemplate(boolean isPC) ;
	
	/**
	 * 获取子表模版。
	 * @return
	 */
	public List<BpmFormTemplate> getAllSubTableTemplate(boolean isPC) ;

	/**
	 * 获取宏模版。
	 * @return
	 */
	public List<BpmFormTemplate> getAllMacroTemplate() ;
	
	/**
	 * 获取表管理模版。
	 * @return
	 */
	public List<BpmFormTemplate> getAllTableManageTemplate() ;
	/**
	 * 获取列表模版。
	 * @return
	 */
	public List< BpmFormTemplate> getListTemplate() ;
	
	/**
	 * 获取明细模版。
	 * @return
	 */
	public List< BpmFormTemplate> getDetailTemplate() ;
	
	/**
	 * 获取数据模版。
	 * @return
	 */
	public List< BpmFormTemplate> getDataTemplate() ;
	/**
	 * 获取查询数据模版。
	 * @return
	 */
	public List< BpmFormTemplate> getQueryDataTemplate() ;
}
