package com.hotent.org.persistence.dao;
import com.hotent.base.db.api.Dao;
import com.hotent.org.persistence.model.SysDemension;

/**
 * 
 * <pre> 
 * 描述：维度管理 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-19 15:30:09
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysDemensionDao extends Dao<String, SysDemension> {
	
	/**
	 * 根据Code取定义对象。
	 * @param code
	 * @return
	 */
	SysDemension getByCode(String code);
	
	/**
	 * 获取默认维度
	 * @return
	 */
	SysDemension getDefaultDemension();
	
	/**
	 * 设置所有维度为非默认
	 * @param id
	 */
	public void setNotDefaultDemension();
}
