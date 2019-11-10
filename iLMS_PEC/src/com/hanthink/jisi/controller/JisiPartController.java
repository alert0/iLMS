package com.hanthink.jisi.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.jisi.manager.JisiPartManager;
import com.hanthink.jisi.model.JisiPartGroupModel;
import com.hanthink.jisi.model.JisiPartModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;
/**
 * 
 * ClassName: JisiPartController 
 * @Description: 厂内同步零件信息维护
 * @author yokoboy
 * @date 2018年11月9日
 */
@Controller
@RequestMapping("/jisi/part")
public class JisiPartController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(JisiPartController.class);

	@Resource
	private JisiPartManager manager;
	/**
	 * 
	 * @Description: 厂内同步零件信息维护
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午9:51:24
	 */
	@RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") JisiPartModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
        try {
        	PageList<JisiPartModel> pageList = (PageList<JisiPartModel>) manager.queryJisiPartForPage(model, p);
            return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
        
    }
	
	/**
	 * 
	 * @Description: 厂内同步零件信息维护新增/修改
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午9:52:49
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response, JisiPartModel model) throws Exception{
		String resultMsg = null;
		String id = model.getId();
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		try {
			if(StringUtil.isEmpty(id)){
				String partGroupId = model.getPartGroupId();
				String partNo = model.getPartNo();
				//判断零件号是否存在
				List<JisiPartModel> li = manager.queryPartNoIsExists(model);
				if(li.size() <= 0) {
					resultMsg="零件号不存在";
					writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
					return;
				}
				boolean b= manager.isExistByCode(partGroupId, partNo);
				if (b) {
					resultMsg="零件组代码和零件号已存在";
					writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
					return;
				}else {
					model.setCreationUser(user.getAccount()); //记录创建人
					model.setFactoryCode(user.getCurFactoryCode());
					manager.create(model);
					resultMsg="添加成功";
				}
			}else{				
				model.setLastModifiedUser(user.getAccount());//记录修改人
				manager.updateAndLog(model, RequestUtil.getIpAddr(request));
				resultMsg="更新成功";
			}
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
	 * 
	 * @Description: 厂内同步零件信息维护删除
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午9:56:50
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) 
			throws Exception{
			ResultMessage message=null;
			try {
				String ids = RequestUtil.getString(request,"ids");
				String []idArr = ids.split(",");
				manager.removeAndLogByIds(idArr, RequestUtil.getIpAddr(request));
				message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.toString());
				throw new Exception("系统错误,请联系管理员");
//				message = new ResultMessage(ResultMessage.FAIL, "删除失败");
			}
			writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	 * @Description: 获取零件组下拉框
	 * @param @param request
	 * @param @param response
	 * @param @return   
	 * @return List<JisiPartModel>  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 上午10:02:51
	 */
	@RequestMapping("getJisiPartGroupIdUnload")
	public @ResponseBody List<JisiPartModel> getJisiPartGroupIdUnload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultMsg = null;
		try {
			IUser user = ContextUtil.getCurrentUser();
			JisiPartModel model = new JisiPartModel();
			model.setFactoryCode(user.getCurFactoryCode());
			List<JisiPartModel> list = manager.getJisiPartGroupIdUnload(model);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			resultMsg="获取零件组下拉框失败";
		}
