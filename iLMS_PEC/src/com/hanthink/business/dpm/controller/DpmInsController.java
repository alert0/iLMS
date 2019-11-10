package com.hanthink.business.dpm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.apache.xmlbeans.impl.jam.mutable.MPackage;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.base.model.DictVO;
import com.hanthink.business.dpm.manager.DpmInsManager;
import com.hanthink.business.dpm.model.DpmInsModel;
import com.hanthink.business.util.SessionKey;
import com.hanthink.dpm.model.DpmDepartmentModel;
import com.hanthink.dpm.model.DpmItemModel;
import com.hanthink.sw.model.SwDeliveryModel;
import com.hanthink.util.constant.Constant;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.org.persistence.model.User;
import com.hotent.sys.api.identity.IdentityService;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * 
 * <pre>
 * Description:不良品申请
 * Company: HanThink
 * Date: 2018年9月17日 下午6:36:14
 * </pre>
 * 
 * @author luoxq
 */
@Controller
@RequestMapping("/dpm/ins")
public class DpmInsController extends GenericController {

	@Resource
	private DpmInsManager dpmInsManager;

	/** 获取流水号接口 **/
	@Resource
	private IdentityService service;
	
	private static String DPM_FACTORY = "DPM_FACTORY";

	private static Logger log = LoggerFactory.getLogger(DpmInsController.class);


