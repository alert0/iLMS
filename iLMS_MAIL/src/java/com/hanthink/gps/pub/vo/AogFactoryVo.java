package com.hanthink.gps.pub.vo;

/**
 * 
 * 广汽乘用车公共模块-订购模块专用-卸货口与工厂关系
 * 
 * 日期        版本    作者         说 明
 * 
 * 2016 03/31 0.01  anMin    新建
 * 
 */
public class AogFactoryVo {
	
	private String code;
	private String name;
	private String aogFactory;
	
	public AogFactoryVo() {
		
	}
	
	public AogFactoryVo(String code, String name, String aogFactory) {
		this.code = code;
		this.name = name;
		this.aogFactory = aogFactory;
	}
	
	/**
     * code getter
     * @return the code
     */
    public String getCode() {
    	return code;
    }



	/**
     * code setter
     * @param code
     * 			the code to set
     */
    public void setCode(String code) {
    	this.code = code;
    }



	/**
     * name getter
     * @return the name
     */
    public String getName() {
    	return name;
    }



	/**
     * name setter
     * @param name
     * 			the name to set
     */
    public void setName(String name) {
    	this.name = name;
    }



	/**
     * aogFactory getter
     * @return the aogFactory
     */
    public String getAogFactory() {
    	return aogFactory;
    }



	/**
     * aogFactory setter
     * @param aogFactory
     * 			the aogFactory to set
     */
    public void setAogFactory(String aogFactory) {
    	this.aogFactory = aogFactory;
    }

}
