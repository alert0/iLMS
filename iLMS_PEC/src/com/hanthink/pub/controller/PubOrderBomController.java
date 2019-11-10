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

import com.hanthink.base.model.DictVO;
import com.hanthink.pub.manager.PubOrderBomManager;
import com.hanthink.pub.model.PubOrderBomModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.persistence.dao.SysTypeDao;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;


/**
 * 
 * <pre> 
 * 描述：单车BOM表 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-06 00:31:49
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Controller
@RequestMapping("/pub/pubOrderBom")
public class PubOrderBomController extends GenericController{
	
	private static Logger log = LoggerFactory.getLogger(PubOrderBomController.class);
	@Resource
	PubOrderBomManager pubOrderBomManager;
	
	@Resource
    SysTypeDao sysTypeDao;
	
	/**
     * 分页查询单车BOM表
     * @param request
     * @param reponse
	 * @return 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("curdlistJson")
    public @ResponseBody PageJson curdlistJson(HttpServletRequest request,
            HttpServletResponse response,
            PubOrderBomModel model) {
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			// 查询需要转换的的数据字典
            List<DictVO> workcenterList = sysTypeDao.queryPubDataDictByCodeType("PUB_WORKCENTER");
            for(DictVO d: workcenterList){
            	if (d.getValueName() != null && d.getValueName().equals(model.getWorkcenter())) {
                    model.setWorkcenter(d.getValueKey());
                }
            }
			List<PubOrderBomModel> pageList = (PageList<PubOrderBomModel>) pubOrderBomManager
					.queryPubOrderBomForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
    /**
	 * 下载导出单车BOM表信息
	 * @param request
	 * @param response
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午11:25:20
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("downloadPubOrderBomModel")
	public void downloadPubOrderBomModel(HttpServletRequest request,HttpServletResponse response,
			PubOrderBomModel model){
		try {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		// 查询需要转换的的数据字典
        List<DictVO> workcenterList = sysTypeDao.queryPubDataDictByCodeType("PUB_WORKCENTER");
        for(DictVO d: workcenterList){
        	if (d.getValueName() != null && d.getValueName().equals(model.getWorkcenter())) {
                model.setWorkcenter(d.getValueKey());
            }
        }
		List<PubOrderBomModel> list =  pubOrderBomManager.queryPubOrderBomByKey(model);
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
		
		String[] headers = {"生产单号", "整车物料号", "组件物料行号", "零件号",
				"零件名称","车间","工位","用量", "用量单位", "采购类型"};
		String[] columns = {"orderNo", "vehiclePartNo", "partRowNo", "partNo",
				"partNameCn","codeValueName","stationCode","num", "usageAmountUnit", "purchaseType"
				};
		int[] widths = {80, 80, 80, 80,
				80,80, 80, 80, 80, 80};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "单车BOM"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
    
}
