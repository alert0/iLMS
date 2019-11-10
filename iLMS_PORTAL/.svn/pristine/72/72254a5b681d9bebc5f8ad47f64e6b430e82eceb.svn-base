package com.hotent.sys.persistence.manager.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysDataSourceDefDao;
import com.hotent.sys.persistence.manager.SysDataSourceDefManager;
import com.hotent.sys.persistence.model.SysDataSourceDef;

@Service("sysDataSourceDefManager")
public class SysDataSourceDefManagerImpl extends AbstractManagerImpl<String, SysDataSourceDef> implements SysDataSourceDefManager {
	@Resource
	SysDataSourceDefDao sysDataSourceDefDao;

	@Override
	protected Dao<String, SysDataSourceDef> getDao() {
		return sysDataSourceDefDao;
	}

	/**
	 * 获取类名为classPath的所有有setting的字段
	 * 
	 * @param classPath
	 * @return List&lt;Field>
	 * @exception
	 * @since 1.0.0
	 */
	private List<Field> getHasSetterFields(String classPath) {
		List<Field> fields = new ArrayList<Field>();

		try {
			Class<?> _class = Class.forName(classPath);
			Field[] fs=_class.getDeclaredFields();
			for (Field field : fs) {
				if (checkHasSetter(_class, field)) {
					fields.add(field);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return fields;
	}
	
	/**
	 * 
	 * 获取这个classPath的拥有setter的字段
	 * @param classPath
	 * @return 
	 * JSONArray
	 * @exception 
	 * @since  1.0.0
	 */
	@Override
	public JSONArray getHasSetterFieldsJsonArray(String classPath) {
		JSONArray jsonArray = new JSONArray();
		for (Field field : getHasSetterFields(classPath)) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("name", field.getName());
			jsonObject.accumulate("comment", field.getName());
			jsonObject.accumulate("type", field.getType().getName());
			jsonObject.accumulate("baseAttr", "0");
			jsonArray.add(jsonObject);
		}

//		System.out.println(jsonArray.toString());

		return jsonArray;
	}

	/**
	 * 检查资格字段在_class类中是否有setter
	 * 
	 * @param _class
	 * @param field
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	private boolean checkHasSetter(Class<?> _class, Field field) {
		boolean b = false;
		
		for (Method method : _class.getMethods()) {
			if (!method.getName().startsWith("set")) continue;

			if (method.getName().replace("set", "").toUpperCase().equals(field.getName().toUpperCase())) {
				b = true;
			}
		}

		return b;
	}

	public static void main(String[] args) {
		SysDataSourceDefManagerImpl impl = new SysDataSourceDefManagerImpl();
		List<Field> fields = impl.getHasSetterFieldsJsonArray("org.apache.commons.dbcp.BasicDataSource");
//		List<Field> fields = impl.getHasSetterFieldsJsonArray("org.logicalcobwebs.proxool.ProxoolDataSource");
	}

}
