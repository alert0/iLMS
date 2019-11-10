package com.hotent.bpmx.persistence.model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.constants.Bool;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.DesignerType;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;



/**
 * 对象功能:@名称：BPM_DEFINITION 【流程定义】 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2013-12-12 18:02:04
 */

@XmlRootElement(name = "BpmDef")
@XmlAccessorType(XmlAccessType.NONE)
public class DefaultBpmDefinition extends AbstractModel<String> implements BpmDefinition,Cloneable{
	
	protected String  defId; /*流程定义ID*/
	@XmlAttribute(name="name")
	protected String  name; /*流程名称*/
	@XmlAttribute(name="defKey")
	protected String  defKey; /*流程定义key*/
	@XmlAttribute(name="desc")
	protected String  desc; /*流程描述*/
	
	protected String  typeId; /*所属分类ID*/
	@XmlAttribute(name="status")
	protected String  status; /*流程状态。草稿、发布、禁用*/
	
	/*测试状态,是和否*/
	@XmlAttribute(name="testStatus")
	protected String  testStatus=BpmDefinition.TEST_STATUS.TEST; 

	protected String  bpmnDefId; /*BPMN - 流程定义ID*/
	protected String  bpmnDeployId; /*BPMN - 流程发布ID*/
	protected Integer  version;			/*版本 - 当前版本号*/
	protected String  mainDefId; /*版本 - 主版本流程ID*/
	protected String  isMain; /*版本 - 是否主版本*/
	protected String  reason; /*版本 - 变更理由*/
	
	protected String  createBy; /*创建人ID*/
	
	protected java.util.Date  createTime; /*创建时间*/
	protected String  createOrgId; /*创建者所属组织ID*/
	protected String  updateBy; /*更新人ID*/
	protected java.util.Date  updateTime; /*更新时间*/
	
	@XmlAttribute(name="designer")
	protected String  designer=DesignerType.FLASH.name(); /*更新人ID*/
	
	@XmlAttribute(name="supportMobile")
	protected int supportMobile=0;
	
	
	
	//流程分管授权权限对象
  	protected JSONObject authorizeRight;
  	
  	
	
  	@XmlElement(name="bpmDefData")
	protected BpmDefData bpmDefData = new BpmDefData(); 
	
	@Override
	public void setId(String id) {
		setDefId(id);
	}
	@XmlTransient
	@Override
	public String getId() {
		return defId;
	}	
	public void setDefId(String defId) 
	{
		this.defId = defId;
//		bpmDefData.setId(defId);		
	}
	/**
	 * 返回 流程定义ID
	 * @return
	 */
	public String getDefId() 
	{
		return this.defId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 流程名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	
	public void setDefKey(String defKey) 
	{
		this.defKey = defKey;
	}
	/**
	 * 返回 流程定义Key
	 * @return
	 */
	public String getDefKey() 
	{
		return this.defKey;
	}
	public void setDesc(String desc) 
	{
		this.desc = desc;
	}
	/**
	 * 返回 流程描述
	 * @return
	 */
	public String getDesc() 
	{
		return this.desc;
	}
	public void setTypeId(String typeId) 
	{
		this.typeId = typeId;
	}
	/**
	 * 返回 所属分类ID
	 * @return
	 */
	public String getTypeId() 
	{
		return this.typeId;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}
	/**
	 * 返回 流程状态。草稿、发布、禁用
	 * @return
	 */
	public String getStatus() 
	{
		return this.status;
	}
	public void setTestStatus(String testStatus) 
	{
		this.testStatus = testStatus;
	}
	/**
	 * 返回 测试状态
	 * @return
	 */
	public String getTestStatus() 
	{
		return this.testStatus;
	}
	
	public void setBpmnDefId(String bpmnDefId) 
	{
		this.bpmnDefId = bpmnDefId;
	}
	/**
	 * 返回 BPMN - 流程定义ID
	 * @return
	 */
	public String getBpmnDefId() 
	{
		return this.bpmnDefId;
	}
	public void setBpmnDeployId(String bpmnDeployId) 
	{
		this.bpmnDeployId = bpmnDeployId;
	}
	/**
	 * 返回 BPMN - 流程发布ID
	 * @return
	 */
	public String getBpmnDeployId() 
	{
		return this.bpmnDeployId;
	}
	public void setVersion(Integer version) 
	{
		this.version = version;
	}
	/**
	 * 返回 版本 - 当前版本号
	 * @return
	 */
	public Integer getVersion() 
	{
		return this.version;
	}
	public void setMainDefId(String mainDefId) 
	{
		this.mainDefId = mainDefId;
	}
	/**
	 * 返回 版本 - 主版本流程ID
	 * @return
	 */
	public String getMainDefId() 
	{
		return this.mainDefId;
	}
	public void setIsMain(String isMain) 
	{
		this.isMain = isMain;
	}
	/**
	 * 返回 版本 - 是否主版本
	 * @return
	 */
	public String getIsMain() 
	{
		return this.isMain;
	}
	public void setReason(String reason) 
	{
		this.reason = reason;
	}
	/**
	 * 返回 版本 - 变更理由
	 * @return
	 */
	public String getReason() 
	{
		return this.reason;
	}
	
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}
	/**
	 * 返回 创建人ID
	 * @return
	 */
	public String getCreateBy() 
	{
		return this.createBy;
	}
	public void setCreateTime(java.util.Date createTime) 
	{
		this.createTime = createTime;
	}
	/**
	 * 返回 创建时间
	 * @return
	 */
	@Override
	@XmlTransient
	public java.util.Date getCreateTime() 
	{
		return this.createTime;
	}
	public void setCreateOrgId(String createOrgId) 
	{
		this.createOrgId = createOrgId;
	}
	/**
	 * 返回 创建者所属组织ID
	 * @return
	 */
	public String getCreateOrgId() 
	{
		return this.createOrgId;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}
	/**
	 * 返回 更新人ID
	 * @return
	 */
	public String getUpdateBy() 
	{
		return this.updateBy;
	}
	public void setUpdateTime(java.util.Date updateTime) 
	{
		this.updateTime = updateTime;
	}
	/**
	 * 返回 更新时间
	 * @return
	 */
	public java.util.Date getUpdateTime() 
	{
		return this.updateTime;
	}
	
