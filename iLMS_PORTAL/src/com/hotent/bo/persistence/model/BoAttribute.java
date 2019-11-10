package com.hotent.bo.persistence.model;
import com.hotent.bo.api.model.BaseAttribute;


 /**
 * BO 实体属性。
 * <pre> 
 * 描述：业务实体定义属性 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-01 16:41:22
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BoAttribute extends BaseAttribute{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7508387893576725439L;

	/**
	 *从属实体ID
	 */
	protected String entId; 
	
	
	
	public BoAttribute(){
		
	}
	
	public BoAttribute(BaseAttribute attr){
		this.setFieldName(attr.getFieldName());
		this.setDesc(attr.getDesc());
		this.setDataType(attr.getDataType());
		this.setDefaultValue(attr.getDefaultValue());
		this.setFormat(attr.getFormat());
		this.setIsRequired(attr.getIsRequired());
		this.setAttrLength(attr.getAttrLength());
		this.setDecimalLen(attr.getDecimalLen());
		this.setName(attr.getName());
		this.setBoEnt(attr.getBoEnt());
		this.setSn(attr.getSn());

	}
	
	
	public String getEntId() {
		return entId;
	}
	
	/**
	 * 返回从属实体ID。
	 * @param entId
	 */
	public void setEntId(String entId) {
		this.entId = entId;
	}
}