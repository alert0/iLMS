package com.hanthink.sps.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.business.util.MakeQrcodeImages;
import com.hanthink.pub.model.PubDataDictModel;
import com.hanthink.pub.model.PubPlanCodeModel;
import com.hanthink.sps.controller.SpsInsController;
import com.hanthink.sps.dao.SpsInsDao;
import com.hanthink.sps.dao.SpsVehQueueDao;
import com.hanthink.sps.manager.SpsVehQueueManager;
import com.hanthink.sps.model.SpsInsDetailModel;
import com.hanthink.sps.model.SpsInsModel;
import com.hanthink.sps.model.SpsMouldModel;
import com.hanthink.sps.model.SpsVehQueueModel;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * @ClassName: SpsVehQueueManagerImpl
 * @Description: SPS过点车序查询
 * @author dtp
 * @date 2018年10月16日
 */
@Service("SpsVehQueueManager")
public class SpsVehQueueManagerImpl extends AbstractManagerImpl<String, SpsVehQueueModel> implements SpsVehQueueManager{

	@Resource
	private SpsVehQueueDao spsVehQueueDao;
	@Resource
	private SpsInsDao spsInsDao;
	
	@Override
	protected Dao<String, SpsVehQueueModel> getDao() {
		return spsVehQueueDao;
	}

	/**
	 * @Description: SPS过点车序查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<SpsVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@Override
	public PageList<SpsVehQueueModel> querySpsVehQueuePage(SpsVehQueueModel model, DefaultPage page) {
		return spsVehQueueDao.querySpsVehQueuePage(model, page);
	}

	/**
	 * @Description: SPS过点车序查询(导出excel)
	 * @param: @param model
	 * @param: @return    
	 * @return: List<SpsVehQueueModel>   
	 * @author: dtp
	 * @date: 2018年10月16日
	 */
	@Override
	public List<SpsVehQueueModel> querySpsVehQueueList(SpsVehQueueModel model) {
		return spsVehQueueDao.querySpsVehQueueList(model);
	}

	/**
	 * @Description: 加载SPS信息点下拉框     
	 * @param: @param model
	 * @param: @return    
	 * @return: List<PubPlanCodeModel>   
	 * @author: dtp
	 * @date: 2018年12月27日
	 */
	@Override
	public List<PubPlanCodeModel> loadPlanCodeComboData(PubPlanCodeModel model) {
		return spsVehQueueDao.loadPlanCodeComboData(model);
	}

	/**
	 * 加载票据模板下拉框
	 * @param model
	 * @return
	 */
	@Override
	public List<PubDataDictModel> loadSpsMouldComboData(SpsVehQueueModel model) {
		return spsVehQueueDao.loadSpsMouldComboData(model);
	}

