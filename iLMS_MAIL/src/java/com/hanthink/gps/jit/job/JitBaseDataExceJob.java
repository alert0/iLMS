package com.hanthink.gps.jit.job;

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

import com.hanthink.gps.jit.service.JitExceService;
import com.hanthink.gps.jit.vo.JitPartVO;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.FileUtil;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.excel.ExcelUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Desc    : 拉动基础数据维护异常提醒
 * @FileName: JitBaseDataExceJob.java 
 * @CreateOn: 2016-7-27 下午04:55:49
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 *
 */
public class JitBaseDataExceJob extends BaseJob{
	private static final String FACTORY_CODE = "G1";
	private static final String JIT_EXCE_BEAN_NAME_PEC = "jitExceServicePEC";
	
	private final static String DATE_FORMAT = "yyyy-MM-dd";
	private final static long ONE_DAY_LONG = 24 * 60 * 60 * 1000;
	private final static String FILE_PATH = Constants.FILE_UPLOAD_PATH + File.separator + "excejit";
	
	private final static String FILE_NAME_LDCTS = "拉动落点车体数未维护_";
	private final static String FILE_NAME_LDLJLD = "拉动零件落点未维护_";
	private final static String FILE_NAME_LDJCSJ = "拉动配送基本信息未维护_";
	
	private JitExceService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		
		if(null == service){
			service = (JitExceService)SpringContextUtil.getBean(JIT_EXCE_BEAN_NAME_PEC);
		}
		
		//数据查询条件
		JitPartVO qvo = new JitPartVO();
		qvo.setFactoryCode(FACTORY_CODE);
		
//		String rarFileName = "拉动基础数据维护异常提醒("+qvo.getFactoryCode()+")_";
		
		//删除当前日期前7天的打包文件
//		String deleteymd = new SimpleDateFormat(DATE_FORMAT).format(new Date(new Date().getTime() - 7 * ONE_DAY_LONG));
//		String deleteFileName = rarFileName + deleteymd + ".rar";
//		File deleteFile = new File(FILE_PATH + File.separator + deleteFileName);
//		if(deleteFile.exists()){
//			deleteFile.delete();
//			LogUtil.info("删除文件:"+deleteFileName);
//		}
		deleteFile();
		
