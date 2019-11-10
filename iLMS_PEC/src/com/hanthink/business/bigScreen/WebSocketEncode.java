package com.hanthink.business.bigScreen;

import java.util.List;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import net.sf.json.JSONArray;

public class WebSocketEncode implements Encoder.Text<Map<String, List<?>>>{

	@Override
	public void destroy() {}

	@Override
	public void init(EndpointConfig endpointConfig) {}

	@Override
	public String encode(Map<String, List<?>> map) throws EncodeException {
		JSONArray jsonArray = JSONArray.fromObject(map);
		return jsonArray.toString();
	}

}
