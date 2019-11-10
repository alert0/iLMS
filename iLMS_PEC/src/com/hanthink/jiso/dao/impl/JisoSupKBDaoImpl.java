package com.hanthink.jiso.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.jiso.dao.JisoSupKBDao;
import com.hanthink.jiso.model.JisoOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * @Desc		: 厂外同步出货地切换看板
 * @FileName	: JisoSupKBDaoImpl.java
 * @CreateOn	: 2018年11月21日 上午9:55:38
 * @author 		: ZUOSL
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 
 */
@SuppressWarnings("rawtypes")
@Repository
public class JisoSupKBDaoImpl extends MyBatisDaoImpl<String, JisoOrderModel> implements JisoSupKBDao {

	@Override
	public String getNamespace() {
		return "com.hanthink.jiso.jisoSupKB";
	}

	/**
	 * 查询厂外同步出货地切换看板数据信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月21日 上午10:27:34
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryJisoSupKB(Map<String, Object> param, Page page) {
		return this.getByKey("queryJisoSupKB", param, page);
	}

	/**
	 * 更新派车处理信息
	 * @param param
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月21日 上午10:27:50
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateDealData(Map<String, Object> param) {
		return this.updateByKey("updateDealData", param);
	}

	/**
	 * 查询出货地切换派车汇总
	 * @param model
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JisoOrderModel> queryJisoSupCarPage(JisoOrderModel model, DefaultPage page) {
		return (PageList<JisoOrderModel>) this.getList("queryJisoSupCarPage", model, page);
	}

	/**
	 * @Description:  更新车牌 
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月20日
	 */
	@Override
	public void updateJisoSupCar(JisoOrderModel model) {
		this.updateByKey("updateJisoSupCar", model);
	}
	

}
