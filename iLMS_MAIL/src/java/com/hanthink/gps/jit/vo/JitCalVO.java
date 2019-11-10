package com.hanthink.gps.jit.vo;

import java.io.Serializable;

/**
 * @Desc    : 拉动推算服务信息VO 
 * @FileName: JitCalVO.java 
 * @CreateOn: 2016-7-27 下午07:09:40
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 *
 */
public class JitCalVO  implements Serializable {

	private static final long serialVersionUID = -4870668387424528714L;
	
	/** 参数代码前缀 */
	public static final String MM_JIT_CAL_LOCK = "MM_JIT_CAL_LOCK_";
	
	/** 工厂代码 */
	private String factoryCode;
	/** 计算信息点 */
	private String jitPlanCode;
	/** 参数代码 */
	private String paramCode;
	/** 推算服务停止时长 */
	private Long stopTime;
	
	
	/**
	 * 获取工厂代码
	 * @return the factoryCode 工厂代码
	 */
	public String getFactoryCode() {
		return factoryCode;
	}
	/**
	 * 设置工厂代码
	 * @param factoryCode the factoryCode 工厂代码
	 */
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	/**
	 * 获取计算信息点
	 * @return the jitPlanCode 计算信息点
	 */
	public String getJitPlanCode() {
		return jitPlanCode;
	}
	/**
	 * 设置计算信息点
	 * @param jitPlanCode the jitPlanCode 计算信息点
	 */
	public void setJitPlanCode(String jitPlanCode) {
		this.jitPlanCode = jitPlanCode;
	}
	/**
	 * 获取参数代码
	 * @return the paramCode 参数代码
	 */
	public String getParamCode() {
		return paramCode;
	}
	/**
	 * 设置参数代码
	 * @param paramCode the paramCode 参数代码
	 */
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	/**
	 * 获取推算服务停止时长
	 * @return the stopTime 推算服务停止时长
	 */
	public Long getStopTime() {
		return stopTime;
	}
	/**
	 * 设置推算服务停止时长
	 * @param stopTime the stopTime 推算服务停止时长
	 */
	public void setStopTime(Long stopTime) {
		this.stopTime = stopTime;
	} 
	

}
