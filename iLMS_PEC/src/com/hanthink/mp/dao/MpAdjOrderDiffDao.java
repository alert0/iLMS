package com.hanthink.mp.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.MpAdjOrderDiffModel;

import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：计划对比  DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpAdjOrderDiffDao extends Dao<String, MpAdjOrderDiffModel> {
	
	 /**
     * 分页查询计划对比
     * @param model
     * @param p
     * @return
     */
    List<MpAdjOrderDiffModel> queryMpAdjOrderDiffForPage(MpAdjOrderDiffModel model, DefaultPage p);
	
	/**
	 * 导出数据的查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<MpAdjOrderDiffModel> queryMpAdjOrderDiffByKey(MpAdjOrderDiffModel model);

	/**
	 * 获取计算队列
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<DictVO> getUnloadPort();

	/**
	 * 执行计划对比
	 * <p>return: void</p>  
	 * <p>Description: MpAdjOrderDiffDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月25日
	 * @version 1.0
	 * @return 
	 */
	Integer getAdjOrderDiff(String curFactoryCode, String account);

	/**
	 * 根据供应商代码查询相关信息
	 * <p>return: String</p>  
	 * <p>Description: MpAdjOrderDiffDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月8日
	 * @version 1.0
	 */
	List<MpAdjOrderDiffModel> queryEmail(String supplier);

	/**
	 * 向信息共享平台供应商能力反馈表批量插入数据
	 * <p>return: void</p>  
	 * <p>Description: MpAdjOrderDiffDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月9日
	 * @version 1.0
	 */
	void insertMpAdjSupFeedback(List<MpAdjOrderDiffModel> pageList);

	/**
	 * 更新发送标识
	 * <p>return: void</p>  
	 * <p>Description: MpAdjOrderDiffDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年11月7日
	 * @version 1.0
	 */
	void updateSendFlag(MpAdjOrderDiffModel idVo);

}
