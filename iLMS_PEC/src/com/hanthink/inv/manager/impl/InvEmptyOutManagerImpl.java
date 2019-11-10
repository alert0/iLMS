package com.hanthink.inv.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.inv.dao.InvEmptyOutDao;
import com.hanthink.inv.manager.InvEmptyOutManager;
import com.hanthink.inv.model.InvEmptyOutModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:空容器出库指示业务实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月18日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("emptyOut")
public class InvEmptyOutManagerImpl extends AbstractManagerImpl<String, InvEmptyOutModel>
						implements InvEmptyOutManager{
	
	@Resource
	private InvEmptyOutDao emptyOutDao;
	
	@Override
	protected Dao<String, InvEmptyOutModel> getDao() {
		return emptyOutDao;
	}
	
	/**
	 * 空容器出库指示分页查询业务层接口
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月18日
	 */
	@Override
	public PageList<InvEmptyOutModel> queryEmptyOutForPage(InvEmptyOutModel model, Page page) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		List<InvEmptyOutModel> list = emptyOutDao.queryEmptyOutForPage(model,page);
		
		return new PageList<InvEmptyOutModel>(list);
	}
	/**
	 * 空容器出库指示数据Excel导出业务实现方法
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月18日
	 */
	@Override
	public List<InvEmptyOutModel> queryForExcelExport(InvEmptyOutModel model) throws Exception {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			
			List<InvEmptyOutModel> list = emptyOutDao.queryForExcelExport(model);
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	@Override
	public Integer makeEmptyContainer() throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		paramMap.put("resultCode", "0");
		return emptyOutDao.makeEmptyContainer(paramMap);
	}
}
