/**
 * 
 */
package com.hanthink.gps.system.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.pub.vo.SystemParamVO;
import com.hanthink.gps.system.service.DailyCheckInfoService;
import com.hanthink.gps.system.vo.CheckDBObjectSpaceVO;
import com.hanthink.gps.system.vo.CheckProcedureTimeVO;
import com.hanthink.gps.system.vo.CheckTableRowNumVO;
import com.hanthink.gps.system.vo.CheckTableSpaceVO;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * 描述：每日点检邮件信息
 * @author chenyong
 * @date   2016-10-10
 */
public class DailyCheckInfoJob extends BaseJob{

	public static final String FACTORY_CODE="2000";
	private String TABLESPACE_NAME = "ILMSPEC"; //数据库表空间
	private String TABLESPACE_NAME_PORTAL = "ILMSPORTAL"; //信息共享平台数据库表空间
	private String USER_SEGMENTS = "10000000000"; //数据库对象占用bytes数量 10亿
	private String CHECK_TABLE_ROW_NUM = "1000000"; //表行数 100万
	private String DAILY_CHECK_INFO_BEAN_NAME = "dailyCheckInfoService";
	private DailyCheckInfoService  service;
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		
		
		if(null == service){
			service = (DailyCheckInfoService)SpringContextUtil.getBean(DAILY_CHECK_INFO_BEAN_NAME);
		}
		CheckTableSpaceVO tableSpaceName = new CheckTableSpaceVO();
		CheckTableSpaceVO tableSpaceName_PORTAL = new CheckTableSpaceVO();
		
		tableSpaceName.setTableSpaceName(TABLESPACE_NAME);
		tableSpaceName_PORTAL.setTableSpaceName(TABLESPACE_NAME_PORTAL);
		
		SystemParamVO vo = new SystemParamVO();
		vo.setFactory(FACTORY_CODE);
		vo.setParamCode(TABLESPACE_NAME);
		
		SystemParamVO vo_PORTAL = new SystemParamVO();
		vo_PORTAL.setFactory(FACTORY_CODE);
		vo_PORTAL.setParamCode(TABLESPACE_NAME_PORTAL);
		
		SystemParamVO paramVO = service.queryParamByParamCode(vo);
		if(null != paramVO && !StringUtil.isNullOrEmpty(paramVO.getParamVal())){
			tableSpaceName.setTableSpaceName(paramVO.getParamVal());
		}
		SystemParamVO paramVO_PORTAL = service.queryParamByParamCode(vo_PORTAL);
		if(null != paramVO_PORTAL && !StringUtil.isNullOrEmpty(paramVO_PORTAL.getParamVal())){
			tableSpaceName_PORTAL.setTableSpaceName(paramVO_PORTAL.getParamVal());
		}
		//获取统计数据库表空间信息
		List<CheckTableSpaceVO>  listTableSpaceInfo = service.checkTableSpace(tableSpaceName);
		List<CheckTableSpaceVO>  listTableSpaceInfo_PORTAL = service.checkTableSpace_PORTAL(tableSpaceName_PORTAL);
		
		//获取存储过程执行时间信息
		List<CheckProcedureTimeVO> listProcedureTimeInfo = service.checkProcedureTime();
		//List<CheckProcedureTimeVO> listProcedureTimeInfo_PORTAL = service.checkProcedureTime_PORTAL();

		CheckDBObjectSpaceVO userSegmentsVO = new CheckDBObjectSpaceVO();
		userSegmentsVO.setUseSpace(USER_SEGMENTS);
		vo.setParamCode(USER_SEGMENTS);
		SystemParamVO paramVO2 = service.queryParamByParamCode(vo);
		if(null != paramVO2 && !StringUtil.isNullOrEmpty(paramVO2.getParamVal())){
			if(null != Integer.valueOf(paramVO2.getParamVal()) && Integer.valueOf(paramVO2.getParamVal()) > 0){
				userSegmentsVO.setUseSpace(paramVO2.getParamVal());
			}
		}
		//获取数据库对象占用空间信息
		List<CheckDBObjectSpaceVO> listDBObjectSpaceInfo = service.checkDBObjectSpace(userSegmentsVO);
		List<CheckDBObjectSpaceVO> listDBObjectSpaceInfo_PORTAL = service.checkDBObjectSpace_PORTAL(userSegmentsVO);
	
