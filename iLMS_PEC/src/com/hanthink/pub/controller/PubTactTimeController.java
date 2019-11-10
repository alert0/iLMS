package com.hanthink.pub.controller;


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

import com.hanthink.pub.manager.PubTactTimeManager;
import com.hanthink.pub.model.PubTactTimeModel;
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
 * 描述：生产节拍表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/pub/pubTactTime")
public class PubTactTimeController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PubTactTimeController.class);
	@Resource
	PubTactTimeManager pubTactTimeManager;
	
	/**
     * 分页查询生产节拍表
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            PubTactTimeModel model) {
    	String resultMsg=null;
    	try {
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());		
        List<PubTactTimeModel> pageList =  (PageList<PubTactTimeModel>) pubTactTimeManager.queryPubTactTimeForPage(model, p);
    	return new PageJson(pageList);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
	
    /**
   	 * 下载导出物料主数据表信息
   	 * @param request
   	 * @param response
   	 * @author linzhuo	
   	 * @DATE	2018年9月10日 上午11:25:20
   	 */
   	@RequestMapping("downloadPubTactTimeModel")
   	public void downloadPubTactTimeModel(HttpServletRequest request,HttpServletResponse response,
   			PubTactTimeModel model){
   		try {
   		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
   		List<PubTactTimeModel> list =  pubTactTimeManager.queryPubTactTimeByKey(model);
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
   		
   		String[] headers = {"车间", "生产线", "生产节拍（s）", "小时产能（JPH）",
   				"开动率（OEE）","年产能","备注"};
   		String[] columns = {"codeValueName", "codeValueNameC", "produceTime", "jph",
   				"oee","proCapacity","remark"};
   		int[] widths = {80, 80, 80, 80,
   				80, 80, 80};
   		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
   		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "生产节拍"+df.format(new Date()), list, headers, widths, columns);
   		} catch (Exception e) {
   			e.printStackTrace();
   			log.error(e.toString());
   			ExcelUtil.exportException(e, request, response);
   		}
   		
   	}
    
}
