package com.hanthink.pub.manager;

import java.util.LinkedHashMap;
import java.util.List;

import com.hanthink.pub.model.PubSysParamModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：剩余量主数据 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubSysParamManager extends Manager<String, PubSysParamModel>{

	 /**
     * 分页查询零件剩余量主数据
     * @param model
     * @param p
     * @return
     */
    PageList<PubSysParamModel> queryPubSysParamForPage(PubSysParamModel model, DefaultPage p);
	
	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param opeIp
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:40
	 */
	void updateAndLog(PubSysParamModel pubSysParamModel, String opeIp);

	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 上午11:00:04
	 */
	void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception;

	/**
	 * 主键冲突
	 * @param pubSysParamModel
	 * @return
	 */
	Integer selectPrimaryKey(PubSysParamModel pubSysParamModel);

	/**
	 * 校验SQL
	 * <p>return: void</p>  
	 * <p>Description: PubSysParamManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月27日
	 * @version 1.0
	 */
	Integer sheckParamVal(String sql);

	/**
	 * 用户修改
	 * <p>return: void</p>  
	 * <p>Description: PubSysParamManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月28日
	 * @version 1.0
	 */
	void userUpdateAndLog(PubSysParamModel pubSysParamModel, String ipAddr);
	
}
