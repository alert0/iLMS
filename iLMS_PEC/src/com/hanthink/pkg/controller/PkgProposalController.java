package com.hanthink.pkg.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.business.util.SessionKey;
import com.hanthink.pkg.manager.PkgProposalManager;
import com.hanthink.pkg.model.PkgBoxModel;
import com.hanthink.pkg.model.PkgProposalModel;
import com.hanthink.pkg.util.PkgExportUtil;
import com.hanthink.pub.manager.PubSupplierManager;
import com.hanthink.pub.model.PubSupplierModel;
import com.hanthink.util.constant.Constant;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mini.web.util.AppFileUtil;
import com.hotent.mini.web.util.DefaultFileUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.persistence.manager.FileManager;
import com.hotent.sys.persistence.model.DefaultFile;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;

/**
* <p>Title: PkgProposalController.java<／p>
* <p>Description: <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月30日
*/

@Controller
@RequestMapping("/pkg/pkgProposal")
public class PkgProposalController extends GenericController{

	@Resource
	private PkgProposalManager pkgProposalManager;
	@Resource
	private FileManager fileManager;
	@Resource
	PubSupplierManager pubSupplierManager;
	
	private static Logger log = LoggerFactory.getLogger(PkgProposalController.class);
	
	/**
	 * 
	* @Title: curdlistJson 
	* @Description: 分页查询数据列表 
	* @param @param request
	* @param @param reponse
	* @param @param model
	* @param @return
	* @param @throws Exception    
	* @return PageJson 
	* @throws 
	* @author luoxq
	* @date   2018年10月8日 下午5:00:51
	 */
	@RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") PkgProposalModel model) throws Exception{
		
        DefaultPage p = (DefaultPage) getQueryFilter(request).getPage();
        
