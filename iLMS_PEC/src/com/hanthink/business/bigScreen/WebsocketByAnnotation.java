package com.hanthink.business.bigScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
 
@ServerEndpoint(value = "/websocket", encoders = {WebSocketEncode.class})
public class WebsocketByAnnotation {

    private static AtomicInteger onlineCount = new AtomicInteger(0);
    
    protected static ConcurrentHashMap<String, WebsocketByAnnotation> webSocketSet = new ConcurrentHashMap<String, WebsocketByAnnotation>();

    private Session session;
    
    private String targetIp;
    
    public WebsocketByAnnotation() {
        System.out.println(" WebSocket init~~~");
    }
    
    //连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
 
        addOnlineCount();
    }
    //连接关闭调用的方法
    @OnClose
    public void onClose(){
    	
    	webSocketSet.remove(targetIp);
    	
        subOnlineCount();
    }
    //收到客户端消息后调用的方法
    @OnMessage
    public void onMessage(String message, Session session) {
        //发送数据到指定客户端
        try {
        	this.targetIp = message;
        	webSocketSet.put(message, this);
        	List<String> list = new ArrayList<String>();
        	list.add(message.toString());
        	sendMessageToUser(this.targetIp, list);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
 
    //发生错误时调用
    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }
 
    //发送数据
    public static void sendMessageToUser(String targetUser, List<?> list) throws IOException{
    	Map<String, List<?>> resultMap = new HashMap<String, List<?>>();
    	resultMap.put("resultMap", list);
    	webSocketSet.get(targetUser).sendMessage(resultMap);
    }
    
    //发送数据
    public void sendMessage(Map<String, List<?>> resultMap) throws IOException {
        try {
			this.session.getBasicRemote().sendObject(resultMap);
		} catch (EncodeException e) {
			e.printStackTrace();
		}
    }
    
    public static void sendToUser(String targetIp,List<?> list) {
    	try {
			sendMessageToUser(targetIp, list);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static int getOnlineCount() {
        return onlineCount.get();
    }
 
    public static void addOnlineCount() {
        onlineCount.incrementAndGet();
    }
 
    public static void subOnlineCount() {
        onlineCount.decrementAndGet();
    }
}