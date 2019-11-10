package com.hanthink.mon.dao;


import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.mon.model.BigStockKbModel;
import com.hanthink.mon.model.MonKbModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: MonKbDao
 * @Description:物流监控看板
 * @author luocc
 * @date 2018年11月3日
 */
public interface MonKbDao extends Dao<String, MonKbModel>{
	
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
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	void addMonBaseKbOne(MonKbModel model);
	
	/**
	 * @Description:基础看板信息单条修改
	 * @param: @param model
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	void modifyMonBaseKbOne(MonKbModel model);
	
	
	/**
	 * @Description:基础看板信息批量删除
	 * @param: @param ids
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	void removeByKbIds(List<MonKbModel> models);
	
	/**
	 * @Description:基础看板信息业务主键校验
	 * @param: @param model
	 * @param: @return Integer
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月3日
	 */
	Integer checkUnique(MonKbModel model);
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
	 * @Description:封装下拉框  工程
	 * @param: @param model
	 * @param: @return List<DictVO>
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	List<DictVO> queryForDistriPerson(MonKbModel model);
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
	 * @Description:看板明细信息修改
	 * @param: @param model
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	void modifyKbDetailOne(MonKbModel model);
	/**
	 * @Description:看板明细信息新增
	 * @param: @param model
	 * @param: @return    
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	void addKbDetailOne(MonKbModel model);
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
	 * @Description:看板明细业务主键校验
	 * @param: @param model
	 * @param: @return Integer
	 * @return: 
	 * @author: luocc
	 * @date: 2018年11月8日
	 */
	Integer checkKbId(MonKbModel model);
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
	void updateSkewProcessNo(MonKbModel model);

	List<BigStockKbModel> queryStockKbDetails(Map<String, String> paramMap)throws Exception;
}
