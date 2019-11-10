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
 * @Title: JitNotRecGoodsJob.java
 * @Package: com.hanthink.gps.jit.job
 * @Description: 拉动未收货情况汇报/2h
 * @author dtp
 * @date 2018-8-7
 */
public class JitNotRecGoodsJob extends BaseJob{

	private static final String FACTORY_CODE = "G1";
	private static final String JIT_EXCE_BEAN_NAME_PEC = "jitExceServicePEC";
	private final static String DATE_FORMAT = "yyyy-MM-dd";
	private final static long ONE_DAY_LONG = 24 * 60 * 60 * 1000;
	private final static String FILE_PATH = Constants.FILE_UPLOAD_PATH + File.separator + "excejit";
	
	private final static String FILE_NAME_AF_SUP = "总装未收货(供应商)";
	private final static String FILE_NAME_WE_SUP = "焊装未收货(供应商)";
	private final static String FILE_NAME_ENG_SUP = "发动机未收货(供应商)";
	private final static String FILE_NAME_AF_WX = "总装未收货(外协)";
	private final static String FILE_NAME_WE_WX = "焊装未收货(外协)";
	private final static String FILE_NAME_ENG_WX = "发动机未收货(外协)";
	
	private JitExceService service;
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (JitExceService)SpringContextUtil.getBean(JIT_EXCE_BEAN_NAME_PEC);
			JitPartVO jpvo = new JitPartVO(); 
			jpvo.setFactoryCode(FACTORY_CODE);
			
			deleteFile();
			
			//总装(供应商)
			JitPartVO af = new JitPartVO();
			af.setFactory(FACTORY_CODE);
			af.setWorkcenter("AF");
			af.setKbCode("G1AF");
			JitPartVO afBatch = service.queryKbBatch(af);
			af.setShipDepotType("SUP");
			af.setKbProductSeqNo(afBatch.getKbProductSeqNo());
			List<JitPartVO> afListSUP = service.queryNotRecGoodsList(af);
			
			//总装(外协)
			af.setShipDepotType("WX");
			List<JitPartVO> afListWX = service.queryNotRecGoodsList(af);
			
			//焊装(供应商)
			JitPartVO we = new JitPartVO();
			we.setFactory(FACTORY_CODE);
			we.setWorkcenter("WE");
			we.setKbCode("G1WE");
			JitPartVO weBatch = service.queryKbBatch(we);
			we.setShipDepotType("SUP");
			we.setKbProductSeqNo(weBatch.getKbProductSeqNo());
			List<JitPartVO> weListSUP = service.queryNotRecGoodsList(we);
			
			//焊装(外协)
			we.setShipDepotType("WX");
			List<JitPartVO> weListWX = service.queryNotRecGoodsList(we);
			
			//发动机1(供应商)
			JitPartVO eng1 = new JitPartVO();
			eng1.setFactory(FACTORY_CODE);
			eng1.setWorkcenter("ENG");
			eng1.setKbCode("G1E1ENG");
			JitPartVO eng1Batch = service.queryKbBatch(eng1);
			eng1.setShipDepotType("SUP");
			eng1.setKbProductSeqNo(eng1Batch.getKbProductSeqNo());
			List<JitPartVO> eng1ListSUP = service.queryNotRecGoodsList(eng1);
			
			//发动机1(外协)
			eng1.setShipDepotType("WX");
			List<JitPartVO> eng1ListWX = service.queryNotRecGoodsList(eng1);
			
			//发动机2(供应商)
			JitPartVO eng2 = new JitPartVO();
			eng2.setFactory(FACTORY_CODE);
			eng2.setWorkcenter("ENG");
			eng2.setKbCode("G1E2ENG");
			JitPartVO eng2Batch = service.queryKbBatch(eng2);
			eng2.setShipDepotType("SUP");
			eng2.setKbProductSeqNo(eng2Batch.getKbProductSeqNo());
			List<JitPartVO> eng2ListSUP = service.queryNotRecGoodsList(eng2);
			
			//发动机2(外协)
			eng2.setShipDepotType("WX");
			List<JitPartVO> eng2ListWX = service.queryNotRecGoodsList(eng2);
			
