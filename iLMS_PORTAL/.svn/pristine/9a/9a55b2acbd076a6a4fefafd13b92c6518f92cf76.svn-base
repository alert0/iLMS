package com.hotent.bpmx.persistence.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmApprovalItemDao;
import com.hotent.bpmx.persistence.model.BpmApprovalItem;

/**
 * 
 * <pre> 
 * 描述：常用语管理 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-11-03 10:56:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BpmApprovalItemDaoImpl extends MyBatisDaoImpl<String, BpmApprovalItem> implements BpmApprovalItemDao{

    @Override
    public String getNamespace() {
        return BpmApprovalItem.class.getName();
    }

	@Override
	public List<BpmApprovalItem> getByDefKeyAndUserAndSys(String defKey,String curUserId) {
		Map param = new HashMap();
		param.put("defKey", defKey);
		param.put("curUserId", curUserId);
		return this.sqlSessionTemplate.selectList("getByDefKeyAndUserAndSys", param);
	}

	@Override
	public List<BpmApprovalItem> getItemByType(Short type) {
		Map param = new HashMap<String, Short>();
		param.put("type",type);
		return  this.sqlSessionTemplate.selectList("getItemByType", type);
	}
	
	
}

