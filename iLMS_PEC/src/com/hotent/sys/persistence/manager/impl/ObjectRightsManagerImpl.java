package com.hotent.sys.persistence.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
 
import com.hotent.sys.api.curuser.CurrentUserService;
import com.hotent.sys.api.permission.IPermission;
import com.hotent.sys.persistence.dao.ObjectRightsDao;
import com.hotent.sys.persistence.manager.ObjectRightsManager;
import com.hotent.sys.persistence.model.ObjectRights;
 

/**
 * 
 * <pre>
 * 描述：对象权限 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh zhuang
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-04-16 14:49:38
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("objectRightsManager")
public class ObjectRightsManagerImpl extends
		AbstractManagerImpl<String, ObjectRights> implements
		ObjectRightsManager {
	@Resource
	ObjectRightsDao objectRightsDao;
	@Autowired(required = false)
	CurrentUserService currentUserService;

	@Override
	protected Dao<String, ObjectRights> getDao() {
		return objectRightsDao;
	}

	/**
	 * 
	 */
	@Override
	public Map<String, String> getRights(String objId, String objType,String beanId) {
		Map<String, List<ObjectRights>> rightsMap = new HashMap<String, List<ObjectRights>>();
		List<ObjectRights> list = objectRightsDao.getByObjIdAndObjType(objId,objType);
		
		for (ObjectRights rights : list) {
			String ownerType = rights.getOwnerType();
			List<ObjectRights> rightsList = rightsMap.get(ownerType);
			if (BeanUtils.isEmpty(rightsList)){
				rightsList = new ArrayList<ObjectRights>();
				rightsList.add(rights);
				rightsMap.put(ownerType, rightsList);
			}
			else{
				rightsList.add(rights);
			}
		}

		// 整理需要对象
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		List<IPermission> keyList = currentUserService.getCurUserServiceList(beanId);

		for (IPermission permission : keyList) {
			String type=permission.getType();
			List<ObjectRights> rightsList = rightsMap.get(type);
			map.put(type, coverList2Json(rightsList));
		}
		return map;
	}

	/**
	 * 将权限列表转换成为map对象。
	 * 
	 * @param list
	 * @return
	 */
	private String coverList2Json(List<ObjectRights> list) {
		if (BeanUtils.isEmpty(list))
			return "";
		JSONArray jarray = new JSONArray();

		for (ObjectRights r : list) {
			JSONObject jobject = new JSONObject();
			jobject.accumulate("ownerId", r.getOwnerId()).accumulate("ownerName", r.getOwnerName());
			jarray.add(jobject);
		}
		return jarray.toString();
	}

	@Override
	public void saveRights(String objId, String objType, String[] owner, String[] ownerType) {
		objectRightsDao.delByObjIdAndobjType(objId, objType);
		List<ObjectRights> rightList = coverTypeRights(objId, objType, ownerType, owner);
		if (BeanUtils.isEmpty(rightList)) return;
		for (ObjectRights r : rightList) {
			objectRightsDao.create(r);
		}

	}

	private List<ObjectRights> coverTypeRights(String objId, String objType, String[] ownerType, String[] owner) {

		if (owner == null || owner.length == 0) return null;

		List<ObjectRights> list = new ArrayList<ObjectRights>();
		// 对权限类型进行循环。
		for (int i = 0; i < ownerType.length; i++) {
			String right = ownerType[i];
			String ownerObj = owner[i];
			if (StringUtils.isEmpty(ownerObj)) continue;
			JSONArray jarray = JSONArray.fromObject(ownerObj);
			int size = jarray.size();
			if (size == 0) continue;

			for (int j = 0; j < size; j++) {
				JSONObject jObject = (JSONObject) jarray.get(j);
				String id = jObject.getString("ownerId");
				String name = jObject.getString("ownerName");
				if (StringUtils.isEmpty(id)) continue;
				ObjectRights objRight = setObjectRights(objId, objType, right, id, name);
				list.add(objRight);
			}

		}
		return list;
	}

	private ObjectRights setObjectRights(String objId, String objType,
			String ownerType, String ownerId, String ownerName) {
		ObjectRights rights = new ObjectRights();
		rights.setId(UniqueIdUtil.getSuid());
		rights.setObjType(objType);
		rights.setObjId(objId);
		rights.setOwnerType(ownerType);
		rights.setOwnerId(ownerId);
		rights.setOwnerName(ownerName);
		return rights;
	}

}
