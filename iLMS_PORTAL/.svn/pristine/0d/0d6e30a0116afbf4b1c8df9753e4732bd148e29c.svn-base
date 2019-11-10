package com.hanthink.jit.controller;


import java.io.IOException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanthink.jit.manager.JitVehScrapManager;
import com.hanthink.jit.model.JitVehScrapModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;


/**
 * 
 * <pre> 
 * 描述：报废信息表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/jit/jitVehScrap")
public class JitVehScrapController extends GenericController{
	private static Logger log = LoggerFactory.getLogger(JitVehScrapController.class);
	
	@Resource
	JitVehScrapManager jitVehScrapManager;
	
	/**
     * 报废信息表
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse,
            @ModelAttribute("model") JitVehScrapModel model) {
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), 
        		getQueryFilter(request).getPage().getPageSize()));
        model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
        List<JitVehScrapModel> pageList = (PageList<JitVehScrapModel>) jitVehScrapManager.queryJitVehScrapForPage(model, p);
        return new PageJson(pageList);
    }
	
    /**
	 * 下载导出报废信息信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadJitVehScrapModel")
	public void downloadJitVehScrapModel(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("model") JitVehScrapModel model){
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		try {
			List<JitVehScrapModel> list =  jitVehScrapManager.queryJitVehScrapByKey(model);
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
			
			String[] headers = {"VIN","报废车间", "最后经过工位", "报废时间","报废原因",
					"补看板状态","补看板时间", "操作人"};
			String[] columns = {"vin","scrapWorkcenter", "scrapStationCode", "scrapTime","scrapReason",
					"adjustKbState","adjustTimeStr", "adjustUser"};
			int[] widths = {80, 80, 80, 80, 80,
					80, 80, 80};
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "报废信息"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
	
	/**
	 * @Description: 手工补看板
	 * @param: @param request
	 * @param: @param response
	 * @param: @param models    
	 * @return: void   
	 * @author: dtp
	 * @throws IOException 
	 * @date: 2018年10月28日
	 */
	@RequestMapping("adjustKbFn")
	public void adjustKbFn(HttpServletRequest request,HttpServletResponse response,
				@RequestBody JitVehScrapModel[] models) throws IOException {
		IUser user = ContextUtil.getCurrentUser();
		List<JitVehScrapModel> list = new ArrayList<JitVehScrapModel>();
		for (JitVehScrapModel jitVehScrapModel : models) {
			jitVehScrapModel.setAdjustUser(user.getAccount());
			jitVehScrapModel.setAdjustIp(RequestUtil.getIpAddr(request));
			list.add(jitVehScrapModel);
		}
		try {
			jitVehScrapManager.updateAdjustKb(list, RequestUtil.getIpAddr(request));
			writeResultMessage(response.getWriter(),"手工补看板操作成功",ResultMessage.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			writeResultMessage(response.getWriter(),"手工补看板操作失败",e.getMessage(),ResultMessage.FAIL);
		}
	}
	
}
