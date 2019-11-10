package com.hanthink.inv.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.inv.dao.PartMaintenanceManagerDao;
import com.hanthink.inv.manager.PartMaintenanceManager;
import com.hanthink.inv.model.PartMainTenanance;
import com.hanthink.util.excel.ExcelUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;
import com.mysql.jdbc.StringUtils;

@Service("PartMainTenanance")
public class PartMaintenanceManagerImpl extends AbstractManagerImpl<String, PartMainTenanance> implements PartMaintenanceManager{
	
	
	
	@Resource
	private PartMaintenanceManagerDao partMaintenanceManagerDao;
	/**
	 * 分页查询
	 *
	 * @param model
	 * @param p
	 * @return
	 * 李兴辉
	 */
	@Override
	public PageList<PartMainTenanance> queryPartMaintenanceForPage(
			PartMainTenanance model, DefaultPage p) {
		return partMaintenanceManagerDao.querypartMaintenanceForPage(model,p);
	}

	
	@Override
	protected Dao<String, PartMainTenanance> getDao() {
		return partMaintenanceManagerDao;
	}
	/**
	 * 查询属地零件作为导出数据
	 *
	 * @param model
	 * @return
	 * 李兴辉
	 */
	@Override
	public List<PartMainTenanance> queryexportList(PartMainTenanance model) {
		return  partMaintenanceManagerDao.queryexportList(model);
	}

