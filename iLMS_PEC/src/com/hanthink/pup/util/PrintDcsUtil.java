package com.hanthink.pup.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import com.hanthink.business.util.MakeQrcodeImages;
import com.hanthink.pup.model.PupDcsModel;
import com.hotent.base.core.util.FileUtil;

public class PrintDcsUtil {
    
    /**
     * 供应商数据List
     * @param detailList
     * @param file
     * @param paramModel
     * @return
     * @throws JRException
     */
    public static JasperPrint getJasPerPrintUtil(List<PupDcsModel> detailList, InputStream file, PupDcsModel paramModel)
            throws JRException {
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        if (null != detailList && detailList.size() > 0) {
            // 订单头信息
            parameters.put("factoryName", paramModel.getFactoryName());
            parameters.put("routeCode", paramModel.getRouteName());
            parameters.put("planStartDate", paramModel.getPlanStartDate());
            parameters.put("planStartTime", paramModel.getPlanStartTime());
            parameters.put("planEndDate", paramModel.getPlanEndDate());
            parameters.put("planEndTime", paramModel.getPlanEndTime());
            parameters.put("workDay", paramModel.getWorkDay());
            parameters.put("supPickNum", paramModel.getSupPickNumStr());
            parameters.put("factoryName", paramModel.getFactoryName());
            parameters.put("sealNo", paramModel.getSealNo());
            parameters.put("planSheetNo", paramModel.getPlanSheetNo());
            parameters.put("logoImg", paramModel.getLogoImg());
            parameters.put("gacneLogoImg", paramModel.getGacneLogoImg());
            parameters.put("SUBREPORT_DIR", FileUtil.getClassesPath() + File.separator + "template" + File.separator + "ireport" + File.separator + "dcs"
                    + File.separator);
            
            // 条形码
            Object img = MakeQrcodeImages.getQrCodeImage(paramModel.getRouteCode() + "#" + paramModel.getPlanSheetNo() + "#" + new Date().getTime(), "100", "100");
            parameters.put("barCode", img);
            JRDataSource jRDataSource;
            if (detailList.size() > 0) {
                jRDataSource = new JRBeanCollectionDataSource(detailList);
            } else {
                jRDataSource = new JREmptyDataSource();
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(file, parameters, jRDataSource);
            return jasperPrint;
        } else {
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
    public static void exportReportOrderUtil(HttpServletResponse response, List<JasperPrint> JasperPrintList) throws IOException, JRException {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline;");
        JRPdfExporter exporter = new JRPdfExporter();
        OutputStream out = response.getOutputStream();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, JasperPrintList);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();
    }

    /**
     * DCS打印
     * 
     * @param response
     * @param JasperPrintList
     * @throws IOException
     * @throws JRException
     */
    public static void exportReportDcsUtil(HttpServletResponse response, List<PupDcsModel> list) throws IOException, JRException {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline;");
        JRPdfExporter exporter = new JRPdfExporter();
        OutputStream out = response.getOutputStream();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, list);
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
