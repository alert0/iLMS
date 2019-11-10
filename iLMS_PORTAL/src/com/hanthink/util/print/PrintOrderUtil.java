package com.hanthink.util.print;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.hanthink.pub.model.PubFactoryInfoModel;
import com.hanthink.pub.model.PubPrintOrderModel;
import com.hanthink.util.BufferImage.BarCodeImage;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * @ClassName: PrintOrder
 * @Description: 生成PDF订单打印
 * @author dtp
 * @date 2018年12月2日
 */
public class PrintOrderUtil {

	/**
	 * @Description: 获取JasperPrint
	 * @param: @param request
	 * @param: @param response
	 * @param: @param detailList    
	 * @return: JasperPrint   
	 * @author: dtp
	 * @throws JRException 
	 * @date: 2018年12月2日
	 */
	public static JasperPrint getJasPerPrintUtil(List<PubPrintOrderModel> detailList, InputStream file, PubFactoryInfoModel paramModel) throws JRException {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		if(null != detailList && detailList.size() > 0) {
			for (int j = 0; j < detailList.size(); j++) {
				//打印序号
				detailList.get(j).setNo(j+1);
			}
			//订单头信息
			PubPrintOrderModel model = detailList.get(0);
			parameters.put("factoryName", paramModel.getFactoryName());
			parameters.put("type", paramModel.getOrderType());
			parameters.put("logo", paramModel.getLogo());
			//条形码
			BufferedImage img = BarCodeImage.creatBarCode(model.getOrderNo());
			parameters.put("barCode", img);
			parameters.put("purchaseOrderno", model.getPurchaseOrderno());
			parameters.put("orderNo", model.getOrderNo());
			parameters.put("arriveTime", model.getArriveTime());
			parameters.put("arriveBatch", model.getArriveBatch());
			parameters.put("supplierName", model.getSupplierName());
			parameters.put("supplierNo", model.getSupplierNo());
			//明细size/每页打印行数
			parameters.put("ys", paramModel.getYs());
			parameters.put("labelPageNum", model.getLabelPageNum());
			JRDataSource jRDataSource;
			if (detailList.size() > 0) {
				jRDataSource = new JRBeanCollectionDataSource(detailList);
			} else {
				jRDataSource = new JREmptyDataSource();
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
			return jasperPrint;
		}else {
			return null;
		}
		
	}
	
	/**
	 * @Description: 返回打印  
	 * @param: @param response
	 * @param: @param JasperPrintList
	 * @param: @throws IOException
	 * @param: @throws JRException    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月2日
	 */
	public static void exportReportOrderUtil(HttpServletResponse response, List<JasperPrint> JasperPrintList) 
			throws IOException, JRException {
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline;");
		JRPdfExporter exporter = new JRPdfExporter();
		OutputStream out = response.getOutputStream();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.exportReport();
	}
	
	
	/**
	 * @Description: 厂外同步指示票打印工具
	 * @param: @param response
	 * @param: @param insNoArr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月26日
	 */
	public static void printJisoInsUtil(HttpServletResponse response, String[] insNoArr) {
		
	}
}

