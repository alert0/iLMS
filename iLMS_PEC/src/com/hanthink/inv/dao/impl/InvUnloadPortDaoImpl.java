package com.hanthink.inv.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvUnloadPortDao;
import com.hanthink.inv.model.InvUnloadPortModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
/**
 * <pre> 
 * 功能描述:卸货口管理持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月9日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class InvUnloadPortDaoImpl extends MyBatisDaoImpl<String, InvUnloadPortModel>
						implements InvUnloadPortDao{

	@Override
	public String getNamespace() {
		return InvUnloadPortModel.class.getName();
	}
	/**
	 * 卸货口数据查询持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	@Override
	public List<InvUnloadPortModel> queryUnloadPortForPage(InvUnloadPortModel model, Page page) throws Exception {
		return this.getByKey("queryUnloadPortForPage", model, page);
	}
	/**
	 * 查询数据Excel导出持久层实现方法
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvUnloadPortModel> queryUnloadPortForExport(InvUnloadPortModel model) throws Exception {
		return (List<InvUnloadPortModel>) this.getList("queryUnloadPortForPage", model);
	}
	@Override
	public void removeUnloadPort(String[] ids) throws Exception {
		this.deleteByKey("removeUnloadPort", ids);
	}
	@Override
	public boolean isExistsUnload(InvUnloadPortModel model) throws Exception {
		return this.sqlSessionTemplate.selectOne("isExistsUnload", model);
	}
	@Override
	public boolean isExistsWareCode(InvUnloadPortModel model) throws Exception {
		return this.sqlSessionTemplate.selectOne("isExistsWareCode",model);
	}
	@Override
	public void createUnloadport(InvUnloadPortModel model) throws Exception {
		this.sqlSessionTemplate.insert("createUnloadport", model);
	}
	@Override
	public void updateUnloadPort(InvUnloadPortModel model) throws Exception {
		this.updateByKey("updateUnloadPort", model);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<InvUnloadPortModel> queryWareCodeLsit(String factoryCode) throws Exception {
		return (List<InvUnloadPortModel>) this.getList("queryWareCodeLsit", factoryCode);
	}
	/**
	 * 加载物理卸货口下拉框
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvUnloadPortModel> queryLogicUnload(String factoryCode) throws Exception {
		return (List<InvUnloadPortModel>) this.getList("queryLogicUnload", factoryCode);
	}
	@Override
	public boolean unloadPortIsUsedInLocation(String[] ids) throws Exception {
		return this.sqlSessionTemplate.selectOne("unloadPortIsUsedInLocation", ids);
	}
	@Override
	public boolean isUsedInLocation(InvUnloadPortModel model) throws Exception {
		return this.sqlSessionTemplate.selectOne("isUsedInLocation",model);
	}
}
