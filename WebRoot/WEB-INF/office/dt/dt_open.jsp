<%@ page language="java" import="java.util.*,com.zhuozhengsoft.pageoffice.*" pageEncoding="gb2312"%>
<%@page import="com.zhuozhengsoft.pageoffice.excelwriter.*"%>
<%@page import="java.awt.Color"%>
<%@page import="java.text.*"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//设置服务器页面
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
//打开Word文档
String path = (String)request.getAttribute("tempPath");
if(path.endsWith("xls")||path.endsWith("xlsx")){
	poCtrl.webOpen(request.getContextPath()+"/"+path, OpenModeType.xlsNormalEdit, "");
}else if(path.endsWith("doc")||path.endsWith("docx")){
	poCtrl.webOpen(request.getContextPath()+"/"+path, OpenModeType.docNormalEdit, "");
}if(path.endsWith("ppt")||path.endsWith("pptx")){
	poCtrl.webOpen(request.getContextPath()+"/"+path, OpenModeType.pptNormalEdit, "");
}
poCtrl.setTagId("PageOfficeCtrl1");//此行必需
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
  </head>
<body>
    <form id="form1">
    <div style=" width:100%; height:700px;">
        <po:PageOfficeCtrl id="PageOfficeCtrl1">
        </po:PageOfficeCtrl>
    </div>
    </form>
</body>
</html>

