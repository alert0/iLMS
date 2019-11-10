package com.hanthink.jit.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.jit.model.JitPartRemainLogModel;
import com.hanthink.jit.model.JitPartRemainModel;
import com.hanthink.jit.model.JitPartRemainProdModel;
import com.hanthink.jit.model.JitVehQueueModel;
import com.hanthink.pub.model.PubPlanCodeModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: JitReckonManager
 * @Description: 拉动推算控制台
 * @author dtp
 * @date 2018年9月21日
 */
public interface JitReckonManager extends Manager<String, JitPartRemainModel>{
	
	/**
	 * @Description: 过点车序查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	PageList<JitVehQueueModel> queryJitVehQueuePage(JitVehQueueModel model, DefaultPage page);

	/**
	 * @Description: 过点车序导出excel   
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	List<JitVehQueueModel> queryJitVehQueueList(JitVehQueueModel model);

	/**
	 * @Description: 当前零件余量查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	PageList<JitPartRemainModel> queryJitPartRemainPage(JitPartRemainModel model, DefaultPage page);

	/**
	 * @Description: 当前零件余量导出excel 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	List<JitPartRemainModel> queryJitPartRemainList(JitPartRemainModel model);

	/**
	 * @Description: 截止产品编号零件余量查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitPartRemainProdModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	PageList<JitPartRemainProdModel> queryJitPartRemainProdPage(JitPartRemainProdModel model, DefaultPage page);

	/**
	 * @Description: 截止产品编号零件余量导出excel  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainProdModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	List<JitPartRemainProdModel> queryJitPartRemainProdList(JitPartRemainProdModel model);

	/**
	 * @Description: 零件余量修改日志查询   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitPartRemainLogModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	PageList<JitPartRemainLogModel> queryJitPartRemainLogPage(JitPartRemainLogModel model, DefaultPage page);

	/**
	 * @Description: 新增零件余量
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月25日
	 */
	void savePartRemain(JitPartRemainModel model);

	/**
	 * @Description: 修改零件余量  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月25日
	 */
	void updatePartRemain(JitPartRemainModel model);

	/**
	 * @Description: 校验业务主键唯一性，信息点、配送地址、零件号 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月25日
	 */
	List<JitPartRemainModel> queryJitPartRemainUnique(JitPartRemainModel model);

	/**
	 * @Description: 查询当前零件余量 
	 * @param: @param model
	 * @param: @return    
	 * @return: JitPartRemainModel   
	 * @author: dtp
	 * @date: 2018年9月25日
	 */
	JitPartRemainModel queryPartRemain(JitPartRemainModel model);

	/**
	 * @Description: 零件余量excel批量导入
	 * @param: @param file
	 * @param: @param uuid
	 * @param: @param ipAddr
	 * @param: @return    
	 * @return: Map<String,Object>   
	 * @author: dtp
	 * @date: 2018年9月26日
	 */
	Map<String, Object> importJitPartRemain(MultipartFile file, String uuid, String ipAddr);

	/**
	 * @Description:  确定导入，将临时表数据写入到正式业务表(零件余量)
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	void insertImportData(Map<String, String> paramMap);

	/**
	 * @Description: 查询临时表数据-零件余量 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	PageList<JitPartRemainModel> queryImportTempPage(JitPartRemainModel model, DefaultPage page);

	/**
	 * @Description: 导出临时表数据-零件余量 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	List<JitPartRemainModel> queryImportTempList(JitPartRemainModel model);

	/**
	 * @Description: 拉动推算控制台推算服务状态查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<PubPlanCodeModel>   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	PageList<PubPlanCodeModel> queryJitReckonStatePage(PubPlanCodeModel model, DefaultPage page);

	/**
	 * @Description: 加载信息点下拉框
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubPlanCodeModel>   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	List<PubPlanCodeModel> loadPlanCodeComboData(PubPlanCodeModel model);

	/**
	 * @Description: 更新推算状态
	 * @param: @param pubPlanCodeModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	void updateReckonExecState(PubPlanCodeModel pubPlanCodeModel);

	/**
	 * @Description: 根据uuid删除导入临时数据 
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月4日
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * @Description: 根据planCode查询推算状态  
	 * @param: @param model
	 * @param: @return    
	 * @return: PubPlanCodeModel   
	 * @author: dtp
	 * @date: 2018年11月14日
	 */
	PubPlanCodeModel queryReckonState(PubPlanCodeModel model);

	/**
	 * @Description: 根据planCode查询推算状态  
	 * @param: @param model
	 * @param: @return    
	 * @return: PubPlanCodeModel   
	 * @author: dtp
	 * @date: 2018年11月14日
	 */
	PubPlanCodeModel queryReckonExecState(PubPlanCodeModel pubPlanCodeModel);

	/**
	 * @Description: 查询零件与配送地址关系是否存在
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年11月14日
	 */
	List<JitPartRemainModel> queryPartAndLocationIsExist(JitPartRemainModel model);

	/**
	 * @Description: 零件余量修改日志导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainProdModel>   
	 * @author: dtp
	 * @date: 2018年12月31日
	 */
	List<JitPartRemainProdModel> queryJitPartRemainLogList(JitPartRemainLogModel model);

	/**
	 * @Description:  拉动推算控制台信息点下拉框(添加数据权限) 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubPlanCodeModel>   
	 * @author: dtp
	 * @date: 2019年1月17日
	 */
	List<PubPlanCodeModel> loadJitReckonPlanCodeComboData(PubPlanCodeModel model);

	/**
	 * @Description: 查询导入校验结果  
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年1月23日
	 */
	int queryIsExistsCheckResultFalse(String uuid);
	
}