		CheckTableRowNumVO checkTableRowNumVO = new CheckTableRowNumVO();
		checkTableRowNumVO.setRowNums(CHECK_TABLE_ROW_NUM);
		vo.setParamCode(CHECK_TABLE_ROW_NUM);
		SystemParamVO paramVO3 = service.queryParamByParamCode(vo);
		if(null != paramVO3 && !StringUtil.isNullOrEmpty(paramVO3.getParamVal())){
			checkTableRowNumVO.setRowNums(paramVO3.getParamVal());
		}
		//获取数据表记录行数信息
		List<CheckTableRowNumVO> listTableRowNumInfo = service.checkTableRowNum(checkTableRowNumVO);
		List<CheckTableRowNumVO> listTableRowNumInfo_PORTAL = service.checkTableRowNum_PORTAL(checkTableRowNumVO);
		
		
		//查询发送人信息
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有每日点检提醒的人员");
			return;
		}
		
		//拼接邮件信息
		StringBuffer emailInfo = new StringBuffer();
		
		//拼接统计数据库表空间信息
		emailInfo.append("<table><thead><tr><td colspan='12'>厂内数据库表空间</td></tr>");
		emailInfo.append("<tr><td>表空间名</td><td>总空间容量MB</td><td>已用空间容量MB</td><td>可用空间容量MB</td><td>空间容量使用率%</td></tr></thead>");
		emailInfo.append("<tbody>");
		for(int i=0;i<listTableSpaceInfo.size();i++){
			if(i%2==0){
			  emailInfo.append("<tr class="+"jishu"+">");
			  emailInfo.append("<td>"+listTableSpaceInfo.get(i).getTableSpaceName()+"</td>");
			  emailInfo.append("<td>"+listTableSpaceInfo.get(i).getTotalSpace()+"</td>");
			  emailInfo.append("<td>"+listTableSpaceInfo.get(i).getUseSpace()+"</td>");
			  emailInfo.append("<td>"+listTableSpaceInfo.get(i).getRestSpace()+"</td>");
			  emailInfo.append("<td>"+listTableSpaceInfo.get(i).getPercent()+"</td>");
			  emailInfo.append("</tr>");
			}
			else{
				emailInfo.append("<tr>");
				  emailInfo.append("<td>"+listTableSpaceInfo.get(i).getTableSpaceName()+"</td>");
				  emailInfo.append("<td>"+listTableSpaceInfo.get(i).getTotalSpace()+"</td>");
				  emailInfo.append("<td>"+listTableSpaceInfo.get(i).getUseSpace()+"</td>");
				  emailInfo.append("<td>"+listTableSpaceInfo.get(i).getRestSpace()+"</td>");
				  emailInfo.append("<td>"+listTableSpaceInfo.get(i).getPercent()+"</td>");
				  emailInfo.append("</tr>");
			}
		}
		emailInfo.append("</tbody>");
		emailInfo.append("</table>");
		
		
		//拼接统计存储过程执行时间
		emailInfo.append("<table><thead><tr><td colspan='12'>厂内存储过程执行时间</td></tr>");
		emailInfo.append("<tr><td>推算代码</td><td>指摘范围</td><td>最大执行时间(s)</td><td>最小执行时间(s)</td></tr></thead>");
		emailInfo.append("<tbody>");
		for(int j=0;j<listProcedureTimeInfo.size();j++){
			if(j%2==0){
				emailInfo.append("<tr class="+"jishu"+">");
			    emailInfo.append("<td>"+listProcedureTimeInfo.get(j).getExecCode()+"</td>");
			    emailInfo.append("<td>"+listProcedureTimeInfo.get(j).getRange()+"</td>");
			    emailInfo.append("<td>"+listProcedureTimeInfo.get(j).getMaxExecTime()+"</td>");
			    emailInfo.append("<td>"+listProcedureTimeInfo.get(j).getMinExecTime()+"</td>");
			    emailInfo.append("</tr>");
			}else{
				emailInfo.append("<tr>");
			    emailInfo.append("<td>"+listProcedureTimeInfo.get(j).getExecCode()+"</td>");
			    emailInfo.append("<td>"+listProcedureTimeInfo.get(j).getRange()+"</td>");
			    emailInfo.append("<td>"+listProcedureTimeInfo.get(j).getMaxExecTime()+"</td>");
			    emailInfo.append("<td>"+listProcedureTimeInfo.get(j).getMinExecTime()+"</td>");
			    emailInfo.append("</tr>");
			}
		}
		emailInfo.append("</tbody>");
		emailInfo.append("</table>");
		
		//拼接统计数据库对象占用空间 
		emailInfo.append("<table><thead><tr><td colspan='12'>厂内数据库对象占用空间大小(bytes>"+ userSegmentsVO.getUseSpace() +")</td></tr>");
		emailInfo.append("<tr><td>数据库对象名</td><td>数据库对象类型</td><td>占用空间大小</td></tr></thead>");
		emailInfo.append("<tbody>");
		for(int m=0;m<listDBObjectSpaceInfo.size();m++){
			if(m%2==0){
			   emailInfo.append("<tr class="+"jishu"+">");
			   emailInfo.append("<td>"+listDBObjectSpaceInfo.get(m).getSegnName()+"</td>");
			   emailInfo.append("<td>"+listDBObjectSpaceInfo.get(m).getSegnType()+"</td>");
			   emailInfo.append("<td>"+listDBObjectSpaceInfo.get(m).getUseSpace()+"</td>");
			   emailInfo.append("</tr>");
			}else{
			   emailInfo.append("<tr>");
			   emailInfo.append("<td>"+listDBObjectSpaceInfo.get(m).getSegnName()+"</td>");
			   emailInfo.append("<td>"+listDBObjectSpaceInfo.get(m).getSegnType()+"</td>");
			   emailInfo.append("<td>"+listDBObjectSpaceInfo.get(m).getUseSpace()+"</td>");
			   emailInfo.append("</tr>");
			}
		}
		emailInfo.append("</tbody>");
		emailInfo.append("</table>");
		
		//拼接数据表记录行数信息
		emailInfo.append("<table><thead><tr><td colspan='12'>厂内数据表记录行数信息(行数>"+ checkTableRowNumVO.getRowNums() +")</td></tr>");
		emailInfo.append("<tr><td>表名</td><td>数据行数</td></tr></thead>");
		emailInfo.append("<tbody>");
		for(int n=0;n<listTableRowNumInfo.size();n++){
			if(n%2==0){
				emailInfo.append("<tr class="+"jishu"+">");
			    emailInfo.append("<td>"+listTableRowNumInfo.get(n).getTableName()+"</td>");
			    emailInfo.append("<td>"+listTableRowNumInfo.get(n).getRowNums()+"</td>");
			    emailInfo.append("</tr>");
			}else{
				emailInfo.append("<tr>");
				emailInfo.append("<td>"+listTableRowNumInfo.get(n).getTableName()+"</td>");
				emailInfo.append("<td>"+listTableRowNumInfo.get(n).getRowNums()+"</td>");
				emailInfo.append("</tr>");
			}
		}
		emailInfo.append("</tbody>");
		emailInfo.append("</table><br>");
		
		//设置邮件下边表示
