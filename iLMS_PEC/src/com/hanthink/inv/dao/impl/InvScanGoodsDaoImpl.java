package com.hanthink.inv.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvScanGoodsDao;
import com.hanthink.inv.model.InvScanGoodsModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

/**
 * <pre> 
 * 功能描述:扫描收货持久层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月11日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Repository
public class InvScanGoodsDaoImpl extends MyBatisDaoImpl<String, InvScanGoodsModel>
						implements InvScanGoodsDao{

	@Override
	public String getNamespace() {
		return InvScanGoodsModel.class.getName();
	}

	@Override
	public String queryOrderTypeFromJit(String orderNo) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryOrderTypeFromJit",orderNo);
	}

	@Override
	public String queryOrderTypeFromJiso(String orderNo) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryOrderTypeFromJiso",orderNo);
	}

	@Override
	public String queryOrderTypeFromSw(String orderNo) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryOrderTypeFromSw", orderNo);
	}

	@Override
	public List<InvScanGoodsModel> queryJitListByOrderNo(InvScanGoodsModel model,Page page) throws Exception {
		return this.getByKey("queryJitListByOrderNo", model, page);
	}

	@Override
	public List<InvScanGoodsModel> queryJisoListByOrderNo(InvScanGoodsModel model,Page page) throws Exception {
		return this.getByKey("queryJisoListByOrderNo", model, page);
	}

	@Override
	public List<InvScanGoodsModel> querySwListByOrderNo(InvScanGoodsModel model,Page page) throws Exception {
		return this.getByKey("querySwListByOrderNo", model, page);
	}

	@Override
	public List<InvScanGoodsModel> querySHListByOrderNo(InvScanGoodsModel model) throws Exception {
		String orderType = model.getOrderType();
		if ("JIT".equals(orderType)) {
			return this.getByKey("queryJitListByOrderNo", model);
		}else if ("JISO".equals(orderType)) {
			return this.getByKey("queryJisoListByOrderNo", model);
		}else if ("SW".equals(orderType)) {
			return this.getByKey("querySwListByOrderNo", model);
		}else {
			return null;
		}
	}

	@Override
	public Integer queryShNum(String orderNo, String orderType) throws Exception {
		if ("JIT".equals(orderType)) {
            return this.sqlSessionTemplate.selectOne("queryJitShNum", orderNo);
        } else if ("JISO".equals(orderType)) {
        	return this.sqlSessionTemplate.selectOne("queryJisoShNum", orderNo);
        } else if ("SW".equals(orderType)) {
        	return this.sqlSessionTemplate.selectOne("querySwShNum", orderNo);
        }else {
        	return null;
		} 
	}

	@Override
	public void updateOrderSHdealOrderSh(InvScanGoodsModel invScanGoodsModel) throws Exception {
		String orderType = invScanGoodsModel.getOrderType();
		if ("JIT".equals(orderType)) {
			this.updateByKey("updateJitOrdetailByDealOrderSh",invScanGoodsModel);
		}else if ("JISO".equals(orderType)) {
			this.updateByKey("updateJisoOrdetailByDealOrderSh",invScanGoodsModel);
		}else if ("SW".equals(orderType)) {
			this.updateByKey("updateSwOrdetailByDealOrderSh",invScanGoodsModel);
		}else {
			throw new Exception("获取订单类型失败");
		}
	}

	@Override
	public void insertOrderSH(InvScanGoodsModel orderModel) throws Exception {
		this.insertByKey("insertOrderShHeadLog", orderModel);
	}

	@Override
	public void insertOrderShDetailLog(InvScanGoodsModel invScanGoodsModel) throws Exception {
		System.err.println(invScanGoodsModel);
		this.insertByKey("insertOrderShDetailLog", invScanGoodsModel);
	}

	@Override
	public Integer queryShStatusByOrderNo(InvScanGoodsModel recModel) throws Exception {
		String orderType = recModel.getOrderType();
		String orderNo = recModel.getOrderNo();
		if ("JIT".equals(orderType)) {
			return this.sqlSessionTemplate.selectOne("queryJitShStatusByOrderNo",orderNo);
		}else if ("JISO".equals(orderType)) {
			return this.sqlSessionTemplate.selectOne("queryJisoShStatusByOrderNo",orderNo);
		}else if ("SW".equals(orderType)) {
			return this.sqlSessionTemplate.selectOne("querySwShStatusByOrderNo",orderNo);
		}else {
			return -1;
		}
	}

	@Override
	public void updateOrderShStatus(InvScanGoodsModel recModel) throws Exception {
		String orderType = recModel.getOrderType();
		if ("JIT".equals(orderType)) {
			this.updateByKey("updateJitOrderShStatus", recModel);
		}else if ("JISO".equals(orderType)) {
			this.updateByKey("updateJisoOrderShStatus", recModel);
		}else if ("SW".equals(orderType)) {
			this.updateByKey("updateSwOrderShStatus", recModel);
		}else {
			throw new Exception("获取订单类型失败");
		}
	}

	@Override
	public InvScanGoodsModel queryJISOWareCode(String orderNo) {
		return this.getUnique("queryJISOWareCode", orderNo);
	}

	/**
	 * 协同单找工厂
	 */
	@Override
	public String querySwFactoryByOrderNo(InvScanGoodsModel model) {
		return this.sqlSessionTemplate.selectOne("querySwFactoryByOrderNo",model);
	}

	/**
	 * 拉动单找工厂
	 */
	@Override
	public String queryJitFactoryByOrderNo(InvScanGoodsModel model) {
		return this.sqlSessionTemplate.selectOne("queryJitFactoryByOrderNo",model);
	}

	/**
	 * 同步单找工厂
	 */
	@Override
	public String queryJisoFactoryByOrderNo(InvScanGoodsModel model) {
		return this.sqlSessionTemplate.selectOne("queryJisoFactoryByOrderNo",model);
	}

}
