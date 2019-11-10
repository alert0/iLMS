package com.hanthink.pub.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pub.dao.PubDataDictDao;
import com.hanthink.pub.model.PubDataDictModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：数据字典查询 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class PubDataDictDaoImpl extends MyBatisDaoImpl<String, PubDataDictModel> implements PubDataDictDao{

    @Override
    public String getNamespace() {
        return PubDataDictModel.class.getName();
    }

    /**
     * 执行分页查询
     */
	@Override
	public List<PubDataDictModel> queryPubDataDictForPage(PubDataDictModel model, DefaultPage p) {
		
		return this.getByKey("queryPubDataDictForPage", model, p);
	}

	/**
	 * 右侧栏位显示查询结果
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubDataDictModel> getRowClick(Map<String, String> map, DefaultPage p) {
		return (List<PubDataDictModel>) this.getList("getRowClick", map,p);
	}

	/**
	 * 左侧新增
	 */
	@Override
	public void insertLeft(PubDataDictModel pubDataDictModel) throws Exception{
		this.insertByKey("insertLeft", pubDataDictModel);
		
	}

	/**
	 * 右侧新增
	 */
	@Override
	public void insertRight(PubDataDictModel pubDataDictModel) throws Exception{
		this.insertByKey("insertRight", pubDataDictModel);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void deleteByIds(String[] aryIds)throws Exception {
		this.deleteByKey("deleteByIds", aryIds);
	}
	
        /**
	 * @Description: 根据数据字典类型查询数据字典信息(key-value反转)  
	 * @param: @param PubDataDictModel model
	 * @param: @return    
	 * @return: List<PubDataDictModel>   
	 * @author: dtp
	 * @date: 2018年11月23日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubDataDictModel> queryDataDictByCodeType(PubDataDictModel model) {
		return (List<PubDataDictModel>) this.getList("queryDataDictByCodeType", model);
	}
	
}

