package com.hanthink.pub.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.business.util.SessionKey;
import com.hanthink.pub.manager.PubPartSupplierManager;
import com.hanthink.pub.model.PubPartSupplierModel;
import com.hanthink.pub.model.PubPartSupplierModel;
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
 * 描述：物料供应商关系表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/pub/pubPartSupplier")
public class PubPartSupplierController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PubPartSupplierController.class);
	@Resource
	PubPartSupplierManager pubPartSupplierManager;
	
	/**
	 * 分页查询物料供应商关系表
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("curdlistJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse,
			PubPartSupplierModel model) {
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			/**
			 * 判断pageList是否为空
			 */
			List<PubPartSupplierModel> pageList = (PageList<PubPartSupplierModel>) pubPartSupplierManager
					.queryPubPartSupplierForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /**
   	 * 下载导出物料供应商关系表信息
   	 * @param request
   	 * @param response
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:25:20
   	 */
   	@RequestMapping("downloadPubPartSupplierModel")
   	public void downloadPubPartSupplierModel(HttpServletRequest request,HttpServletResponse response,
   			PubPartSupplierModel model){
   		try {
   		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
   		List<PubPartSupplierModel> list =  pubPartSupplierManager.queryPubPartSupplierByKey(model);
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
   		
   		String[] headers = {"零件号", "简号", "零件名称", "供应商代码","出货地代码",
   				"供应商名称","最小采购量","收容数","提前期","生效日期",
   				"失效日期"};
   		String[] columns = {"partNo", "partShortNo", "partNameCn", "supplierNo","supFactory",
   				"supplierName","minOrderNum","standardPackage","inPlanForwardTime","effStartStr",
   				"effEndStr"};
   		int[] widths = {80, 80, 80, 80,
   				80, 80, 80, 80,
   				80};
   		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
   		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "物料供应商关系"+df.format(new Date()), list, headers, widths, columns);
   		} catch (Exception e) {
   			e.printStackTrace();
   			log.error(e.toString());
   			ExcelUtil.exportException(e, request, response);
   		}
   		
   	}
    
}
