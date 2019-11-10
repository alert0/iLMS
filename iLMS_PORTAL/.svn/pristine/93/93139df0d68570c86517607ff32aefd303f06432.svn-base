package com.hotent.bo.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bo.persistence.dao.BOEntRelDao;
import com.hotent.bo.persistence.model.BOEnt;
import com.hotent.bo.persistence.model.BOEntRel;

/**
 * 
 * <pre> 
 * 描述：BO 应用定义 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BOEntRelDaoImpl extends MyBatisDaoImpl<String, BOEntRel> implements BOEntRelDao{

    @Override
    public String getNamespace() {
        return BOEntRel.class.getName();
    }
	
    
    @Override
	public List<BOEntRel> getByDefId(String defId) {
		List<BOEntRel> list=this.getByKey("getByDefId", defId);
		return list;
	}


	@Override
	public void removeByDefId(String defId) {
		this.deleteByKey("removeByDefId", defId);
	}


	@Override
	public List<BOEntRel> getByEntId(String entId) {
		List<BOEntRel> list=this.getByKey("getByEntId", entId);
		return list;
	}
}

