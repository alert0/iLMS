package com.hotent.bo.instance.impl;

import com.alibaba.fastjson.JSONObject;
import com.hotent.bo.api.instance.BoUtil;
import com.hotent.bo.api.instance.DataTransform;
import com.hotent.bo.api.model.BoData;

public class JsonTransformImpl implements DataTransform {

	@Override
	public BoData parse(String data) {
		JSONObject json=JSONObject.parseObject(data);
		return BoUtil.transJSON(json);
	}

	@Override
	public String getByData(BoData boData,boolean needInitData) {
		JSONObject json=BoUtil. toJSONObject(boData,needInitData);
		return json.toJSONString();
	}
	
	

}
