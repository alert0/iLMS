package com.hotent.sys.persistence.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;




import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.persistence.dao.SysTransDefDao;
import com.hotent.sys.persistence.manager.SysTransDefManager;
import com.hotent.sys.persistence.model.SysTransDef;
import com.hotent.sys.util.ContextUtil;

/**
 * <pre>
 * 对象功能:sys_trans_def Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:liyj
 * 创建时间:2015-04-16 11:15:55
 * </pre>
 */
@Service
public class SysTransDefManagerImpl extends AbstractManagerImpl<String,SysTransDef> implements SysTransDefManager {
	@Resource
	private SysTransDefDao dao;
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private IUserService userService;
	@Resource
	GroovyScriptEngine groovyScriptEngine;
	public static String IDS = "{ids}";
	public static String NAMES = "{names}";
	public static String TARGET_PERSONID = "{targetPersonId}";
	public static String TARGET_PERSONNAME = "{targetPersonName}";
	public static String AUTHOR_ID = "{authorId}";
	public static String AUTHOR_NAME = "{authorName}";

	public SysTransDefManagerImpl() {
	}

	@Override
	protected Dao<String, SysTransDef> getDao() {
		return dao;
	}
	/* (non-Javadoc)
	 * @see com.hotent.sys.persistence.manager.impl.SysTransDefManager#save(com.hotent.sys.persistence.model.SysTransDef)
	 */
	@Override
	public void save(SysTransDef sysTransDef) {
		String id = sysTransDef.getId();
		if (StringUtil.isEmpty(id)) {
			id = UniqueIdUtil.getSuid();
			sysTransDef.setId(id);

			IUser sysUser = ContextUtil.getCurrentUser();
			sysTransDef.setCreator(sysUser.getFullname());
			sysTransDef.setCreatorId(sysUser.getUserId());
			sysTransDef.setCreateTime(new Date());

			this.create(sysTransDef);
		} else {
			this.update(sysTransDef);
		}
	}

	/* (non-Javadoc)
	 * @see com.hotent.sys.persistence.manager.impl.SysTransDefManager#treeListJson(java.util.List, java.lang.String)
	 */
	@Override
	public List<JSONObject> treeListJson(List<SysTransDef> list, String authorId) {
		List<JSONObject> result = new ArrayList<JSONObject>();
		JSONObject jo = new JSONObject();
		jo.put("id", "1");
		jo.put("parentId", "0");
		jo.put("name", "所属权限");
		result.add(jo);
		for (SysTransDef std : list) {
			jo = new JSONObject();
			jo.put("id", std.getId());
			jo.put("parentId", "1");
			List l = null;
			try {
				l = excuteSelectSql(std, authorId);
			} catch (Exception e) {
				continue;
			}
			jo.put("count", l.size());
			jo.put("name", std.getName() + "(" + l.size() + ")");
			if (l.size() <= 0) {// 长度为0就不显示了
				continue;
			}
			result.add(jo);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hotent.sys.persistence.manager.impl.SysTransDefManager#excuteSelectSql(com.hotent.sys.persistence.model.SysTransDef, java.lang.String)
	 */
	@Override
	public List<Map<String,Object>> excuteSelectSql(SysTransDef sysTransDef, String authorId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = groovyScriptEngine.executeString(sysTransDef
				.getSelectSql().replace(AUTHOR_ID, authorId), map);
		if (StringUtil.isEmpty(sql)) {
			return null;
		}
		List<Map<String,Object>> l = jdbcTemplate.queryForList(sql);
		return l;
	}

	/* (non-Javadoc)
	 * @see com.hotent.sys.persistence.manager.impl.SysTransDefManager#excuteUpdateSql(com.hotent.sys.persistence.model.SysTransDef, com.hotent.org.api.model.IUser, com.hotent.org.api.model.IUser, java.lang.String)
	 */
	@Override
	public void excuteUpdateSql(SysTransDef sysTransDef, IUser author, IUser targetPerson, String selectedItem) {
		JSONArray selectedItemJa = JSONArray.parseArray(selectedItem);
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		for (int i = 0; i < selectedItemJa.size(); i++) {
			if (ids.length() != 0) {
				ids.append(",");
				names.append(",");
			}
			JSONObject jo = selectedItemJa.getJSONObject(i);
			ids.append(jo.getString("id"));
			names.append(jo.getString("name"));
		}

		// 替代sql
		String updateSql = sysTransDef
				.getUpdateSql()
				.replace(AUTHOR_ID, author.getUserId().toString())
				.replace(TARGET_PERSONID,
						targetPerson.getUserId().toString())
				.replace(IDS, ids.toString())
				.replace(NAMES, names.toString())
				.replace(AUTHOR_NAME, author.getFullname())
				.replace(TARGET_PERSONNAME, targetPerson.getFullname())
				.replace(AUTHOR_ID, author.getUserId().toString())
				.replace(TARGET_PERSONID,
						targetPerson.getUserId().toString())
				.replace(IDS, ids.toString())
				.replace(AUTHOR_NAME, author.getFullname())
				.replace(TARGET_PERSONNAME, targetPerson.getFullname());

		String sql = groovyScriptEngine.executeString(updateSql,
				new HashMap<String, Object>());
		if (StringUtil.isNotEmpty(sql)) {
			jdbcTemplate.execute(sql);
		}
	}

	/* (non-Javadoc)
	 * @see com.hotent.sys.persistence.manager.impl.SysTransDefManager#getLogContent(com.hotent.sys.persistence.model.SysTransDef, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getLogContent(SysTransDef sysTransDef, String authorId,
			String targetPersonId, String selectedItem) {
		// 开始写日志
		String content = sysTransDef.getLogContent();
		IUser author = userService.getUserById(authorId);
		IUser target = userService.getUserById(targetPersonId);

		JSONArray selectedItemJa = JSONArray.parseArray(selectedItem);
		StringBuffer names = new StringBuffer();
		for (int i = 0; i < selectedItemJa.size(); i++) {
			if (names.length() != 0) {
				names.append(",");
			}
			JSONObject jo = selectedItemJa.getJSONObject(i);
			names.append(jo.getString("name"));
		}

		content = content
				.replace(SysTransDefManagerImpl.NAMES, names.toString())
				.replace(SysTransDefManagerImpl.AUTHOR_NAME, author.getFullname())
				.replace(SysTransDefManagerImpl.TARGET_PERSONNAME,
						target.getFullname());

		return content;
	}


}
