package com.mes.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

import com.smartwork.esb.dict.ReceiveTableIDs;
import com.smartwork.esb.transaction.configurator.IfConfigures;
import com.smartwork.esb.transaction.data.QueryRecords;
import com.smartwork.esb.transaction.data.ReceiveMasterRecord;
import com.smartwork.esb.transaction.data.ReceiveRecords;
import com.smartwork.esb.transaction.pub.SmartWorkESBException;
import com.smartwork.esb.transaction.ws.DefaultWSSenderService;

/**
 * webservice发送客户端（有效期：1次调用发送任务）
 * @author lanym
 * @create_date 2018年11月2日 上午8:55:30
 */
public class MESTestWSSenderService extends DefaultWSSenderService {

    /**
     * 对方返回的错误信息
     */
    private Map<String, Object> thirdSuccessMap;
    
    @Override
    public ReceiveRecords send(QueryRecords thirdQueryRecords) {
        try {
            
            ReceiveRecords thirdReceiveRecords = super.send(thirdQueryRecords);
            
            //设置固定错误信息（小数据，不缓存）
            thirdReceiveRecords.setReceiveFile(false);
            List<ReceiveMasterRecord> thirdRecordList = new ArrayList<ReceiveMasterRecord>();
            thirdRecordList.add(new ReceiveMasterRecord(thirdSuccessMap));
            thirdReceiveRecords.setMasterRecordList(ReceiveTableIDs.MESSAGE, thirdRecordList);
            
            return thirdReceiveRecords;
            
        } catch(RuntimeException e) {
            String message = e.getMessage();
            if (null != message && message.contains("The MessageContext does not have an associated SOAPFault")) {
                throw new SmartWorkESBException("发送参数不正确，请确认");
            } else {
                throw e;
            }
        }
    }
    
    @Override
    protected OMElement getRequestOMElement() {
        
        //获取parameters节点
        OMElement result = super.getRequestOMElement();
        OMElement parametersElment = result.getFirstElement();  
        
        //获取公用变量
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace namespace = result.getNamespace();
        
        //插入from节点
        {
            String from = IfConfigures.getQueryParam("FROM"); //来自接口参数
            OMElement element = factory.createOMElement("from", namespace);
            element.setText(from);
            parametersElment.insertSiblingBefore(element);
        } 
        
        //插入token节点
        {
            String token = IfConfigures.getThirdQueryFileKey(); //来自生成转换后文件标识
            OMElement element = factory.createOMElement("token", namespace);
            element.setText(token);
            parametersElment.insertSiblingBefore(element);
        }
        
        //插入funcName节点
        {
            String funcName = IfConfigures.getQueryParam("FUN_NAME"); //来自接口参数
            OMElement element = factory.createOMElement("funcName", namespace);
            element.setText(funcName);
            parametersElment.insertSiblingBefore(element);
        }
                
        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected String saveResponseOMElement(OMElement responseOMElement) {
        
        //因返回的节点已经固定，所以直接返回内容
        if (null != responseOMElement) {
            OMElement returnOMElement = responseOMElement.getFirstElement(); //获取return节点
            if (null != returnOMElement) {
                Iterator<OMElement> iterator = returnOMElement.getChildElements();
                OMElement element = null;
                thirdSuccessMap = new HashMap<String, Object>();
                while (iterator.hasNext()) {
                    element = iterator.next();
                    thirdSuccessMap.put(element.getLocalName(), element.getText());
                }
                
                //修改跟其他系统的不一致的的地方
                thirdSuccessMap.put("InvokeResult", thirdSuccessMap.get("invokeResult"));
                thirdSuccessMap.remove("invokeResult");
            }
        }
        
        return super.saveResponseOMElement(responseOMElement);
    }
}
