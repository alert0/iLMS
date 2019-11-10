package com.hanthink.pub.dao;
import java.util.List;

import com.hanthink.pub.model.PubTactTimeModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：生产节拍查询DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubTactTimeDao extends Dao<String, PubTactTimeModel> {
	/**
	 * 分页查询
	 * <p>return: PageList<PubTactTimeModel></p>  
	 * <p>Description: PubTactTimeDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<PubTactTimeModel> queryPubTactTimeForPage(PubTactTimeModel model, DefaultPage p);

	/**
	 * 导出查询
	 * @param model
	 * @return
	 */
	List<PubTactTimeModel> queryPubTactTimeByKey(PubTactTimeModel model);
}
