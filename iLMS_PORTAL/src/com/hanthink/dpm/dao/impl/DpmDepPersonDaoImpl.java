package com.hanthink.dpm.dao.impl;

import org.springframework.stereotype.Repository;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hanthink.dpm.dao.DpmDepPersonDao;
import com.hanthink.dpm.model.DpmDepPersonModel;
import com.hanthink.dpm.model.DpmDepartmentModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月14日 上午11:14:50
 * </pre>
 * @author luoxq
 */
@Repository
public class DpmDepPersonDaoImpl extends MyBatisDaoImpl<String, DpmDepPersonModel> implements DpmDepPersonDao{

	@Override
	public String getNamespace() {
		
		return DpmDepPersonModel.class.getName();
	}

	@Override
	public PageList<DpmDepPersonModel> queryDpmDepPersonForPage(DpmDepPersonModel model, DefaultPage p) {
		
		return (PageList<DpmDepPersonModel>) this.getByKey("queryDpmDepPersonForPage", model, p);
	}

//	@Override
//	public DpmDepartmentModel getByDepUserId(String userId) {
//		
//		return null;
//	}

}
