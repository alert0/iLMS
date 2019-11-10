package com.hanthink.mp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.hanthink.demo.controller.DemoController;
import com.hanthink.mp.manager.MpExcepOrderDemandHisManager;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.MpExcepOrderDemandHisModel;
import com.hanthink.pup.util.PupUtil;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * 
 * @Desc    : 例外订单
 * @CreateOn: 2018年9月29日
 * @author  : WY
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
@Controller
@RequestMapping("/mp/mpExcepOrderDemandHis")
public class MpExcepOrderDemandHisController extends GenericController{

    private static Logger log = LoggerFactory.getLogger(DemoController.class);
    
    @Resource
    private MpExcepOrderDemandHisManager mpExcepOrderDemandHis;    

    /**
     * 分页查询例外需求数据
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    @RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse reponse,
            MpExcepOrderDemandHisModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
        List<MpExcepOrderDemandHisModel> pageList = (PageList<MpExcepOrderDemandHisModel>) mpExcepOrderDemandHis.queryMpExcepOrderDemandHisForPage(model, p);
        return new PageJson(pageList);
    }
    
    /**
	 * 下载导出MpExcepOrderDemandHis数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadMpExcepOrderDemandHisModel")
	public void downloadMpExcepOrderDemandHisModel(HttpServletRequest request,HttpServletResponse response
			,MpExcepOrderDemandHisModel model){
		try {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MpExcepOrderDemandHisModel> list =  mpExcepOrderDemandHis.queryMpExcepOrderDemandHisByKey(model);
		/**
		 * 如果查询记录超过1000000条则报错
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
		
		String[] headers = {"订单号", "行号", "零件号", "出货地代码",
				"供应商名称","到货日期", "净需求", "供应商代码"};
		String[] columns = {"purchaseNo", "rowNo", "partNo", "supFactory",
				"supplierName","arriveDateStr","orderNum", "supplierNo"};
		int[] widths = { 80, 80, 80, 80,
				80, 80, 80, 80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "例外订单"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
    
    /**
     * 例外订单生成
     * <p>return: void</p>  
     * <p>Description: MpExcepOrderDemandHisController.java</p>  
     * @author linzhuo  
     * @date 2018年9月27日
     * @version 1.0
     */
	@RequestMapping("generateMpExcepOrderDemandHis")
	public void generateMpExcepOrderDemandHis(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			Integer OutCode= mpExcepOrderDemandHis.generateMpExcepOrderDemandHis(ContextUtil.getCurrentUser().getCurFactoryCode());
			if (OutCode.equals(0)) {
		        message=new ResultMessage(ResultMessage.FAIL, "已计算需求，请重新导入需求");				
			}
			else {
			message=new ResultMessage(ResultMessage.SUCCESS, "例外订单生成成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message=new ResultMessage(ResultMessage.FAIL, "例外订单生成失败");
		}
 		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 例外订单发布
	 * <p>return: void</p>  
	 * <p>Description: MpExcepOrderDemandHisController.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 */
	@RequestMapping("releaseMpExcepOrderDemandHis")
	public void releaseMpExcepOrderDemandHis(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			Integer count = mpExcepOrderDemandHis.releaseMpExcepOrderDemandHis(ContextUtil.getCurrentUser().getCurFactoryCode(),
					ContextUtil.getCurrentUser().getAccount());
			if (0 == count) {
				message = new ResultMessage(ResultMessage.SUCCESS, "例外订单发布成功");
			} else {
				message = new ResultMessage(ResultMessage.FAIL, "例外订单发布失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message=new ResultMessage(ResultMessage.FAIL, "例外订单发布失败");
		}
 		writeResultMessage(response.getWriter(), message);
	}
     
}
