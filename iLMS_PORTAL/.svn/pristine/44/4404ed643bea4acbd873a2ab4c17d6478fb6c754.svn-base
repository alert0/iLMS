package com.hotent.base.core.util.gson;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hotent.base.core.util.time.DateFormatUtil;


public class DateSerializer implements JsonDeserializer<Date>,JsonSerializer<Date>{
	public Date deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		String jsonPrimitiveStr = json.getAsJsonPrimitive().getAsString();
		//毫秒数
		if(jsonPrimitiveStr.matches("^\\d+$")){
			return new Date(Long.parseLong(jsonPrimitiveStr));
		}
		try {
			Date date = DateFormatUtil.parse(jsonPrimitiveStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public JsonElement serialize(Date arg0, Type arg1, JsonSerializationContext arg2) {
		String s = DateFormatUtils.format(arg0, "yyyy-MM-dd'T'HH:mm:ss.sssZZ");
		return new JsonPrimitive(s);
	}
}
