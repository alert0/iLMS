package com.hotent.bo.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bo.persistence.model.BODef;

/**
 * 
 * <pre> 
 * 描述：xbo_def DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BODefDao extends Dao<String, BODef> {
	
	/**
	 * 根据别名获取定义对象。
	 * @param alias
	 * @return
	 */
	BODef getByAlias(String alias);
	
	/**
	 * 判断定义是否可编辑（就是判断有没有绑定表单）
	 * @param id
	 * @return 
	 * boolean
	 * @exception 
	 * @since  1.0.0
	 */
	boolean isEditable(String id);
	
	
	/**
	 * 根据表单key获取bo对象列表。
	 * @param formKey
	 * @return
	 */
	List<BODef> getByFormKey(String formKey);
	
	 
}