	/**
	 * @Description: 生成试打印指示票
	 * @param: @param list
	 * @param: @param request
	 * @param: @param response    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月23日
	 */
	@Override
	public void printTestIns(List<SpsVehQueueModel> list, HttpServletRequest request, HttpServletResponse response,
			Map<String, String> map, SpsVehQueueModel testPrintModel) {
		//写入临时表
		testPrintModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		int count = Integer.valueOf(String.valueOf(spsVehQueueDao.insertTestInsTemp(list)));
		if(count > 0) {
			//调用存储生成sps分拣单
			spsVehQueueDao.createSpsTestIns(map);
			List<SpsInsModel> insNoList = spsVehQueueDao.querySpsTestPrintInsNoList(testPrintModel);
			//查询打印模板MODEL_CODE
			SpsMouldModel modelCodeModel = spsVehQueueDao.queryModelCode(testPrintModel);
			modelCodeModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			//查询分拣单最大位置号,判断是否分页打印
			SpsMouldModel spsModel = spsVehQueueDao.queryMaxPlaceByModelCode(modelCodeModel);
			Integer maxMouldPlace =  206;
			if("SPS_XH".equals(modelCodeModel.getMouldCode())) {
				maxMouldPlace = SpsInsController.maxMouldPlaceSPS_XH;//箱子模板给个固定值266
			}
			if("SPS_TC".equals(modelCodeModel.getMouldCode())) {
				maxMouldPlace = SpsInsController.maxMouldPlaceSPS_TC;//台车模板给固定值206
			}
			if(null != spsModel && StringUtils.isNotBlank(spsModel.getMouldHeadPlace())) {
				maxMouldPlace = Integer.valueOf(spsModel.getMouldHeadPlace());//根据模板获取最大位置号
			}
			//打印N张
			List<JasperPrint> JasperPrintList = new ArrayList<JasperPrint>();
			try {
				for (SpsInsModel insNoModel : insNoList) {
					String filenurl = FileUtil.getClassesPath() + File.separator + "template" 
							+ File.separator +"ireport" + File.separator + "sps" + File.separator 
							+ modelCodeModel.getMouldCode() + ".jasper";
					InputStream file = new FileInputStream(filenurl);
					//分页
					String filenurl_2 = FileUtil.getClassesPath() + File.separator + "template" 
							+ File.separator +"ireport" + File.separator + "sps" + File.separator 
							+ modelCodeModel.getMouldCode() + "2" + ".jasper";
					InputStream file_2 = new FileInputStream(filenurl_2);
					//参数
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					HashMap<String, Object> parameters_2 = new HashMap<String, Object>();
					insNoModel.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
					//查询打印信息
					List<SpsInsDetailModel> detailList = spsInsDao.querySpsInsDetailList(insNoModel);
					if(null != detailList && detailList.size() > 0) {
						//设置打印值
						List<Integer> mouldPlaceList = new ArrayList<Integer>();
						for (int j = 0; j < detailList.size(); j++) {
							SpsInsDetailModel sps = detailList.get(j);
							mouldPlaceList.add(Integer.valueOf(sps.getMouldPlace()));
							if(21 == sps.getMouldPlace()) {
								//打印二维码
								parameters.put("s21", MakeQrcodeImages.getQrCodeImage(sps.getShowValue(), "80", "80"));
								parameters_2.put("s21", MakeQrcodeImages.getQrCodeImage("21", "80", "80"));
							}else {
								parameters.put("s" + sps.getMouldPlace(), sps.getShowValue());
								parameters_2.put("s" + sps.getMouldPlace(), sps.getShowValue());
							}
						}
						JRDataSource jRDataSource = new JREmptyDataSource();
						JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
						JasperPrint jasperPrint_2 = JasperFillManager.fillReport(file_2, parameters_2, jRDataSource);
						JasperPrintList.add(jasperPrint);
						//判断是否需要分页
						if(null != mouldPlaceList && mouldPlaceList.size() > 0) {
							Integer mouldPlace = Collections.max(mouldPlaceList);
							if(maxMouldPlace < mouldPlace) {
								JasperPrintList.add(jasperPrint_2);
							}
						}
					}
				}
				//防止size = 0 抛异常
				if(JasperPrintList.size() > 0) {
					response.setContentType("application/pdf");
					response.setHeader("Content-disposition", "inline;");
					JRPdfExporter exporter = new JRPdfExporter();
					OutputStream out = response.getOutputStream();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
					exporter.exportReport();
				}
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("指示票内容为空");
			}
				
		}
	}

	/**
	 * @Description:  查询分拣单最大位置号,判断是否分页打印  
	 * @param: @param modelCodeModel
	 * @param: @return    
	 * @return: SpsMouldModel   
	 * @author: dtp
	 * @date: 2019年2月26日
	 */
	@Override
	public SpsMouldModel queryMaxPlaceByModelCode(SpsMouldModel modelCodeModel) {
		return spsVehQueueDao.queryMaxPlaceByModelCode(modelCodeModel);
	}

}