	/**
	 * 设计器。
	 * @param designer 
	 * void
	 */
	public void setDesigner(String designer){
		this.designer=designer;
	}
	
	/**
	 * 设计器。
	 * @param designer
	 * @return  String
	 */
	public String getDesigner(){
		return this.designer;
	}
	
	public String getDefJson() {
		return bpmDefData.defJson;
	}
	
	public void setDefJson(String defJson){
		bpmDefData.defJson = defJson;
	}
	
	@Override
	public String getDefXml() {
		return bpmDefData.defXml;
	}
	public void setDefXml(String defXml){
		// 处理泳道池的泳道Lane 的ID重复的问题
		defXml=dealPool(defXml);
		
		bpmDefData.defXml = defXml;
	}
	@Override
	public String getBpmnXml() {
		return bpmDefData.bpmnXml;
	}
	public void setBpmnXml(String bpmnXml){
		bpmDefData.bpmnXml = bpmnXml;
	}
	@Override
	public boolean isMain() {		
		return "Y".equals(isMain)?true:false;
	}
	
	public BpmDefData getBpmDefData() {
		return bpmDefData;
	}
	public void setBpmDefData(BpmDefData bpmDefData) {
		if(StringUtil.isNotEmpty(this.getDefId())){
			bpmDefData.setId(this.getDefId());
		}
		this.bpmDefData = bpmDefData;
	}
	
	public JSONObject getAuthorizeRight()
	{
		return authorizeRight;
	}
	public void setAuthorizeRight(JSONObject authorizeRight)
	{
		this.authorizeRight = authorizeRight;
	}
	
	
	/**
	 * 处理泳道池的泳道Lane 的ID重复的问题
	 * 
	 * @param defXml
	 */
	private String dealPool(String defXml) {
		// 控制ID不重复
		int v = 1;
		int h = 1;
		// 处理垂直泳道池
		Pattern vRegex = Pattern.compile("<bg:VLane\\s*(id=\"\\w+\")\\s*");
		Matcher vRegexMatcher = vRegex.matcher(defXml);
		while (vRegexMatcher.find()) {
			String vLane = "id=\"vLane" + v + "\"";
			defXml = defXml.replaceFirst(vRegexMatcher.group(1), vLane);
			v++;
		}
		// 处理水平泳道池
		Pattern hRegex = Pattern.compile("<bg:HLane\\s*(id=\"\\w+\")\\s*");
		Matcher hRegexMatcher = hRegex.matcher(defXml);
		while (hRegexMatcher.find()) {
			String hLane = "id=\"hLane" + h + "\"";
			defXml = defXml.replaceFirst(hRegexMatcher.group(1), hLane);
			h++;
		}
		return defXml;
	}
	
	public int getSupportMobile() {
		return supportMobile;
	}
	public void setSupportMobile(int supportMobile) {
		this.supportMobile = supportMobile;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("defId", this.defId) 
		
		.append("name", this.name) 
		 
		.append("defKey", this.defKey) 
		.append("desc", this.desc) 
		.append("typeId", this.typeId) 
		.append("status", this.status) 
		.append("testStatus", this.testStatus) 
		 
		.append("bpmnDefId", this.bpmnDefId) 
		.append("bpmnDeployId", this.bpmnDeployId) 
		.append("version", this.version) 
		.append("mainDefId", this.mainDefId) 
		.append("isMain", this.isMain) 
		.append("reason", this.reason) 
		 
		.append("createBy", this.createBy) 
		.append("createTime", this.createTime) 
		.append("createOrgId", this.createOrgId) 
		.append("updateBy", this.updateBy) 
		.append("updateTime", this.updateTime) 
		.toString();
	}

	public Object clone() {
		DefaultBpmDefinition obj=null;
		try{
			obj=(DefaultBpmDefinition)super.clone();
			obj.setBpmDefData((BpmDefData)bpmDefData.clone());
		}catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
}