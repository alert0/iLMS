package com.hanthink.sys.manager.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.sys.dao.SysPdaUserManagerDao;
import com.hanthink.sys.manager.SysPdaUserManagerManager;
import com.hanthink.sys.model.SysPdaUserManagerModel;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：PDA用户信息管理 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("SysPdaUserManagerManager")
public class SysPdaUserManagerManagerImpl extends AbstractManagerImpl<String, SysPdaUserManagerModel> implements SysPdaUserManagerManager{
	@Resource
	SysPdaUserManagerDao sysPdaUserManagerDao;
	@Override
	protected Dao<String, SysPdaUserManagerModel> getDao() {
		return sysPdaUserManagerDao;
	}
	
	/**
	 * 查询系统参数表是否有图片信息
	 */
	@Override
	public Integer queryPicture(SysPdaUserManagerModel model) {
		return sysPdaUserManagerDao.queryPicture(model);
	}

	/**
	 * 新增图片
	 */
	@Override
	public void insertImageId(SysPdaUserManagerModel model) {
		sysPdaUserManagerDao.insertImageId(model);
	}

	/**
	 * 更新图片
	 */
	@Override
	public void updateImageIdAndLog(SysPdaUserManagerModel model, String ipAddr) {
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PUB_SYS_PARAM");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("PARAM_CODE");
		pkColumnVO.setColumnVal(model.getImageId());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		sysPdaUserManagerDao.updateImageId(model);
	}

	/**
	 * 查询之前的图片ID
	 */
	@Override
	public List<SysPdaUserManagerModel> queryOlderPicture(SysPdaUserManagerModel model) {
		return sysPdaUserManagerDao.queryOlderPicture(model);
	}

}
