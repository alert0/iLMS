package com.hanthink.util.constant;
/**
* <p>Title: Constant.java<／p>
* <p>Description: 常量类<／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月25日
*/

public class Constant {
	public static String  PLAN_CODE = null;
	/**是**/
	public static String  IS = "是";
	/**否**/
	public static String  NO = "否";
	/**厂内同步是否自动打印 1：是**/
	public static String JISI_IS = "1";
	/**厂内同步是否自动打印 0：否**/
	public static String JISI_NO = "0";
	/**未提交**/
	public static final String UNSUBIMT = "0";
	/**提交、材料不良、例外订单已生成状态**/
	public static final String SUBIMT_TYPE_STATUS = "1";
	/**审核、加工不良**/
	public static final String CHECK_TYPE = "2";
	/**驳回**/
	public static final String REJECT = "3";
	/**转为材不**/
	public static final String TURN_MATERIAL = "4";
	/**生成例外订单**/
	public static final String EXCEP_ORDER = "5";
	/**订单类型,例外订单**/
	public static final String EXC = "EXC";
    
	
	/**提案通过**/
	public static final String PKG_STATUS = "2";
	
	/**待审核**/
	public static final String PKG_STATUS_CHECK = "待审核";
	/**提案通过**/
	public static final String PKG_STATUS_PASS = "提案通过";
	/**信息点**/
//	public static final String PLAN_CODE = "N1_JISI_PAOFF";
	/** 厂外同步推算控制台信息点 **/
	public static final String N1_JISO_PAOFF = "N1_JISO_PAOFF";
	/**全车型查询**/
	public static final String PKG_PROP0SAL_ALL = "0";
	/**单车型查询**/
	public static final String PKG_PROPOSAL_SINGLE = "1";
	/**组合查询**/
	public static final String PKG_PROPOSAL_COM = "2";
	/**登录用户类型 1:供应商**/
	public static final String PUB_USER_FLAG_SU = "1";
	/** 订单类型 量产 **/
	public static final String ORDER_TYPE_MP = "量产";
	/** 订单类型 试制 **/
	public static final String ORDER_TYPE_TM = "试制";
	
	/**预测数据，已发布 1**/
	public static final String SW_RELEASE_STATUS_YES = "1";
}
