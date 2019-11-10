package com.hanthink.sys.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sys.dao.SysPdaLabelScanLogDao;
import com.hanthink.sys.manager.SysPdaLabelScanLogManager;
import com.hanthink.sys.model.SysPdaLabelScanLogModel;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;

/**
 * 
 * <pre> 
 * 描述：PDA标签扫描日志查询 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("SysPdaLabelScanLogManager")
public class SysPdaLabelScanLogManagerImpl extends AbstractManagerImpl<String, SysPdaLabelScanLogModel> implements SysPdaLabelScanLogManager{
	@Resource
	SysPdaLabelScanLogDao sysPdaLabelScanLogDao;
	@Override
	protected Dao<String, SysPdaLabelScanLogModel> getDao() {
		return sysPdaLabelScanLogDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<SysPdaLabelScanLogModel> querySysPdaLabelScanLogForPage(SysPdaLabelScanLogModel model, DefaultPage p) {
		return sysPdaLabelScanLogDao.querySysPdaLabelScanLogForPage(model, p);
	}

	/**
	 * 到处查询
	 */
	@Override
	public List<SysPdaLabelScanLogModel> querySysPdaLabelScanLogByKey(SysPdaLabelScanLogModel model) {
		return sysPdaLabelScanLogDao.querySysPdaLabelScanLogByKey(model);
	}
	
}
