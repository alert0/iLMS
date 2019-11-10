package com.hanthink.inv.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvOutLogDao;
import com.hanthink.inv.model.InvOutLogModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

/**
 * <pre> 
 * 功能描述:出库查询持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月11日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class InvOutLogDaoImpl extends MyBatisDaoImpl<String, InvOutLogModel>
						implements InvOutLogDao{

	@Override
	public String getNamespace() {
		return InvOutLogModel.class.getName();
	}
	/**
	 * 分页数据查询持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月11日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvOutLogModel> queryOutLogForPage(InvOutLogModel model, Page page) throws Exception {
		return (List<InvOutLogModel>) this.getList("queryOutLogForPage", model,page);
	}
	/**
	 * 导出查询数据持久层实现方法
	 * @param model
	 * @return
	 * @author zmj
	 * @date 2018年10月11日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvOutLogModel> queryOutLogForExport(InvOutLogModel model) {
		return (List<InvOutLogModel>) this.getList("queryOutLogForPage", model);
	}
	/**
	 * 加载出库类型数据下拉框
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月7日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvOutLogModel> queryOutType(String factoryCode) throws Exception {
		return (List<InvOutLogModel>) this.getList("queryOutType", factoryCode);
	}
}
