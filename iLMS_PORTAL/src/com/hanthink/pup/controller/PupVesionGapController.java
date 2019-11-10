package com.hanthink.pup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.business.util.SessionKey;
import com.hanthink.pup.manager.PupVersionGapManager;
import com.hanthink.pup.model.PupVersionModel;
import com.hanthink.pup.model.PupVersionPageModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
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
	 * @param pageModel
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@RequestMapping("/queryVesion")
	public @ResponseBody PageJson queryVesionGapFprPage(HttpServletRequest request,HttpServletResponse response,
															PupVersionPageModel pageModel)throws Exception {
		Page page = getQueryFilter(request).getPage();
		HttpSession session = request.getSession();
		session.setAttribute(SessionKey.PUP_VERSION_QYERFILTER, pageModel);
		
		PageList<PupVersionModel> pageList = versionManager.queryVersionGapForPage(pageModel,page);
		return new PageJson(pageList);
	}
	/**
	 * 数据字典加载差异标识
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@RequestMapping("/getDiffFlag")
	public @ResponseBody Object getDiffFlag()throws Exception {
		List<DictVO> models = versionManager.getDiffFlag();
		for (DictVO dictVO : models) {
			if("1".equals(dictVO.getValueKey())) {
				dictVO.setValueName("有差异");
			}
			if("0".equals(dictVO.getValueKey())) {
				dictVO.setValueName("无差异");
			}
		}
		return new PageJson(models);
	}
	/**
	 * 版本差异数据导出控制器
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@RequestMapping("/exportQueryData")
	public void exportVersionGapForQuery(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			HttpSession session = request.getSession();
			PupVersionPageModel pageModel = (PupVersionPageModel) session.getAttribute(SessionKey.PUP_VERSION_QYERFILTER);
			List<PupVersionModel> list = versionManager.exportVersionGapForQuery(pageModel);
			
			if(0 == list.size()){
				ExcelUtil.exportNoDataError(request, response);
				return;
			}
			int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE", 100000); //获取系统所允许的最大导出数量
			if(list.size() > sysMaxNum){
				ExcelUtil.exportNumError(sysMaxNum, request, response);
				return;
			}
			String[] headers = {"区域","卸货地点","订单物流模式","车型","集货线路","累计车次","合并车次","新工作日","旧工作日",
								"工作日差异","当日车次","计划取货日期","计划取货时间","计划到货日期","计划到货时间",
								"计划装配日期","原计划装配日期","计划装配时间","原计划装配时间"};
			String[] columns = {"area","unloadPlace","pickupType","carType","routeCode","totalNo","mergeNo","newWorkday","oldWorkday",
								"workdayGap","todayNo","pickDate","pickTime","arriveDate","arriveTime",
								"newAssembleDate","oldAssembleDate","newAssembleTime","oldAssembleTime"};
			int[] widths = {80,80,80,80,80,  80,80,80,80,80,  80,80,80,80,80,  80,80,80,80};
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "锁定计划"+PupUtil.getCurrentTime("yyyyMMddHHmmss"), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}
	/**
	 * 版本数据导入控制器
	 * @param request
	 * @param response
	 * @param file
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月28日
	 */
	@RequestMapping("/importVersion")
	public void importVersion(HttpServletRequest request,HttpServletResponse response,
								@RequestParam("file")MultipartFile file)throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap = versionManager.importVersion(file);
			if((Boolean)resultMap.get("result")){
				writeResultMessage(response.getWriter(), JSONObject.fromObject(resultMap).toString(), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(), JSONObject.fromObject(resultMap).toString(), ResultMessage.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			resultMap.put("result", false);
			resultMap.put("console", "导入失败");
			writeResultMessage(response.getWriter(), JSONObject.fromObject(resultMap).toString(), ResultMessage.FAIL);
		}
	}
}
