package com.hanthink.jit.dao;
import java.util.List;

import com.hanthink.jit.model.JitVehScrapModel;
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
public interface JitVehScrapDao extends Dao<String, JitVehScrapModel> {
	/**
	 * 分页查询
	 * <p>return: PageList<JitVehScrapModel></p>  
	 * <p>Description: JitVehScrapDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<JitVehScrapModel> queryJitVehScrapForPage(JitVehScrapModel model, DefaultPage p);

	/**
	 * 导出查询的方法
	 * <p>return: List<JitVehScrapModel></p>  
	 * <p>Description: JitVehScrapDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月30日
	 * @version 1.0
	 */
	List<JitVehScrapModel> queryJitVehScrapByKey(JitVehScrapModel model);

	/**
	 * @Description: 手工补看板 
	 * @param: @param jitVehScrapModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月28日
	 */
	void updateAdjustKb(JitVehScrapModel jitVehScrapModel);
}
