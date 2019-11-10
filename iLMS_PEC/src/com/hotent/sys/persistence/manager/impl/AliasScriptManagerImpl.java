package com.hotent.sys.persistence.manager.impl;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.AliasScriptDao;
import com.hotent.sys.persistence.manager.AliasScriptManager;
import com.hotent.sys.persistence.model.AliasScript;
 

@Service("aliasScriptManager")
public class AliasScriptManagerImpl extends AbstractManagerImpl<String, AliasScript> implements AliasScriptManager {

	@Resource
	private AliasScriptDao aliasScriptDao;
	
	@Override
	protected Dao<String, AliasScript> getDao() {
		return aliasScriptDao;
	}
	
	@Override
	public AliasScript getByAlias(String alias) {
		return aliasScriptDao.getByAlias(alias);
	}

	@Override
	public JSONArray getMethodsByClassName(String calssName)  throws Exception {
		JSONArray jarray = new JSONArray(); 
		Class<?> t = Class.forName(calssName);
		Method[] methods = t.getDeclaredMethods();
		for(Method method : methods){
			String returnType = method.getReturnType().getCanonicalName();
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
				jaryPara.add(new JSONObject().accumulate("paraName", paraName)
											 .accumulate("paraType", paraType)
											 .accumulate("paraDesc", ""));
			}
			jobMethod.accumulate("returnType", returnType)
					 .accumulate("methodName", method.getName())
					 .accumulate("para", jaryPara);
			jarray.add(jobMethod);
		}
		return jarray;
	}

}
