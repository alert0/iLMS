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
 * @Title: PubPartSupplierJob.java
 * @Package: com.hanthink.gps.gacne.pub.job
 * @Description: 系统存在异常零件信息
 * @author dtp
 * @date 2019-2-19
 */
public class PubPartSupplierJob extends BaseJob{
	
	private final static String FACTORY_CODE = "2000";

	private final static String DATE_FORMAT = "yyyy-MM-dd";
	private final static long ONE_DAY_LONG = 24 * 60 * 60 * 1000;
	private final static String FILE_PATH = Constants.FILE_UPLOAD_PATH + File.separator + "pubPartSupplier";
	
	private final static String FILE_NAME = "系统零件异常记录";
	
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
		
		List<PubOrderVO> list = service.queryPartSupplier(vo);
		
		try {
			String filePath = createFilePath(list);
			//附件路径list
			List<String> filePathList = new ArrayList<String>();
			//邮件正文
			StringBuffer emailInfo = new StringBuffer();
			if(!StringUtil.isNullOrEmpty(filePath)){
				filePathList.add(filePath);
				emailInfo.append("<span style=\"color:red;\">有零件异常信息，详见附件。</span></br>");
			}
			//查询发送人信息
			String[] toArr = this.queryTimerEmailAddress();
			if(null == toArr || 0 >= toArr.length){
				LogUtil.info("没有接收零件异常提醒的人员");
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
			if(!StringUtil.isNullOrEmpty(filePath)){
				String[] csArr = this.queryCSEmailAddress();
				boolean sendFlg = MailUtil.sendMail("common.ftl", "零件异常提醒", 
						toArr, csArr, null, model, attachmentName, resource);
				LogUtil.info("零件异常提醒,发送状态："+sendFlg);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private String createFilePath(List<PubOrderVO> list) throws FileNotFoundException {
		if(null == list || 0 >= list.size()){
			LogUtil.info("零件异常提醒:系统不存在异常零件记录");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建零件异常提醒文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator + FILE_NAME +ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"零件号", "供应商代码", "供应商出货地", "生效时间", "失效时间", "异常描述"};
		String[] columns = {"partNo", "supplierNo","supFactory","effStart","effEnd","errorMessage"};
		int[] widths = {120, 100,100,100,100,150};
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

}
