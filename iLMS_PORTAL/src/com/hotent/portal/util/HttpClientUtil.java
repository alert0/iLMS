package com.hotent.portal.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.hotent.base.core.util.BeanUtils;


/**
 * 封装一个http请求的工具类
 */
public class HttpClientUtil {
    
    public static JSONObject executeRestfulApi(String restApi,JSONArray params){
    	JSONObject responseMsg = new JSONObject();
    	try {
    		if(BeanUtils.isNotEmpty(params)){
    			postHttp(restApi,params);
    		}else{
    			responseMsg = getHttp(restApi);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return JSONObject.fromObject(responseMsg);
    }
    
    /**
     * get方式
     * @param url
     * @return
     */
    public static JSONObject getHttp(String url) {
        String responseMsg = "";
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
        try {
            httpClient.executeMethod(getMethod);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = getMethod.getResponseBodyAsStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while((len=in.read(buf))!=-1){
                out.write(buf, 0, len);
            }
            responseMsg = out.toString("UTF-8");
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放连接
            getMethod.releaseConnection();
        }
        return JSONObject.fromObject(responseMsg);
    }

    /**
     * post方式
     * @param url
     * @param code
     * @param type
     * @return
     */
    public static JSONObject postHttp(String url,JSONArray params) {
        String responseMsg = "";
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setContentCharset("GBK");
        PostMethod postMethod = new PostMethod(url);
        try {
        	if(BeanUtils.isNotEmpty(params)){
        		JSONObject obj = new JSONObject();
        		for (Object object : params) {
        			JSONObject jsonObj = JSONObject.fromObject(object);
        			obj.put((String)jsonObj.get("name"),jsonObj.get("value"));
        			//postMethod.addParameter((String)jsonObj.get("name"),(String) jsonObj.get("value"));
        		}
        		postMethod.setRequestEntity(new StringRequestEntity(obj.toString(), "application/json", "UTF-8"));
        	}
            httpClient.executeMethod(postMethod);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = postMethod.getResponseBodyAsStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while((len=in.read(buf))!=-1){
                out.write(buf, 0, len);
            }
            responseMsg = out.toString("UTF-8");
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        
        return JSONObject.fromObject(responseMsg);
    }

}
