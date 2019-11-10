package com.hanthink.jiso.manager.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.jiso.dao.JisoSupKBDao;
import com.hanthink.jiso.manager.JisoSupKBManager;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * @Desc		: 厂外同步出货地切换看板
 * @FileName	: JisoSupKBManagerImpl.java
 * @CreateOn	: 2018年11月21日 上午9:48:45
 * @author 		: ZUOSL
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 
 */
@Service("JisoSupKBManager")
public class JisoSupKBManagerImpl implements JisoSupKBManager {
	
	@Resource
	private JisoSupKBDao dao;

	/**
	 * 查询厂外同步出货地切换看板数据信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月21日 上午10:11:18
	 */
	@Override
	public PageList<Map<String, Object>> queryJisoSupKB(Map<String, Object> param, Page page) {
		return dao.queryJisoSupKB(param, page);
	}

	/**
	 * 更新派车处理信息
	 * @param param
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月21日 上午10:23:16
	 */
	@Override
	public int updateDealData(Map<String, Object> param) {
		return dao.updateDealData(param);
	}

}
