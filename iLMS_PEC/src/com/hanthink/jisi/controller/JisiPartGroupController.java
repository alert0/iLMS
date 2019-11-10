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
import com.hanthink.jisi.manager.JisiPartGroupManager;
import com.hanthink.jisi.model.JisiPartGroupModel;
import com.hanthink.pup.util.PupUtil;
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
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;

/**
 * 
 * ClassName: JisiPartGroupController
 * 
 * @Description: 厂内同步零件组维护
 * @author luoxq
 * @date 2018年11月5日
 */
@Controller
@RequestMapping("/jisi/partGroup")
public class JisiPartGroupController extends GenericController {

	private static Logger log = LoggerFactory.getLogger(JisiPartGroupController.class);

	@Resource
	private JisiPartGroupManager manager;

	String PLAN_CODE = null;

	/**
	 * 
	 * @Description: 通过工厂和信息点类型获取信息点
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年11月11日 下午3:50:26
	 */
	@RequestMapping("getPlanCode")
	public void getPlanCode(HttpServletRequest request, HttpServletResponse response, JisiPartGroupModel model) {
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		/** 通过工厂和信息点类型获取信息点 **/
		Constant.PLAN_CODE = manager.getPlanCode(model);
	}

	/**
	 * 
	 * @Description: 厂内同步零件组维护分页查询
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception
	 * @return PageJson
	 * @throws @author luoxq
	 * @date 2018年11月8日 下午2:56:10
	 */
	@RequestMapping("curdlistJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") JisiPartGroupModel model) throws Exception {
		DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		try {
			PageList<JisiPartGroupModel> pageList = (PageList<JisiPartGroupModel>) manager
					.queryJisiPartGroupForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}

	}

	/**
	 * 
	 * @Description: 厂内同步零件组维护批量删除
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年11月8日 下午3:19:10
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String ids = RequestUtil.getString(request, "ids");
			String[] idArr = ids.split(",");
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
	 * @Description: 新增/修改厂内同步零件组
	 * @param @param request
	 * @param @param response
	 * @param @param dpmItemModel
	 * @param @throws Exception
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年11月8日 下午4:09:38
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, JisiPartGroupModel model)
			throws Exception {
		String resultMsg = null;
		String id = model.getId();
		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());

		String planCode = manager.getPlanCode(model);
		model.setPlanCode(planCode);
		try {
			if (StringUtil.isEmpty(id)) {
				// 新增之前判断零件组代码是否已存在
				String partGroup = model.getPartGroupNo();
				boolean b = manager.isExistByCode(partGroup);
				if (b) {
					resultMsg = "零件组代码已存在";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				}

				model.setCreationUser(user.getAccount()); // 记录创建人
				model.setFactoryCode(user.getCurFactoryCode());
				manager.create(model);
				resultMsg = "添加成功";
			} else {
				model.setLastModifiedUser(user.getAccount());// 记录修改人
				manager.updateAndLog(model, RequestUtil.getIpAddr(request));
				resultMsg = "更新成功";
			}
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			resultMsg="操作失败";
//			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}

	/**
	 * 
	 * @Description: 导出
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @return void
	 * @throws Exception
	 * @throws           @author luoxq
	 * @date 2018年11月8日 下午4:49:15
	 */
	@RequestMapping("downloadJisiModel")
	public void downloadJisiModel(HttpServletRequest request, HttpServletResponse response, JisiPartGroupModel model)
			throws Exception {
		try {
			String exportFileName = "厂内同步零件组信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			IUser user = ContextUtil.getCurrentUser();
			model.setFactoryCode(user.getCurFactoryCode());
			List<JisiPartGroupModel> list = manager.queryJisiPartGroupByKey(model);
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

			String[] headers = { "零件组代码", "零件组名称", "打印机", "打印机位置", "是否自动打印", "生效日期", "失效日期"};
			String[] columns = { "partGroupNo", "partGroupName", "printerIdDesc", "printLocation", "isAutoPrintName",
					"effStart", "effEnd"};
			int[] widths = { 80, 100, 80, 80, 80, 80, 80 };
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths,
					columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			ExcelUtil.exportException(e, request, response);
		}
	}

	/**
	 * 
	 * @Description: 零件组信息导入控制器
	 * @param @param request
	 * @param @param response
	 * @param @param file
	 * @param @throws IOException
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年11月10日 下午2:01:02
	 */
	@RequestMapping("importJisiPartGroupModel")
	public void importJisiPartGroup(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file) throws IOException {
		JisiPartGroupModel model = new JisiPartGroupModel();
		String planCodeType = RequestUtil.getString(request, "planCodeType");
		model.setPlanCodeType(planCodeType);
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		String planCode = manager.getPlanCode(model);
		try {
			IUser user = ContextUtil.getCurrentUser();
			String uuid = null;
			HttpSession session = request.getSession();
			uuid = (String) session.getAttribute(SessionKey.JISI_PART_GROUP_IMPORT_UUID);

			if (StringUtil.isNotEmpty(uuid)) {
				manager.deleteJisiPartGroupImportTempDataByUUID(uuid);
			} else {
				uuid = RequestUtil.getString(request, "uuid");
				if (StringUtil.isNotEmpty(uuid)) {
					manager.deleteJisiPartGroupImportTempDataByUUID(uuid);
				} else {
					uuid = UUID.randomUUID().toString().replaceAll("-", "");
				}
			}
			session.setAttribute(SessionKey.JISI_PART_GROUP_IMPORT_UUID, uuid);
			Map<String, Object> rtn = manager.importJisiPartGroupModel(file, uuid, RequestUtil.getIpAddr(request), user,
					planCode);
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
	 * @Description: 分页查询导入临时表数据
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception
	 * @return PageJson
	 * @throws @author luoxq
	 * @date 2018年11月10日 下午4:39:08
	 */
	@RequestMapping("queryImportInformation")
	public @ResponseBody PageJson queryImportInformationForPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> paramMap = new HashMap<>();

		HttpSession session = request.getSession();
		String uuid = (String) session.getAttribute(SessionKey.JISI_PART_GROUP_IMPORT_UUID);
		if (StringUtil.isEmpty(uuid)) {
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		paramMap.put("factoryCode", ContextUtil.getCurrentUser().getAccount());
		Page page = getQueryFilter(request).getPage();

		PageList<JisiPartGroupModel> pageList = manager.queryImportInformationForPage(paramMap, page);
		return new PageJson(pageList);
	}

	/**
	 * 
	 * @Description: 导入正式表
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年11月10日 下午4:53:14
	 */
	@RequestMapping("/isImport")
	public void isImport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage rMessage = null;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
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
			paramMap.put("creationUser", ContextUtil.getCurrentUser().getAccount());
			paramMap.put("uuid", uuid);
			paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
			/**
			 * 临时表数据写入正式表
			 */
			manager.insertTempDataToPartGroupTable(paramMap);
			rMessage = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			rMessage = new ResultMessage(ResultMessage.ERROR,e.getMessage());
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
	 * @throws @author luoxq
	 * @date 2018年11月10日 下午5:47:44
	 */
	@RequestMapping("exportNeedToModifiedData")
	public void exportNeedToModifiedData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {

			String uuid = (String) session.getAttribute(SessionKey.PUP_PROPLAN_IMPORT_UUID);
			if (StringUtil.isEmpty(uuid)) {
				uuid = RequestUtil.getString(request, "uuid");
			}
			List<JisiPartGroupModel> list = manager.queryImportInformationForExport(uuid);
			String[] headers = { "零件组代码", "零件组名称", "打印机", "打印机位置", "是否自动打印", "生效日期", "失效日期",
					"导入状态", "校验结果", "校验信息"};
			String[] columns = { "partGroupNo", "partGroupName", "printerName", "printLocation", "isAutoPrintName",
					"effStart", "effEnd", "importStatusDesc", "checkResultDesc", "checkInfo" };
			int[] widths = { 80, 100, 80, 80, 80, 80, 80, 80, 80 , 100 };

			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response,
					"厂内同步零件组维护导入需修改数据" + PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
}
