package com.hotent.bo.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.bo.persistence.model.BOEnt;

/**
 * 
 * <pre> 
 * 描述：业务对象定义 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BOEntDao extends Dao<String, BOEnt> {
	
	/**
	 * 根据名称获取实体对象。
	 * @param name
	 * @return
	 */
	BOEnt getByName(String name);
	
	
	/**
	 * 根据名字获取引用表单的个数。
	 * @param name
	 * @return
	 */
	Integer getRefCountByName(String name);
	
	

}
