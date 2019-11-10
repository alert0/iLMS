package com.hotent.service.parse;

import java.util.Map;

import javax.xml.ws.WebServiceException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.bo.api.model.BoData;

public class WebServiceParamHandler {
    public static final String INPUT = "input";
    public static final String FLOW_OUTPUT = "flowOutput";
    /** 参数类型 ：FLOW_INPUT ,FLOW_OUTPUT ,非流程 **/
    private static ThreadLocal < String > jsonType = new ThreadLocal < String > ();
    private static ThreadLocal < Map < String, Object >> params = new ThreadLocal < Map < String, Object >> ();
    /*** 流程中需要webService返回参数的时候， 需要 解析OutPutParam json将 参数的name 返回 */
    private static ThreadLocal < String > flowOutPutKey = new ThreadLocal < String > ();

    public static Object buildJsonParam(JsonArray jarray,
        JsonElement jsonParamObj, Map paramMap, String type) {
        // 递归 解析参数所需的变量放在线程里（参数传来传去的， 参数列表太长）
        jsonType.set(type);
        params.set(paramMap);
        flowOutPutKey.set("");
        try {
            buildJsonParams(jarray, jsonParamObj);

            if (FLOW_OUTPUT.equals(type))
                return flowOutPutKey.get();
            return null;
        } catch (Exception e) {
            throw new WebServiceException("webService " + type + "Json 解析出错！");
        } finally {
            jsonType.remove();
            params.remove();
            flowOutPutKey.remove();
        }
    }

    /**
     * 将 符合规则的webservice inputJson 转换成交互所定义的JSON类型
     * 
     * @param jarray
     *            所返回的json
     * @param jsonParamObj
     *            输出参数的值
     * @param params
     *            值
     * @jsonType 类型
     */
    // 递归构建jsonParam
    private static void buildJsonParams(JsonArray jarray,JsonElement jsonParamObj) {
        for (JsonElement jelement: jarray) {
            if (!jelement.isJsonObject())
                continue;

            JsonObject jobject = jelement.getAsJsonObject();
            String key = jobject.get("key").getAsString();
            String type = jobject.get("type").getAsString();
            JsonElement bind = jobject.get("bind"); // bind json 对象
            JsonElement genericsValue = jobject.get("generics"); // 泛型

            Boolean generics = false;
            if (BeanUtils.isNotEmpty(genericsValue)) {
                generics = genericsValue.getAsBoolean();
            }
            JsonElement paramJson = null;
            if ((!"Bean".equals(type) && !generics) || jsonType.get().equals("flowOutput")) { // 普通的input
                // 或者output
                paramJson = new JsonPrimitive("");
                paramJson = handlerBind(bind, paramJson);
            } else if ("Bean".equals(type)) {
                JsonArray children = jobject.get("children").getAsJsonArray();
                paramJson = new JsonObject();

                paramJson = handlerBind(bind, paramJson);
                buildJsonParams(children, paramJson);
            } else if (generics) {
                JsonArray children = jobject.get("children").getAsJsonArray();
                int count = children.size();
                // 一种类型的泛型为 集合 两种类型的泛型为 对象
                paramJson = count == 1 ? new JsonArray() : new JsonObject();

                paramJson = handlerBind(bind, paramJson);
                handlerGenerics(children, paramJson);
            }

            if (jsonParamObj.isJsonObject()) {
                jsonParamObj.getAsJsonObject().add(key, paramJson);
            }
        }
    }

    // 处理参数绑定
    private static JsonElement handlerBind(JsonElement bindElement,
        JsonElement jsonElement) {
        Map < String, Object > param = params.get();
        if (BeanUtils.isEmpty(bindElement))
            return jsonElement;
        JsonObject bind = bindElement.getAsJsonObject();

        int type = bind.get("type").getAsInt();
        String value = bind.get("value").getAsString();

        JsonElement jsonVal = null;
        switch (type) {
            // 固定值
            case 1:
                jsonVal = new JsonPrimitive(value);
                break;
                // 流程常量 或者 参数列表
            case 2:
                if (jsonType.get().equals(FLOW_OUTPUT)) {
                    flowOutPutKey.set(value); // output将key设置进去
                } else {
                    jsonVal = new JsonPrimitive(param.get(value).toString());
                }
                break;
                // 脚本
            case 3:
                Map map = params.get();
                Object obj = AppUtil.getBean(GroovyScriptEngine.class)
                    .executeObject(value, map);
                jsonVal = GsonUtil.toJsonTree(obj);
                break;

                // 表单变量，在map中方了Bo 主表的
            case 4:
                String[] BODataKey = value.split("\\.");
                if (BODataKey.length != 2)
                    throw new WebServiceException("BO[" + value + "]数据 格式不合法");
                
                BoData databoject = (BoData) param.get(BODataKey[0]);
                if (databoject == null)
                    throw new WebServiceException("BO ”code:" + BODataKey[0] + "“ 丢失");
               String result =databoject.getString(BODataKey[1]);
               jsonVal = new JsonPrimitive(result);
        }
        if (BeanUtils.isEmpty(jsonVal))
            return jsonElement;
        if (jsonElement.isJsonArray()) {
            if (!jsonVal.isJsonArray()) {
                JsonArray jarray = new JsonArray();
                jarray.add(jsonVal);
                jsonVal = jarray;
            }
        }
        return jsonVal;
    }

    // 处理泛型
    private static void handlerGenerics(JsonArray childrenAry,
        JsonElement jsonElement) {
        JsonElement jelement;
        if (jsonElement.isJsonArray()) {
            jelement = childrenAry.get(0);
        } else {
            jelement = childrenAry.get(1);
        }
        if (!jelement.isJsonObject())
            return;
        JsonObject jobject = jelement.getAsJsonObject();

        String type = jobject.get("type").getAsString();
        Boolean isGenerics = false;
        JsonElement genericsValue = jobject.get("generics");
        if (BeanUtils.isNotEmpty(genericsValue)) {
            isGenerics = genericsValue.getAsBoolean();
        }

        JsonElement generics;

        if ("Bean".equals(type)) {
            generics = new JsonObject();
            JsonArray children = jobject.get("children").getAsJsonArray();
            // 继续构建jsonParam
            buildJsonParams(children, generics);
        }
        // 泛型
        else if (isGenerics) {
            generics = new JsonObject();
            JsonArray children = jobject.get("children").getAsJsonArray();
            // 继续构建jsonParam
            handlerGenerics(children, generics);
        } else {
            generics = new JsonPrimitive("");
        }

        if (jsonElement.isJsonArray()) {
            JsonArray ja = jsonElement.getAsJsonArray();
            if (ja.size() == 0) {
                ja.add(generics);
            }
        } else {
            String key = "";
            JsonElement jsonKey = childrenAry.get(0);
            if (jsonKey.isJsonArray() || jsonKey.isJsonObject()) {
                key = jsonKey.toString();
            } else if (jsonKey.isJsonPrimitive()) {
                key = jsonKey.getAsString();
            }
            jsonElement.getAsJsonObject().add(key, generics);
        }
    }
}
