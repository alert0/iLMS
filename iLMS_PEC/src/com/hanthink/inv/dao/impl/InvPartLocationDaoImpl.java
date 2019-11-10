package com.hanthink.inv.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.inv.dao.InvPartLocationDao;
import com.hanthink.inv.model.InvPartLocationModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: InvPartLocationDaoImpl
 * @Description: 零件属地维护
 * @author dtp
 * @date 2019年2月16日
 */
@Repository
public class InvPartLocationDaoImpl extends MyBatisDaoImpl<String, InvPartLocationModel> 
	implements InvPartLocationDao{

	@Override
	public String getNamespace() {
		return InvPartLocationModel.class.getName();
	}

	/**
	 * @Description:  分页查询零件属地 数据 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<InvPartLocationModel> queryInvPartLocationPage(InvPartLocationModel model, DefaultPage page) {
		return (PageList<InvPartLocationModel>) this.getList("queryInvPartLocationPage", model, page);
	}

	/**
	 * @Description:  查询零件属地 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvPartLocationModel> queryInvPartLocationList(InvPartLocationModel model) {
		return this.getListByKey("queryInvPartLocationPage", model);
	}

	/**
	 * @Description:  零件数据维护查询货架标签打印信息 
	 * @param: @param model
	 * @param: @return    
	 * @return: InvPartLocationModel   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@Override
	public InvPartLocationModel queryInvShelfPrintInfo(InvPartLocationModel model) {
		return this.getUnique("queryInvShelfPrintInfo", model);
	}

	/**
	 * @Description: 根据uuid删除导入临时数据   
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteImportTempDataByUUID", uuid);
	}

	/**
	 * @Description: 根据卸货口获取仓库代码
	 * @param: @param m
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvPartLocationModel> queryWareCodeByOldUnloadPort(InvPartLocationModel m) {
		return (List<InvPartLocationModel>) this.getList("queryWareCodeByOldUnloadPort", m);
	}

	/**
	 * @Description: 根据卸货口获取仓库代码
	 * @param: @param m
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvPartLocationModel> queryWareCodeByNewUnloadPort(InvPartLocationModel m) {
		return (List<InvPartLocationModel>) this.getList("queryWareCodeByNewUnloadPort", m);
	}

	/**
	 * @Description: 导入数据到临时表
	 * @param: @param importList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@Override
	public void insertInvPartLocationTempData(List<InvPartLocationModel> importList) {
		this.insertBatchByKey("insertInvPartLocationTempData", importList);
	}

	/**
	 * @Description: 调用存储过程校验导入数据 
	 * @param: @param ckParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".checkImportData", ckParamMap);
	}

	/**
	 * @Description: 查询临时表数据
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<InvPartLocationModel> queryImportTempPage(InvPartLocationModel model, DefaultPage page) {
		return (PageList<InvPartLocationModel>) this.getList("queryImportTempPage", model, page);
	}

	/**
	 * @Description: 导出临时表数据 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvPartLocationModel> queryImportTempList(InvPartLocationModel model) {
		return (List<InvPartLocationModel>) this.getList("queryImportTempPage", model);
	}

	/**
	 * @Description: 查询导入校验结果
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return Integer.parseInt(this.getOne("queryIsExistsCheckResultFalse", uuid).toString());
	}

	/**
	 * @Description: 临时表写入正式表
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@Override
	public void insertImportDataAdd(Map<String, String> paramMap) {
		this.insertByKey("insertImportDataAdd", paramMap);
	}

	/**
	 * @Description:  更新临时表数据导入状态
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@Override
	public void updateImportDataImpStatus(Map<String, String> paramMap) {
		this.updateByKey("updateImportDataImpStatus", paramMap);
	}

	/**
	 * @Description:  临时表写入正式表 
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@Override
	public void updateImportData(Map<String, String> paramMap) {
		this.updateByKey("updateImportData", paramMap);
	}

	/**
	 * @Description: 查询废止数据 
	 * @param: @param paramMap
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvPartLocationModel> queryFZList(Map<String, String> paramMap) {
		return (List<InvPartLocationModel>) this.getList("queryFZList", paramMap);
	}

	/**
	 * @Description: 更新废止信息到正式表 
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@Override
	public void updateImportDataFZ(InvPartLocationModel model) {
		this.updateByKey("updateImportDataFZ", model);
	}

	/**
	 * @Description: 查询临时表移动数据
	 * @param: @param paramMap
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvPartLocationModel> queryYDList(Map<String, String> paramMap) {
		return (List<InvPartLocationModel>) this.getList("queryYDList", paramMap);
	}

	/**
	 * @Description: 更新移动数据到正式表  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月17日
	 */
	@Override
	public void updateImportDataYD(InvPartLocationModel model) {
		this.updateByKey("updateImportDataYD", model);
	}

	/**
	 * @Description: 零件属地维护日志查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月25日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<InvPartLocationModel> queryInvPartLocationLogPage(InvPartLocationModel model, DefaultPage page) {
		return (PageList<InvPartLocationModel>) this.getList("queryInvPartLocationLogPage", model, page);
	}

	/**
	 * @Description: 属地维护日志导入  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<InvPartLocationModel>   
	 * @author: dtp
	 * @date: 2019年2月25日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvPartLocationModel> queryInvPartLocationLogList(InvPartLocationModel model) {
		return this.getListByKey("queryInvPartLocationLogPage", model);
	}
	
	

}
