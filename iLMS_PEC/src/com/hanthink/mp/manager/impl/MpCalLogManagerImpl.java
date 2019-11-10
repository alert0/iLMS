package com.hanthink.mp.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.DictVO;

/**
 * 根据表名实现的类
 */

import com.hanthink.mp.dao.MpCalLogDao;
import com.hanthink.mp.manager.MpCalLogManager;
import com.hanthink.mp.model.MpCalLogModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre> 
 * 描述：订单计算 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("MpCalLogManager")
public class MpCalLogManagerImpl extends AbstractManagerImpl<String, MpCalLogModel> implements MpCalLogManager{
	@Resource
	MpCalLogDao mpCalLogDao;
	@Override
	protected Dao<String, MpCalLogModel> getDao() {
		return mpCalLogDao;
	}
	
	/**
	 * 分页查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	 @Override
	    public PageList<MpCalLogModel> queryMpCalLogForPage(MpCalLogModel model, DefaultPage p) {
	        return (PageList<MpCalLogModel>) mpCalLogDao.queryMpCalLogForPage(model, p);
	    }

	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<MpCalLogModel> queryMpCalLogByKey(MpCalLogModel model) {
		return mpCalLogDao.queryMpCalLogByKey(model);
	}

	/**
	 * 点击“需求计算”按钮时，要校验当前是否正在进行净需求计算。如果有，要提示错误，且不允许执行。
	 */
	@Override
	public String isLockCheck(MpCalLogModel model) {
		return mpCalLogDao.isLockCheck(model);
	}

	/**
	 * 集成订购需求推算
	 */
	@Override
	public Integer demandCal(String uuid, String opeId, String typeLock, String arrFactory) {
		return mpCalLogDao.demandCal(uuid,opeId,typeLock,arrFactory);
	}

	/**
	 * 生成采购订单
	 */
	@Override
	public Integer releaseOrder(String uuid, String opeId, String typeLock, String arrFactory) {
		return mpCalLogDao.releaseOrder(uuid,opeId,typeLock,arrFactory);
	}

	@Override
	public Integer selectStatus(String arrFactory) {
		return mpCalLogDao.selectStatus(arrFactory);
	}

    @Override
    public Integer generateOrderNo(String uuid, String opeId, String typeLock, String arrFactory) {
        return mpCalLogDao.generateOrderNo(uuid,opeId,typeLock,arrFactory);
    }

    @Override
    public boolean isAvailable(String arrFactory) {
        boolean b = true;
        /**
         * 查询最后一次操作日志
         */
        String calType = mpCalLogDao.queryLastCalLog(arrFactory);
        //前面没有执行过或者最新一次执行不是执行的生成订单号都返回false
        if(calType == null || !"3".equals(calType)){
            b = false;
        }        
        /**
         * 查询MM_MP_PUR_ORDER_TEMP的数据的数量
         */
        Integer count = mpCalLogDao.queryPurOrderTempCount(arrFactory);
        if(count <= 0){
            b = false;
        }
        return b;
    }
}
