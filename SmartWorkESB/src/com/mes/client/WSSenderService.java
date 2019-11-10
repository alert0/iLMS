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
 * webservice���Ϳͻ��ˣ���Ч�ڣ�1�ε��÷�������
 * @author lanym
 * @create_date 2018��11��2�� ����8:55:30
 */
public class WSSenderService extends DefaultWSSenderService {

    /**
     * �Է����صĴ�����Ϣ
     */
    private Map<String, Object> thirdSuccessMap;
    
    @Override
    public ReceiveRecords send(QueryRecords thirdQueryRecords) {
        try {
            
            ReceiveRecords thirdReceiveRecords = super.send(thirdQueryRecords);
            
            //���ù̶�������Ϣ��С���ݣ������棩
            thirdReceiveRecords.setReceiveFile(false);
            IfConfigures.setReceiveFileKey(thirdReceiveRecords.getReceiveFileKey());
            List<ReceiveMasterRecord> thirdRecordList = new ArrayList<ReceiveMasterRecord>();
            thirdRecordList.add(new ReceiveMasterRecord(thirdSuccessMap));
            thirdReceiveRecords.setMasterRecordList(ReceiveTableIDs.MESSAGE, thirdRecordList);
            
            return thirdReceiveRecords;
            
        } catch(RuntimeException e) {
            String message = e.getMessage();
            if (null != message && message.contains("The MessageContext does not have an associated SOAPFault")) {
                throw new SmartWorkESBException("���Ͳ�������ȷ����ȷ��");
            } else {
                throw e;
            }
        }
    }
    
    @Override
    protected OMElement getRequestOMElement() {
        
        //��ȡparameters�ڵ�
        OMElement result = super.getRequestOMElement();
        OMElement parametersElment = result.getFirstElement();  
        
        //��ȡ���ñ���
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace namespace = result.getNamespace();
        
        //����from�ڵ�
        {
            String from = IfConfigures.getQueryParam("FROM"); //���Խӿڲ���
            OMElement element = factory.createOMElement("from", namespace);
            element.setText(from);
            parametersElment.insertSiblingBefore(element);
        } 
        
        //����token�ڵ�
        {
            String token = IfConfigures.getThirdQueryFileKey(); //��������ת�����ļ���ʶ
            OMElement element = factory.createOMElement("token", namespace);
            element.setText(token);
            parametersElment.insertSiblingBefore(element);
        }
        
        //����funcName�ڵ�
        {
            String funcName = IfConfigures.getQueryParam("FUN_NAME"); //���Խӿڲ���
            OMElement element = factory.createOMElement("funcName", namespace);
            element.setText(funcName);
            parametersElment.insertSiblingBefore(element);
        }
                
        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected String saveResponseOMElement(OMElement responseOMElement) {
        
        //�򷵻صĽڵ��Ѿ��̶�������ֱ�ӷ�������
        if (null != responseOMElement) {
            Iterator<OMElement> iterator = responseOMElement.getChildElements();
            OMElement element = null;
            thirdSuccessMap = new HashMap<String, Object>();
            while (iterator.hasNext()) {
                element = iterator.next();
                thirdSuccessMap.put(element.getLocalName(), element.getText());
            }
        }
        
        return super.saveResponseOMElement(responseOMElement);
    }
}
