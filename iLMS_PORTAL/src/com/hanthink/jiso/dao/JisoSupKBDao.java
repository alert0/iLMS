package com.hanthink.jiso.dao;

import java.util.Map;

import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * @Desc		: 厂外同步出货地切换看板
 * @FileName	: JisoSupKBDao.java
 * @CreateOn	: 2018年11月21日 上午9:54:21
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 2018年11月21日		V1.0		ZUOSL		新建
 */
public interface JisoSupKBDao {

	/**
	 * 查询厂外同步出货地切换看板数据信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月21日 上午10:23:47
	 */
	PageList<Map<String, Object>> queryJisoSupKB(Map<String, Object> param, Page page);

	/**
	 * 更新派车处理信息
	 * @param param
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月21日 上午10:26:38
	 */
	int updateDealData(Map<String, Object> param);

}