//		writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
//		return null;
	}
	
	/**
	 * 
	 * @Description: 厂内同步零件信息导出
	 * @param @param request
	 * @param @param response
	 * @param @param model   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 下午2:22:01
	 */
	@RequestMapping("downloadJisiPartModel")
	public void downloadJisiPartModel(HttpServletRequest request,HttpServletResponse response, JisiPartModel model) throws Exception{
		try {
		String exportFileName = "厂内同步零件信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
		List<JisiPartModel> list =  manager.queryJisiPartByKey(model);
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
		
		String[] headers = {"零件组代码","零件组名称", "零件号", "简号", "零件名称","记号","生效日期","失效日期", "生失效状态"};
		String[] columns = {"partGroupNo","partGroupName", "partNo", "partShortNo", "partNameCn","partMark","effStart","effEnd","effStatusDesc"};
		int[] widths = {80,100, 100, 80, 80,80,100,100,100};
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			ExcelUtil.exportException(e, request, response);
		}
	}
	
	/**
	 * 
	 * @Description: 导入临时表
	 * @param @param request
	 * @param @param response
	 * @param @param file
	 * @param @throws IOException   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月11日 下午2:09:47
	 */
	@RequestMapping("importJisiPartModel")
	public void importJisiPart(HttpServletRequest request,HttpServletResponse response,@RequestParam("file")MultipartFile file) throws IOException {
		String planCodeType = RequestUtil.getString(request, "planCodeType");
		JisiPartModel model = new JisiPartModel();
		model.setPlanCodeType(planCodeType);
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String planCode = manager.getPlanCode(model);
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.JISI_PART_IMPORT_UUID);
			
			if(StringUtil.isNotEmpty(uuid)){
				manager.deleteJisiPartImportTempDataByUUID(uuid);
			}else{
				uuid = RequestUtil.getString(request, "uuid");
				if (StringUtil.isNotEmpty(uuid)) {
					manager.deleteJisiPartImportTempDataByUUID(uuid);
				} else {
					uuid = UUID.randomUUID().toString().replaceAll("-", "");
				}
			}
			
			session.setAttribute(SessionKey.JISI_PART_IMPORT_UUID, uuid);
			
			Map<String,Object> rtn = manager.importJisiPartModel(file, uuid, RequestUtil.getIpAddr(request),user, planCode);
			rtn.put("uuid", uuid);
			if((Boolean)rtn.get("result")){
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(rtn), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(), "失败", (String) rtn.get("console"), JSONObject.fromObject(rtn), ResultMessage.FAIL);
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
	 * @Description: 分页查询导入临时表数据
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午4:39:08
	 */
	@RequestMapping("queryImportInformation")
	public @ResponseBody PageJson queryImportInformationForPage(HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		Map<String, String> paramMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		String uuid = (String) session.getAttribute(SessionKey.JISI_PART_GROUP_IMPORT_UUID);
		if (StringUtil.isEmpty(uuid)) {
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		paramMap.put("factoryCode",ContextUtil.getCurrentUser().getAccount());
		Page page = getQueryFilter(request).getPage();
		
		PageList<JisiPartModel> pageList = manager.queryImportInformationForPage(paramMap,page);
		return new PageJson(pageList);
	}
	
	/**
	 * 
	 * @Description: 导入正式表
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午4:53:14
	 */
	@RequestMapping("/isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResultMessage rMessage = null;
		try {
			Map<String, String> paramMap = new HashMap<String,String>();
			HttpSession session = request.getSession();
			String uuid = (String) session.getAttribute(SessionKey.JISI_PART_GROUP_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			//查询导入校验结果是否包含不通过
			int count = manager.queryIsExistsCheckResultFalse(uuid);
			if(count > 0) {
				writeResultMessage(response.getWriter(), "存在校验结果不通过数据", ResultMessage.FAIL);
				return;
			}
			paramMap.put("uuid", uuid) ;
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			manager.insertTempDataToPartTable(paramMap);
			rMessage = new ResultMessage(ResultMessage.SUCCESS,"执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			rMessage = new ResultMessage(ResultMessage.ERROR,e.getMessage());
		}
		writeResultMessage(response.getWriter(), rMessage);
	}
	
	/**
	 * 
	 * @Description: 导出Excel导入的所有数据（包含错误/正确的数据）
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 下午5:47:44
	 */
	@RequestMapping("exportNeedToModifiedData")
	public void exportNeedToModifiedData(HttpServletRequest request,HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession();
		String uuid = (String) session.getAttribute(SessionKey.PUP_PROPLAN_IMPORT_UUID);
		if (StringUtil.isEmpty(uuid)) {
			uuid = RequestUtil.getString(request, "uuid");
		}
		List<JisiPartModel> list = manager.queryImportInformationForExport(uuid);
		String[] headers = {"零件组代码","零件号", "记号", "生效日期","失效日期", "校验信息"};
		String[] columns = {"partGroupNo","partNo", "partMark", "effStart","effEnd", "checkInfo"};
		int[] widths = {80,100, 80, 100,100};
		
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "厂内同步零件信息维护导入需修改数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
	}
	
}
