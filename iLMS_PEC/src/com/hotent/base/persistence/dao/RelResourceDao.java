package com.hotent.base.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.base.persistence.model.RelResource;

/**
 * 
 * <pre> 
 * 描述：关联资源 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:40:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface RelResourceDao extends Dao<String, RelResource> {
	
	List<RelResource> getByResourceId(String resId);
	
	/**
	 * 根据资源id删除关联资源。
	 * @param resId
	 */
	void removeByResId(String resId) ;
}
