package com.hotent.form.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.form.persistence.dao.BpmFormDefDao;
import com.hotent.form.persistence.model.BpmFormDef;

/**
 * 
 * <pre> 
 * 描述：bpm_form_def DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:苗继方
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-03-17 10:10:05
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BpmFormDefDaoImpl extends MyBatisDaoImpl<String, BpmFormDef> implements BpmFormDefDao{

    @Override
    public String getNamespace() {
        return BpmFormDef.class.getName();
    }
	
    public void deleteBpmFormBo(String formId) {
		this.deleteByKey("deleteBpmFormBo", buildMap("formId", formId));
	}

	public void createBpmFormBo(String boDefId, String formId) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("id", UniqueIdUtil.getSuid());
		map.put("formId", formId);
		map.put("boDefId", boDefId);
		this.insertByKey("createBpmFormBo", map);
	}

	@SuppressWarnings("unchecked")
	public List<String> getBODefIdByFormId(String formId) {
		return this.getList("getBODefIdByFormId", buildMap("formId", formId));
	}

	@Override
	public List<BpmFormDef> getByBODefId(String BODefId) {
		return this.getList("getByBODefId", buildMap("BODefId", BODefId));
	}

	@Override
	public BpmFormDef getByKey(String key) {
		return this.getUnique("getByKey", key);
	}

	@Override
	public void updateOpinionConf(String id, String opinionJson) {
		Map<String, Object> map = buildMap("id", id);
		map.put("opinionJson", opinionJson);
		this.updateByKey("updateOpinionConf", map);
	}

	@Override
	public List<String> getBOCodeByFormId(String formId) {
		return this.getList("getBOCodeByFormId", buildMap("formId", formId) );
	}

	@Override
	public List<Map<String, String>> getEntInfoByFormId(String formId) {
		return this.getList("getEntInfoByFormId", buildMap("formId", formId));
	}

	@Override
	public List<BpmFormDef> getByEntName(String name) {
		return this.getList("getByEntName", buildMap("entName", name));
	}

	@Override
	public String getMetaKeyByFormKey(String formKey) {
		String key=(String) this.getOne("getMetaKeyByFormKey", formKey);
		return key;
	}

	@Override
	public List<BpmFormDef> getByEntId(String entId) {
		return this.getByKey("getByEntId", entId);
	}
	
	
}

