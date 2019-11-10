package com.hanthink.sw.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sw.dao.SwVentureForecastMonDao;
import com.hanthink.sw.model.SwVentureForecastModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Repository
public class SwVentureForecastMonDaoImpl extends MyBatisDaoImpl<String, SwVentureForecastModel>
implements SwVentureForecastMonDao{

	@Override
	public String getNamespace() {
		
		return SwVentureForecastModel.class.getName();
	}

	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		this.deleteByKey("deleteImportTempDataByUUIDMon", uuid);
	}

	@Override
	public void insertImportTempData(List<SwVentureForecastModel> importList) {
		this.insertBatchByKey("insertImportTempDataMon", importList);
	}

	@Override
	public void checkImportData(Map<String, String> ckParamMap) {
		this.sqlSessionTemplate.selectOne(getNamespace()+ ".checkImportDataMon", ckParamMap);
	}

	@Override
	public String queryIsImportFlag(String uuid) {
		
		return this.sqlSessionTemplate.selectOne(getNamespace()+ ".queryIsImportFlagMon", uuid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwVentureForecastModel> queryImportTempData(Map<String, String> paramMap, Page page) {
		
		return (PageList<SwVentureForecastModel>) this.getList("queryImportTempDataMon", paramMap, page);
	}

	@Override
	public List<SwVentureForecastModel> queryTempDataForExport(Map<String, String> paramMap) {
		
		return this.getByKey("queryImportTempDataMon", paramMap);
	}

	@Override
	public List<SwVentureForecastModel> queryForInsertList(Map<String, Object> paramMap) {
		
		return this.getByKey("queryForInsertListMon", paramMap);
	}

	@Override
	public void insertImportData(Map<String, Object> paramMap) {
		this.insertByKey("insertImportDataMon", paramMap);
	}

	@Override
	public void updateVentureForecastImportDataImpStatus(Map<String, Object> paramMap) {
		this.updateByKey("updateVentureForecastImportDataImpStatusMon", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwVentureForecastModel> queryVentureForePage(SwVentureForecastModel model, Page page) {
		
		return (PageList<SwVentureForecastModel>) this.getList("queryVentureForePageMon", model, page);
	}

	@Override
	public List<SwVentureForecastModel> selectVentureByVersion(SwVentureForecastModel model) {
		
		return this.getByKey("queryVentureForePageMon", model);
	}

	@Override
	public void deleteVentureVersion(SwVentureForecastModel model) {
		this.deleteByKey("deleteVentureVersionMon", model);
	}
	
	@Override
	public void deleteVentureVersionJv(SwVentureForecastModel model) {
		this.deleteByKey("deleteVentureVersionJvMon", model);
	}

	@Override
	public List<SwVentureForecastModel> exportForExcelModel(SwVentureForecastModel model) {
		
		return this.getByKey("queryVentureForePageMon", model);
	}

	@Override
	public int insertReleaseVersion(SwVentureForecastModel model) {
		
		return this.insertByKey("insertReleaseVersionMon", model);
	}

	@Override
	public SwVentureForecastModel selectDemandForecasetByTagVersion(SwVentureForecastModel model) {
		
		return this.sqlSessionTemplate.selectOne( getNamespace()+ ".selectDemandForecasetByTagVersionMon", model);
	}

	@Override
	public Integer selectIsReleaseVersion(SwVentureForecastModel model) {
		
		return this.sqlSessionTemplate.selectOne(getNamespace()+ ".selectIsReleaseVersionMon", model);
	}

	@Override
	public List<SwVentureForecastModel> getJvVersion(Map<String, String> map) {
		
		return this.getByKey("getJvVersionMon", map);
	}

	@Override
	public void insertVersion(Map<String, Object> paramMap) {
		this.insertByKey("insertVersionMon", paramMap);
	}

	@Override
	public List<SwVentureForecastModel> getErpVersion(Map<String, String> map) {
		
		return this.getByKey("getErpVersionMon", map);
	}


	@Override
	public void insertReleaseImpJv(SwVentureForecastModel model) {
		this.insertByKey("insertReleaseImpJvMon", model);
	}

	@Override
	public void insertReleaseImpErp(SwVentureForecastModel model) {
		this.insertByKey("insertReleaseImpErpMon", model);
	}
	

	@Override
	public void deleteReleaseImp(SwVentureForecastModel model) {
		this.deleteByKey("deleteReleaseImpMon", model);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<SwVentureForecastModel> queryTotalQty(SwVentureForecastModel model, Page page) {
		
		return (PageList<SwVentureForecastModel>) this.getList("queryTotalQtyMon", model, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryVentureForecastMonExportDataByPage(String startMonthStr,
			String endMonthStr, SwVentureForecastModel model, DefaultPage page) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
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
		

		return this.getByKey("queryVentureForecastMonExportDataByPage", param, page);  
	}

	@Override
	public SwVentureForecastModel getExportModeMsg(Map<String, Object> map) {
		
		return this.sqlSessionTemplate.selectOne(getNamespace()+ ".getExportModeMsgMon", map);
	}

	@Override
	public void insertForecastRecord(SwVentureForecastModel model) {
		this.insertByKey("insertForecastRecordMon", model);
	}

	@Override
	public void updateJvReleaseStatus(SwVentureForecastModel model) {
		this.updateByKey("updateJvReleaseStatusMon", model);
	}

	@Override
	public void updateErpReleaseStatus(SwVentureForecastModel model) {
		this.updateByKey("updateErpReleaseStatusMon", model);
	}

}
