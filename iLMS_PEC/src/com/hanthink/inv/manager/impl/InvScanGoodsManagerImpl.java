package com.hanthink.inv.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.inv.dao.InvScanGoodsDao;
import com.hanthink.inv.manager.InvScanGoodsManager;
import com.hanthink.inv.model.InvScanGoodsModel;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * <pre> 
 * 功能描述:扫描说活业务层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月11日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("scanGoods")
public class InvScanGoodsManagerImpl extends AbstractManagerImpl<String,InvScanGoodsModel>
					implements InvScanGoodsManager{
	@Resource
	private InvScanGoodsDao scanGoodsDao;
	@Override
	protected Dao<String, InvScanGoodsModel> getDao() {
		return scanGoodsDao;
	}
	@Override
	public List<InvScanGoodsModel> scanGoods(InvScanGoodsModel model, Page page) throws Exception {
		String orderNo = model.getOrderNo();
		
		/**
		 * 根据订单判定工厂
		 */
        String factoryCode = scanGoodsDao.querySwFactoryByOrderNo(model);
		if(("").equals(factoryCode)||null == factoryCode) {
			factoryCode = scanGoodsDao.queryJitFactoryByOrderNo(model);
			if(("").equals(factoryCode)||null == factoryCode) {
				factoryCode = scanGoodsDao.queryJisoFactoryByOrderNo(model);
			}
		}
		/**
		 * 若找不到工厂则为当前人工厂
		 */
		if(("").equals(factoryCode)||null == factoryCode) {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		}else {
			model.setFactoryCode(factoryCode);
		}
		
		if (StringUtil.isEmpty(orderNo)) {
			throw new Exception("物流单号为空");
		}
		//获取订单类型
		String orderType = scanGoodsDao.queryOrderTypeFromJit(orderNo);
		if(StringUtil.isEmpty(orderType)) {
			orderType = scanGoodsDao.queryOrderTypeFromJiso(orderNo);
		}
		if (StringUtil.isEmpty(orderType)) {
			orderType = scanGoodsDao.queryOrderTypeFromSw(orderNo);
		}
		if (StringUtil.isEmpty(orderType)) {
			throw new Exception("订单不存在");
		}
		model.setFlag("0");
		//按照订单类型查询订单详细
		List<InvScanGoodsModel> list = null;
		if ("JIT".equals(orderType)) {
			list = scanGoodsDao.queryJitListByOrderNo(model,page);
		}else if ("JISO".equals(orderType)) {
			list = scanGoodsDao.queryJisoListByOrderNo(model,page);
		}else if ("SW".equals(orderType)){
			list = scanGoodsDao.querySwListByOrderNo(model,page);
		}else {
			throw new Exception("该订单下的零件已全部收货");
		}
		//查询结果判定
		if (list.size() <= 0) {
			throw new Exception("该订单下的零件已全部收货");
		}
		
		return list;
	}
	
	public int find(String oldStr,String target,int ocurTime){
	    int index = oldStr.indexOf(target);
	    for(int i = 0;i < ocurTime;i++){
	    	index = oldStr.indexOf(target,index+1);
	    }
	    return index;
	}
	
	@Override
	public void dealOrderSh(List<InvScanGoodsModel> list, String ipAddr) throws Exception {
		//判断界面是否有数据
		if( list.size() <= 0) {
			throw new Exception("订单明细为空");
		}
		
		//获取当前订单明细信息
		List<InvScanGoodsModel> orderList = new ArrayList<InvScanGoodsModel>();
		IUser user = ContextUtil.getCurrentUser();

		InvScanGoodsModel scanGoodsModel = new InvScanGoodsModel();
		scanGoodsModel.setOrderNo(list.get(0).getOrderNo());
		/**
		 * 根据订单判定工厂
		 */
        String factoryCode = scanGoodsDao.querySwFactoryByOrderNo(scanGoodsModel);
		if(("").equals(factoryCode)||null == factoryCode) {
			factoryCode = scanGoodsDao.queryJitFactoryByOrderNo(scanGoodsModel);
			if(("").equals(factoryCode)||null == factoryCode) {
				factoryCode = scanGoodsDao.queryJisoFactoryByOrderNo(scanGoodsModel);
			}
		}
		/**
		 * 若找不到工厂则为当前人工厂
		 */
		if(("").equals(factoryCode)||null == factoryCode) {
			factoryCode = user.getCurFactoryCode();
		}
		
		int zeroCount = 0;
		for(int i = 0;i < list.size();i++) {
			if ("0".equals(list.get(i).getThisTimeReceiveNum())) {
				zeroCount++;
				continue;
			}
			InvScanGoodsModel model = new InvScanGoodsModel();
			model.setOrderNo(list.get(i).getOrderNo());
			model.setPartNo(list.get(i).getPartNo());
			model.setOrderRowNo(list.get(i).getOrderRowNo());
			model.setShipQuantity(list.get(i).getShipQuantity());
			model.setThisTimeReceiveNum(list.get(i).getThisTimeReceiveNum());
			model.setFactoryCode(factoryCode);
			model.setOpeUser(user.getAccount());
			model.setIpAddr(ipAddr);
			orderList.add(model);
		}
		if (zeroCount == list.size()) {
			throw new Exception("所有订单收货数量为0");
		}
		InvScanGoodsModel model = new InvScanGoodsModel();
		model.setOrderNo(list.get(0).getOrderNo());
		//获取订单类型
		String orderType = scanGoodsDao.queryOrderTypeFromJit(list.get(0).getOrderNo());
		if(StringUtil.isEmpty(orderType)) {
			orderType = scanGoodsDao.queryOrderTypeFromJiso(list.get(0).getOrderNo());
		}
		if (StringUtil.isEmpty(orderType)) {
			orderType = scanGoodsDao.queryOrderTypeFromSw(list.get(0).getOrderNo());
		}
		if (StringUtil.isEmpty(orderType)) {
			throw new Exception("订单不存在");
		}
		
		InvScanGoodsModel wareModel = new InvScanGoodsModel();
		if("JISO".equals(orderType)){
			//如果为同步订单,查询该同步订单对应的车间,然后取到对应的线边到货仓库
			wareModel = scanGoodsDao.queryJISOWareCode(list.get(0).getOrderNo());
		}
		model.setOrderType(orderType);
		model.setFlag("1");
		model.setFactoryCode(factoryCode);
		// 查询订单详细信息(包括收齐订单行)
		List<InvScanGoodsModel> orderDetails = scanGoodsDao.querySHListByOrderNo(model);
		
		//校验数量是否超过订单
        String row = "";
        int totalShNum = 0;
        int orderNum = 0;
        int total = 0;
        for(int i = 0;i < orderDetails.size();i++) {
        	row = orderDetails.get(i).getOrderRowNo();
        	totalShNum = Integer.parseInt(orderDetails.get(i).getReceiveNum());
        	orderNum = Integer.parseInt(orderDetails.get(i).getShipQuantity());
        	for(int j = 0;j < orderList.size(); j++) {
        		if(row.equals(orderList.get(j).getOrderRowNo())) {
        			total = Integer.parseInt(orderList.get(j).getThisTimeReceiveNum()) + totalShNum;
        			if(total > orderNum) {
        				throw new Exception("收货失败,重复作业");
        			}
        			orderList.get(j).setShipQuantity(orderNum + "");
        			// 添加订单ID，工厂等信息
        			if (i == 0) {
        				orderList.get(j).setDepotNo(orderDetails.get(i).getDepotNo());
						orderList.get(j).setFactoryCode(factoryCode);
						if("JIT".equals(orderType) || "JISO".equals(orderType)) {
							orderList.get(j).setCurSeq(orderDetails.get(i).getCurSeq());
						}
						//如果为同步订单,设置到货仓库
						if("JISO".equals(orderType)){
							orderList.get(j).setDepotNo(wareModel.getDepotNo());
						}
					}
        		}
        	}
        }
    	try {
			if (null == orderList || orderList.size() <= 0) {
				throw new Exception("数据解析异常，请联系管理人员");
			}
			// 为1时插入订单头表
	        int num = 1;
	        // 收货次数
	        int shNum;
	        String orderNo = "";
	        String shStatus = "";
	        String orderNoSeq = "";
	        orderNo = orderList.get(0).getOrderNo();
	        if (scanGoodsDao.queryShNum(orderNo, orderType) == null
	                && "SW".equals(orderType)) {
	            shNum = 1;
	        } else {
	            shNum = scanGoodsDao.queryShNum(orderNo, orderType) + 1;
	        }
	        // 订单序列号
	        orderNoSeq = orderNo + "_" + shNum;
	        InvScanGoodsModel recModel = null;
	        for (InvScanGoodsModel invScanGoodsModel : orderList) {
	        	invScanGoodsModel.setOrderNoSeq(orderNoSeq);
				invScanGoodsModel.setOrderType(orderType);
				scanGoodsDao.updateOrderSHdealOrderSh(invScanGoodsModel);
				//如果是num==1写入收入日志头表
				if (num == 1) {
					InvScanGoodsModel orderModel = new InvScanGoodsModel();
					recModel = invScanGoodsModel;
					orderModel.setCurSeq(invScanGoodsModel.getCurSeq());
					orderModel.setOrderType(orderType);
					orderModel.setOrderNo(orderNo);
					orderModel.setFactoryCode(factoryCode);
					orderModel.setRecTimes(String.valueOf(shNum));
					orderModel.setOrderNoSeq(invScanGoodsModel.getOrderNoSeq());
					orderModel.setDepotNo(invScanGoodsModel.getDepotNo());
					orderModel.setOpeUser(invScanGoodsModel.getOpeUser());
					orderModel.setIpAddr(invScanGoodsModel.getIpAddr());
					scanGoodsDao.insertOrderSH(orderModel);
				}
				//设置明细需要的数据
				invScanGoodsModel.setOrderNoSeq(orderNoSeq);
				invScanGoodsModel.setRecTimes(String.valueOf(shNum));
				invScanGoodsModel.setOrderType(orderType);
				invScanGoodsModel.setPartNo(invScanGoodsModel.getPartNo());
				scanGoodsDao.insertOrderShDetailLog(invScanGoodsModel);
				num++;
			}
	        // 更新订单状态
	        shStatus = String.valueOf(scanGoodsDao.queryShStatusByOrderNo(recModel));
	        recModel.setShStatus(shStatus);
	        scanGoodsDao.updateOrderShStatus(recModel);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据解析异常");
		}
    }
}
