package com.hanthink.mp.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.manager.MpSupplierSortManager;
import com.hanthink.mp.model.MpSupplierSortModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;


/**
 * 
 * <pre> 
 * 描述：供应商分组 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpSupplierSort")
public class MpSupplierSortController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(MpSupplierSortController.class);
	
	@Resource
	MpSupplierSortManager mpSupplierSortManager;
	  	
	/**
     * 分页查询供应商分组
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
             MpSupplierSortModel model) {
		String resultMsg = null;
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<MpSupplierSortModel> pageList = (PageList<MpSupplierSortModel>) mpSupplierSortManager
					.queryMpSupplierSortForPage(model, p);
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
	public @ResponseBody Object getUnloadPort(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = mpSupplierSortManager.getUnloadPort();
            return new PageJson(models);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 下载导出MpSupplierSort数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadMpSupplierSortModel")
	public void downloadMpSupplierSortModel(HttpServletRequest request,HttpServletResponse response,
			MpSupplierSortModel model){
		try {
		/*HttpSession session = request.getSession();
		MpSupplierSortModel model = (MpSupplierSortModel)session.getAttribute(SessionKey.MP_SUPPLIER_SORTMODEL_QUERYFILTER);*/
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MpSupplierSortModel> list =  mpSupplierSortManager.queryMpSupplierSortByKey(model);
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
		
		String[] headers = {"供应商代码","出货地代码", "计算队列", "分组号", "D_R起始号",
				"D_R截止号","首台车下线时间","末台车下线时间","计算状态"};
		String[] columns = {"supplierNo","supFactory", "unloadPort", "groupId", "drSortIdStart",
				"drSortIdEnd","finalUnderlineTimeStartStr","finalUnderlineTimeEndStr","codeValueName"};
		int[] widths = {80,80, 80, 80, 80,
				80,80,80,80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "供应商分组"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
	
	/**
	 * 大数据导出
	 */
	/*@RequestMapping("downloadMpSupplierSortModel2")
	public void downloadMpSupplierSortModel2(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		MpSupplierSortModel model = (MpSupplierSortModel)session.getAttribute(SessionKey.MP_RESIDUALMODEL_QUERYFILTER);
		List<MpSupplierSortModel> list =  mpSupplierSortManager.queryMpSupplierSortByKey(model);
		if(0 == list.size()){
			ExcelUtil.exportNoDataError(request, response);
			return;
		}
		int sysMaxNum = SysPropertyUtil.getIntByAlias("EXCEL_EXPORT_MAX_SIZE_T", 100000); //获取系统所允许的最大导出数量
		if(list.size() > sysMaxNum){
			ExcelUtil.exportNumError(sysMaxNum, request, response);
			return;
		}
		MpSupplierSortExportUtil exportUtil = new MpSupplierSortExportUtil(mpSupplierSortManager, model);
		String[] headers = {"供应商代码","出货地代码", "计算队列", "分组号", "D_R起始号",
				"D_R截止号","首台车下线时间","末台车下线时间","计算状态"};
		String[] columns = {"supplierNo","supFactory", "unloadPort", "groupId", "drSortIdStart",
				"drSortIdEnd","finalUnderlineTimeStartStr","finalUnderlineTimeEndStr","codeValueName"};
		int[] widths = {80,80, 80, 80, 80,
				80,80,80,80};
		try {
			exportUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "供应商分组导出模板", list, headers, widths, columns);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
	}*/
	
	
}
