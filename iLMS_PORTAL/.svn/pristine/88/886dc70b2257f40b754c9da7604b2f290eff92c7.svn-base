package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;
import javax.swing.text.StyledEditorKit.BoldAction;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysDataGridFieldRightDao;
import com.hotent.sys.persistence.model.SysDataGridFieldRight;
import com.hotent.sys.persistence.manager.SysDataGridFieldRightManager;

/**
 * 
 * <pre> 
 * 描述：sys_datagrid_field_right 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-27 16:04:13
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysDataGridFieldRightManager")
public class SysDataGridFieldRightManagerImpl extends AbstractManagerImpl<String, SysDataGridFieldRight> implements SysDataGridFieldRightManager{
	@Resource
	SysDataGridFieldRightDao sysDataGridFieldRightDao;
	@Override
	protected Dao<String, SysDataGridFieldRight> getDao() {
		return sysDataGridFieldRightDao;
	}
	public boolean removeByGridId(String gridId)
	{
		return sysDataGridFieldRightDao.removeByGridId(gridId);
	}
	/**
	 * 获取默认的权限数据。
	 * 
	 * @param field 字段名称
	 * @param memo 字段标题
	 * @param type
	 * @return
	 */
	public JSONObject getEmptyItemJson(String field, String memo)
	{
		String defJson = "[{type:'everyone',id:'', fullname:''}]";
		JSONObject json = new JSONObject();
		json.element("title", field);
		json.element("memo", memo);
		json.element("read", defJson);
		json.element("write", defJson);
		json.element("required",defJson );
		return json;
	}
	
	public SysDataGridFieldRight getJsonObjectConvertRight(String gridId,Object obj)
	{
		JSONObject jsonObj = (JSONObject) obj;
		String name = (String) jsonObj.get("title");
		String fieldId= (String) jsonObj.get("formFieldId");
		String json = obj.toString();
		SysDataGridFieldRight right=new SysDataGridFieldRight();
		right.setRightId(UniqueIdUtil.getSuid());
		right.setGridId(gridId);
		right.setFieldId(fieldId);
		right.setPermission(json);
		return right;
	}
}
