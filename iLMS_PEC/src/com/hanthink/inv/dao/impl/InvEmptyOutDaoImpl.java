package com.hanthink.inv.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvEmptyOutDao;
import com.hanthink.inv.model.InvEmptyOutModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
/**
 * <pre> 
 * 功能描述:空容器出库指示持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月18日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class InvEmptyOutDaoImpl extends MyBatisDaoImpl<String, InvEmptyOutModel>
						implements InvEmptyOutDao{

	@Override
	public String getNamespace() {
		return InvEmptyOutModel.class.getName();
	}
	/**
	 * 空容器出库指示分页查询持久实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月18日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvEmptyOutModel> queryEmptyOutForPage(InvEmptyOutModel model, Page page) throws Exception {
		return (List<InvEmptyOutModel>) this.getList("queryForPage", model,page);
	}
	/**
	 * 出库指示数据Excel导出查询持久层实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月18日
	 */
	@SuppressWarnings("unchecked")
	@Override 
	public List<InvEmptyOutModel> queryForExcelExport(InvEmptyOutModel model) throws Exception {
		return (List<InvEmptyOutModel>) this.getList("queryForPage", model);
	}
	@Override
	public Integer makeEmptyContainer(Map<String, String> paramMap) throws Exception {
		this.sqlSessionTemplate.selectOne("makeEmptyContainer", paramMap);
		return Integer.parseInt(paramMap.get("resultCode"));
	}

}
