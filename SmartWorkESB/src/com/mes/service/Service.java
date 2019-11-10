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
 * webservice����
 * @author lanym
 * @create_date 2018��11��2�� ����8:51:26
 */
public class Service {
    
    private Log log = LogFactory.getLog("���շ���");
    
    /**
     * XML����
     */
    private XmlReceiver receiver;
    public void setReceiver(XmlReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * ������־����
     */
    private ReceiveLogService receiveLogService;
    public void setReceiveLogService(ReceiveLogService receiveLogService) {
        this.receiveLogService = receiveLogService;
    }
    
    /**
     * �첽����XML��Invoke������ʷ���������һ����д��
     * @author lanym
     * @create_date 2018��11��2�� ����8:51:52
     * @param from ��֤��
     * @param token XML����Ψһ��ʶ
     * @param funcName �ӿڴ���
     * @param parameters XML����
     * @return ������ɣ�OK��������������Ϣ
     */
    public Result Invoke(String from, String token, String funcName, String parameters){
        
        //�������
    	Result returnFlag = new Result(true); 
        String ifCode = null;
        String fromSys = null;
        String handCode = null;
        boolean isPrepared = false; //�Ƿ�׼�����
        
        try {
            
        	//from��֤
        	int fromIndex = null == from ? -1 : from.indexOf("_"); 
        	if(fromIndex < 0){
        		returnFlag.error("from���ݲ��������⣬��ȷ��");
                log.error(String.format("token[%1$s],funcName[%2$s]�쳣��%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
        	}
        	//�õ�������
        	handCode =  from.substring(fromIndex + 1);
        	
        	if(!GlobalParameters.getActionValidKey().equals(handCode)){
        		//������ϵͳ���õ������벻һ��
        		returnFlag.error("from���ݲ��������⣬��ȷ��");
                log.error(String.format("token[%1$s],funcName[%2$s]�쳣��%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
        	}
        	//��ȡ�Է���Դϵͳ
        	fromSys = from.substring(0, fromIndex);
        	if(!"MES".equals(fromSys) && !"SAP".equals(fromSys) && !"G-BOM".equals(fromSys)){
        		//������ϵͳ���õ������벻һ��
        		returnFlag.error("ϵͳδ���룬��ȷ��");
                log.error(String.format("token[%1$s],funcName[%2$s]�쳣��%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
        	}
            
            //��ȡ�ӿڴ���
            int index = null == funcName ? -1 : funcName.lastIndexOf("_"); 
            if (index < 0) {
            	returnFlag.error("funcName���ݲ��������⣬��ȷ��");
                log.error(String.format("token[%1$s],funcName[%2$s]�쳣��%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
            }
            ifCode = funcName.substring(index + 1);

            if (DataUtil.isEmpty(ifCode)) {
            	returnFlag.error("funcName���ݲ��������⣬��ȷ��");
                log.error(String.format("token[%1$s],funcName[%2$s]�쳣��%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
            }
            if (!receiver.exists(ifCode)) {
            	returnFlag.error(String.format("�ӿ�%1$s�����ڣ���ȷ��", ifCode));
                log.error(String.format("token[%1$s],funcName[%2$s]�쳣��%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
            }
            
            //�ж������Ƿ�Ϊ��
            if (DataUtil.isEmpty(parameters)) {
                returnFlag.error("�����xmlΪ��");
                log.error(String.format("token[%1$s],funcName[%2$s]�쳣��%3$s", token, funcName, returnFlag.getResult()));
                return returnFlag;
            }
            
            //�ӿ�׼��
            receiver.prepared(ifCode);
            isPrepared = true;
            IfConfigures.setThirdKey(token); //���öԷ�Ψһ��ʶ
            
            //���ӹ�����־
            receiveLogService.addProcess(String.format("��ʼ�첽����%1$s�ļ�...", ifCode));
            
            //�����ļ�
            String receiveFileKey = receiver.saveToFile(parameters, true);
            IfConfigures.setReceiveFileKey(receiveFileKey);
            
        } catch(SmartWorkESBException e) {
            String message = DataUtil.getMessage(e);
            returnFlag.error(message);
            log.error(String.format("token[%1$s],funcName[%2$s]�쳣��%3$s", token, funcName, returnFlag.getResult()));
        } catch(Throwable e) {
            String message = DataUtil.getMessage(e);
            receiveLogService.add(null, ReceiveResults.FAILURE, message, LogTypes.ENTRY); //���쳣д�뵽��־
            returnFlag.error(message);
            log.error(String.format("token[%1$s],funcName[%2$s]�쳣��%3$s", token, funcName, returnFlag.getResult()));
        } finally {
            
            if (isPrepared) {
                receiveLogService.addProcess(String.format("�첽����%1$s�ļ����", ifCode));//�����һ��ִ��
            }
            
            AbsReceiver.release(); //�ͷŻ�������
        }
        
        return returnFlag;
    }
}
