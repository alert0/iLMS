package com.hanthink.mp.dao;
import java.util.List;

import com.hanthink.mp.model.MpAdjSupFeedbackModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：供应商能力反馈  DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpAdjSupFeedbackDao extends Dao<String, MpAdjSupFeedbackModel> {
	
	 /**
     * 分页查询供应商能力反馈
     * @param model
     * @param p
     * @return
     */
    List<MpAdjSupFeedbackModel> queryMpAdjSupFeedbackForPage(MpAdjSupFeedbackModel model, DefaultPage p);
	
	/**
	 * 导出数据的查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<MpAdjSupFeedbackModel> queryMpAdjSupFeedbackByKey(MpAdjSupFeedbackModel model);

	/**
	 * 提交
	 * <p>return: void</p>  
	 * <p>Description: MpAdjSupFeedbackDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月21日
	 * @version 1.0
	 */
	void getCommit(MpAdjSupFeedbackModel model);


}
