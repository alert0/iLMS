package com.hotent.sys.persistence.dao;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.model.SysSignRight;
import com.hotent.sys.persistence.model.SysTransDef;
/**
 *<pre>
 * 对象功能:sys_trans_def Dao类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:liyj
 * 创建时间:2015-04-16 11:15:55
 *</pre>
 */
@Repository
public interface SysTransDefDao extends Dao<String, SysTransDef>
{

	
	
}