package com.hanthink.sw.manager.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.sw.dao.SwSupplierMsgDao;
import com.hanthink.sw.manager.SwSupplierMsgManager;
import com.hanthink.sw.model.SwSupplierMsgModel;
import com.hotent.base.core.encrypt.EncryptUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.sys.util.ContextUtil;
/**
 * 
* <p>Title: SwSupplierMsgManagerImpl</p>  
* <p>Description: 供应商信息更新，修改功能</p> 
* <p>Company: hanthink</p>
* @author luoxq  
* @date 2018年10月22日 上午11:09:42
 */
@Service("SwSupplierMsgManager")
public class SwSupplierMsgManagerImpl extends AbstractManagerImpl<String, SwSupplierMsgModel>
implements SwSupplierMsgManager{
	
	@Resource
	private SwSupplierMsgDao dao;

	@Override
	protected Dao<String, SwSupplierMsgModel> getDao() {
		
		return dao;
	}

	/**
	 * 
	 * <p>Title: updateAndLog</p>  
	 * <p>Description: 供应商更新界面，修改功能</p>  
	 * @param model
	 * @param ipAddr  
	 * @authoer luoxq
	 * @time 2018年10月22日 上午11:07:13
	 */
	@Override
	public void updateAndLog(SwSupplierMsgModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PUB_SUPPLIER");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("SUPPLIER_NO");
		pkColumnVO.setColumnVal(model.getSupplierNo());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		dao.updateSupplier(model);
		dao.updateSupplierStatus(model); //更新供应商地址
//		model.setNewConfirmPassword(EncryptUtil.encryptSha256(model.getNewConfirmPassword()));
//		dao.updateAccountPassword(model);
		
	}

	/**
	 * 
	 * @Description: 通过登录供应商用户id获取供应商代码
	 * @param @param userId
	 * @param @return   
	 * @return SwSupplierMsgModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月25日 下午2:17:10
	 */
	@Override
	public SwSupplierMsgModel getSupplierByUserId(SwSupplierMsgModel model) {
		
		return dao.getSupplierByUserId(model);
	}

	@Override
	public void registSupplier(SwSupplierMsgModel model, String ipAddr) {
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PUB_SUPPLIER");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("SUPPLIER_NO");
		pkColumnVO.setColumnVal(model.getSupplierNo());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		dao.updateSupplierStatus(model); //第一次登录填写信息后更新供应商状态为激活3
		dao.insertSupplierDetail(model); //供应商第一次登录信息写入到供应商明细表中
		model.setNewConfirmPassword(EncryptUtil.encryptSha256(model.getNewConfirmPassword()));
		dao.updateAccountPassword(model);
	}

	/**
	 * 
	 * @Description: 获取当前供应商账号的供应商状态
	 * @param @param map
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月21日 下午3:10:07
	 */
	@Override
	public String getSupStatusByUser(Map<String, Object> map) {
		
		return dao.getSupStatusByUser(map);
	}

	@Override
	public String getActiveStatusByMap(Map<String, Object> map) {
		
		return dao.getActiveStatusByMap(map);
	}

}
