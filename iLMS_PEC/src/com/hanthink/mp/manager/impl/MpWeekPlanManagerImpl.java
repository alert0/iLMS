package com.hanthink.mp.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.mp.dao.MpWeekPlanDao;
import com.hanthink.mp.manager.MpWeekPlanManager;
import com.hanthink.mp.model.MpWeekPlanModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：周计划维护 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpWeekPlanManager")
public class MpWeekPlanManagerImpl extends AbstractManagerImpl<String, MpWeekPlanModel> implements MpWeekPlanManager{
	@Resource
	MpWeekPlanDao mpWeekPlanDao;
	@Override
	protected Dao<String, MpWeekPlanModel> getDao() {
		return mpWeekPlanDao;
	}
	
	/**
	 * 分页查询
	 */
	 @Override
	    public PageList<MpWeekPlanModel> queryMpWeekPlanForPage(MpWeekPlanModel model, DefaultPage p) {
	        return (PageList<MpWeekPlanModel>) mpWeekPlanDao.queryMpWeekPlanForPage(model, p);
	    }
	
	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param ipAddr
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:52
	 */
	@Override
	public void updateAndLog(MpWeekPlanModel mpWeekPlanModel, String ipAddr){
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_MP_WEEK_PLAN");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(mpWeekPlanModel.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		mpWeekPlanDao.update(mpWeekPlanModel);
		
	}

	/**
	 * 获取年份填充下拉框
	 */
	@Override
	public List<DictVO> getYear() {
		return mpWeekPlanDao.getYear();
	}
	
}
