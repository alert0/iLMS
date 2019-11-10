package com.hanthink.pub.manager;

import java.util.List;

import com.hanthink.pub.model.PubPrTransLogModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：打印传输日志表 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubPrTransLogManager extends Manager<String, PubPrTransLogModel>{

	/**
	 * 执行分页查询
	 * <p>return: PageList<PubPrTransLogModel></p>  
	 * <p>Description: PubPrTransLogManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<PubPrTransLogModel> queryPubPrTransLogForPage(PubPrTransLogModel model, DefaultPage p);
}
