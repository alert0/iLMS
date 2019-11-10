package com.hanthink.jit.controller;


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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.jit.manager.JitPkgReqManager;
import com.hanthink.jit.model.JitPkgReqModel;
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
 * 描述：拉动包装明细查询表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/jit/jitPkgReq")
public class JitPkgReqController extends GenericController{
	private static Logger log = LoggerFactory.getLogger(JitPkgReqController.class);
	
	@Resource
	JitPkgReqManager jitPkgReqManager;
	
	/**
     * 拉动包装明细查询表
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse,
            @ModelAttribute("model") JitPkgReqModel model) {
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
        		getQueryFilter(request).getPage().getPageSize()));
        model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
        List<JitPkgReqModel> pageList = (PageList<JitPkgReqModel>) jitPkgReqManager.queryJitPkgReqForPage(model, p);
        return new PageJson(pageList);
    }
	
    /**
	 * 下载导出拉动包装明细查询信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadJitPkgReqModel")
	public void downloadJitPkgReqModel(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitPkgReqModel model){
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		try {
			List<JitPkgReqModel> list =  jitPkgReqManager.queryJitPkgReqByKey(model);
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
			
			String[] headers = {"车间","信息点", "物流单号", "零件号","简号",
					"零件名称","配送地址", "配送量", "收容数","订购包装数",
					"出货仓库", "到货仓库", "下线批次",
					"备件批次进度","发货批次进度", "到货批次进度", "配送批次进度",
					"供应商代码", "出货地代码","供应商名称",
					"创建时间"};
			String[] columns = {"workcenter","planCodeDesc", "orderNo", "partNo","partShortNo",
					"partName","location", "requireNum", "standardPackage","distriPackage",
					"shipDepot", "arrDepot", "kbProductSeqno",
					"prepareProductSeqno", "deliveryProductSeqno", "arriveProductSeqno","distriProductSeqno",
					"supplierNo", "supFactory","supplierName",
					"creationTime"};
			int[] widths = {80, 80, 80, 80, 80,
					80, 80, 80, 80, 80,
				    80, 80, 80,
				    80, 80, 80, 80,
					80, 80, 80,
					80};
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "拉动包装明细查询"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
}
