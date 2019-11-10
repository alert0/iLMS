package com.hanthink.mp.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;

/**
 * 根据表名实现的类
 */

import com.hanthink.mp.dao.MpVehPlanDao;
import com.hanthink.mp.manager.MpVehPlanManager;
import com.hanthink.mp.model.MpVehPlanModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：车辆计划 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpVehPlanManager")
public class MpVehPlanManagerImpl extends AbstractManagerImpl<String, MpVehPlanModel> implements MpVehPlanManager{
	@Resource
	MpVehPlanDao mpVehPlanDao;
	@Override
	protected Dao<String, MpVehPlanModel> getDao() {
		return mpVehPlanDao;
	}
	
	 @Override
	    public PageList<MpVehPlanModel> queryMpVehPlanForPage(MpVehPlanModel model, DefaultPage p) {
	        return (PageList<MpVehPlanModel>) mpVehPlanDao.queryMpVehPlanForPage(model, p);
	    }
	
	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月4日 上午11:01:08
	 */
	@Override
	public void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception{
		
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("车辆计划删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_MP_VEH_PLAN");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("SORT_ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		//执行删除
		mpVehPlanDao.deleteByIds(aryIds);
	}

	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<MpVehPlanModel> queryMpVehPlanByKey(MpVehPlanModel model) {
		return mpVehPlanDao.queryMpVehPlanByKey(model);
	}

	/**
	 * 校验批量删除里面是否有已订购数据
	 * @param model
	 * @return
	 */
	@Override
	public Integer queryMpVehPlanCheck(List<String> listIds) {
		return mpVehPlanDao.queryMpVehPlanCheck(listIds);
	}
    
	/**
	 * 直接删除未订购数据
	 * @param model
	 * @return
	 */
	@Override
	public void removeAndLogByCalStatus(String factoryCode, List<String> listIds,String ipAddr) {
		//日志记录
				TableOpeLogVO logVO = new TableOpeLogVO();
				logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
				logVO.setOpeIp(ipAddr); 
				logVO.setFromName("界面更新");
				logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
				logVO.setTableName("MM_MP_VEH_PLAN");
				TableColumnVO pkColumnVO = new TableColumnVO();
				pkColumnVO.setColumnName("SORT_ID");
				/**
				 * 集合转数组
				 */
				String[] aryIds = new String[listIds.size()];
				listIds.toArray(aryIds);
				pkColumnVO.setColumnValArr(aryIds);
				this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
				mpVehPlanDao.removeAndLogByCalStatus(factoryCode);
		
	}

	/**
	 * 查询未订购数据的SortId用于记录日志
	 * @param 
	 * @return
	 */
	@Override
	public List<String> querySortIdAndLogByCalStatus() {
		return mpVehPlanDao.querySortIdAndLogByCalStatus();
	}

	/**
	 * 获取生产计划
	 */
	@Override
	public Integer getVehPlan(String params) {
		return mpVehPlanDao.getVehPlan(params);
	}
	
	
}
