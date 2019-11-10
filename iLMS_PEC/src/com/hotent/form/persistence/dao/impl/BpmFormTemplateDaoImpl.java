package com.hotent.form.persistence.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.form.persistence.dao.BpmFormTemplateDao;
import com.hotent.form.persistence.model.BpmFormTemplate;


@Repository

public class BpmFormTemplateDaoImpl extends MyBatisDaoImpl<String, BpmFormTemplate> implements BpmFormTemplateDao{

    @Override
    public String getNamespace() {
        return BpmFormTemplate.class.getName();
    }
    /**
	 * 删除所有的数据
	 */
    @Override
	public void delSystem(){
		this.deleteByKey("delSystem", null);
	}
    
    /**
	 * 根据模版类型取得模版列表。
	 * @param type
	 * @return
	 */
    @Override
    public List<BpmFormTemplate> getTemplateType(String type){
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("templateType",type);
		//宏模板中包含PC端宏模板和手机端宏模板
		if("macro".equals(type)){
			List<BpmFormTemplate> templates = new ArrayList<BpmFormTemplate>();
			templates.addAll(this.getByKey("getTemplateType", params));
			params.put("templateType","mobileMacro");
			templates.addAll(this.getByKey("getTemplateType", params));
			return templates;
		}else{
			return this.getByKey("getTemplateType", params);
		}
    }
	
	/**
	 * 根据别名获取模版。
	 * @param alias
	 * @return
	 */
    @Override
	public BpmFormTemplate getByTemplateAlias(String alias){
		return this.getUnique("getByTemplateAlias", alias);
	}
	
	/**
	 * 获取模版是否有数据。
	 * @return
	 */
    @Override
	public Integer getHasData(){
		return (Integer)this.getOne("getHasData", null);
	}
}

