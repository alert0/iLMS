package com.hanthink.sw.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwDemandForecastDao;
import com.hanthink.sw.model.SwDemandForecastModel;
import com.hanthink.util.constant.Constant;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * 
* <p>Title: SwAnnViewDaoImpl</p>  
* <p>Description:公告查看 </p>  
* @author luoxq  
* @date 2018年10月16日 下午4:19:42
 */
@Repository
public class SwDemandForecastDaoImpl extends MyBatisDaoImpl<String, SwDemandForecastModel>
implements SwDemandForecastDao{

	@Override
	public String getNamespace() {
		return SwDemandForecastModel.class.getName();
	}

	/**
	 * 
	 * <p>Title: queryJisoDemandPage</p>  
	 * <p>Description: 预测数据界面分页查询</p>  
	 * @param model
	 * @param p
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月16日 下午6:49:51
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwDemandForecastModel> queryJisoDemandPage(SwDemandForecastModel model, DefaultPage p) {
		return (PageList<SwDemandForecastModel>) this.getList("queryJisoDemandPage", model, p);
	}

	/**
	 * 
	 * <p>Title: publishDemandForecast</p>  
	 * <p>Description: 预测数据，发布功能</p>  
	 * @param id  
	 * @authoer luoxq
	 * @time 2018年10月17日 下午2:47:30
	 */
	@Override
	public void publishDemandForecast(SwDemandForecastModel model) {
//		SwDemandForecastModel model = new SwDemandForecastModel();
//		model.setId(id);
		model.setReleaseStatus(Constant.SW_RELEASE_STATUS_YES); //修改发布状态为已发布
		this.updateByKey("publishDemandForecast", model);
	}

	/**
	 * 
	 * <p>Title: feedbackDemandForecast</p>  
	 * <p>Description: 预测数据界面，反馈功能</p>  
	 * @param id  
	 * @authoer luoxq
	 * @time 2018年10月17日 下午3:04:15
	 */
	@Override
	public void feedbackDemandForecast(String[] ids, SwDemandForecastModel model){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("model", model);
		this.updateByKey("feedbackDemandForecast", map);
	}

	/**
	 * 
	 * <p>Title: querySwDemandForecastByKey</p>
	 * <p>Description: 预测数据界面，导出功能</p>  
	 * @param model
	 * @return  
	 * @authoer luoxq
	 * @time 2018年10月17日 下午3:30:53
	 */
	@Override
	public List<SwDemandForecastModel> querySwDemandForecastByKey(SwDemandForecastModel model) {
		
		return (List<SwDemandForecastModel>) this.getList("querySwDemandForecastByKey", model);
	}
	
	/**
	 * 分页查询需求预测数据导出的数据
	 * @param startMonthStr 开始月份 如 2019-05 不能为空
	 * @param endMonthStr 结束月份 如2019-07 允许为空，为空时默认三个月区间
	 * @param supplierNo 供应商代码 允许为空
	 * @param p 分页对象
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月12日 下午5:25:57
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryDemandForecastExportDataByPage(String startMonthStr, String endMonthStr,
			SwDemandForecastModel model, DefaultPage p) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		SimpleDateFormat sdfym = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfHym = new SimpleDateFormat("yyMM");
		SimpleDateFormat sdfHymd = new SimpleDateFormat("yyMM_dd");
		
		Calendar cal = Calendar.getInstance();
		String smonth = startMonthStr;
		String emonth = endMonthStr;
		String sday = smonth + "-01";
		String eday = null;
		Date smonthdate = null;
		Date emonthdate = null;
		Date sdaydate = null;
		Date edaydate = null;
		
		try {
			smonthdate = sdfym.parse(smonth);
		} catch (Exception e) {
			e.printStackTrace();
			new RuntimeException("日期格式错误");
		}
		if(null == emonth){
			cal.setTime(smonthdate);
			cal.add(Calendar.MONTH, 2);
			emonth = sdfym.format(cal.getTime());
		}
		try {
			emonthdate = sdfym.parse(emonth);
		} catch (Exception e) {
			e.printStackTrace();
			new RuntimeException("日期格式错误");
		}
		cal.setTime(emonthdate);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		eday = sdfymd.format(cal.getTime());
		try {
			sdaydate = sdfymd.parse(sday);
			edaydate = sdfymd.parse(eday);
		} catch (ParseException e) {
			e.printStackTrace();
			new RuntimeException("日期格式错误");
		}
		
		//月份
		StringBuffer sbfMonthSql = new StringBuffer();
		StringBuffer sbfMonthSqlB = new StringBuffer();
        Date curMonth = smonthdate;
		do{
			String columnname = "MONTH" + sdfHym.format(curMonth); //MONTH1905
			sbfMonthSql.append(" B.");
			sbfMonthSql.append(columnname);
			sbfMonthSql.append(", ");
			
			sbfMonthSqlB.append(" SUM(DECODE(YEAR_MONTH, '");
			sbfMonthSqlB.append(sdfym.format(curMonth));
			sbfMonthSqlB.append("', MONTH_TTL, 0)) ");
			sbfMonthSqlB.append(columnname);
			sbfMonthSqlB.append(", ");
			
			cal.setTime(curMonth);
			cal.add(Calendar.MONTH, 1);
			curMonth = cal.getTime();
		}while(curMonth.getTime() <= emonthdate.getTime());
        
        //日明细
		StringBuffer sbfDaySql = new StringBuffer();
		StringBuffer sbfDaySqlC = new StringBuffer();
		Date curDay = sdaydate;
		do{
			String columnname = "DAY" + sdfHymd.format(curDay); //DAY1905_01
			sbfDaySql.append(" C.");
			sbfDaySql.append(columnname);
			sbfDaySql.append(", ");
			
			sbfDaySqlC.append(" SUM(DECODE(YEAR_MONTH_DAY, '");
			sbfDaySqlC.append(sdfymd.format(curDay));
			sbfDaySqlC.append("', MONTH_TTL)) ");
			sbfDaySqlC.append(columnname);
			sbfDaySqlC.append(", ");
			
			cal.setTime(curDay);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			curDay = cal.getTime();
		}while(curDay.getTime() <= edaydate.getTime());
		
		
		param.put("startDateStr", sday);
		param.put("endDateStr", eday);
//		param.put("supplierNo", supplierNo);
//		param.put("releaseStatus", releaseStatus);
		param.put("model", model);
		param.put("monthSql", sbfMonthSql.toString());
		param.put("daySql", sbfDaySql.toString());
		param.put("monthSqlB", sbfMonthSqlB.toString());
		param.put("daySqlC", sbfDaySqlC.toString());
		
//		param.put("releasStatus", releasStatus);
		return this.getByKey("queryDemandForecastExportData", param, p);  
	}

	/**
	 * 
	 * @Description: 获取查询条件进行判断
	 * @param @param model
	 * @param @return   
	 * @return SwDemandForecastModel  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月13日 上午10:24:49
	 */
	@Override
	public SwDemandForecastModel getUserType(SwDemandForecastModel model) {
		
		return this.sqlSessionTemplate.selectOne(getNamespace()+".getUserType",model);
	}

	@Override
	public List<SwDemandForecastModel> getVersion(Map<String, String> map) {

		return this.getByKey("getVersion", map);
	}

	
	@Override
	public List<SwDemandForecastModel> getDefaultVersion(Map<String, String> map) {

		return this.getByKey("getDefaultVersion", map);
	}

	@Override
	public PageList<SwDemandForecastModel> queryDemandWeekPage(SwDemandForecastModel model, DefaultPage p) {
		
		return (PageList<SwDemandForecastModel>) this.getByKey("queryDemandWeekPage", model, p);
	}

	@Override
	public String getObjWeekByVersion(String version) {
		
		return this.sqlSessionTemplate.selectOne("getObjWeekByVersion", version);
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public PageList<Map<String, Object>> queryDemandForecastWeekExportDataByPage(String startMonthStr,
			String endMonthStr, SwDemandForecastModel model, DefaultPage page) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		SimpleDateFormat sdfym = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfHym = new SimpleDateFormat("yyMM");
		SimpleDateFormat sdfHymd = new SimpleDateFormat("yyMM_dd");
		
		Calendar cal = Calendar.getInstance();
		String smonth = startMonthStr;
		String emonth = endMonthStr;
		String sday = smonth ;
		String eday = null;
		Date smonthdate = null;
		Date emonthdate = null;
		Date sdaydate = null;
		Date edaydate = null;
		
		try {
			smonthdate = sdfym.parse(smonth);
		} catch (Exception e) {
			e.printStackTrace();
			new RuntimeException("日期格式错误");
		}
		if(null == emonth){
			cal.setTime(smonthdate);
			cal.add(Calendar.MONTH, 2);
			emonth = sdfymd.format(cal.getTime());
		}
		try {
			emonthdate = sdfymd.parse(emonth);
		} catch (Exception e) {
			e.printStackTrace();
			new RuntimeException("日期格式错误");
		}
		cal.setTime(emonthdate);
//		cal.add(Calendar.MONTH, 1);
//		cal.add(Calendar.DATE, 14);
		eday = sdfymd.format(cal.getTime());
		try {
			sdaydate = sdfymd.parse(sday);
			edaydate = sdfymd.parse(eday);
		} catch (ParseException e) {
			e.printStackTrace();
			new RuntimeException("日期格式错误");
		}
		
		//月份
		StringBuffer sbfMonthSql = new StringBuffer();
		StringBuffer sbfMonthSqlB = new StringBuffer();
        Date curMonth = smonthdate;
//		do{
//			String columnname = "MONTH" + sdfHym.format(curMonth); //MONTH1905
//			sbfMonthSql.append(" B.");
//			sbfMonthSql.append(columnname);
//			sbfMonthSql.append(", ");
//			
//			sbfMonthSqlB.append(" SUM(DECODE(YEAR_MONTH, '");
//			sbfMonthSqlB.append(sdfym.format(curMonth));
//			sbfMonthSqlB.append("', MONTH_TTL, 0)) ");
//			sbfMonthSqlB.append(columnname);
//			sbfMonthSqlB.append(", ");
//			
//			cal.setTime(curMonth);
//			cal.add(Calendar.MONTH, 1);
//			curMonth = cal.getTime();
//		}while(curMonth.getTime() <= emonthdate.getTime());
        
        //日明细
		StringBuffer sbfDaySql = new StringBuffer();
		StringBuffer sbfDaySqlC = new StringBuffer();
		Date curDay = sdaydate;
		do{
			String columnname = "DAY" + sdfHymd.format(curDay); //DAY1905_01
			sbfDaySql.append(" C.");
			sbfDaySql.append(columnname);
			sbfDaySql.append(", ");
			
			sbfDaySqlC.append(" SUM(DECODE(YEAR_MONTH_DAY, '");
			sbfDaySqlC.append(sdfymd.format(curDay));
			sbfDaySqlC.append("', MONTH_TTL)) ");
			sbfDaySqlC.append(columnname);
			sbfDaySqlC.append(", ");
			
			cal.setTime(curDay);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			curDay = cal.getTime();
		}while(curDay.getTime() <= edaydate.getTime());
		
		
		param.put("startDateStr", sday);
		param.put("endDateStr", eday);
//		param.put("supplierNo", supplierNo);
//		param.put("releaseStatus", releaseStatus);
		param.put("model", model);
//		param.put("monthSql", sbfMonthSql.toString());
		param.put("daySql", sbfDaySql.toString());
		param.put("monthSqlB", sbfMonthSqlB.toString());
		param.put("daySqlC", sbfDaySqlC.toString());
		
//		int dayNum = edaydate.getDay()-curDay.getDay();
//		int weekNum = dayNum / 7 +1;
//		String W = "W";
////		int i = 1;
//		StringBuffer WiSql = new StringBuffer();
//		StringBuffer WiOrderQtySql = new StringBuffer();
//		for (int i = 1; i <= weekNum; i++) {
//		
//			String SWSql= "LEFT JOIN (\r\n" + 
//				"		             \r\n" + 
//				"		             SELECT SUM(A.ORDER_QTY) ORDER_QTY,\r\n" + 
//				"		                     A.PART_NO,\r\n" + 
//				"		                     max(a.plan_delivery),\r\n" + 
//				"		                     A.FACTORY_CODE,\r\n" + 
//				"		                     A.VERSION,\r\n" + 
//				"		                     A.PHASE,\r\n" + 
//				"		                     A.SUPPLIER_NO\r\n" + 
//				"		               FROM MM_SW_DEMAND_FORECAST A\r\n" + 
//				"		               \r\n" + 
//				"		               LEFT JOIN (select trunc(to_date(SUBSTR(MAX(A.OBJ_WEEK), 0, 10),'yyyy-mm-dd') ,'d') + 1 \r\n" + 
//				"					  		FIRST_WEEK from MM_SW_DEMAND_FORECAST A ) B \r\n" + 
//				"			            ON 1=1\r\n" + 
//				"                       WHERE A.FORE_TYPE = 2\r\n" + 
//				"                       AND B.FIRST_WEEK &lt;=\r\n" + 
//				"                           A.PLAN_DELIVERY " +(i-1)*7+ "\r\n" + 
//				"                       AND B.FIRST_WEEK + " +i*7+ " &gt;\r\n" + 
//				"                           A.PLAN_DELIVERY\r\n" + 
//				"                           \r\n" + 
//				"		              GROUP BY A.PART_NO， A.FACTORY_CODE,\r\n" + 
//				"		                        A.VERSION,\r\n" + 
//				"		                        A.PHASE,\r\n" + 
//				"		                        A.SUPPLIER_NO)" + W+i+"\r\n" + 
//				"		    ON A.VERSION = W1.VERSION\r\n" + 
//				"		   AND A.FACTORY_CODE = " + W+i+ ".FACTORY_CODE\r\n" + 
//				"		   AND A.PART_NO = " + W+i+".PART_NO\r\n" + 
//				"		   AND A.SUPPLIER_NO = "+W+i+".SUPPLIER_NO\r\n" + 
//				"		   AND A.PHASE = " + W+i+".PHASE";
//			
//			WiSql.append(SWSql);
//			WiOrderQtySql.append(W+i+ ".ORDER_QTY WEEK_ONE,");
//		}
		
//		param.put("releasStatus", releasStatus);
//		param.put("WiSql", WiSql);
//		param.put("WiOrderQtySql", WiOrderQtySql);
		return this.getByKey("queryDemandForecastWeekExportData", param, page);  
	}

	@Override
	public SwDemandForecastModel getMinAndMaxDate(Map<String, Object> map) {
		
		return (SwDemandForecastModel) this.sqlSessionTemplate.selectOne("getMinAndMaxDate", map);
	}
	

}
