package com.hanthink.pkg.dao;

import java.util.List;

import com.hanthink.pkg.model.PkgBoxModel;
import com.hanthink.pkg.model.PkgPartModel;
import com.hanthink.pkg.model.PkgProposalModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
* <p>Title: PkgPartDao.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月26日
*/

public interface PkgPartDao extends Dao<String, PkgPartModel>{

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
	PageList<PkgPartModel> queryPkgPartForPage(PkgPartModel model, DefaultPage p);

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
	PkgProposalModel getTelNoByUser(String partRespUser);
	
	/**
	 * 
	 * @Description: 定时调用存储将数据写入到业务表
	 * @param    
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月4日 上午10:08:47
	 */
	void getPartFromIf();

	/**
	 * 
	 * @Description: 获取包装模块人员
	 * @param @return   
	 * @return List<PkgPartModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月17日 下午5:12:21
	 */
	List<PkgPartModel> getPkgUser();

}
