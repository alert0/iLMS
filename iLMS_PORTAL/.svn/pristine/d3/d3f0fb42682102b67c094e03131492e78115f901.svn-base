package com.hanthink.pup.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.dao.PupPlanDao;
import com.hanthink.pup.model.PupPlanModel;
import com.hanthink.pup.model.PupPlanPageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
/**
 * <pre> 
 * 功能描述:取货计划查询持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月29日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PupPlanDaoImpl extends MyBatisDaoImpl<String, PupPlanModel>
						implements PupPlanDao{

	@Override
	public String getNamespace() {
		return PupPlanModel.class.getName();
	}
	/**
	 * 取货计划查询业务持久层实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public List<PupPlanModel> queryPlanForPage(PupPlanPageModel model, Page page) throws Exception {
		return this.getByKey("queryPlanForPage", model, page);
	}
	/**
	 * 数据字典项下载状态加载持久层实现方法
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DictVO> getDownloadStatus(Map<String, Object> paramMap) throws Exception {
		return this.getList("queryDownloadStatus", paramMap);
	}
	/**
	 * 单条/批量删除数据业务持久层实现方法
	 * @param ids
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月30日
	 */
	@Override
	public void deletePlansById(String[] ids) throws Exception {
		this.deleteByKey("deletePlansById",ids);
	}

}
