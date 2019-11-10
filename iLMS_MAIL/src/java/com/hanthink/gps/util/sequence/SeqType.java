package com.hanthink.gps.util.sequence;

/**
 * 采番枚举
 *  
 * @author hcsang
 *
 */
public enum SeqType {
	/** 用户ID */
	SEQ_TYPE_USER_ID,
	/** 基础数据 */
	SEQ_TYPE_BASIC_DATA_ID,
	/** 基础数据 */
	SEQ_TYPE_SUPPLIER_NO,
	/** 角色 */
	SEQ_TYPE_ROLE_ID,
	/** 部门 */
	SEQ_TYPE_DEPARTMENT_ID,
	/** APQP计划 */
	SEQ_TYPE_APQP_PLAN_ID,
	/** 整改通知 */
	SEQ_RECTIFICATION_NOTICEID,
	/** 操作日志*/
	SEQ_MM_PUB_OPE_LOG_ID,
	/** 询价信息 */
	SEQ_TYPE_PRICE,
	/** 物流公司编号 李强追加 */
	SEQ_LOGISTICS_NO,
	/** 公告信息 */
	SEQ_INFONO,
	/** 供应商角色 */
	SEQ_TYPE_SUPROLE_ID,
	/** 子用户ID */
	SEQ_TYPE_SUP_SUB_ID,
	/** 索赔管理NO */
	SEQ_TYPE_CLAIM_ID,
	/** 模具分摊NO */
	SEQ_TYPE_MOULDNO_ID,
	/** 取货计划NO */
	SEQ_TYPE_PICKUPPLAN_ID,
	/** 排序计划NO */
	SEQ_TYPE_SORTINGPLAN_ID, 
	/** 生产进度管理 */
	SEQ_TYPE_SORTING_SCHEDULE_ID,
	/** 生产计划管理 */
	SEQ_TYPE_SORTING_PRODUCT_ID,
	/** 同步节拍管理*/
	SEQ_TYPE_TACTMANAGE_ID,
	/** 同步日历管理*/
	SEQ_TYPE_CALENDARMANAGE_ID,
	/** 同步零件组HEAD*/
	SEQ_M_SYN_GROUP_HEAD_ID,
	/** 同步零件组LINE*/
	SEQ_M_SYN_GROUP_LINE_ID,
	/** 同步零件组SUPPLIER*/
	SEQ_MM_JISO_GROUP_SUP_ID,
	/** 手工组单vote*/
	SEQ_TYPE_VOTE_MANUAL_ID,
	/** 手工订单po */
	SEQ_TYPE_PO_MANUAL_ID,
	/** 手工订单po */
	SEQ_TYPE_INFO_CONFIG_ID,
	/** 手工订单po */
	SEQ_M_SYN_GROUP_SUPPLIER_ID,
	/** 手工订单po */
	SEQ_MILITARY_VEHICLE_ORDE_ID,
	/** 预测 */
	SEQ_TYPE_D_DEMAND_FORECAST_ID,

	/** 零件单落点表 */
	SEQ_MM_LOC_PART,
	
	/** 拉动订单生成配置表 */
	SEQ_MM_JIT_ORDER_CONFIG,
	
	/** 零件单落点临时表 */
	SEQ_MM_LOC_PART_TEMP,
	
	/** 零件多落点临时表 */
	SEQ_MM_LOC_PART_MTO,
	
	/** 零件多落点临时表 */
	SEQ_MM_LOC_PART_MTO_TEMP,
	
	/** 打印机配置信息ID */
	SEQ_MM_PUB_PRINTER,
	
	/** 配送零件基本信息ID */
	SEQ_MM_JIT_PART,
	
	/** 拉动配送拼车路线ID */
	SEQ_MM_JIT_ROUTE_CARPOOL,
	
	/** 物料主数据临时表 ID */
	SEQ_MM_PART_TEMP,
	
	/** 拉动配送拼车路线临时表ID */
	SEQ_MM_JIT_ROUTE_CARPOOL_TEMP,
	
	/** 初始库存临时表ID */
	SEQ_MM_INV_INVENTORY,
	
	/** 设变新旧零件临时表ID */
	SEQ_MM_INV_PART_CHANGE,
	
	/** 同步零件组ID */
	SEQ_MM_JISI_PARTGROUP,
	
	/** 同步零件组临时表ID */
	SEQ_MM_JISI_PARTGROUP_TEMP,
	
	/** 同步零件ID */
	SEQ_MM_JISI_PART,
	
	/** 同步零件临时表ID */
	SEQ_MM_JISI_PART_TEMP,
	
	/** 导入临时表PKID */
	SEQ_IMPORT_DATA_TEMP,
	
	/** VMI订单行 */
	SEQ_VMI_ORDER_LINE_NO,
	
	/** 外协仓零件基础信息 */
	SEQ_MM_VMI_PART,
	
	/** 供应预测 */
	SEQ_TYPE_D_SUPPLY_FORECAST_ID,	
	
	/**落点车体流动数维护*/
	SEQ_MM_LOC_NUM,
	
	/**落点车体流动数维护*/
	SEQ_MM_LOC_NUM_TEMP,
	
	/**订购车辆计划*/
	SEQ_MM_MP_VEH_TEMP,
}
