package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.ObjectRights;
 

/**
 * 
 * <pre> 
 * 描述：对象权限 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh zhuang
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-04-16 14:49:38
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface ObjectRightsDao extends Dao<String, ObjectRights> {

	List<ObjectRights> getByObjIdAndObjType(String objId, String objType);

	void delByObjIdAndobjType(String objId, String objType);
}
