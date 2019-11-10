package com.hanthink.jisi.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.jisi.dao.JisiPartGroupDao;
import com.hanthink.jisi.model.JisiPartGroupModel;
import com.hanthink.pup.model.PupProPlanModel;
import com.hanthink.util.constant.Constant;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

import sun.nio.cs.ext.TIS_620;
@Repository
public class JisiPartGroupDaoImpl extends MyBatisDaoImpl<String, JisiPartGroupModel> implements JisiPartGroupDao{

	@Override
	public String getNamespace() {
		
		return JisiPartGroupModel.class.getName();
	}

	/**
	 * 
	 * @Description: 厂内同步零件组维护分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月5日 下午9:25:04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JisiPartGroupModel> queryJisiPartGroupForPage(JisiPartGroupModel model, DefaultPage p) {
		
		return (PageList<JisiPartGroupModel>) this.getList("queryJisiPartGroupForPage", model, p);
	}

	/**
	 * 
	 * @Description: 判断零件组代码是否已经存在
	 * @param @param partGroup
	 * @param @return   
	 * @return List<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月8日 下午4:17:28
	 */
	@Override
	public List<JisiPartGroupModel> getPartGroupByCode(String partGroup) {
		IUser user = ContextUtil.getCurrentUser();
		JisiPartGroupModel model = new JisiPartGroupModel();
		model.setFactoryCode(user.getCurFactoryCode());
		model.setPartGroupNo(partGroup);
		return this.getByKey("queryJisiPartGroupForPage", model);
	}

	/**
	 * 
	 * @Description: 查询出需要导出的数据
	 * @param @param model
	 * @param @return   
	 * @return List<JisiPartGroupModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月8日 下午4:54:03
	 */
	@Override
	public List<JisiPartGroupModel> queryJisiPartGroupByKey(JisiPartGroupModel model) {
		
		return this.getByKey("queryJisiPartGroupForPage", model);
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
	public void deleteJisiPartGroupImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteJisiPartGroupImportTempDataByUUID", uuid);
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
	public void insertJisiPartGroupIntoTempData(List<JisiPartGroupModel> importList, String planCode) {
		List<JisiPartGroupModel> list = new ArrayList<JisiPartGroupModel>();
		for (JisiPartGroupModel model : importList) {
			if (Constant.IS.equals(model.getIsAutoPrintName())) {
				model.setIsAutoPrint(Constant.JISI_IS);
			}
			if (Constant.NO.equals(model.getIsAutoPrintName())) {
				model.setIsAutoPrint(Constant.JISI_NO);
			}
			model.setPlanCode(planCode);
			list.add(model);
		}
		this.insertBatchByKey("insertJisiPartGroupIntoTempData", list);
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
	public void checkJisiPartGroupImportDataInformation(Map<String, String> checkMap) {
		this.sqlSessionTemplate.selectOne(getNamespace()+".checkJisiPartGroupImportDataInformation", checkMap);
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
	public String queryJisiPartGroupImportFlag(String uuid) {
		
		return this.sqlSessionTemplate.selectOne(getNamespace()+".queryJisiPartGroupImportFlag", uuid);
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
	public PageList<JisiPartGroupModel> queryImportInformationForPage(Map<String, String> paramMap, Page page) {
		
		return (PageList<JisiPartGroupModel>) this.getByKey("queryImportInformationForPage", paramMap, page);
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
	public List<JisiPartGroupModel> queryImportInformationForExport(String uuid) {
		IUser user = ContextUtil.getCurrentUser();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid",uuid);
		map.put("factoryCode",user.getCurFactoryCode());
		return this.getByKey("queryImportInformationForPage", map);
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
	public void updateJisiPartGroupPrintId(List<JisiPartGroupModel> importList) {
		for (JisiPartGroupModel jisiPartGroupModel : importList) {
			this.updateByKey("updateJisiPartGroupPrintId", jisiPartGroupModel);
		}
		
	}

	/**
	 * 
	 * @Description: 通过工厂和信息点类型获取信息点
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月11日 下午3:42:43
	 */
	@Override
	public String getPlanCode(JisiPartGroupModel model) {
		
		return this.sqlSessionTemplate.selectOne(getNamespace()+".getPlanCode", model);
	}

	/**
	 * @Description: 查询是否有校验结果不通过数据  
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


}
