package com.mes.service;

public class Result {

	private boolean invokeResult;
	private String result;

	public Result(boolean invokeResult) {
		this.invokeResult = invokeResult;
		this.result = "OK";
	}

	public boolean isInvokeResult() {
		return invokeResult;
	}

	public void setInvokeResult(boolean invokeResult) {
		this.invokeResult = invokeResult;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public void error(String result) {
		this.invokeResult = false;
		this.result = result;
	}
}
