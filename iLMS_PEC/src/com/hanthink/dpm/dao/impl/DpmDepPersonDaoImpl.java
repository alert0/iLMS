package com.hanthink.dpm.dao.impl;

import java.util.List;

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

	/**
	 * 
	 * @Description: 部门人员维护界面，判断人员是否已经存在
	 * @param @param account
	 * @param @return   
	 * @return boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月29日 下午4:24:15
	 */
	@Override
	public boolean isUserIdExist(DpmDepPersonModel dpmDepPersonModel) {
		
		@SuppressWarnings("unchecked")
		List<DpmDepPersonModel> list = (List<DpmDepPersonModel>) this.getList("isUserIdExist", dpmDepPersonModel);
		if (null != list && list.size()>0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Description: 部门人员维护界面，判断责任组是否已经存在
	 * @param @param dpmDepPersonModel
	 * @param @return   
	 * @return Integer  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月26日 上午11:37:22
	 */
	@Override
	public Integer isDepExixt(DpmDepPersonModel dpmDepPersonModel) {
		
		return this.sqlSessionTemplate.selectOne("isDepExixt", dpmDepPersonModel);
	}

}
