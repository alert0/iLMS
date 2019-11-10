package com.hanthink.mes.print.bean;

public class MTOC {

	private String model = null;
	
	private String type = null;
	
	private String option = null;
	
	private String outerCode = null;
	
	private String innerCode = null;
	
	public MTOC() {
		
	}
	
	public MTOC(String model, String type, String option,
			String outerCode, String innerCode) {
		this.model = model;
		this.type = type;
		this.option = option;
		this.outerCode = outerCode;
		this.innerCode = innerCode;
	}
	
	public MTOC(String mtoc) {
		this.model = mtoc.substring(0, 4);
		this.type = mtoc.substring(5, 8);
		this.option =  mtoc.substring(9, 11);
		this.outerCode = mtoc.substring(12, 14);
		this.innerCode = mtoc.substring(15, mtoc.length());
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getOuterCode() {
		return outerCode;
	}

	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	
	@Override
	public String toString() {
		return model + "-" + type + "-" + option + "-" + outerCode + "-" + innerCode;
	}
}
