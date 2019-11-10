package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mp.dao.MpOrderRecordHisDao;
import com.hanthink.mp.model.MpOrderRecordHisModel;
import com.hanthink.mp.model.MpOrderRecordHisModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;


/**
 * 
 * <pre> 
 * 描述：订单履历 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class MpOrderRecordHisDaoImpl extends MyBatisDaoImpl<String, MpOrderRecordHisModel> implements MpOrderRecordHisDao{

    @Override
    public String getNamespace() {
        return MpOrderRecordHisModel.class.getName();
    }
	
    /**
   	 * 查询功能重写
   	 * @param importList
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:11:33
   	 */
	@Override
	public List<MpOrderRecordHisModel> queryMpOrderRecordHisForPage(MpOrderRecordHisModel model, DefaultPage p) {
		 return this.getByKey("queryMpOrderRecordHisForPage", model, p);
	}

	
	/**
	 * 导出数据查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpOrderRecordHisModel> queryMpOrderRecordHisByKey(MpOrderRecordHisModel model) {
		return this.getByKey("queryMpOrderRecordHisForPage", model);
	}

	/**
	 * 获取计算队列
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
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
	public void insertMpOrderRecordHisImportTempData(List<MpOrderRecordHisModel> importList) {
		this.insertByKey("insertMpOrderRecordHisImportTempData", importList);
	}

	/**
	 * 检查导入的临时数据信息
	 * @param ckParamMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:12:37
	 */
	@Override
	public void checkMpOrderRecordHisImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne("checkMpOrderRecordHisImportData", ckParamMap);
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
	public PageList<MpOrderRecordHisModel> queryMpOrderRecordHisImportTempData(Map<String, String> params,Page page) {
		return (PageList)this.getByKey("queryMpOrderRecordHisImportTempData", params,page);
	}

	/**
	 * 更新临时表导入状态
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:03:43
	 */
	@Override
	public void updateMpOrderRecordHisImportDataImpStatus(Map<String, Object> paramMap) {
		this.updateByKey("updateMpOrderRecordHisImportDataImpStatus", paramMap);
	}

	/**
	 * 根据UUID删除导入的临时数据
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public void deleteMpOrderRecordHisImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteMpOrderRecordHisImportTempDataByUUID", uuid);
	}
	
	/**
	 * 根据ID查询出哪些数据要更新导入
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<String> queryUpdateDataFromMpOrderRecordHisImp(Map<String, Object> paramMap) {
		return this.sqlSessionTemplate.selectList(getNamespace()+".queryUpdateDataFromMpOrderRecordHisImp", paramMap);
	}

	/**
	 * 更新导入的方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public void updateMpOrderRecordHisImportData(Map<String, Object> paramMap) {
		this.updateByKey("updateMpOrderRecordHisImportData", paramMap);
	}
 
	/**
	 * 查询是否可以批量导入
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
    public String queryMpOrderRecordHisIsImportFlag(String uuid) {
	    return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryMpOrderRecordHisIsImportFlag", uuid);
    }

	/**
	 * 导出临时数据信息
	 * @param paramMap
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:19:09
	 */
	@Override
	public List<MpOrderRecordHisModel> queryMpOrderRecordHisImportTempDataForExport(Map<String, String> paramMap) {
		return this.sqlSessionTemplate.selectList("queryMpOrderRecordHisImportTempData", paramMap);
	}

	/**
	 * 查询可导入的数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MpOrderRecordHisModel> queryForInsertList(Map<String, Object> paramMap) {
		return (List<MpOrderRecordHisModel>) this.getList("queryForInsertList", paramMap);
	}

	@Override
	public String queryOrderStatus(String codeValueName) {
		Map<String,Object> map=new HashMap();
		map.put("orderStatus", codeValueName);
		map.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		return  this.sqlSessionTemplate.selectOne("queryOrderStatus", map);
	}

	
}

