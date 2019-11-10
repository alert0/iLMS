package com.hanthink.pkg.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hanthink.pkg.model.PkgProposalModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.mini.web.util.DefaultFileUtil;
import com.hotent.sys.persistence.manager.FileManager;
import com.hotent.sys.persistence.model.DefaultFile;

/**
 * 
 * @Desc		: 包装提案书导出工具类
 * @FileName	: PkgExportUtil.java
 * @CreateOn	: 2018年11月26日 下午9:39:21
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 
 */
public class PkgExportUtil {
	
	private static Logger log = LoggerFactory.getLogger(PkgExportUtil.class);
	
	FileManager fileManager;
	
	/** 供应商日期 */
	public static final String SUP_DATE = "SUP_DATE";
	/** 供应商包装联系部门 */
	public static final String SUP_DEPT = "SUP_DEPT";
	/** 供应商包装联系电话 */
	public static final String SUP_TEL = "SUP_TEL";
	/** 供应商包装联系邮箱 */
	public static final String SUP_EMAIL = "SUP_EMAIL";
	/** 供应商包装管理部长 */
	public static final String SUP_PACK_BZ = "SUP_PACK_BZ";
	/** 供应商包装管理科长 */
	public static final String SUP_PACK_KZ = "SUP_PACK_KZ";
	/** 供应商包装管理担当 */
	public static final String SUP_PACK_DD = "SUP_PACK_DD";
	
	
	public PkgExportUtil(FileManager fileManager){
		this.fileManager = fileManager;
	}
	
	
	/**
	 * 导出一般包装提案书
	 * @param pkgModel
	 * @param param
	 * @author ZUOSL	
	 * @throws Exception 
	 * @DATE	2018年11月27日 下午1:19:31
	 */
	public void exportPkgProposalNormal(PkgProposalModel pkgModel, Map<String, String> param,
			OutputStream outStream) throws Exception{
		
//		String srcFilePath = "E:\\test\\excel\\PKG_PROPLSAL_NORMAL_MODEL.xlsx";
		String srcFilePath = FileUtil.getClassesPath() + File.separator + "template" 
				+ File.separator +"excel" + File.separator +"pkg" 
				+ File.separator + "PKG_PROPLSAL_NORMAL_MODEL.xlsx";
		
		try {
			
			InputStream is = new FileInputStream(new File(srcFilePath));
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);
	        if (null == sheet) {
	        	throw new Exception("not found sheet!");
	        }
	        
	        //填充数据
	        setCellVal(sheet, 5, "I", null == pkgModel.getPartNo() ? "" : pkgModel.getPartNo()); //零件号
	        setCellVal(sheet, 5, "S", null == pkgModel.getPartNameCn() ? "" : pkgModel.getPartNameCn()); 
	        setCellVal(sheet, 5, "AE", null == pkgModel.getProject() ? "" : pkgModel.getProject());
	        setCellVal(sheet, 6, "B", null == pkgModel.getCarType() ? "" : pkgModel.getCarType());
	        setCellVal(sheet, 6, "I", null == pkgModel.getSupplierNo() ? "" : pkgModel.getSupplierNo());
	        setCellVal(sheet, 6, "S", null == pkgModel.getSupplierName() ? "" : pkgModel.getSupplierName());
	        setCellVal(sheet, 37, "G", null == pkgModel.getReplyLimitDate() ? "" : pkgModel.getReplyLimitDate());
	        setCellVal(sheet, 35, "S", null == param.get(SUP_DEPT) ? "" : param.get(SUP_DEPT));//供应商包装联系部门
	        setCellVal(sheet, 35, "Y", null == param.get(SUP_DATE) ? "" : param.get(SUP_DATE));//日期
//	        setCellVal(sheet, 36, "S", null == param.get(SUP_TEL) ? "" : param.get(SUP_TEL));//供应商包装联系电话
//	        setCellVal(sheet, 37, "S", null == param.get(SUP_EMAIL) ? "" : param.get(SUP_EMAIL));//供应商包装联系邮箱
	        setCellVal(sheet, 36, "AB", null == param.get(SUP_PACK_BZ) ? "" : param.get(SUP_PACK_BZ));//供应商包装联系部长
	        setCellVal(sheet, 36, "AD", null == param.get(SUP_PACK_KZ) ? "" : param.get(SUP_PACK_KZ));//供应商包装联系科长
//	        setCellVal(sheet, 36, "AF", null == param.get(SUP_PACK_DD) ? "" : param.get(SUP_PACK_DD));//供应商包装联系担当
	        
	        setCellVal(sheet, 36, "S", null == pkgModel.getPackDeptTel()? "" : pkgModel.getPackDeptTel());//供应商包装联系电话
	        setCellVal(sheet, 37, "S", null == pkgModel.getPackDeptMail() ? "" : pkgModel.getPackDeptMail());//供应商包装联系邮箱
	        setCellVal(sheet, 36, "AF", null == pkgModel.getPackDeptContact()? "" : pkgModel.getPackDeptContact());//供应商包装联系担当
	        
	        setCellVal(sheet, 39, "C", null == pkgModel.getBoxTypeName() ? "" : pkgModel.getBoxTypeName());
	        setCellVal(sheet, 40, "P", null == pkgModel.getTotalWeight() ? "" : pkgModel.getTotalWeight()); //总重量
	        
			
	        if(null != pkgModel.getPackLength()){
	        	setCellVal(sheet, 40, "E", Double.valueOf(pkgModel.getPackLength()));
	        }
	        if(null != pkgModel.getPackWidth()){
	        	setCellVal(sheet, 40, "G", Double.valueOf(pkgModel.getPackWidth()));
	        }
	        if(null != pkgModel.getPackHeight()){
	        	setCellVal(sheet, 40, "I", Double.valueOf(pkgModel.getPackHeight()));
	        }
	        if(null != pkgModel.getPackWeight()){
	        	setCellVal(sheet, 40, "O", Double.valueOf(pkgModel.getPackWeight()));
	        }
			
	        
	        if(null != pkgModel.getStandardPackage()){
	        	setCellVal(sheet, 39, "K", Double.valueOf(pkgModel.getStandardPackage()));
	        }
	        if(null != pkgModel.getPartWeight()){
	        	setCellVal(sheet, 40, "M", Double.valueOf(pkgModel.getPartWeight()));
	        }
	        if(null != pkgModel.getTotalWeight()){
	        	setCellVal(sheet, 40, "P", Double.valueOf(pkgModel.getTotalWeight()));
	        }
	        
	        //看板位置
	        if("L".equals(pkgModel.getBoardLocation())){
	        	setCellVal(sheet, 39, "Q", "√ L侧");
	        }else if("W".equals(pkgModel.getBoardLocation())){
	        	setCellVal(sheet, 40, "Q", "√ W侧");
	        }
	        
	        if(null != pkgModel.getTrayLength()){
	        	setCellVal(sheet, 40, "S", Double.valueOf(pkgModel.getTrayLength()));
	        }
	        if(null != pkgModel.getTrayWidth()){
	        	setCellVal(sheet, 40, "U", Double.valueOf(pkgModel.getTrayWidth()));
	        }
	        if(null != pkgModel.getTrayHeight()){
	        	setCellVal(sheet, 40, "W", Double.valueOf(pkgModel.getTrayHeight()));
	        }
	        
	        //逐个包装
	        if("1".equals(pkgModel.getOnePackage())){
	        	setCellVal(sheet, 39, "Y", "√ 有");
	        }else if("0".equals(pkgModel.getOnePackage())){
	        	setCellVal(sheet, 40, "Y", "√ 无");
	        }
	        
	        //防尘罩
	        if("1".equals(pkgModel.getDustCover())){
	        	setCellVal(sheet, 39, "AB", "√ 有");
	        }else if("0".equals(pkgModel.getDustCover())){
	        	setCellVal(sheet, 40, "AB", "√ 无");
	        }
	        
	        //内材
	        if("1".equals(pkgModel.getIntMate())){
	        	setCellVal(sheet, 39, "AE", "√ 有");
	        }else if("0".equals(pkgModel.getIntMate())){
	        	setCellVal(sheet, 40, "AE", "√ 无");
	        }
	        
	        setCellVal(sheet, 42, "Y", null == pkgModel.getWordDesc() ? "" : pkgModel.getWordDesc());
	        
	        //填充图片
	        Drawing drawing = sheet.createDrawingPatriarch();
//	        byte[] buff = new byte[1024];
//	        int rc = 0;  
	        
	        //单一零件图
	        if(StringUtil.isNotEmpty(pkgModel.getSinglePartPic())){
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
//		        InputStream imgis = new FileInputStream("e:\\test\\ftpdownjpg.jpg"); //本地图片测试 
//				while ((rc = imgis.read(buff)) > 0) {  
//					out.write(buff, 0, rc);  
//				} 
	        	DefaultFile dfile = fileManager.get(pkgModel.getSinglePartPic());
	        	DefaultFileUtil.exportFile(dfile, out);
				if(null != out){
	        		out.close();
	        	}
	        	ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 
	        			ExcelUtil.getColumnIndex("C"), 41,
	        			ExcelUtil.getColumnIndex("N"), 50);
	        	drawing.createPicture(anchor, workbook.addPicture(out.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG)); 
	        }
	        
