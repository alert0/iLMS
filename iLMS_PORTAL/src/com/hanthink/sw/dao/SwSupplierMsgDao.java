package com.hanthink.sw.dao;

import java.util.Map;

import com.hanthink.sw.model.SwSupplierMsgModel;
import com.hotent.base.db.api.Dao;

public interface SwSupplierMsgDao extends Dao<String, SwSupplierMsgModel>{

	/**
	 * 
	 * <p>Title: updateSupplier</p>  
	 * <p>Description: 供应商更新界面，修改功能</p>  
	 * @param model  
	 * @authoer luoxq
	 * @time 2018年10月22日 上午11:11:22
	 */
	void updateSupplier(SwSupplierMsgModel model);

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
	SwSupplierMsgModel getSupplierByUserId(SwSupplierMsgModel model);

	/**
	 * 更新账号密码
	 * @param model
	 */
    void updateAccountPassword(SwSupplierMsgModel model);

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
	String getSupStatusByUser(Map<String, Object> map);

	/**
	 * 
	 * @Description: 供应商第一次登录信息写入到供应商明细表中
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月21日 下午4:40:25
	 */
	void insertSupplierDetail(SwSupplierMsgModel model);

	/**
	 * 
	 * @Description: 第一次登陆填写信息更新供应商状态为激活3
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月21日 下午4:51:35
	 */
	void updateSupplierStatus(SwSupplierMsgModel model);

	/**
	 * 
	 * @Description: 判断供应商是否为激活
	 * @param @param map
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月12日 上午2:09:00
	 */
	String getActiveStatusByMap(Map<String, Object> map);

}
