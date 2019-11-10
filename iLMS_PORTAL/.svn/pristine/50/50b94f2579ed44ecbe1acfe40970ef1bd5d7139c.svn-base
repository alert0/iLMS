package com.hanthink.sw.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwSupplierMsgDao;
import com.hanthink.sw.model.SwSupplierMsgModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
@Repository
public class SwSupplierMsgDaoImpl extends MyBatisDaoImpl<String, SwSupplierMsgModel>
implements SwSupplierMsgDao{
	@Override
	public String getNamespace() {
		
		return SwSupplierMsgModel.class.getName();
	}

	/**
	 * 
	 * <p>Title: updateSupplier</p>  
	 * <p>Description: 供应商更新界面，修改功能</p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月22日 上午11:11:22
	 */
	@Override
	public void updateSupplier(SwSupplierMsgModel model) {
		this.updateByKey("updateSupplier", model);
	}

	/**
	 * 
	 * @Description: 通过登录的供应商用户id获取供应商代码
	 * @param @param userId
	 * @param @return   
	 * @return SwSupplierMsgModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月25日 下午2:18:41
	 */
	@Override
	public SwSupplierMsgModel getSupplierByUserId(SwSupplierMsgModel model) {
		
		return this.getUnique("getSupplierByUserId", model);
	}

    @Override
    public void updateAccountPassword(SwSupplierMsgModel model) {
        this.updateByKey("updateAccountPassword", model);
    }

    /**
     * 
     * @Description: 获取当前账号的供应商状态
     * @param @param map
     * @param @return   
     * @return String  
     * @throws  
     * @author luoxq
     * @date 2019年2月21日 下午3:11:18
     */
	@Override
	public String getSupStatusByUser(Map<String, Object> map) {
		
		return this.getSqlSessionTemplate().selectOne("getSupStatusByUser", map);
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
	public void insertSupplierDetail(SwSupplierMsgModel model) {
		this.insertByKey("insertSupplierDetail", model);
	}

	
	@Override
	public void updateSupplierStatus(SwSupplierMsgModel model) {
		this.updateByKey("updateSupplierStatus", model);
	}

	@Override
	public String getActiveStatusByMap(Map<String, Object> map) {
		
		return this.sqlSessionTemplate.selectOne("getActiveStatusByMap", map);
	}

	
}
