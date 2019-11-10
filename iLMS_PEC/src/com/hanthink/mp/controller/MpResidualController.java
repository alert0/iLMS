package com.hanthink.mp.controller;


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

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.business.util.SessionKey;
import com.hanthink.mp.manager.MpResidualManager;
import com.hanthink.mp.model.MpResidualModel;
import com.hanthink.mp.model.MpResidualModelImport;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;


/**
 * 
 * <pre> 
 * 描述：剩余量主数据 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpResidual")
public class MpResidualController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(MpResidualController.class);
	
	@Resource
	MpResidualManager mpResidualManager;
	  		
	/**
     * 分页查询零件剩余量主数据
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse, MpResidualModel model) {
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<MpResidualModel> pageList = (PageList<MpResidualModel>) mpResidualManager.queryMpResidualForPage(model,
					p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 获取计算队列填充下拉框
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("getUnloadPort")
	public @ResponseBody List<DictVO> getUnloadPort(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = mpResidualManager.getUnloadPort();
            return models;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 保存剩余量主数据信息
	 * @param request
	 * @param response
	 * @param mpResidual
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,
			@RequestBody MpResidualModel mpResidualModel) throws Exception{
		String resultMsg = null;
		String id = mpResidualModel.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				/**
				 * 校验计算队列是否存在
				 */
				Integer qstNum = mpResidualManager.selectUnloadPort(mpResidualModel);
				if (qstNum == 0) {
					resultMsg = "该计算队列不存在";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return ;
				} 
				/**
				 * 判断是否主键冲突
				 */
				Integer count = mpResidualManager.selectPrimaryKey(mpResidualModel);
				if (count > 0) {
					resultMsg = "该数据已存在";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				} else {
					mpResidualModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
					mpResidualModel.setCreationUser(ContextUtil.getCurrentUser().getAccount());
					mpResidualModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
					mpResidualManager.create(mpResidualModel);
					resultMsg = "添加剩余量主数据成功";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
				}
			} else {
				mpResidualModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
				mpResidualManager.updateAndLog(mpResidualModel, RequestUtil.getIpAddr(request));
				resultMsg = "更新剩余量主数据成功";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			log.error(e.toString());
			if(StringUtil.isEmpty(id)) {
				resultMsg = "新增失败";
			}else {
				resultMsg = "修改失败";
			}
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}

	/**
	 * 批量删除剩余量主数据记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			mpResidualManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message=new ResultMessage(ResultMessage.SUCCESS, "删除剩余量主数据成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除剩余量主数据失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 下载导出MpResidual数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadMpResidualModel")
	public void downloadMpResidualModel(HttpServletRequest request,HttpServletResponse response,
			MpResidualModel model){
		try {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MpResidualModel> list =  mpResidualManager.queryMpResidualByKey(model);
		/**
		 * 如果查询记录超过10000条则报错
		 */
		if(0 == list.size()){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); //获取系统所允许的最大导出数量
		if(list.size() > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		
		String[] headers = {"零件号","简号","零件名称", "供应商代码", "出货地代码", "计算队列",
				"订购数量","净需求数量","实际剩余量","订购前余量",
				"计划变更差异量","安全库存","不良品库存","手工调整余量"};
		String[] columns = {"partNo", "partShortNo","partNameCn","supplierNo", "supFactory", "unloadPort",
				"orderNum","necessaryOrderNum","realResidualNum","preResidualNum",
				"adjDiffNum","safeNum","defectNum","manuResidual"};
		int[] widths = {80, 80, 80, 80,
				80,80, 80, 80,
				80,80, 80, 80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "零件剩余量主数据"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
	
	/**
	 * 导入Excel数据信息
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author linzhuo	
	 * @DATE	2018年9月1日 下午11:03:18
	 */
	@RequestMapping("importMpResidualModel")
	public void importMpResidualModel(@RequestParam("file") MultipartFile file,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String)session.getAttribute(SessionKey.MP_RESIDUALMODEL_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			if(StringUtil.isNotEmpty(uuid)){
				mpResidualManager.deleteMpResidualImportTempDataByUUID(uuid);
			}else{
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.MP_RESIDUALMODEL_IMPORT_UUID, uuid);
			
			Map<String,Object> resultMap = mpResidualManager.importMpResidualModel(file, uuid, RequestUtil.getIpAddr(request),user);
			/**
			 * 这里要传递uuid***
			 */
			resultMap.put("uuid", uuid);
			if((Boolean)resultMap.get("result")){
				writeResultMessage(response.getWriter(), "成功", "", 
				JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(),"失败", (String) resultMap.get("console"),
				JSONObject.fromObject(resultMap), ResultMessage.FAIL);
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
	 * 查询导入的临时数据信息
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 * @author linzhuo	
	 * @DATE	2018年9月1日 下午6:59:07
	 */
	@RequestMapping("curdlistImportTempJson")
	public @ResponseBody PageJson curdlistImportTempJson(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("uuid", RequestUtil.getString(request, "uuid"));
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<MpResidualModelImport> pageList = (PageList<MpResidualModelImport>) mpResidualManager.queryMpResidualImportTempData(paramMap, page);
		return new PageJson(pageList);
	}
	
	/**
	 * 确定导入，将临时表数据写入到正式业务表
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author linzhuo	
	 * @DATE	2018年9月2日 下午12:08:51
	 */
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = (String)session.getAttribute(SessionKey.MP_RESIDUALMODEL_IMPORT_UUID);
			/**
			 * 若uuid为空，则从前端请求中获取
			 */
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			mpResidualManager.insertMpResidualImportData(paramMap,RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
			
		} catch (Exception e) {
    		e.printStackTrace();
			log.error(e.toString());
			
			message = new ResultMessage(ResultMessage.FAIL, "没有正确数据可导入或已全部导入");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 导出临时数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:11:28
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response){
		try {
		Map<String, String> paramMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.MP_RESIDUALMODEL_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		List<MpResidualModelImport> list = mpResidualManager.queryMpResidualImportTempDataForExport(paramMap);
		
		String[] headers = {"零件号", "供应商代码", "出货地代码", "计算队列","手工调整余量",
				"校验结果","导入状态", "校验信息"};
		String[] columns = {"partNo", "supplierNo", "supFactory", "unloadPort","manuResidual", 
				"codeValueNameB","codeValueNameC", "checkInfo"};
		int[] widths = {80,80,80,80,80,
				50,50, 360};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "零件剩余量主数据"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		}
	
	
}
