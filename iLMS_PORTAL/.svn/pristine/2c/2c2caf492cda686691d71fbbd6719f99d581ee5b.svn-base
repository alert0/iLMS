package com.hotent.sys.persistence.manager.impl;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.ConditionScriptDao;
import com.hotent.sys.persistence.manager.ConditionScriptManager;
import com.hotent.sys.persistence.model.ConditionScript;


@Service("conditionScriptManager")
public class ConditionScriptManagerImpl extends AbstractManagerImpl<String, ConditionScript> implements ConditionScriptManager {

	@Resource
	private ConditionScriptDao conditionScriptDao;

	@Override
	protected Dao<String, ConditionScript> getDao() {
		return conditionScriptDao;
	}

	@Override
	public JSONArray getMethodsByClassName(String className, ConditionScript conditionScript,Integer type) throws Exception {
		JSONArray jarray = new JSONArray();
		Class<?> t = Class.forName(className);
		Method[] methods = t.getDeclaredMethods();
		for (Method method : methods) {
			String returnType = method.getReturnType().getCanonicalName();
			// 只要返回值为boolean的方法
			if (type==1&& (!"boolean".equals(returnType) && !"java.lang.Boolean".equals(returnType) )) continue;
			
			if(type==2&&!"java.util.Set".equals(returnType)) continue; 
			
			Integer modifirer = method.getModifiers();
			// 只要public方法
			if (modifirer != 1) continue;
			
			JSONObject jobMethod = new JSONObject();
			JSONArray jaryPara = new JSONArray();
			Class<?>[] paraArr = method.getParameterTypes();
			for (int i = 0; i < paraArr.length; i++) {
				Class<?> para = paraArr[i];
				String paraName = "arg" + i;
				String paraType = para.getCanonicalName();
				JSONObject jsonObject = new JSONObject().accumulate("paraName", paraName).accumulate("paraType", paraType).accumulate("paraDesc", "");

				//初始化之前写下来的备注和控件类型
				if(conditionScript!=null&&conditionScript.getMethodName().equals(method.getName())&&StringUtil.isNotEmpty(conditionScript.getArgument())){
					JSONArray ja = JSONArray.fromObject(conditionScript.getArgument());
					for(int j=0;j<ja.size();j++){
						JSONObject jo =ja.getJSONObject(j);
						if(jo.getString("paraName").equals(paraName)){
							jsonObject.remove("paraDesc");
							jsonObject.accumulate("paraDesc", jo.getString("paraDesc"));
							jsonObject.remove("paraCt");
							jsonObject.accumulate("paraCt", jo.getString("paraCt"));
						}
					}
				}
				jaryPara.add(jsonObject);
			}
			jobMethod.accumulate("returnType", returnType).accumulate("methodName", method.getName()).accumulate("para", jaryPara);
			jarray.add(jobMethod);
		}
		return jarray;
	}

}
