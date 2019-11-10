package com.hanthink.pkg.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.pkg.model.PkgBoxModel;
import com.hanthink.pkg.model.PkgPartModel;
import com.hanthink.pkg.model.PkgProposalModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
* <p>Title: PkgProposalDao.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月30日
*/

public interface PkgProposalDao extends Dao<String, PkgProposalModel>{
	
	/**
	 * 
	* @Title: queryPkgPartForPage 
	* @Description: 分页查询所有数据 
	* @param @param model
	* @param @param p
	* @param @return    
	* @return PageList<PkgPartModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月8日 上午10:51:26
	 */
	public PageList<PkgProposalModel> queryPkgProposalForPage(PkgProposalModel model, DefaultPage p);

	/**
	 * 
	* @Title: getUnloadBoxCode 
	* @Description: 供应商包装提案界面获取箱code下拉框  
	* @param @return    
	* @return List<DictVO> 
	* @throws 
	* @author luoxq
	* @date   2018年10月8日 下午6:09:33
	 */
	public List<DictVO> getUnloadBoxCode();

	/**
	 * 
	* @Title: insertProposal 
	* @Description: 数据写入MM_PKG_PROPOSAL表中，返回id 
	* @param @param pkgProposalModel
	* @param @return    
	* @return String 
	* @throws 
	* @author luoxq
	* @date   2018年10月9日 下午2:37:05
	 */
	public String updateProposal(PkgProposalModel pkgProposalModel);

	/**
	 * 
	* @Title: insertNotTrolley 
	* @Description: 数据写入非台车包装信息表中MM_PKG_PRO_NOT_TROLLEY  
	* @param @param pkgProposalModel    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月9日 下午3:52:41
	 */
	public void insertNotTrolley(PkgProposalModel pkgProposalModel);

	/**
	 * 
	* @Title: queryPkgProposalByKey 
	* @Description: 查询需要导出的数据 
	* @param @param model
	* @param @return    
	* @return List<DpmInsModel> 
	* @throws 
	* @author luoxq
	* @date   2018年10月11日 上午11:01:03
	 */
	public List<PkgProposalModel> queryPkgProposalByKey(PkgProposalModel model);

	/**
	 * 
	 * @param p 
	 * @Description: 包装提案查询界面，全车型查询
	 * @param @param list
	 * @param @return   
	 * @return PkgProposalModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月28日 下午5:34:30
	 */
	public PageList<PkgProposalModel> queryListOfAllType(PkgProposalModel model,List<String> list, DefaultPage p);

	/**
	 * 
	 * @param model 
	 * @param model 
	 * @param p 
	 * @Description: 包装查询界面，单车型查询
	 * @param @param list
	 * @param @return   
	 * @return PkgProposalModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月28日 下午8:41:20
	 */
	public PageList<PkgProposalModel> queryListOfSigleType( PkgProposalModel model, List<String> list, DefaultPage p);

	/**
	 * 
	 * @param model 
	 * @param p 
	 * @Description: 包装查询界面，组合车型查询
	 * @param @param list
	 * @param @return   
	 * @return PkgProposalModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月28日 下午9:52:44
	 */
	public PageList<PkgProposalModel> queryListOfComPackType(PkgProposalModel model, List<String> list, DefaultPage p);

