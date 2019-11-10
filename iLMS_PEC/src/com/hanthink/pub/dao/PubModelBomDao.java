package com.hanthink.pub.dao;
import java.util.List;

import com.hanthink.pub.model.PubModelBomModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：BOM基础数据查询DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubModelBomDao extends Dao<String, PubModelBomModel> {
	/**
	 * 分页查询
	 * <p>return: PageList<PubModelBomModel></p>  
	 * <p>Description: PubModelBomDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<PubModelBomModel> queryPubModelBomForPage(PubModelBomModel model, DefaultPage p);

	/**
	 * 导出查询
	 * @param model
	 * @return
	 */
	List<PubModelBomModel> queryPubModelBomByKey(PubModelBomModel model);

	
}
