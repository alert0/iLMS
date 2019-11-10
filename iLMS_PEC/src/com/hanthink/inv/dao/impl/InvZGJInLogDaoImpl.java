package com.hanthink.inv.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvZGJInLogDao;
import com.hanthink.inv.model.InvInLogModel;
import com.hanthink.inv.model.InvZGJInLogModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

/**
 * @ClassName: InvZGJInLogDaoImpl
 * @Description: 支给件入库查询DaoImpl
 * @author dtp
 * @date 2019年4月10日
 */
@Repository
public class InvZGJInLogDaoImpl extends MyBatisDaoImpl<String, InvZGJInLogModel> implements 
		InvZGJInLogDao{

	@Override
	public String getNamespace() {
		return InvZGJInLogModel.class.getName();
	}

	/**
	 * 入库数据分页查询持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @author zmj
	 * @date 2018年10月9日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvInLogModel> queryInLogForPage(InvInLogModel model, Page page) {
		return (List<InvInLogModel>) this.getList("queryInLogForPage", model,page);
	}
	
	/**
	 * 入库数据导出持久层实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvInLogModel> queryInLogForExport(InvInLogModel model) throws Exception {
		return (List<InvInLogModel>) this.getList("queryInLogForPage", model);
	}
}
