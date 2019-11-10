package com.hotent.org.api.model;

import java.util.List;
import java.util.Map;

import com.hotent.base.api.BaseModel;
 

 


/**
 * 
 * 描述：用户实体接口
 * 构建组：x5-bpmx-api
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2013-11-15-上午11:39:09
 * 版权：广州宏天软件有限公司版权所有
 */
public interface IUser    extends IdentityType,BaseModel{
	
	/** 用户类型-普通用户 */
	public static final String USER_TYPE_NORMAL = "1";
	/** 用户类型-供应商用户 */
	public static final String USER_TYPE_SUPP = "2";
	
	/**
	 * 男性=Male
	 */
	public static final String SEX_MALE="Male";
	/**
	 * 女性=Female
	 */
	public static final String SEX_FAMALE="Female";
	
	/**
	 * 用户标识Id
	 * @return 
	 * String
	 */
	String getUserId();
	
	void setUserId(String userId);
	/**
	 * 用户姓名
	 * @return 
	 * String
	 */
	String getFullname();
	
	void setFullname(String fullName);
	/**
	 * 用户账号
	 * @return  String
	 */
	String getAccount();
	
	void setAccount(String account);
	/**
	 * 获取密码
	 * @return  String
	 */
	String getPassword();
	
	/**
	 * 邮件。
	 * @return String
	 */
	String getEmail();
	
	/**
	 * 手机。
	 * @return  String
	 */
	String getMobile();
	
	/**
	 * 返回 头像
	 * @return
	 */
	public String getPhoto();
	
	/**
	 * 设置用户其它属性
	 * @param map
	 */
	void setAttributes(Map<String,String> map);
	
	
	boolean isAdmin();
	
	/**
	 * 是否禁用用户
	 * @return
	 */
	boolean isEnable();
	
	/**
	 * 获取用户其它属性
	 * @param map
	 */
	Map<String,String> getAttributes();
	
	/**
	 * 根据属性获取属性值。
	 * @param key
	 * @return
	 */
	String getAttrbuite(String key);
	
	
	//--------------------工厂信息与方法----开始-----------------
	
	/**
	 * 获取用户所配置的工厂代码
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月10日 上午11:59:57
	 */
	List<String> getConfFactoryCodeList();
	
	/**
	 * 获取用户当前所选工厂代码
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月10日 下午12:01:05
	 */
	String getCurFactoryCode();
	
	/**
	 * 获取用户所配置的产线信息
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月10日 下午12:02:56
	 */
	List<String> getConfProductLineList();
	
	/**
	 * 获取用户当前所选产线代码
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月10日 下午12:03:27
	 */
	String getCurProductLine();
	
	//-------------工厂信息-结束----
	
	//增加用户信息
	
	/**
	 * 获取用户类型
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月25日 下午6:58:29
	 */
	String getUserType();
	
	/**
	 * 获取用户部门编码
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月25日 下午7:00:04
	 */
	String getDeptCode();
	
	/**
	 * 获取用户的供应商代码（当用户类型为供应商时有效）
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月25日 下午6:59:35
	 */
	String getSupplierNo();
	
	/**
	 * 获取工厂代码
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年3月16日 下午2:24:51
	 */
	String getFactoryCode();
	
	/**
	 * 获取车间信息
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年3月16日 下午2:26:14
	 */
	String getWorkshop();
	
	/**
	 * 获取临时的自定义字段1
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年3月16日 下午2:44:28
	 */
	String getTempField1();
	
	/**
	 * 获取临时的自定义字段2
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年3月16日 下午2:44:28
	 */
	String getTempField2();
	
	/**
	 * 获取临时的自定义字段3
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年3月16日 下午2:44:28
	 */
	String getTempField3();
	
	/**
	 * 获取临时的自定义字段4
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年3月16日 下午2:44:28
	 */
	String getTempField4();
	
	/**
	 * 获取临时的自定义字段5
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年3月16日 下午2:44:28
	 */
	String getTempField5();
	
}
