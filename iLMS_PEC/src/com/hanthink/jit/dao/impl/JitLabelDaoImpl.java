package com.hanthink.jit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.jit.dao.JitLabelDao;
import com.hanthink.jit.model.JitLabelModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitLabelDaoImpl
 * @Description: 零件标签查询
 * @author dtp
 * @date 2018年9月29日
 */
@Repository
public class JitLabelDaoImpl extends MyBatisDaoImpl<String, JitLabelModel> implements JitLabelDao{

	@Override
	public String getNamespace() {
		return JitLabelModel.class.getName();
	}

	/**
	 * @Description: 零件标签查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitLabelModel>   
	 * @author: dtp
	 * @date: 2018年9月29日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitLabelModel> queryJitLabelPage(JitLabelModel model, DefaultPage page) {
		return (PageList<JitLabelModel>) this.getList("queryJitLabelPage", model, page);
	}

	/**
	 * @Description: 零件标签查询导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitLabelModel>   
	 * @author: dtp
	 * @date: 2018年9月29日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitLabelModel> queryJitLabelList(JitLabelModel model) {
		return (List<JitLabelModel>) this.getList("queryJitLabelPage", model);
	}

	/**
	 * @Description: 获取标签(标签打印) 
	 * @param: @param model
	 * @param: @return    
	 * @return: JitLabelModel   
	 * @author: dtp
	 * @date: 2018年10月4日
	 */
	@Override
	public JitLabelModel queryJitLabel(JitLabelModel model) {
		return this.getUnique("queryJitLabel", model);
	}

	/**
	 * @Description: 更新打印信息  
	 * @param: @param jitLabelModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月26日
	 */
	@Override
	public void updateJitLabelPrintInfo(JitLabelModel jitLabelModel) {
		this.updateByKey("updateJitLabelPrintInfo", jitLabelModel);
	}

	/**
	 * @Description:   
	 * @param: @param jitLabelModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年3月20日
	 */
	@Override
	public void updateLabelUUID(JitLabelModel jitLabelModel) {
		this.updateByKey("updateLabelUUID", jitLabelModel);
	}

	

}