        IUser user = ContextUtil.getCurrentUser();
		if(IUser.USER_TYPE_SUPP.equals(user.getUserType())){
			model.setSupplierNo(user.getSupplierNo());
		}
		model.setFactoryCode(user.getCurFactoryCode());
        PageList<PkgProposalModel> pageList = (PageList<PkgProposalModel>) pkgProposalManager.queryPkgProposalForPage(model, p);
        return new PageJson(pageList);
    }
	
	/**
	 * 查询待发起提案的零件数据信息
	 * @param request
	 * @param reponse
	 * @param model
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年11月23日 上午11:36:06
	 */
	@RequestMapping("queryApplyPartDataList")
    public @ResponseBody PageJson queryApplyPartDataList(HttpServletRequest request, HttpServletResponse reponse) throws Exception{
		
		Map<String,Object> param = new HashMap<String,Object>();
        Page page = getQueryFilter(request).getPage();
        param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
        param.put("CAR_TYPE", RequestUtil.getString(request, "CAR_TYPE", null));
        param.put("PART_NO", RequestUtil.getString(request, "PART_NO", null));
        param.put("SUPPLIER_NO", RequestUtil.getString(request, "SUPPLIER_NO", null));
        param.put("STATUS", RequestUtil.getString(request, "STATUS", null));
        param.put("PROJECT", RequestUtil.getString(request, "PROJECT", null));
        
        //用户要求如果提案状态除了未实物通过或者实物未通过时，其他状态的信息不能在提案管理界面同时存在
        param.put("MCHECK_YES",PkgProposalModel.PROPOSAL_STATUS_MCHECK_YES);
        param.put("MCHECK_NO", PkgProposalModel.PROPOSAL_STATUS_MCHECK_NO);

        PageList<Map<String,Object>> pageList = pkgProposalManager.queryApplyPartDataList(param, page);
        return new PageJson(pageList);
    }
	
	/**
	 * 发起 添加提案
	 * @param request
	 * @param response
	 * @param param
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年11月23日 上午11:54:28
	 */
	@RequestMapping("addProposal")
	public void addProposal(HttpServletRequest request,HttpServletResponse response,@RequestBody List<Map<String,String>> paramList) throws Exception{
		String resultMsg = null;
		
		IUser user = ContextUtil.getCurrentUser();
		
		try {
			resultMsg = "发起提案成功";
			
			for(Map<String,String> param : paramList){
				param.put("PROPOSAL_STATUS", PkgProposalModel.PROPOSAL_STATUS_START); //默认发起时提案状态为 发起
				param.put("CREATION_USER", user.getAccount());
				param.put("factoryCode", user.getCurFactoryCode());
				param.put("IS_SHOW_CHANGE", PkgProposalModel.IS_SHOW_CHANGE_NO);
				
				param.put("EMAIL_FLAG_YES", PkgProposalModel.EMAIL_FLAG_YES); //发起后修改零件担当维护表中的邮件发送标识
			}
			
			pkgProposalManager.addProposal(paramList);
			
			pkgProposalManager.udpdateEmailFlag(paramList); 
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	
	/**
	 * 供应商包装提案提交
	 * @param request
	 * @param response
	 * @param pkgModelList
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年11月26日 下午8:08:13
	 */
	@RequestMapping("supPkgProposalSubmit")
	public void supPkgProposalSubmit(HttpServletRequest request,HttpServletResponse response,
			@RequestBody List<PkgProposalModel> pkgModelList) throws Exception{
		
		String resultMsg = null;
		IUser user = ContextUtil.getCurrentUser();
		
		//只允许供应商提交自己数据
		if(IUser.USER_TYPE_SUPP.equals(user.getUserType())){
			for(PkgProposalModel m : pkgModelList){
				if(!user.getSupplierNo().equals(m.getSupplierNo())){
					writeResultMessage(response.getWriter(), "供应商信息错误", "",ResultMessage.FAIL);
				}
			}
		}else{
			writeResultMessage(response.getWriter(), "用户类型不允许该操作", "用户类型不允许该操作",ResultMessage.FAIL);
			return;
		}
		
		try {
			resultMsg = "提案提交成功";
			
			pkgProposalManager.supPkgProposalSubmit(pkgModelList);
			
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			resultMsg="操作失败";
//			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 提案审核 审核提交
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @throws Exception 
	 * @DATE	2018年11月29日 下午5:33:54
	 */
	@RequestMapping("proposalCheckSubmit")
	public void proposalCheckSubmit(HttpServletRequest request,HttpServletResponse response,
			@RequestBody List<Map<String, String>> paramList) throws Exception{
		
		if(null == paramList || 0 >= paramList.size()){
			writeResultMessage(response.getWriter(), "审核失败", "参数获取失败",ResultMessage.FAIL);
			return;
		}
		
		String checkResult = paramList.get(0).get("checkResult"); //审核结果：1-通过 0-不通过
		String checkRemark = paramList.get(0).get("checkRemark"); //审核备注
		if(StringUtil.isEmpty(checkResult)){
			writeResultMessage(response.getWriter(), "审核失败", "参数获取失败",ResultMessage.FAIL);
			return;
		}
		
		String resultMsg = null;
		IUser user = ContextUtil.getCurrentUser();
		
		try {
			resultMsg = "审核提交成功";
			
			List<PkgProposalModel> pkgModelList = new ArrayList<PkgProposalModel>();
			for(Map<String, String> map : paramList){
				String id = map.get("id");
				if(StringUtil.isEmpty(id)){
					writeResultMessage(response.getWriter(), "审核失败", "参数获取失败",ResultMessage.FAIL);
					return;
				}
				PkgProposalModel model = new PkgProposalModel();
				model.setId(id);
				model.setProposalStatus("1".equals(checkResult) ? PkgProposalModel.PROPOSAL_STATUS_CHECK_YES : PkgProposalModel.PROPOSAL_STATUS_CHECK_NO);
				model.setCheckRemark(checkRemark);
				pkgModelList.add(model);
			}
			
			pkgProposalManager.proposalCheckSubmit(pkgModelList, user);
			
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			resultMsg="操作失败";
//			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
		
	}

	
	/**
	 * 提案审核-实物审核提交
	 * @param request
	 * @param response
	 * @param paramList
	 * @author ZUOSL	
	 * @throws Exception 
	 * @DATE	2018年11月30日 下午4:12:53
	 */
	@RequestMapping("proposalMaterialCheckSubmit")
	public void proposalMaterialCheckSubmit(HttpServletRequest request,HttpServletResponse response,
			@RequestBody Map<String, String> map) throws Exception{
		
		String resultMsg = null;
		try {
			String checkResult = map.get("checkResult"); //审核结果：1-通过 0-不通过
			String ids = map.get("id"); //当前审核ID
			if(StringUtil.isEmpty(checkResult) || StringUtil.isEmpty(ids)){
				writeResultMessage(response.getWriter(), "审核失败", "参数获取失败",ResultMessage.FAIL);
				return;
			}
			
			String idArr[] = ids.split(",");
			PkgProposalModel model = new PkgProposalModel();
			model.setIdArr(idArr);
			
			IUser user = ContextUtil.getCurrentUser();
			resultMsg = "审核提交成功";
			
			if("0".equals(checkResult)){ // 审核结果为不通过时，直接更新提案状态
				model.setProposalStatus(PkgProposalModel.PROPOSAL_STATUS_MCHECK_NO);
			}else if("1".equals(checkResult)){ //审核结果为通过时，检查生失效日期是否存在重叠
				String partNoStr = map.get("PART_NO");
				
				String effStart = map.get("EFF_START");
				String effEnd = map.get("EFF_END");
				if(StringUtil.isEmpty(partNoStr) || StringUtil.isEmpty(effStart) || StringUtil.isEmpty(effEnd)){
					writeResultMessage(response.getWriter(), "更新失败", "参数获取失败",ResultMessage.FAIL);
					return;
				}
				String partNoArr[] = partNoStr.split(",");
				
				model.setEffStart(effStart);
				model.setEffEnd(effEnd);
				model.setProposalStatus(PkgProposalModel.PROPOSAL_STATUS_MCHECK_YES);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date curDateStart = sdf.parse(effStart);
				Date curDateEnd = sdf.parse(effEnd);
				if(curDateStart.getTime() > curDateEnd.getTime()){
					writeResultMessage(response.getWriter(), "审核失败", "结束日期应大于开始日期",ResultMessage.FAIL);
					return;
				}
				
				//根据零件号查询已有提案的生失效日期，检查日期是否有交叉重叠
				Map<String,Object> param = new HashMap<String,Object>();
		        param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
		        param.put("partNoArr", partNoArr);
		        param.put("PROPOSAL_STATUS", PkgProposalModel.PROPOSAL_STATUS_MCHECK_YES); //只查询实物审核通过的数据
		        Page page = new DefaultPage(1, 10000);
		        PageList<PkgProposalModel> pageList = pkgProposalManager.queryProposalDataByPartNo(param, page);
		        
		        String id = null;
		        for (int i = 0; i < idArr.length; i++) {
				  id = idArr[i];
		        for(PkgProposalModel m : pageList){
		        	if(!id.equals(m.getId())){
		        		if(StringUtil.isEmpty(m.getEffStart()) || StringUtil.isEmpty(m.getEffEnd())){
		        			continue;
		        		}
		        		Date tmpStart = sdf.parse(m.getEffStart());
		        		Date tmpEnd = sdf.parse(m.getEffEnd());
		        		if(!(curDateEnd.getTime() < tmpStart.getTime() || curDateStart.getTime() > tmpEnd.getTime())){
		        			writeResultMessage(response.getWriter(), "审核失败", 
		        					"该零件在车型【" + m.getCarType() + "】的生失效日期为【" + m.getEffStart() + "~" + m.getEffEnd() + "】，日期存在重合，请修正日期", 
		        					ResultMessage.FAIL);
		        			return;
		        		}
		        	}
		        }
		        }
				
			}
			
			model.setFactoryCode(user.getCurFactoryCode());
			pkgProposalManager.proposalMaterialCheckSubmit(model, user, RequestUtil.getIpAddr(request));
			
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			resultMsg="操作失败";
//			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
		
	}
	
	/**
	 * 查询相关零件的生失效日期信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2018年11月30日 下午4:09:52
	 */
	@RequestMapping("queryMaterialCheckDataList")
    public @ResponseBody PageJson queryMaterialCheckDataList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map<String,Object> param = new HashMap<String,Object>();
        Page page = getQueryFilter(request).getPage();
        String partNos = RequestUtil.getString(request, "partNo");
        String partNoArr[] = partNos.split(",");
        param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
        param.put("partNoArr",partNoArr);
        param.put("PROPOSAL_STATUS", PkgProposalModel.PROPOSAL_STATUS_MCHECK_YES); //只查询实物审核通过的数据
        
        PageList<PkgProposalModel> pageList = pkgProposalManager.queryProposalDataByPartNo(param, page);
        return new PageJson(pageList);
    }
	
	/**
	 * 
	 * @Description: 批量修改查询出相关零件的生失效日期
	 * @param @param request
	 * @param @param response   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月9日 下午6:57:18
	 */
	@RequestMapping("updatePartDataAll")
	public void updatePartDataAll(HttpServletRequest request, HttpServletResponse response, PkgProposalModel model) throws Exception {
		String resultMsg = null;
		String ids = null;
		try {
			if (null != model && model.getId() !=null) {
				ids = model.getId();
			}
			if (StringUtil.isNotEmpty(ids)) {
				String idArr[] = ids.split(",");
				model.setId("");
				model.setIdArr(idArr);
				pkgProposalManager.updatePartDataAll(model);
				resultMsg = "生失效日期修改成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
		
	}
	
	/**
	 * 更新包装提案的生失效日期
	 * @param request
	 * @param response
	 * @param map
	 * @author ZUOSL	
	 * @throws IOException 
	 * @DATE	2018年12月1日 上午11:27:03
	 */
	@RequestMapping("updteProposalEffDate")
	public void updteProposalEffDate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> map) throws IOException{
		
		try {
			
			String curId = (String) map.get("ID");
			String idArr[] = curId.split(",");
			map.put("idArr",idArr);
			String partNo = (String) map.get("PART_NO");
			String []partNoArr = partNo.split(",");
			String effStart = (String) map.get("EFF_START");
			String effEnd = (String) map.get("EFF_END");
			if(StringUtil.isEmpty(curId) || StringUtil.isEmpty(partNo)
					|| StringUtil.isEmpty(effStart) || StringUtil.isEmpty(effEnd)){
				writeResultMessage(response.getWriter(), "更新失败", "参数获取失败",ResultMessage.FAIL);
				return;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date curDateStart = sdf.parse(effStart);
			Date curDateEnd = sdf.parse(effEnd);
			if(curDateStart.getTime() > curDateEnd.getTime()){
				writeResultMessage(response.getWriter(), "更新失败", "结束日期应大于开始日期",ResultMessage.FAIL);
				return;
			}
			
			//根据零件号查询已有提案的生失效日期，检查日期是否有交叉重叠
			Map<String,Object> param = new HashMap<String,Object>();
	        Page page = new DefaultPage(1, 10000);
	        param.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
	        param.put("partNoArr", partNoArr);
	        param.put("PROPOSAL_STATUS", PkgProposalModel.PROPOSAL_STATUS_MCHECK_YES); //只查询实物审核通过的数据
	        PageList<PkgProposalModel> pageList = pkgProposalManager.queryProposalDataByPartNo(param, page);
	        for(PkgProposalModel model : pageList){
	        	if(!curId.equals(model.getId())){
	        		if(StringUtil.isEmpty(model.getEffStart()) || StringUtil.isEmpty(model.getEffEnd())){
	        			continue;
	        		}
	        		Date tmpStart = sdf.parse(model.getEffStart());
	        		Date tmpEnd = sdf.parse(model.getEffEnd());
	        		if(!(curDateEnd.getTime() < tmpStart.getTime() || curDateStart.getTime() > tmpEnd.getTime())){
	        			writeResultMessage(response.getWriter(), "更新失败", 
	        					"该零件在车型【" + model.getCarType() + "】的生失效日期为【" + model.getEffStart() + "~" + model.getEffEnd() + "】，日期存在重合，请修正日期", 
	        					ResultMessage.FAIL);
	        			return;
	        		}
	        	}
	        }
			
			//更新日期
			pkgProposalManager.updteProposalEffDate(map);
			
			writeResultMessage(response.getWriter(),"更新成功",ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(),"操作异常",e.getMessage(),ResultMessage.FAIL);
		}
		
	}
	
	
	/**
	 * 导出包装提案书
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2018年11月26日 下午9:38:10
	 */
	@RequestMapping("exportPkgPorposalFile")
	public void exportPkgPorposalFile(HttpServletRequest request,HttpServletResponse response){
		
		//包装提案书ID
		String id = RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			try {
				ExcelUtil.downloadFileError(request, response, "获取参数失败");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		String[] idArr = id.split(",");
		if(1 == idArr.length){
			exportSingleProposalFile(id, request, response);
		}else{ 
			//多个提案书ID，生成提案书文件后进行压缩打包下载
			exportMultProposalFile(idArr, request, response);
		}
		
	}
	
	/**
	 * 多个提案书导出，将提案书打包压缩导出
	 * @param idArr
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2019年1月8日 下午4:31:29
	 */
	@SuppressWarnings("resource")
	private void exportMultProposalFile(String[] idArr, HttpServletRequest request, HttpServletResponse response) {
		
		PkgExportUtil exportUtil = new PkgExportUtil(fileManager);
		
		//导出设置
		String ymdhms = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String exportFileName = "包装提案书导出【" + ymdhms + "】.rar";
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String downName = null;
    	try {
    		if (request.getHeader("user-agent").toLowerCase().contains("msie")
    				|| request.getHeader("user-agent").toLowerCase().contains("like gecko") ) {
				downName = URLEncoder.encode(exportFileName, "UTF-8");
	    	}else{
	    		downName = new String(exportFileName.getBytes("UTF-8"), "ISO_8859_1");
	    	}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
			return;
		}
		response.setHeader("Content-disposition", "attachment; filename=" + downName);
		
		byte[] buffer = new byte[4096];
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			log.error("获取output异常："+e.toString());
			return;
		}
		
		//遍历提案信息 生成提案书文件
		for(String id : idArr){
			PkgProposalModel proModel = pkgProposalManager.get(id);
			if(proModel == null){
				continue;
			}
			
			//如果为台车且为组合包装，需查询相关组合零件信息 --> 非台车也要有组合包装的情况，直接根据是否组合包装进行判断
			List<PkgProposalModel> proModelList = null;
			if(PkgProposalModel.IS_COM_PACK_YES.equals(proModel.getIsComPack())){
				proModelList = pkgProposalManager.queryCombPackProposal(proModel);
			}else{
				proModelList = new ArrayList<PkgProposalModel>();
				proModelList.add(proModel);
			}
			
			//供应商等其他数据信息
			PubSupplierModel qmodel = new PubSupplierModel();
			qmodel.setSupplierNo(proModel.getSupplierNo());
//			PubSupplierModel supplierModel = pubSupplierManager.querySupplierBySupplierNo(qmodel);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put(PkgExportUtil.SUP_DATE, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//			if(null != supplierModel){
//				paramMap.put(PkgExportUtil.SUP_DEPT, supplierModel.getPackDeptName());
//				paramMap.put(PkgExportUtil.SUP_EMAIL, supplierModel.getPackDeptMail());
//				paramMap.put(PkgExportUtil.SUP_PACK_BZ, supplierModel.getPackDeptMinister());
//				paramMap.put(PkgExportUtil.SUP_PACK_KZ, supplierModel.getPackDeptChief());
//				paramMap.put(PkgExportUtil.SUP_PACK_DD, supplierModel.getPackDeptContact());
//				paramMap.put(PkgExportUtil.SUP_TEL, supplierModel.getPackDeptTel());
//			}
			
			//文件打包
			String tmpFileName = "包装提案书【" + proModel.getCarType() + "-" + proModel.getPartNo() + "】" + ExcelUtil.EXCEL_XLSX;
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if(PkgProposalModel.PACK_TYPE_TROLLEY.equals(proModel.getPackType())){
					exportUtil.exportPkgProposalTrolley(proModelList, paramMap, baos);
				}else if(!PkgProposalModel.IS_COM_PACK_YES.equals(proModel.getIsComPack())){
					exportUtil.exportPkgProposalNormal(proModel, paramMap, baos);
				}else{
					exportUtil.exportPkgProposalNormalCom(proModelList, paramMap, baos);
				}
				
				InputStream in = new ByteArrayInputStream(baos.toByteArray());
				out.putNextEntry(new ZipEntry(tmpFileName));
				int len = 0;
				while((len = in.read(buffer)) > 0){
					out.write(buffer,0,len); 
				}
				out.closeEntry();
				in.close();
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("打包文件导出异常："+e.toString());
				return ;
			}
			
		}
		
		try {
			if(null != out){
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("关闭out异常："+e.toString());
			return ;
		}
		
	}

	/**
	 * 单条提案书导出
	 * @param id
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2019年1月8日 上午9:59:27
	 */
	private void exportSingleProposalFile(String id, HttpServletRequest request, HttpServletResponse response) {
		
		PkgProposalModel proModel = pkgProposalManager.get(id);
		if(null == proModel){
			try {
				ExcelUtil.downloadFileError(request, response, "获取提案信息失败");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		//如果为台车且为组合包装，需查询相关组合零件信息 --> 非台车也要有组合包装的情况，直接根据是否组合包装进行判断
		List<PkgProposalModel> proModelList = null;
//		if(PkgProposalModel.PACK_TYPE_TROLLEY.equals(proModel.getPackType())
//				&& PkgProposalModel.IS_COM_PACK_YES.equals(proModel.getIsComPack())){
//			proModelList = pkgProposalManager.queryCombPackProposal(proModel);
//		}else if(PkgProposalModel.PACK_TYPE_TROLLEY.equals(proModel.getPackType())){
//			proModelList = new ArrayList<PkgProposalModel>();
//			proModelList.add(proModel);
//		}
		if(PkgProposalModel.IS_COM_PACK_YES.equals(proModel.getIsComPack())){
			proModelList = pkgProposalManager.queryCombPackProposal(proModel);
		}else{
			proModelList = new ArrayList<PkgProposalModel>();
			proModelList.add(proModel);
		}
		
		//供应商等其他数据信息
		PubSupplierModel qmodel = new PubSupplierModel();
		qmodel.setSupplierNo(proModel.getSupplierNo());
//		PubSupplierModel supplierModel = pubSupplierManager.querySupplierBySupplierNo(qmodel);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(PkgExportUtil.SUP_DATE, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//		if(null != supplierModel){  //供应商信息由从供应商表中获取--》改为供应商自己填写
//			paramMap.put(PkgExportUtil.SUP_DEPT, supplierModel.getPackDeptName());
//			paramMap.put(PkgExportUtil.SUP_EMAIL, supplierModel.getPackDeptMail());
//			paramMap.put(PkgExportUtil.SUP_PACK_BZ, supplierModel.getPackDeptMinister());
//			paramMap.put(PkgExportUtil.SUP_PACK_KZ, supplierModel.getPackDeptChief());
//			paramMap.put(PkgExportUtil.SUP_PACK_DD, supplierModel.getPackDeptContact());
//			paramMap.put(PkgExportUtil.SUP_TEL, supplierModel.getPackDeptTel());
//		}
		
		String exportFileName = "包装提案书【" + proModel.getCarType() + "-" + proModel.getPartNo() + "】" + ExcelUtil.EXCEL_XLSX;
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String downName = null;
    	try {
    		if (request.getHeader("user-agent").toLowerCase().contains("msie")
    				|| request.getHeader("user-agent").toLowerCase().contains("like gecko") ) {
				downName = URLEncoder.encode(exportFileName, "UTF-8");
	    	}else{
	    		downName = new String(exportFileName.getBytes("UTF-8"), "ISO_8859_1");
	    	}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
			return;
		}
		response.setHeader("Content-disposition", "attachment; filename=" + downName);
		
		PkgExportUtil exportUtil = new PkgExportUtil(fileManager);
		try {
			if(PkgProposalModel.PACK_TYPE_TROLLEY.equals(proModel.getBoxType())){
				exportUtil.exportPkgProposalTrolley(proModelList, paramMap, response.getOutputStream());
			}else if(!PkgProposalModel.IS_COM_PACK_YES.equals(proModel.getIsComPack())){
				exportUtil.exportPkgProposalNormal(proModel, paramMap, response.getOutputStream());
			}else{
				exportUtil.exportPkgProposalNormalCom(proModelList, paramMap, response.getOutputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			return;
		}
		
	}

	/**
	 * 签字提案文件上传后保存文件ID到业务表
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2018年11月29日 下午3:08:53
	 */
	@RequestMapping("uploadSignProFile")
	public void uploadSignProFile(HttpServletRequest request,HttpServletResponse response){
		try {
			String id = RequestUtil.getString(request, "id");
			String fileId = RequestUtil.getString(request, "fileId");
			if(StringUtil.isEmpty(id) || StringUtil.isEmpty(fileId)){
				writeResultMessage(response.getWriter(), "参数获取失败", "参数获取失败",ResultMessage.FAIL);
			}
			
			PkgProposalModel pkgModel = new PkgProposalModel();
			pkgModel.setId(id);
			pkgModel.setSignProFile(fileId);
			
			pkgProposalManager.uploadSignProFile(pkgModel);
			
			writeResultMessage(response.getWriter(), "保存成功", "保存成功",ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			try {
				writeResultMessage(response.getWriter(),"操作失败",e.getMessage(),ResultMessage.FAIL);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 批量导出提案书的签字提案文件
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2019年1月8日 下午16:55:26
	 */
	@RequestMapping("downloadSignProFile")
	public void downloadSignProFile(HttpServletRequest request,HttpServletResponse response){
		
		//文件ID
		String id = RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			try {
				ExcelUtil.downloadFileError(request, response, "获取参数失败");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		String[] idArr = id.split(",");
		if(1 == idArr.length){
			exportSingleSignProFile(id, request, response);
		}else{ 
			//多个文件ID，进行压缩打包下载
			exportMultSignProFile(idArr, request, response);
		}
		
	}
	
	/**
	 * 多个签字提案文件压缩打包导出
	 * @param id
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2019年1月8日 下午17:51:23
	 */
	@SuppressWarnings("resource")
	private void exportMultSignProFile(String[] idArr, HttpServletRequest request, HttpServletResponse response) {
		
		//导出设置
		String ymdhms = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String exportFileName = "签字提案书导出【" + ymdhms + "】.rar";
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String downName = null;
    	try {
    		if (request.getHeader("user-agent").toLowerCase().contains("msie")
    				|| request.getHeader("user-agent").toLowerCase().contains("like gecko") ) {
				downName = URLEncoder.encode(exportFileName, "UTF-8");
	    	}else{
	    		downName = new String(exportFileName.getBytes("UTF-8"), "ISO_8859_1");
	    	}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
			return;
		}
		response.setHeader("Content-disposition", "attachment; filename=" + downName);
		
		byte[] buffer = new byte[4096];
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			log.error("获取output异常："+e.toString());
			return;
		}
		
		//遍历文件ID，打包导出
		for(String id : idArr){
			
			DefaultFile file = fileManager.get(id);
			if(null == file){
				continue;
			}
			
			//文件打包
			String tmpFileName = file.getFileName() + "." + file.getExt();
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DefaultFileUtil.exportFile(file, baos);
				InputStream in = new ByteArrayInputStream(baos.toByteArray());
//				OutputStream baos = new FileOutputStream(new File("e:\\"+tmpFileName));
//				DefaultFileUtil.exportFile(file, baos);
//				InputStream in = new FileInputStream(new File("e:\\"+tmpFileName));
				out.putNextEntry(new ZipEntry(tmpFileName));
				int len = 0;
				while((len = in.read(buffer)) > 0){
					out.write(buffer,0,len); 
				}
				out.closeEntry();
				in.close();
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("打包文件导出异常："+e.toString());
				return ;
			}
		}
		
		try {
			if(null != out){
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("关闭out异常："+e.toString());
			return ;
		}
	}

	/**
	 * 单个签字提案文件导出
	 * @param id
	 * @param request
	 * @param response
	 * @author ZUOSL	
	 * @DATE	2019年1月8日 下午17:01:28
	 */
	private void exportSingleSignProFile(String id, HttpServletRequest request, HttpServletResponse response) {
		DefaultFile file = fileManager.get(id);
		if (file == null) {
			try {
				ExcelUtil.downloadFileError(request, response, "获取文件信息失败");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		try {
			String fileName = file.getFileName() + "." + file.getExt();
			if(file.getStoreType().equals(DefaultFile.FILE_STORE_DB)){
				RequestUtil.downLoadFileByByte(request, response, file.getBytes(), fileName);
			}else if(DefaultFile.FILE_STORE_FTP.equals(file.getStoreType())){
				try {
					RequestUtil.downLoadFileByFtp(request, response, file, fileName);
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.toString());
					String msg = "download error!" + e.toString();
					response.getOutputStream().write(msg.getBytes("utf-8"));
				}
			}else{
				String filePath = file.getFilePath();
				if (StringUtil.isEmpty(filePath)){
					try {
						ExcelUtil.downloadFileError(request, response, "获取文件信息失败");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				String fullPath = StringUtil.trimSuffix(AppFileUtil.ATTACH_PATH, File.separator) + File.separator + filePath.replace("/", File.separator);
				RequestUtil.downLoadFile(request, response, fullPath, fileName);
			}
		} catch (Exception e) {
			try {
				ExcelUtil.downloadFileError(request, response, "导出异常："+e.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			log.error(e.toString());
			return;
		}
	}

	/**
	 * 
	* @Title: curdgetJson 
	* @Description: 通过id获取明细 
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws Exception    
	* @return PkgProposalModel 
	* @throws 
	* @author luoxq
	* @date   2018年10月8日 下午5:01:10
	 */
	@RequestMapping("curdgetJson")
	public @ResponseBody PkgProposalModel curdgetJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new PkgProposalModel();
		}
	    PkgProposalModel p = pkgProposalManager.get(id);
		return p;
	}
	
	/**
	 * 
	* @Title: getUnloadBoxCode 
	* @Description: 包装提案界面获取箱code下拉框 
	* @param @param request
	* @param @param reponse
	* @param @return
	* @param @throws Exception    
	* @return Object 
	* @throws 
	* @author luoxq
	* @date   2018年10月9日 下午12:26:04
	 */
	@RequestMapping("getUnloadBoxCode")
	public @ResponseBody Object getUnloadBoxCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
            List<DictVO> models = pkgProposalManager.getUnloadBoxCode();
            return new PageJson(models);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 
	* @Title: save 
	* @Description: 供应商包装提案 
	* @param @param request
	* @param @param response
	* @param @param pkgProposalModel
	* @param @throws Exception    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月9日 下午5:19:43
	 */
	@RequestMapping("save")
	public void save(MultipartFile  singlePartPutPic, MultipartFile packSideLookPic, MultipartFile singlePartPic,MultipartFile packOverLookPic, HttpServletRequest request,HttpServletResponse response,@RequestBody PkgProposalModel pkgProposalModel) throws Exception{
		String resultMsg = null;
		IUser user = ContextUtil.getCurrentUser();
		String id = RequestUtil.getString(request, "id");
		System.out.println(singlePartPutPic);
		
		try {
			HttpSession session = request.getSession();
			if (null != session) {
				PkgBoxModel p = (PkgBoxModel) session.getAttribute("pkgBoxModel");
				if (null != p) {
					pkgProposalModel.setPackLength(p.getPackLength());
					pkgProposalModel.setPackWidth(p.getPackWidth());;
					pkgProposalModel.setPackHeight(p.getPackHeight());;
				}
			}
			
			pkgProposalModel.setCreateBy(user.getAccount()); //记录包装提案发起人
			pkgProposalModel.setFactoryCode(user.getCurFactoryCode());
			
			pkgProposalModel.setProposalId(id);
			pkgProposalManager.insertNotTrolley(pkgProposalModel);
			resultMsg="包装提案成功";

			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 
	* @Title: updateProposalStatus 
	* @Description: 修改提案状态 
	* @param @param request
	* @param @param response
	* @param @throws Exception    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月10日 下午2:31:48
	 */
	@RequestMapping("updateProposalStatus")
	public void updateProposalStatus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String resultMsg = null;
		String[] ids = RequestUtil.getStringAryByStr(request, "ids");
		String proposalStatus = RequestUtil.getString(request, "proposalStatus");
		PkgProposalModel pkgProposalModel = new PkgProposalModel();
		pkgProposalModel.setProposalStatus(proposalStatus);
		try {
            for (String id : ids) {
            	pkgProposalModel.setId(id);
            	resultMsg = pkgProposalManager.updateProposal(pkgProposalModel);
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	
	/**
	 * 
	* @Title: downloadPkgProposalModel 
	* @Description: 包装管理数据导出 
	* @param @param request
	* @param @param response    
	* @return void 
	* @throws 
	* @author luoxq
	* @date   2018年10月11日 上午11:04:54
	 */
	@RequestMapping("downloadPkgProposalModel")
	public void downloadPkgProposalModel( HttpServletRequest request,HttpServletResponse response,PkgProposalModel model){
		try {
		String exportFileName = "包装管理数据导出 " + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//		PkgProposalModel model = (PkgProposalModel)session.getAttribute(SessionKey.PKG_PROPOSAL_QUERYFILTER);
		List<PkgProposalModel> list = new ArrayList<PkgProposalModel>();
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		
        String carTypes = model.getCarTypes();
        String []carTypeArr = carTypes.split(",");
        List<String> carList = new ArrayList<String>();
        if (null != carTypes && StringUtil.isNotEmpty(carTypes)) {
        	for (String carType : carTypeArr) {
        		carList.add(carType);
        	}
		}
        //如果前端没有输入查询类型，默认为全车型查询
    	if (model.getQueryType() == null || model.getQueryType() == "") {
			model.setQueryType("0");
		}
    	//如果是全车型查询
        if (Constant.PKG_PROP0SAL_ALL.equals(model.getQueryType()) ) {
        	list = pkgProposalManager.queryListOfAllType(model,carList);
//			pageList.add(pkgProposalModel);
			}
        //如果是单车型
        if (Constant.PKG_PROPOSAL_SINGLE.equals(model.getQueryType()) ) {
        	list = pkgProposalManager.queryListOfSigleType(model,carList);
		}
        //如果是组合车型
        if (Constant.PKG_PROPOSAL_COM.equals(model.getQueryType())) {
        	if (carList.size() < 1) {
        		list = pkgProposalManager.queryListOfSigleType(model,carList);
			} else {
				list = pkgProposalManager.queryListOfComPackType(model,carList );
			}
        	
		}
//		List<PkgProposalModel> list =  pkgProposalManager.queryPkgProposalByKey(model);
		/**
		 * 如果查询记录超过10000条则报错
		 */
		if(0 == list.size()){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000); //获取系统所允许的最大导出数量
		if(list.size() > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		String[] headers = {"车型","供应商代码", "供应商名称", "区域", "零件号",
							"箱种","箱CODE","长","宽","高","零件重","箱子重"
							,"总重量","防尘罩","逐个包装","内材"};
		String[] columns = {"carType","supplierNo", "supplierName", "area", "partNo",
							"boxType","boxCode","packLength","packWidth","packHeight","partWeight","packWeight","totalWeight"
							,"dustCover","onePackage","intMate"};
		int[] widths = {80,80, 120, 80, 80,80,80,80,80,80,80,80,80,80,
				        80,80};
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	
	/**
	 * 
	 * @Description: 包装管理信息查询界面，分页查询功能
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月28日 下午4:19:49
	 */
	@RequestMapping("getPkgMsgList")
    public @ResponseBody PageJson getPkgMsgList(HttpServletRequest request,
            HttpServletResponse reponse,
             PkgProposalModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
        		getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        PageList<PkgProposalModel> pageList = new PageList<PkgProposalModel>();

        String carTypes = model.getCarTypes();
        String []carTypeArr = carTypes.split(",");
        List<String> list = new ArrayList<String>();
        if (null != carTypes && StringUtil.isNotEmpty(carTypes)) {
        	for (String carType : carTypeArr) {
        		list.add(carType);
        	}
		}
        else {
			list = null;
		}
    	if (model.getQueryType() == null || model.getQueryType() == "") {
			model.setQueryType(Constant.PKG_PROP0SAL_ALL);
		}
    	//如果是全车型查询
        if (Constant.PKG_PROP0SAL_ALL.equals(model.getQueryType()) ) {
			pageList = pkgProposalManager.queryListOfAllType(model,list,p);
//			pageList.add(pkgProposalModel);
			}
        //如果是单车型
        if (Constant.PKG_PROPOSAL_SINGLE.equals(model.getQueryType()) ) {
        	pageList = pkgProposalManager.queryListOfSigleType(model,list, p);
		}
        //如果是组合车型
        if (Constant.PKG_PROPOSAL_COM.equals(model.getQueryType())) {
        	//选择组合查询，但是没有输入车型，则按照单车型查询
        	if (list.size() < 1) {
        		pageList = pkgProposalManager.queryListOfSigleType(model,list, p);
			} else {
				pageList = pkgProposalManager.queryListOfComPackType(model,list , p);
			}
		}
        return new PageJson(pageList);
    }
    
	/**
	 * 
	 * @Description: 容器管理界面，箱子数量查询
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2018年10月29日 上午12:03:58
	 */
	@RequestMapping("curdBoxNumlistJson")
    public @ResponseBody PageJson curdBoxNumlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") PkgProposalModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        PageList<PkgProposalModel> pageList = (PageList<PkgProposalModel>) pkgProposalManager.queryBoxNumForPage(model, p);
        return new PageJson(pageList);
    }
	
	/**
	 * 
	 * @Description: 容器箱子数量导出
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月7日 上午10:11:14
	 */
	@RequestMapping("downloadBoxModel")
	public void downloadBoxModel(HttpServletRequest request, HttpServletResponse response, PkgProposalModel model)
			throws Exception {
		try {
			String exportFileName = "箱子数量导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			IUser user = ContextUtil.getCurrentUser();
			model.setFactoryCode(user.getCurFactoryCode());

			List<PkgProposalModel> list = pkgProposalManager.queryPkgBoxByKey(model);
			/**
			 * 如果查询记录超过10000条则报错
			 */
			if (0 == list.size()) {
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000); // 获取系统所允许的最大导出数量
			if (list.size() > sysMaxNum) {
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}

			String[] headers = { "车型", "供应商代码", "供应商名称", "区域", "零件号", "零件名称", "车间", 
					"物流模式", "箱种", "箱CODE", "包装长(mm)","包装宽(mm)", "包装高(mm)", "收容数", "箱子需求量",
					"已纳入量", "计划完成日期", "延迟原因", "id","提案ID"};
			String[] columns = { "carType", "supplierNo", "supplierName", "area", "partNo", "partNameCn", "project",
					"hairNoteModel", "boxType", "boxCode", "packLength", "packWidth", "packHeight", "standardPackage", "boxRequireQty",
					"provideQty", "planDate", "delayReason" ,"id", "proposalId"};
			int[] widths = { 80, 100, 150, 80, 100, 150, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 100, 200, 40, 40};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths,
					columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	/**
	 * 
	 * @Description: 容器管理界面，托盘数量查询
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月2日 下午5:28:02
	 */
	@RequestMapping("curdTrayNumlistJson")
    public @ResponseBody PageJson curdTrayNumlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") PkgProposalModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        PageList<PkgProposalModel> pageList = (PageList<PkgProposalModel>) pkgProposalManager.queryTrayNumForPage(model, p);
        return new PageJson(pageList);
    }
	
	/**
	 * 
	 * @Description: 导出托盘信息
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月7日 上午11:11:50
	 */
	@RequestMapping("downloadTrayModel")
	public void downloadTrayModel(HttpServletRequest request, HttpServletResponse response, PkgProposalModel model)
			throws Exception {
		try {
			String exportFileName = "托盘数量导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			IUser user = ContextUtil.getCurrentUser();
			model.setFactoryCode(user.getCurFactoryCode());

			List<PkgProposalModel> list = pkgProposalManager.queryPkgTrayByKey(model);
			/**
			 * 如果查询记录超过10000条则报错
			 */
			if (0 == list.size()) {
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 10000); // 获取系统所允许的最大导出数量
			if (list.size() > sysMaxNum) {
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}

			String[] headers = { "车型", "供应商代码", "供应商名称", "需求量", "已纳入量", "计划日期", "延迟原因", "id"};
			String[] columns = { "carType", "supplierNo", "supplierName", "trayRequireQty", "provideQty", "planDate", "delayReason", "id"};
			int[] widths = { 80, 100, 150, 80, 80, 150, 200,80};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths,
					columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	/**
	 * 
	 * @Description: 容器管理界面，箱子需求量维护
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月2日 下午5:28:02
	 */
	@RequestMapping("updateBoxNeed")
	public void updateBoxNeed(HttpServletRequest request,HttpServletResponse response, PkgProposalModel model) throws Exception{
		String resultMsg = null;
		try {
			pkgProposalManager.updateBoxNeed(model);
			resultMsg = "操作成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 
	 * @Description: 箱子纳入量更新
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 下午3:53:46
	 */
	@RequestMapping("updateBoxProvide")
	public void updateBoxProvide(HttpServletRequest request,HttpServletResponse response, PkgProposalModel model) throws Exception{
		String resultMsg = null;
		try {
			pkgProposalManager.updateBoxProvide(model);
			resultMsg = "操作成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 
	 * @Description: 托盘需求量管理
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 下午4:35:09
	 */
	@RequestMapping("updateTrayNeed")
	public void updateTrayNeed(HttpServletRequest request,HttpServletResponse response, PkgProposalModel model) throws Exception{
		String resultMsg = null;
		try {
			pkgProposalManager.updateTrayNeed(model);
			resultMsg = "操作成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 
	 * @Description: 托盘纳入量管理
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月3日 下午4:35:57
	 */
	@RequestMapping("updateTrayProvide")
	public void updateTrayProvide(HttpServletRequest request,HttpServletResponse response, PkgProposalModel model) throws Exception{
		String resultMsg = null;
		try {
			pkgProposalManager.updateTrayProvide(model);
			resultMsg = "操作成功";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMsg="操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 
	 * @Description: 包装信息查询界面，车型弹窗
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PkgProposalModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月4日 下午2:50:57
	 */
	@RequestMapping("getCarType")
    public @ResponseBody List<PkgProposalModel> getCarType(HttpServletRequest request,
            HttpServletResponse reponse  ) throws Exception{
		PkgProposalModel model = new PkgProposalModel();
		
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        List<PkgProposalModel> list = (List<PkgProposalModel>) pkgProposalManager.getCarType(model);
//        String []carTypeArr = new String[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//			carTypeArr[i] = list.get(i).getValue();
//		}
        return list;
    }
	
	/**
	 * 
	 * @Description: 获取提案状态下拉框（不包含待发起）
	 * @param @return   
	 * @return List<PkgProposalModel>  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月4日 下午7:24:41
	 */
	@RequestMapping("getProposalStatus")
	public @ResponseBody List<PkgProposalModel> getProposalStatus(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			IUser user = ContextUtil.getCurrentUser();
			map.put("factoryCode", user.getCurFactoryCode());
			return pkgProposalManager.getProposalStatus(map);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	/**
	 * 
	 * @Description: 数据导入临时表
	 * @param @param request
	 * @param @param response
	 * @param @param file
	 * @param @throws IOException   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月7日 下午7:21:10
	 */
	@RequestMapping("importPkgBoxGroupModel")
	public void importPkgBoxGroup(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file) throws IOException {
		PkgProposalModel model = new PkgProposalModel();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String) session.getAttribute(SessionKey.JISI_PART_GROUP_IMPORT_UUID);
			if (StringUtil.isNotEmpty(uuid)) {
				pkgProposalManager.deletePkgBoxImportTempDataByUUID(uuid);
			} else {
				uuid = RequestUtil.getString(request, "uuid");
				if (StringUtil.isNotEmpty(uuid)) {
					pkgProposalManager.deletePkgBoxImportTempDataByUUID(uuid);
				} else {
					uuid = UUID.randomUUID().toString().replaceAll("-", "");
				}
			}

			session.setAttribute(SessionKey.JISI_PART_GROUP_IMPORT_UUID, uuid);

			Map<String, Object> rtn = pkgProposalManager.importPkgBoxModel(file, uuid, RequestUtil.getIpAddr(request), user);
			rtn.put("uuid", uuid);
			if ((Boolean) rtn.get("result")) {
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			} else {
				writeResultMessage(response.getWriter(), "失败", (String) rtn.get("console"), JSONObject.fromObject(rtn),
						ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("result", false);
			rtn.put("console", "导入失败");
			writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 
	 * @Description: 查询导入临时表的数据
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月8日 上午10:07:24
	 */
    @RequestMapping("curdlistImportTempJson")
    public @ResponseBody PageJson curdlistImportTempJson(HttpServletRequest request,HttpServletResponse reponse, PkgProposalModel model) throws Exception{
        Map<String, String> paramMap = new HashMap<String, String>();
        HttpSession session = request.getSession();
		String uuid = (String) session.getAttribute(SessionKey.MM_SW_SUPPLIER_GROUP_IMPORT_UUID);
		if (StringUtil.isEmpty(uuid)) {
//			uuid = RequestUtil.getString(request, "uuid");
			uuid = model.getUuid();
		}
        paramMap.put("uuid", uuid);
        paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
//        QueryFilter queryFilter = getQueryFilter(request);
//        Page page = queryFilter.getPage();
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
        		getQueryFilter(request).getPage().getPageSize()));
        PageList<PkgProposalModel> pageList = (PageList<PkgProposalModel>) pkgProposalManager.queryImportTempData(paramMap, p);
        return new PageJson(pageList);
    }
    
    /**
     * 箱子维护数据导入正式表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("isImport")
    public void isImport(HttpServletRequest request,HttpServletResponse response) throws Exception{
        ResultMessage message = null;
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            HttpSession session = request.getSession();
			String uuid = (String) session.getAttribute(SessionKey.MM_SW_SUPPLIER_GROUP_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
            paramMap.put("uuid", uuid);
            paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
            paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
            paramMap.put("creationUser", ContextUtil.getCurrentUser().getAccount());
            paramMap.put("lastModifiedUser", ContextUtil.getCurrentUser().getAccount());
            pkgProposalManager.insertImportData(paramMap);
            message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new Exception("系统错误,请联系管理员");
//            message = new ResultMessage(ResultMessage.FAIL, "执行失败");
        }
        writeResultMessage(response.getWriter(), message);
    }
    
    /**以下是托盘批量导入***********************************************************/
    
    /**
	 * 
	 * @Description: 数据导入临时表
	 * @param @param request
	 * @param @param response
	 * @param @param file
	 * @param @throws IOException   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月7日 下午7:21:10
	 */
	@RequestMapping("importPkgTrayGroupModel")
	public void importPkgTrayGroup(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file) throws IOException {
		PkgProposalModel model = new PkgProposalModel();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String) session.getAttribute(SessionKey.JISI_PART_GROUP_IMPORT_UUID);
			if (StringUtil.isNotEmpty(uuid)) {
				pkgProposalManager.deletePkgTrayImportTempDataByUUID(uuid);
			} else {
				uuid = RequestUtil.getString(request, "uuid");
				if (StringUtil.isNotEmpty(uuid)) {
					pkgProposalManager.deletePkgTrayImportTempDataByUUID(uuid);
				} else {
					uuid = UUID.randomUUID().toString().replaceAll("-", "");
				}
			}

			session.setAttribute(SessionKey.JISI_PART_GROUP_IMPORT_UUID, uuid);

			Map<String, Object> rtn = pkgProposalManager.importPkgTrayModel(file, uuid, RequestUtil.getIpAddr(request), user);
			rtn.put("uuid", uuid);
			if ((Boolean) rtn.get("result")) {
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			} else {
				writeResultMessage(response.getWriter(), "失败", (String) rtn.get("console"), JSONObject.fromObject(rtn),
						ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("result", false);
			rtn.put("console", "导入失败");
			writeResultMessage(response.getWriter(), JSONObject.fromObject(rtn).toString(), ResultMessage.FAIL);
		}
	}
	
	/**
	 * 
	 * @Description: 查询导入临时表的托盘数据
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月8日 上午10:07:24
	 */
    @RequestMapping("curdlistTrayImportTempJson")
    public @ResponseBody PageJson curdlistTrayImportTempJson(HttpServletRequest request,HttpServletResponse reponse, PkgProposalModel model) throws Exception{
        Map<String, String> paramMap = new HashMap<String, String>();
        HttpSession session = request.getSession();
		String uuid = (String) session.getAttribute(SessionKey.MM_SW_SUPPLIER_GROUP_IMPORT_UUID);
		if (StringUtil.isEmpty(uuid)) {
//			uuid = RequestUtil.getString(request, "uuid");
			uuid = model.getUuid();
		}
        paramMap.put("uuid", uuid);
        paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
//        QueryFilter queryFilter = getQueryFilter(request);
//        Page page = queryFilter.getPage();
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
        		getQueryFilter(request).getPage().getPageSize()));
        PageList<PkgProposalModel> pageList = (PageList<PkgProposalModel>) pkgProposalManager.queryTrayImportTempData(paramMap, p);
        return new PageJson(pageList);
    }
    
    /**
     * 托盘维护数据导入正式表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("isImportTray")
    public void isImportTray(HttpServletRequest request,HttpServletResponse response) throws Exception{
        ResultMessage message = null;
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            HttpSession session = request.getSession();
			String uuid = (String) session.getAttribute(SessionKey.MM_SW_SUPPLIER_GROUP_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
            paramMap.put("uuid", uuid);
            paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
            paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
            paramMap.put("creationUser", ContextUtil.getCurrentUser().getAccount());
            paramMap.put("lastModifiedUser", ContextUtil.getCurrentUser().getAccount());
            pkgProposalManager.insertTrayImportData(paramMap);
            message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new Exception("系统错误,请联系管理员");
//            message = new ResultMessage(ResultMessage.FAIL, "执行失败");
        }
        writeResultMessage(response.getWriter(), message);
    }
    
    
    /****以下为包装变更相关业务逻辑****************************************************************************/
    
    /**
     * 
     * @Description: 
     * @param @param request
     * @param @param response
     * @param @param paramList
     * @param @throws Exception   
     * @return void  
     * @throws  
     * @author luoxq
     * @date 2019年1月29日 下午2:43:36
     */
	@RequestMapping("addProposalChange")
	public void addProposalChange(HttpServletRequest request,HttpServletResponse response,@RequestBody List<Map<String,String>> paramList) throws Exception{
		String resultMsg = null;
		
		IUser user = ContextUtil.getCurrentUser();
		
		try {
			resultMsg = "发起提案成功";
			
			for(Map<String,String> param : paramList){
				param.put("PROPOSAL_STATUS", PkgProposalModel.PROPOSAL_STATUS_START); //默认发起时提案状态为 发起
				param.put("CREATION_USER", user.getAccount());
				param.put("factoryCode", user.getCurFactoryCode());
				
				param.put("IS_SHOW_CHANGE", PkgProposalModel.IS_SHOW_CHANGE_YES);
			}
			
			pkgProposalManager.addProposalChange(paramList);
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
    
	@RequestMapping("curdChangelistJson")
    public @ResponseBody PageJson curdChangelistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") PkgProposalModel model) throws Exception{
		
        DefaultPage p = (DefaultPage) getQueryFilter(request).getPage();
        
        IUser user = ContextUtil.getCurrentUser();
		if(IUser.USER_TYPE_SUPP.equals(user.getUserType())){
			model.setSupplierNo(user.getSupplierNo());
		}
		model.setFactoryCode(user.getCurFactoryCode());
        PageList<PkgProposalModel> pageList = (PageList<PkgProposalModel>) pkgProposalManager.queryChangePkgProposalForPage(model, p);
        return new PageJson(pageList);
    }
    
    /**
     * 
     * @Description: 如果是留用状态零件则根据零件号查询出上一个零件的包装信息，根据失效时间为最后为准
     * @param @param request
     * @param @param response
     * @param @return
     * @param @throws Exception   
     * @return PkgProposalModel  
     * @throws  
     * @author luoxq
     * @date 2019年1月17日 下午8:51:18
     */
	@RequestMapping("getProposalByStayPart")
	public @ResponseBody PkgProposalModel getProposalByStayPart(HttpServletRequest request,
				HttpServletResponse response) throws Exception{
		try {
			IUser user = ContextUtil.getCurrentUser();
			Map<String, Object> map = new HashMap<String, Object>();
			String partNo = RequestUtil.getString(request, "partNo");
			if(StringUtil.isEmpty(partNo)){
				return new PkgProposalModel();
			}
			map.put("partNo", partNo);
			map.put("factoryCode", user.getCurFactoryCode());
			map.put("proposalStatus", PkgProposalModel.PROPOSAL_STATUS_MCHECK_YES);
			PkgProposalModel p = pkgProposalManager.getProposalByStayPart(map);
			if (null == p) {
				p = new PkgProposalModel();
			}
			return p;
		} catch (Exception e) {
			 e.printStackTrace();
	         log.error(e.toString());
	         throw new Exception("系统错误,请联系管理员");
		}
	}
	
	/**
	 * 
	 * @Description: 包装变更提交
	 * @param @param request
	 * @param @param response
	 * @param @param pkgModelList
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月25日 下午7:13:28
	 */
	@RequestMapping("supPkgProposalChangeSubmit")
	public void supPkgProposalChangeSubmit(HttpServletRequest request,HttpServletResponse response,
			@RequestBody List<PkgProposalModel> pkgModelList) throws Exception{
		
		String resultMsg = null;
		IUser user = ContextUtil.getCurrentUser();
		
		try {
			resultMsg = "提案提交成功";
			
			pkgProposalManager.supPkgProposalChangeSubmit(pkgModelList);
			
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	/**
	 * 
	 * @Description: 如果变更选择的是组合包装，则根据组合包装备注获取最新的一个包装信息
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception   
	 * @return PkgProposalModel  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月28日 下午2:39:35
	 */
	@RequestMapping("getProposalByStayPartGroup")
	public @ResponseBody PkgProposalModel getProposalByStayPartGroup(HttpServletRequest request,
				HttpServletResponse response ,@RequestBody List<PkgProposalModel> partRemark) throws Exception{
		try {
			IUser user = ContextUtil.getCurrentUser();
			Map<String, Object> map = new HashMap<String, Object>();
//			String partRemark = RequestUtil.getString(request, "partRemark");
//			if(StringUtil.isEmpty(partRemark)){
			if (partRemark.size() < 1) {
				return new PkgProposalModel();				
			}
			map.put("comPackRemark", partRemark);
			map.put("size", partRemark.size()-1);
			map.put("factoryCode", user.getCurFactoryCode());
			map.put("proposalStatus", PkgProposalModel.PROPOSAL_STATUS_MCHECK_YES);
			PkgProposalModel p = pkgProposalManager.getProposalByStayPart(map);
			if (null == p) {
				p = new PkgProposalModel();
			}
			return p;
		} catch (Exception e) {
			 e.printStackTrace();
	         log.error(e.toString());
	         throw new Exception("系统错误,请联系管理员");
		}
	}

}
