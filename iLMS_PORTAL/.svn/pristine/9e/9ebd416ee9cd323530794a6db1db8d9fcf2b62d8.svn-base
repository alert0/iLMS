package com.hanthink.dpm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.dpm.dao.DpmDepartmentDao;
import com.hanthink.dpm.model.DpmDepartmentModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre>
 * Description:
 * Company: HanThink
 * Date: 2018年9月13日 下午12:10:57
 * </pre>
 * @author luoxq
 */
@Repository
public class DpmDepartmentDaoImpl extends MyBatisDaoImpl<String, DpmDepartmentModel> implements DpmDepartmentDao{

	@Override
	public String getNamespace() {
		
		return DpmDepartmentModel.class.getName();
	}
	
	@Override
	public DpmDepartmentModel getByCode(String depCode) {
		
		return this.getUnique("getByCode", depCode);
	}

	@Override
	public PageList<DpmDepartmentModel> queryDpmDepartmentForPage(DpmDepartmentModel model, DefaultPage p) {
		
		return (PageList<DpmDepartmentModel>) this.getByKey("queryDpmItemForPage", model, p);
	}

	@Override
	public PageList<DpmDepartmentModel> getDepChecker(DpmDepartmentModel model, DefaultPage p) {
		
		return (PageList<DpmDepartmentModel>) this.getByKey("getDepChecker", model, p);
	}

	@Override
	public List<DpmDepartmentModel> isExistChecker(DpmDepartmentModel departmentModel) {
		
		return (List<DpmDepartmentModel>) this.getList("getDepChecker", departmentModel);
		
	}

	@Override
	public List<DictVO> getDepUnloadCode() {
		Map<String, DictVO> map=new HashMap();
		return (List<DictVO>) this.getList("getDepUnloadCode", map);
	}

}
