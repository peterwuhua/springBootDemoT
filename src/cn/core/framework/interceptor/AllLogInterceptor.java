package cn.core.framework.interceptor;


import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.CsvUtil;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;

public class AllLogInterceptor extends HandlerInterceptorAdapter{
	public static Logger log = Logger.getLogger(AllLogInterceptor.class);
	private static String recordFilePath;
	private static boolean isMultithreading = true;
	private static boolean isRecord = true;
	static{
		try {
			recordFilePath = (String)ApplicationUtils.getValue("config.logFile.path");
			isMultithreading = BooleanUtils.toBoolean((String)ApplicationUtils.getValue("config.logFile.isMulti"));
			isRecord = BooleanUtils.toBoolean((String)ApplicationUtils.getValue("config.logFile.isRecord"));
		} catch (Exception e) {
			log.info("获取ActionAspect日志配置信息出错", e);
		}
	}
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(!HandlerMethod.class.isAssignableFrom(handler.getClass())){  
	         return true;  
	     }  
		if(!isRecord)
			return super.preHandle(request, response, handler);
		
		 HandlerMethod methodHandler = (HandlerMethod) handler;  
	     String msg = "";
	     if(null!=CurrentUtils.getCurrent()){
	    	 msg = DateUtils.getCurrDateTimeStr()+"%"+request.getRemoteAddr()+"%"+CurrentUtils.getCurrent().getUserName()+"%"+methodHandler.getMethod().getName() +"%"+ getParameterNames(request);
	     }else{
	    	 //正常登录后操作
	    	 if(null!=request.getParameter("loginname")){
	    		 msg = DateUtils.getCurrDateTimeStr()+"%"+request.getRemoteAddr()+"%"+request.getParameter("loginname")+"%"+"登录系统  "+"%"+" ";
	    	 }else{
	    		 msg = DateUtils.getCurrDateTimeStr()+"%"+request.getRemoteAddr()+"%"+" "+"%"+"打开系统  "+"%"+" ";
	    	 }	 
	    }
	    String[] stringArrays = msg.split("%");
	    String outPutPath = StrUtils.replaceAll(recordFilePath,"\\", File.separator)+File.separator;
	    String fileName = DateUtils.getCurrDateStr()+"_all";
	    if(isMultithreading){
			Thread exportThread = new Thread(new CreateAllLogCsv(stringArrays, outPutPath, fileName));
			exportThread.run();
		}else{
			new CreateAllLogCsv().createLog2Csv(stringArrays, outPutPath, fileName);	
		}
		return super.preHandle(request, response, handler);
	}
	
	@SuppressWarnings("rawtypes")
	private String getParameterNames(HttpServletRequest request) {
		Enumeration en = request.getParameterNames();
        String re = "";
        String parameterName =  null;
        String parameterValue = null;
        int i=0;
        while(en.hasMoreElements()){
        	parameterName = (String)en.nextElement();
        	if(i++>0)re+="&";
            if (parameterName != null) {
                    re+= parameterName + "=";
                    parameterValue= request.getParameter(parameterName);
                    if(parameterValue !=  null)
                       re += parameterValue;
            }
        }
       return re;
	}
}


class CreateAllLogCsv implements Runnable{
	public CreateAllLogCsv() {
		
	}
	
	public CreateAllLogCsv(String[] stringList, String outPutPath,String fileName) {
		createLog2Csv(stringList,outPutPath,fileName);
	}
	
	public void method1(String[] stringList, String outPutPath,String fileName) {   
		createLog2Csv(stringList,outPutPath,fileName);
    }   
	
    public void method3(String[] stringList, String outPutPath,String fileName) { 
    	createLog2Csv(stringList,outPutPath,fileName);
    }  
    
    @SuppressWarnings("rawtypes")
	public void createLog2Csv(String[] stringList, String outPutPath,
			String fileName) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("1", "操作时间");
		map.put("2", "ip");
		map.put("3", "操作人");
		map.put("4", "操作内容");
		map.put("5", "参数");
		List<Map> exprotList = new ArrayList<Map>();
		LinkedHashMap<String, String> datamMap = new LinkedHashMap<String, String>();
		for(int i = 1 ; i < stringList.length + 1; i++){
			datamMap.put(String.valueOf(i), stringList[i-1]);
		}
		exprotList.add(datamMap);
		CsvUtil.createCSVFile(exprotList, map, outPutPath, fileName);
	}

	public static void main(String[] args) {
		
	}

	@Override
	public void run() {
				
	}
}
