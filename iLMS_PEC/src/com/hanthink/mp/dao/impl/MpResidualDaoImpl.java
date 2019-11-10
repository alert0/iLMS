package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mp.dao.MpResidualDao;
import com.hanthink.mp.model.MpResidualModel;
import com.hanthink.mp.model.MpResidualModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：剩余量主数据 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MpResidualDaoImpl extends MyBatisDaoImpl<String, MpResidualModel> implements MpResidualDao{

    @Override
    public String getNamespace() {
        return MpResidualModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<MpResidualModel> queryMpResidualForPage(MpResidualModel model, DefaultPage p) {
		 return this.getByKey("queryMpResidualForPage", model, p);
	}
    
    /**
	 * 查询数据表中的计算队列
	 * @param importList
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:11:33
	 */
	@Override
	public List getUnloadPort() {
		Map<String,Object> map=new HashMap();
		return this.getList("getUnloadPort", map);
	}

	/**
	 * 写入导入数据到临时表
	 * @param importList
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:11:33
	 */
	@Override
	public void insertMpResidualImportTempData(List<MpResidualModelImport> importList) {
		this.insertByKey("insertMpResidualImportTempData", importList);
	}

	/**
	 * 检查导入的临时数据信息
	 * @param ckParamMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:12:37
	 */
	@Override
	public void checkMpResidualImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkMpResidualImportData", ckParamMap);
	}

	/**
	 * 查询导入的临时数据信息
	 * @param params
	 * @param page
	 * @return
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:30:46
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageList<MpResidualModelImport> queryMpResidualImportTempData(Map<String, String> params,Page page) {
		return (PageList)this.getByKey("queryMpResidualImportTempData", params,page);
	}

	/**
	 * 临时表数据信息写入到正式表
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午12:31:10
	 */
	@Override
	public void insertMpResidualImportData(Map<String, Object> paramMap) {
		this.insertByKey("insertMpResidualImportData", paramMap);
	}

	/**
	 * 更新临时表导入状态
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:03:43
	 */
	@Override
	public void updateMpResidualImportDataImpStatus(Map<String, Object> paramMap) {
		this.updateByKey("updateMpResidualImportDataImpStatus", paramMap);
	}

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public void deleteMpResidualImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteMpResidualImportTempDataByUUID", uuid);
	}

	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpResidualModel> queryMpResidualByKey(MpResidualModel model) {
		return this.getByKey("queryMpResidualForPage", model);
	}
    
	/**
	 * 根据ID查询出哪些数据要更新导入
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<String> queryUpdateDataFromMpResidualImp(Map<String, Object> paramMap) {
		return this.sqlSessionTemplate.selectList(getNamespace()+".queryUpdateDataFromMpResidualImp", paramMap);
	}

	/**
	 * 更新导入的方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public void updateMpResidualImportData(Map<String, Object> paramMap) {
		this.updateByKey("updateMpResidualImportData", paramMap);
	}
 
	/**
	 * 查询是否可以批量导入
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
    public String queryMpResidualIsImportFlag(String uuid) {
	    return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryMpResidualIsImportFlag", uuid);
    }

	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpResidualModelImport> queryMpResidualImportTempDataForExport(Map<String, String> paramMap) {
		return this.sqlSessionTemplate.selectList("queryMpResidualImportTempData", paramMap);
	}

	/**
	 * 查询可导入的数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MpResidualModelImport> queryForInsertList(Map<String, Object> paramMap) {
		return (List<MpResidualModelImport>) this.getList("queryForInsertList", paramMap);
	}

    /**
     * 批量删除数据
     */
	@Override
	public void deleteByIds(String[] aryIds) throws Exception{
		this.deleteByKey("deleteByIds", aryIds);
	}

	/**
	 * 判断主键冲突
	 */
	@Override
	public Integer selectPrimaryKey(MpResidualModel mpResidualModel) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".selectPrimaryKey", mpResidualModel);
	}

	/**
	 * 校验计算队列
	 */
	@Override
	public Integer selectUnloadPort(MpResidualModel mpResidualModel) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".selectUnloadPort", mpResidualModel);
	}
}

