package com.hanthink.demo.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * 
 * @Desc		: Demo
 * @FileName	: DemoModel.java
 * @CreateOn	: 2018年8月31日 下午2:29:54
 * @author 		: ZUOSL
 *
 *
 * @ChangeList
 * Date				Version		Editor		Reasons
 * 2018年8月31日		V1.0		ZUOSL		新建
 */
public class DemoModel extends AbstractModel<String>{
	
	private static final long serialVersionUID = 5961673427493369837L;

	@Override
	public void setId(String id) {
		this.pkId = id;
	}
	
	@Override
	public String getId() {
		return this.pkId;
	}
	
	/** PKID */
	private String pkId;
	/** 字段一 */
	private String col1;
	/** 字段二 */
	private String col2;
	/** 字段三 */
	private String col3;
	/** 字段四 */
	private String col4;

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}
	
	
}
