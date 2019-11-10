package com.hanthink.pub.manager;

import com.hanthink.pub.model.PubPrJobModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：打印机任务配置 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubPrJobManager extends Manager<String, PubPrJobModel>{

	 /**
     * 分页查询打印机任务配置
     * @param model
     * @param p
     * @return
     */
    PageList<PubPrJobModel> queryPubPrJobForPage(PubPrJobModel model, DefaultPage p);
	
	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param opeIp
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:40
	 */
	void updateAndLog(PubPrJobModel PubPrJobModel, String opeIp);

	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 上午11:00:04
	 */
	void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception;
	
}
