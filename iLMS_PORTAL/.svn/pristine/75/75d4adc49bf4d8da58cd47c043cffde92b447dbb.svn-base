package com.hotent.sys.persistence.manager;

import java.util.List;
import java.util.Map;



import com.alibaba.fastjson.JSONObject;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.model.SysTransDef;

public interface SysTransDefManager extends Manager<String, SysTransDef>{

	/**
	 * 生成ztree格式的json数组
	 * 
	 * @param list
	 * @return List<JSONObject>
	 * @exception
	 * @since 1.0.0
	 */
	 List<JSONObject> treeListJson(List<SysTransDef> list,
			String authorId);

	List<Map<String, Object>> excuteSelectSql(
			SysTransDef sysTransDef, String authorId);

	void excuteUpdateSql(SysTransDef sysTransDef, IUser author,
			IUser targetPerson, String selectedItem);

	String getLogContent(SysTransDef sysTransDef,
			String authorId, String targetPersonId, String selectedItem);

	void save(SysTransDef sysTransDef);

}