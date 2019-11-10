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
 * @Title: JitPartRemainOpeAlertJob.java
 * @Package: com.hanthink.gps.jit.job
 * @Description: 零件余量日志修改提醒/小时
 * @author dtp
 * @date 2018-8-6
 */
public class JitPartRemainOpeAlertJob extends BaseJob{
	
	private static final String FACTORY_CODE = "G1";
	private static final String JIT_EXCE_BEAN_NAME_PEC = "jitExceServicePEC";
	private final static String DATE_FORMAT = "yyyy-MM-dd";
	private final static long ONE_DAY_LONG = 24 * 60 * 60 * 1000;
	private final static String FILE_PATH = Constants.FILE_UPLOAD_PATH + File.separator + "excejit";
	private final static String FILE_NAME_LDCTS = "零件余量修改日志";
	
	private JitExceService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (JitExceService)SpringContextUtil.getBean(JIT_EXCE_BEAN_NAME_PEC);
			
			deleteFile();
			
			JitPartVO jpvo = new JitPartVO(); 
			jpvo.setFactoryCode(FACTORY_CODE);
			
			List<JitPartVO> list = service.queryPartRemainOpeAlertList(jpvo);
			if(null != list && list.size() > 0){
				try {
					String filePath = createJitPartRemainOpeAlertFile(list);
					
					List<String> filePathList = new ArrayList<String>();
					StringBuffer emailInfo = new StringBuffer();
					if(!StringUtil.isNullOrEmpty(filePath)){
						filePathList.add(filePath);
						emailInfo.append("<span style=\"color:red;\">最近二小时有修改零件余量日志,详见附件。</span></br>");
					}
					if(null == filePathList || 0 >= filePathList.size()){
						LogUtil.info("没有需要发送的零件余量日志修改提醒");
						return;
					}
					//查询发送邮件人信息
					String[] toArr = this.queryTimerEmailAddress();
					if(null == toArr || 0 >= toArr.length){
						LogUtil.info("没有接收零件余量日志修改提醒的人员");
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
					boolean sendFlg = MailUtil.sendMail("common.ftl", 
							"零件余量修改提醒("+jpvo.getFactoryCode()+")", 
							toArr, null, null, model, attachmentName, resource);
					LogUtil.info("发送零件余量修改提醒,发送状态："+sendFlg);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					LogUtil.error(e);
					LogUtil.info("创建零件余量日志修改统计文件夹失败");
				}
				
			}
			
		}
		
		
	}

	/**
	 * 生成零件余量修改记录excel
	 * @param list
	 * @return
	 * @throws FileNotFoundException
	 */
	private String createJitPartRemainOpeAlertFile(List<JitPartVO> list) throws FileNotFoundException {
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建零件余量日志修改统计文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator +FACTORY_CODE+ FILE_NAME_LDCTS+ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"工厂","工作中心","信息点","零件编号","落点","修改前余量","修改后余量","手工调整量",
				"操作人","操作时间","操作IP"};
		String[] columns = {"factory","workcenter","planCode","partNo","location","partRemainOld","partRemainNew"
				,"modNum","opeUser","opeTime","opeIp"};
		int[] widths = {80,100,100,150,120,100,100,100,100,100,120};
		
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
				if(!StringUtil.isNullOrEmpty(pathname.getName())
						&& (pathname.getName().startsWith(FACTORY_CODE + FILE_NAME_LDCTS))) {
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

	public JitExceService getService() {
		return service;
	}

	public void setService(JitExceService service) {
		this.service = service;
	}

	
}
