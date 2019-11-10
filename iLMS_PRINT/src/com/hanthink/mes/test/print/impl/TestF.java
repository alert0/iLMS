package com.hanthink.mes.test.print.impl;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.util.ArrayList;  
import java.util.List;  
  
  
public class TestF {  
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {  
        List list = readLog("d://Nopublic_log_2010-11-12_Y.txt");  
        //checkFiles("f:\\user\\"+list.get(0).toString(),"J:\\178_f\\"+list.get(0).toString());  
        for(int i =0;i<list.size();i++){  
            System.out.println(list.get(i).toString());  
            checkFiles("f:\\user\\"+list.get(i).toString(),"J:\\178_f\\"+list.get(i).toString());  
        }  
        System.out.println("over my test");  
    }  
  
    private static void checkFiles(String upload_Dir, String download_Dir) {  
        try {  
            File fold_download_Dir = new File(download_Dir);  
            if(!fold_download_Dir.exists()){  
                fold_download_Dir.mkdirs();  
            }  
            Runtime rt = Runtime.getRuntime();  
            Process proc = rt.exec("xcopy /e "+upload_Dir+" "+download_Dir);  
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");   
            StreamGobbler outputGobbler = new  
            StreamGobbler(proc.getInputStream(), "OUTPUT");  
  
            errorGobbler.start();  
            outputGobbler.start();  
            int exitVal = proc.waitFor();  
            System.out.println("ExitValue: " + exitVal);   
              
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
  
  
    @SuppressWarnings("finally")
	private static List<String> readLog(String logpath) {  
        InputStreamReader read = null;  
        List<String> strs = new ArrayList<String>();  
        File log = new File(logpath);  
        try {  
            read = new InputStreamReader(new FileInputStream(log));  
            BufferedReader br = new BufferedReader(read);  
            String data = null;  
            while ((data = br.readLine()) != null) {  
                strs.add(data.trim());  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            return strs;  
        }  
    }  
}  
