package com.hanthink.gps.pub.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hanthink.gps.pub.dao.SysParamDao;
import com.hanthink.gps.pub.vo.DataDictVO;
import com.hanthink.gps.util.SpringContextUtil;
/**
 * @Desc    : 系统参数Service
 * @FileName: PubSysParamServer.java 
 * @CreateOn: 2016-7-13 上午10:44:07
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-13	V1.1		zuosl		修改使用SpringContextUtil获取bean
 * 
 *
 */
public class PubSysParamServer {
	
	private static String SYS_DAO_BEAN_NAME = "pubSysParamDao";
	
	private static SysParamDao dao;
	
	static{
//		org.springframework.beans.factory.BeanFactory beanFactory = 
//			(org.springframework.beans.factory.BeanFactory)ActionContext.getContext().getApplication().get(org.springframework.web.context.WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		dao = (SysParamDao)SpringContextUtil.getBean(SYS_DAO_BEAN_NAME);
	}
	
	/**
	 * 查询系统参数
	 * @param paramCode
	 * @return
	 */
	public static String querySysParam(String paramCode){
		if(null == dao){
//			org.springframework.beans.factory.BeanFactory beanFactory = 
//				(org.springframework.beans.factory.BeanFactory)ActionContext.getContext().getApplication().get(org.springframework.web.context.WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			dao = (SysParamDao)SpringContextUtil.getBean(SYS_DAO_BEAN_NAME);
		}
		return dao.querySysParam(paramCode);
	}

	/**
	 * 查询数据字典信息
	 * @param codeType
	 * @return
	 */
	public static Map<String, String> queryDictMapByCodeType(String codeType) {
		if(null == dao){
//			org.springframework.beans.factory.BeanFactory beanFactory = 
//				(org.springframework.beans.factory.BeanFactory)ActionContext.getContext().getApplication().get(org.springframework.web.context.WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			dao = (SysParamDao)SpringContextUtil.getBean(SYS_DAO_BEAN_NAME);
		}
		
		List<DataDictVO> dictList = dao.queryDictMapByCodeType(codeType);
		if(null == dictList){
			return null;
		}
		Map<String, String> dictMap = new HashMap<String, String>();
		for(DataDictVO dictvo : dictList){
			dictMap.put(dictvo.getCodeValue(), dictvo.getCodeValueName());
		}
		return dictMap;
	}
	
	/**
	 * 查询数据字典信息(key-value反转)
	 * @param codeType
	 * @return
	 */
	public static Map<String, String> queryDictMapKeyValueReversalByCodeType(String codeType) {
		if(null == dao){
//			org.springframework.beans.factory.BeanFactory beanFactory = 
//				(org.springframework.beans.factory.BeanFactory)ActionContext.getContext().getApplication().get(org.springframework.web.context.WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			dao = (SysParamDao)SpringContextUtil.getBean(SYS_DAO_BEAN_NAME);
		}
		
		List<DataDictVO> dictList = dao.queryDictMapByCodeType(codeType);
		if(null == dictList){
			return null;
		}
		Map<String, String> dictMap = new HashMap<String, String>();
		for(DataDictVO dictvo : dictList){
			dictMap.put(dictvo.getCodeValueName(), dictvo.getCodeValue());
		}
		return dictMap;
	}
	
}
