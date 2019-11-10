package com.hanthink.mp.manager;

import java.util.List;

import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.MpExcepOrderDemandHisModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * 
 * @Desc    : 例外订单接口
 * @CreateOn: 2018年9月7日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
public interface MpExcepOrderDemandHisManager extends Manager<String, MpExcepOrderDemandHisModel>{

    /**
     * 分页查询例外订购需求
     * @param model
     * @param p
     * @return
     */
    PageList<MpExcepOrderDemandHisModel> queryMpExcepOrderDemandHisForPage(MpExcepOrderDemandHisModel model, DefaultPage p);

    /**
     * 例外订单生成
     * <p>return: void</p>  
     * <p>Description: MpExcepOrderDemandHisManager.java</p>  
     * @author linzhuo  
     * @date 2018年9月27日
     * @version 1.0
     */
    Integer generateMpExcepOrderDemandHis(String curFactoryCode);
    
	/**
	 * 例外订单发布
	 * <p>return: void</p>  
	 * <p>Description: MpExcepOrderDemandHisManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 * @return 
	 */
	Integer releaseMpExcepOrderDemandHis(String curFactoryCode,String opeId);

	/**
	 * 导出例外订单需求
	 * @param model
	 * @return
	 */
	List<MpExcepOrderDemandHisModel> queryMpExcepOrderDemandHisByKey(MpExcepOrderDemandHisModel model);
}
