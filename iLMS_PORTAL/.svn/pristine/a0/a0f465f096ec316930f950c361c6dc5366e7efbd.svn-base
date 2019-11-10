package com.hotent.bo.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bo.persistence.dao.BoAttributeDao;
import com.hotent.bo.persistence.model.BoAttribute;

/**
 * 
 * <pre> 
 * 描述：业务实体定义属性 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BoAttributeDaoImpl extends MyBatisDaoImpl<String, BoAttribute> implements BoAttributeDao{

    @Override
    public String getNamespace() {
        return BoAttribute.class.getName();
    }

	@Override
	public List<BoAttribute> getByEntId(String entId) {
		List<BoAttribute> list=this.getByKey("getByEntId", entId);
		return list;
	}

	@Override
	public void removeByEntId(String entId) {
		this.deleteByKey("removeByEntId", entId);
	} 
	
}

