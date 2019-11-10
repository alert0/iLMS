package com.mes.client;

import org.junit.Test;

import com.mes.util.TestUtil;
import com.smartwork.esb.dict.GlobalParameters;
import com.smartwork.esb.dict.IfParamNames;
import com.smartwork.esb.dict.ReceiveTableIDs;
import com.smartwork.esb.transaction.configurator.IfConfigures;
import com.smartwork.esb.transaction.data.ReceiveRecords;
import com.smartwork.esb.util.SpringBeanUtil;

public class WSSenderServiceTest {

    @Test
    public void test() {
        
        IfConfigures.setStorageFolder(GlobalParameters.getStorageFolder()); 
        
        //项目运行起来后，测试配置问题
        String url = "http://127.0.0.1:8080/SmartWorkESB/services/Service"; //webservice
        String actionURL = "http://service.mes.com/Invoke"; //方法父节点的targetNamespace+方法名
        String paramName = "parameters"; //方法参数名；如果没有该参数，则直接加载包文件作为参数
        String fileKey = TestUtil.copyToStoreFolder("/com/mes/client/Test.xml"); //复制文件
        
        IfConfigures.getQueryParam().put("FROM", "MESSmartWork@123");
        IfConfigures.getQueryParam().put("FUN_NAME", "XXX_DR202");
        IfConfigures.getQueryParam().put(IfParamNames.SERVER_URL, url);
        IfConfigures.getQueryParam().put(IfParamNames.ACTION_URL, actionURL);
        IfConfigures.getQueryParam().put(IfParamNames.REQUEST_PARAM_NAME, paramName);
        IfConfigures.setThirdQueryFileKey(fileKey); //设置后会读取复制后的文件内容
        
        WSSenderService service = new WSSenderService(); //输入对象或字符串，返回字符串
        service.setFileLogService(SpringBeanUtil.getFileLogService());
        service.setReceiveLogService(SpringBeanUtil.getReceiveLogService());
        ReceiveRecords receiveRecords = service.send(null);
        
        System.out.println(TestUtil.readStoreFolderFile(receiveRecords.getReceiveFileKey() + ".xml")); 
        TestUtil.print(receiveRecords.getMasterRecordList(ReceiveTableIDs.MESSAGE), ReceiveTableIDs.MESSAGE);
    }

}
