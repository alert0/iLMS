package com.hotent.bo.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bo.persistence.dao.BODefDao;
import com.hotent.bo.persistence.model.BODef;

/**
 * 
 * <pre> 
 * 描述：xbo_def DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BODefDaoImpl extends MyBatisDaoImpl<String, BODef> implements BODefDao{

    @Override
    public String getNamespace() {
        return BODef.class.getName();
    }

	@Override
	public BODef getByAlias(String alias) {
		return this.getUnique("getByAlias", alias);
	}
	
	@Override
	public boolean isEditable(String id){
		return this.getOne("isEditable", this.buildMap("id", id)).toString().equals("0");
	}

	@Override
	public List<BODef> getByFormKey(String formKey) {
		return this.getByKey("getByFormKey", formKey);
	}
}

