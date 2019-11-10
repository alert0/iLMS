package com.hanthink.mp.manager;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.MpCalLogModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：订单计算 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpCalLogManager extends Manager<String, MpCalLogModel>{

	 /**
     * 分页查询订单计算
     * @param model
     * @param p
     * @return
     */
    PageList<MpCalLogModel> queryMpCalLogForPage(MpCalLogModel model, DefaultPage p);
	
	/**
	 * 导出订单计算
	 * @param model
	 * @return
	 */
	List<MpCalLogModel> queryMpCalLogByKey(MpCalLogModel model);

	/**
	 * 点击“需求计算”按钮时，要校验当前是否正在进行净需求计算。如果有，要提示错误，且不允许执行。
	 * <p>return: String</p>  
	 * <p>Description: MpCalLogManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月25日
	 * @version 1.0
	 */
	String isLockCheck(MpCalLogModel model);

    /**
     * 集成订购需求推算
     * <p>return: void</p>  
     * <p>Description: MpCalLogManager.java</p>  
     * @author linzhuo  
     * @date 2018年9月25日
     * @version 1.0
     */
	Integer demandCal(String uuid, String opeId, String typeLock, String arrFactory);

	/**
	 * 生成采购订单
	 * <p>return: void</p>  
	 * <p>Description: MpCalLogManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月25日
	 * @version 1.0
	 */
	Integer releaseOrder(String uuid, String opeId, String typeLock, String arrFactory);

	/**
	 * 查询状态
	 * <p>return: String</p>  
	 * <p>Description: MpCalLogManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年11月21日
	 * @version 1.0
	 * @param arrFactory 
	 */
	Integer selectStatus(String arrFactory);

	/**
	 * 生成订单号
	 * @param uuid
	 * @param opeId
	 * @param typeLock
	 * @param arrFactory
	 * @return
	 */
    Integer generateOrderNo(String uuid, String opeId, String typeLock, String arrFactory);

    /**
     * 是否可执行订单发布
     * @param arrFactory
     * @return
     */
    boolean isAvailable(String arrFactory);
	
	

}
