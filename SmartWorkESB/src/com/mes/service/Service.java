package com.mes.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.smartwork.esb.dict.GlobalParameters;
import com.smartwork.esb.dict.LogTypes;
import com.smartwork.esb.dict.ReceiveResults;
import com.smartwork.esb.transaction.configurator.IfConfigures;
import com.smartwork.esb.transaction.pub.AbsReceiver;
import com.smartwork.esb.transaction.pub.SmartWorkESBException;
import com.smartwork.esb.transaction.service.ReceiveLogService;
import com.smartwork.esb.transaction.xml.XmlReceiver;
import com.smartwork.esb.util.DataUtil;

/**
 * webservice服务
 * @author lanym
 * @create_date 2018年11月2日 上午8:51:26
 */
public class Service {
    
    private Log log = LogFactory.getLog("接收服务");
    
    /**
     * XML接收
     */
    private XmlReceiver receiver;
    public void setReceiver(XmlReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * 接收日志服务
     */
    private ReceiveLogService receiveLogService;
    public void setReceiveLogService(ReceiveLogService receiveLogService) {
        this.receiveLogService = receiveLogService;
    }
    
    /**
     * 异步接收XML（Invoke：因历史遗留问题第一个大写）
     * @author lanym
     * @create_date 2018年11月2日 上午8:51:52
     * @param from 验证码
     * @param token XML数据唯一标识
     * @param funcName 接口代码
     * @param parameters XML内容
     * @return 接收完成：OK，其他：错误信息
     */
    public Result Invoke(String from, String token, String funcName, String parameters){
        
        //定义变量
    	Result returnFlag = new Result(true); 
        String ifCode = null;
        String fromSys = null;
        String handCode = null;
        boolean isPrepared = false; //是否准备完毕
        
        try {
            
        	//from验证
        	int fromIndex = null == from ? -1 : from.indexOf("_"); 
        	if(fromIndex < 0){
        		returnFlag.error("from数据参数有问题，请确认");
                log.error(String.format("token[%1$s],funcName[%2$s]异常：%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
        	}
        	//拿到握手码
        	handCode =  from.substring(fromIndex + 1);
        	
        	if(!GlobalParameters.getActionValidKey().equals(handCode)){
        		//表明跟系统配置的握手码不一致
        		returnFlag.error("from数据参数有问题，请确认");
                log.error(String.format("token[%1$s],funcName[%2$s]异常：%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
        	}
        	//获取对方来源系统
        	fromSys = from.substring(0, fromIndex);
        	if(!"MES".equals(fromSys) && !"SAP".equals(fromSys) && !"G-BOM".equals(fromSys)){
        		//表明跟系统配置的握手码不一致
        		returnFlag.error("系统未接入，请确认");
                log.error(String.format("token[%1$s],funcName[%2$s]异常：%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
        	}
            
            //获取接口代码
            int index = null == funcName ? -1 : funcName.lastIndexOf("_"); 
            if (index < 0) {
            	returnFlag.error("funcName数据参数有问题，请确认");
                log.error(String.format("token[%1$s],funcName[%2$s]异常：%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
            }
            ifCode = funcName.substring(index + 1);

            if (DataUtil.isEmpty(ifCode)) {
            	returnFlag.error("funcName数据参数有问题，请确认");
                log.error(String.format("token[%1$s],funcName[%2$s]异常：%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
            }
            if (!receiver.exists(ifCode)) {
            	returnFlag.error(String.format("接口%1$s不存在，请确认", ifCode));
                log.error(String.format("token[%1$s],funcName[%2$s]异常：%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
            }
            
            //判断数据是否为空
            if (DataUtil.isEmpty(parameters)) {
                returnFlag.error("传入的xml为空");
                log.error(String.format("token[%1$s],funcName[%2$s]异常：%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
            }
            
            //接口准备
            receiver.prepared(ifCode);
            isPrepared = true;
            IfConfigures.setThirdKey(token); //设置对方唯一标识
            
            //增加过程日志
            receiveLogService.addProcess(String.format("开始异步保存%1$s文件...", ifCode));
            
            //保存文件
            String receiveFileKey = receiver.saveToFile(parameters, true);
            IfConfigures.setReceiveFileKey(receiveFileKey);
            
        } catch(SmartWorkESBException e) {
            String message = DataUtil.getMessage(e);
            returnFlag.error(message);
            log.error(String.format("token[%1$s],funcName[%2$s]异常：%3$s", token, funcName, returnFlag.getResult()));
        } catch(Throwable e) {
            String message = DataUtil.getMessage(e);
            receiveLogService.add(null, ReceiveResults.FAILURE, message, LogTypes.ENTRY); //将异常写入到日志
            returnFlag.error(message);
            log.error(String.format("token[%1$s],funcName[%2$s]异常：%3$s", token, funcName, returnFlag.getResult()));
        } finally {
            
            if (isPrepared) {
                receiveLogService.addProcess(String.format("异步保存%1$s文件完成", ifCode));//必须第一步执行
            }
            
            AbsReceiver.release(); //释放缓存数据
        }
        
        return returnFlag;
    }
}
