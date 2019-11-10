package com.hanthink.pup.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.pup.dao.PupLockPlanDao;
import com.hanthink.pup.model.PupLockPlanModel;
import com.hanthink.pup.model.PupLockPlanPageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
/**
 * <pre> 
 * 功能描述:锁定计划维护持久层业务实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月7日下午10:10:22
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PupLockPlanDaoImpl extends MyBatisDaoImpl<String, PupLockPlanModel>
					implements PupLockPlanDao{
	@Override
	public String getNamespace() {
		return PupLockPlanModel.class.getName();
	}
	/**
	 * 锁定取货计划维护查询持久层实现方法
	 * @param model 查询参数
	 * @param page 页面参数
	 * @return 与查询结果匹配的锁定计划数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@Override
	public List<PupLockPlanModel> queryLockPlanModelsForPage(PupLockPlanPageModel model,Page page) throws Exception {
		return this.getByKey("queryLockPlanModelsForPage", model,page);
	}
	/**
	 * 锁定取货计划维护数据删除持久层实现方法
	 * @param ids 一个/多个数据id
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@Override
	public void deleteLockPlanByIds(String[] ids) throws Exception {
		this.deleteByKey("deleteLockPlanByIds", ids);
	}
	/**
	 * 锁定取货计划维护数据导出查询持久层实现方法
	 * @param model 请求参数
	 * @return 与查询结果匹配的计划数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	@Override
	public List<PupLockPlanModel> queryLockPlanForExport(PupLockPlanPageModel model) throws Exception {
		return this.getByKey("queryLockPlanModelsForPage", model);
	}
	/**
	 * 锁定取货计划维护数据导入持久层实现方法
	 * @param file Excel文件
	 * @param ipAddr 用户IP地址
	 * @return 操作结果集
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月26日
	 */
	@Override
	public void insertExcelDataToTale(List<PupLockPlanModel> importList) throws Exception {
		this.insertBatchByKey("insertExcelDataToTale", importList);
	}

}
