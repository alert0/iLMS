
package com.hanthink.gps.util;

import java.io.InputStream;
import java.util.Properties;

import com.hanthink.gps.util.logger.LogUtil;



/**
 * 共通常量类
 * 
 */
public class Constants {
	
	private static String TEMP_CUR_APP_TYPE = "ILMS_N1";
	private static String TEMP_ADMIN_PWD = "admin"; //admin
	
	static{
		InputStream inputStream = Constants.class.getResourceAsStream("/System.properties");
		Properties property = new Properties();
		try {
			property.load(inputStream);
			String curAppType = property.getProperty("CURRENT_APP_TYPE");
			if(!StringUtil.isNullOrEmpty(curAppType)){
				TEMP_CUR_APP_TYPE = curAppType;
			}
			String adminPwd = property.getProperty("ADMIN_PWD");
			if(!StringUtil.isNullOrEmpty(adminPwd)){
				TEMP_ADMIN_PWD = adminPwd;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error("初始化加载当前DSA应用类型失败!");
		}
	}
	
	/** 定时器ID属性 */
	public final static String TIMER_JOB_ID = "TIMER_JOB_ID";
	
	/** 当前应用类型，用于区分多个应用指向同一个数据库 */
	public final static String CUR_APP_TYPE = TEMP_CUR_APP_TYPE;
	
	//工厂代码
	/** 广州一线工厂代码 */
	public final static String FACTORY_CODE_G1 = "G1";
	/** 广州二线工厂代码 */
	public final static String FACTORY_CODE_G2 = "G2";
	
	/** 服务器文件上传的位置 */
	public static final String FILE_UPLOAD_PATH = "D://i-LMS_mail";
	
	
	/** 超级用户用户名 */
	public final static String SUPER_USER = "admin";
	/** ADMIN超级管理员密码(加密) */
	public final static String ADMIN_PWD = TEMP_ADMIN_PWD;
	
	// 身份类型
	/** 身份类型-供应商 */
	public static final String USER_TYPE_SUPPLIER = "0";
	/** 身份类型-GAMC */
	public static final String USER_TYPE_USER = "1";
	/** 身份类型-物流公司 */
	public static final String USER_TYPE_LOGISTICS = "2";
	
	/** 系统用户状态-正常 */
	public static final String SYS_USER_STATUS_YES = "1";
	/** 系统用户状态-禁用 */
	public static final String SYS_USER_STATUS_NO = "2";
	
	//权限类型
	/** 权限类型-菜单 */
	public static final String PRIVILEGE_TYPE_MENU = "1";
	/** 权限类型-界面按钮、数据 */
	public static final String PRIVILEGE_TYPE_PAGE = "2";
	
	// 角色类型
	/** 广乘用户角色 */
	public static final String ROLE_TYPE_GAMC = "1";
	/** 供应商用户角色 */
//	public static final String ROLE_TYPE_SUPPLIER = "2";
	//供应商角色类型
	/** 供应商角色类型-供应商 */
	public static final String SUP_ROLE_TYPE_SUPPLIER = "2";
	/** 供应商角色类型-中转仓 */
	public static final String SUP_ROLE_TYPE_TRANSIT = "3";
	/** 供应商角色类型-外协仓 */
	public static final String SUP_ROLE_TYPE_WX = "4";
	
	//激活状态 : 物流公司状态
	/** 激活状态-未激活 */
	public static final String ACTIVATE_STATUS_NO = "0";
	/** 激活状态-已激活 */
	public static final String ACTIVATE_STATUS_YES = "1";
	
	//供应商状态
	/** 供应商状态-潜在 */
	public static final String SUPPLIER_STATUS_1 = "1";
	/** 供应商状态-活动 */
	public static final String SUPPLIER_STATUS_2 = "2";
	/** 供应商状态-不活动 */
	public static final String SUPPLIER_STATUS_3 = "3";
	
	//供应商激活状态
	/** 供应商激活状态-未激活 */
	public static final String SUP_ACTIVE_STATUS_NO = "0";
	/** 供应商激活状态-已激活 */
	public static final String SUP_ACTIVE_STATUS_YES = "1";
	/** 供应商激活状态-已注册 */
	public static final String SUP_ACTIVE_STATUS_RIGEST = "2";
	/** 供应商激活状态-已禁用 */
	public static final String SUP_ACTIVE_STATUS_DISABLE = "3";
	
	/** 生成随机密码的长度 */
	public static final int GEN_PWD_LEN = 8;
	
	
	/** 成功信息 */
	public static final String MSG_SUCCESS = "{success:true,msg:'success'}";
	
	//数据字典 CODE_TYPE
	/** 需求订单-打印状态 */
	public static final String DICT_SW_PRINT_STATUS = "PRINT_STATUS";
	/** 需求订单-下载状态 */
	public static final String DICT_SW_DOWNLOAD_DTATUS = "DOWNLOAD_STATUS";
	/** 需求订单-发货状态 */
	public static final String DICT_SW_DELIVERY_STATUS = "DELIVERY_STATUS";
	/** 需求订单-收货状态 */
	public static final String DICT_SW_RECEIVE_STATUS = "RECEIVE_STATUS";
	/** 需求订单-过期状态 */
	public static final String DICT_SW_OVERDUE_STATUS = "OVERDUE_STATUS";
	/** 协同发货单-取消状态 */
	public static final String DICT_SW_DELIVERY_CANCEL_STATUS = "SW_DELIVERY_CANCEL_STATUS";
	
	
	// 登录状态
	/** 离线 */
	public static final String LOGIN_STATUS_OFFLINE = "00";
	/** 在线 */
	public static final String LOGIN_STATUS_ONLINE = "01";
	/** 密锁 */
	public static final String LOGIN_STATUS_LOCK = "02";
	/** 用户登录开始 */
	public static final String SW_I_M001 = "SW_I_M001";
	/** 用户登录结束 */
	public static final String SW_I_M002 = "SW_I_M002";
	/** 验证码错误 */
	public static final String SW_E_M001 = "SW_E_M001";
	/** 用户不存在 */
	public static final String SW_E_M002 = "SW_E_M002";
	/** 已有用户登录,请先退出已登录的用户后再刷新页面登录 */
	public static final String SW_E_M003 = "SW_E_M003";
	/** 该用户已被禁用 */
	public static final String SW_E_M004 = "SW_E_M004";
	/** 密码输入错误 */
	public static final String SW_E_M005 = "SW_E_M005";
	/** 该用户未激活 */
	public static final String SW_E_M006 = "SW_E_M006";
	/** 该用户已被取消 */
	public static final String SW_E_M007 = "SW_E_M007";
	/** 请输入用户名 */
	public static final String SW_E_M008 = "SW_E_M008";
	/** 请输入密码 */
	public static final String SW_E_M009 = "SW_E_M009";
	/** 请选择身份类型 */
	public static final String SW_E_M010 = "SW_E_M010";
	/** 请输入验证码 */
	public static final String SW_E_M011 = "SW_E_M011";
	
	/** {0}使用中,无法删除 */
	public static final String MSG_ID_E_UNUSE_FAIL = "GSL_E_M104";
	//信息提示ID
	/** 登录名重复 */
	public static final String MSG_ID_E_USERNAME_REPEAT = "GPS_E_M001";
	/** 公司代码重复 */
	public static final String MSG_ID_E_COMPANYNO_REPEAT = "GPS_E_M002";
	/** 用户未配置工厂 */
	public static final String MSG_ID_E_USER_NOT_FACTORY = "GPS_E_M003";
	/** 获取供应商信息失败 */
	public static final String MSG_ID_E_NOT_SUPPLIER_INFO = "GPS_E_M004";
	/** 供应商[?]未激活 */
	public static final String MSG_ID_E_SUPPLIER_NOACTIVATE = "GPS_E_M005";
	/** 发货序号超过最大值 */
	public static final String MSG_ID_E_DELIVERY_SEQ_NO_OVER = "GPS_E_M006";
	/** 数据已存在 */
	public static final String MSG_ID_E_DATA_REPEAT = "GPS_E_M007";
	/** 有任务正在执行或已完成 */
	public static final String MSG_ID_E_TASK_ING = "GPS_E_M008";
	/** 操作失败 */
	public static final String MSG_ID_E_OPE_FAILED = "GPS_E_M009";
	
	//Session Key
	/** 登录用户管理信息的保存key 作为保存在session里面的key */
	public static final String USER_KEY = "USER_KEY";
	/** 登录用户管理信息 用户有多个工厂时临时保存用户信息的key */
	public static final String USER_KEY_TEMP = "USER_KEY_TEMP";
	/** 登录用户权限顶级模块信息 */
	public static final String USER_ROLE_ZERO_MENU_KEY = "USER_ROLE_ZERO_MENU_KEY";
	/** 登录用户权限菜单表示信息的保存key 作为保存在session里面的key */
	public static final String USER_ROLE_MENU_KEY = "USER_ROLE_MENU_KEY";
	/** 登录用户页面权限信息的保存key 作为保存在session里面的key */
	
	/** 最小长度check失败message */
	public static final String MSG_ID_E_MINLENGTH = "GSL_E_M009";
	/** 最大长度check失败message */
	public static final String MSG_ID_E_MAXLENGTH = "GSL_E_M010";
	/** 最大值check失败message */
	public static final String MSG_ID_E_MAXVALUE = "GSL_E_M107";
	/** 必须输入项check失败message */
	public static final String MSG_ID_E_REQUEST = "GSL_E_M011";
	/** 登录名重复 */
	public static final String MSG_ID_E_UserName_REPEAT = "GSL_E_M013";
	/** 未删除 */
	public static final String DELETE_STATUS_NO = "0";
	/** 删除 */
	public static final String DELETE_STATUS_YES = "1";



	/** 注册失败 */
	public static final String MSG_ID_E_REQUEST_REGEDIT_FAIL = "GSL_E_M100";
	/** 零部件新规失败 */
	public static final String MSG_ID_E_COMPONENT_FAIL = "GSL_E_M101";
	/** 公司代码添加失败 */
	public static final String MSG_ID_E_LOGISTICSID_FAIL = "GSL_E_M201";
	/** 公司登录名添加失败 */
	public static final String MSG_ID_E_LOGIN_FAIL = "GSL_E_M202";
	/** 公司地址添加失败 */
	public static final String MSG_ID_E_LOGISTICSNAME_FAIL = "GSL_E_M203";
	/** 原密码错误 */
	public static final String MSG_ID_E_ORIGINAL_PWD_FAIL = "GSL_E_M102";
	/** 密码修改失败 */
	public static final String MSG_ID_E_MODIFY_PWD_FAIL = "GSL_E_M103";
	/** 附件超过最大{0}MB限制,无法上传！ */
	public static final String MSG_ID_E_UPLOAD_FILE_FAIL = "GSL_E_M105";
	/** 附件不存在！ */
	public static final String MSG_ID_E_UPLOAD_FILE_NOT_EXIST = "GSL_E_M108";
	/** 附件只能上传pdf|xls|doc|rar类型文档！ */
	public static final String MSG_ID_E_FILETYPE_FAIL = "GSL_E_M106";
	/** mail模板:注册供应商审批退回 */
	public static final String MSG_ID_MAIL_REGSUPPLIER_BACK = "GSL_EM_M003";
	/** mail模板:注册供应商审批通过 */
	public static final String MSG_ID_MAIL_REGSUPPLIER_PASS = "GSL_EM_M002";
	/** mail模板:潜在供应商审批通过 */
	public static final String MSG_ID_MAIL_POTENTIALSUPPLIER_PASS = "GSL_EM_M005";

	/** session常量---->收货信息管理 session */
	public static final String RECEIVE_SEARCH_KEY = "RECEIVE_SEARCH_KEY";
	/** session常量---->需求预测管理 session */
	public static final String DEMANDFORECAST_SEARCH_KEY = "DEMANDFORECAST_SEARCH_KEY";
	/** APQP计划项目 BAIC 017 */
	public static final String APQP_PLANFLG_BAIC = "017";
	/** 执行标记 0 执行 */
	public static final String DOFLG_YES = "0";
	/** 执行标记1 未执行 */
	public static final String DOFLG_NO = "1";

	
	// 文件判定常量
	public static int FILE_NOT_EXIST = 1;
	public static int FILE_OVER_SIZE = 2;
	public static int FILE_OK = 0;


	//上传的重试的最大次数
	public static final int RETRY_COUNT=10;
	/** 供应商 */
	public static final String USER_CONFIRM_TYPE = "1";
	/** 系统参数信息的保存key 作为保存在servletContext里面的key */
	public static final String SYSTEM_PARAM_KEY = "SYSTEM_PARAM_KEY";
	/** GAMC */
	public static final String USER_UNCONFIRM_TYPE = "0";
	/** 角色类型 供应商 */
	public static String RELATIVE_ID_SUP = "1";
	/** 角色类型 中转仓 */
	public static String RELATIVE_ID_TRANSPORTOR = "2";
	/** 角色类型 外协仓 */
	public static String RELATIVE_ID_SUPPORTOR = "3";
	
	/** 上传文件最大大小限制（单位M） */
	public static final int MAX_FILE_SIZE = 5;
    /**
     * 定时器运行最大超时时间
     */
    public final static long TIMER_DATE_OVER = 10 * 60 * 1000;
    /**
	 * 登录用户页面权限信息的保存key 作为保存在session里面的key
	 */
	public static final String USER_PAGES_AUTH_KEY = "USER_PAGES_AUTH_KEY";
	
	 /**
     * 是
     */
    public final static String YES = "1";

    /**
     * 否
     */
    public final static String NO = "0";

}
