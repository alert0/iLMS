package com.hotent.bo.api.instance;

import com.hotent.bo.api.model.BoData;

/**
 * 数据转换接口。
 * @author yongguo
 *
 */
public interface DataTransform {
	
	/**
	 * 将字符串转换成BoData对象。
	 * @param data
	 * @return
	 */
	BoData parse(String data);
	
	/**
	 * 根据boData获取字符串数据，可以是json，或者 xml。
	 * @param boData
	 * @return
	 */
	String getByData(BoData boData,boolean needInit);
	
	
	

}
