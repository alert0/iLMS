package com.hanthink.pub.dao;
import java.util.List;

import com.hanthink.pub.model.PubSupplierModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：供应商主数据查询DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubSupplierDao extends Dao<String, PubSupplierModel> {
	/**
	 * 分页查询
	 * <p>return: PageList<PubSupplierModel></p>  
	 * <p>Description: PubSupplierDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<PubSupplierModel> queryPubSupplierForPage(PubSupplierModel model, DefaultPage p);

	/**
	 * 导出查询的方法
	 * <p>return: List<PubSupplierModel></p>  
	 * <p>Description: PubSupplierDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月30日
	 * @version 1.0
	 */
	List<PubSupplierModel> queryPubSupplierByKey(PubSupplierModel model);

	/**
	 * 通过供应商代码查询供应商信息
	 * @param model
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月28日 上午10:00:05
	 */
	PubSupplierModel qeurySupplierBySupplierNo(PubSupplierModel model);
}
