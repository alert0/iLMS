package com.hanthink.sps.dao.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sps.dao.SpsMouldDao;
import com.hanthink.sps.model.SpsConfigItemModel;
import com.hanthink.sps.model.SpsConfigModel;
import com.hanthink.sps.model.SpsMouldConfigModel;
import com.hanthink.sps.model.SpsMouldModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: SpsMouldDaoImpl
 * @Description: SPS票据模板管理
 * @author dtp
 * @date 2018年11月21日
 */
@Repository
public class SpsMouldDaoImpl extends MyBatisDaoImpl<String, SpsMouldModel> implements SpsMouldDao{

	@Override
	public String getNamespace() {
		return SpsMouldModel.class.getName();
	}

	/**
	 * @Description: 模板列表查询  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsMouldModel>   
	 * @author: dtp
	 * @date: 2018年11月21日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SpsMouldModel> querySpsMouldPage(SpsMouldModel model, DefaultPage page) {
		return (PageList<SpsMouldModel>) this.getList("querySpsMouldPage", model, page);
	}

	/**
	 * @Description: 修改模板列表
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月21日
	 */
	@Override
	public void updateSpsMould(SpsMouldModel model) {
		this.updateByKey("updateSpsMould", model);
	}

	/**
	 * @Description: 配置列表查询 
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月21日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SpsMouldConfigModel> querySpsMouldConfigPage(SpsMouldConfigModel model, DefaultPage page) {
		return (PageList<SpsMouldConfigModel>) this.getList("querySpsMouldConfigPage", model, page);
	}

	/**
	 * @Description:   
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月22日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsMouldConfigModel> querySpsMouldConfigList(SpsMouldConfigModel model) {
		return (List<SpsMouldConfigModel>) this.getList("querySpsMouldConfigPage", model);
	}

	/**
	 * @Description: 修改配置信息 
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月22日
	 */
	@Override
	public void updateSpsMouldConfig(SpsMouldConfigModel model) {
		this.updateByKey("updateSpsMouldConfig", model);
	}

	/**
	 * @Description: SPS票据模板管理配置列表导入临时表 
	 * @param: @param importList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@Override
	public void insertSpsMouldConfigTempData(List<SpsMouldConfigModel> importList) {
		this.insertBatchByKey("insertSpsMouldConfigTempData", importList);
	}

	/**
	 * @Description: 调用存储过程等检查数据准确性  
	 * @param: @param ckParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".checkImportData", ckParamMap);
	}

	/**
	 * @Description: SPS配置列表临时表导入到正式表  
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@Override
	public void insertImportData(Map<String, String> paramMap) {
		this.updateByKey("insertImportData", paramMap);
	}

	/**
	 * @Description: 查询临时表数据
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SpsMouldConfigModel> queryImportTempPage(SpsMouldConfigModel model, DefaultPage page) {
		return (PageList<SpsMouldConfigModel>) this.getList("queryImportTempPage", model, page);
	}

	/**
	 * @Description: 导出临时表数据 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsMouldConfigModel> queryImportTempList(SpsMouldConfigModel model) {
		return (List<SpsMouldConfigModel>) this.getList("queryImportTempPage", model);
	}

	/**
	 * @Description: 更新临时表导入状态 
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@Override
	public void updateImportDataImpStatus(Map<String, String> paramMap) {
		this.updateByKey("updateImportDataImpStatus", paramMap);
	}

	/**
	 * @Description: 根据UUID删除临时表数据 
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteImportTempDataByUUID", uuid);
	}

	/**
	 * @Description: 上传图片后保存fileId
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年11月22日
	 */
	@Override
	public void updateSpsMouldConfigFileId(SpsMouldConfigModel model) {
		this.updateByKey("updateSpsMouldConfigFileId", model);
	}

	/**
	 * @Description: 获取SPS配置项与ID转换
	 * @param: @return    
	 * @return: List<SpsConfigItemModel>   
	 * @author: dtp
	 * @date: 2018年12月26日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsConfigModel> querySpsConfigList(SpsConfigModel model) {
		return (List<SpsConfigModel>) this.getList("querySpsConfigList", model);
	}

	/**
	 * @Description: 查询校验结果是否包含不通过  
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2018年12月30日
	 */
	@Override
	public int queryIsExistsCheckResultFalse(String uuid) {
		return  Integer.parseInt(this.getOne("queryIsExistsCheckResultFalse", uuid).toString()); 
	}

	/**
	 * @Description: 新增票据模板  
	 * @param: @param model
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年1月12日
	 */
	@Override
	public void insertSpsMould(SpsMouldModel model) {
		this.insertByKey("insertSpsMould", model);
	}

	/**
	 * @Description: 配置列表删除   
	 * @param: @param spsMouldConfigModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年1月21日
	 */
	@Override
	public void deleteSpsMouldConfig(SpsMouldConfigModel spsMouldConfigModel) {
		this.deleteByKey("deleteSpsMouldConfig", spsMouldConfigModel);
	}

	/**
	 * @Description:  新增票据模板配置列表 
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月28日
	 */
	@Override
	public void insertSpsMouldConfig(SpsMouldConfigModel model) {
		this.insertByKey("insertSpsMouldConfig", model);
	}

	/**
	 * @Description:  查询业务主键是否冲突 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsMouldConfigModel>   
	 * @author: dtp
	 * @date: 2019年3月23日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpsMouldConfigModel> queryIsExists(SpsMouldConfigModel model) {
		return (List<SpsMouldConfigModel>) this.getList("queryIsExists", model);
	}

}
