package com.hanthink.pub.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pub.dao.PubDataDictDao;
import com.hanthink.pub.manager.PubDataDictManager;
import com.hanthink.pub.model.PubDataDictModel;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：供应商分组 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("PubDataDictManager")
public class PubDataDictManagerImpl extends AbstractManagerImpl<String, PubDataDictModel> implements PubDataDictManager{
	@Resource
	PubDataDictDao pubDataDictDao;
	@Override
	protected Dao<String, PubDataDictModel> getDao() {
		return pubDataDictDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<PubDataDictModel> queryPubDataDictForPage(PubDataDictModel model, DefaultPage p) {
		return pubDataDictDao.queryPubDataDictForPage(model, p);
	}
	
	/**
	 * 右侧栏位显示查询结果
	 */
	@Override
	public List<PubDataDictModel> getRowClick(Map<String, String> map, DefaultPage p) {
		return pubDataDictDao.getRowClick(map,p);
	}

	/**
	 * 左侧新增
	 * @throws Exception 
	 */
	@Override
	public void insertLeft(PubDataDictModel pubDataDictModel) throws Exception {
		 pubDataDictDao.insertLeft(pubDataDictModel);
	}

	/**
	 * 右侧新增
	 * @throws Exception 
	 */
	@Override
	public void insertRight(PubDataDictModel pubDataDictModel) throws Exception {
		 pubDataDictDao.insertRight(pubDataDictModel);
	}

	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月4日 上午11:01:08
	 */
	@Override
	public void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception{
		
		//日志记录
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("数据字典删除");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
		logVO.setTableName("MM_PUB_DATA_DICT");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnValArr(aryIds);
		this.tableDataLogDao.logOpeTableDataBatch(logVO, pkColumnVO);
		//执行删除
		pubDataDictDao.deleteByIds(aryIds);
	}

	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param ipAddr
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:52
	 */
	@Override
	public void updateAndLog(PubDataDictModel pubDataDictModel, String ipAddr){
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PUB_DATA_DICT");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("ID");
		pkColumnVO.setColumnVal(pubDataDictModel.getId().toString());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		pubDataDictDao.update(pubDataDictModel);
		
	}
	
           /**
	 * @Description: 根据数据字典类型查询数据字典信息(key-value反转)  
	 * @param: @param codeType
	 * @param: @return    
	 * @return: List<PubDataDictModel>   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@Override
	public Map<String, String> queryDataDictByCodeType(String codeType) {
		PubDataDictModel model = new PubDataDictModel();
		model.setCodeType(codeType);
		List<PubDataDictModel> list = pubDataDictDao.queryDataDictByCodeType(model);
		Map<String, String> dictMap = new HashMap<String, String>();
		for (PubDataDictModel dataDict : list) {
			dictMap.put(dataDict.getCodeValueName(), dataDict.getCodeValue());
		}
		return dictMap;
		
	}

	/**
	 * @Description: 根据数据字典类型和codeValue查codeValueName
	 * @param: @param codeType
	 * @param: @return    
	 * @return: String  
	 * @author: dtp
	 * @date: 2018年12月3日
	 */
	@Override
	public String queryDataDictCodeValueName(String codeType, String codeValue) {
		PubDataDictModel model = new PubDataDictModel();
		model.setCodeType(codeType);
		List<PubDataDictModel> list = pubDataDictDao.queryDataDictByCodeType(model);
		String codeValueName = "";
		for (PubDataDictModel pubDataDictModel : list) {
			if(codeValue.equals(pubDataDictModel.getCodeValue())) {
				codeValueName = pubDataDictModel.getCodeValueName();
			}
		}
		return codeValueName;
	}
	

	
}
