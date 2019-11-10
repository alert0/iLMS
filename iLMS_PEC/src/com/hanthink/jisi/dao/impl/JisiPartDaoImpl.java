package com.hanthink.jisi.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.jisi.dao.JisiPartDao;
import com.hanthink.jisi.model.JisiPartGroupModel;
import com.hanthink.jisi.model.JisiPartModel;
import com.hanthink.util.constant.Constant;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
@Repository
public class JisiPartDaoImpl extends MyBatisDaoImpl<String, JisiPartModel> implements JisiPartDao{

	@Override
	public String getNamespace() {
		
		return JisiPartModel.class.getName();
	}

	/**
	 * 
	 * @Description: 零件信息维护分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiPartModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午10:48:30
	 */
	@Override
	public PageList<JisiPartModel> queryJisiPartForPage(JisiPartModel model, DefaultPage p) {
		
		return (PageList<JisiPartModel>) this.getByKey("queryJisiPartForPage", model, p);
	}

	/**
	 * 
	 * @Description: 判断零件组代码和零件号是否已经存在
	 * @param @param partGroupNo
	 * @param @param partNo
	 * @param @return   
	 * @return List<JisiPartModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午10:55:30
	 */
	@Override
	public List<JisiPartModel> getJisiPartListByCode(String partGroupId, String partNo) {
		IUser user = ContextUtil.getCurrentUser();
		JisiPartModel model = new JisiPartModel();
		model.setPartGroupId(partGroupId);
		model.setPartNo(partNo);
		model.setFactoryCode(user.getCurFactoryCode());
		return this.getByKey("queryJisiPartForPage", model);
	}

	/**
	 * 
	 * @Description: 获取零件信息新增界面，零件组下拉框
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午11:12:21
	 */
	@Override
	public List<JisiPartModel> getJisiPartGroupIdUnload(JisiPartModel model) {
		return this.getByKey("getJisiPartGroupIdUnload", model);
	}

	/**
	 * 
	 * @Description: 查询需要导出的数据
	 * @param @param model
	 * @param @return   
	 * @return List<JisiPartModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 下午2:05:10
	 */
	@Override
	public List<JisiPartModel> queryJisiPartByKey(JisiPartModel model) {
		
		return this.getByKey("queryJisiPartForPage", model);
	}

	/**
	 * 
	 * @Description: 导入之前删除上次导入到临时表的数据
	 * @param @param uuid   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午2:05:55
	 */
	@Override
	public void deleteJisiPartImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteJisiPartImportTempDataByUUID", uuid);
	}

	/**
	 * 
	 * @Description: 数据写入临时表
	 * @param @param importList   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午2:55:49
	 */
	@Override
	public void insertJisiPartIntoTempData(List<JisiPartModel> importList) {
		
		this.insertBatchByKey("insertJisiPartIntoTempData", importList);
	}

	/**
	 * 
	 * @Description: 调用存储检验数据准确性
	 * @param @param checkMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午3:52:39
	 */
	@Override
	public void checkJisiPartImportDataInformation(Map<String, String> checkMap) {
		this.sqlSessionTemplate.selectOne(getNamespace()+".checkJisiPartImportDataInformation", checkMap);
	}

	/**
	 * 
	 * @Description: 检查是否可以批量导入
	 * @param @param uuid
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午4:33:23
	 */
	@Override
	public String queryJisiPartImportFlag(String uuid) {
		
		return this.sqlSessionTemplate.selectOne(getNamespace()+".queryJisiPartImportFlag", uuid);
	}

	/**
	 * 
	 * @Description: 分页查询导入到临时表的数据
	 * @param @param paramMap
	 * @param @param page
	 * @param @return   
	 * @return PageList<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午4:41:12
	 */
	@Override
	public PageList<JisiPartModel> queryImportInformationForPage(Map<String, String> paramMap, Page page) {
		
		return (PageList<JisiPartModel>) this.getByKey("queryImportInformationForPage", paramMap, page);
	}

	/**
	 * 
	 * @Description: 查询正确数据条数
	 * @param @param paramMap
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:27:30
	 */
	@Override
	public Integer getCountForImport(Map<String, String> paramMap) {
		
		return this.sqlSessionTemplate.selectOne(getNamespace()+".getCountForImport", paramMap);
	}

	/**
	 * 
	 * @Description: 删除之前数据表数据
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:30:48
	 */
	@Override
	public void deleteRegulaData(Map<String, String> paramMap) {
		this.deleteByKey("deleteRegulaData", paramMap);
	}

	/**
	 * 
	 * @Description: 临时表数据写入正式表
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:33:34
	 */
	@Override
	public void insertTempDataToRegula(Map<String, String> paramMap) {
		this.insertByKey("insertTempDataToRegula", paramMap);
	}

	/**
	 * 
	 * @Description: 更新临时表中导入状态
	 * @param @param paramMap   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:40:46
	 */
	@Override
	public void updateImportStatus(Map<String, String> paramMap) {
		this.updateByKey("updateImportStatus", paramMap);
	}

	/**
	 * 
	 * @Description: 导出Excel 在导入时候的所有数据
	 * @param @param uuid
	 * @param @return   
	 * @return List<PupProPlanModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:49:49
	 */
	@Override
	public List<JisiPartModel> queryImportInformationForExport(String uuid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid",uuid);
		return this.getByKey("queryImportInformationForPage", map);
	}

	/**
	 * 
	 * @Description: 根据工厂和信息点类型获取信息点
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月11日 下午9:42:38
	 */
	@Override
	public String getPlanCode(JisiPartModel model) {
		return this.sqlSessionTemplate.selectOne(getNamespace()+".getPlanCode", model);
	}

	/**
	 * @Description: 查询导入校验结果是否包含不通过 
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2019年1月27日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return Integer.parseInt(this.getOne("queryIsExistsCheckResultFalse", uuid).toString());
	}

	/**
	 * @Description: 判断零件号是否存在  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisiPartModel>   
	 * @author: dtp
	 * @date: 2019年3月28日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisiPartModel> queryPartNoIsExists(JisiPartModel model) {
		return this.getListByKey("queryPartNoIsExists", model);
	}
	
}
