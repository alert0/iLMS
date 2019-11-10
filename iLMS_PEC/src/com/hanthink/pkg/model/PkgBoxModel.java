package com.hanthink.pkg.model;

import com.hotent.base.core.model.AbstractModel;

/**
* <p>Title: PkgBoxModel.java<／p>
* <p>Description:箱种信息管理实体类 <／p>
* <p>Company: hanthink<／p>
* @author luoxq
* @date 2018年9月25日
*/

public class PkgBoxModel extends AbstractModel<String>{
	
	/** 特殊的箱CODE值，箱种信息维护时不允许使用该CODE-其它，选择该CODE时允许编辑数值 */
	public static final String BOX_CODE_OTHER = "other";
	
	/** 箱CODE状态-生效 */
	public static final String BOX_STATUS_YES = "1";
	

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 4855280753498838109L;
    /**逻辑主键**/
	private String id;
	/**箱CODE**/
	private String boxCode;
	/**箱种**/
	private String boxType;
	/**数据字典箱种name**/
	private String boxTypeName;
	/**包装长**/
	private  String packLength;
	/**包装宽**/
	private String packWidth;
	/**包装高**/
	private String packHeight;
	/**状态**/
	private String status;
	/**数据字典状态**/
	private String statusName;
	/**工厂代码**/
	private String factoryCode;
	
	/**创建人**/
	private String createUser;
	/**最后修改人**/
	private String lastModifiedUser;
	
	private String value;
	private String label;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

	public String getPackLength() {
		return packLength;
	}

	public void setPackLength(String packLength) {
		this.packLength = packLength;
	}

	public String getPackWidth() {
		return packWidth;
	}

	public void setPackWidth(String packWidth) {
		this.packWidth = packWidth;
	}

	public String getPackHeight() {
		return packHeight;
	}

	public void setPackHeight(String packHeight) {
		this.packHeight = packHeight;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getBoxTypeName() {
		return boxTypeName;
	}

	public void setBoxTypeName(String boxTypeName) {
		this.boxTypeName = boxTypeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
