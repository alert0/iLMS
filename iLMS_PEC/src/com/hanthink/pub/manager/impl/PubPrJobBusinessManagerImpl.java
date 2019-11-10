package com.hanthink.pub.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pub.dao.PubPrJobBusinessDao;
import com.hanthink.pub.manager.PubPrJobBusinessManager;
import com.hanthink.pub.model.PubPrJobBusinessModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：打印任务与业务关系配置 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("PubPrJobBusinessManager")
public class PubPrJobBusinessManagerImpl extends AbstractManagerImpl<String, PubPrJobBusinessModel> implements PubPrJobBusinessManager{
	@Resource
	PubPrJobBusinessDao pubPrJobBusinessDao;
	@Override
	protected Dao<String, PubPrJobBusinessModel> getDao() {
		return pubPrJobBusinessDao;
	}
	
	/**
	 * 分页查询
	 */
	 @Override
	    public PageList<PubPrJobBusinessModel> queryPubPrJobBusinessForPage(PubPrJobBusinessModel model, DefaultPage p) {
	        return (PageList<PubPrJobBusinessModel>) pubPrJobBusinessDao.queryPubPrJobBusinessForPage(model, p);
	    }
	
	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param ipAddr
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:52
	 */
	@Override
	public void updateAndLog(PubPrJobBusinessModel PubPrJobBusinessModel, String ipAddr){
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PR_JOB_BUSINESS");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(PubPrJobBusinessModel.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		pubPrJobBusinessDao.update(PubPrJobBusinessModel);
		
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
		logVO.setFromName("打印任务与业务关系配置删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_PR_JOB_BUSINESS");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		//执行删除
		pubPrJobBusinessDao.deleteByIds(aryIds);
	}

}
