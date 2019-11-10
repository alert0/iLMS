package com.hanthink.gps.gacne.pub.job;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.gacne.pub.service.PubOrderAlertService;
import com.hanthink.gps.gacne.pub.vo.PubOrderVO;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.FileUtil;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.excel.ExcelUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Title: PubOrderJob.java
 * @Package: com.hanthink.gps.gacne.pub.job
 * @Description: 广汽新能源_订购零件基本信息维护异常邮件提醒Job
 * @author dtp
 * @date 2019-2-14
 */
public class PubOrderJob extends BaseJob{
	
	private final static String FACTORY_CODE = "2000";
	
	private final static String DATE_FORMAT = "yyyy-MM-dd";
	private final static long ONE_DAY_LONG = 24 * 60 * 60 * 1000;
	private final static String FILE_PATH = Constants.FILE_UPLOAD_PATH + File.separator + "pubOrderAlert";
	private final static String FILE_NAME = "订购基础数据维护异常提醒";
	private final static String FILE_NAME_A = "属地管理不存在零件号、工位、车型生效记录";
	private final static String FILE_NAME_B = "取货物流没有维护对应的路线信息";
	private final static String FILE_NAME_C = "BOM不存在此生效记录";
	private final static String FILE_NAME_D = "系统不存在零件属地信息生效记录";
	private final static String FILE_NAME_PART = "系统不存在物料与供应商关系生效记录";
	private final static String FILE_NAME_E = "系统不存在卸货口、仓库生效记录";
	
	
	private PubOrderAlertService service;
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		
		if(null == service){
			service = (PubOrderAlertService)SpringContextUtil.getBean("pubOrderAlertService");
		}
		deleteFile();
		
		//设置查询参数
		PubOrderVO vo = new PubOrderVO();
		vo.setFactoryCode(FACTORY_CODE);
		
		//(工深提醒)属地管理不存在零件号、工位、车型生效记录
		List<PubOrderVO> listA = service.queryPubOrderA(vo);
		
		//BOM不存在此生效记录(C)
		List<PubOrderVO> listC = service.queryPubOrderC(vo);
		//取货物流没有维护对应的路线信息(B)
		List<PubOrderVO> listB = service.queryPubOrderB(vo);
		//系统不存在零件属地信息生效记录(D)
		List<PubOrderVO> listD = service.queryPubOrderD(vo);
		List<PubOrderVO> list = new ArrayList<PubOrderVO>(); 
		//物料与供应商关系失效记录
		List<PubOrderVO> list_part = service.queryPartSupplierInvalid(vo);
		//系统不存在卸货口、仓库生效记录
		List<PubOrderVO> listE = service.queryPubOrderE(vo);
		//零件异常记录
		List<PubOrderVO> listF = service.queryPartSupplier(vo);
		
		for (PubOrderVO pubOrderVO : listC) {
			list.add(pubOrderVO);
		}
		/*for (PubOrderVO pubOrderVO : listB) {
			list.add(pubOrderVO);
		}*/
		/*for (PubOrderVO pubOrderVO : listA) {
			list.add(pubOrderVO);
		}*/
		for (PubOrderVO pubOrderVO : listD) {
			list.add(pubOrderVO);
		}
		
		for (PubOrderVO pubOrderVO : list_part) {
			list.add(pubOrderVO);
		}
		
		//零件异常记录
		for (PubOrderVO pubOrderVO : listF) {
			list.add(pubOrderVO);
		}
		
