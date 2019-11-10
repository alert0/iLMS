package com.hanthink.pkg.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.base.model.DictVO;
import com.hanthink.pkg.dao.PkgProposalDao;
import com.hanthink.pkg.model.PkgProposalModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
* <p>Title: PkgProposalDaoImpl.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月30日
*/
@Repository
public class PkgProposalDaoImpl extends MyBatisDaoImpl<String, PkgProposalModel> implements PkgProposalDao{


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
	@Override
	public PageList<PkgProposalModel> queryPkgProposalForPage(PkgProposalModel model, DefaultPage p) {
		
		return (PageList<PkgProposalModel>) this.getByKey("queryPkgProposalForPage", model, p);
	}

	@Override
	public String getNamespace() {
		
		return PkgProposalModel.class.getName();
	}

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
	@Override
	public List<DictVO> getUnloadBoxCode() {
		Map<String, Object> map=new HashMap();
		return (List<DictVO>) this.getList("getUnloadBoxCode", map);
	}

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
	@Override
	public String updateProposal(PkgProposalModel pkgProposalModel) {
		
		Integer a= this.getSqlSessionTemplate().insert(this.getNamespace()+".updateProposal", pkgProposalModel);
	    return a.toString();
	}

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
	@Override
	public void insertNotTrolley(PkgProposalModel pkgProposalModel) {
		this.insertByKey("insertNotTrolley", pkgProposalModel);
	}

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
	@Override
	public List<PkgProposalModel> queryPkgProposalByKey(PkgProposalModel model) {
		return this.getByKey("queryPkgProposalForPage", model);
	}

	/**
	 * 
	 * @Description: 包装提案查询界面，全车型查询
	 * @param @param list
	 * @param @return   
	 * @return PkgProposalModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月28日 下午5:34:30
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<PkgProposalModel> queryListOfAllType(PkgProposalModel model, List<String> list, DefaultPage p) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("model", model);
		return (PageList<PkgProposalModel>) this.getList("queryListOfAllType", map, p);
	}

	/**
	 * 
	 * @Description: 包装查询界面，单车型查询
	 * @param @param list
	 * @param @return   
	 * @return PkgProposalModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月28日 下午8:41:20
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<PkgProposalModel> queryListOfSigleType(PkgProposalModel model, List<String> list, DefaultPage p) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("model", model);
		return (PageList<PkgProposalModel>) this.getList("queryListOfSigleType", map, p);
	}

	/**
	 * 
	 * @Description: 包装查询界面，组合车型查询
	 * @param @param list
	 * @param @return   
	 * @return PkgProposalModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月28日 下午9:52:44
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<PkgProposalModel> queryListOfComPackType(PkgProposalModel model, List<String> list ,DefaultPage p) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("model", model);
		return (PageList<PkgProposalModel>) this.getList("queryListOfComPackType", map ,p);
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public PageList<PkgProposalModel> queryBoxNumForPage(PkgProposalModel model, DefaultPage p) {
		
		return (PageList<PkgProposalModel>) this.getList("queryBoxNumForPage", model, p);
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public PageList<PkgProposalModel> queryTrayNumForPage(PkgProposalModel model, DefaultPage p) {
		
		return (PageList<PkgProposalModel>) this.getList("queryTrayNumForPage", model, p);
	}

	/**
	 * 
	 * @Description: 容器管理界面，箱子需求量维护
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 上午10:45:39
	 */
	@Override
	public void updateBoxNeed(PkgProposalModel model) {
		this.updateByKey("updateBoxNeed", model);
	}
	
	/**
	 * 
	 * @Description: 箱子纳入量更新
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 下午4:01:19
	 */
	@Override
	public void updateBoxProvide(PkgProposalModel model) {
		this.updateByKey("updateBoxProvide", model);
	}

	/**
	 * 
	 * @Description: 托盘纳入量管理
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 下午4:59:05
	 */
	@Override
	public void updateTrayProvide(PkgProposalModel model) {
		this.updateByKey("updateTrayProvide", model);
	}

