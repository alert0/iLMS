package com.hanthink.sw.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sw.dao.SwAccountBillDao;
import com.hanthink.sw.manager.SwAccountBillManager;
import com.hanthink.sw.model.SwAccountBillModel;
import com.hanthink.sw.model.SwDemandForecastModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
/**
 * 
* <p>Title: SwAccountBillManagerImpl</p>  
* <p>Description: 发票对账管理managerImpl</p> 
* <p>Company: hanthink</p>
* @author luoxq  
* @date 2018年10月22日 下午4:35:37
 */
@Service("SwAccountBillManager")
public class SwAccountBillManagerImpl extends AbstractManagerImpl<String, SwAccountBillModel>
implements SwAccountBillManager{

	@Resource
	private SwAccountBillDao dao;
	@Override
	protected Dao<String, SwAccountBillModel> getDao() {
		
		return dao;
	}
	
	/**
	 * 
	 * <p>Title: queryJisoAccountBillPage</p>  
	 * <p>Description: 发票对账管理，分页查询功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月22日 下午4:38:38
	 */
	@Override
	public PageList<SwAccountBillModel> queryJisoAccountBillPage(SwAccountBillModel model, DefaultPage p) {
		
		return dao.queryJisoAccountBillPage(model,p);
	}

	/**
	 * 
	 * <p>Title: queryJisoAccountBillDetailPage</p>  
	 * <p>Description: 发票对账管理，明细查看功能</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月22日 下午5:04:59
	 */
	@Override
	public PageList<SwAccountBillModel> queryJisoAccountBillDetailPage(SwAccountBillModel model, DefaultPage p) {
		
		return dao.queryJisoAccountBillDetailPage(model,p);
	}

	/**
	 * 
	 * <p>Title: billFeedback</p>  
	 * <p>Description: 发票对账管理，反馈功能</p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月22日 下午5:41:44
	 */
	@Override
	public void billFeedback(SwAccountBillModel model) {
		 dao.billFeedback(model);
//		 dao.insertIfAccountInv(model);//数据写入到结算对账单发票反馈中间表
//		 dao.insertIfAccountInvDetail(model);//结算对账单发票反馈明细中间表
	}

	/**
	 * 
	 * <p>Title: querySwAccountBillByKey</p>  
	 * <p>Description: 发票对账管理界面，导出功能</p>  
	 * @param model
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月22日 下午6:06:03
	 */
	@Override
	public List<SwAccountBillModel> querySwAccountBillByKey(SwAccountBillModel model) {
		return dao.querySwAccountBillByKey(model);
	}

	/**
	 * 
	 * <p>Title: querySwAccountDetailBillByKey</p>  
	 * <p>Description: 发票对账管理明细查看，导出功能</p>  
	 * @param model
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月22日 下午6:12:25
	 */
	@Override
	public List<SwAccountBillModel> querySwAccountDetailBillByKey(SwAccountBillModel model) {
		
		return dao.querySwAccountDetailBillByKey(model);
	}

	/**
	 * 
	 * @Description: 根据对账单号，发票号，发票代码判断发票是否已存在
	 * @param @param model
	 * @param @return   
	 * @return Boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月19日 下午9:27:07
	 */
	@Override
	public Boolean isExists(SwAccountBillModel model) {
		Integer count = dao.getCountBill(model);
		if (null != count && count > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @Description: 查看发票反馈情况
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<SwAccountBillModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月3日 下午2:01:27
	 */
	@Override
	public PageList<SwAccountBillModel> queryJisoAccountInvoicePage(SwAccountBillModel model, DefaultPage p) {
		
		return dao.queryJisoAccountInvoicePage(model,p);
	}

	/**
	 * 
	 * @Description: 发票反馈提交后修改主表中提交标识
	 * @param @param billNoArr
	 * @param @param submitStatusYes   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月3日 下午4:10:11
	 */
	@Override
	public void updateSubmitStatus(String[] billNoArr, String submitStatusYes) {
		dao.updateSubmitStatus(billNoArr,submitStatusYes);
	}

	@Override
	public PageList<SwAccountBillModel> queryJisoAccountBillSearchPage(SwAccountBillModel model, DefaultPage p) {
		
		return dao.queryJisoAccountBillSearchPage(model,p);
	}

	@Override
	public List<SwAccountBillModel> querySwAccountBillSearchByKey(SwAccountBillModel model) {
		
		return dao.querySwAccountBillSearchByKey(model);
	}

	@Override
	public List<SwAccountBillModel> downloadSwAccountBillInvoiceModel(SwAccountBillModel model) {
		
		return dao.downloadSwAccountBillInvoiceModel(model);
	}

}
