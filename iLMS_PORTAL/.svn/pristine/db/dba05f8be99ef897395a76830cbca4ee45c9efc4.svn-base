package com.hanthink.sw.manager;

import java.util.Map;

import com.hanthink.sw.model.SwSupplierMsgModel;
import com.hotent.base.manager.api.Manager;

public interface SwSupplierMsgManager extends Manager<String, SwSupplierMsgModel>{

	/**
	 * 
	 * <p>Title: updateAndLog</p>  
	 * <p>Description: 供应商更新界面，修改功能</p>  
	 * @param model
	 * @param ipAddr  
	 * @authoer luoxq
	 * @time 2018年10月22日 上午11:07:13
	 */
	void updateAndLog(SwSupplierMsgModel model, String ipAddr);

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
	SwSupplierMsgModel getSupplierByUserId(SwSupplierMsgModel model);

	/**
	 * 
	 * @Description: 供应商第一次登录填写信息
	 * @param @param model
	 * @param @param ipAddr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月24日 上午9:23:12
	 */
	void registSupplier(SwSupplierMsgModel model, String ipAddr);

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
	String getSupStatusByUser(Map<String, Object> map);

	/**
	 * 
	 * @Description: 判断供应商是否为激活状态
	 * @param @param map
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2019年3月12日 上午2:08:00
	 */
	String getActiveStatusByMap(Map<String, Object> map);

}
