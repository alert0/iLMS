package com.hanthink.sw.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sw.dao.SwVentureOrderDao;
import com.hanthink.sw.manager.SwVentureOrderMananger;
import com.hanthink.sw.model.SwVentureOrderModel;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.api.Page;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

@Service("SwVentureOrder")
public class SwVentureOrderManagerImpl extends AbstractManagerImpl<String, SwVentureOrderModel>
				implements SwVentureOrderMananger{
	
	@Resource
	private SwVentureOrderDao orderDao;
	
	@Override
	protected Dao<String, SwVentureOrderModel> getDao() {
		return orderDao;
	}

	@Override
	public PageList<SwVentureOrderModel> queryVentureOrderForPage(SwVentureOrderModel model, Page page) {
		List<SwVentureOrderModel> list = orderDao.queryVentureOrderForPage(model, page);
		return new PageList<SwVentureOrderModel>(list);
	}

	@Override
	public void deleteImportTempDataByUUID(String uuid) {
		orderDao.deleteImportTempDataByUUID(uuid);
	}

	@Override
	public Map<String, Object> importModel(MultipartFile file, String uuid, String ipAddr, IUser user) {
		Map<String,Object> rtnMap = new HashMap<String, Object>();
        Boolean result = true;
        String console = "";
        
        if(file == null || file.isEmpty()){
            result = false;
            console = "文件为空！";
            throw new RuntimeException(console);
        }
        
        //读取Excel数据
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String[] columns = {"jvPlace","orderType","unloadPort","supplierNo","supFactory","supplierName","orderNo",
        				"partNo","partShortNo","standardPackage","orderBox","orderQty","storage",
        				"location","arriveBatch","orderDate","supShipTime","supArriveTime","supRouteName",
        				"supCarBatch","gblShipTime","gblArriveTime","gblRouteName","gblCarBatch","arriveDate"};
        List<SwVentureOrderModel> importList = null;
        try {
            if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
                importList = (List<SwVentureOrderModel>) ExcelUtil.importExcelXLS(new SwVentureOrderModel(), 
                		file.getInputStream(), columns, 1, 0);
            }else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
                importList = (List<SwVentureOrderModel>) ExcelUtil.importExcelXLSX(new SwVentureOrderModel(), 
                		file.getInputStream(), columns, 1, 0);
            }else{
                result = false;
                console = "上传文件不是excel类型！";
                throw new RuntimeException(console);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            console = e.getMessage();
            throw new RuntimeException(console);
        }
        
        //数据导入初始化，格式初步检查
        for(SwVentureOrderModel m : importList){
            m.setUuid(uuid);
            m.setFactoryCode(user.getCurFactoryCode());
			m.setCreationUser(user.getAccount());
			m.setImpIp(ipAddr);
			m.setCheckInfo("");
			m.setCheckResult("1");
			m.setImportStatus(ExcelUtil.EXCEL_IMPSTATUS_NO);
        }
        
        //导入数据写入到临时表
        orderDao.insertImportTempData(importList);
        
        //调用存储过程等检查数据准确性
        Map<String, String> ckParamMap = new HashMap<String, String>();
        ckParamMap.put("uuid", uuid);
        ckParamMap.put("userName", ContextUtil.getCurrentUser().getAccount());
        ckParamMap.put("opeIp", ipAddr);
        ckParamMap.put("errorFlag", "");
        ckParamMap.put("errorMsg", "");
        orderDao.checkImportData(ckParamMap);
        result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
        if(!result && StringUtil.isEmpty(console)){
            console = String.valueOf(ckParamMap.get("errorMsg"));
        }
//        String flag = orderDao.queryIsImportFlag(uuid);
        //临时导入情况返回
        rtnMap.put("result", result);
        rtnMap.put("console", console);
//        rtnMap.put("flag", flag);
        return rtnMap;
	}

	@Override
	public PageList<SwVentureOrderModel> queryImportTempData(Map<String, String> paramMap, Page page) {
		
		return orderDao.queryImportTempData(paramMap,page);
	}

	@Override
	public List<SwVentureOrderModel> queryTempDataForExport(Map<String, String> paramMap) {
		
		return orderDao.queryTempDataForExport(paramMap);
	}

	@Override
	public void insertImportData(Map<String, Object> paramMap, String ipAddr) throws Exception {
		paramMap.put("IMPORT_STATUS_YES", ExcelUtil.EXCEL_IMPSTATUS_YES);
		paramMap.put("importStatus", ExcelUtil.EXCEL_IMPSTATUS_NO);

		try {
			List<SwVentureOrderModel> list = orderDao
					.queryForInsertList(paramMap);
			if (list.size() < 1) {
				throw new Exception("没有正确数据可导入或已全部导入");
			}
			/**
			 * 导入新增的方法
			 */
			orderDao.insertImportData(paramMap);

			// 更新临时数据导入状态
			orderDao.updateVentureOrderImportDataImpStatus(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误，请联系管理员");

		}
	}

	@Override
	public void deleteAllPuchaeIsNull(Map<String, Object> paramMap) throws Exception {
		//查询当前账号需要删除的订单
		List<SwVentureOrderModel> list = orderDao.queryForPuchaeIsNull(paramMap);
				
		if (list.size() <= 0) {
			return;
		}else {
			//记录删除日志信息
			
			//删除订单头信息
			orderDao.deleteAllHeader(list);
			//删除订单明细信息
			orderDao.deleteAllBody(list);
		}
	}
	/**
	 * 订单界面数据导出
	 * @param model
	 * @return
	 * @author zmj
	 * @date 2019年8月24日
	 */
	@Override
	public List<SwVentureOrderModel> queryVentureOrderForExport(SwVentureOrderModel model) {
		return orderDao.queryVentureOrderForExport(model);
	}

	@Override
	public void orederRelease(Map<String, Object> paramMap) {
		orderDao.orederRelease(paramMap);
	}

}
