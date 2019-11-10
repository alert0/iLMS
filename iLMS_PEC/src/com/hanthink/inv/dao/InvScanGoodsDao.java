package com.hanthink.inv.dao;

import java.util.List;

import com.hanthink.inv.model.InvScanGoodsModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;

/**
 * <pre> 
 * 功能描述:扫描收货持久层接口
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月11日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvScanGoodsDao extends Dao<String, InvScanGoodsModel>{
	/**
	 * 判断订单是否是拉动订单
	 * @param orderNo
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	String queryOrderTypeFromJit(String orderNo)throws Exception;
	/**
	 * 判断订单是否是同步订单
	 * @param orderNo
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	String queryOrderTypeFromJiso(String orderNo)throws Exception;
	/**
	 * 判断订单是否是协同订单
	 * @param orderNo
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	String queryOrderTypeFromSw(String orderNo)throws Exception;
	/**
	 * 查询拉动订单明细
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<InvScanGoodsModel> queryJitListByOrderNo(InvScanGoodsModel model,Page page)throws Exception;
	/**
	 * 查询同步订单明细
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<InvScanGoodsModel> queryJisoListByOrderNo(InvScanGoodsModel model,Page page)throws Exception;
	/**
	 * 查询协同订单明细
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<InvScanGoodsModel> querySwListByOrderNo(InvScanGoodsModel model,Page page)throws Exception;
	/**
	 * 查询订单详情
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	List<InvScanGoodsModel> querySHListByOrderNo(InvScanGoodsModel model)throws Exception;
	/**
	 * 获取订单收货次数
	 * @param orderNo
	 * @param orderType
	 * @return
	 * @throws Exception
	 * @author zmj
	 */
	Integer queryShNum(String orderNo, String orderType)throws Exception;
	/**
	 * 更新订单详情
	 * @param orderList
	 * @param orderType
	 * @throws Exception
	 * @author zmj
	 */
	void updateOrderSHdealOrderSh(InvScanGoodsModel invScanGoodsModel)throws Exception;
	/**
	 * 记录本次收货日志头表
	 * @param orderModel
	 * @throws Exception
	 * @author zmj
	 */
	void insertOrderSH(InvScanGoodsModel orderModel)throws Exception;
	/**
	 * 新增订单收货日志明细数据
	 * @param invScanGoodsModel
	 * @throws Exception
	 * @author zmj
	 */
	void insertOrderShDetailLog(InvScanGoodsModel invScanGoodsModel)throws Exception;
	/**
	 * 查询订单收货状态
	 * @param recModel
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年11月5日
	 */
	Integer queryShStatusByOrderNo(InvScanGoodsModel recModel)throws Exception;
	/**
	 * 更新订单表收货状态
	 * @param recModel
	 * @throws Exception
	 * @author zmj
	 */
	void updateOrderShStatus(InvScanGoodsModel recModel)throws Exception;
	
	/**
	 * 查询同步订单对应的线边仓库
	 * @param orderNo
	 * @return
	 */
	InvScanGoodsModel queryJISOWareCode(String orderNo);
	
	/**
	 * 协同单找工厂
	 * @param model
	 * @return
	 */
	String querySwFactoryByOrderNo(InvScanGoodsModel model);
	
	/**
	 * 拉动单找工厂
	 * @param model
	 * @return
	 */
	String queryJitFactoryByOrderNo(InvScanGoodsModel model);
	
	/**
	 * 同步单找工厂
	 * @param model
	 * @return
	 */
	String queryJisoFactoryByOrderNo(InvScanGoodsModel model);
	
	
}
