package com.hanthink.mp.dao;
import java.util.List;

import com.hanthink.mp.model.MpDiffNumTempModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：W+1,W+2生产计划  DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpDiffNumTempDao extends Dao<String, MpDiffNumTempModel> {
	
	 /**
     * 分页查询供应商分组
     * @param model
     * @param p
     * @return
     */
    List<MpDiffNumTempModel> queryMpDiffNumTempForPage(MpDiffNumTempModel model, DefaultPage p);
	
	/**
	 * 导出数据的查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<MpDiffNumTempModel> queryMpDiffNumTempByKey(MpDiffNumTempModel model);

	/**
	 * 获取调整计划
	 * <p>return: Integer</p>  
	 * <p>Description: MpDiffNumTempDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月15日
	 * @version 1.0
	 */
	Integer getZsbDiffPlan(String curFactoryCode);

}
