package com.hanthink.jit.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.jit.dao.JitVehScrapDao;
import com.hanthink.jit.manager.JitVehScrapManager;
import com.hanthink.jit.model.JitVehScrapModel;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：报废信息查询 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("jitVehScrapManager")
public class JitVehScrapManagerImpl extends AbstractManagerImpl<String, JitVehScrapModel> implements JitVehScrapManager{
	@Resource
	JitVehScrapDao jitVehScrapDao;
	@Override
	protected Dao<String, JitVehScrapModel> getDao() {
		return jitVehScrapDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<JitVehScrapModel> queryJitVehScrapForPage(JitVehScrapModel model, DefaultPage p) {
		return jitVehScrapDao.queryJitVehScrapForPage(model, p);
	}

	/**
	 * 报废信息查询
	 */
	@Override
	public List<JitVehScrapModel> queryJitVehScrapByKey(JitVehScrapModel model) {
		return jitVehScrapDao.queryJitVehScrapByKey(model);
	}

	/**
	 * @Description: 手工补看板 
	 * @param: @param list
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月28日
	 */
	@Override
	public void updateAdjustKb(List<JitVehScrapModel> list, String ipAddr) {
		for (JitVehScrapModel jitVehScrapModel : list) {
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("手工补看板");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
			logVO.setTableName("MM_JIT_VEH_SCRAP");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ORDER_NO");
			pkColumnVO.setColumnVal(jitVehScrapModel.getOrderNo());
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			
			jitVehScrapDao.updateAdjustKb(jitVehScrapModel);
		}
	}
	
}
