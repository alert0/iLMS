package com.hanthink.mp.manager;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.MpSupplierSortModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：供应商分组 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpSupplierSortManager extends Manager<String, MpSupplierSortModel>{

	 /**
     * 分页查询供应商分组
     * @param model
     * @param p
     * @return
     */
    PageList<MpSupplierSortModel> queryMpSupplierSortForPage(MpSupplierSortModel model, DefaultPage p);
	
	/**
	 * 导出供应商分组
	 * @param model
	 * @return
	 */
	List<MpSupplierSortModel> queryMpSupplierSortByKey(MpSupplierSortModel model);

	/**
	 * 获取计算队列
	 * @param model
	 * @return
	 */
	List<DictVO> getUnloadPort();

}
