package com.hanthink.jisi.controller;

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

import com.hanthink.jisi.manager.JisiVehQueueManager;
import com.hanthink.jisi.model.JisiVehQueueModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;
/**
 * 
 * ClassName: JisiVehQueueController 
 * @Description: 过点车序队列分页查询
 * @author yokoboy
 * @date 2018年11月9日
 */
@Controller
@RequestMapping("/jisi/vehQueue")
public class JisiVehQueueController extends GenericController{

	private static Logger log = LoggerFactory.getLogger(JisiVehQueueController.class);
	@Resource
	private JisiVehQueueManager manager;
	
	/**
	 * 
	 * @Description: 厂内同步过点车序队列分页查询
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return
	 * @param @throws Exception   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 下午4:22:44
	 */
	@RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            @ModelAttribute("model") JisiVehQueueModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        IUser user = ContextUtil.getCurrentUser();
        try {
        	model.setFactoryCode(user.getCurFactoryCode());
            PageList<JisiVehQueueModel> pageList = (PageList<JisiVehQueueModel>) manager.queryJisiVehQueueForPage(model, p);
            return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
		}
        
    }
	
	/**
	 * 
	 * @Description: 过点车序数据导出
	 * @param @param request
	 * @param @param response
	 * @param @param model   
	 * @return void  
	 * @throws Exception 
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月9日 下午4:38:22
	 */
	@RequestMapping("downloadJisiPartModel")
	public void downloadJisiVehQueueModel(HttpServletRequest request,HttpServletResponse response, JisiVehQueueModel model) throws Exception{
		try {
		String exportFileName = "PAOFF队列信息导出" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		IUser user = ContextUtil.getCurrentUser();
        model.setFactoryCode(user.getCurFactoryCode());
		List<JisiVehQueueModel> list =  manager.queryJisiVehQueueByKey(model);
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
		String[] headers = {"生产单号","VIN","车型", "车身序号", "PA OFF时间","推算状态", "推算时间","下线批次进度"};
		String[] columns = {"erpOrderNo","vin", "modelCode", "wcSeqno", "passTime","execStatus", "execTime","offineSeqno"};
		int[] widths = {80, 100, 60, 80, 120, 80, 100,100};
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, exportFileName, list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw new Exception("系统错误,请联系管理员");
//			ExcelUtil.exportException(e, request, response);
		}
	}
}
