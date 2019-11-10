package com.hanthink.inv.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvEmptyDao;
import com.hanthink.inv.model.InvEmptyModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
/**
 * <pre> 
 * 功能描述:空容器查询业务持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月17日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class InvEmptyDaoImpl extends MyBatisDaoImpl<String, InvEmptyModel>
						implements InvEmptyDao{

	@Override
	public String getNamespace() {
		return InvEmptyModel.class.getName();
	}
	/**
	 * 空容器分页查询持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvEmptyModel> queryEmptyForPage(InvEmptyModel model, Page page) throws Exception {
		return (List<InvEmptyModel>) this.getList("queryEmptyForPage", model,page);
	}
	/**
	 * 修改空容器数量持久层实现方法
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@Override
	public void updateForEmpty(InvEmptyModel model) throws Exception {
		this.updateByKey("updateForEmpty", model);
	}

}