//		Date currentTime = new Date();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
//		String dateString = formatter.format(currentTime);
//		emailInfo.append("<span>Copyright &nbsp;(c)&nbsp;  2008-/"+dateString+"&nbsp;  GAC MOTOR"+"</span><br>");
		
		
		//**************************************************************************************************
		//**************************************************************************************************
		//**************************************************************************************************
		//**************************************************************************************************
		//**************************************************************************************************
		
		//拼接统计数据库表空间信息
		emailInfo.append("<table><thead><tr><td colspan='12'>信息共享平台数据库表空间</td></tr>");
		emailInfo.append("<tr><td>表空间名</td><td>总空间容量MB</td><td>已用空间容量MB</td><td>可用空间容量MB</td><td>空间容量使用率%</td></tr></thead>");
		emailInfo.append("<tbody>");
		for(int i=0;i<listTableSpaceInfo_PORTAL.size();i++){
			if(i%2==0){
			  emailInfo.append("<tr class="+"jishu"+">");
			  emailInfo.append("<td>"+listTableSpaceInfo_PORTAL.get(i).getTableSpaceName()+"</td>");
			  emailInfo.append("<td>"+listTableSpaceInfo_PORTAL.get(i).getTotalSpace()+"</td>");
			  emailInfo.append("<td>"+listTableSpaceInfo_PORTAL.get(i).getUseSpace()+"</td>");
			  emailInfo.append("<td>"+listTableSpaceInfo_PORTAL.get(i).getRestSpace()+"</td>");
			  emailInfo.append("<td>"+listTableSpaceInfo_PORTAL.get(i).getPercent()+"</td>");
			  emailInfo.append("</tr>");
			}
			else{
				emailInfo.append("<tr>");
				  emailInfo.append("<td>"+listTableSpaceInfo_PORTAL.get(i).getTableSpaceName()+"</td>");
				  emailInfo.append("<td>"+listTableSpaceInfo_PORTAL.get(i).getTotalSpace()+"</td>");
				  emailInfo.append("<td>"+listTableSpaceInfo_PORTAL.get(i).getUseSpace()+"</td>");
				  emailInfo.append("<td>"+listTableSpaceInfo_PORTAL.get(i).getRestSpace()+"</td>");
				  emailInfo.append("<td>"+listTableSpaceInfo_PORTAL.get(i).getPercent()+"</td>");
				  emailInfo.append("</tr>");
			}
		}
		emailInfo.append("</tbody>");
		emailInfo.append("</table>");
		
		
		//拼接统计存储过程执行时间
		/*emailInfo.append("<table><thead><tr><td colspan='12'>信息共享平台存储过程执行时间</td></tr>");
		emailInfo.append("<tr><td>推算代码</td><td>指摘范围</td><td>最大执行时间</td><td>最小执行时间</td></tr></thead>");
		emailInfo.append("<tbody>");
		for(int j=0;j<listProcedureTimeInfo_PORTAL.size();j++){
			if(j%2==0){
				emailInfo.append("<tr class="+"jishu"+">");
			    emailInfo.append("<td>"+listProcedureTimeInfo_PORTAL.get(j).getExecCode()+"</td>");
			    emailInfo.append("<td>"+listProcedureTimeInfo_PORTAL.get(j).getRange()+"</td>");
			    emailInfo.append("<td>"+listProcedureTimeInfo_PORTAL.get(j).getMaxExecTime()+"</td>");
			    emailInfo.append("<td>"+listProcedureTimeInfo_PORTAL.get(j).getMinExecTime()+"</td>");
			    emailInfo.append("</tr>");
			}else{
				emailInfo.append("<tr>");
			    emailInfo.append("<td>"+listProcedureTimeInfo_PORTAL.get(j).getExecCode()+"</td>");
			    emailInfo.append("<td>"+listProcedureTimeInfo_PORTAL.get(j).getRange()+"</td>");
			    emailInfo.append("<td>"+listProcedureTimeInfo_PORTAL.get(j).getMaxExecTime()+"</td>");
			    emailInfo.append("<td>"+listProcedureTimeInfo_PORTAL.get(j).getMinExecTime()+"</td>");
			    emailInfo.append("</tr>");
			}
		}
		emailInfo.append("</tbody>");
		emailInfo.append("</table>");
		*/
		//拼接统计数据库对象占用空间 
		emailInfo.append("<table><thead><tr><td colspan='12'>信息共享平台数据库对象占用空间大小(bytes>"+ userSegmentsVO.getUseSpace() +")</td></tr>");
		emailInfo.append("<tr><td>数据库对象名</td><td>数据库对象类型</td><td>占用空间大小</td></tr></thead>");
		emailInfo.append("<tbody>");
		for(int m=0;m<listDBObjectSpaceInfo_PORTAL.size();m++){
			if(m%2==0){
			   emailInfo.append("<tr class="+"jishu"+">");
			   emailInfo.append("<td>"+listDBObjectSpaceInfo_PORTAL.get(m).getSegnName()+"</td>");
			   emailInfo.append("<td>"+listDBObjectSpaceInfo_PORTAL.get(m).getSegnType()+"</td>");
			   emailInfo.append("<td>"+listDBObjectSpaceInfo_PORTAL.get(m).getUseSpace()+"</td>");
			   emailInfo.append("</tr>");
			}else{
			   emailInfo.append("<tr>");
			   emailInfo.append("<td>"+listDBObjectSpaceInfo_PORTAL.get(m).getSegnName()+"</td>");
			   emailInfo.append("<td>"+listDBObjectSpaceInfo_PORTAL.get(m).getSegnType()+"</td>");
			   emailInfo.append("<td>"+listDBObjectSpaceInfo_PORTAL.get(m).getUseSpace()+"</td>");
			   emailInfo.append("</tr>");
			}
		}
		emailInfo.append("</tbody>");
		emailInfo.append("</table>");
		
		//拼接数据表记录行数信息
		emailInfo.append("<table><thead><tr><td colspan='12'>信息共享平台数据表记录行数信息(行数>"+ checkTableRowNumVO.getRowNums() +")</td></tr>");
		emailInfo.append("<tr><td>表名</td><td>数据行数</td></tr></thead>");
		emailInfo.append("<tbody>");
		for(int n=0;n<listTableRowNumInfo_PORTAL.size();n++){
			if(n%2==0){
				emailInfo.append("<tr class="+"jishu"+">");
			    emailInfo.append("<td>"+listTableRowNumInfo_PORTAL.get(n).getTableName()+"</td>");
			    emailInfo.append("<td>"+listTableRowNumInfo_PORTAL.get(n).getRowNums()+"</td>");
			    emailInfo.append("</tr>");
			}else{
				emailInfo.append("<tr>");
				emailInfo.append("<td>"+listTableRowNumInfo_PORTAL.get(n).getTableName()+"</td>");
				emailInfo.append("<td>"+listTableRowNumInfo_PORTAL.get(n).getRowNums()+"</td>");
				emailInfo.append("</tr>");
			}
		}
		emailInfo.append("</tbody>");
		emailInfo.append("</table><br>");

		
		
		//发送邮件
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("info", emailInfo.toString());
		boolean sendFlg = MailUtil.sendMail("dailyCheck.ftl", 
				"每日点检", toArr, null, null, model, null, null);
		LogUtil.info("GACNE iLMS每日点检信息,发送状态："+sendFlg);
	}
	
	//get 和    set方法
	public DailyCheckInfoService getService() {
		return service;
	}
	public void setService(DailyCheckInfoService service) {
		this.service = service;
	}
}
