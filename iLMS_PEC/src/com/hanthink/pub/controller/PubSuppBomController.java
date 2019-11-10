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
import com.hanthink.pub.manager.PubSuppBomManager;
import com.hanthink.pub.model.PubOrderBomModel;
import com.hanthink.pub.model.PubSuppBomModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysPropertyUtil;

@Controller
@RequestMapping("/pub/pubSuppBom")
public class PubSuppBomController extends GenericController {

	@Resource
	private PubSuppBomManager manager;
	private static Logger log = LoggerFactory.getLogger(PubSuppBomController.class);

	/**
	 * 
	 * @Description: 分页查询支给bom
	 * @param @param request
	 * @param @param reponse
	 * @param @param model
	 * @param @return   
	 * @return PageJson  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月16日 上午9:30:07
	 */
	@RequestMapping("curdlistJson")
	public @ResponseBody PageJson curdlistJson(HttpServletRequest request, HttpServletResponse reponse,
			PubSuppBomModel model) {
		String resultMsg = null;
		try {
			DefaultPage p = new DefaultPage(new RowBounds(getQueryFilter(request).getPage().getStartIndex(),
					getQueryFilter(request).getPage().getPageSize()));
			model.setFactoryCode(ContextUtil.getCurrentUser().getFactoryCode());
			PageList<PubSuppBomModel> pageList = (PageList<PubSuppBomModel>) manager.queryPubSuppBomForPage(model, p);
			return new PageJson(pageList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Description: 导出支给bom数据
	 * @param @param request
	 * @param @param response
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年5月16日 上午9:32:50
	 */
	@RequestMapping("downloadPubSuppBomModel")
	public void downloadPubSuppBomModel(HttpServletRequest request,HttpServletResponse response,
			PubSuppBomModel model){
		try {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		// 查询需要转换的的数据字典
//        List<DictVO> workcenterList = sysTypeDao.queryPubDataDictByCodeType("PUB_WORKCENTER");
//        for(DictVO d: workcenterList){
//        	if (d.getValueName() != null && d.getValueName().equals(model.getWorkcenter())) {
//                model.setWorkcenter(d.getValueKey());
//            }
//        }
		List<PubSuppBomModel> list =  manager.downloadPubSuppBomModel(model);
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
		
		String[] headers = {"一级件", "一级件简号", "一级件名称", "零件号", "简号",
				"零件名称","用量","用量单位","装配线工位", "下一落点", "采购类型" ,"创建时间","最后修改时间"};
		String[] columns = {"partfId", "partShortNoId", "partNameId", "partNo","partShortNo",
				"partName","num","useAgeAmountUnit","lineStation", "nextPlacement", "purchaseType","creationTime","lastModifiedTime"
				};
		int[] widths = {100, 80, 200, 100,80,
				200,80, 80, 80, 80, 80,150,150};
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		ExcelUtil.exportExcel(ExcelUtil.EXCEL_XLSX, request, response, "支给件BOM"+df.format(new Date()), list, headers, widths, columns);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			ExcelUtil.exportException(e, request, response);
		}
		
	}
}
