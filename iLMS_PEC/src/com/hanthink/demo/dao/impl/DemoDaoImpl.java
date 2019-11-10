package com.hanthink.demo.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.demo.dao.DemoDao;
import com.hanthink.demo.model.DemoModel;
import com.hanthink.demo.model.DemoModelImport;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * @Desc		: 
 * @FileName	: DemoDaoImpl.java
 * @CreateOn	: 2018年8月31日 下午3:33:03
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 2018年8月31日		V1.0		ZUOSL		新建
 */
@Repository
public class DemoDaoImpl extends MyBatisDaoImpl<String, DemoModel> implements DemoDao {

	@Override
	public String getNamespace() {
		return DemoModel.class.getName();
	}

	/**
	 * 写入导入数据到临时表
	 * @param importList
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 上午11:11:33
	 */
	@Override
	public void insertImportTempData(List<DemoModelImport> importList) {
		this.insertBatchByKey("insertImportTempData", importList);
	}

	/**
	 * 检查导入的临时数据信息
	 * @param ckParamMap
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 上午11:12:37
	 */
	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkImportData", ckParamMap);
	}

	/**
	 * 查询导入的临时数据信息
	 * @param paramMap
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 下午12:30:46
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageList<DemoModelImport> queryImportTempData(Map<String, String> params, Page page) {
		return (PageList)this.getByKey("queryImportTempData", params, page);
	}

	/**
	 * 临时表数据信息写入到正式表
	 * @param paramMap
	 * @author ZUOSL	
	 * @DATE	2018年9月2日 下午12:31:10
	 */
	@Override
	public void insertImportData(Map<String, String> paramMap) {
		this.insertByKey("insertImportData", paramMap);
	}

	/**
	 * 更新临时表导入状态
	 * @param paramMap
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 上午10:03:43
	 */
	@Override
	public void updateImportDataImpStatus(Map<String, String> paramMap) {
		this.updateByKey("updateImportDataImpStatus", paramMap);
	}

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author ZUOSL	
	 * @DATE	2018年9月3日 下午4:19:09
	 */
	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteImportTempDataByUUID", uuid);
	}

	

}
