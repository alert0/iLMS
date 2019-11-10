package com.hotent.org.persistence.model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.model.IdentityType;
import com.hotent.sys.util.SysPropertyUtil;


 /**
 * 
 * <pre> 
 * 描述：用户表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:26:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class User extends AbstractModel<String> implements IUser {
	
	public final static String FROM_SYSTEM = "system";
	
	public final static String FROM_RESTFUL = "restful";
	/**
	 * JMS添加
	 */
	public final static String FROM_JMS = "jms";
	
	/**
	* id_
	*/
	protected String id; 
	
	/**
	* 姓名
	*/
	protected String fullname; 
	
	/**
	* 账号
	*/
	protected String account; 
	
	/**
	* 密码
	*/
	protected String password; 
	
	/**
	* 邮箱
	*/
	protected String email; 
	
	/**
	* 手机号码
	*/
	protected String mobile; 
	
	/**
	* 微信号
	*/
	protected String weixin; 
	
	/**
	* 创建时间
	*/
	protected java.util.Date createTime; 
	
	/**
	* 地址
	*/
	protected String address; 
	
	/**
	* 头像
	*/
	protected String photo; 
	
	/**
	* 性别：男，女，未知
	*/
	protected String sex; 
	
	/**
	* 来源
	*/
	protected String from="system"; 
	
	/**
	* 0:禁用，1正常
	*/
	protected Integer status; 
	
	
	/**
	 * 组织ID，用于在组织下添加用户。
	 */
	protected String groupId="";
	
	/**
	 * 微信同步关注状态  0：未同步  1：已同步，尚未关注  2：已同步且已关注
	 */
	protected Integer hasSyncToWx;
	
	/**
	* 微信用户唯一识别号
	*/
	protected String openId; 
	
	/** 电子签名文件ID */
	private String signNameFileId;
	
	/** 用户类型 */
	private String userType;
	/** 供应商代码 */
	private String deptCode;
	/** 供应商代码 */
	private String supplierNo;
	private Integer num;

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 id_
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	/**
	 * 返回 姓名
	 * @return
	 */
	public String getFullname() {
		return this.fullname;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	/**
	 * 返回 账号
	 * @return
	 */
	public String getAccount() {
		return this.account;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 返回 密码
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 返回 邮箱
	 * @return
	 */
	public String getEmail() {
		return this.email;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 返回 手机号码
	 * @return
	 */
	public String getMobile() {
		return this.mobile;
	}
	
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	
	/**
	 * 返回 微信号
	 * @return
	 */
	public String getWeixin() {
		return this.weixin;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 返回 地址
	 * @return
	 */
	public String getAddress() {
		return this.address;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	/**
	 * 返回 头像
	 * @return
	 */
	public String getPhoto() {
		return this.photo;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	/**
	 * 返回 性别：男，女，未知
	 * @return
	 */
	public String getSex() {
		return this.sex;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	/**
	 * 返回 来源
	 * @return
	 */
	public String getFrom() {
		return this.from;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 * 返回 0:禁用，1正常
	 * @return
	 */
	public Integer getStatus() {
		return this.status;
	}
	
	/**
	 * 返回 微信用户唯一识别号
	 * @return
	 */
	
	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("fullname", this.fullname) 
		.append("account", this.account) 
		.append("password", this.password) 
		.append("email", this.email) 
		.append("mobile", this.mobile) 
		.append("weixin", this.weixin) 
		.append("createTime", this.createTime) 
		.append("address", this.address) 
		.append("photo", this.photo) 
		.append("sex", this.sex) 
		.append("from", this.from) 
		.append("status", this.status)
		.append("openId", this.openId)
		.toString();
	}

	public String getIdentityType() {
		return IdentityType.USER;
	}

	public String getUserId() {
		return this.id;
	}
	public void setUserId(String userId) {
		this.id=userId;
		
	}

	public void setAttributes(Map<String, String> map) {
		
	}

	public boolean isAdmin() {
		String tmp=SysPropertyUtil.getByAlias("admin.account", "admin");
		boolean flag = tmp.equals(this.account);
		if(!flag){
			String[] adminAccountArr = SysPropertyUtil.getByAlias("admin.accountArr", "").split(",");
			Set<String> set = null;
			if(null != adminAccountArr){
				if(!(adminAccountArr.length == 1 && "".equals(adminAccountArr[0]))){
					set = new HashSet<String>();
					for(String adminAccount : adminAccountArr){
						set.add(adminAccount);
					}
				}
				if(null != set && set.contains(this.account)){
					flag = true;
				}
			}
		}
		return flag;
	}

	public Map<String, String> getAttributes() {
		return null;
	}
	public String getAttrbuite(String key) {
		return null;
	}

	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getHasSyncToWx() {
		return hasSyncToWx;
	}

	public void setHasSyncToWx(Integer hasSyncToWx) {
		this.hasSyncToWx = hasSyncToWx;
	}

	@Override
	public boolean isEnable() {
		int status=this.getStatus();
		if(status==1){
			return true;
		}
		return false;
	}
	
	/**
	 * @return the signNameFileId
	 */
	public String getSignNameFileId() {
		return signNameFileId;
	}


	/**
	 * @param signNameFileId the signNameFileId to set
	 */
	public void setSignNameFileId(String signNameFileId) {
		this.signNameFileId = signNameFileId;
	}
	

	//-------------工厂信息-开始-------------先直接定义，后续扩展为配置------

	/**
	 * 
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月11日 下午12:08:08
	 */
	@Override
	public List<String> getConfFactoryCodeList() {
		List<String> codeList = new ArrayList<String>();
		codeList.add("2000");
		return codeList;
	}


	/**
	 * 
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月11日 下午12:08:23
	 */
	@Override
	public String getCurFactoryCode() {
		return "2000";
	}


	/**
	 * 
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月11日 下午12:08:29
	 */
	@Override
	public List<String> getConfProductLineList() {
		List<String> lineList = new ArrayList<String>();
		lineList.add("Line_01");
		return lineList;
	}


	/**
	 * 
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年9月11日 下午12:08:34
	 */
	@Override
	public String getCurProductLine() {
		return "Line_01";
	}
	
	//-------------工厂信息-结束----
	
	
	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}
}