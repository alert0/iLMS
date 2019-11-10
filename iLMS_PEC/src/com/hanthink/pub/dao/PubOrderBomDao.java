package com.hanthink.pub.dao;
import java.util.List;

import com.hanthink.pub.model.PubOrderBomModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：单车BOM信息查询DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubOrderBomDao extends Dao<String, PubOrderBomModel> {
	/**
	 * 分页查询
	 * <p>return: PageList<PubOrderBomModel></p>  
	 * <p>Description: PubOrderBomDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<PubOrderBomModel> queryPubOrderBomForPage(PubOrderBomModel model, DefaultPage p);

	/**
	 * 导出查询
	 * @param model
	 * @return
	 */
	List<PubOrderBomModel> queryPubOrderBomByKey(PubOrderBomModel model);

	/**
	 * 不汇总查询单车BOM
	 * @param model
	 * @param p
	 * @return
	 */
	PageList<PubOrderBomModel> queryNotSummaryOrderBom(PubOrderBomModel model,
			DefaultPage p);

	/**
	 * 汇总查询单车BOM
	 * @param model
	 * @param p
	 * @return
	 */
	PageList<PubOrderBomModel> querySummaryOrderBom(PubOrderBomModel model,
			DefaultPage p);

	List<PubOrderBomModel> queryNotSummaryOrderBom(PubOrderBomModel model);

	List<PubOrderBomModel> querySummaryOrderBom(PubOrderBomModel model);
}
