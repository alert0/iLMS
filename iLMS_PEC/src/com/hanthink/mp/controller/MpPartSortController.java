package com.hanthink.mp.controller;


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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.manager.MpPartSortManager;
import com.hanthink.mp.model.MpPartSortModel;
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
 * 描述：零件分组 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/mp/mpPartSort")
public class MpPartSortController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(MpPartSortController.class);
	
	@Resource
	MpPartSortManager mpPartSortManager;
	  	
	/**
     * 分页查询零件分组
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            MpPartSortModel model) {
		String resultMsg = null;
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			List<MpPartSortModel> pageList = (PageList<MpPartSortModel>) mpPartSortManager.queryMpPartSortForPage(model,
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
	public @ResponseBody Object getUnloadPort(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = mpPartSortManager.getUnloadPort();
            return new PageJson(models);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 下载导出MpPartSort数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadMpPartSortModel")
	public void downloadMpPartSortModel(HttpServletRequest request,HttpServletResponse response
			, MpPartSortModel model){
		try {
		/*HttpSession session = request.getSession();
		MpPartSortModel model = (MpPartSortModel)session.getAttribute(SessionKey.MP_RESIDUALMODEL_QUERYFILTER);*/
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MpPartSortModel> list =  mpPartSortManager.queryMpPartSortByKey(model);
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
		
		String[] headers = {"零件号","简号", "零件名称", "供应商代码", "出货地代码",
				"供应商名称","计算队列","车间","分组号",
				"D_R起始号","D_R截止号","车型排序","收容数",
				"到货时间","线边时间","分组号使用量","线边车号(混合车型)","计算状态"};
		String[] columns = {"partNo","partShortNo", "partNameCn", "supplierNo", "supFactory",
				"supplierName","unloadPort","workcenter","groupId",
				"drSortIdStart","drSortIdEnd","realEndSortId", "orderPackage",
				"arriveTimeStr","lineSideTimeStr","groupQty","lineSideSortId","codeValueName"};
		int[] widths = {80, 80, 80, 80, 80,
				80,80,80,80,
				80,80,80,80,
				80,80,80,80,80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "零件分组"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
	
	
}
