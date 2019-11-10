package com.hanthink.pkg.manager;

import java.util.List;

import com.hanthink.pkg.model.PkgPartModel;
import com.hanthink.pkg.model.PkgProposalModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
* <p>Title: PkgPartManager.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月26日
*/

public interface PkgPartManager extends Manager<String, PkgPartModel>{

	/**
	 * 
	* @Title: queryPkgPartForPage 
	* @Description: 分页查询零件担当维护界面数据 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<PkgPartModel> 
	* @throws
	 */
	public PageList<PkgPartModel> queryPkgPartForPage(PkgPartModel model, DefaultPage p);

	/**
	 * 
	* @Title: updateAndLog 
	* @Description: 修改零件担当信息并记录日志  
	* @param @param PkgProposalModel
	* @param @param ipAddr    
	* @return void 
	* @throws
	 */
	public void updateAndLog(PkgPartModel pkgPartModel, String ipAddr);

	/**
	 * 
	* @Title: getTelNoByUser 
	* @Description: 通过零件担当带出联系方式  
	* @param @param partRespUser
	* @param @return    
	* @return PkgProposalModel 
	* @throws 
	* @author luoxq
	* @date   2018年10月7日 下午3:57:16
	 */
	public PkgProposalModel getTelNoByUser(String partRespUser);

	/**
	 * 
	 * @Description: 调用定时器将数据从接口表写到业务表
	 * @param    
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月4日 上午10:07:34
	 */
	public void getPartFromIf();

	/**
	 * 
	 * @Description: 获取包装模块人员
	 * @param @return   
	 * @return List<PkgPartModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月17日 下午5:11:34
	 */
	public List<PkgPartModel> getPkgUser();

}
