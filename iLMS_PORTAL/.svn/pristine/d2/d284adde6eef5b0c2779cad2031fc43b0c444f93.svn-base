package com.hanthink.pup.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.pup.dao.PupPlanDao;
import com.hanthink.pup.manager.PupPlanManager;
import com.hanthink.pup.model.PupPlanModel;
import com.hanthink.pup.model.PupPlanPageModel;
import com.hanthink.pup.util.PupUtil;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:取货计划查询业务层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月29日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("planQuery")
public class PupPlanManagerImpl extends AbstractManagerImpl<String, PupPlanModel>
				implements PupPlanManager{
	@Resource
	private PupPlanDao planDao;
	@Override
	protected Dao<String, PupPlanModel> getDao() {
		return planDao;
	}
	/**
	 * 取货计划查询业务实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public PageList<PupPlanModel> queryPlanForPage(PupPlanPageModel model, Page page)throws Exception {
		List<PupPlanModel> list = new ArrayList<PupPlanModel>();
		
		if(!PupUtil.isAllFieldNull(model)) {
			String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
			model.setFactoryCode(factoryCode);
			list = planDao.queryPlanForPage(model,page);
		}
		return new PageList<PupPlanModel>(list);
	}
	/**
	 * 数据字项下载状态加载业务层接口
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public List<DictVO> getDownloadStatus() throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return planDao.getDownloadStatus(paramMap);
	}
	/**
	 * 单条/批量删除数据业务接口实现
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public void deletePlansById(String[] ids) throws Exception {
		if(ids.length < 1) {
			throw new Exception("请选择需要删除的数据");
		}
		planDao.deletePlansById(ids);
	}

	
}
