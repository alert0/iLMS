package com.hanthink.mp.manager.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mp.dao.MpExcepOrderDemandHisDao;
import com.hanthink.mp.manager.MpExcepOrderDemandHisManager;
import com.hanthink.mp.model.MpExcepOrderDemandHisModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
/**
 * 
 * @Desc    : 
 * @CreateOn: 2018年9月10日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
@Service
public class MpExcepOrderDemandHisManagerImpl extends AbstractManagerImpl<String, MpExcepOrderDemandHisModel> implements MpExcepOrderDemandHisManager{
    
    @Resource
    private MpExcepOrderDemandHisDao mpExcepOrderDemandHisDao;

    @Override
    protected Dao<String, MpExcepOrderDemandHisModel> getDao() {
        return mpExcepOrderDemandHisDao;
    }

    @Override
    public PageList<MpExcepOrderDemandHisModel> queryMpExcepOrderDemandHisForPage(MpExcepOrderDemandHisModel model, DefaultPage p) {
        return (PageList<MpExcepOrderDemandHisModel>) mpExcepOrderDemandHisDao.queryMpExcepOrderDemandHisForPage(model, p);
    }
    
    /**
     * 例外订单生成
     */
	@Override
	public Integer generateMpExcepOrderDemandHis(String curFactoryCode) {
		return mpExcepOrderDemandHisDao.generateMpExcepOrderDemandHis(curFactoryCode);		
	}

	/**
	 * 例外订单发布
	 */
	@Override
	public Integer releaseMpExcepOrderDemandHis(String curFactoryCode,String opeId) {
		return mpExcepOrderDemandHisDao.releaseMpExcepOrderDemandHis(curFactoryCode,opeId);	
	}

	/**
	 * 导出查询
	 */
	@Override
	public List<MpExcepOrderDemandHisModel> queryMpExcepOrderDemandHisByKey(MpExcepOrderDemandHisModel model) {
		return mpExcepOrderDemandHisDao.queryMpExcepOrderDemandHisByKey(model);
	}

}
