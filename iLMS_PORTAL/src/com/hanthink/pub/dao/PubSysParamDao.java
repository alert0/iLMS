package com.hanthink.pub.dao;
import java.util.LinkedHashMap;
import java.util.List;

import com.hanthink.pub.model.PubSysParamModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：剩余量主数据 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubSysParamDao extends Dao<String, PubSysParamModel> {
	
	 /**
     * 分页查询零件剩余量主数据
     * @param model
     * @param p
     * @return
     */
    List<PubSysParamModel> queryPubSysParamForPage(PubSysParamModel model, DefaultPage p);

	/**
	 * 批量删除数据
	 * <p>return: void</p>  
	 * <p>Description: PubSysParamDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月30日
	 * @version 1.0
	 * @throws Exception 
	 */
	void deleteByIds(String[] aryIds) throws Exception;

	/**
	 * 主键冲突
	 * @param pubSysParamModel
	 * @return
	 */
	Integer selectPrimaryKey(PubSysParamModel pubSysParamModel);
	
	/**
	 * 校验SQL
	 * <p>return: List<LinkedHashMap<String,Object>></p>  
	 * <p>Description: PubSysParamDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月27日
	 * @version 1.0
	 */
	Integer sheckParamVal(String sql);

	/**
	 * 用户修改
	 * <p>return: void</p>  
	 * <p>Description: PubSysParamDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月28日
	 * @version 1.0
	 */
	void userUpdate(PubSysParamModel pubSysParamModel);
}
