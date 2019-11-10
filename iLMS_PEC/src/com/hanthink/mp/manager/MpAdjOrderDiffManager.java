package com.hanthink.mp.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.MpAdjOrderDiffModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：计划对比 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpAdjOrderDiffManager extends Manager<String, MpAdjOrderDiffModel>{

	 /**
     * 分页查询计划对比
     * @param model
     * @param p
     * @return
     */
    PageList<MpAdjOrderDiffModel> queryMpAdjOrderDiffForPage(MpAdjOrderDiffModel model, DefaultPage p);
	
	/**
	 * 导出计划对比
	 * @param model
	 * @return
	 */
	List<MpAdjOrderDiffModel> queryMpAdjOrderDiffByKey(MpAdjOrderDiffModel model);

	/**
	 * 执行计划对比
	 * <p>return: void</p>  
	 * <p>Description: MpAdjOrderDiffManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月25日
	 * @version 1.0
	 * @return 
	 */
	Integer getAdjOrderDiff(String curFactoryCode, String account);

	/**
	 * 根据供应商代码查询相关信息
	 * <p>return: void</p>  
	 * <p>Description: MpAdjOrderDiffManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月8日
	 * @version 1.0
	 * @return 
	 * @throws Exception 
	 */
	String sendMail(HttpServletRequest request,MpAdjOrderDiffModel[] model) throws Exception;

	/**
	 * 调整数量并记录日志
	 * <p>return: void</p>  
	 * <p>Description: MpAdjOrderDiffManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月31日
	 * @version 1.0
	 */
	void updateAndLog(MpAdjOrderDiffModel model, String ipAddr);

}
