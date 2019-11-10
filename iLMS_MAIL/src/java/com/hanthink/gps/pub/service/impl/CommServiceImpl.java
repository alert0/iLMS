package com.hanthink.gps.pub.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hanthink.gps.pub.dao.CommDao;
import com.hanthink.gps.pub.service.CommService;
import com.hanthink.gps.pub.vo.AogFactoryVo;
import com.hanthink.gps.pub.vo.CmbItemVO;
import com.hanthink.gps.pub.vo.FactoryParamItemVO;
import com.hanthink.gps.pub.vo.SysMessageVO;
import com.hanthink.gps.pub.vo.SystemParamVO;
import com.hanthink.gps.util.Constants;
import com.opensymphony.xwork2.ActionContext;

public class CommServiceImpl extends BaseServiceImpl implements CommService {
	
	private CommDao commDao;
	
	/**
	 * 取得下拉框的选项
	 * @param type 下拉框类型
	 * @param params 检索条件
	 * @return 下拉框的选项
	 */
	public List<?> queryCmbItems(String type,String param,String supplierNo){
		List<CmbItemVO> items = new ArrayList<CmbItemVO>();
		if(null != param) {
			param = param.trim();
		}
		
		 //定时器分组
		else if("uCode".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_Code");
		}
		
		// 广乘角色
		if("role".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_role");
		}
		
		//供应商角色(供应商、中转仓、外协仓)
		else if("supplierRole".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_supplierRole", param);
		}
		
		//角色类型
		else if("roleType".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_roleType");
		}
		
		
		//部门
		else if("department".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_department");
		}
		
		//结算方式
		else if("settlementstyle".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_settlementstyle");
		}
		
		//工厂
		else if("factory".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_factory");
		}
		
		//工厂
		else if("effState".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_effState");
		}
		//供应商组下拉框
		else if("supGroupId".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_supGroupId");
		}

		//厂内物流工作中心
		else if("workcenter".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_workcenter");
		}
		
		
		
		//机能区
		else if("functionArea".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_functionArea");
		}
		//机能区
		else if("kbCode".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_kbCode");
		}
		//同步类型下拉框
		else if("JIS_FLAG".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_jisFlag");
		}
		
		//车辆订单状态下拉框
		else if("VEH_ORDER_STATUS".equals(type)){
			return commDao.queryCmbItems("comm.select_vehOrderStatus");
		}
		
		//车辆生产阶段下拉框
		else if("PRO_PHASE".equals(type)){
			return commDao.queryCmbItems("comm.select_proPhase");
		}
		
		//是否发送ERP下拉框
		else if("IS_SENDTOERP".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_isSendToErp");
		}
		
		//是否自动打印下拉框
		else if("IS_AUTO_PRINT".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_isAutoPrint");
		}
		
		//打印机下拉框
		else if("MM_PRINTER".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_printer");
		}
		
		//MES收货状态下拉框
		else if("mesStatus".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_mesStatus");
		}
		
		//ERP收货状态下拉框
		else if("erpStatus".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_erpStatus");
		}
		
		//信息点下拉框
		else if("planCode".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_planCode");
		}
		
		//出货仓库
		else if("shipDepot".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_shipDepot");
		}
		//出货仓库类别
		else if("shipDepotType".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_shipDepotType");
		}
		
		//供应商代码下拉框
		else if("supplierNoCmb".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_supplierNo");
		}
		
		//订单类型下拉框
		else if("orderType".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_orderType");
		}
		
		//到货车间类别 下拉框
		else if("workcenterType".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_workcenterType");
		}
		
		//到货仓库
		else if("arrDepot".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_arrDepot");
		}
		//ERP到货仓库
		else if("erpArrDepot".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_erpArrDepot");
		}
		//包装类型
		else if("packageType".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_packageType");
		}
		
		//零脱批
		else if("isLtp".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_isLtp");
		}
		
		//是否加急下拉框
		else if("orderDiff".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_orderDiff");
		}
		
		//打印机所属分组
		else if("printerGroup".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_printerGroup");
		}
		//打印单据类型
		else if("billType".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_billType");
		}
		//订单打印状态
		else if("orderPrintState".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_jitOrderState");
		}
		//打印状态
		else if("printStatus".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_printStatus");
		}
		
		//零件组下拉框
		else if("MM_PartGroup".equals(type)){
			return commDao.queryCmbItems("comm.select_partGroup");
		}

		//公司代码
		else if("relativeId".equals(type)){
			
				// 供应商代码
			if(Constants.SUP_ROLE_TYPE_SUPPLIER.equals(param)){
				return commDao.queryCmbItems("comm.select_cmbitems_supplierNo");
				// 中转仓代码
			} else if (Constants.SUP_ROLE_TYPE_TRANSIT.equals(param)){
				return commDao.queryCmbItems("comm.select_cmbitems_transitNo");
				// 外协仓代码
			} else if (Constants.SUP_ROLE_TYPE_WX.equals(param)){
				return commDao.queryCmbItems("comm.select_cmbitems_outSourcingNo");
			}
		}
		// 存放仓库
		else if("depot".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_depot");
		}
		/*********************************订购模块下拉框**********************************************/
		 
		
		
//		// 零件单位
//		else if("partunit".equals(type)){
//			return commDao.queryCmbItems("comm.select_partunit");
//		}
		
		// 供应商代码与工厂代码通用数据库连接anMin
		else if("partnerId".equals(type)){
		    return commDao.queryCmbItems("comm.select_cmbitems_partner_id", param);
		}
		
		// SPS零件组
		else if("partGroupId".equals(type)){
			 return commDao.queryCmbItems("comm.select_partGroupId", param);
		}
		
		// SPS零件组
		else if("SpsPart".equals(type)){
			 return commDao.queryCmbItems("comm.select_SpsPart", param);
		}
		
		// 车型
		else if("MTO".equals(type)){
				return commDao.queryCmbItems("comm.select_Mto", param);
		}
		
		
		// 分拣区域
		else if("SORTING_AREA".equals(type)){
			return commDao.queryCmbItems("comm.select_sortingArea", param);
		}
		
		// 备注标示级别
		else if("REMARK_LEVEL".equals(type)){
			return commDao.queryCmbItems("comm.select_remarkLevel", param);
		}
		
		
		//liqiang追加 零件代码
		else if("partId".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_partId",param);
		}
		/*********************************订购模块下拉框**********************************************/
		
		/*********************************同步零件组**********************************************/
		//同步零件组
		else if("synComponentGroup".equals(type)){
			return commDao.queryCmbItems("comm.select_synComponentGroupComboBoxList",param);
		}
		//同步零件供应商-出货地下拉框
		else if("synComponentFactoryId".equals(type)){
			return commDao.queryCmbItems("comm.select_synComponentGroupSupFactoryId",param);
		}
		
		//拉动管控 信息点-出货仓库下拉框
		else if("shipByPlanCode".equals(type)){
			return commDao.queryCmbItems("comm.select_shipDepotByPlanCode",param);
		}
		
		/*********************************同步零件组**********************************************/

		/*********************************VMI模块下拉框**********************************************/
	     // 卸货口 一览
	    else if("VMI_OPE_CODE".equals(type)){
	      return commDao.queryCmbItems("comm.vmiOpeCodeList");
	    }
	    /*********************************VMI模块下拉框**********************************************/

		/********************结算管理下拉框 ****************/
		//结算供应商代码
	    else if("settSupplierNoQuery".equals(type)){
	    	return commDao.queryCmbItems("comm.select_cmbitems_settlement_supNoQuery",param);
	    }
		//零件采购员
	    else if("buyerIdQuery".equals(type)){
	    	return commDao.queryCmbItems("comm.select_cmbitems_componentBuyerQuery",param);
	    }
		
		/********************结算管理下拉框 ****************/
		
		return items;
	}

	
	/**
	 * 系统信息表中插入数据
	 * @param message 信息数据
	 * @return 插入的信息数据
	 */
	public SysMessageVO insertSysMessage(SysMessageVO message){
		return commDao.insertSysMessage(message);
	}
	
