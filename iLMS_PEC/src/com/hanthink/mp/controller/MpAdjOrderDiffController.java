package com.hanthink.mp.controller;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.mp.manager.MpAdjOrderDiffManager;
import com.hanthink.mp.model.MpAdjOrderDiffModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * 
 * <pre>
 *  
 * 描述：计划对比 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpAdjOrderDiff")
public class MpAdjOrderDiffController extends GenericController {

	private static Logger log = LoggerFactory.getLogger(MpAdjOrderDiffController.class);

	@Resource
	MpAdjOrderDiffManager mpAdjOrderDiffManager;

	/**
	 * 分页查询计划对比
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("curdlistJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse
			 ,MpAdjOrderDiffModel model) {
		String resultMsg = null;
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<MpAdjOrderDiffModel> pageList = (PageList<MpAdjOrderDiffModel>) mpAdjOrderDiffManager.queryMpAdjOrderDiffForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 放回对象属性是否全为空
	 * <p>
	 * return: boolean
	 * </p>
	 * <p>
	 * Description: MpAdjOrderDiffController.java
	 * </p>
	 * 
	 * @author linzhuo
	 * @date 2018年9月27日
	 * @version 1.0
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isAllFieldNull(Object obj) throws Exception {
		Class cla = (Class) obj.getClass();
		Field[] fields = cla.getDeclaredFields();
		boolean flag = true;
		for (Field field : fields) {
			field.setAccessible(true);
			Object val = field.get(obj);
			if (val != null) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * 修改计划对比信息
	 * @param request
	 * @param response
	 * @param MpOrderRecordHis
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("updateAdjOrderDiff")
	public void updateAdjOrderDiff(HttpServletRequest request,HttpServletResponse response
			,@RequestBody MpAdjOrderDiffModel model) throws Exception{
		String resultMsg=null;
		try {
			mpAdjOrderDiffManager.updateAndLog(model, RequestUtil.getIpAddr(request));
			resultMsg="调整数量成功";
		    writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			log.error(e.toString());
			resultMsg="对计划对比操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 执行计划对比
	 * <p>
	 * return: void
	 * </p>
	 * <p>
	 * Description: MpAdjOrderDiffController.java
	 * </p>
	 * 
	 * @author linzhuo
	 * @date 2018年9月20日
	 * @version 1.0
	 */
	@RequestMapping("getAdjOrderDiff")
	public void getAdjOrderDiff(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			Integer count = mpAdjOrderDiffManager.getAdjOrderDiff(ContextUtil.getCurrentUser().getCurFactoryCode(),
					ContextUtil.getCurrentUser().getAccount());
			if (0 == count) {
			    message = new ResultMessage(ResultMessage.SUCCESS, "执行计划对比成功");
			} else {
				message = new ResultMessage(ResultMessage.FAIL, "执行计划对比失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "执行计划对比失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 供应商调查
	 * <p>
	 * return: void
	 * </p>
	 * <p>
	 * Description: MpAdjOrderDiffController.java
	 * </p>
	 * 
	 * @author linzhuo
	 * @date 2018年9月20日
	 * @version 1.0
	 */
	@SuppressWarnings({ "unused", "null" })
	@RequestMapping("supplierCheck")
	public void supplierCheck(HttpServletRequest request, HttpServletResponse response,
			@RequestBody MpAdjOrderDiffModel[] model) throws Exception {
		ResultMessage message = null;
		try {
			if (null != model&&model.length>0) {
				String result = mpAdjOrderDiffManager.sendMail( request, model);
				if(!result.isEmpty()) {
					if (result.equals("1")) {
						message = new ResultMessage(ResultMessage.FAIL, "该邮箱不存在");
					} else if (result.equals("2")) {
						message = new ResultMessage(ResultMessage.FAIL, "至少选择一条未发送的数据");
					} else if (result.equals("3")) {
						message = new ResultMessage(ResultMessage.FAIL, "包含已存在信息共享平台供应商能力反馈界面的数据");
					} else if (result.equals("4")) {
						message = new ResultMessage(ResultMessage.FAIL, "选择的数据供应商代码，出货地代码，零件号不能为空");
					} else {
						message = new ResultMessage(ResultMessage.SUCCESS, "供应商调查成功");
					}
				}
			}else {
				message = new ResultMessage(ResultMessage.FAIL, "请选择至少一条数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "供应商调查失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 下载导出MpAdjOrderDiff数据信息
	 * 
	 * @param request
	 * @param response
	 * @author linzhuo
	 * @DATE 2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadMpAdjOrderDiffModel")
	public void downloadMpAdjOrderDiffModel(HttpServletRequest request, HttpServletResponse response
			, MpAdjOrderDiffModel model) {
		try {
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<MpAdjOrderDiffModel> list = mpAdjOrderDiffManager.queryMpAdjOrderDiffByKey(model);
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

			String[] headers = { "零件号", "简号", "零件名称", "调整差异数量", "供应商代码",
					"调整日期", "差异数量", "发送标识", "发送时间" };
			String[] columns = { "partNo", "partShortNo", "partNameCn", "adjDiffNum", "supplierNo", 
					"adjDateStr","calDiffNum", "codeValueName", "sendTimeStr" };
			int[] widths = { 80, 80, 80, 80, 80, 
					 80, 80, 80, 80 };
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "计划对比" + df.format(new Date()), list,
					headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}

	}

}
