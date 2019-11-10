package com.hanthink.pub.dao;
import java.util.List;

import com.hanthink.pub.model.PubPartModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：物料主数据查询DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubPartDao extends Dao<String, PubPartModel> {
	/**
	 * 分页查询
	 * <p>return: PageList<PubPartModel></p>  
	 * <p>Description: PubPartDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<PubPartModel> queryPubPartForPage(PubPartModel model, DefaultPage p);

	/**
	 * 导出查询
	 * @param model
	 * @return
	 */
	List<PubPartModel> queryPubPartByKey(PubPartModel model);
}
