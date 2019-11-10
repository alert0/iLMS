package com.hanthink.pub.manager;

import java.util.List;

import com.hanthink.pub.model.PubPartSupplierModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：物料供应商关系 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubPartSupplierManager extends Manager<String, PubPartSupplierModel>{

	/**
	 * 执行分页查询
	 * <p>return: PageList<PubPartSupplierModel></p>  
	 * <p>Description: PubPartSupplierManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<PubPartSupplierModel> queryPubPartSupplierForPage(PubPartSupplierModel model, DefaultPage p);

	/**
	 * 导出查询
	 * @param model
	 * @return
	 */
	List<PubPartSupplierModel> queryPubPartSupplierByKey(PubPartSupplierModel model);
}
