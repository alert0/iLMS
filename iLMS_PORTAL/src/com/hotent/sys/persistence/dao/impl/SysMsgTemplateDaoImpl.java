package com.hotent.sys.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysMsgTemplateDao;
import com.hotent.sys.persistence.model.SysMsgTemplate;


/**
 * 
 * <pre> 
 * 描述：消息模版 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-03-10 09:20:11
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysMsgTemplateDaoImpl extends MyBatisDaoImpl<String, SysMsgTemplate> implements SysMsgTemplateDao{

    @Override
    public String getNamespace() {
        return SysMsgTemplate.class.getName();
    }

	@Override
	public void setNotDefaultByType(String key) {
		this.updateByKey("updateNotDefaultByType", key);
	}

	@Override
	public void setDefault(String id) {
		this.updateByKey("updateDefault", id);
	}

	@Override
	public List<SysMsgTemplate> getByIds(List<String> ids) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		return this.getByKey("getByIds", params);
	}

	@Override
	public void delByTypeKey(List<String> typeKeyList) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("typeKeyList", typeKeyList);
		this.deleteByKey("delByTypeKey", params);
	}

	@Override
	public boolean isExistByKeyAndTypeKey(String key, String typeKey) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("typeKey", typeKey);
		params.put("key", key);
		SysMsgTemplate SysMsgTemplate =(SysMsgTemplate) this.getOne("isExistByKeyAndTypeKey", params);
		return BeanUtils.isNotEmpty(SysMsgTemplate);
	}
	
}

