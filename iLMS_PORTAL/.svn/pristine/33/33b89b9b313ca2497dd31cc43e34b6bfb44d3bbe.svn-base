package com.hotent.base.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.base.persistence.model.RelResource;

/**
 * 
 * <pre> 
 * 描述：关联资源 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 11:40:20
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface RelResourceManager extends Manager<String, RelResource>{
	/**
	 * 根据资源ID获得其拥有的URL
	 * @return
	 */
	List<RelResource> getByResourceId(String resId);
	
	/**
	 * 根据资源ID删除关联资源。
	 * @param resId
	 */
	void removeByResId(String resId);
}
