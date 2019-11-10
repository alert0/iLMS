package com.hanthink.mp.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.model.DictVO;
import com.hanthink.business.util.SessionKey;
import com.hanthink.mp.manager.MpExcepOrderManager;
import com.hanthink.mp.model.MpExcepOrderModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
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
 * @Desc    : 例外订单
 * @CreateOn: 2018年9月29日
 * @author  : linzhuo
 *
 * @ChangeList
 * Date			  Version		   Editor		     ChangeReasons
 *
 */
@Controller
@RequestMapping("/mp/mpExcepOrder")
public class MpExcepOrderController extends GenericController{

    private static Logger log = LoggerFactory.getLogger(MpExcepOrderController.class);
    
    @Resource
    private MpExcepOrderManager mpExcepOrderManager;    

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
            MpExcepOrderModel model) throws Exception{
        DefaultPage p=new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(), getQueryFilter(request).getPage().getPageSize()));
        model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
        List<MpExcepOrderModel> pageList = (PageList<MpExcepOrderModel>) mpExcepOrderManager.queryMpExcepOrderForPage(model, p);
        return new PageJson(pageList);
    }
    
    /**
	 * 下载导出MpExcepOrder数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@RequestMapping("downloadMpExcepOrderModel")
	public void downloadMpExcepOrderModel(HttpServletRequest request,HttpServletResponse response
			,MpExcepOrderModel model){
		try {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<MpExcepOrderModel> list =  mpExcepOrderManager.queryMpExcepOrderByKey(model);
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
		
		String[] headers = {"订单号", "行号", "零件号","采购类型", "出货地代码","供应商代码",
				"供应商名称","到货仓库","到货日期", "净需求", 
				"用途","需求部门","需求人","联系电话",
				"发布人","发布时间", "发布状态"};
		String[] columns = {"purchaseNo", "rowNo", "partNo","purchaseType", "supFactory","supplierNo",
				"supplierName","storage","arriveDateStr","orderNum",
				"use","demandDepartment","demander","conNumber",
				"optUser","optTimeStr", "delFlagStr"
				};
		int[] widths = {80, 80, 80, 80, 80, 80,
				80, 80, 80, 80,
				80, 80, 80, 80,
				80, 80, 80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "例外订单"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
    
	/**
	 * 导入EXCEL数据信息
	 * <p>return: void</p>  
	 * <p>Description: ExcepOrderController.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月26日
	 * @version 1.0
	 */
    @RequestMapping("importModel")
    public void importModel(@RequestParam("file") MultipartFile file,
    		HttpServletRequest request,HttpServletResponse response) throws Exception{
        try {
            IUser user = ContextUtil.getCurrentUser();
            String uuid = null;
            HttpSession session = request.getSession();
            uuid = (String)session.getAttribute(SessionKey.MP_EXCEP_ORDER_IMPORT_UUID);
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
            if(StringUtil.isNotEmpty(uuid)){
            	mpExcepOrderManager.deleteImportTempDataByUUID(uuid);
            }else{
                uuid = UUID.randomUUID().toString().replaceAll("-", "");
            }
            session.setAttribute(SessionKey.MP_EXCEP_ORDER_IMPORT_UUID, uuid);
            
            Map<String,Object> resultMap = mpExcepOrderManager.importExcepOrderModel(file, uuid, RequestUtil.getIpAddr(request), user);
            /**
			 * 这里要传递uuid
			 */
            resultMap.put("uuid", uuid);
			if((Boolean)resultMap.get("result")){
				writeResultMessage(response.getWriter(), "导入成功", "", JSONObject.fromObject(resultMap), ResultMessage.SUCCESS);
			}else{
				writeResultMessage(response.getWriter(),"导入失败", (String) resultMap.get("console"),JSONObject.fromObject(resultMap), ResultMessage.FAIL);
			}
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            writeResultMessage(response.getWriter(), e.getMessage(), ResultMessage.FAIL);
        }
    }
    
    /**
     * 查询导入的临时例外订单用量数据
     * @param request
     * @param reponse
     * @return
     * @throws Exception
     */
    @RequestMapping("curdlistImportTempJson")
    public @ResponseBody PageJson curdlistImportTempJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
        Map<String, String> paramMap = new HashMap<String, String>();
        HttpSession session = request.getSession();
        paramMap.put("uuid", RequestUtil.getString(request, "uuid"));
        QueryFilter queryFilter = getQueryFilter(request);
        Page page = queryFilter.getPage();
        PageList<MpExcepOrderModel> pageList = (PageList<MpExcepOrderModel>) mpExcepOrderManager.queryImportTempData(paramMap, page);
        return new PageJson(pageList);
    }
    
    /**
     * 将临时例外订单需求数据导入正式表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("isImport")
    public void isImport(HttpServletRequest request,HttpServletResponse response) throws Exception{
        ResultMessage message = null;
        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            HttpSession session = request.getSession();
            String uuid = (String)session.getAttribute(SessionKey.MP_EXCEP_ORDER_IMPORT_UUID);
			/**
			 * 若uuid为空，则从前端请求中获取
			 */
			if(StringUtil.isEmpty(uuid)){
				uuid = RequestUtil.getString(request, "uuid");
			}
            paramMap.put("uuid", uuid);
            paramMap.put("checkResult", ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS);
            paramMap.put("factoryCode", ContextUtil.getCurrentUser().getCurFactoryCode());
            mpExcepOrderManager.insertImportData(paramMap);
            message = new ResultMessage(ResultMessage.SUCCESS, "执行成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            message = new ResultMessage(ResultMessage.FAIL, "执行失败");
        }
        writeResultMessage(response.getWriter(), message);
    }
    
    /**
	 * 导出临时数据信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:11:28
	 */
	@RequestMapping("exportTempData")
	public void exportTempData(HttpServletRequest request,HttpServletResponse response){
		try {
		Map<String, String> paramMap = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String uuid = (String)session.getAttribute(SessionKey.MP_EXCEP_ORDER_IMPORT_UUID);
		if(StringUtil.isEmpty(uuid)){
			uuid = RequestUtil.getString(request, "uuid");
		}
		paramMap.put("uuid", uuid);
		List<MpExcepOrderModel> list = mpExcepOrderManager.queryMpExcepOrderModelTempDataForExport(paramMap);
		
		String[] headers = {"到货仓库","零件号", "供应商代码", "出货地代码", "零件用量",
		"到货时间","用途","需求部门","需求人","联系电话",
		"校验结果","导入状态", "校验信息"};
		String[] columns = {"storage","partNo", "supplierNo", "supFactory", "orderNum",
				"arriveDateStr","use", "demandDepartment", "demander", "conNumber",
				"codeValueNameB","codeValueNameC", "checkInfo"};
		int[] widths = {80, 80, 80, 80, 80,
				80, 80, 80, 80, 80, 
				50, 50, 360};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "例外需求"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		}
	
    /**
     * 例外订单生成
     * <p>return: void</p>  
     * <p>Description: MpExcepOrderController.java</p>  
     * @author linzhuo  
     * @date 2018年9月27日
     * @version 1.0
     */
	@RequestMapping("generateMpExcepOrder")
	public void generateMpExcepOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			Integer OutCode= mpExcepOrderManager.generateMpExcepOrder(ContextUtil.getCurrentUser().getCurFactoryCode());
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
	 * <p>Description: MpExcepOrderController.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月27日
	 * @version 1.0
	 */
	@RequestMapping("releaseMpExcepOrder")
	public void releaseMpExcepOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			/**
			 * 根据当前登录人获取到工厂信息
			 */
			Integer count = mpExcepOrderManager.releaseMpExcepOrder(ContextUtil.getCurrentUser().getCurFactoryCode(),
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
     
	/**
	 * 获取填充下拉框
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception MM_INV_WAREHOUSE
	 */
	@RequestMapping("getInvWareHouse")
	public @ResponseBody List<DictVO> getInvWareHouse(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		try {
            List<DictVO> models = mpExcepOrderManager.getInvWareHouse();
            return models;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
	}
	
	/**
	 * 新增例外订单
	 * @param request
	 * @param response
	 * @param mpResidual
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,
			@RequestBody MpExcepOrderModel mpExcepOrderModel) throws Exception{
		String resultMsg = null;
		String id = mpExcepOrderModel.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				mpExcepOrderModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
				mpExcepOrderModel.setCreateUser(ContextUtil.getCurrentUser().getAccount());
				mpExcepOrderModel.setOptUser(ContextUtil.getCurrentUser().getAccount());
				mpExcepOrderModel.setOptTimeStr((new Date()).toString());
//				String storage = mpExcepOrderManager.selectStorageByWorkCenter(mpExcepOrderModel);
//				if(("").equals(storage)||null == storage) {
//					resultMsg = "没有该车间与到货仓库的关系，请先维护属地信息";
//					writeResultMessage(response.getWriter(), resultMsg, ResultMessage.FAIL);
//					return ;
//				}
				mpExcepOrderManager.create(mpExcepOrderModel);
				resultMsg = "添加成功";
				writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
			}
		} catch (Exception e) {
			log.error(e.toString());
			resultMsg = "新增失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}

	/**
	 * 批量删除例外订单
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			/**
			 *将数组转成list集合
			 */
			List<String> listIds = Arrays.asList(aryIds);
			/**
			 *查看选中的记录里面是否存在已订购数据
			 */
			Integer checknum = mpExcepOrderManager.queryMpExcepOrderCheck(listIds);
			 if(checknum>0) {
				 message=new ResultMessage(ResultMessage.FAIL, "记录中包含已订购数据,删除失败");
				 writeResultMessage(response.getWriter(), message);
				 return ;
			 }
			mpExcepOrderManager.removeAndLogByIds(aryIds, RequestUtil.getIpAddr(request));
			message=new ResultMessage(ResultMessage.SUCCESS, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			message=new ResultMessage(ResultMessage.FAIL, "删除失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
}