	/**
	 * 系统参数查询
	 * @return
	 */
	public SystemParamVO querySystemParam(){
		// 已存在
		if(ActionContext.getContext().getApplication().containsKey(Constants.SYSTEM_PARAM_KEY)){
			return (SystemParamVO)ActionContext.getContext().getApplication().get(Constants.SYSTEM_PARAM_KEY);
		}else{
			// 检索
			SystemParamVO param = commDao.querySystemParam();
			ActionContext.getContext().getApplication().put(Constants.SYSTEM_PARAM_KEY,param);
			return param;
		}
	}
	
	

	public CommDao getCommDao() {
		return commDao;
	}

	public void setCommDao(CommDao commDao) {
		this.commDao = commDao;
	}


	public CmbItemVO querySupplierNameById(String supplierId) {
		return commDao.querySupplierNameById(supplierId);

	}

	/*
	 * 根据登陆工厂限制
	 * dingbb
	 */
	public List<?> queryCmbItemsByFactory(String type, String param, String factory
			) {
		List<CmbItemVO> items = new ArrayList<CmbItemVO>();
		if(null != factory) {
			factory = factory.trim();
			param = param.trim();

		}
		
		if("factoryByCondition".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_checkFactory",factory);
		}
		
		//物流看板 下拉框
		else if("jitKb".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_jitKb",factory);
		}
		// 出货仓库下拉框限制
		/*else if("shipDepotByParam".equals(type)){
			if(Constants.USER_TYPE_SUPPLIER.equals(factory)){
				return commDao.queryCmbItems("comm.select_JitDepot_byParamType", param);
			}else{
				return commDao.queryCmbItems("comm.select_JitDepot_byParam", param);
			}
		}*/
		
		// 出货仓库下拉框限制
		else if("shipDepotByParam".equals(type)){
			FactoryParamItemVO factoryParamVo = new FactoryParamItemVO();
			String[] Strs = param.split("[.]");
			factoryParamVo.setFactory(Strs[1]);
			factoryParamVo.setParam(Strs[0]);
			List<AogFactoryVo> aogFactorySpecial = new ArrayList<AogFactoryVo>();
			if(Constants.USER_TYPE_SUPPLIER.equals(factory)){
				aogFactorySpecial = (List<AogFactoryVo>) commDao.queryCmbItems("comm.select_JitDepot_byParamType", factoryParamVo);
			}else{
				if(!"".equals(Strs[0])){
					aogFactorySpecial = (List<AogFactoryVo>) commDao.queryCmbItems("comm.select_JitDepot_byParam", factoryParamVo);
				}else{
					aogFactorySpecial = (List<AogFactoryVo>) commDao.queryCmbItems("comm.select_JitDepot_byNoParam", factoryParamVo);
				}
			}
			
			List<CmbItemVO> unloadIdSpecial = new ArrayList<CmbItemVO>();
			CmbItemVO cmbItemVO;
			for(int i = 0; i < aogFactorySpecial.size(); i ++) {
				cmbItemVO = new CmbItemVO();
				cmbItemVO.setCode(aogFactorySpecial.get(i).getCode());
				cmbItemVO.setName(aogFactorySpecial.get(i).getCode() + "+" +(aogFactorySpecial.get(i).getName()==null ? " " : aogFactorySpecial.get(i).getName()) + '+' + aogFactorySpecial.get(i).getAogFactory());
				unloadIdSpecial.add(cmbItemVO);
			}
			return unloadIdSpecial;
		}
		
		else if("partGroupByFactory".equals(type)){
			return commDao.queryCmbItems("comm.select_partGroupByFactory",factory);
		}
		
		// SPS零件组
		else if("partGroupId".equals(type)){
			return commDao.queryCmbItems("comm.select_partGroupId", factory);
		}
				
		// SPS零件组
		else if("SpsPart".equals(type)){
			return commDao.queryCmbItems("comm.select_SpsPart", factory);
		}
		
		// 厂外同步零件组
		if("synPartGroupByFactory".equals(type)){
			return commDao.queryCmbItems("comm.select_synComponentGroupComboBoxList",factory);
		}
		
		//订购模块--卸货口 一览
		else if("unloadPortList".equals(type)){
			return commDao.queryCmbItems("comm.unloadPortList",factory);
		}
		//订购模块--工作中心anMin
		else if("mpWorkcenter".equals(type)){
			return commDao.queryCmbItems("comm.select_cmbitems_mpWorkcenter",factory);
		}
		//订购模块-- 到货仓库查询
		else if("warehouse".equals(type)){
			return commDao.queryCmbItems("comm.select_warehouse",factory);
		}
		// 订购模块--零件单位
		else if("partunit".equals(type)){
			return commDao.queryCmbItems("comm.select_partunit",factory);
		}
		//订购模块--车型代码
	  	else if("carType".equals(type)){
	  		return commDao.queryCmbItems("comm.select_carType",factory);
	  	}
		
		//订购模块--不带*的 卸货口代码
		else if("unloadIdSpecialNo".equals(type)){
			FactoryParamItemVO factoryParamVo = new FactoryParamItemVO();
			factoryParamVo.setFactory(factory);
			factoryParamVo.setParam(param);
			List<AogFactoryVo> aogFactorySpecial = (List<AogFactoryVo>) commDao.queryCmbItems("comm.select_aogFactory_unloadId", factoryParamVo);
			List<CmbItemVO> unloadIdSpecial = new ArrayList<CmbItemVO>();
			CmbItemVO cmbItemVO;
			for(int i = 0; i < aogFactorySpecial.size(); i ++) {
				cmbItemVO = new CmbItemVO();
				cmbItemVO.setCode(aogFactorySpecial.get(i).getCode());
				cmbItemVO.setName(aogFactorySpecial.get(i).getCode() + "+" +(aogFactorySpecial.get(i).getName()==null ? " " : aogFactorySpecial.get(i).getName()) + '+' + aogFactorySpecial.get(i).getAogFactory());
				unloadIdSpecial.add(cmbItemVO);
			}
			return unloadIdSpecial;
		}
		return items;
	}


	public CmbItemVO queryPartGroupNoById(String code) {
		return commDao.queryPartGroupNoById(code);
	}


	public CmbItemVO querySpsPartById(String code) {
		return commDao.querySpsPartById(code);
	}


	public CmbItemVO queryShipDepotById(CmbItemVO tmp) {
		return commDao.queryShipDepotById(tmp);
	}
	
}