		try {
			
			//订购零件基本信息维护异常邮件提醒
			String filePath = createFilePath(list);
			
			//属地管理不存在零件号、工位、车型生效记录
			String filePathA = createFilePathA(listA);
			
			//取货物流没有维护对应的路线信息(B)
			String filePathB = createFilePathB(listB);
			
			//附件路径list
			List<String> filePathList = new ArrayList<String>();
			
			//物料与供应商关系生效记录
			//String filePath_part = createFilePath_part(list_part);
			
			//系统不存在卸货口、仓库生效记录
			String filePathE = createFilePathE(listE);
			
			//邮件正文
			StringBuffer emailInfo = new StringBuffer();
			
			if(!StringUtil.isNullOrEmpty(filePath)){
				filePathList.add(filePath);
				emailInfo.append("<span style=\"color:red;\">有订购基础数据维护异常，详见附件。</span></br>");
			}
			
			if(!StringUtil.isNullOrEmpty(filePathB)){
				filePathList.add(filePathB);
				emailInfo.append("<span style=\"color:red;\">有取货物流没有维护对应的路线信息，详见附件。</span></br>");
			}
			
			if(!StringUtil.isNullOrEmpty(filePathA)){
				filePathList.add(filePathA);
				emailInfo.append("<span style=\"color:red;\">有属地管理不存在零件号、车型生效记录，详见附件。</span></br>");
			}
			
			/*if(!StringUtil.isNullOrEmpty(filePath_part)){
				filePathList.add(filePath_part);
				emailInfo.append("<span style=\"color:red;\">有物料与供应商关系维护异常，详见附件。</span></br>");
			}*/
			
			if(!StringUtil.isNullOrEmpty(filePathE)){
				filePathList.add(filePathE);
				emailInfo.append("<span style=\"color:red;\">有系统不存在卸货口、仓库生效记录，详见附件。</span></br>");
			}
			
			/*
			//系统不存在订购零件基本信息生效记录(A)
			String filePathA = createFilePathA(listA);
			//取货物流没有维护对应的路线信息(B)
			String filePathB = createFilePathB(listB);
			//BOM不存在此生效记录(C)
			String filePathC = createFilePathC(listC);
			//系统不存在零件属地信息生效记录(D)
			String filePathD = createFilePathD(listD);
			
			//附件路径list
			List<String> filePathList = new ArrayList<String>();
			//邮件正文
			StringBuffer emailInfo = new StringBuffer();
			if(!StringUtil.isNullOrEmpty(filePathA)){
				filePathList.add(filePathA);
				emailInfo.append("<span style=\"color:red;\">系统不存在订购零件基本信息生效记录，详见附件。</span></br>");
			}
			if(!StringUtil.isNullOrEmpty(filePathB)){
				filePathList.add(filePathB);
				emailInfo.append("<span style=\"color:red;\">取货物流没有维护对应的路线信息，详见附件。</span></br>");
			}
			if(!StringUtil.isNullOrEmpty(filePathC)){
				filePathList.add(filePathC);
				emailInfo.append("<span style=\"color:red;\">BOM不存在此生效记录，详见附件。</span></br>");
			}
			if(!StringUtil.isNullOrEmpty(filePathD)){
				filePathList.add(filePathD);
				emailInfo.append("<span style=\"color:red;\">系统不存在零件属地信息生效记录，详见附件。</span></br>");
			}*/
			//查询发送人信息
			String[] toArr = this.queryTimerEmailAddress();
			if(null == toArr || 0 >= toArr.length){
				LogUtil.info("没有订购基础数据维护异常提醒的人员");
				return;
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("info", emailInfo.toString());
			String[] attachmentName = new String[filePathList.size()];
			String[] resource = new String[filePathList.size()];
			for(int i = 0; i < filePathList.size(); i ++){
				String fpath = filePathList.get(i);
				resource[i] = fpath;
				attachmentName[i] = fpath.substring(fpath.lastIndexOf(File.separator)+1);
			}
			String[] csArr = this.queryCSEmailAddress();
			boolean sendFlg = MailUtil.sendMail("common.ftl", "订购基础数据维护异常提醒(A)", 
					toArr, csArr, null, model, attachmentName, resource);
			LogUtil.info("订购基础数据维护异常提醒,发送状态："+sendFlg);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 系统不存在卸货口、仓库生效记录
	 * @param listE
	 * @return
	 * @throws FileNotFoundException 
	 */
	private String createFilePathE(List<PubOrderVO> list) throws FileNotFoundException {
		if(null == list || 0 >= list.size()){
			LogUtil.info("订购基础数据维护异常提醒:系统不存在卸货口、仓库生效记录");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建订购基础数据维护异常提醒文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator + FILE_NAME_E +ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"零件号","车型","工作中心","零件名称","到货仓库","卸货口","异常描述"};
		String[] columns = {"partNo","modelCode","workcenterDesc","partName", "arrDepot", "unloadPort","errorMessage"};
		int[] widths = {80, 60, 80, 120, 80, 80, 200};
		ExcelUtil.exportExcel(false, out, list, headers, widths, columns);
		return filePath;
	}

	/**
	 * 物料与供应商关系生效记录
	 * @param listPart
	 * @return
	 * @throws FileNotFoundException 
	 */
	private String createFilePath_part(List<PubOrderVO> list) throws FileNotFoundException {
		if(null == list || 0 >= list.size()){
			LogUtil.info("物料与供应商关系异常提醒:系统不存在物料与供应商关系生效记录");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建物料与供应商关系异常提醒文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator + FILE_NAME_PART +ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"零件号","供应商代码","异常描述"};
		String[] columns = {"partNo","supplierNo","errorMessage"};
		int[] widths = {100, 100, 200};
		ExcelUtil.exportExcel(false, out, list, headers, widths, columns);
		return filePath;
	}

	/**
	 * 订购零件基本信息异常邮件提醒
	 * @param list
	 * @return
	 * @throws FileNotFoundException 
	 * @throws FileNotFoundException 
	 */
	private String createFilePath(List<PubOrderVO> list) throws FileNotFoundException {
		if(null == list || 0 >= list.size()){
			LogUtil.info("订购基础数据维护异常提醒:系统不存在订购零件基本信息生效记录");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建订购基础数据维护异常提醒文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator + FILE_NAME +ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"零件号", "车型","工作中心","零件名称","异常描述"};
		String[] columns = {"partNo", "modelCode","workcenter","partName","errorMessage"};
		int[] widths = {120, 60, 80, 120, 500};
		/*String[] headers = {"零件号", "供应商代码", "车型","工作中心","零件名称","异常描述"};
		String[] columns = {"partNo", "supplierNo", "modelCode","workcenter","partName","errorMessage"};
		int[] widths = {120, 100, 60, 80, 120, 500};*/
		ExcelUtil.exportExcel(false, out, list, headers, widths, columns);
		return filePath;
	}

	/**
	 * 工深提醒
	 * @param list
	 * @return
	 * @throws FileNotFoundException 
	 */
	private String createFilePathA(List<PubOrderVO> list) throws FileNotFoundException {
		if(null == list || 0 >= list.size()){
			LogUtil.info("订购基础数据维护异常提醒:属地管理不存在零件号、工位、车型生效记录");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建订购基础数据维护异常提醒文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator + FILE_NAME_A +ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"卸货代码", "供应商代码", "零件号", "备货工程",
				"台车号", "物流库地址", "配送工程", "配送地址",
				"工位号(超级BOM)", "货架号", "工程深度", "责任班组", "车型",
				"生效日期", "失效日期", "状态", "零件简号", "零件名"};
		String[] columns = {"unloadPort","supplierNo", "partNo","preparePerson",
				"carpool","storage", "distriPerson","location", 
				"processType","shelfNo", "locationNum","deptNo", "modelCode",
				"effStart", "effEnd", "sort", "partShortNo", "partName"};
		int[] widths = {60, 60, 120, 60, 
				60, 60, 60, 60,
				60, 60, 40, 60, 60,
				100, 100, 50, 60, 120};
		/*String[] headers = {"工厂","车型","工作中心","零件号","零件名称","异常描述"};
		String[] columns = {"factoryCode","modelCode","workcenterDesc","partNo","partName","errorMessage"};
		int[] widths = {60, 60, 80, 100, 120, 200};*/
		ExcelUtil.exportExcel(false, out, list, headers, widths, columns);
		return filePath;
	}
	
	/**
	 * 生成取货物流没有维护对应的路线信息附件
	 * @param list
	 * @return
	 * @throws FileNotFoundException 
	 */
	private String createFilePathB(List<PubOrderVO> list) throws FileNotFoundException {
		if(null == list || 0 >= list.size()){
			LogUtil.info("订购基础数据维护异常提醒:取货物流没有维护对应的路线信息");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建订购基础数据维护异常提醒文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator + FILE_NAME_B +ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"车型","出货地","卸货口","异常描述"};
		String[] columns = {"modelCode","supFactory","unloadPort","errorMessage"};
		int[] widths = {60, 80, 80, 200};
		ExcelUtil.exportExcel(false, out, list, headers, widths, columns);
		return filePath;
	}
	
	/**
	 * 生成BOM不存在此生效记录附件
	 * @param list
	 * @return
	 * @throws FileNotFoundException 
	 */
	private String createFilePathC(List<PubOrderVO> list) throws FileNotFoundException {
		if(null == list || 0 >= list.size()){
			LogUtil.info("订购基础数据维护异常提醒:BOM不存在此生效记录");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建订购基础数据维护异常提醒文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator + FILE_NAME_C +ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"工厂","车型","工作中心","零件号","零件名称","异常描述"};
		String[] columns = {"factoryCode","modelCode","workcenterDesc","partNo","partName","errorMessage"};
		int[] widths = {60, 60, 80, 100, 120, 200};
		ExcelUtil.exportExcel(false, out, list, headers, widths, columns);
		return filePath;
	}
	
	/**
	 * 系统不存在零件属地信息生效记录
	 * @param list
	 * @return
	 * @throws FileNotFoundException 
	 */
	private String createFilePathD(List<PubOrderVO> list) throws FileNotFoundException {
		if(null == list || 0 >= list.size()){
			LogUtil.info("订购基础数据维护异常提醒:系统不存在零件属地信息生效记录");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建订购基础数据维护异常提醒文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator + FILE_NAME_D +ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"工厂","工作中心","零件号","零件名称","异常描述"};
		String[] columns = {"factoryCode","workcenterDesc","partNo","partName","errorMessage"};
		int[] widths = {60, 80, 100, 120, 200};
		ExcelUtil.exportExcel(false, out, list, headers, widths, columns);
		return filePath;
	}

	/**
	 * 删除7天前生成的附件文件
	 */
	private void deleteFile() {
		FileFilter ff = new FileFilter(){
			@Override
			public boolean accept(File pathname) {
				if(!StringUtil.isNullOrEmpty(pathname.getName())&& (
							pathname.getName().startsWith(FILE_NAME)
							|| pathname.getName().startsWith(FILE_NAME_A)
							|| pathname.getName().startsWith(FILE_NAME_B)
							|| pathname.getName().startsWith(FILE_NAME_C)
							|| pathname.getName().startsWith(FILE_NAME_D)
							|| pathname.getName().startsWith(FILE_NAME_E)
							|| pathname.getName().startsWith(FILE_NAME_PART)
						)) {
					long d = new Date().getTime() - 7 * ONE_DAY_LONG;
					if(d > pathname.lastModified()){
						return true;
					}
				}
				return false;
			}
		};
		File f = new File(FILE_PATH);
		if(f.isDirectory()){
			for(File fi : f.listFiles(ff)){
				if(fi.exists()){
					fi.delete();
				}
			}
		}
	}

	
	public PubOrderAlertService getService() {
		return service;
	}

	public void setService(PubOrderAlertService service) {
		this.service = service;
	}

	
}
