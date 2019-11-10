package com.hanthink.pkg.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.pkg.model.PkgBoxModel;
import com.hanthink.pkg.model.PkgProposalModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.api.model.IUser;

/**
* <p>Title: PkgProposalManager.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月30日
*/
public interface PkgProposalManager extends Manager<String, PkgProposalModel>{

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
	* @date   2018年10月8日 上午10:45:58
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
	* @date   2018年10月8日 下午6:07:55
	 */
	public List<DictVO> getUnloadBoxCode();

	/**
	 * 
	* @Title: insertProposal 
	* @Description: 修改提案状态 
	* @param @param pkgProposalModel
	* @param @return    
	* @return String 
	* @throws 
	* @author luoxq
	* @date   2018年10月9日 下午2:35:48
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
	* @date   2018年10月9日 下午3:50:17
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
	* @date   2018年10月11日 上午10:59:58
	 */
	public List<PkgProposalModel> queryPkgProposalByKey(PkgProposalModel model);

	/**
	 * 
	 * @param model 
	 * @param p 
	 * @Description: 包装管理查询界面，全车型查询
	 * @param @param list
	 * @param @return   
	 * @return PkgProposalModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月28日 下午5:33:17
	 */
	public PageList<PkgProposalModel> queryListOfAllType(PkgProposalModel model, List<String> list, DefaultPage p);

	/**
	 * 
	 * @param model 
	 * @param p 
	 * @Description: 包装管理查询界面，单车型查询
	 * @param @param list
	 * @param @return   
	 * @return PkgProposalModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月28日 下午8:38:06
	 */
	public PageList<PkgProposalModel> queryListOfSigleType(PkgProposalModel model, List<String> list, DefaultPage p);

	/**
	 * 
	 * @param model 
	 * @param p 
	 * @Description: 包装管理查询界面，组合查询
	 * @param @param list
	 * @param @return   
	 * @return PkgProposalModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月28日 下午9:27:04
	 */
	public PageList<PkgProposalModel> queryListOfComPackType(PkgProposalModel model, List<String> list, DefaultPage p);

	/**
	 * 
	 * @Description: 容器管理界面，箱子数量查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月29日 上午12:15:42
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
	 * @date 2018年11月2日 下午5:29:21
	 */
	public PageList<PkgProposalModel> queryTrayNumForPage(PkgProposalModel model, DefaultPage p);
	
	/**
	 * 
	 * @Description: 容器管理界面，箱子需求量维护
	 * @param @param model   
	 * @return void  
	 * @throws
	 * @author luoxq
	 * @date 2018年11月3日 上午10:36:52
	 */
	public void updateBoxNeed(PkgProposalModel model);

	/**
	 * 
	 * @Description: 箱子纳入量更新
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 下午3:55:02
	 */
	public void updateBoxProvide(PkgProposalModel model);

	/**
	 * 
	 * @Description: 托盘需求量管理
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 下午4:57:05
	 */
	public void updateTrayNeed(PkgProposalModel model);

	/**
	 * 
	 * @Description: 托盘纳入量管理
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 下午4:57:23
	 */
	public void updateTrayProvide(PkgProposalModel model);

	/**
	 * 
	 * @Description: 包装信息查询界面，车型弹窗
	 * @param @param model
	 * @param @return   
	 * @return List<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月4日 下午2:51:59
	 */
	public List<PkgProposalModel> getCarType(PkgProposalModel model);
	
	/**
	 * 
	 * @Description: 查询导出数据,全车型查询导出
	 * @param @param model
	 * @param @param carList
	 * @param @return   
	 * @return List<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 上午10:13:49
	 */
	public List<PkgProposalModel> queryListOfAllType(PkgProposalModel model, List<String> carList);

	/**
	 * 
	 * @Description: 查询导出数据，单车型查询导出
	 * @param @param model
	 * @param @param carList
	 * @param @return   
	 * @return List<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 上午10:14:05
	 */
	public List<PkgProposalModel> queryListOfSigleType(PkgProposalModel model, List<String> carList);

	/**
	 * 
	 * @Description: 查询导出数据，组合查询导出
	 * @param @param model
	 * @param @param carList
	 * @param @return   
	 * @return List<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月17日 下午2:44:00
	 */
	public List<PkgProposalModel> queryListOfComPackType(PkgProposalModel model, List<String> carList);

	/**
	 * 查询待发起提案的零件信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月23日 上午11:40:34
	 */
	public PageList<Map<String, Object>> queryApplyPartDataList(Map<String, Object> param, Page page);

	/**
	 * 发起 添加提案
	 * @param paramList
	 * @author ZUOSL	
	 * @DATE	2018年11月23日 下午12:45:25
	 */
	public void addProposal(List<Map<String, String>> paramList);

	/**
	 * 供应商包装提案提交
	 * @param pkgModelList
	 * @author ZUOSL	
	 * @DATE	2018年11月26日 下午8:11:51
	 */
	public void supPkgProposalSubmit(List<PkgProposalModel> pkgModelList);

	/**
	 * 查询同组的组合提案信息
	 * @param proModel
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月29日 上午10:22:10
	 */
	public List<PkgProposalModel> queryCombPackProposal(PkgProposalModel proModel);

	/**
	 * 签字提案文件上传，保存签字提案文件ID
	 * @param pkgModel
	 * @author ZUOSL	
	 * @DATE	2018年11月29日 下午3:18:42
	 */
	public void uploadSignProFile(PkgProposalModel pkgModel);

	/**
	 * 提案审核提交处理
	 * @param pkgModelList
	 * @param user
	 * @author ZUOSL	
	 * @DATE	2018年11月30日 下午3:19:29
	 */
	public void proposalCheckSubmit(List<PkgProposalModel> pkgModelList, IUser user);

	/**
	 * 根据零件号查询相关的包装提案数据信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月1日 上午10:10:55
	 */
	public PageList<PkgProposalModel> queryProposalDataByPartNo(Map<String, Object> param, Page page);

	/**
	 * 更新包装提案的生失效日期
	 * @param map
	 * @author ZUOSL	
	 * @DATE	2018年12月1日 上午11:38:57
	 */
	public void updteProposalEffDate(Map<String, String> map);

	/**
	 * 实物审核提交处理
	 * @param model
	 * @param user
	 * @param ipAddr
	 * @author ZUOSL	
	 * @DATE	2018年12月1日 下午1:10:14
	 */
	public void proposalMaterialCheckSubmit(PkgProposalModel model, IUser user, String ipAddr);
	
	/**
	 * 
	 * @Description: 获取下拉框状态
	 * @param @param map
	 * @param @return   
	 * @return List<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月17日 下午9:13:40
	 */
	public List<PkgProposalModel> getProposalStatus(Map<String, Object> map);

	/**
	 * 
	 * @Description: 留用状态零件根据零件号获取上个零件的包装信息
	 * @param @param map
	 * @param @return   
	 * @return PkgProposalModel  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月17日 下午9:13:54
	 */
	public PkgProposalModel getProposalByStayPart(Map<String, Object> map);

}
