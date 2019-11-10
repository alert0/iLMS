package com.hotent.org.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.persistence.dao.SysUserRelDao;
import com.hotent.org.persistence.model.SysUserRel;

/**
 * 
 * <pre> 
 * 描述：用户关系 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liygui
 * 邮箱:liygui@jee-soft.cn
 * 日期:2017-06-12 09:21:48
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysUserRelDaoImpl extends MyBatisDaoImpl<String, SysUserRel> implements SysUserRelDao{

    @Override
    public String getNamespace() {
        return SysUserRel.class.getName();
    }

	@Override
	public List<SysUserRel> getByTypeId(String typeId) {
		return this.getByKey("getByTypeId", typeId);
	}

	@Override
	public SysUserRel getByUserIdAndParentId(String typeId, String userId,
			String parentId) {
		Map<String, Object> param = buildMap("typeId", typeId);
		param.put("userId", userId);
		param.put("parentId", parentId);
		return this.getUnique("getByUserIdAndParentId", param);
	}

	@Override
	public List<SysUserRel> getSuperUser(String userId,String level, String typeId) {
		Map<String, Object> param = buildMap("typeId", typeId);
		param.put("userId", userId);
		param.put("level", level);
		return this.getByKey("getSuperUser", param);
	}
	
}

