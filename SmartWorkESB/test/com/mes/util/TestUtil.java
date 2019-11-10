package com.mes.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import com.smartwork.esb.transaction.cache.RecordCacheService;
import com.smartwork.esb.transaction.configurator.IfConfigures;
import com.smartwork.esb.transaction.data.ReceiveMasterRecord;
import com.smartwork.esb.util.DataUtil;
import com.smartwork.esb.util.FileUtil;

/**
 * 测试工具
 * @author lanym
 * @create_date 2018年5月9日 上午11:04:55
 */
public class TestUtil {
    
    /**
     * 停止源定时器运行
     * @author lanym
     * @create_date 2018年5月11日 下午2:53:53
     */
    public static void stopJob() {
        
        try {
            
            //加载类
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get("com.smartwork.esb.job.PollingJob");
            
            //修改原方法名
            CtMethod method = cc.getDeclaredMethod("execute");
            method.insertBefore("if(true)return;");
            
            //加载到运行库中
            cc.toClass();
            
        } catch(Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 改变为测试发送服务
     * @author lanym
     * @create_date 2018年5月9日 上午11:05:24
     * @param clazz 测试发送服务类名
     */
    public static void changeToTestSenderService(String clazz) {
        
        try {
        
            //加载类
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get("com.smartwork.esb.transaction.configurator.IfConfigures");
            
            //修改原方法名
            CtMethod method = cc.getDeclaredMethod("getConfig");
            method.setName("getConfigTemp");
            
            //增加原方法
            StringBuffer buffer = new StringBuffer();
            buffer.append("public static com.smartwork.esb.config.po.TIfConfig getConfig(){")
                  .append("    com.smartwork.esb.config.po.TIfConfig config = getConfigTemp();")
                  .append("    if(null != config) {")
                  .append("        config.setServiceClass(\"").append(clazz).append("\");")
                  .append("    }")
                  .append("    return config;")
                  .append("}");
            cc.addMethod(CtNewMethod.make(buffer.toString(), cc));
            
            //加载到运行库中
            cc.toClass();
            
        } catch(Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 读取包文件
     * @author lanym
     * @create_date 2018年1月17日 下午2:46:10
     * @param fileKey
     * @return
     */
    public static String readPackageFile(String fileName) {
        InputStream readerStream = null; 
        BufferedReader reader = null;
        String result = null;
        
        try {
            readerStream = TestUtil.class.getResourceAsStream(fileName);    
            reader = new BufferedReader(new InputStreamReader(readerStream, "utf-8")); 
            
            StringBuffer sb = new StringBuffer();
            String line = null;
            while((line = reader.readLine()) != null) {
                sb.append(line); 
            }
            result = sb.toString();
            
        } catch(Throwable e) {
            throw new RuntimeException(e);
        } finally {
            FileUtil.close(reader);
            FileUtil.close(readerStream);
        }
        
        return result;
    }
    
    /**
     * 复制文件到文件存储目录
     * @author lanym
     * @create_date 2018年5月9日 上午11:08:35
     * @param filePath 相对src的包内的文件相对路径
     * @return 文件主键
     */
    public static String copyToStoreFolder(String filePath) {
        InputStream is = null;
        OutputStream os = null;
        String result = DataUtil.getUUID();
        
        try {
            
            //获取文件扩展名
            String ext = filePath.substring(filePath.lastIndexOf("."));
            
            //规范话文件
            filePath = filePath.replaceAll("\\\\", "/");
            if (!filePath.startsWith("/")) {
                filePath = "/" + filePath;
            }
            
            is = TestUtil.class.getResourceAsStream(filePath);
            os = new FileOutputStream(IfConfigures.getStorageFolder() + result + ext, false);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len); 
            }
        } catch(Throwable e) {
            throw new RuntimeException(e);
        } finally {
            FileUtil.close(is);
            FileUtil.close(os);
        }
        
        return result;
    }
    
    /**
     * 读取文件存储目录的文件文件
     * @author lanym
     * @create_date 2018年1月17日 下午2:46:10
     * @param fileKey
     * @return
     */
    public static String readStoreFolderFile(String fileName) {
        
        InputStream readerStream = null; 
        BufferedReader reader = null;
        String result = null;
        
        try {
            readerStream = new FileInputStream(IfConfigures.getStorageFolder() + fileName);   
            reader = new BufferedReader(new InputStreamReader(readerStream, "utf-8")); 
            
            StringBuffer sb = new StringBuffer();
            String line = null;
            while((line = reader.readLine()) != null) {
                sb.append(line); 
            }
            result = sb.toString();
            
        } catch(Throwable e) {
            throw new RuntimeException(e);
        } finally {
            FileUtil.close(reader);
            FileUtil.close(readerStream);
        }
        
        return result;
    }
    
    /**
     * 获取日期
     * @author lanym
     * @create_date 2018年5月9日 下午4:04:41
     * @param diffTime
     * @return
     */
    public static Date getDate(long diffTime) {
        return new Date(new Date().getTime() +  diffTime);
    }
    
    /**
     * 数据转换成list
     * @author lanym
     * @create_date 2018年5月10日 下午5:15:38
     * @param val
     * @return
     */
    public static List<String> convert(String... val) {
        List<String> result = new ArrayList<String>();
        
        if (null != val) {
            for (String item : val) {
                result.add(item);
            }
        }
        
        return result;
    }
    
    /**
     * 打印接收
     * @author lanym
     * @create_date 2018年5月11日 上午10:32:21
     * @param thirdReceiveRecordList
     */
    public static void print(List<ReceiveMasterRecord> thirdReceiveRecordList, String tableId) {
        if (DataUtil.isEmpty(thirdReceiveRecordList)) {
            return;
        }
        
        for (ReceiveMasterRecord thirdReceiveRecord : thirdReceiveRecordList) {
            Map<String, Object> thirdMap = RecordCacheService.getInstance().get(thirdReceiveRecord);
            System.out.println(tableId + ":" + thirdMap);
            
            for (String childTableId : thirdReceiveRecord.getTableIdList()) {
                print(thirdReceiveRecord.getDetailRecordList(childTableId), childTableId); 
            }
        }
    }
}
