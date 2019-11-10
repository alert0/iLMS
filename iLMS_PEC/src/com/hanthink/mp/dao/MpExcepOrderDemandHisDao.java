package com.hanthink.mp.dao;

import java.util.List;

import com.hanthink.mp.model.MpExcepOrderDemandHisModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;

public interface MpExcepOrderDemandHisDao extends Dao<String, MpExcepOrderDemandHisModel>{

    /**
     * 分页查询例外订购需求
     * @param model
     * @param p
     * @return
     */
    List<MpExcepOrderDemandHisModel> queryMpExcepOrderDemandHisForPage(MpExcepOrderDemandHisModel model, DefaultPage p);

    /**
     * 例外订单生成
     * <p>return: void</p>  
     * <p>Description: MpExcepOrderDemandHisDao.java</p>  
     * @author linzhuo  
     * @date 2018年9月27日
     * @version 1.0
     */
	Integer generateMpExcepOrderDemandHis(String curFactoryCode);

	/**
	 * 例外订单发布
	 * <p>return: void</p>  
	 * <p>Description: MpExcepOrderDemandHisDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 * @return 
	 */
	Integer releaseMpExcepOrderDemandHis(String curFactoryCode,String opeId);

	/**
	 * 导出查询的方法
	 * <p>return: void</p>  
	 * <p>Description: MpExcepOrderDemandHisDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月29日
	 * @version 1.0
	 */
	List<MpExcepOrderDemandHisModel> queryMpExcepOrderDemandHisByKey(MpExcepOrderDemandHisModel model);
    
}
