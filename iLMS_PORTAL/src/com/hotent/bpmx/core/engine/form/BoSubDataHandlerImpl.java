package com.hotent.bpmx.core.engine.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.datasource.DataSourceUtil;
import com.hotent.bo.api.instance.BoSubDataHandler;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmSubTableRight;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.persistence.manager.BpmBusLinkManager;
import com.hotent.sys.util.ContextUtil;

/**
 * 子表数据读取。
 * 
 * @author ray
 *
 */
public class BoSubDataHandlerImpl implements BoSubDataHandler {

	@Resource
	JdbcTemplate jdbcTemplate;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	GroovyScriptEngine groovyScriptEngine;
	@Resource
	BpmBusLinkManager bpmBusLinkManager;

	@Override
	public List<Map<String, Object>> getSubDataByFk(BaseBoEnt boEnt, Object fkValue) {
		// 获取子表权限
		String defId = (String) ContextThreadUtil.getCommuVar("defId","");
		String nodeId = (String) ContextThreadUtil.getCommuVar("nodeId","");
		String parentDefKey = (String) ContextThreadUtil.getCommuVar("parentDefKey", BpmConstants.LOCAL);

		BpmSubTableRight bpmSubTableRight = getSubTableRight(defId, nodeId, parentDefKey, boEnt);

		// 拼装sql
		String sql = "";
		if (boEnt.getType().equals(BaseBoDef.BOENT_RELATION.MANY_TO_MANY)) {
			sql = "select A.* from " + boEnt.getTableName() + " A , xbo_data_rel B where " + " B.SUB_BO_NAME = '" + boEnt.getName() + "' AND A." + boEnt.getPkKey() + "=B.FK_  AND B.PK_=?";
		} else {
			String fk = boEnt.getFk();
			if(StringUtil.isEmpty(fk)){
				throw new RuntimeException("通过添加外部表构建业务对象时必须指定外键");
			}
			sql = "select * from " + boEnt.getTableName() + " A  where A." + fk + "=?";
		}
		sql = handleRight(bpmSubTableRight, fkValue, sql);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if( boEnt.isExternal() ){
			//外部表数据
			try {
				list = DataSourceUtil.getJdbcTempByDsAlias(boEnt.getDsName()).queryForList(sql, fkValue);
			} catch (Exception e) {
				throw new RuntimeException("操作外部表：" + boEnt.getDsName() + " 中的 " + boEnt.getDesc() + " 出错："+e.getMessage(), e);
			}
		
		}else{
			list = jdbcTemplate.queryForList(sql, fkValue);
		}
		
		return list;
	}

	/**
	 * 获取权限。
	 * 
	 * @param defId
	 * @param nodeId
	 * @param parentDefKey
	 * @param boEnt
	 * @return
	 */
	private BpmSubTableRight getSubTableRight(String defId, String nodeId, String parentDefKey, BaseBoEnt boEnt) {
		if(StringUtil.isEmpty(nodeId)) return null;
		
   		BpmNodeDef nodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		UserTaskNodeDef utnd = (UserTaskNodeDef) nodeDef;
		BpmSubTableRight bpmSubTableRight = null;
		List<BpmSubTableRight> list=utnd.getBpmSubTableRightByParentDefKey(parentDefKey);
		for (BpmSubTableRight bsr : list) {
			if (bsr.getTableName().equals(boEnt.getName())) {
				bpmSubTableRight = bsr;
				break;
			}
		}
		return bpmSubTableRight;
	}

	private String handleRight(BpmSubTableRight right, Object fkValue, String sql) {
		if (right == null)
			return sql;
		if (right.getRightType().equals("script")) {
			String str = groovyScriptEngine.executeString(right.getScript(), new HashMap<String, Object>());
			sql += " and " + str;
		} else if (right.getRightType().equals("curUser")) {
			sql = "select a.* from ("+sql+") a , bpm_bus_link b where a.ID_ = B.businesskey_str_ and  B.start_id_="+ContextUtil.getCurrentUserId();
		}else if(right.getRightType().equals("curOrg")){
			sql = "select a.* from ("+sql+") a , bpm_bus_link b where a.ID_ = B.businesskey_str_ and  B.start_group_id_="+ContextUtil.getCurrentGroupId();
		}
		return sql;
	}
}
