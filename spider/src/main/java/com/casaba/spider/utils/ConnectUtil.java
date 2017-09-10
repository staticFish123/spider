package com.casaba.spider.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConnectUtil {
	
    public static boolean isConnect(){
    	boolean connect = false;
    	Runtime runtime = Runtime.getRuntime();
    	Process process;
		try {
			process = runtime.exec("ping " + "www.baidu.com");
			InputStream is = process.getInputStream(); 
	        InputStreamReader isr = new InputStreamReader(is); 
	        BufferedReader br = new BufferedReader(isr); 
	        String line = null; 
	        StringBuffer sb = new StringBuffer(); 
	        while ((line = br.readLine()) != null) { 
	            sb.append(line); 
	        } 
	        System.out.println("返回值为:"+sb);  
	        is.close(); 
	        isr.close(); 
	        br.close(); 

	        if (null != sb && !sb.toString().equals("")) { 
//	            String logString = ""; 
	            if (sb.toString().indexOf("TTL") > 0) { 
	                // 网络畅通  
	            	connect = true;
	            } else { 
	                // 网络不畅通  
	            	connect = false;
	            } 
	        } 
		} catch (IOException e) {
			e.printStackTrace();
		} 
        return connect;
    }

}
