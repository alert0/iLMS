package com.hanthink.pub.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.pub.model.PubDataDictModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：生产日历 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubDataDictManager extends Manager<String, PubDataDictModel>{

	/**
	 * 执行分页查询
	 * <p>return: PageList<PubDataDictModel></p>  
	 * <p>Description: PubDataDictManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<PubDataDictModel> queryPubDataDictForPage(PubDataDictModel model, DefaultPage p);

	/**
	 * 右侧栏位显示查询结果
	 * <p>return: List<PubDataDictModel></p>  
	 * <p>Description: PubDataDictManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 * @param p 
	 */
	List<PubDataDictModel> getRowClick(Map<String, String> map, DefaultPage p);

	/**
	 * 左侧新增
	 * <p>return: void</p>  
	 * <p>Description: PubDataDictManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年11月24日
	 * @version 1.0
	 * @throws Exception 
	 */
	void insertLeft(PubDataDictModel pubDataDictModel) throws Exception;

	/**
	 * 右侧新增
	 * <p>return: void</p>  
	 * <p>Description: PubDataDictManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年11月24日
	 * @version 1.0
	 * @throws Exception 
	 */
	void insertRight(PubDataDictModel pubDataDictModel) throws Exception;

	/**
	 * 批量删除
	 * <p>return: void</p>  
	 * <p>Description: PubDataDictManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年11月24日
	 * @version 1.0
	 * @throws Exception 
	 */
	void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception;

	/**
	 * 更新
	 * <p>return: void</p>  
	 * <p>Description: PubDataDictManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年11月24日
	 * @version 1.0
	 */
	void updateAndLog(PubDataDictModel pubDataDictModel, String ipAddr);

	
    /**
	 * @Description: 根据数据字典类型查询数据字典信息(key-value反转)  
	 * @param: @param codeType
	 * @param: @return    
	 * @return: Map<String, String>  
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	Map<String, String> queryDataDictByCodeType(String codeType);
	
	/**
	 * @Description: 根据数据字典类型和codeValue查codeValueName
	 * @param: @param codeType
	 * @param: @return    
	 * @return: String   
	 * @author: dtp
	 * @date: 2018年12月3日
	 */
	String queryDataDictCodeValueName(String codeType, String codeValue);
	
}
