package com.hotent.sys.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.ResUrl;

public interface ResUrlManager extends Manager<String, ResUrl>{
	
	/**
	 * 通过资源 ID删除非默认url
	 * @param id
	 */
	void deleteByResId(String resId);
	
	List<ResUrl> getByResId(String resId);
	
}
