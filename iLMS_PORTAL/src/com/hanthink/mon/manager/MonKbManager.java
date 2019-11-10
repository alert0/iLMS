package com.hanthink.mon.manager;


import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.mon.model.BigStockKbModel;
import com.hanthink.mon.model.MonKbModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
/**
 * @ClassName: MonKbManager
 * @Description: 物流看板信息
 * @author luocc
 * @date 2018年11月3日
 */
public interface MonKbManager extends Manager<String,MonKbModel> {
	/**
	 * @Description:基础看板信息分页查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<MonKbModel>   
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	PageList<MonKbModel> queryMonBaseKbPage(MonKbModel model, DefaultPage page);
	
	/**
	 * @Description:基础看板信息单条新增
	 * @param: @param model
	 * @param: @return    
	 * @return: String
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	String addMonBaseKbOne(MonKbModel model);
	
	/**
	 * @Description:基础看板信息单条修改
	 * @param: @param model
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	void modifyMonBaseKbOne(MonKbModel model,String ipAddr);
	
	/**
	 * @Description:基础看板信息批量删除
	 * @param: @param ids
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	void removeByIds(String[] ids, String ipAddr);
	/**
	 * @Description:看板详情信息设置保存
	 * @param: @param model
	 * @param: @return    
	 * @return: String
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	String saveKbDetail(MonKbModel model);
	/**
	 * @Description:查询看板详情
	 * @param: @param model
	 * @param: @return MonKbModel
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	MonKbModel queryKbDetailOne(MonKbModel model);
	/**
	 * @Description:封装下拉框  看板名称
	 * @param: @param model
	 * @param: @return List<DictVO>
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	List<DictVO> queryForMonKbName(MonKbModel model);
	/**
	 * @Description:封装下拉框  工程
	 * @param: @param model
	 * @param: @return List<DictVO>
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	List<DictVO> queryForDistriPerson(MonKbModel model);
	/**
	 * @Description:封装下拉框  安灯灯号
	 * @param: @param model
	 * @param: @return List<DictVO>
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	List<DictVO> queryForLampId();
	/**
	 * @Description:查询供应商看板
	 * @param: @param model
	 * @param: @return MonKbModel
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月10日
	 */
	MonKbModel selectBatchBySeq(MonKbModel model);
	/**
	 * @Description:查询厂内拉动看板
	 * @param: @param model
	 * @param: @return MonKbModel
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月10日
	 */
	MonKbModel selectBatchBySeqAndSkew(MonKbModel model);
	/**
	 * @Description:设置厂内拉动偏移进度
	 * @param: @param model
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月10日
	 */
	void updateSkewProcessNo(MonKbModel model, String ipAddr);
	
	/**
	 * 获取当前用户的数据权限
	 * @param paraMap
	 * @throws Exception
	 */
	void queryLoguserJurisdiction(Map<String, String> paraMap)throws Exception;
	
	List<BigStockKbModel> queryStockKbDetails(Map<String, String> paramMap)throws Exception;

	
}
