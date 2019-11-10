package com.hanthink.mp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.dao.MpExcepOrderDao;
import com.hanthink.mp.model.MpExcepOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * @Desc    : 例外订单Dao实现类
 * @CreateOn: 2018年9月7日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */

@Repository
public class MpExcepOrderDaoImpl extends MyBatisDaoImpl<String, MpExcepOrderModel> implements MpExcepOrderDao{

    @Override
    public String getNamespace() {
        return MpExcepOrderModel.class.getName();
    }

    @Override
    public List<MpExcepOrderModel> queryMpExcepOrderForPage(MpExcepOrderModel model, DefaultPage p) {
        return this.getByKey("queryMpExcepOrderForPage", model, p);
    }

    /**
	 * 例外订单生成
	 */
	@Override
	public Integer generateMpExcepOrder(String curFactoryCode) {
		Map<String,Object> map = new HashMap<>();
		map.put("factoryCode", curFactoryCode);
		map.put("outCode", null);
		this.sqlSessionTemplate.selectOne("generateMpExcepOrder",map);	
		/**
		 * 获取存储过程返回值
		 */
		return  (Integer) map.get("outCode");
	}

	/**
	 * 例外订单发布
	 */
	@Override
	public Integer releaseMpExcepOrder(String curFactoryCode,String opeId) {
		Map<String,Object> map = new HashMap<>();
		map.put("factoryCode", curFactoryCode);
		map.put("opeId", opeId);
		map.put("outCode", null);   
		this.sqlSessionTemplate.selectOne("releaseMpExcepOrder",map);
		return   (Integer) map.get("outCode");
	}

	/**
	 * 导出查询的方法
	 */
	@Override
	public List<MpExcepOrderModel> queryMpExcepOrderByKey(MpExcepOrderModel model) {
		return this.getByKey("queryMpExcepOrderForPage", model);
	}

	 @Override
    public void deleteImportTempDataByUUID(String uuid) {
        this.deleteByKey("deleteExcepImportTempDataByUUID", uuid);
    }

	 /**
	  * 导入数据到临时表
	  */
    @Override
    public void insertImportTempData(List<MpExcepOrderModel> importList) {
        this.insertBatchByKey("insertExcepImportTempData", importList);
    }

    /**
     * 校验临时数据
     */
    @Override
    public void checkImportData(Map<String, String> ckParamMap) {
        this.sqlSessionTemplate.selectOne("checkImportMpExcepData", ckParamMap);
    }

    /**
     * 查询临时数据
     */
    @Override
    public PageList<MpExcepOrderModel> queryImportTempData(Map<String, String> paramMap, Page page) {
        return (PageList)this.getByKey("queryImportMpExcepTempData", paramMap, page);
    }

    /**
     * 插入临时数据
     */
    @Override
    public void insertImportData(Map<String, String> paramMap) {
        this.insertByKey("insertMpExcepImportData", paramMap);
    }

    /**
     * 更新导入状态
     */
    @Override
    public void updateImportDataImpStatus(Map<String, String> paramMap) {
        this.updateByKey("updateMpExcepImportDataImpStatus", paramMap);
    }

    /**
     * 删除处理数据
     */
    @Override
    public void deleteNotDealData(Map<String, String> paramMap) {
       this.deleteByKey("deleteNotDealData", paramMap);
    }

    /**
     * 查询导入标识
     */
    @Override
    public String queryIsImportFlag(String uuid) {
        return this.sqlSessionTemplate.selectOne(getNamespace() + ".queryMpExcepIsImportFlag", uuid);
    }

	/**
	 * 导出临时数据信息
	 */
	@Override
	public List<MpExcepOrderModel> queryMpExcepOrderModelTempDataForExport(Map<String, String> paramMap) {
		return this.sqlSessionTemplate.selectList("queryImportMpExcepTempData", paramMap);
	}

	/**
	 * 查询可导入的数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MpExcepOrderModel> queryForInsertList(Map<String, String> paramMap) throws Exception {
		return (List<MpExcepOrderModel>) this.getList("queryForInsertList", paramMap);
	}

	/**
	 * 根据车间取仓库代码
	 */
	@Override
	public String selectStorageByWorkCenter(MpExcepOrderModel mpExcepOrderModel) {
		return this.sqlSessionTemplate.selectOne(getNamespace() + ".selectStorageByWorkCenter", mpExcepOrderModel);
	}
	
	/**
	 * 批量删除
	 */
	public void deleteByIds(String[] aryIds) throws Exception{
		this.deleteByKey("deleteByIds", aryIds);
	}

	/**
	 * 查询是否存在已订购数据
	 */
	@Override
	public Integer queryMpExcepOrderCheck(List<String> listIds) {
		return sqlSessionTemplate.selectOne(getNamespace()+".queryMpExcepOrderCheck", listIds);	
	}

	/**
	 * 获取到货仓库填充下拉框
	 */
	@Override
	public List<DictVO> getInvWareHouse() {
		Map<String,Object> map=new HashMap();
		return this.getList("getInvWareHouse", map);
	}
	
}