	/**
	 * 
	 * @Description: 托盘需求量管理
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 下午4:59:31
	 */
	@Override
	public void updateTrayNeed(PkgProposalModel model) {
		this.updateByKey("updateTrayNeed", model);
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public List<PkgProposalModel> getCarType(PkgProposalModel model) {
		
		return (List<PkgProposalModel>) this.getList("getCarType", model);
	}

	/**
	 * 
	 * @Description: 查询导出数据
	 * @param @param model
	 * @param @param carList
	 * @param @return   
	 * @return List<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 上午10:15:38
	 */
	@Override
	public List<PkgProposalModel> queryListOfAllType(PkgProposalModel model, List<String> carList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", carList);
		map.put("model", model);
		return this.getList("queryListOfAllType", map);
	}

	/**
	 * 
	 * @Description: 查询导出数据
	 * @param @param model
	 * @param @param carList
	 * @param @return   
	 * @return List<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月6日 上午10:15:38
	 */
	@Override
	public List<PkgProposalModel> queryListOfSigleType(PkgProposalModel model, List<String> carList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", carList);
		map.put("model", model);
		return this.getList("queryListOfSigleType", map);
	}

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
	@Override
	public List<PkgProposalModel> queryListOfComPackType(PkgProposalModel model, List<String> carList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", carList);
		map.put("model", model);
		return this.getByKey("queryListOfComPackType", map);
	}

	/**
	 * 查询待发起提案的零件信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月23日 上午11:41:56
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryApplyPartDataList(Map<String, Object> param, Page page) {
		return this.getByKey("queryApplyPartDataList", param, page);
	}

	/**
	 * 发起 添加提案
	 * @param paramList
	 * @author ZUOSL	
	 * @DATE	2018年11月23日 下午12:46:37
	 */
	@Override
	public void addProposal(List<Map<String, String>> list) {
		this.insertBatchByKey("addProposal", list);
	}

	/**
	 * 添加提案明细信息
	 * @param paramList
	 * @author ZUOSL	
	 * @DATE	2018年11月26日 上午9:45:06
	 */
	@Override
	public void addProposalDetail(List<Map<String, String>> paramList) {
		this.insertBatchByKey("addProposalDetail", paramList);
	}

	/**
	 * @param pkgModelList
	 * @author ZUOSL	
	 * @DATE	2018年11月26日 下午8:26:41
	 */
	@Override
	public void updateSupSubmitPkgProposal(List<PkgProposalModel> pkgModelList) {
		this.updateByKey("updateSupSubmitPkgProposal", pkgModelList);
	}

	/**
	 * @param pkgModelList
	 * @author ZUOSL	
	 * @DATE	2018年11月26日 下午8:26:51
	 */
	@Override
	public void updateSupSubmitPkgProposalDetail(List<PkgProposalModel> pkgModelList) {
		this.updateByKey("updateSupSubmitPkgProposalDetail", pkgModelList);
	}

	/**
	 * 查询同组的组合提案信息
	 * @param proModel
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月29日 上午10:24:24
	 */
	@Override
	public List<PkgProposalModel> queryCombPackProposal(PkgProposalModel proModel) {
		return this.getByKey("queryCombPackProposal", proModel);
	}

	/**
	 * 签字提案文件上传，保存签字提案文件ID
	 * @param pkgModel
	 * @author ZUOSL	
	 * @DATE	2018年11月29日 下午3:20:13
	 */
	@Override
	public void uploadSignProfile(PkgProposalModel pkgModel) {
		this.updateByKey("updatePkgProposalSignProFileId", pkgModel);
	}

	/**
	 * @param model
	 * @author ZUOSL	
	 * @DATE	2018年11月30日 下午3:25:28
	 */
	@Override
	public void updateProposalStatus(PkgProposalModel model) {
		this.updateByKey("updateProposalStatus", model);
	}

	/**
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月1日 上午10:13:37
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<PkgProposalModel> queryProposalDataByPartNo(Map<String, Object> param, Page page) {
		return this.getByKey("queryProposalDataByPartNo", param, page);
	}

	/**
	 * @param map
	 * @author ZUOSL	
	 * @DATE	2018年12月1日 上午11:40:00
	 */
	@Override
	public void updateProposalEffDate(Map<String, String> map) {
		this.updateByKey("updateProposalEffDate", map);
	}

	/**
	 * 
	 * @Description: 如果实物通过，则把提案信息写入到箱子数量维护表中
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年12月9日 下午2:46:45
	 */
	@Override
	public void insertBoxQty(PkgProposalModel model) {
		this.insertByKey("insertBoxQty", model);
	}

	@Override
	public List<PkgProposalModel> getProposalStatus(Map<String, Object> map) {
		
		return this.getByKey("getProposalStatus", map);
	}

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
	@Override
	public PkgProposalModel getProposalByStayPart(Map<String, Object> map) {
		
		return this.getSqlSessionTemplate().selectOne(getNamespace() + ".getProposalByStayPart", map);
	}

}
