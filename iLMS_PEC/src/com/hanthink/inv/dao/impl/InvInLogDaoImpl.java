package com.hanthink.inv.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvInLogDao;
import com.hanthink.inv.model.InvInLogModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

/**
 * <pre> 
 * 功能描述:入库管理业务持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月9日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class InvInLogDaoImpl extends MyBatisDaoImpl<String, InvInLogModel>
							implements InvInLogDao{

	@Override
	public String getNamespace() {
		return InvInLogModel.class.getName();
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