	/**
	 * 
	 * @Description: 容器查询界面，箱子数量查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月29日 上午12:16:47
	 */
	public PageList<PkgProposalModel> queryBoxNumForPage(PkgProposalModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 容器管理界面，托盘数量查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月2日 下午5:30:51
	 */
	public PageList<PkgProposalModel> queryTrayNumForPage(PkgProposalModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 容器管理界面，箱子需求量维护
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 上午10:45:39
	 */
	public void updateBoxNeed(PkgProposalModel model);

	/**
	 * 
	 * @Description: 箱子纳入量更新
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 下午4:01:19
	 */
	public void updateBoxProvide(PkgProposalModel model);

	/**
	 * 
	 * @Description: 托盘纳入量管理
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 下午4:59:05
	 */
	public void updateTrayProvide(PkgProposalModel model);

	/**
	 * 
	 * @Description: 托盘需求量管理
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 下午4:59:31
	 */
	public void updateTrayNeed(PkgProposalModel model);

	/**
	 * 
	 * @Description: 包装信息查询界面，车型弹窗
	 * @param @param model
	 * @param @return   
	 * @return List<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月4日 下午2:52:58
	 */
	public List<PkgProposalModel> getCarType(PkgProposalModel model);

	/**
	 * 
	 * @Description: 查询导出数据,全车型
	 * @param @param model
	 * @param @param carList
	 * @param @return   
	 * @return List<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 上午10:15:38
	 */
	public List<PkgProposalModel> queryListOfAllType(PkgProposalModel model, List<String> carList);

	/**
	 * 
	 * @Description: 查询导出数据，单车型
	 * @param @param model
	 * @param @param carList
	 * @param @return   
	 * @return List<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 上午10:15:53
	 */
	public List<PkgProposalModel> queryListOfSigleType(PkgProposalModel model, List<String> carList);

	/**
	 * 
	 * @Description: 查询导出，组合查询
	 * @param @param model
	 * @param @param carList
	 * @param @return   
	 * @return List<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月17日 下午2:45:32
	 */
	public List<PkgProposalModel> queryListOfComPackType(PkgProposalModel model, List<String> carList);

	/**
	 * 查询待发起提案的零件信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月23日 上午11:41:39
	 */
	public PageList<Map<String, Object>> queryApplyPartDataList(Map<String, Object> param, Page page);

	/**
	 * 发起 添加提案
	 * @param paramList
	 * @author ZUOSL	
	 * @DATE	2018年11月23日 下午12:46:16
	 */
	public void addProposal(List<Map<String, String>> paramList);

	/**
	 * 添加提案明细信息
	 * @param paramList
	 * @author ZUOSL	
	 * @DATE	2018年11月26日 上午9:44:41
	 */
	public void addProposalDetail(List<Map<String, String>> paramList);

	/**
	 * 供应商提交包装提案，更新主信息
	 * @param pkgModelList
	 * @author ZUOSL	
	 * @DATE	2018年11月26日 下午8:25:42
	 */
	public void updateSupSubmitPkgProposal(List<PkgProposalModel> pkgModelList);

	/**
	 * 供应商提交包装提案，更新明细信息
	 * @param pkgModelList
	 * @author ZUOSL	
	 * @DATE	2018年11月26日 下午8:26:09
	 */
	public void updateSupSubmitPkgProposalDetail(List<PkgProposalModel> pkgModelList);

	/**
	 * 查询同组的组合提案信息
	 * @param proModel
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月29日 上午10:24:09
	 */
	public List<PkgProposalModel> queryCombPackProposal(PkgProposalModel proModel);

	/**
	 * 签字提案文件上传，保存签字提案文件ID
	 * @param pkgModel
	 * @author ZUOSL	
	 * @DATE	2018年11月29日 下午3:19:44
	 */
	public void uploadSignProfile(PkgProposalModel pkgModel);

	/**
	 * 更新包装提案状态
	 * @param model
	 * @author ZUOSL	
	 * @DATE	2018年11月30日 下午3:25:05
	 */
	public void updateProposalStatus(PkgProposalModel model);

	/**
	 * 根据零件号查询相关的包装提案数据信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月1日 上午10:13:23
	 */
	public PageList<PkgProposalModel> queryProposalDataByPartNo(Map<String, Object> param, Page page);

	/**
	 * 更新包装提案的生失效日期
	 * @param map
	 * @author ZUOSL	
	 * @DATE	2018年12月1日 上午11:39:47
	 */
	public void updateProposalEffDate(Map<String, String> map);

	/**
	 * 
	 * @Description: 如果实物通过，则把提案信息写入到箱子数量维护表中
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月9日 下午2:46:45
	 */
	public void insertBoxQty(PkgProposalModel model);

	public List<PkgProposalModel> getProposalStatus(Map<String, Object> map);

	/**
	 * 
	 * @Description: 留用零件根据零件号查询出上个零件的包装信息
	 * @param @param map
	 * @param @return   
	 * @return PkgProposalModel  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月17日 下午9:15:19
	 */
	public PkgProposalModel getProposalByStayPart(Map<String, Object> map);

}