	/**
	 * 查询导入的临时数据
	 * @param model
	 * @return
	 * 李兴辉
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PartMainTenanance> queryPartMaintenanceImport(PartMainTenanance model) {
		return (List<PartMainTenanance>) partMaintenanceManagerDao.queryPartMaintenanceImport(model);
	}
	
	
	/**
	 * excel数据导入到临时表
	 *
	 * @param file
	 * @return
	 * 李兴辉
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> importPartMaintenanceTemp(MultipartFile file) {
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		String console = "";
		if(file == null || file.isEmpty()){
			result = false;
			console = "文件为空！";
			throw new RuntimeException(console);
		}
		String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
//		if (!"零件属地维护导入模板".equals(fileName)) {
//			rtnMap.put("result", false);
//			rtnMap.put("console", "导入模板错误，请校验");
//			return rtnMap;
//		};
		//读取Excel数据
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String[] columns = {"oldUnloadPort","oldSupplierNo", "oldPartNo", "oldReparePerson",
				"oldCarpool","oldStorage", "oldDistriPerson", "oldLocation", "oldStationCode",
				"oldShelfNo", "oldLocationNum",	 "oldDeptNo","newUnloadPort", "newSupplierNo", "newPartNo", "newReparePerson","newCarpool",
				"newStorage", "newDistriPerson", "newLocation","newStationCode", "newShelfNo",
				"newLocationNum","newDeptNo","operationType","modelCode","effStart","effEnd"};	
		List<PartMainTenanance> importList = null;
		try {
			if(ExcelUtil.EXCEL_XLS.equals(fileExt.toLowerCase())){
				importList = (List<PartMainTenanance>) ExcelUtil.importExcelXLS(new PartMainTenanance(), file.getInputStream(), columns, 2, 1);
			}else if(ExcelUtil.EXCEL_XLSX.equals(fileExt.toLowerCase())){
				importList = (List<PartMainTenanance>) ExcelUtil.importExcelXLSX(new PartMainTenanance(), file.getInputStream(), columns, 2, 1);
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
		for(PartMainTenanance m : importList){
			PartMainTenanance.checkImportData(m);
			IUser currentUser = ContextUtil.getCurrentUser();
			m.setFactory(currentUser.getCurFactoryCode());
			if ( "新增".equals(m.getOperationType())) {
				List<PartMainTenanance> warCode = partMaintenanceManagerDao.getWarCode(m);
				if (warCode != null && warCode.size() == 1) {
					m.setWareCode(warCode.get(0).getWareCode());
				} else if (warCode != null && warCode.size() > 1){
					m.setCheckFlag(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
					m.setCheckResult("卸货口对应的仓库号不唯一");
				} else {
					m.setCheckFlag(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
					m.setCheckResult("卸货口不存在");
				}
			}
		}
		
		for (int i = 0; i < importList.size(); i++) {

			String operationType = importList.get(i).getOperationType();//操作类型
			String checkFlag = importList.get(i).getCheckFlag();
			String newPartNo = importList.get(i).getNewPartNo();//零件号
			String newUnloadPort = importList.get(i).getNewUnloadPort();//卸货口
			String newReparePerson = importList.get(i).getNewReparePerson();//被货工程
			String newCarpool = importList.get(i).getNewCarpool();//台车
			String newStorage = importList.get(i).getNewStorage();//物流库地址
			String newDistriPerson = importList.get(i).getNewDistriPerson();//配送工程
			String newLocation = importList.get(i).getNewLocation();//配送地址
			String newShelfNo = importList.get(i).getNewShelfNo();//货架号
			String newLocationNum = importList.get(i).getNewLocationNum();//工程深度
			String factory = importList.get(i).getFactory();//工厂代码
			String wareCode = importList.get(i).getWareCode();//仓库号
			String newStationCode = importList.get(i).getNewStationCode();//工位
		    String newSupplierNo = importList.get(i).getNewSupplierNo();//供应商代码
			if (ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS.equals(checkFlag) && ("新增".equals(operationType) || "移动".equals(operationType)))  {
				for (int j = i+ 1; j < importList.size(); j++) {
					String operationTypes = importList.get(j).getOperationType();//操作类型
					String checkFlags = importList.get(j).getCheckFlag();
					String newPartNos = importList.get(j).getNewPartNo();//零件号
					String newUnloadPorts = importList.get(j).getNewUnloadPort();//卸货口
					String newReparePersons = importList.get(j).getNewReparePerson();//被货工程
					String newCarpools = importList.get(j).getNewCarpool();//台车
					String newStorages = importList.get(j).getNewStorage();//物流库地址
					String newDistriPersons = importList.get(j).getNewDistriPerson();//配送工程
					String newLocations = importList.get(j).getNewLocation();//配送地址
					String newShelfNos = importList.get(j).getNewShelfNo();//货架号
					String newLocationNums = importList.get(j).getNewLocationNum();//工程深度
					String factorys = importList.get(j).getFactory();//工厂代码
					String wareCodes = importList.get(j).getWareCode();//仓库号
					String newStationCodes = importList.get(j).getNewStationCode();//工位
					String newSupplierNos = importList.get(j).getNewSupplierNo();//供应商代码
					if (ExcelUtil.EXCEL_IMPCKRESULT_SUCCESS.equals(checkFlags) && ("新增".equals(operationTypes) || "移动".equals(operationTypes))) {
//						根据卸货口获取仓库代码，同一零件号+仓库代码只有一个卸货口。
//						根据卸货口获取仓库代码，同一零件号+仓库代码只有一个备货工程。
//						根据卸货口获取仓库代码，同一零件号+仓库代码只有一个台车号。
//						根据卸货口获取仓库代码，同一零件号+仓库代码只有一个物流库地址。
//						同一零件号+工位只有一个配送工程。
//						同一货架号只有一个配送地址。
//						同一零件号+工位只有一个货架号。
//						同一工厂代码+配送地址只有一个工程深度。
						StringBuffer message = new StringBuffer();
						if (newPartNo == null || wareCode == null || newStationCode == null || factory == null || newCarpool == null
								|| newShelfNo == null || newLocation == null || newLocationNum == null) {
							
						} else {
							//PART_NO, SUPPLIER_NO, WARE_CODE, STATION_CODE, FACTORY_CODE
							if (newPartNo.equals(newPartNos) && wareCode.equals(wareCodes) && 
									newStationCodes.equals(newStationCode) && factorys.equals(factory)
									&& newSupplierNos.equals(newSupplierNo)) {
								message.append("存在相同的零件号，供应商，仓库，工位号，工厂数据；");
							}
							if (newPartNo.equals(newPartNos) && wareCode.equals(wareCodes)
									&& !newUnloadPort.equals(newUnloadPorts)) {
								message.append("同一零件号+仓库代码只能有一个卸货口；");
							}
							
							if (newPartNo.equals(newPartNos) && wareCode.equals(wareCodes)
									&& !newReparePerson.equals(newReparePersons)) {
								message.append("同一零件号+仓库代码只能有一个备货工程；");
							}
							
							if (newPartNo.equals(newPartNos) && wareCode.equals(wareCodes)
									&& !newCarpool.equals(newCarpools)) {
								message.append( "同一零件号+仓库代码只能有一个台车号；");
							}
							
							if (newPartNo.equals(newPartNos) && wareCode.equals(wareCodes)
									&& !newStorage.equals(newStorages)) {
								message.append("同一零件号+仓库代码只能有一个物流库地址；");
							}
							
							if (newPartNo.equals(newPartNos) && newStationCode.equals(newStationCodes)
									&& !newDistriPerson.equals(newDistriPersons)) {
								message.append("同一零件号+工位只能有一个配送工程；");
							}
							
							if (newShelfNo.equals(newShelfNos) && !newLocation.equals(newLocations)) {
								message.append("同一货架号只能有一个配送地址；");
							}
							
							if (newPartNo.equals(newPartNos) && newStationCode.equals(newStationCodes)
									&& !newShelfNo.equals(newShelfNos)) {
								message.append("同一零件号+工位只能有一个货架号；");
							}
							
							if (factory.equals(factorys) && newLocation.equals(newLocations)
									&& !newLocationNum.equals(newLocationNums) ) {
								message.append( "同一工厂代码+配送地址只能有一个工程深度；");
							}
							
							if (!StringUtils.isNullOrEmpty(message.toString()) ){
								importList.get(j).setCheckFlag(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
//								String checkResult = importList.get(j).getCheckResult() !=null ? 
//										importList.get(j).getCheckResult() : "";
//										String checkResult2 = importList.get(i).getCheckResult() != null ?
//												importList.get(i).getCheckResult() : "";
								importList.get(j).setCheckResult("EXCEL导入的数据中：" + message.toString());
								importList.get(i).setCheckFlag(ExcelUtil.EXCEL_IMPCKRESULT_ERROR);
								importList.get(i).setCheckResult( "EXCEL导入的数据中：" + message.toString());
							}
						}

					}
					
				}
			}
		}
		//导入数据写入到临时表
		partMaintenanceManagerDao.importPartMaintenanceTemp(importList);
		
		//调用存储过程等检查数据准确性
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		partMaintenanceManagerDao.checkImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		if(!result && StringUtil.isEmpty(console)){
			console = String.valueOf(ckParamMap.get("errorMsg"));
		}		
		//临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("console", console);
		return rtnMap;
	}

	/**
	 *导入确认
	 *
	 * @return
	 * 李兴辉
	 */
	@Override
	public  Map<String,Object>  PartMaintenanceImportData() {
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		Boolean result = true;
		Map<String, String> ckParamMap = new HashMap<String, String>();
		ckParamMap.put("errorFlag", "");
		ckParamMap.put("errorMsg", "");
		partMaintenanceManagerDao.PartMaintenanceImportData(ckParamMap);
		result = "0".equals(String.valueOf(ckParamMap.get("errorFlag")));
		String message = String.valueOf(ckParamMap.get("errorMsg"));
		//临时导入情况返回
		rtnMap.put("result", result);
		rtnMap.put("message", message);
		return rtnMap;
		
	}

