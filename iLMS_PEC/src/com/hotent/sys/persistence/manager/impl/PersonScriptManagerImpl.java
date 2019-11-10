package com.hotent.sys.persistence.manager.impl;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.PersonScriptDao;
import com.hotent.sys.persistence.manager.PersonScriptManager;
import com.hotent.sys.persistence.model.PersonScript;
 

@Service("personScriptManager")
public class PersonScriptManagerImpl extends AbstractManagerImpl<String, PersonScript> implements PersonScriptManager {

	@Resource
	private PersonScriptDao personScriptDao;
	@Override
	protected Dao<String, PersonScript> getDao() {
		return personScriptDao;
	}
	
	/**
	 * 根据类名获取方法
	 * @param className	：类名
	 * @param personScript :若是编辑模式则传进来编辑对象（因为要初始化方法的描叙），若是新增模式，则为null
	 * @return
	 */
	public JSONArray getMethodsByClassName(String className,PersonScript personScript) throws Exception{
		JSONArray jarray = new JSONArray(); 
		Class<?> t = Class.forName(className);
		Method[] methods = t.getDeclaredMethods();
		for(Method method : methods){
			String returnType = method.getReturnType().getCanonicalName();
			//只要返回值为Set<String>的方法
			if(!"Set<String>".equals(returnType)&&!"java.util.Set".equals(returnType))continue;
			Integer modifirer = method.getModifiers();
			//只要public方法
			if(modifirer!=1)continue;
			JSONObject jobMethod = new JSONObject();
			JSONArray jaryPara = new JSONArray();
			Class<?>[] paraArr = method.getParameterTypes();
			for(int i=0;i<paraArr.length;i++){
				Class<?> para = paraArr[i];
				String paraName = "arg" + i;
				String paraType = para.getCanonicalName();
				JSONObject jsonObject = new JSONObject().accumulate("paraName", paraName).accumulate("paraType", paraType).accumulate("paraDesc", "");
				//初始化之前写下来的备注和控件类型
				if(personScript!=null&&personScript.getMethodName().equals(method.getName())){
					JSONArray ja = JSONArray.fromObject(personScript.getArgument());
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
			jobMethod.accumulate("returnType", returnType)
					 .accumulate("methodName", method.getName())
					 .accumulate("para", jaryPara);
			jarray.add(jobMethod);
		}
		return jarray;
	}


}
