package com.hanthink.pkg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pkg.dao.PkgBoxDao;
import com.hanthink.pkg.dao.PkgPartDao;
import com.hanthink.pkg.model.PkgBoxModel;
import com.hanthink.pkg.model.PkgPartModel;
import com.hanthink.pkg.model.PkgProposalModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
* <p>Title: PkgPartDaoImpl.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月26日
*/
@Repository
public class PkgPartDaoImpl extends MyBatisDaoImpl<String, PkgPartModel> implements PkgPartDao{

	
	@Override
	public String getNamespace() {
		
		return PkgPartModel.class.getName();
	}

	/**
	 * 
	* @Title: queryPkgPartForPage 
	* @Description: 分页查询零件担维护列表信息 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<PkgPartModel> 
	* @throws 
	* @author luoxq
	* @date   2018年9月26日 下午12:21:08
	 */
	@Override
	public PageList<PkgPartModel> queryPkgPartForPage(PkgPartModel model, DefaultPage p) {
		
		return (PageList<PkgPartModel>) this.getByKey("queryPkgPartForPage", model, p);
	}

	/**
	 * 
	* @Title: getTelNoByUser 
	* @Description: 通过零件担当带出联系方式 
	* @param @param partRespUser
	* @param @return    
	* @return PkgProposalModel 
	* @throws 
	* @author luoxq
	* @date   2018年10月7日 下午3:58:51
	 */
	@Override
	public PkgProposalModel getTelNoByUser(String partRespUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		IUser user = ContextUtil.getCurrentUser();
		map.put("partRespUser", partRespUser);
		map.put("factoryCode", user.getCurFactoryCode());
		return (PkgProposalModel) this.getOne("getTelNoByUser", map);
	}

	@Override
	public void getPartFromIf() {
		
		
	}

	/**
	 * 
	 * @Description: 获取包装模块人员
	 * @param @return   
	 * @return List<PkgPartModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月17日 下午5:12:21
	 */
	@Override
	public List<PkgPartModel> getPkgUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		return this.getByKey("getPkgUser",map);
	}

}
