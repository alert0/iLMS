package com.hanthink.pup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.pup.manager.PupVersionGapManager;
import com.hanthink.pup.model.PupVersionModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:版本比对控制器类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月28日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
import com.hotent.sys.util.SysPropertyUtil;

import net.sf.json.JSONObject;
@RequestMapping("/pup/version")
@Controller
public class PupVesionGapController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PupVesionGapController.class);
	
	@Resource
	private PupVersionGapManager versionManager;
	/**
	 * 订单数据差异分页查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@RequestMapping("/queryVesion")
	public @ResponseBody PageJson queryVesionGapFprPage(HttpServletRequest request,HttpServletResponse response,
															PupVersionModel model)throws Exception {
		PageList<PupVersionModel> pageList = null;
		
		Page page = getQueryFilter(request).getPage();
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		try {
			if(StringUtil.isNotEmpty(model.getFirstVersion())&&StringUtil.isNotEmpty(model.getLastVersion())) {
				pageList = versionManager.queryOneOrTwoVersion(model, page);
			}else if (StringUtil.isNotEmpty(model.getFirstVersion()) || StringUtil.isNotEmpty(model.getLastVersion())) {
				//检查是否生成物流计划
				versionManager.checkIsPlan();
				//检查是否导入上一版本数据
				versionManager.checkIsPrePlan();
				
				pageList = versionManager.queryOneOrTwoVersion(model, page);
			}else {
				//检查是否生成物流计划
				versionManager.checkIsPlan();
				//检查是否导入上一版本数据
				versionManager.checkIsPrePlan();
				
				pageList = versionManager.queryVersionGapForPage(model,page);
			}
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			List<PupVersionModel> list = new ArrayList<PupVersionModel>();
			PupVersionModel resMolde = new PupVersionModel();
			resMolde.setResultMsg(e.getMessage());
			list.add(resMolde);
			return new PageJson(list);
		}
	}
	/**
	 * 数据字典加载差异标识
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@RequestMapping("/getDiffFlag")
	public @ResponseBody List<DictVO> getDiffFlag()throws Exception {
		List<DictVO> models = versionManager.getDiffFlag();
		for (DictVO dictVO : models) {
			if("1".equals(dictVO.getValueKey())) {
				dictVO.setValueName("有差异");
			}
			if("0".equals(dictVO.getValueKey())) {
				dictVO.setValueName("无差异");
			}
		}
		return models;
	}
	/**
	 * 版本差异数据导出控制器
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@RequestMapping("/exportQueryData")
	public void exportVersionGapForQuery(HttpServletRequest request,HttpServletResponse response,
										PupVersionModel model)throws Exception {
		try {
			
			List<PupVersionModel> list = versionManager.exportVersionGapForQuery(model);
			
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); //获取系统所允许的最大导出数量
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			for (PupVersionModel pupVersionModel : list) {
				if (StringUtil.isNotEmpty(pupVersionModel.getWorkdayGap())) {
					if (Integer.valueOf(pupVersionModel.getWorkdayGap()).intValue() > 0) {
						pupVersionModel.setDiffFlag("true");
					}else {
						pupVersionModel.setDiffFlag("false");
					}
				}else {
					pupVersionModel.setDiffFlag("false");
				}
			}
			String[] headers = {"区域","卸货地点","订单物流模式","车型","集货线路","累计车次","合并车次","新工作日","旧工作日",
								"工作日差异","当日车次","计划取货日期","计划取货时间","计划到货日期","计划到货时间",
								"计划装配日期","原计划装配日期","计划装配时间","原计划装配时间","是否有差异"};
			String[] columns = {"area","unloadPlace","pickupType","carType","routeCode","totalNo","mergeNo","newWorkday","oldWorkday",
								"workdayGap","todayNo","pickDate","pickTime","arriveDate","arriveTime",
								"newAssembleDate","oldAssembleDate","newAssembleTime","oldAssembleTime","diffFlag"};
			int[] widths = {80,80,80,80,80,  80,80,80,80,80,  80,80,80,80,80,  80,80,80,80,80};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "版本比对查询数据"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 版本数据导入
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception
	 * @author zmj
	 */
	@RequestMapping("/importVersion")
	public void importVersion(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file)throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap = versionManager.importVersion(file,RequestUtil.getIpAddr(request));
			if ((Boolean)resultMap.get("result")) {
				writeResultMessage(response.getWriter(), JSONObject.fromObject(resultMap).toString(), ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMap.put("result", false);
			resultMap.put("console", e.getMessage());
			writeResultMessage(response.getWriter(),(String) resultMap.get("console"),e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	@RequestMapping("/queryforVesion")
	public @ResponseBody List<PupVersionModel> queryforVesion(HttpServletRequest request,HttpServletResponse response)throws Exception{
		List<PupVersionModel> pageList = versionManager.queryforVesion();
		return pageList;
	}
	
	@RequestMapping("/queryOneVersion")
	public @ResponseBody PageJson queryOneOrTwoVersion(HttpServletRequest request,HttpServletResponse response,
														PupVersionModel model)throws Exception{
		Page page = getQueryFilter(request).getPage();
		PageList<PupVersionModel> pageList = versionManager.queryOneOrTwoVersion(model,page);
		return new PageJson(pageList);
	}
}
