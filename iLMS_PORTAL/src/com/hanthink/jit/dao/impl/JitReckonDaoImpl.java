package com.hanthink.jit.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.jit.dao.JitReckonDao;
import com.hanthink.jit.model.JitPartRemainLogModel;
import com.hanthink.jit.model.JitPartRemainModel;
import com.hanthink.jit.model.JitPartRemainProdModel;
import com.hanthink.jit.model.JitVehQueueModel;
import com.hanthink.pub.model.PubPlanCodeModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitReckonDaoImpl
 * @Description: 拉动推算控制台
 * @author dtp
 * @date 2018年9月21日
 */
@Repository
public class JitReckonDaoImpl extends MyBatisDaoImpl<String, JitPartRemainModel> implements JitReckonDao{

	@Override
	public String getNamespace() {
		return JitPartRemainModel.class.getName();
	}

	/**
	 * @Description: 过点车序查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitVehQueueModel> queryJitVehQueuePage(JitVehQueueModel model, DefaultPage page) {
		return (PageList<JitVehQueueModel>) this.getList("queryJitVehQueuePage", model, page);
	}

	/**
	 * @Description: 过点车序导出excel 
	 * @param: @return    
	 * @return: List<JitVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitVehQueueModel> queryJitVehQueueList(JitVehQueueModel model) {
		return (List<JitVehQueueModel>) this.getList("queryJitVehQueuePage", model);
	}

	/**
	 * @Description: 当前零件余量查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitPartRemainModel> queryJitPartRemainPage(JitPartRemainModel model, DefaultPage page) {
		return (PageList<JitPartRemainModel>) this.getList("queryJitPartRemainPage", model, page);
	}

	/**
	 * @Description: 当前零件余量导出excel 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitPartRemainModel> queryJitPartRemainList(JitPartRemainModel model) {
		return (List<JitPartRemainModel>) this.getList("queryJitPartRemainPage", model);
	}

	/**
	 * @Description: 截止产品编号零件余量查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitPartRemainProdModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitPartRemainProdModel> queryJitPartRemainProdPage(JitPartRemainProdModel model, DefaultPage page) {
		return (PageList<JitPartRemainProdModel>) this.getList("queryJitPartRemainProdPage", model, page);
	}

	/**
	 * @Description: 截止产品编号零件余量导出excel  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainProdModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitPartRemainProdModel> queryJitPartRemainProdList(JitPartRemainProdModel model) {
		return (List<JitPartRemainProdModel>) this.getList("queryJitPartRemainProdPage", model);
	}

	/**
	 * @Description: 零件余量修改日志查询   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitPartRemainLogModel>   
	 * @author: dtp
	 * @date: 2018年9月21日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitPartRemainLogModel> queryJitPartRemainLogPage(JitPartRemainLogModel model, DefaultPage page) {
		return (PageList<JitPartRemainLogModel>) this.getList("queryJitPartRemainLogPage", model, page);
	}

	/**
	 * @Description: 新增零件余量
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月25日
	 */
	@Override
	public void savePartRemain(JitPartRemainModel model) {
		this.insertByKey("savePartRemain", model);
	}

	/**
	 * @Description: 修改零件余量  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月25日
	 */
	@Override
	public void updatePartRemain(JitPartRemainModel model) {
		this.updateByKey("updatePartRemain", model);
	}

	/**
	 * @Description: 校验业务主键唯一性，信息点、配送地址、零件号 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月25日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitPartRemainModel> queryJitPartRemainUnique(JitPartRemainModel model) {
		return (List<JitPartRemainModel>) this.getList("queryJitPartRemainUnique", model);
	}

	/**
	 * @Description: 查询当前零件余量 
	 * @param: @param model
	 * @param: @return    
	 * @return: JitPartRemainModel   
	 * @author: dtp
	 * @date: 2018年9月25日
	 */
	@Override
	public JitPartRemainModel queryPartRemain(JitPartRemainModel model) {
		return this.getUnique("queryPartRemain", model);
	}

	/**
	 * @Description: 零件余量导入写入临时表 
	 * @param: @param importList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月26日
	 */
	@Override
	public void insertJitPartRemainTempData(List<JitPartRemainModel> importList) {
		this.insertBatchByKey("insertJitPartRemainTempData", importList);
	}

	/**
	 * @Description:调用存储过程校验批量导入的零件余量 
	 * @param: @param ckParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月26日
	 */
	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".checkImportData", ckParamMap);
	}
	
	/**
	 * @Description: 临时表数据信息写入到正式表
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@Override
	public void insertImportData(Map<String, String> paramMap) {
		this.insertByKey("insertImportData", paramMap);
	}

	/**
	 * @Description: 更新临时表导入状态
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@Override
	public void updateImportDataImpStatus(Map<String, String> paramMap) {
		this.updateByKey("updateImportDataImpStatus", paramMap);
	}

	/**
	 * @Description: 查询临时表数据-零件余量 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitPartRemainModel> queryImportTempPage(JitPartRemainModel model, DefaultPage page) {
		return (PageList<JitPartRemainModel>) this.getList("queryImportTempPage", model, page);
	}

	/**
	 * @Description: 导出临时表数据-零件余量 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitPartRemainModel>   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitPartRemainModel> queryImportTempList(JitPartRemainModel model) {
		return (List<JitPartRemainModel>) this.getList("queryImportTempPage", model);
	}

	/**
	 * @Description: 拉动推算控制台推算服务状态查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<PubPlanCodeModel>   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<PubPlanCodeModel> queryJitReckonStatePage(PubPlanCodeModel model, DefaultPage page) {
		return (PageList<PubPlanCodeModel>) this.getList("queryJitReckonStatePage", model, page);
	}

	/**
	 * @Description: 加载信息点下拉框
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubPlanCodeModel>   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubPlanCodeModel> loadPlanCodeComboData(PubPlanCodeModel model) {
		return (List<PubPlanCodeModel>) this.getList("loadPlanCodeComboData", model);
	}

	/**
	 * @Description: 更新推算状态
	 * @param: @param pubPlanCodeModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年9月27日
	 */
	@Override
	public void updateReckonExecState(PubPlanCodeModel pubPlanCodeModel) {
		this.updateByKey("updateReckonExecState", pubPlanCodeModel);
	}

	/**
	 * @Description: 根据uuid删除导入临时数据 
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月4日
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteImportTempDataByUUID", uuid);
	}

	/**
	 * @Description: 根据planCode查询推算状态  
	 * @param: @param model
	 * @param: @return    
	 * @return: PubPlanCodeModel   
	 * @author: dtp
	 * @date: 2018年11月14日
	 */
	@Override
	public PubPlanCodeModel queryReckonState(PubPlanCodeModel model) {
		return (PubPlanCodeModel) this.getOne("queryReckonState", model);
	}

	/**
	 * @Description: 根据id查询修改前零件余量信息 
	 * @param: @param model
	 * @param: @return    
	 * @return: JitPartRemainModel   
	 * @author: dtp
	 * @date: 2018年12月3日
	 */
	@Override
	public JitPartRemainModel queryOldPartRemain(JitPartRemainModel model) {
		return this.getUnique("queryOldPartRemain", model);
	}

	/**
	 * @Description: 保存零件余量修改日志
	 * @param: @param logModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月3日
	 */
	@Override
	public void insertJitPartRemainLog(JitPartRemainLogModel logModel) {
		this.insertByKey("insertJitPartRemainLog", logModel);
	}

	/**
	 * @Description: 查询导入校验结果  
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年1月23日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return Integer.parseInt(this.getOne("queryIsExistsCheckResultFalse", uuid).toString());
	}

}
