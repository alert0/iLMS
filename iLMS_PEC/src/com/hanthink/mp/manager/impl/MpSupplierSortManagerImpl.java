package com.hanthink.mp.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;

/**
 * 根据表名实现的类
 */

import com.hanthink.mp.dao.MpSupplierSortDao;
import com.hanthink.mp.manager.MpSupplierSortManager;
import com.hanthink.mp.model.MpSupplierSortModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：供应商分组 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpSupplierSortManager")
public class MpSupplierSortManagerImpl extends AbstractManagerImpl<String, MpSupplierSortModel> implements MpSupplierSortManager{
	@Resource
	MpSupplierSortDao mpSupplierSortDao;
	@Override
	protected Dao<String, MpSupplierSortModel> getDao() {
		return mpSupplierSortDao;
	}
	
	/**
	 * 分页查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	 @Override
	    public PageList<MpSupplierSortModel> queryMpSupplierSortForPage(MpSupplierSortModel model, DefaultPage p) {
	        return (PageList<MpSupplierSortModel>) mpSupplierSortDao.queryMpSupplierSortForPage(model, p);
	    }

	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<MpSupplierSortModel> queryMpSupplierSortByKey(MpSupplierSortModel model) {
		return mpSupplierSortDao.queryMpSupplierSortByKey(model);
	}

	/**
	 * 获取计算队列
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<DictVO> getUnloadPort() {
		
		return mpSupplierSortDao.getUnloadPort();
	}
	
	
}