	/**
	 * 导入的临时数据查询
	 *
	 * @param model
	 * @param page
	 * @return
	 * 李兴辉
	 */
	@Override
	public PageList<PartMainTenanance> queryPartMaintenanceTemp(PartMainTenanance model, DefaultPage page) {
		return partMaintenanceManagerDao.queryPartMaintenanceTemp(model,page);
	}

	/**
	 * 导入的临时数据用于导出
	 *
	 * @param model
	 * @return
	 * 李兴辉
	 */
	@Override
	public List<PartMainTenanance> queryPartMaintenanceTemp(PartMainTenanance model) {
		return partMaintenanceManagerDao.queryPartMaintenanceTemp(model);
	}


	/**
	 * 移除导入 的临时数据
	 */
	@Override
	public int removePartMaintenanceTemp() {
		return partMaintenanceManagerDao.removePartMaintenanceTemp();
	}


	/**
	 * 查询验证不通过的数据是否存在
	 * @return
	 * Lxh
	 */
	@Override
	public int queryImportFailCount(PartMainTenanance m) {
		
		return partMaintenanceManagerDao.queryImportFailCount(m);
	}

	
	/**
	 * 零件数据维护查询货架标签打印信息
	 * @Description:   
	 * @param: @param model
	 * @param: @return    
	 * @return: PartMainTenanance   
	 * @author: dtp
	 * @date: 2019年1月19日
	 */
	@Override
	public PartMainTenanance queryInvShelfPrintInfo(PartMainTenanance model) {
		return partMaintenanceManagerDao.queryInvShelfPrintInfo(model);
	}

}
