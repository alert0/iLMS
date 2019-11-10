package com.hotent.form.persistence.manager.impl;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.Dom4jUtil;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.form.persistence.dao.BpmFormTemplateDao;
import com.hotent.form.persistence.manager.BpmFormTemplateManager;
import com.hotent.form.persistence.model.BpmFormTemplate;

@Service("bpmFormTemplateManager")
public class BpmFormTemplateManagerImpl extends AbstractManagerImpl<String, BpmFormTemplate> implements BpmFormTemplateManager{
	private static Log logger = LogFactory.getLog(BpmFormTemplateManagerImpl.class);
	@Resource
	BpmFormTemplateDao bpmFormTemplateDao;
	@Override
	protected Dao<String, BpmFormTemplate> getDao() {
		return bpmFormTemplateDao;
	}
	/**
	 * 返回模版物理的路径。
	 * @return
	 * @throws Exception 
	 */
	public static  String getFormTemplatePath() throws Exception{
		return FileUtil.getClassesPath() + File.separator + "template" + File.separator +"form" + File.separator;
	}
	
	/**
	 * 根据模版别名取得模版。
	 * @param alias
	 * @return
	 */
	@Override
	public BpmFormTemplate getByTemplateAlias(String alias){
		return bpmFormTemplateDao.getByTemplateAlias(alias);
	}
	
	/**
	 * 获取所有的系统原始模板
	 * @return
	 * @throws Exception 
	 */
	@Override
	public void initAllTemplate() throws Exception{
		bpmFormTemplateDao.delSystem();
		addTemplate();
	}
	
	/**
	 * 当模版数据为空时，将form目录下的模版添加到数据库中。
	 */
	@Override
	public void init()  throws Exception{
		Integer amount=bpmFormTemplateDao.getHasData();
		if(amount==0){
			addTemplate();
		}
	}
	
	/**
	 * 初始化模版，在系统启用的时候进行调用。
	 */
	public static void initTemplate(){
		BpmFormTemplateManager service= (BpmFormTemplateManager) AppUtil.getBean(BpmFormTemplateManager.class);
		try {
			service.init();
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
	}
	/**
	 * 初始化添加form下的模版数据到数据库。
	 */
	private void addTemplate()  throws Exception{
		String templatePath=getFormTemplatePath();
		String xml= FileUtil.readFile(templatePath +"templates.xml");
		Document document=Dom4jUtil.loadXml(xml);
		Element root=document.getRootElement();
		List<Element> list=root.elements();
		for(Element element:list){
			String alias=element.attributeValue("alias");
			String name=element.attributeValue("name");
			String type=element.attributeValue("type");
			String templateDesc=element.attributeValue("templateDesc");
			String macroAlias=element.attributeValue("macroAlias");
			String dir = element.attributeValue("dir");
			
			String fileName=templatePath+dir+File.separator+alias+".ftl";
			String html=FileUtil.readFile(fileName);
		
			BpmFormTemplate bpmFormTemplate=new BpmFormTemplate();
			bpmFormTemplate.setTemplateId(UniqueIdUtil.getSuid());
			bpmFormTemplate.setMacrotemplateAlias(macroAlias);
			bpmFormTemplate.setHtml(html);
			bpmFormTemplate.setTemplateName(name);
			bpmFormTemplate.setAlias(alias);
			bpmFormTemplate.setCanedit(0);
			bpmFormTemplate.setTemplateType(type);
			bpmFormTemplate.setTemplateDesc(templateDesc);
			bpmFormTemplateDao.create(bpmFormTemplate);
		}
	}
	
	/**
	 * 检查模板别名是否唯一
	 * @param alias
	 * @return
	 */
	@Override
	public boolean isExistAlias(String alias){
		List<BpmFormTemplate>list=bpmFormTemplateDao.getAll();
		for(BpmFormTemplate bpmFormTemplate:list){
			if(bpmFormTemplate.getAlias().equals(alias)){
				return true;
			}
		}
		return false;
	}
	
	
	
	
	/**
	 * 将用户自定义模板备份
	 * @param id
	 */
	@Override
	public void backUpTemplate(String id){
		BpmFormTemplate bpmFormTemplate=bpmFormTemplateDao.get(id);
		String alias=bpmFormTemplate.getAlias();
		String name=bpmFormTemplate.getTemplateName();
		String desc=bpmFormTemplate.getTemplateDesc();
		String html=bpmFormTemplate.getHtml();
		String type=bpmFormTemplate.getTemplateType();
		String macroAlias=bpmFormTemplate.getMacrotemplateAlias();
		
		String templatePath = null;
		try {
			templatePath = getFormTemplatePath();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String xmlPath=templatePath +"templates.xml";
		String xml= FileUtil.readFile(xmlPath);
		
		Document document=Dom4jUtil.loadXml(xml);
		Element root=document.getRootElement();
		
		Element e=root.addElement("template");
		e.addAttribute("alias", alias);
		e.addAttribute("name", name);
		e.addAttribute("type", type);
		e.addAttribute("templateDesc", desc);
		e.addAttribute("macroAlias",macroAlias);
		String content=document.asXML();
		
		FileUtil.writeFile(xmlPath, content);
		FileUtil.writeFile(templatePath +alias+".ftl", html);
		
		bpmFormTemplate.setCanedit(0);
		bpmFormTemplateDao.update(bpmFormTemplate);
	}
	
	/**
	 * 根据模版类型取得模版列表。
	 * @param type
	 * @return
	 */
	@Override
	public List<BpmFormTemplate> getTemplateType(String type){
		return bpmFormTemplateDao.getTemplateType(type);
	}
	
	/**
	 * 获取主表模版
	 * @return
	 */
	@Override
	public List<BpmFormTemplate> getAllMainTableTemplate(boolean isPC) {
		return getTemplateType(isPC? BpmFormTemplate.MAIN_TABLE : BpmFormTemplate.MOBILE_MAIN);
	}
	
	/**
	 * 获取子表模版。
	 * @return
	 */
	@Override
	public List<BpmFormTemplate> getAllSubTableTemplate(boolean isPC) {
		return getTemplateType(isPC? BpmFormTemplate.SUB_TABLE : BpmFormTemplate.Mobile_SUB);
	}

	/**
	 * 获取宏模版。
	 * @return
	 */
	@Override
	public List<BpmFormTemplate> getAllMacroTemplate() {
		return getTemplateType(BpmFormTemplate.MACRO);
	}
	
	/**
	 * 获取表管理模版。
	 * @return
	 */
	@Override
	public List<BpmFormTemplate> getAllTableManageTemplate() {
		return getTemplateType(BpmFormTemplate.TABLE_MANAGE);
	}
	
	/**
	 * 获取列表模版。
	 * @return
	 */
	@Override
	public List< BpmFormTemplate> getListTemplate() {
		return getTemplateType(BpmFormTemplate.LIST);
	}
	
	/**
	 * 获取明细模版。
	 * @return
	 */
	public List< BpmFormTemplate> getDetailTemplate() {
		return getTemplateType(BpmFormTemplate.DETAIL);
	}
	
	/**
	 * 获取数据模版。
	 * @return
	 */
	@Override
	public List< BpmFormTemplate> getDataTemplate() {
		return getTemplateType(BpmFormTemplate.DATA_TEMPLATE);
	}
	/**
	 * 获取查询数据模版。
	 * @return
	 */
	@Override
	public List< BpmFormTemplate> getQueryDataTemplate() {
		return getTemplateType(BpmFormTemplate.QUERY_DATA_TEMPLATE);
	}
}
