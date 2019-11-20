<%@ page language="java" import="java.util.*,com.zhuozhengsoft.pageoffice.*" pageEncoding="gb2312"%>
<%@page import="com.zhuozhengsoft.pageoffice.excelwriter.*"%>
<%@page import="java.awt.Color"%>
<%@page import="java.text.*"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//设置服务器页面
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
//添加自定义按钮
poCtrl.addCustomToolButton("确定","Save",1);
cn.demi.base.system.vo.TemplateVo t = (cn.demi.base.system.vo.TemplateVo)request.getAttribute("vo");

//设置保存页面
poCtrl.setSaveFilePage("ajaxSaveFile.do?id="+t.getId()+"&type="+t.getType()+"&code="+t.getCode()+"&name="+t.getName());
//打开Word文档
String tempPath = (String)request.getAttribute("tempPath");
String suffix = tempPath.substring(tempPath.lastIndexOf("."), tempPath.length());
System.out.print(suffix);
if(suffix.contains("xls")){
	poCtrl.webOpen(request.getContextPath()+"/"+tempPath, OpenModeType.xlsNormalEdit, "");
}else if(suffix.contains("doc")){
	poCtrl.webOpen(request.getContextPath()+"/"+tempPath, OpenModeType.docNormalEdit, "");
}else if(suffix.contains("ppt")){
	poCtrl.webOpen(request.getContextPath()+"/"+tempPath, OpenModeType.pptNormalEdit, "");
}
poCtrl.setTagId("PageOfficeCtrl1");//此行必需
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>编辑模板文件</title>
  </head>
<body>
    <script type="text/javascript">
        function Save() {
            document.getElementById("PageOfficeCtrl1").WebSave();
        }
    </script>
    <form id="form1">
    <div style=" width:100%; height:700px;">
        <po:PageOfficeCtrl id="PageOfficeCtrl1">
        </po:PageOfficeCtrl>
    </div>
    </form>
</body>
</html>

