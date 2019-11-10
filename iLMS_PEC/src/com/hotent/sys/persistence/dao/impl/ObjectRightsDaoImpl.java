package com.hotent.sys.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.ObjectRightsDao;
import com.hotent.sys.persistence.model.ObjectRights;


/**
 * 
 * <pre>
 * 描述：对象权限 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh zhuang
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-04-16 14:49:38
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class ObjectRightsDaoImpl extends MyBatisDaoImpl<String, ObjectRights>
		implements ObjectRightsDao {

	@Override
	public String getNamespace() {
		return ObjectRights.class.getName();
	}

	@Override
	public List<ObjectRights> getByObjIdAndObjType(String objId, String objType) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("objId", objId);
		params.put("objType", objType);
		return this.getByKey("getByObjIdAndObjType", params);
	}

	@Override
	public void delByObjIdAndobjType(String objId, String objType) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("objId", objId);
		params.put("objType", objType);
		this.deleteByKey("delByObjIdAndobjType", params);
	}

}