		try {
			
			//查询拉动落点车体数未维护数据生成Excel
			List<JitPartVO> locationList = service.queryJitLocCarExce(qvo);
			String locationPath = createJitLocCarFile(locationList);
			
			//查询拉动配送基本信息未维护数据生成Excel
			List<JitPartVO> baseList = service.queryJitBaseInfoExce(qvo);
			String basePath = createJitBaseInfoFile(baseList);
			
			//查询拉动零件落点未维护数据生成Excel
			List<JitPartVO> locList = service.queryJitPartLocExce(qvo);
			String locPath = createJitPartLocFile(locList);
			
			List<String> filePathList = new ArrayList<String>();
			StringBuffer emailInfo = new StringBuffer();
			if(!StringUtil.isNullOrEmpty(basePath)){
				filePathList.add(basePath);
				emailInfo.append("<span style=\"color:red;\">有拉动配送基本信息数据未维护,详见附件。</span></br>");
			}
			if(!StringUtil.isNullOrEmpty(locPath)){
				filePathList.add(locPath);
				emailInfo.append("<span style=\"color:red;\">有拉动零件落点未维护,详见附件。</span></br>");
			}
			if(!StringUtil.isNullOrEmpty(locationPath)){
				filePathList.add(locationPath);
				emailInfo.append("<span style=\"color:red;\">有拉动落点车体数未维护,详见附件。</span></br>");
			}
			if(null == filePathList || 0 >= filePathList.size()){
				LogUtil.info("没有拉动基础数据维护异常数据信息");
				return;
			}
			
			//查询发送邮件人信息
			String[] toArr = this.queryTimerEmailAddress();
			if(null == toArr || 0 >= toArr.length){
				LogUtil.info("没有接收一线拉动基础数据维护异常提醒的人员");
				return;
			}
			
			//数据打包
//			File[] files = new File[filePathList.size()];
//			for(int i = 0; i < filePathList.size(); i ++){
//				files[i] = new File(filePathList.get(i));
//			}
//			String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
//			String fileName = rarFileName + ymd + ".rar";
//			String outPath = FILE_PATH + File.separator + fileName;
//			FileUtil.zipFile(files, new FileOutputStream(new File(outPath)));
			
			//发送邮件
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("info", emailInfo.toString());
			String[] attachmentName = new String[filePathList.size()];
			String[] resource = new String[filePathList.size()];
			for(int i = 0; i < filePathList.size(); i ++){
				String fpath = filePathList.get(i);
				resource[i] = fpath;
				attachmentName[i] = fpath.substring(fpath.lastIndexOf(File.separator)+1);
			}
			boolean sendFlg = MailUtil.sendMail("common.ftl", 
					"拉动基础数据维护异常提醒("+qvo.getFactoryCode()+")", 
					toArr, null, null, model, attachmentName, resource);
			LogUtil.info("发送拉动基础数据维护异常提醒,发送状态："+sendFlg);
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error(e);
			LogUtil.info("拉动基础数据维护异常提醒Job执行失败");
		}
		
	}

	/**
	 * 删除7天前生成的附件文件
	 * @author zuosl 2016-8-1
	 */
	private void deleteFile() {
		FileFilter ff = new FileFilter(){
			@Override
			public boolean accept(File pathname) {
				if(!StringUtil.isNullOrEmpty(pathname.getName())
						&& (
							pathname.getName().startsWith(FILE_NAME_LDCTS)
							|| pathname.getName().startsWith(FILE_NAME_LDJCSJ)
							|| pathname.getName().startsWith(FILE_NAME_LDLJLD)
						) ){
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

	/**
	 * 生成拉动落点车体数未维护数据的Excel
	 * @param locationList
	 * @return
	 * @author zuosl 2016-7-27
	 * @throws FileNotFoundException 
	 */
	private String createJitLocCarFile(List<JitPartVO> locationList) throws FileNotFoundException {
		if(null == locationList || 0 >= locationList.size()){
			LogUtil.info("拉动基础数据维护异常:没有拉动落点车体数未维护数据");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建拉动基础数据维护异常文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator +FACTORY_CODE+ FILE_NAME_LDCTS+ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"落点"};
		String[] columns = {"location"};
		int[] widths = {130};
		
		ExcelUtil.exportExcel(false, out, locationList, headers, widths, columns);
		
		return filePath;
	}

	/**
	 * 生成拉动零件落点未维护数据的Excel
	 * @param locList
	 * @return
	 * @author zuosl 2016-7-27
	 * @throws FileNotFoundException 
	 */
	private String createJitPartLocFile(List<JitPartVO> locList) throws FileNotFoundException {
		if(null == locList || 0 >= locList.size()){
			LogUtil.info("拉动基础数据维护异常:没有拉动零件落点未维护数据");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建拉动基础数据维护异常文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator +FACTORY_CODE+ FILE_NAME_LDLJLD+ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"MTO", "零件编号"};
		String[] columns = {"mto", "partNo"};
		int[] widths = {110, 130};
		
		ExcelUtil.exportExcel(false, out, locList, headers, widths, columns);
		
		return filePath;
	}

	/**
	 * 生成拉动配送基本信息未维护数据的Excel
	 * @param baseList
	 * @return
	 * @author zuosl 2016-7-27
	 * @throws FileNotFoundException 
	 */
	private String createJitBaseInfoFile(List<JitPartVO> baseList) throws FileNotFoundException {
		if(null == baseList || 0 >= baseList.size()){
			LogUtil.info("拉动基础数据维护异常:没有基本信息未维护数据");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建拉动基础数据维护异常文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator +FACTORY_CODE +FILE_NAME_LDJCSJ+ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"工作中心","零件编码", "零件简号", "零件名称","最小生效日期","MTOC前4位","多供应商"};
		String[] columns = {"workcenter","partNo", "partShortNo", "partNameZh","effDate","modelList","supplierList"};
		int[] widths = {90,130, 90, 150,120,90,150};
		
		ExcelUtil.exportExcel(false, out, baseList, headers, widths, columns);
		
		return filePath;
	}

	public JitExceService getService() {
		return service;
	}
	public void setService(JitExceService service) {
		this.service = service;
	}
	
}
