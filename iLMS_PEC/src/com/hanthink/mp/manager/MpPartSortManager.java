package com.hanthink.mp.manager;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.MpPartSortModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：零件分组 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpPartSortManager extends Manager<String, MpPartSortModel>{

	 /**
     * 分页查询零件分组
     * @param model
     * @param p
     * @return
     */
    PageList<MpPartSortModel> queryMpPartSortForPage(MpPartSortModel model, DefaultPage p);
	
	/**
	 * 导出零件分组
	 * @param model
	 * @return
	 */
	List<MpPartSortModel> queryMpPartSortByKey(MpPartSortModel model);

	/**
	 * 获取计算队列
	 * @param model
	 * @return
	 */
	List<DictVO> getUnloadPort();

}
