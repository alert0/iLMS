/**
 * 
 */
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
import com.hanthink.gps.jit.vo.JitPartOneToMoreVO;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.FileUtil;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.excel.ExcelUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * 厂外拉动一点零件多个供应商异常邮件提醒
 * @author chenyong 2016-09-20 
 * mod by dtp 20180809 添加邮件信息
 */
public class JitPartOneToMoreJob extends BaseJob{
	
	private JitExceService service;
	
	private String FACTORY_CODE = "G1";
	private String JIT_EXCE_BEAN_NAME_PEC="jitExceServicePEC";
	
	private final static String DATE_FORMAT = "yyyy-MM-dd";
	private final static long ONE_DAY_LONG = 24 * 60 * 60 * 1000;
	private final static String FILE_PATH = Constants.FILE_UPLOAD_PATH + File.separator + "excejit";
	private final static String FILE_NAME_PART_ONE_TO_MORE = "一个零件多个供应商";
	private final static String FILE_NAME_JIT_PART_SUPPLIER_INVALID = "拉动零件物料供应商关系失效或未维护";
	private final static String FILE_NAME_JIT_PART_SUPPLIER_INVALID_AFTER_THREE_DAY = "拉动零件物料供应商关系3天后失效";
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (JitExceService)SpringContextUtil.getBean(JIT_EXCE_BEAN_NAME_PEC);
		}
		
		deleteFile();
		
		//设置查询参数
		JitPartOneToMoreVO jitPartOneToMoreVO=new JitPartOneToMoreVO();
		jitPartOneToMoreVO.setFactory(FACTORY_CODE);
		//查询邮件提醒出发条件
		List<JitPartOneToMoreVO>  list=service.queryJitPartOneToMoreInfo(jitPartOneToMoreVO);
		//查询厂外拉动零件并且bom生效但物料供应商关系数据失效或者没有维护物料供应商关系数据
		List<JitPartOneToMoreVO> supInvalidList = service.queryJitPartSupplierInvalid(jitPartOneToMoreVO);
		//查询厂外拉动零件并且bom生效但物料供应商关系数据在3天后即将失效的
		List<JitPartOneToMoreVO> supInvalidAfterThreeDayList = service.querySupInvalidAfterThreeDayList(jitPartOneToMoreVO);
		
		
		//工厂
		String factory=list.get(0).getFactory();
		
		//一个零件对应过个供应商
		try {
			String partOneToMoreFilePath = createPartOneToMoreSupFile(list);
			String supInvalidFilePath = createSupInvalidFile(supInvalidList);
			String supInvalidAfterThreeDayFilePath = createSupInvalidAfterThreeDayFile(supInvalidAfterThreeDayList);
			
			
			List<String> filePathList = new ArrayList<String>();

			//邮件正文
			StringBuffer emailInfo = new StringBuffer();
			if(!StringUtil.isNullOrEmpty(partOneToMoreFilePath)){
				filePathList.add(partOneToMoreFilePath);
				emailInfo.append("<span style=\"color:red;\">有一个零件多个供应商异常信息,详见附件。</span></br>");
			}
			if(!StringUtil.isNullOrEmpty(supInvalidFilePath)){
				filePathList.add(supInvalidFilePath);
				emailInfo.append("<span style=\"color:red;\">有bom生效厂外拉动零件但物料供应商关系数据失效或没有维护物料供应商关系异常信息,详见附件。</span></br>");
			}
			if(!StringUtil.isNullOrEmpty(supInvalidAfterThreeDayFilePath)){
				filePathList.add(supInvalidAfterThreeDayFilePath);
				emailInfo.append("<span style=\"color:red;\">有bom生效厂外拉动零件但物料供应商关系数据3天后即将失效,详见附件。</span></br>");
			}
			
			
			//查询发送人信息
			String[] toArr = this.queryTimerEmailAddress();
			if(null == toArr || 0 >= toArr.length){
				LogUtil.info("没有厂外拉动一点零件多个供应商异常提醒的人员");
				return;
			}
			
			//时间格式转换
			SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
			
			//拼接邮件信息
			/*emailInfo.append("&nbsp;&nbsp;以下是"+time.format(new Date())+"");
			emailInfo.append("厂外拉动一个零件多个供应商异常信息!</br></br>");
			emailInfo.append("&nbsp;&nbsp;工厂&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;零件编号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;供应商代码</br>");
			for(int i=0;i<list.size();i++){
				emailInfo.append("&nbsp;&nbsp;"+list.get(i).getFactory()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+list.get(i).getPartNo()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+list.get(i).getSupplierList()+"</br>");
			}*/
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
					factory+"物料与供应商关系异常", 
					toArr, null, null, model, attachmentName, resource);
			LogUtil.info("物料与供应商关系异常,发送状态："+sendFlg);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
	}
	/**
	 * 厂外拉动零件并且bom生效但物料供应商关系数据失效或者没有维护物料供应商关系数据EXCEL
	 * @param supInvalidAfterThreeDayList
	 * @return
	 * @throws FileNotFoundException 
	 */
	private String createSupInvalidFile(
			List<JitPartOneToMoreVO> list) throws FileNotFoundException {
		if(null == list || 0 >= list.size()){
			LogUtil.info("一个零件多个供应商异常信息:没有可导出厂外拉动零件并且" +
					"bom生效但物料供应商关系数据失效或者没有维护物料供应商关系数据");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建供应商关系文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator +FACTORY_CODE+FILE_NAME_JIT_PART_SUPPLIER_INVALID +ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"工厂","工作中心","零件号"};
		String[] columns = {"factory","workcenter","partNo"};
		int[] widths = {80, 80, 150};
		
		ExcelUtil.exportExcel(false, out, list, headers, widths, columns);
		
		return filePath;
		
	}

	/***
	 * 厂外拉动零件并且bom生效但物料供应商关系数据在3天后即将失效的EXCEL
	 * @param supInvalidList
	 * @return
	 * @throws FileNotFoundException 
	 */
	private String createSupInvalidAfterThreeDayFile(List<JitPartOneToMoreVO> list) throws FileNotFoundException {
		if(null == list || 0 >= list.size()){
			LogUtil.info("一个零件多个供应商异常信息:厂外拉动零件并且" +
					"bom生效但物料供应商关系数据在3天后即将失效的");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建供应商关系文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator +FACTORY_CODE+ FILE_NAME_JIT_PART_SUPPLIER_INVALID_AFTER_THREE_DAY +ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"工厂","工作中心","零件号"};
		String[] columns = {"factory","workcenter","partNo"};
		int[] widths = {80, 80, 150};
		
		ExcelUtil.exportExcel(false, out, list, headers, widths, columns);
		
		return filePath;
	}

	/**
     * 生产一个零件多个供应商异常excel
     * @author dtp 20180809
     * @throws FileNotFoundException 
     */
	private String createPartOneToMoreSupFile(List<JitPartOneToMoreVO>  list) throws FileNotFoundException{
		if(null == list || 0 >= list.size()){
			LogUtil.info("一个零件多个供应商异常信息:没有可导出的一个零件多个供应商数据");
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建拉动基础数据维护异常文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator +FACTORY_CODE+ FILE_NAME_PART_ONE_TO_MORE+ymd+".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"工厂","零件编号","供应商代码"};
		String[] columns = {"factory","partNo","supplierList"};
		int[] widths = {80, 150, 150};
		
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
						&& (
							pathname.getName().startsWith(FACTORY_CODE + FILE_NAME_PART_ONE_TO_MORE)
							|| pathname.getName().startsWith(FACTORY_CODE + FILE_NAME_JIT_PART_SUPPLIER_INVALID)
							|| pathname.getName().startsWith(FACTORY_CODE + FILE_NAME_JIT_PART_SUPPLIER_INVALID_AFTER_THREE_DAY)
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
	
	/*get 和   set方法*/
	public JitExceService getService() {
		return service;
	}
	public void setService(JitExceService service) {
		this.service = service;
	}
}
