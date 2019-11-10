package com.hanthink.dpm.dao.impl;

import org.springframework.stereotype.Repository;

import com.hanthink.demo.dao.DemoDao;
import com.hanthink.demo.model.DemoModel;
import com.hanthink.dpm.dao.DpmItemDao;
import com.hanthink.dpm.model.DpmItemModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月9日 下午5:24:06
 * </pre>
 * @author luoxq
 */
@Repository
public class DpmItemDaoImpl extends MyBatisDaoImpl<String, DpmItemModel> implements DpmItemDao{

	@Override
	public String getNamespace() {
        
		return DpmItemModel.class.getName();
	}

	@Override
	public DpmItemModel getByCode(DpmItemModel dpmItemModel) {
		
		return this.getUnique("getByCode", dpmItemModel);
	}

	@Override
	public PageList<DpmItemModel> queryDpmItemForPage(DpmItemModel model, DefaultPage p) {
		
		return (PageList<DpmItemModel>) this.getByKey("queryDpmItemForPage", model, p);
	}


}
