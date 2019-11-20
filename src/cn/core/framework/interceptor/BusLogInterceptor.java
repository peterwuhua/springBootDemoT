package cn.core.framework.interceptor;


import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.CsvUtil;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.ILogRecordService;
/**
 * 业务日志 拦截器
 * @author QuJunLong
 *
 */
public class BusLogInterceptor extends HandlerInterceptorAdapter{
	public static Logger log = Logger.getLogger(BusLogInterceptor.class);
	private static String recordFilePath;
	static{
		try {
			recordFilePath = (String)ApplicationUtils.getValue("config.logFile.path");
		} catch (Exception e) {
			log.info("获取ActionAspect日志配置信息出错", e);
		}
	}
	
	@Autowired
	ILogRecordService logRecordService;
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

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}
		HandlerMethod methodHandler = (HandlerMethod) handler;  
		Log log = methodHandler.getMethodAnnotation(Log.class);  
        if (log == null) {
        	return super.preHandle(request, response, handler);
        }  	
        String[] stringArrays = new String[6];
        stringArrays[0] = request.getRemoteAddr();//地址
        stringArrays[1] = log.module();//操作模块
        stringArrays[2] = log.operation().toString();//操作动作
        stringArrays[3] = log.content();//描述
        stringArrays[4] = methodHandler.getMethod().getName();//操作方法
        stringArrays[5] = getParameterNames(request);//参数
        logRecordService.add(stringArrays);
        if(DateUtils.getDay().equals("01")) {
        	List<String[]> logList=logRecordService.delete4Quarter();
        	String outPutPath = StrUtils.replaceAll(recordFilePath,"\\", File.separator)+File.separator;
    	    String fileName = DateUtils.getCurrDateStr()+"_backup";
        	Thread exportThread = new Thread(new CreateBusLogCsv(logList, outPutPath, fileName));
			exportThread.run();
        }
		return super.preHandle(request, response, handler);
	}
}

class CreateBusLogCsv implements Runnable{
	private List<String[]> logList=null;
	private String outPutPath=null;
	private String fileName=null;
	public CreateBusLogCsv() {
	}
	public CreateBusLogCsv(List<String[]> stringList, String outPutPath,String fileName) {
		this.logList=stringList;
		this.outPutPath=outPutPath;
		this.fileName=fileName;
	}
	@Override
	public void run() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("1", "操作时间");
		map.put("2", "ip");
		map.put("3", "操作人");
		map.put("4", "模块");
		map.put("5", "动作");
		map.put("6", "描述");
		map.put("7", "方法");
		map.put("8", "参数");
		List<Map<String,String>> exprotList = new ArrayList<Map<String,String>>();
		LinkedHashMap<String, String> datamMap = new LinkedHashMap<String, String>();
		for (String[] stringList : logList) {
			for(int i = 1 ; i < stringList.length + 1; i++){
				datamMap.put(String.valueOf(i), stringList[i-1]);
			}
			exprotList.add(datamMap);
			CsvUtil.createCSVFile(exprotList, map, outPutPath, fileName);
		}
	}
}