	        //单个零件摆放图
	        if(StringUtil.isNotEmpty(pkgModel.getSinglePartPutPic())){
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        	InputStream imgis = new FileInputStream("e:\\test\\tesnpng.png");   //本地图片测试 
//	        	while ((rc = imgis.read(buff)) > 0) {  
//	        		out.write(buff, 0, rc);  
//	        	}
	        	DefaultFile dfile = fileManager.get(pkgModel.getSinglePartPutPic());
	        	DefaultFileUtil.exportFile(dfile, out);
	        	if(null != out){
	        		out.close();
	        	}
	        	ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 
	        			ExcelUtil.getColumnIndex("O"), 41,
	        			ExcelUtil.getColumnIndex("Y"), 50);
	        	drawing.createPicture(anchor, workbook.addPicture(out.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
	        }
	        
	        //包装俯视图
	        if(StringUtil.isNotEmpty(pkgModel.getPackOverLookPic())){
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        	InputStream imgis = new FileInputStream("e:\\test\\tesnpng.png");   //本地图片测试 
//	        	while ((rc = imgis.read(buff)) > 0) {  
//					out.write(buff, 0, rc);  
//				} 
	        	DefaultFile dfile = fileManager.get(pkgModel.getPackOverLookPic());
	        	DefaultFileUtil.exportFile(dfile, out);
	        	if(null != out){
	        		out.close();
	        	}
	        	ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 
	        			ExcelUtil.getColumnIndex("C"), 51,
	        			ExcelUtil.getColumnIndex("N"), 60);
	        	drawing.createPicture(anchor, workbook.addPicture(out.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
	        }
	        
	        //包装侧视图
	        if(StringUtil.isNotEmpty(pkgModel.getPackSideLookPic())){
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        	InputStream imgis = new FileInputStream("e:\\test\\tesnpng.png");   //本地图片测试 
//	        	while ((rc = imgis.read(buff)) > 0) {  
//					out.write(buff, 0, rc);  
//				}
	        	DefaultFile dfile = fileManager.get(pkgModel.getPackSideLookPic());
	        	DefaultFileUtil.exportFile(dfile, out);
	        	if(null != out){
	        		out.close();
	        	}
	        	ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 
	        			ExcelUtil.getColumnIndex("O"), 51,
	        			ExcelUtil.getColumnIndex("Y"), 60);
	        	drawing.createPicture(anchor, workbook.addPicture(out.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
	        }
	        
	        
	        workbook.write(outStream);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw e;
		}
	}

	/**
	 * 导出一般包装提案的组合提案的提案书
	 * @param proModelList
	 * @param param
	 * @param outStream
	 * @throws Exception
	 * @author ZUOSL	
	 * @DATE	2019年1月8日 上午11:46:09
	 */
	public void exportPkgProposalNormalCom(List<PkgProposalModel> proModelList, Map<String, String> param,
			OutputStream outStream) throws Exception{
		if(null == proModelList || 0 >= proModelList.size()){
			return;
		}
		
		PkgProposalModel pkgModel = proModelList.get(0);
		
//		String srcFilePath = "E:\\test\\excel\\PKG_PROPLSAL_NORMAL_COM_MODEL.xlsx";
		String srcFilePath = FileUtil.getClassesPath() + File.separator + "template" 
				+ File.separator +"excel" + File.separator +"pkg" 
				+ File.separator + "PKG_PROPLSAL_NORMAL_COM_MODEL.xlsx";
		
		try {
			
			InputStream is = new FileInputStream(new File(srcFilePath));
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);
	        if (null == sheet) {
	        	throw new Exception("not found sheet!");
	        }
	        
	        //填充数据
	        setCellVal(sheet, 5, "AB", null == pkgModel.getSupplierNo() ? "" : pkgModel.getSupplierNo());
	        setCellVal(sheet, 5, "E", null == pkgModel.getSupplierName() ? "" : pkgModel.getSupplierName());
	        
	        for(int i = 0; i < proModelList.size(); i ++){
	        	if(i >= 6){
	        		break;
	        	}
	        	PkgProposalModel tmpModel = proModelList.get(i);
	        	setCellVal(sheet, 7+i, "B", null == tmpModel.getCarType() ? "" : tmpModel.getCarType());
	        	setCellVal(sheet, 7+i, "F", null == tmpModel.getPartNo() ? "" : tmpModel.getPartNo()); //零件号
	        	setCellVal(sheet, 7+i, "L", null == tmpModel.getPartNameCn() ? "" : tmpModel.getPartNameCn()); 
	        	setCellVal(sheet, 7+i, "AE", null == tmpModel.getProject() ? "" : tmpModel.getProject());
	        	
	        	if(null != tmpModel.getStandardPackage()){
		        	setCellVal(sheet, 31+i, "E", Double.valueOf(tmpModel.getStandardPackage()));
		        }
	        	if(null != tmpModel.getPartWeight()){
		        	setCellVal(sheet, 31+i, "G", Double.valueOf(tmpModel.getPartWeight()));
		        }
	        	
	        	//逐个包装 - 使用关联单元格填充控件值
		        if("1".equals(tmpModel.getOnePackage())){
		        	setCellVal(sheet, 38+i, "BU", "0");
		        	setCellVal(sheet, 38+i, "BV", "1");
		        }else if("0".equals(tmpModel.getOnePackage())){
		        	setCellVal(sheet, 38+i, "BU", "1");
		        	setCellVal(sheet, 38+i, "BV", "0");
		        }
	        	
	        }
	        
	        //箱Code 长、宽、高
	        setCellVal(sheet, 46, "C", null == pkgModel.getBoxCode() ? "" : pkgModel.getBoxCode());
	        if(null != pkgModel.getPackLength()){
	        	setCellVal(sheet, 46, "E", Double.valueOf(pkgModel.getPackLength()));
	        }
	        if(null != pkgModel.getPackWidth()){
	        	setCellVal(sheet, 46, "G", Double.valueOf(pkgModel.getPackWidth()));
	        }
	        if(null != pkgModel.getPackHeight()){
	        	setCellVal(sheet, 46, "I", Double.valueOf(pkgModel.getPackHeight()));
	        }
	        
	        //容器重
	        if(null != pkgModel.getTrolleyWeight()){
	        	setCellVal(sheet, 50, "F", Double.valueOf(pkgModel.getTrolleyWeight()));
	        }
	        if(null != pkgModel.getPartTotalWeight()){
	        	setCellVal(sheet, 51, "F", Double.valueOf(pkgModel.getPartTotalWeight()));
	        }
	        if(null != pkgModel.getTotalWeight()){
	        	setCellVal(sheet, 52, "F", Double.valueOf(pkgModel.getTotalWeight()));
	        }
	        
	        setCellVal(sheet, 28, "F", null == pkgModel.getReplyLimitDate() ? "" : pkgModel.getReplyLimitDate());
	        setCellVal(sheet, 26, "R", null == param.get(SUP_DEPT) ? "" : param.get(SUP_DEPT));//供应商包装联系部门
	        setCellVal(sheet, 26, "Y", null == param.get(SUP_DATE) ? "" : param.get(SUP_DATE));//日期
	        setCellVal(sheet, 27, "R", null == param.get(SUP_TEL) ? "" : param.get(SUP_TEL));//供应商包装联系电话
	        setCellVal(sheet, 28, "R", null == param.get(SUP_EMAIL) ? "" : param.get(SUP_EMAIL));//供应商包装联系邮箱
	        setCellVal(sheet, 27, "AB", null == param.get(SUP_PACK_BZ) ? "" : param.get(SUP_PACK_BZ));//供应商包装联系部长
	        setCellVal(sheet, 27, "AD", null == param.get(SUP_PACK_KZ) ? "" : param.get(SUP_PACK_KZ));//供应商包装联系科长
	        setCellVal(sheet, 27, "AF", null == param.get(SUP_PACK_DD) ? "" : param.get(SUP_PACK_DD));//供应商包装联系担当
	        
	        //看板位置
	        if("L".equals(pkgModel.getBoardLocation())){
	        	setCellVal(sheet, 47, "BU", "1");
	        	setCellVal(sheet, 47, "BV", "0");
	        }else if("W".equals(pkgModel.getBoardLocation())){
	        	setCellVal(sheet, 47, "BU", "0");
	        	setCellVal(sheet, 47, "BV", "1");
	        }
	        
	        //防尘罩
	        if("1".equals(pkgModel.getDustCover())){
	        	setCellVal(sheet, 49, "BU", "0");
	        	setCellVal(sheet, 49, "BV", "1");
	        }else if("0".equals(pkgModel.getDustCover())){
	        	setCellVal(sheet, 49, "BU", "1");
	        	setCellVal(sheet, 49, "BV", "0");
	        }
	        
	        //文字说明
	        setCellVal(sheet, 52, "K", null == pkgModel.getWordDesc() ? "" : pkgModel.getWordDesc());
	        
	        
	        //填充图片
	        Drawing drawing = sheet.createDrawingPatriarch();
//	        byte[] buff = new byte[1024];
//	        int rc = 0;  
	        
	        //单一零件图
	        if(StringUtil.isNotEmpty(pkgModel.getSinglePartPic())){
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
//		        InputStream imgis = new FileInputStream("e:\\test\\ftpdownjpg.jpg"); //本地图片测试 
//				while ((rc = imgis.read(buff)) > 0) {  
//					out.write(buff, 0, rc);  
//				} 
	        	DefaultFile dfile = fileManager.get(pkgModel.getSinglePartPic());
	        	DefaultFileUtil.exportFile(dfile, out);
				if(null != out){
	        		out.close();
	        	}
	        	ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 
	        			ExcelUtil.getColumnIndex("K"), 29,
	        			ExcelUtil.getColumnIndex("V"), 38);
	        	drawing.createPicture(anchor, workbook.addPicture(out.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG)); 
	        }
	        
	        //单个零件摆放图
	        if(StringUtil.isNotEmpty(pkgModel.getSinglePartPutPic())){
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        	InputStream imgis = new FileInputStream("e:\\test\\tesnpng.png");   //本地图片测试 
//	        	while ((rc = imgis.read(buff)) > 0) {  
//	        		out.write(buff, 0, rc);  
//	        	}
	        	DefaultFile dfile = fileManager.get(pkgModel.getSinglePartPutPic());
	        	DefaultFileUtil.exportFile(dfile, out);
	        	if(null != out){
	        		out.close();
	        	}
	        	ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 
	        			ExcelUtil.getColumnIndex("W"), 29,
	        			ExcelUtil.getColumnIndex("AG"), 38);
	        	drawing.createPicture(anchor, workbook.addPicture(out.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
	        }
	        
	        //包装俯视图
	        if(StringUtil.isNotEmpty(pkgModel.getPackOverLookPic())){
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        	InputStream imgis = new FileInputStream("e:\\test\\tesnpng.png");   //本地图片测试 
//	        	while ((rc = imgis.read(buff)) > 0) {  
//					out.write(buff, 0, rc);  
//				} 
	        	DefaultFile dfile = fileManager.get(pkgModel.getPackOverLookPic());
	        	DefaultFileUtil.exportFile(dfile, out);
	        	if(null != out){
	        		out.close();
	        	}
	        	ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 
	        			ExcelUtil.getColumnIndex("K"), 39,
	        			ExcelUtil.getColumnIndex("V"), 50);
	        	drawing.createPicture(anchor, workbook.addPicture(out.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
	        }
	        
	        //包装侧视图
	        if(StringUtil.isNotEmpty(pkgModel.getPackSideLookPic())){
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        	InputStream imgis = new FileInputStream("e:\\test\\tesnpng.png");   //本地图片测试 
//	        	while ((rc = imgis.read(buff)) > 0) {  
//					out.write(buff, 0, rc);  
//				}
	        	DefaultFile dfile = fileManager.get(pkgModel.getPackSideLookPic());
	        	DefaultFileUtil.exportFile(dfile, out);
	        	if(null != out){
	        		out.close();
	        	}
	        	ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 
	        			ExcelUtil.getColumnIndex("W"), 39,
	        			ExcelUtil.getColumnIndex("AG"), 50);
	        	drawing.createPicture(anchor, workbook.addPicture(out.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
	        }
	        
	        
	        workbook.write(outStream);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw e;
		}
	}
	
	/**
	 * 导出 台车包装提案书
	 * @param pkgModel
	 * @param param
	 * @author ZUOSL	
	 * @throws Exception 
	 * @DATE	2018年11月27日 下午1:19:31
	 */
	public void exportPkgProposalTrolley(List<PkgProposalModel> proModelList, Map<String, String> param,
			OutputStream outStream) throws Exception{
		
		if(null == proModelList || 0 >= proModelList.size()){
			return;
		}
		
		PkgProposalModel pkgModel = proModelList.get(0);
		
//		String srcFilePath = "E:\\test\\excel\\PKG_PROPLSAL_TROLLEY_MODEL.xlsx";
		String srcFilePath = FileUtil.getClassesPath() + File.separator + "template" 
				+ File.separator +"excel" + File.separator +"pkg" 
				+ File.separator + "PKG_PROPLSAL_TROLLEY_MODEL.xlsx";
		
		try {
			
			InputStream is = new FileInputStream(new File(srcFilePath));
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheetAt(0);
	        if (null == sheet) {
	        	throw new Exception("not found sheet!");
	        }
	        
	        //填充数据
	        setCellVal(sheet, 5, "AB", null == pkgModel.getSupplierNo() ? "" : pkgModel.getSupplierNo());
	        setCellVal(sheet, 5, "E", null == pkgModel.getSupplierName() ? "" : pkgModel.getSupplierName());
	        
	        for(int i = 0; i < proModelList.size(); i ++){
	        	if(i >= 6){
	        		break;
	        	}
	        	PkgProposalModel tmpModel = proModelList.get(i);
	        	setCellVal(sheet, 7+i, "B", null == tmpModel.getCarType() ? "" : tmpModel.getCarType());
	        	setCellVal(sheet, 7+i, "F", null == tmpModel.getPartNo() ? "" : tmpModel.getPartNo()); //零件号
	        	setCellVal(sheet, 7+i, "L", null == tmpModel.getPartNameCn() ? "" : tmpModel.getPartNameCn()); 
	        	setCellVal(sheet, 7+i, "AE", null == tmpModel.getProject() ? "" : tmpModel.getProject());
	        	
	        	if(null != tmpModel.getStandardPackage()){
		        	setCellVal(sheet, 32+i, "E", Double.valueOf(tmpModel.getStandardPackage()));
		        }
	        	if(null != tmpModel.getPartWeight()){
		        	setCellVal(sheet, 32+i, "G", Double.valueOf(tmpModel.getPartWeight()));
		        }
	        	
	        	if(null != tmpModel.getPartLength()){
		        	setCellVal(sheet, 39+i, "E", Double.valueOf(tmpModel.getPartLength()));
		        }
	        	if(null != tmpModel.getPartWidth()){
		        	setCellVal(sheet, 39+i, "G", Double.valueOf(tmpModel.getPartWidth()));
		        }
	        	if(null != tmpModel.getPartHeight()){
		        	setCellVal(sheet, 39+i, "I", Double.valueOf(tmpModel.getPartHeight()));
		        }
	        	
	        }
	        
	        if(null != pkgModel.getEmptyTrolleyLength()){
	        	setCellVal(sheet, 46, "E", Double.valueOf(pkgModel.getEmptyTrolleyLength()));
	        }
	        if(null != pkgModel.getEmptyTrolleyWidth()){
	        	setCellVal(sheet, 46, "G", Double.valueOf(pkgModel.getEmptyTrolleyWidth()));
	        }
	        if(null != pkgModel.getEmptyTrolleyHeight()){
	        	setCellVal(sheet, 46, "I", Double.valueOf(pkgModel.getEmptyTrolleyHeight()));
	        }
	        if(null != pkgModel.getRealTrolleyLength()){
	        	setCellVal(sheet, 48, "E", Double.valueOf(pkgModel.getRealTrolleyLength()));
	        }
	        if(null != pkgModel.getRealTrolleyWidth()){
	        	setCellVal(sheet, 48, "G", Double.valueOf(pkgModel.getRealTrolleyWidth()));
	        }
	        if(null != pkgModel.getRealTrolleyHeight()){
	        	setCellVal(sheet, 48, "I", Double.valueOf(pkgModel.getRealTrolleyHeight()));
	        }
	        
	        if(null != pkgModel.getTrolleyWeight()){
	        	setCellVal(sheet, 50, "F", Double.valueOf(pkgModel.getTrolleyWeight()));
	        }
	        if(null != pkgModel.getPartTotalWeight()){
	        	setCellVal(sheet, 51, "F", Double.valueOf(pkgModel.getPartTotalWeight()));
	        }
	        if(null != pkgModel.getTotalWeight()){
	        	setCellVal(sheet, 52, "F", Double.valueOf(pkgModel.getTotalWeight()));
	        }
	        
	        setCellVal(sheet, 29, "F", null == pkgModel.getReplyLimitDate() ? "" : pkgModel.getReplyLimitDate());
	        setCellVal(sheet, 27, "R", null == param.get(SUP_DEPT) ? "" : param.get(SUP_DEPT));//供应商包装联系部门
	        setCellVal(sheet, 27, "Y", null == param.get(SUP_DATE) ? "" : param.get(SUP_DATE));//日期
//	        setCellVal(sheet, 28, "R", null == param.get(SUP_TEL) ? "" : param.get(SUP_TEL));//供应商包装联系电话
//	        setCellVal(sheet, 29, "R", null == param.get(SUP_EMAIL) ? "" : param.get(SUP_EMAIL));//供应商包装联系邮箱
	        setCellVal(sheet, 28, "AB", null == param.get(SUP_PACK_BZ) ? "" : param.get(SUP_PACK_BZ));//供应商包装联系部长
	        setCellVal(sheet, 28, "AD", null == param.get(SUP_PACK_KZ) ? "" : param.get(SUP_PACK_KZ));//供应商包装联系科长
//	        setCellVal(sheet, 28, "AF", null == param.get(SUP_PACK_DD) ? "" : param.get(SUP_PACK_DD));//供应商包装联系担当
	        
	        setCellVal(sheet, 28, "R", null == pkgModel.getPackDeptTel() ? "" : pkgModel.getPackDeptTel());//供应商包装联系电话
	        setCellVal(sheet, 29, "R", null == pkgModel.getPackDeptMail() ? "" : pkgModel.getPackDeptMail());//供应商包装联系邮箱
	        setCellVal(sheet, 28, "AF", null == pkgModel.getPackDeptContact() ? "" : pkgModel.getPackDeptContact());//供应商包装联系担当
	        
	        if(null != pkgModel.getMaxPackageNum()){
	        	setCellVal(sheet, 30, "F", Double.valueOf(pkgModel.getMaxPackageNum()));
	        }
	        
	        //定位器
	        if("1".equals(pkgModel.getIsPositioner())){
	        	setCellVal(sheet, 53, "G", "√");
	        }else if("0".equals(pkgModel.getIsPositioner())){
	        	setCellVal(sheet, 53, "I", "√");
	        }
	        
	        //台车编码
	        if("1".equals(pkgModel.getIsTrolleyCode())){
	        	setCellVal(sheet, 55, "G", "√");
	        }else if("0".equals(pkgModel.getIsTrolleyCode())){
	        	setCellVal(sheet, 55, "I", "√");
	        }
	        
	        //防尘罩
	        if("1".equals(pkgModel.getDustCover())){
	        	setCellVal(sheet, 56, "G", "√");
	        }else if("0".equals(pkgModel.getDustCover())){
	        	setCellVal(sheet, 56, "I", "√");
	        }
	        
	        //逐个包装
	        if("1".equals(pkgModel.getOnePackage())){
	        	setCellVal(sheet, 57, "G", "√");
	        }else if("0".equals(pkgModel.getOnePackage())){
	        	setCellVal(sheet, 57, "I", "√");
	        }
	        
	        //看板位置
	        if("L".equals(pkgModel.getBoardLocation())){
	        	setCellVal(sheet, 58, "G", "√");
	        }else if("W".equals(pkgModel.getBoardLocation())){
	        	setCellVal(sheet, 58, "I", "√");
	        }
	        
	        setCellVal(sheet, 53, "K", null == pkgModel.getWordDesc() ? "" : pkgModel.getWordDesc());
	        if(null != pkgModel.getTractionRodHeight()){
	        	setCellVal(sheet, 52, "AE", Double.valueOf(pkgModel.getTractionRodHeight()));
	        }
	        if(null != pkgModel.getWheelDiameter()){
	        	setCellVal(sheet, 55, "AE", Double.valueOf(pkgModel.getWheelDiameter()));
	        }
	        
	        //填充图片
	        Drawing drawing = sheet.createDrawingPatriarch();
//	        byte[] buff = new byte[1024];
//	        int rc = 0;  
	        
	        //空台车正面图
	        if(StringUtil.isNotEmpty(pkgModel.getEmptyTroFrontPic())){
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
//		        InputStream imgis = new FileInputStream("e:\\test\\ftpdownjpg.jpg"); //本地图片测试 
//				while ((rc = imgis.read(buff)) > 0) {  
//					out.write(buff, 0, rc);  
//				} 
	        	DefaultFile dfile = fileManager.get(pkgModel.getEmptyTroFrontPic());
	        	DefaultFileUtil.exportFile(dfile, out);
				if(null != out){
	        		out.close();
	        	}
	        	ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 
	        			ExcelUtil.getColumnIndex("K"), 30,
	        			ExcelUtil.getColumnIndex("V"), 39);
	        	drawing.createPicture(anchor, workbook.addPicture(out.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG)); 
	        }
	        
	        //空台车侧面图
	        if(StringUtil.isNotEmpty(pkgModel.getEmptyTroSidePic())){
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        	InputStream imgis = new FileInputStream("e:\\test\\tesnpng.png");   //本地图片测试 
//	        	while ((rc = imgis.read(buff)) > 0) {  
//	        		out.write(buff, 0, rc);  
//	        	}
	        	DefaultFile dfile = fileManager.get(pkgModel.getEmptyTroSidePic());
	        	DefaultFileUtil.exportFile(dfile, out);
	        	if(null != out){
	        		out.close();
	        	}
	        	ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 
	        			ExcelUtil.getColumnIndex("W"), 30,
	        			ExcelUtil.getColumnIndex("AH"), 39);
	        	drawing.createPicture(anchor, workbook.addPicture(out.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
	        }
	        
	        //实台车图
	        if(StringUtil.isNotEmpty(pkgModel.getRealTroPic())){
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        	InputStream imgis = new FileInputStream("e:\\test\\tesnpng.png");   //本地图片测试 
//	        	while ((rc = imgis.read(buff)) > 0) {  
//					out.write(buff, 0, rc);  
//				} 
	        	DefaultFile dfile = fileManager.get(pkgModel.getRealTroPic());
	        	DefaultFileUtil.exportFile(dfile, out);
	        	if(null != out){
	        		out.close();
	        	}
	        	ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 
	        			ExcelUtil.getColumnIndex("K"), 40,
	        			ExcelUtil.getColumnIndex("V"), 51);
	        	drawing.createPicture(anchor, workbook.addPicture(out.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
	        }
	        
	        //重要部位图
	        if(StringUtil.isNotEmpty(pkgModel.getImportantPostionPic())){
	        	ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        	InputStream imgis = new FileInputStream("e:\\test\\tesnpng.png");   //本地图片测试 
//	        	while ((rc = imgis.read(buff)) > 0) {  
//					out.write(buff, 0, rc);  
//				}
	        	DefaultFile dfile = fileManager.get(pkgModel.getImportantPostionPic());
	        	DefaultFileUtil.exportFile(dfile, out);
	        	if(null != out){
	        		out.close();
	        	}
	        	ClientAnchor anchor = drawing.createAnchor(0,0,0,0, 
	        			ExcelUtil.getColumnIndex("W"), 40,
	        			ExcelUtil.getColumnIndex("AH"), 51);
	        	drawing.createPicture(anchor, workbook.addPicture(out.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
	        }
	        
	        
	        workbook.write(outStream);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw e;
		}
	}
	
	/**
	 * 设置EXCEL单元格值
	 * @param sheet
	 * @param row
	 * @param col 最多只支持两位字母如 AB、BK
	 * @param val
	 * @author ZUOSL	
	 * @DATE	2018年11月27日 下午3:52:21
	 */
	private static void setCellVal(XSSFSheet sheet, int row, String col, String val){
		XSSFRow r = sheet.getRow(ExcelUtil.getRowIndex(row));
        XSSFCell cell = r.getCell(ExcelUtil.getColumnIndex(col));
        cell.setCellValue(val);;
	}
	
	/**
	 * 设置EXCEL单元格值
	 * @param sheet
	 * @param row
	 * @param col 最多只支持两位字母如 AB、BK
	 * @param val
	 * @author ZUOSL	
	 * @DATE	2018年11月27日 下午3:52:21
	 */
	private static void setCellVal(XSSFSheet sheet, int row, String col, Double val){
		XSSFRow r = sheet.getRow(ExcelUtil.getRowIndex(row));
        XSSFCell cell = r.getCell(ExcelUtil.getColumnIndex(col));
        cell.setCellValue(val);;
	}
	
	//使用静态资源测试导出模板
	public static void main(String[] args) {
		String srcFilePath = "E:\\test\\excel\\PKG_PROPLSAL_NORMAL_MODEL.xlsx";
		String outFilePath = "E:\\test\\excel\\excel.xlsx";
		
		try {
			File outFile = new File(outFilePath);
			List<PkgProposalModel> list = new ArrayList<PkgProposalModel>();
			for(int i = 0; i < 3; i ++){
				PkgProposalModel pkgModel = new PkgProposalModel();
				pkgModel.setPartNo("KA899344K99UH0");
				pkgModel.setPartNameCn("零件名称啊");
				pkgModel.setProject("总装");
				pkgModel.setCarType("A56");
				pkgModel.setSupplierNo("VDD502");
				pkgModel.setSupplierName("供应商名称假设很长很长很长很长很长很长很长很长很长的名称");
				pkgModel.setReplyLimitDate("2018-12-31");//回复期限
				pkgModel.setBoxTypeName("EU箱");
				pkgModel.setPackLength("300.5");
				pkgModel.setPackWidth("600.000");
				pkgModel.setPackHeight("369");
				pkgModel.setMaxPackageNum("200.5");
				pkgModel.setStandardPackage("12");
				pkgModel.setPartWeight(String.valueOf(20+i));
				pkgModel.setPartLength(String.valueOf(300+i));
				pkgModel.setPartWidth(String.valueOf(400+i*10));
				pkgModel.setPartHeight(String.valueOf(500+i*20));
				pkgModel.setEmptyTrolleyLength("500");
				pkgModel.setEmptyTrolleyWidth("600");
				pkgModel.setEmptyTrolleyHeight("752");
				pkgModel.setRealTrolleyLength("525");
				pkgModel.setRealTrolleyWidth("625");
				pkgModel.setRealTrolleyHeight("777");
				pkgModel.setTrolleyWeight("200");
				pkgModel.setPartTotalWeight("800.6");
				pkgModel.setTotalWeight("963");
				pkgModel.setIsPositioner("1");
				pkgModel.setIsTrolleyCode("0");
				pkgModel.setWheelDiameter("120");
				pkgModel.setTractionRodHeight("200");
				pkgModel.setBoardLocation("L");
				pkgModel.setTrayLength("300.321");
				pkgModel.setTrayWidth("260");
				pkgModel.setTrayHeight("600");
				pkgModel.setOnePackage("1");
				pkgModel.setDustCover("0");
				pkgModel.setIntMate("1");
				pkgModel.setWordDesc("文字说明说文字说明，说文字说明说文字说明说说明说文字说明，说文字说明说文字说明说说明说文字说明说文字说明说文字，说明说说明说文字说明说文字说明，说文字说明说");
				pkgModel.setSinglePartPic("123");
				pkgModel.setSinglePartPutPic("123");
				pkgModel.setPackOverLookPic("123");
				pkgModel.setPackSideLookPic("123");
				pkgModel.setEmptyTroFrontPic("123");
				pkgModel.setEmptyTroSidePic("123");
				pkgModel.setRealTroPic("123");
				pkgModel.setImportantPostionPic("123");
				
				list.add(pkgModel);
			}
			
			Map<String, String> map = new HashMap<String, String>();
			map.put(SUP_DATE, "2018-12-22");
			map.put(SUP_DEPT, "销售部");
			map.put(SUP_EMAIL, "abc@rrt.com");
			map.put(SUP_TEL, "13325125164");
			map.put(SUP_PACK_BZ, "王XX");
			map.put(SUP_PACK_KZ, "李X");
			map.put(SUP_PACK_DD, "XXX");
			
			PkgExportUtil util = new PkgExportUtil(null);
//			exportPkgProposalNormal(list.getAt(0), map, new FileOutputStream(outFile));
//			exportPkgProposalTrolley(list, map, new FileOutputStream(outFile));
			util.exportPkgProposalNormalCom(list, map, new FileOutputStream(outFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		try {
//			InputStream inStream = new FileInputStream(new File(srcFilePath));
//			ByteArrayOutputStream o = new ByteArrayOutputStream();
//			byte[] buff = new byte[1024];
//			int rc = 0;  
//			while ((rc = inStream.read(buff)) > 0) {  
//				o.write(buff, 0, rc);  
//			} 
//			byte[] f = o.toByteArray();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		try {
//			
//			InputStream is = new FileInputStream(new File(srcFilePath));
//			XSSFWorkbook workbook = new XSSFWorkbook(is);
//			XSSFSheet sheet = workbook.getSheetAt(0);
//	        if (null == sheet) {
//	        	System.out.println("no sheet!");
//	           return;
//	        }
//	        
//	        XSSFRow row = sheet.getRow(6);
//	        XSSFCell cell = row.getCell(1);
//	        cell.setCellValue("测试3内容ABC");
//	        
//	        cell = row.getCell(5);
//	        cell.setCellValue("9测试内容AB99C");
//			
//	        File outFile = new File(outFilePath);
//	        workbook.write(new FileOutputStream(outFile));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}

}
