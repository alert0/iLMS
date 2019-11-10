package com.hanthink.mp.manager;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.MpAdjSupFeedbackModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：供应商能力反馈 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpAdjSupFeedbackManager extends Manager<String, MpAdjSupFeedbackModel>{

	 /**
     * 分页查询供应商能力反馈
     * @param model
     * @param p
     * @return
     */
    PageList<MpAdjSupFeedbackModel> queryMpAdjSupFeedbackForPage(MpAdjSupFeedbackModel model, DefaultPage p);
	
	/**
	 * 导出供应商能力反馈
	 * @param model
	 * @return
	 */
	List<MpAdjSupFeedbackModel> queryMpAdjSupFeedbackByKey(MpAdjSupFeedbackModel model);

	/**
	 * 提交
	 * @param model
	 * @return
	 */
	void getCommit(String ipAddr,MpAdjSupFeedbackModel model);

}