	/**
	 * 
	 * @Title: 分页查询不良品申请列表数据 @Description: @param @param request @param @param
	 * reponse @param @param model @param @return @param @throws Exception @return
	 * PageJson @time 2018年9月18日 上午11:17:21 @throws
	 */
	@RequestMapping("curdlistJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") DpmInsModel model) throws Exception {
		DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
				getQueryFilter(request).getPage().getPageSize()));

		IUser user = ContextUtil.getCurrentUser();
		model.setFactoryCode(user.getCurFactoryCode());
		model.setCreationUser(user.getAccount());

		boolean b = dpmInsManager.isChecker(model); // 判断是否是审核人
		if (b) {
			model.setIsChecker(DpmInsModel.IS_CHECKER);
		}

		model.setInsStatusUnsbmit(DpmInsModel.INS_STATUS_UNSUBMIT);
		PageList<DpmInsModel> pageList;
		pageList = (PageList<DpmInsModel>) dpmInsManager.queryDpmInsForPage(model, p);
		return new PageJson(pageList);
	}

	/**
	 * 
	 * @Title: 修改和新增申请的不良品信息 @Description: @param @param request @param @param
	 * response @param @param dpmInsModel @param @throws Exception @return
	 * void @time 2018年9月18日 下午2:20:56 @throws
	 * 变更记录：iLMS2019060401  取消车型校验（改为下拉框选择） 修改时间：2019-06-04
	 * modelby:罗贤琴  
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, DpmInsModel dpmInsModel)
			throws Exception {
		String resultMsg = null;
		String applyNo = dpmInsModel.getApplyNo();
		IUser user = ContextUtil.getCurrentUser();
		dpmInsModel.setDpmType("");
		String dpmType = RequestUtil.getString(request, "dpmType");
		dpmInsModel.setDpmType(dpmType);
		dpmInsModel.setFactoryCode(user.getCurFactoryCode());

		// 点击保存校验车型是否存在
		//修改时间：2019-06-04
//		Boolean b = dpmInsManager.validateCarType(dpmInsModel);
//		if (!b) {
//			resultMsg = "车型不存在";
//			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
//			return;
//		}
		try {
			if (StringUtil.isEmpty(applyNo)) {
				String dpmFactory = dpmInsManager.getDpmFactory(user.getCurFactoryCode());
				//如果是总成件，则查询出下面的子件
//				if (isZCpart(request, response, dpmInsModel)) {
//					List<String> partNoList = dpmInsManager.getPartListByPartNo(dpmInsModel.getPartNo());
//					for (String partNo : partNoList) {
//						
//					dpmInsModel.setPartNo("");
//					dpmInsModel.setPartNo(partNo);
//					//根据当前登录用户的工厂获取工厂编码
//					
//					// 获取流水号
//					applyNo = service.genNextNo("serialNumber");
//					String newApplyNo = dpmFactory + applyNo;
//					// 生成不良品申请单号（年月日+五位流水号）
//					dpmInsModel.setApplyNo(newApplyNo);
//
//					// 记录创建人
//					dpmInsModel.setCreationUser(user.getAccount());
//
//					dpmInsManager.create(dpmInsModel);
//					}
//				} else {
					// 获取流水号
					applyNo = service.genNextNo("serialNumber");
					
					String newApplyNo = dpmFactory + applyNo;
					// 生成不良品申请单号（年月日+五位流水号）
					dpmInsModel.setApplyNo(newApplyNo);

					// 记录创建人
					dpmInsModel.setCreationUser(user.getAccount());

					dpmInsManager.create(dpmInsModel);
//				}
				
				
				resultMsg = "添加成功";

			} else {
				DpmInsModel d = dpmInsManager.get(dpmInsModel.getApplyNo());
				// 已提交信息不能进行修改
				if (!Constant.UNSUBIMT.equals(d.getInsStatus())) {
					resultMsg = "只能修改未提交信息，请确认";
					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
					return;
				}
				dpmInsModel.setLastModifiedUser(user.getAccount());// 记录修改人
				dpmInsManager.updateAndLog(dpmInsModel, RequestUtil.getIpAddr(request));
				resultMsg = "修改成功";
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
	 * @Title: 通过applyNo获取明细 @Description: @param @param request @param @param
	 * response @param @return @param @throws Exception @return DpmAreaModel @time
	 * 2018年9月18日 上午11:16:30 @throws
	 */
	@RequestMapping("curdgetJson")
	public @ResponseBody DpmInsModel curdgetJson(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String applyNo = RequestUtil.getString(request, "applyNo");
		if (StringUtil.isEmpty(applyNo)) {
			return new DpmInsModel();
		}
		return dpmInsManager.get(applyNo);
	}

	/**
	 * 
	 * @Title: 删除 @Description: @param @param request @param @param
	 * response @param @throws Exception @return void @time 2018年9月20日
	 * 上午10:02:29 @throws
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "applyNo");

			for (String applyId : aryIds) {
				DpmInsModel d = dpmInsManager.get(applyId);
				// 已提交信息不能进行删除
				if (!Constant.UNSUBIMT.equals(d.getInsStatus())) {
					message = new ResultMessage(ResultMessage.FAIL, "只能删除未提交信息，请确认");
					writeResultMessage(response.getWriter(), message);
					return;
				}
			}
			dpmInsManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message = new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			message = new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 
	 * @Title: 新增界面输入零件号或填写简号带出相关信息 
	 * @Description: 
	 * @param @param request @param @param response 
	 * @param @return 
	 * @param @throws Exception 
	 * @return DpmInsModel 
	 * @time 2018年9月19日 上午10:15:10 
	 * @throws
	 */
	@RequestMapping("getMsgByPartNo")
	public @ResponseBody DpmInsModel getMsgByPartNo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String partNo = RequestUtil.getString(request, "partNo");
		String partShortNo = RequestUtil.getString(request, "partShortNo");
		if (StringUtil.isEmpty(partNo) && StringUtil.isEmpty(partShortNo)) { //如果零件号和简号都没有填写，则返回空
			return null;
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("partNo", partNo);
			map.put("partShortNo", partShortNo);
			DpmInsModel dpmInsModel = dpmInsManager.getMsgByPartNo(map);
			if (null == dpmInsModel) {
				return new DpmInsModel();
			}
			return dpmInsModel;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}

	}

	/**
	 * 
	 * @Title: 获取处理结果下拉框 @Description: @param @param request @param @param
	 * reponse @param @return @param @throws Exception @return Object @time
	 * 2018年9月19日 下午3:14:45 @throws
	 */
	@RequestMapping("getUnloadDealResult")
	public @ResponseBody Object getUnloadDealResult(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		try {
			List<DictVO> models = dpmInsManager.getUnloadDealResult();
			return new PageJson(models);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}

	/**
	 * 
	 * @Title: 获取不良品项目代码下拉框 @Description: @param @param request @param @param
	 * reponse @param @return @param @throws Exception @return Object @time
	 * 2018年9月19日 下午3:14:59 @throws
	 */
	@RequestMapping("getUnloadDpmCode")
	public @ResponseBody List<DpmItemModel> getUnloadDpmCode(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		try {
			List<DpmItemModel> models = dpmInsManager.getUnloadDpmCode();
			return models;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}

	/**
	 * 
	 * @Title: 获取新增界面责任组下拉框 @Description: @param @param request @param @param
	 * reponse @param @return @param @throws Exception @return Object @time
	 * 2018年9月19日 下午3:36:25 @throws
	 */
	@RequestMapping("getUnloadRespDep")
	public @ResponseBody List<DpmInsModel> getUnloadRespDep(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			IUser user = ContextUtil.getCurrentUser();
			map.put("account", user.getAccount());
			map.put("factoryCode", user.getCurFactoryCode());
			List<DpmInsModel> models = dpmInsManager.getUnloadRespDep(map);
			return models;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}

	/**
	 * 
	 * @Description: 责任反馈界面下拉框
	 * @param @param request
	 * @param @param reponse
	 * @param @return
	 * @param @throws Exception
	 * @return List<DpmInsModel>
	 * @throws @author luoxq
	 * @date 2018年10月31日 下午7:11:56
	 */
	@RequestMapping("getUnloadRespDepAll")
	public @ResponseBody List<DpmInsModel> getUnloadRespDepAll(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		try {
			IUser user = ContextUtil.getCurrentUser();
			List<DpmInsModel> models = dpmInsManager.getUnloadRespDepAll(user.getAccount());
			return models;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}

	/**
	 * 
	 * @Description: 不良品新增界面，查询零件号弹窗
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception
	 * @return List<DpmInsModel>
	 * @throws @author luoxq
	 * @date 2018年10月30日 上午11:20:38
	 */
	@RequestMapping("getPartNo")
	public @ResponseBody PageJson getPartNo(HttpServletRequest request, HttpServletResponse reponse,
			@ModelAttribute("model") DpmInsModel model) throws Exception {
		IUser user = ContextUtil.getCurrentUser();
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(user.getCurFactoryCode());
			PageList<DpmInsModel> list = (PageList<DpmInsModel>) dpmInsManager.getPartNo(model,p);
			return new PageJson(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}

	}

	/**
	 * 
	 * @Title: 打开新增界面时，根据用户ID显示默认信息 @Description: @param @param
	 * request @param @param reponse @param @return @param @throws Exception @return
	 * Object @time 2018年9月19日 下午3:28:23 @throws
	 */
	@RequestMapping("getDefaultMsg")
	public @ResponseBody DpmInsModel getDefaultMsg(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		DpmInsModel dpmInsModel;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			IUser user = ContextUtil.getCurrentUser();
			map.put("account", user.getAccount());
			map.put("factoryCode", user.getCurFactoryCode());
			dpmInsModel = dpmInsManager.getDefaultMsg(map);
			String insDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			if (dpmInsModel != null) {
				dpmInsModel.setInsDate(insDate);
			} else {
				dpmInsModel = new DpmInsModel();
				dpmInsModel.setInsDate(insDate);
			}
			return dpmInsModel;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}

	/**
	 * 
	 * @Title: 修改不良品申请信息状态 @Description: @param @param request @param @param
	 * response @param @param dpmInsModel @param @throws Exception @return
	 * void @time 2018年9月20日 下午3:58:29 @throws
	 */
	@RequestMapping("submit")
	public void updateInsStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String submit = RequestUtil.getString(request, "submit");
		ResultMessage message = null;
		IUser user = ContextUtil.getCurrentUser();
		try {
			String applyNoStrs = RequestUtil.getString(request, "applyNoArr");
			String[] applyNos = applyNoStrs.split(",");

			message = dpmInsManager.updateInsStatus(applyNos, submit, user.getAccount());

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			message = new ResultMessage(ResultMessage.FAIL, "操作失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 
	 * @Description: 不良品审核
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月14日 下午12:23:06
	 */
	@RequestMapping("submitCheck")
	public void submitCheck(HttpServletRequest request, HttpServletResponse response, DpmInsModel model) throws Exception {
		String resultMsg = null;
		IUser user = ContextUtil.getCurrentUser();
		try {
			String applyNoStrs = model.getApplyNo();
			String[] applyNos = applyNoStrs.split(",");
			model.setFactoryCode(user.getCurFactoryCode());
			model.setCheckUser(user.getAccount());;
//			model.setInsStatus(DpmInsModel.CHECK_STATUS_PASS);

			dpmInsManager.submitCheck(applyNos, model);
			resultMsg = "审核成功";
			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			message = new ResultMessage(ResultMessage.FAIL, "操作失败");
		}
	}

	/**
	 * 
	 * @Description: 不良品界面，责任反馈功能
	 * @param @param request
	 * @param @param response
	 * @param @param model
	 * @param @throws Exception
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年10月31日 下午5:57:04
	 */
	@RequestMapping("repFeedback")
	public void repFeedback(HttpServletRequest request, HttpServletResponse response, DpmInsModel model)
			throws Exception {
		ResultMessage message = null;
		IUser user = ContextUtil.getCurrentUser();
		try {
			model.setAccount(user.getAccount());
			model.setFactoryCode(user.getCurFactoryCode());
			dpmInsManager.repFeedback(model);
			dpmInsManager.insertFeedback(model);
			message = new ResultMessage(ResultMessage.SUCCESS, "反馈成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			message = new ResultMessage(ResultMessage.FAIL, "操作失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 
	 * @Description: 责任反馈回写到多行文本框
	 * @param @param request
	 * @param @param reponse
	 * @param @return
	 * @param @throws Exception
	 * @return DpmInsModel
	 * @throws @author luoxq
	 * @date 2018年10月31日 下午6:45:12
	 */
	@RequestMapping("feedbaceWriteback")
	public @ResponseBody List<DpmInsModel> feedbaceWriteback(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		String applyNo = RequestUtil.getString(request, "applyNo");
		try {
			List<DpmInsModel> models = dpmInsManager.feedbaceWriteback(applyNo);
			if (null != models && models.size() > 0) {
				return models;
			} else {
				return new ArrayList<DpmInsModel>();
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//            return null;
		}
	}

	/**
	 * 
	 * @Title: downloadDpmInsModel
	 * @Description: 导出不良品信息
	 * @param @param request
	 * @param @param response
	 * @return void
	 * @throws Exception
	 * @throws           @author luoxq
	 * @date 2018年10月11日 上午10:55:08
	 */
	@RequestMapping("downloadDpmInsModel")
	public void downloadDpmInsModel(HttpServletRequest request, HttpServletResponse response, DpmInsModel model)
			throws Exception {
		try {
			String exportFileName = "不良品信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			IUser user = ContextUtil.getCurrentUser();
			model.setFactoryCode(user.getCurFactoryCode());
			model.setAccount(user.getAccount());
			model.setCheckUser(user.getAccount());
			model.setCreationUser(user.getAccount());
			boolean b = dpmInsManager.isChecker(model); // 判断是否是审核人
			if (b) {
				model.setIsChecker(DpmInsModel.IS_CHECKER);
			}

			model.setInsStatusUnsbmit(DpmInsModel.INS_STATUS_UNSUBMIT);

			List<DpmInsModel> list = dpmInsManager.queryDpmInsByKey(model);
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

			String[] headers = { "不良品类型", "申请单号", "状态", "填单时间", "申请人", "申请科室", "零件号", "简号", "零件名称", "不良品数量", "发现区域",
					"不良品项目", "不良品描述", "责任组", "车型", "处理结果", "打印状态", "供应商代码", "供应商名称", "供应商担当", "供应商联系方式", "是否紧急",
					"订单生成状态", "例外订单生成状态", "备注" };
			String[] columns = { "dpmType", "applyNo", "insStatus", "insDate", "creator", "applyDep", "partNo",
					"partShortNo", "partNameCn", "dpmNum", "discoArea", "dpmName", "dpmDesc", "respDep", "modelCode",
					"dealResult", "printStatus", "supplierNo", "supplierName", "contact", "telNo", "isUrgent",
					"orderStatus", "excepStatus", "remark" };
			int[] widths = { 80, 100, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
					80, 80, 80 };
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
	 * @Description: 选择导出勾选中的数据
	 * @param @param request
	 * @param @param response
	 * @param @param list
	 * @param @throws Exception   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年1月11日 下午2:23:16
	 */
	@RequestMapping("downloadDpmInsModelSelect")
	public void downloadDpmInsModelSelect(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {
			String applyNos = RequestUtil.getString(request, "applyNos");
			String applyNoArr[] = applyNos.split(",");
			List<DpmInsModel> list = dpmInsManager.getListByApplyArr(applyNoArr);  //查询出勾选中的数据
			String exportFileName = "不良品信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
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

			String[] headers = { "不良品类型", "申请单号", "状态", "填单时间", "申请人", "申请科室", "零件号", "简号", "零件名称", "不良品数量", "发现区域",
					"不良品项目", "不良品描述", "责任组", "车型", "处理结果", "打印状态", "供应商代码", "供应商名称", "供应商担当", "供应商联系方式", "是否紧急",
					"订单生成状态", "例外订单生成状态", "备注" };
			String[] columns = { "dpmType", "applyNo", "insStatus", "insDate", "creator", "applyDep", "partNo",
					"partShortNo", "partNameCn", "dpmNum", "discoArea", "dpmName", "dpmDesc", "respDep", "modelCode",
					"dealResult", "printStatus", "supplierNo", "supplierName", "contact", "telNo", "isUrgent",
					"orderStatus", "excepStatus", "remark" };
			int[] widths = { 80, 100, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80, 80,
					80, 80, 80 };
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
	 * @Title: excepOrder
	 * @Description: 手工生成例外订单
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年9月28日 上午11:12:57
	 */
	@RequestMapping("excepOrder")
	public void excepOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResultMessage message = null;
		IUser user = ContextUtil.getCurrentUser();
		try {
			String applyNos = RequestUtil.getString(request, "applyNoArr");

			// 生成例外订单
			message = dpmInsManager.toExcepOrder(applyNos, user.getAccount(), user.getCurFactoryCode(),
					RequestUtil.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			message = new ResultMessage(ResultMessage.FAIL, "操作失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 
	 * @Description: 不良品单据打印
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception
	 * @return void
	 * @throws @author luoxq
	 * @date 2018年10月31日 上午9:19:25
	 */
	@RequestMapping("print")
	public void dpmInsPrint(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyNos = RequestUtil.getString(request, "applyNoArr");
		String[] applyNoArr = applyNos.split(",");
//		List<DpmInsModel> list = new ArrayList<DpmInsModel>();

		try {

			if (null != applyNoArr && applyNoArr.length > 0) {
				List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
				DpmInsModel model_q = new DpmInsModel();
				
				// 生成多个InputStream file,防止抛异常
				String filenurl = FileUtil.getClassesPath() + File.separator + "template" + File.separator
						+ "ireport" + File.separator + "demo" + File.separator + "DPM_INS.jasper";
				InputStream file = new FileInputStream(filenurl);
				List<Map<String, Object>> paraList = new ArrayList<Map<String,Object>>();
				for (int j = 0; j < applyNoArr.length; j++) {
					
					
					model_q.setApplyNo(applyNoArr[j]);
					// 不良品明细
					List<DpmInsModel> detailList = dpmInsManager.queryDpmInsDetailList(model_q);
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					if (null != detailList || detailList.size() > 1) {
						// null 处理
						DpmInsModel model = detailList.get(0);
//					for (DpmInsModel model : detailList) {

						if ("1".equals(model.getDpmType())) {
							parameters.put("dpmTypeCb", "1");
							parameters.put("dpmTypeJb", "1");
						} else {
							parameters.put("dpmTypeJb", "2");
							parameters.put("dpmTypeCb", "2");
						}
						if ("1".equals(model.getDealResult())) {
							parameters.put("dealResultFp", "1");
							parameters.put("dealResultBf", "1");
						} else {
							parameters.put("dealResultBf", "2");
							parameters.put("dealResultFp", "2");
						}
						parameters.put("applyNo", model.getApplyNo());
						parameters.put("partNo", model.getPartNo());
						parameters.put("partNameCn", model.getPartNameCn());
						parameters.put("supplierNo", model.getSupplierNo());
						parameters.put("supplierName", model.getSupplierName());
						parameters.put("dpmNum", model.getDpmNum());
						parameters.put("discoArea", model.getDiscoArea());
						parameters.put("insDate", model.getInsDate());
						parameters.put("dpmDesc", model.getDpmDesc());
						parameters.put("printUser", model.getPrintUser());
						parameters.put("printPhone", model.getPrintPhone());
						paraList.add(parameters);
					}
				}
				HashMap<String, Object> map = new HashMap<String, Object>();
				JRDataSource jRDataSource = new JRBeanCollectionDataSource(paraList);
				JasperPrint jasperPrint = JasperFillManager.fillReport(file, map, jRDataSource);
				JasperPrintList.add(jasperPrint);
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline;");
				JRPdfExporter exporter = new JRPdfExporter();
				OutputStream out = response.getOutputStream();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
				exporter.exportReport();
				// 更新打印状态
				dpmInsManager.updatePrintStatus(applyNoArr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}
	
	/**
	 * 
	 * @Description: 判断是否是总成件
	 * @param @return
	 * @param @throws Exception   
	 * @return boolean  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月25日 下午4:32:57
	 */
	public boolean isZCpart(HttpServletRequest request, HttpServletResponse response, DpmInsModel model) throws Exception {
		try {
			IUser user = ContextUtil.getCurrentUser();
			model.setFactoryCode(user.getCurFactoryCode());
			boolean b = dpmInsManager.isZCpart(model);   //判断是否是总成件
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
	}

}
