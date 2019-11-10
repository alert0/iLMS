package com.hanthink.pub.manager;

import java.util.List;

import com.hanthink.pub.model.PubSupplierModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：供应商主数据 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubSupplierManager extends Manager<String, PubSupplierModel>{

	/**
	 * 执行分页查询
	 * <p>return: PageList<PubSupplierModel></p>  
	 * <p>Description: PubSupplierManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<PubSupplierModel> queryPubSupplierForPage(PubSupplierModel model, DefaultPage p);

	/**
	 * 供应商主数据查询
	 * <p>return: List<PubSupplierModel></p>  
	 * <p>Description: PubSupplierManager.java</p>  
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
	 * @DATE	2018年11月28日 上午9:58:35
	 */
	PubSupplierModel querySupplierBySupplierNo(PubSupplierModel model);

	/**
	 * 行修改
	 * <p>return: void</p>  
	 * <p>Description: PubSupplierManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月23日
	 * @version 1.0
	 */
	void updateAndLog(PubSupplierModel pubSupplierModel, String ipAddr);
}
