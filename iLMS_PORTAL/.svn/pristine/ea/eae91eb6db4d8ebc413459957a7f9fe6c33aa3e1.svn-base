package com.hanthink.jit.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.jit.dao.JitInvenCompDao;
import com.hanthink.jit.model.JitInvenCompModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitInvenCompDaoImpl
 * @Description: 拉动库存对比
 * @author dtp
 * @date 2018年11月3日
 */
@Repository
public class JitInvenCompDaoImpl extends MyBatisDaoImpl<String, JitInvenCompModel> implements JitInvenCompDao{

	@Override
	public String getNamespace() {
		return JitInvenCompModel.class.getName();
	}

	/**
	 * @Description: 导入数据到临时表
	 * @param: @param importList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@Override
	public void insertImportTempData(List<JitInvenCompModel> importList) {
		this.insertBatchByKey("insertImportTempData", importList);
	}

	/**
	 * @Description: 校验数据准确性
	 * @param: @param ckParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".checkImportData", ckParamMap);
	}

	/**
	 * @Description: 查询excel导入数据  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInvenCompModel>   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitInvenCompModel> queryImportTempPage(JitInvenCompModel model, DefaultPage page) {
		return (PageList<JitInvenCompModel>) this.getList("queryImportTempPage", model, page);
	}

	/**
	 * @Description: 执行拉动库存对比计算 
	 * @param: @param reckonParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@Override
	public void isReckon(Map<String, String> reckonParamMap) {
		this.sqlSessionTemplate.selectOne(this.getNamespace()+".isReckon", reckonParamMap);
	}

	/**
	 * @Description: 拉动库存对比计算excel导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInvenCompModel>   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitInvenCompModel> queryImportTempList(JitInvenCompModel model) {
		return (List<JitInvenCompModel>) this.getList("queryImportTempPage", model);
	}

	/**
	 * @Description: 拉动计划差异查询  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInvenCompModel>   
	 * @author: dtp
	 * @date: 2018年11月8日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitInvenCompModel> queryJitPlanDiff(JitInvenCompModel model, DefaultPage page) {
		return (PageList<JitInvenCompModel>) this.getList("queryJitPlanDiff", model, page);
	}

}
