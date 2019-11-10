package com.hanthink.pub.manager.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pub.dao.PubSysParamDao;
import com.hanthink.pub.manager.PubSysParamManager;
import com.hanthink.pub.model.PubSysParamModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：剩余量主数据 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("PubSysParamManager")
public class PubSysParamManagerImpl extends AbstractManagerImpl<String, PubSysParamModel> implements PubSysParamManager{
	@Resource
	PubSysParamDao pubSysParamDao;
	@Override
	protected Dao<String, PubSysParamModel> getDao() {
		return pubSysParamDao;
	}
	
	/**
	 * 分页查询
	 */
	 @Override
	    public PageList<PubSysParamModel> queryPubSysParamForPage(PubSysParamModel model, DefaultPage p) {
	        return (PageList<PubSysParamModel>) pubSysParamDao.queryPubSysParamForPage(model, p);
	    }
	
	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param ipAddr
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:52
	 */
	@Override
	public void updateAndLog(PubSysParamModel pubSysParamModel, String ipAddr){
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PUB_SYS_PARAM");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(pubSysParamModel.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		pubSysParamDao.update(pubSysParamModel);
		
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
		logVO.setFromName("零件剩余量主数据删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_PUB_SYS_PARAM");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		//执行删除
		pubSysParamDao.deleteByIds(aryIds);
	}

	/**
	 * 主键冲突
	 */
	@Override
	public Integer selectPrimaryKey(PubSysParamModel pubSysParamModel) {
		return pubSysParamDao.selectPrimaryKey(pubSysParamModel);
	}

	/**
	 * 验证SQL是否正确
	 * @return 
	 */
	@Override
	public List<LinkedHashMap<String, Object>> sheckParamVal(String sql) {
		return pubSysParamDao.sheckParamVal(sql);
	}

	/**
	 * 用户修改
	 */
	@Override
	public void userUpdateAndLog(PubSysParamModel pubSysParamModel, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("用户修改");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PUB_SYS_PARAM");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(pubSysParamModel.getId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		pubSysParamDao.userUpdate(pubSysParamModel);
		
	}

	/**
	 * 验证SQL是否有返回值
	 */
	@Override
	public Integer sheckParamValReturn(String sql) {
		return pubSysParamDao.sheckParamValReturn(sql);
	}

	/**
	 * 根据参数值和参数代码获取校验表达式和校验依据
	 */
	@Override
	public PubSysParamModel getCheckBy(PubSysParamModel model) {
		return pubSysParamDao.getCheckBy(model);
	}

}
