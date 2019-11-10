package com.hanthink.inv.manager;

import java.util.List;

import com.hanthink.inv.model.InvScanGoodsModel;
import com.hotent.base.api.Page;
import com.hotent.base.manager.api.Manager;

/**
 * <pre> 
 * 功能描述:扫描收货业务层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月11日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface InvScanGoodsManager extends Manager<String, InvScanGoodsModel>{
	/**
	 * 扫描订单详情
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月11日
	 */
	List<InvScanGoodsModel> scanGoods(InvScanGoodsModel model, Page page)throws Exception;
	/**
	 * 收货处理
	 * @param list
	 * @param ipAddr
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月11日
	 */
	void dealOrderSh(List<InvScanGoodsModel> list, String ipAddr)throws Exception;

}
