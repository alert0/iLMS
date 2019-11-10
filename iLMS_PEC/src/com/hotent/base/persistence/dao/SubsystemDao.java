package com.hotent.base.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.base.persistence.model.Subsystem;

/**
 * 
 * <pre> 
 * 描述：子系统定义 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:40:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SubsystemDao extends Dao<String, Subsystem> {
	
	/**
	 * 判断别名是否存在
	 * @param subsystem
	 * @return
	 */
	boolean isExist(Subsystem subsystem);
	
	/**
	 * 获取子系统列表。
	 * @return
	 */
	List<Subsystem> getList();
	
	/**
	 * 更新为默认。
	 */
	void updNoDefault();
	
	
	/**
	 * 根据用户获取子系统列表。
	 * @param userId
	 * @return
	 */
	List<Subsystem> getSystemByUser(String userId);
}
