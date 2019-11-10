package com.hanthink.mon.manager;


import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.mon.model.BigStockKbModel;
import com.hanthink.mon.model.MonKbModel;
import com.hotent.base.api.Page;
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

	PageList<MonKbModel> queryForKbConfig(Page page)throws Exception;

	MonKbModel getLoactionShowMessage(String ip)throws Exception;

	void updateCurrentKbStatus(MonKbModel model)throws Exception;

	/**
	 * 大物备货看板
	 */
	/**
	 * 获取产品流水号
	 * <p>return: Integer</p>  
	 * <p>Description: MonKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月21日
	 * @version 1.0
	 */
	List<BigStockKbModel> getBatchNo(String kbCode);

	/**
	 * 根据批次获取单头
	 * <p>return: List<BigStockKbModel></p>  
	 * <p>Description: MonKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月21日
	 * @version 1.0
	 * @param factoryCode 
	 */
	List<BigStockKbModel> getBigStockKbAC(String distriProductSeqNo, String factoryCode, String workCenter);
	
	List<BigStockKbModel> getBigStockKbWC(String distriProductSeqNo, String factoryCode, String workCenter);

	/**
	 * 根据产品流水号获取批次
	 * <p>return: String</p>  
	 * <p>Description: MonKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月21日
	 * @version 1.0
	 * @param kbCode 
	 */
	String getDistriProductSeqNo(String kbId, String currProcessNo);

	/**
	 * 根据单头获取明细
	 * <p>return: List<BigStockKbModel></p>  
	 * <p>Description: MonKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年2月21日
	 * @version 1.0
	 */
	List<String> getBigStockKbDetail(String distriPerson);

	List<DictVO> queryDistriPersonForSelect(MonKbModel model)throws Exception;

	String queryWorkCenterByPlanCode(MonKbModel model)throws Exception;

	MonKbModel queryForKbConfig(MonKbModel model)throws Exception;
	
	/**
	 * @Description: 获取冲压配送指示   
	 * @param: @param model
	 * @param: @return    
	 * @return: List<MonKbModel>   
	 * @author: dtp
	 * @date: 2019年4月22日
	 */
	List<MonKbModel> queryStampKb(MonKbModel model);

	/**
	 * @Description: 获取电池车间指示    
	 * @param: @param model
	 * @param: @return    
	 * @return: List<MonKbModel>   
	 * @author: dtp
	 * @date: 2019年4月22日
	 */
	List<MonKbModel> queryBatteryKb(MonKbModel model);

	/**
	 * 用户维护的参数值
	 * @param productSeqNo
	 * @return
	 */
	String selectProductSeqNoByParamCode(String productSeqNo);

	/**
	 * 通过IP查询车间
	 * @param ip
	 * @return
	 */
	String selectWorkCenterByIp(String ip);

	/**
	 * 为空处理
	 * @return
	 */
	List<BigStockKbModel> selectBigStockKbByNull();

	/**
	 * 延迟判定
	 * @param currBatchNo
	 * @return
	 */
	List<String> delayDetermination(String currBatchNo);

	/**
	 * 查询看板代码
	 * @param paramMap
	 * @return
	 */
	String selectKbCodeByIp(Map<String, String> paramMap);

	List<String> getBigStockKbDetail(MonKbModel model);
}
