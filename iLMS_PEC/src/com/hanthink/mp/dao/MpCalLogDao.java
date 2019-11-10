package com.hanthink.mp.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.MpCalLogModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：订单计算  DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpCalLogDao extends Dao<String, MpCalLogModel> {
	
	 /**
     * 分页查询供应商分组
     * @param model
     * @param p
     * @return
     */
    List<MpCalLogModel> queryMpCalLogForPage(MpCalLogModel model, DefaultPage p);
	
	/**
	 * 导出数据的查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<MpCalLogModel> queryMpCalLogByKey(MpCalLogModel model);

	/**
	 * 点击“需求计算”按钮时，要校验当前是否正在进行净需求计算。如果有，要提示错误，且不允许执行。
	 * <p>return: String</p>  
	 * <p>Description: MpCalLogDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月25日
	 * @version 1.0
	 */
	String isLockCheck(MpCalLogModel model);

	/**
	 * 集成订购需求推算
	 * <p>return: void</p>  
	 * <p>Description: MpCalLogDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月25日
	 * @version 1.0
	 */
	Integer demandCal(String uuid, String opeId, String typeLock, String arrFactory);

	/**
	 * 生成采购订单
	 * <p>return: void</p>  
	 * <p>Description: MpCalLogDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月25日
	 * @version 1.0
	 */
	Integer releaseOrder(String uuid, String opeId, String typeLock, String arrFactory);

	/**
	 * 查询状态
	 * <p>return: String</p>  
	 * <p>Description: MpCalLogDao.java</p>  
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
     * 查询最后一次计算的类型
     * @param arrFactory
     */
    String queryLastCalLog(String arrFactory);

    /**
     * 查询MM_MP_PUR_ORDER_TEMP表的记录条数
     * @param arrFactory
     * @return
     */
    Integer queryPurOrderTempCount(String arrFactory);


}
