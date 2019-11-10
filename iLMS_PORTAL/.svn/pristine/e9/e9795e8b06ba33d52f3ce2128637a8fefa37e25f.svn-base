package com.hanthink.mp.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;

/**
 * 根据表名实现的类
 */

import com.hanthink.mp.dao.MpAdjSupFeedbackDao;
import com.hanthink.mp.manager.MpAdjSupFeedbackManager;
import com.hanthink.mp.model.MpAdjSupFeedbackModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：供应商能力反馈 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpAdjSupFeedbackManager")
public class MpAdjSupFeedbackManagerImpl extends AbstractManagerImpl<String, MpAdjSupFeedbackModel> implements MpAdjSupFeedbackManager{
	@Resource
	MpAdjSupFeedbackDao mpAdjSupFeedbackDao;	
	
	@Override
	protected Dao<String, MpAdjSupFeedbackModel> getDao() {
		return mpAdjSupFeedbackDao;
	}
	
	/**
	 * 分页查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	 @Override
	    public PageList<MpAdjSupFeedbackModel> queryMpAdjSupFeedbackForPage(MpAdjSupFeedbackModel model, DefaultPage p) {
	        return (PageList<MpAdjSupFeedbackModel>) mpAdjSupFeedbackDao.queryMpAdjSupFeedbackForPage(model, p);
	    }

	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<MpAdjSupFeedbackModel> queryMpAdjSupFeedbackByKey(MpAdjSupFeedbackModel model) {
		return mpAdjSupFeedbackDao.queryMpAdjSupFeedbackByKey(model);
	}

	/**
	 * 提交数据并记录日志
	 * @param demoModel
	 * @param ipAddr
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:52
	 */
	@Override
	public void getCommit(String ipAddr,MpAdjSupFeedbackModel model){
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("供应商提交反馈");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_MP_ADJ_SUP_FEEDBACK");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(model.getAryIds());
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		
		mpAdjSupFeedbackDao.getCommit(model);
		
	}
}
