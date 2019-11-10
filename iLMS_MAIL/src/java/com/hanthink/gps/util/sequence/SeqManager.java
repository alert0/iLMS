package com.hanthink.gps.util.sequence;

import com.opensymphony.xwork2.ActionContext;
import com.hanthink.gps.pub.vo.SeqVO;
import com.hanthink.gps.pub.dao.CommDao;
import com.hanthink.gps.util.StringUtil;

public class SeqManager {
	
    public static String getSeq(SeqType seqType) {    	
    	SeqVO seqVO = new SeqVO(); 
		switch (seqType) {
		case SEQ_TYPE_USER_ID:
			seqVO.setSeqId("SEQ_USER_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_BASIC_DATA_ID:
			seqVO.setSeqId("SEQ_BASIC_DATA_ID");
			seqVO.setSeqLen(3);
			break;
		case SEQ_TYPE_SUPPLIER_NO:
			seqVO.setSeqId("SEQ_SUPPLIER_NO");
			seqVO.setSeqLen(6);
			break;
		case SEQ_MM_PUB_OPE_LOG_ID:
			seqVO.setSeqId("SEQ_MM_PUB_OPE_LOG_ID");
			seqVO.setSeqLen(19);
			break;
		case SEQ_TYPE_ROLE_ID:
			seqVO.setSeqId("SEQ_ROLE_ID");
			seqVO.setSeqLen(3);
			break;
		case SEQ_TYPE_SUPROLE_ID:
			seqVO.setSeqId("SEQ_TYPE_SUPROLE_ID");
			seqVO.setSeqLen(3);
			break;
		case SEQ_TYPE_DEPARTMENT_ID:
			seqVO.setSeqId("SEQ_DEPARTMENT_ID");
			seqVO.setSeqLen(3);
			break;
		case SEQ_TYPE_APQP_PLAN_ID:
			seqVO.setSeqId("SEQ_APQP_PLAN_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_RECTIFICATION_NOTICEID:
			seqVO.setSeqId("SEQ_RECTIFICATION_NOTICEID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_PRICE:
			seqVO.setSeqId("SEQ_PRICE");
			seqVO.setSeqLen(15);
			break;
		case SEQ_INFONO:
			seqVO.setSeqId("SEQ_MM_SW_MESSAGE_INFO_ID");
			seqVO.setSeqLen(10);
			break;
			// 物流公司编号
		case SEQ_LOGISTICS_NO:
			seqVO.setSeqId("SEQ_LOGISTICS_NO");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_SUP_SUB_ID:
			seqVO.setSeqId("SEQ_TYPE_SUP_SUB_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_CLAIM_ID:
			seqVO.setSeqId("SEQ_TYPE_CLAIM_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_MOULDNO_ID:
			seqVO.setSeqId("SEQ_TYPE_MOULDNO_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_PICKUPPLAN_ID:
			seqVO.setSeqId("SEQ_TYPE_PICKUPPLAN_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_SORTINGPLAN_ID:
			seqVO.setSeqId("SEQ_TYPE_SORTINGPLAN_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_SORTING_SCHEDULE_ID:
			seqVO.setSeqId("SEQ_TYPE_SORTING_SCHEDULE_ID");
			seqVO.setSeqLen(15);
			break;	
		case SEQ_TYPE_SORTING_PRODUCT_ID:
			seqVO.setSeqId("SEQ_TYPE_SORTING_PRODUCT_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_TACTMANAGE_ID:
			seqVO.setSeqId("SEQ_TYPE_TACTMANAGE_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_CALENDARMANAGE_ID:
			seqVO.setSeqId("SEQ_TYPE_CALENDARMANAGE_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_M_SYN_GROUP_HEAD_ID:
			seqVO.setSeqId("SEQ_MM_JISO_GROUP_HEAD_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_M_SYN_GROUP_LINE_ID:
			seqVO.setSeqId("SEQ_MM_JISO_GROUP_LINE_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_JISO_GROUP_SUP_ID:
			seqVO.setSeqId("SEQ_MM_JISO_GROUP_SUP_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_VOTE_MANUAL_ID:
			seqVO.setSeqId("SEQ_TYPE_VOTE_MANUAL_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_PO_MANUAL_ID:
			seqVO.setSeqId("SEQ_TYPE_PO_MANUAL_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_INFO_CONFIG_ID:
			seqVO.setSeqId("SEQ_TYPE_INFO_CONFIG_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_M_SYN_GROUP_SUPPLIER_ID:
			seqVO.setSeqId("SEQ_M_SYN_GROUP_SUPPLIER_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MILITARY_VEHICLE_ORDE_ID:
			seqVO.setSeqId("SEQ_MILITARY_VEHICLE_ORDE_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_TYPE_D_DEMAND_FORECAST_ID:
			seqVO.setSeqId("SEQ_TYPE_D_DEMAND_FORECAST_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_IMPORT_DATA_TEMP:
			seqVO.setSeqId("SEQ_IMPORT_DATA_TEMP");
			seqVO.setSeqLen(10);
			break;
		case SEQ_VMI_ORDER_LINE_NO:
			seqVO.setSeqId("SEQ_VMI_ORDER_LINE_NO");
			seqVO.setSeqLen(11);
			break;
		case SEQ_MM_VMI_PART :
			seqVO.setSeqId("SEQ_MM_VMI_PART");
			seqVO.setSeqLen(17);
			break;
		case SEQ_TYPE_D_SUPPLY_FORECAST_ID:
			seqVO.setSeqId("SEQ_TYPE_D_SUPPLY_FORECAST_ID");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_LOC_PART:
			seqVO.setSeqId("SEQ_MM_LOC_PART");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_LOC_PART_TEMP:
			seqVO.setSeqId("SEQ_MM_LOC_PART_TEMP");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_LOC_PART_MTO:
			seqVO.setSeqId("SEQ_MM_LOC_PART_MTO");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_LOC_PART_MTO_TEMP:
			seqVO.setSeqId("SEQ_MM_LOC_PART_MTO_TEMP");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_PUB_PRINTER:
			seqVO.setSeqId("SEQ_MM_PUB_PRINTER");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_JIT_PART:
			seqVO.setSeqId("SEQ_MM_JIT_PART");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_JISI_PARTGROUP:
			seqVO.setSeqId("SEQ_MM_JISI_PARTGROUP");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_JISI_PART:
			seqVO.setSeqId("SEQ_MM_JISI_PART");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_LOC_NUM:
			seqVO.setSeqId("SEQ_MM_LOC_NUM");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_LOC_NUM_TEMP:
			seqVO.setSeqId("SEQ_MM_LOC_NUM_TEMP");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_INV_INVENTORY:
				seqVO.setSeqId("SEQ_MM_INV_INVENTORY");
				seqVO.setSeqLen(15);
				break;
		case SEQ_MM_INV_PART_CHANGE:
			seqVO.setSeqId("SEQ_MM_INV_PART_CHANGE");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_JISI_PARTGROUP_TEMP:
			seqVO.setSeqId("SEQ_MM_JISI_PARTGROUP_TEMP");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_JISI_PART_TEMP:
			seqVO.setSeqId("SEQ_MM_JISI_PART_TEMP");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_JIT_ROUTE_CARPOOL:
			seqVO.setSeqId("SEQ_MM_JIT_ROUTE_CARPOOL");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_JIT_ROUTE_CARPOOL_TEMP:
			seqVO.setSeqId("SEQ_MM_JIT_ROUTE_CARPOOL_TEMP");
			seqVO.setSeqLen(15);
			break;
		case SEQ_MM_PART_TEMP:
			seqVO.setSeqId("SEQ_MM_PART_TEMP");
			seqVO.setSeqLen(15);
			break;
		//拉动订单生成配置表
		case SEQ_MM_JIT_ORDER_CONFIG:
			seqVO.setSeqId("SEQ_MM_JIT_ORDER_CONFIG");
			seqVO.setSeqLen(15);
			break;
			//订购车辆计划
		case SEQ_MM_MP_VEH_TEMP:
			seqVO.setSeqId("SEQ_MM_MP_VEH_TEMP");
			seqVO.setSeqLen(19);
			break;
		default:
			break;
		}
		
		org.springframework.beans.factory.BeanFactory beanFactory = 
			(org.springframework.beans.factory.BeanFactory)ActionContext.getContext().getApplication().get(org.springframework.web.context.WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		CommDao dao = (CommDao)beanFactory.getBean("commDao");
		
		if (StringUtil.isNullOrEmpty(seqVO.getSeqId()))
			return "";
		String  seq = dao.querySeq(seqVO);
		return StringUtil.leftPad(seq,seqVO.getSeqLen(),'0');
    }    
	
}