			//发动机3(供应商)
			JitPartVO eng3 = new JitPartVO();
			eng3.setFactory(FACTORY_CODE);
			eng3.setWorkcenter("ENG");
			eng3.setKbCode("G1E3ENG");
			JitPartVO eng3Batch = service.queryKbBatch(eng3);
			eng3.setShipDepotType("SUP");
			eng3.setKbProductSeqNo(eng3Batch.getKbProductSeqNo());
			List<JitPartVO> eng3ListSUP = service.queryNotRecGoodsList(eng3);
			
			//发动机3(外协)
			eng3.setShipDepotType("WX");
			List<JitPartVO> eng3ListWX = service.queryNotRecGoodsList(eng3);
			
			//合并发动机(供应商)
			List<JitPartVO> engListSUP = new ArrayList<JitPartVO>();
			StringBuffer engKbBatchSUP = new StringBuffer(); 
			if(!StringUtil.isNullOrEmpty(eng1Batch.getKbBatch())){
				engKbBatchSUP.append("E1:" + eng1Batch.getKbBatch());
			}
			for (int i = 0; i < eng1ListSUP.size(); i++) {
				engListSUP.add(eng1ListSUP.get(i));
			}
			if(!StringUtil.isNullOrEmpty(eng2Batch.getKbBatch())){
				engKbBatchSUP.append(",E2:" + eng2Batch.getKbBatch());
			}
			for (int i = 0; i < eng2ListSUP.size(); i++) {
				engListSUP.add(eng2ListSUP.get(i));
			}
			if(!StringUtil.isNullOrEmpty(eng3Batch.getKbBatch())){
				engKbBatchSUP.append(",E3:" + eng3Batch.getKbBatch());
			}
			for (int i = 0; i < eng3ListSUP.size(); i++) {
				engListSUP.add(eng3ListSUP.get(i));
			}
			JitPartVO engBatch = new JitPartVO();
			engBatch.setKbBatch(engKbBatchSUP.toString());

			//合并发动机(外协)
			List<JitPartVO> engListWX = new ArrayList<JitPartVO>();
			StringBuffer engKbBatchWX = new StringBuffer(); 
			if(!StringUtil.isNullOrEmpty(eng1Batch.getKbBatch())){
				engKbBatchWX.append("E1:" + eng1Batch.getKbBatch());
			}
			for (int i = 0; i < eng1ListWX.size(); i++) {
				engListWX.add(eng1ListWX.get(i));
			}
			if(!StringUtil.isNullOrEmpty(eng2Batch.getKbBatch())){
				engKbBatchWX.append(",E2:" + eng2Batch.getKbBatch());
			}
			for (int i = 0; i < eng2ListWX.size(); i++) {
				engListWX.add(eng2ListWX.get(i));
			}
			if(!StringUtil.isNullOrEmpty(eng3Batch.getKbBatch())){
				engKbBatchWX.append(",E3:" + eng3Batch.getKbBatch());
			}
			for (int i = 0; i < eng3ListWX.size(); i++) {
				engListWX.add(eng3ListWX.get(i));
			}
			
