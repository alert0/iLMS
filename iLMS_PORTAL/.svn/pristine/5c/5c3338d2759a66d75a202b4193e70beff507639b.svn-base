package com.hanthink.sw.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwZCOrderDao;
import com.hanthink.sw.model.SwZCOrderModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SwZCOrderDaoImpl
 * @Description: 资材订单
 * @author dtp
 * @date 2019年3月1日
 */
@Repository
public class SwZCOrderDaoImpl extends MyBatisDaoImpl<String, SwZCOrderModel> implements SwZCOrderDao{

	@Override
	public String getNamespace() {
		return SwZCOrderModel.class.getName();
	}

	/**
	 * @Description:  资材订单查询    
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月1日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwZCOrderModel> queryZCOrderPage(SwZCOrderModel model, DefaultPage page) {
		return (PageList<SwZCOrderModel>) this.getList("queryZCOrderPage", model, page);
	}

	/**
	 * @Description: 资材订单导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZCOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月1日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwZCOrderModel> queryZCOrderList(SwZCOrderModel model) {
		return this.getListByKey("queryZCOrderPage", model);
	}

	/**
	 * 删除导入数据
	 * @Description:   
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteImportTempDataByUUID", uuid);
	}

	/**
	 * @Description: 写入临时表   
	 * @param: @param importList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	@Override
	public void insertSwZCOrderTempData(List<SwZCOrderModel> importList) {
		for (SwZCOrderModel model : importList) {
			this.insertByKey("insertSwZCOrderTempData", model);
		}
	}

	/**
	 * @Description: 查询导入校验结果是否包含不通过  
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return Integer.parseInt(this.getOne("queryIsExistsCheckResultFalse", uuid).toString());
	}

	/**
	 * @Description:  写入反馈表 
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	@Override
	public void insertImportData(Map<String, String> paramMap) {
		this.insertByKey("insertImportData", paramMap);
	}

	/**
	 * @Description:  查询临时表数据 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SwZCOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwZCOrderModel> queryImportTempPage(SwZCOrderModel model, DefaultPage page) {
		return this.getListByKey("queryImportTempPage", model, page);
	}

	/**
	 * @Description:  资材反馈导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SwZCOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwZCOrderModel> queryImportTempList(SwZCOrderModel model) {
		return (List<SwZCOrderModel>) this.getList("queryImportTempPage", model);
	}

	/**
	 * @Description:  资材反馈
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	@Override
	public void insertZCOrderFeedBack(SwZCOrderModel model) {
		this.insertByKey("insertZCOrderFeedBack", model);
	}

	/**
	 * @Description: 修改反馈  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月3日
	 */
	@Override
	public void updateZCOrderFeedBack(SwZCOrderModel model) {
		this.updateByKey("updateZCOrderFeedBack", model);
	}

	/**
	 * @Description: 资材PC端反馈  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月7日
	 */
	@Override
	public void zcPCFeedback(SwZCOrderModel model) {
		this.insertByKey("zcPCFeedback", model);
	}

	/**
	 * @Description: 查询资材订单打印信息 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<SwZCOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月10日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwZCOrderModel> queryZCOrderPrintDetailList(SwZCOrderModel model_q) {
		return this.getListByKey("queryZCOrderPrintDetailList", model_q);
	}

	/**
	 * @Description: 更新资材订单打印状态 
	 * @param: @param list_printInfo    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月10日
	 */
	@Override
	public void updatePrintInfo(SwZCOrderModel model) {
		this.updateByKey("updatePrintInfo", model);
	}

	/**
	 * @Description: 调用存储校验临时表数据  
	 * @param: @param ckParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月12日
	 */
	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".checkImportData", ckParamMap);
	}

	/**
	 * @Description: 更新临时表导入状态  
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月12日
	 */
	@Override
	public void updateImportDataImpStatus(Map<String, String> paramMap) {
		this.updateByKey("updateImportDataImpStatus", paramMap);
	}

	/**
	 * @Description: 查询资材标签打印信息 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<SwZCOrderModel>   
	 * @author: dtp
	 * @date: 2019年3月14日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwZCOrderModel> queryZCOrderLabelDetailList(SwZCOrderModel model_q) {
		return (List<SwZCOrderModel>) this.getList("queryZCOrderLabelDetailList", model_q);
	}

	/**
	 * @Description: 更新mm_sw_order表打印状态  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月9日
	 */
	@Override
	public void updateSwOrderPrintInfo(SwZCOrderModel model) {
		this.updateByKey("updateSwOrderPrintInfo", model);
	}
	

}