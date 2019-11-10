package com.hanthink.mp.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;

/**
 * 根据表名实现的类
 */

import com.hanthink.mp.dao.MpPartSortDao;
import com.hanthink.mp.manager.MpPartSortManager;
import com.hanthink.mp.model.MpPartSortModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre> 
 * 描述：零件分组 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpPartSortManager")
public class MpPartSortManagerImpl extends AbstractManagerImpl<String, MpPartSortModel> implements MpPartSortManager{
	@Resource
	MpPartSortDao mpPartSortDao;
	@Override
	protected Dao<String, MpPartSortModel> getDao() {
		return mpPartSortDao;
	}
	
	/**
	 * 分页查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	 @Override
	    public PageList<MpPartSortModel> queryMpPartSortForPage(MpPartSortModel model, DefaultPage p) {
	        return (PageList<MpPartSortModel>) mpPartSortDao.queryMpPartSortForPage(model, p);
	    }

	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<MpPartSortModel> queryMpPartSortByKey(MpPartSortModel model) {
		return mpPartSortDao.queryMpPartSortByKey(model);
	}

	/**
	 * 获取计算队列
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<DictVO> getUnloadPort() {
		
		return mpPartSortDao.getUnloadPort();
	}
	
	
}
