package com.hanthink.mon.controller;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.business.util.SessionKey;
import com.hanthink.mon.manager.KbIpConfigManager;
import com.hanthink.mon.model.KbIpConfigModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/kb/kbIpConfig")
public class KbIpConfigController extends GenericController {
	private static Logger log = LoggerFactory.getLogger(KbIpConfigController.class);

	@Resource
	KbIpConfigManager kbIpConfigManager;

	/**
	 * 分页查询看板IP配置
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/curdlistJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse,
			KbIpConfigModel model) {
		try {
			Page page = getQueryFilter(request).getPage();
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<KbIpConfigModel> pageList = (PageList<KbIpConfigModel>) kbIpConfigManager.queryKbTypeForPage(model,page);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/queryKbTypeDetail")
	public @ResponseBody PageJson queryDetailForPage(HttpServletRequest request, HttpServletResponse reponse,
				KbIpConfigModel model)throws Exception {
		Page page = getQueryFilter(request).getPage();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		PageList<KbIpConfigModel> pageList = kbIpConfigManager.queryKbIpConfigForPage(model,page);
		return new PageJson(pageList);
	}
	
	@RequestMapping("/queryKbList")
	public @ResponseBody List<KbIpConfigModel> queryKbList()throws Exception{
		return kbIpConfigManager.queryKbList();
	}

	/**
	 * 保存看板IP配置信息 @param request @param response @param KbIpConfig @throws Exception
	 * void @exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			@RequestBody KbIpConfigModel kbIpConfigModel) throws Exception {
		String resultMsg = null;
		String id = kbIpConfigModel.getId();
		kbIpConfigModel.setCreationUser(ContextUtil.getCurrentUser().getAccount());
		kbIpConfigModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
		kbIpConfigModel.setLastModifiedIp(RequestUtil.getIpAddr(request));
		try {
			if (StringUtil.isEmpty(id)) {
				kbIpConfigModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
				/**
				 * 判断是否主键冲突
				 */
				Integer count = kbIpConfigManager.selectPrimaryKey(kbIpConfigModel);
				Integer countDetail = kbIpConfigManager.selectPrimaryKeyDetail(kbIpConfigModel);
				if (countDetail > 0) {
					resultMsg = "该数据已存在";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				} else {
					if (count > 0) {
						kbIpConfigModel.setCreationUser(ContextUtil.getCurrentUser().getAccount());
						kbIpConfigModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
						kbIpConfigManager.createDetail(kbIpConfigModel);
						resultMsg = "修改看板IP配置成功";
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
					} else {
						kbIpConfigModel.setCreationUser(ContextUtil.getCurrentUser().getAccount());
						kbIpConfigModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
						Integer nowId = kbIpConfigManager.getCurrBussId();
						kbIpConfigModel.setId(nowId.toString());
						kbIpConfigModel.setBussId(nowId.toString());
						kbIpConfigManager.create(kbIpConfigModel);
						kbIpConfigManager.createDetail(kbIpConfigModel);
						resultMsg = "添加看板IP配置成功";
						writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
					}
				}
			} else {
				kbIpConfigModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
				kbIpConfigModel.setLastModifiedUser(ContextUtil.getCurrentUser().getAccount());
				kbIpConfigModel.setLastModifiedIp(RequestUtil.getIpAddr(request));
				kbIpConfigManager.updateAndLog(kbIpConfigModel, RequestUtil.getIpAddr(request));
				resultMsg = "更新看板IP配置成功";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			log.error(e.toString());
			writeResultMessage(response.getWriter(), e.getMessage(), e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 批量删除看板IP配置记录 @param request @param response @throws Exception void @exception
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			kbIpConfigManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "删除看板IP配置成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除看板IP配置失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 下载导出KbIpConfig数据信息
	 * 
	 * @param request
	 * @param response
	 * @author linzhuo
	 * @DATE 2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadKbIpConfigModel")
	public void downloadKbIpConfigModel(HttpServletRequest request, HttpServletResponse response,
			KbIpConfigModel model) {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<KbIpConfigModel> list = kbIpConfigManager.queryKbIpConfigByKey(model);
			/**
			 * 如果查询记录超过10000条则报错
			 */
			if (0 == list.size()) {
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); // 获取系统所允许的最大导出数量
			if (list.size() > sysMaxNum) {
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}

			String[] headers = { "看板类型", "看板IP", "界面URL", "大屏电视机网口", "传入参数", "是否可编辑" };
			String[] columns = { "codeValueNameD", "kbIp", "pageUrl", "mbpsComb", "reqParameter", "codeValueNameC" };
			int[] widths = { 80, 80, 80, 80, 80, 80 };
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "看板IP配置" + df.format(new Date()), list,
					headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}

	}

	/**
	 * 导入Excel数据信息
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author linzhuo
	 * @DATE 2018年9月1日 下午11:03:18
	 */
	@RequestMapping("importKbIpConfigModel")
	public void importKbIpConfigModel(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String) session.getAttribute(SessionKey.MM_KB_IP_CONFIG_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			if (StringUtil.isNotEmpty(uuid)) {
				kbIpConfigManager.deleteKbIpConfigImportTempDataByUUID(uuid);
			} else {
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
			}
			session.setAttribute(SessionKey.MM_KB_IP_CONFIG_IMPORT_UUID, uuid);

			Map<String, Object> resultMap = kbIpConfigManager.importKbIpConfigModel(file, uuid,
					RequestUtil.getIpAddr(request), user);
			/**
			 * 这里要传递uuid***
			 */
			resultMap.put("uuid", uuid);
			if ((Boolean) resultMap.get("result")) {
				writeResultMessage(response.getWriter(), "成功", "", JSONObject.fromObject(resultMap),
						ResultMessage.SUCCESS);
			} else {
				writeResultMessage(response.getWriter(), "失败", (String) resultMap.get("console"),
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
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 * @author linzhuo
	 * @DATE 2018年9月1日 下午6:59:07
	 */
	@RequestMapping("curdlistImportTempJson")
	public @ResponseBody PageJson curdlistImportTempJson(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("uuid", RequestUtil.getString(request, "uuid"));
		QueryFilter queryFilter = getQueryFilter(request);
		Page page = queryFilter.getPage();
		PageList<KbIpConfigModel> pageList = (PageList<KbIpConfigModel>) kbIpConfigManager
				.queryKbIpConfigImportTempData(paramMap, page);
		return new PageJson(pageList);
	}

	/**
	 * 确定导入，将临时表数据写入到正式业务表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author linzhuo
	 * @DATE 2018年9月2日 下午12:08:51
	 */
	@RequestMapping("isImport")
	public void isImport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			HttpSession session = request.getSession();
			String uuid = (String) session.getAttribute(SessionKey.MM_KB_IP_CONFIG_IMPORT_UUID);
			/**
			 * 若uuid为空，则从前端请求中获取
			 */
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			kbIpConfigManager.insertKbIpConfigImportData(paramMap, RequestUtil.getIpAddr(request));
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
	 * 
	 * @param request
	 * @param response
	 * @author linzhuo
	 * @DATE 2018年9月10日 上午11:11:28
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			HttpSession session = request.getSession();
			String uuid = (String) session.getAttribute(SessionKey.MM_KB_IP_CONFIG_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			paramMap.put("uuid", uuid);
			List<KbIpConfigModel> list = kbIpConfigManager.queryKbIpConfigImportTempDataForExport(paramMap);

			String[] headers = { "看板类型", "看板IP", "界面URL", "大屏电视机网口", "传入参数", "是否可编辑", "校验结果", "导入状态", "校验信息" };
			String[] columns = { "codeValueNameD", "kbIp", "pageUrl", "mbpsComb", "reqParameter", "codeValueNameC",
					"codeValueNameE", "codeValueNameF", "checkInfo" };
			int[] widths = { 80, 80, 80, 80, 80, 80, 50, 50, 360 };
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "看板IP配置" + df.format(new Date()), list,
					headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
	}
	
	
	@RequestMapping("/saveKbType")
	public void saveKbType(HttpServletRequest request, HttpServletResponse response,
			@RequestBody KbIpConfigModel kbIpConfigModel)throws Exception {
		String ipAddr = RequestUtil.getIpAddr(request);
		try {
			kbIpConfigManager.saveKbType(kbIpConfigModel, ipAddr);
			writeResultMessage(response.getWriter(), "保存看板信息成功", ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
		}
	}
}