			try {
				//总装(供应商)
				String afPatchSUP = createFile(listAddNo(afListSUP), "总装拉动零件未收货报表(供应商)", FILE_NAME_AF_SUP, afBatch);
				//焊装(供应商)
				String wePatchSUP = createFile(listAddNo(weListSUP), "焊装拉动零件未收货报表(供应商)", FILE_NAME_WE_SUP, weBatch);
				//发动机(供应商)
				String engPatchSUP = createFile(listAddNo(engListSUP), "发动机拉动零件未收货报表(供应商)", FILE_NAME_ENG_SUP, engBatch);
				//总装(外协)
				String afPatchWX = createFile(listAddNo(afListWX), "总装拉动零件未收货报表(外协仓)", FILE_NAME_AF_WX, afBatch);
				//焊装(外协)
				String wePatchWX = createFile(listAddNo(weListWX), "焊装拉动零件未收货报表(供应商)", FILE_NAME_WE_WX, weBatch);
				//发动机(外协)
				String engPatchWX = createFile(listAddNo(engListWX), "发动机拉动零件未收货报表(供应商)", FILE_NAME_ENG_WX, engBatch);
				
				List<String> filePathList = new ArrayList<String>();
				StringBuffer emailInfo = new StringBuffer();
				if(!StringUtil.isNullOrEmpty(afPatchSUP)){
					filePathList.add(afPatchSUP);
					emailInfo.append("<span style=\"color:red;\">有总装拉动未收货零件报表(供应商),详见附件。</span></br>");
				}
				if(!StringUtil.isNullOrEmpty(wePatchSUP)){
					filePathList.add(wePatchSUP);
					emailInfo.append("<span style=\"color:red;\">有焊装拉动未收货零件报表(供应商),详见附件。</span></br>");
				}
				if(!StringUtil.isNullOrEmpty(engPatchSUP)){
					filePathList.add(engPatchSUP);
					emailInfo.append("<span style=\"color:red;\">有发动机拉动未收货零件报表(供应商),详见附件。</span></br>");
				}
				if(!StringUtil.isNullOrEmpty(afPatchWX)){
					filePathList.add(afPatchWX);
					emailInfo.append("<span style=\"color:red;\">有总装拉动未收货零件报表(外协),详见附件。</span></br>");
				}
				if(!StringUtil.isNullOrEmpty(wePatchWX)){
					filePathList.add(wePatchWX);
					emailInfo.append("<span style=\"color:red;\">有焊装拉动未收货零件报表(外协),详见附件。</span></br>");
				}
				if(!StringUtil.isNullOrEmpty(engPatchWX)){
					filePathList.add(engPatchWX);
					emailInfo.append("<span style=\"color:red;\">有发动机拉动未收货零件报表(外协),详见附件。</span></br>");
				}
				if(null == filePathList || 0 >= filePathList.size()){
					LogUtil.info("没有拉动未收货信息");
					return;
				}
				
				//查询发送邮件人信息
				String[] toArr = this.queryTimerEmailAddress();
				if(null == toArr || 0 >= toArr.length){
					LogUtil.info("没有拉动未收货信息报表接收人员");
					return;
				}
				
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
						"拉动收货情况每日汇报(" + FACTORY_CODE + ")", 
						toArr, null, null, model, attachmentName, resource);
				
				LogUtil.info("发送拉动未收货报表提醒,发送状态："+sendFlg);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				LogUtil.error(e);
				LogUtil.info("拉动未收货报表发送失败");
			}
			
		}
		
	}
	
	/**
	 * 添加序号
	 */
	private List<JitPartVO> listAddNo(List<JitPartVO> list){
		if(null != list){
			int no = 1;
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setNo(no);
				no++;
			}
		}
		return list;
	}
	
	/***
	 * 生成总装未收货报表文件
	 * @param list
	 * @return
	 * @throws FileNotFoundException 
	 */
	private String createFile(List<JitPartVO> list, String title, String fileName, JitPartVO vo) throws FileNotFoundException {
		if(null == list || 0 >= list.size()){
			LogUtil.info("没有" + title);
			return null;
		}
		String ymd = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		if (!FileUtil.newFolder(FILE_PATH)) {
			LogUtil.info("创建" + title + "文件夹失败");
			return null;
		}
		String filePath = FILE_PATH + File.separator + FACTORY_CODE + fileName + ymd + ".xlsx";
		OutputStream out = new FileOutputStream(filePath);
		String[] headers = {"序号", "工厂","工作中心", "信息点", "单据生成时间", "拉动订单号", "到货批次", "供应商代码"
				, "供应商名称", "零件号", "零件名称", "包装数", "配送量", "未收货数量"};
		String[] columns = {"no", "factoryCode","workcenter", "planCode", "creationTime", "orderNo", "arriveBatch"
				, "supplierNo", "supplierName", "partNo", "partName", "standardPackage", "orderQty", "notRecQty"};
		int[] widths = {50, 50, 80, 80, 100, 120, 130, 80, 140, 120, 120, 65, 65, 80};
		
		//报表日期
		String repDate = ymd;
		ExcelUtil.createNotRecGoodsExcel(false, out, list, headers, widths, columns, title, repDate, vo.getKbBatch());
		
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
							pathname.getName().startsWith(FACTORY_CODE + FILE_NAME_AF_SUP)
							|| pathname.getName().startsWith(FACTORY_CODE + FILE_NAME_WE_SUP)
							|| pathname.getName().startsWith(FACTORY_CODE + FILE_NAME_ENG_SUP)
							|| pathname.getName().startsWith(FACTORY_CODE + FILE_NAME_AF_WX)
							|| pathname.getName().startsWith(FACTORY_CODE + FILE_NAME_WE_WX)
							|| pathname.getName().startsWith(FACTORY_CODE + FILE_NAME_ENG_WX)
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
	
	
	public JitExceService getService() {
		return service;
	}

	public void setService(JitExceService service) {
		this.service = service;
	}
	
	
}
