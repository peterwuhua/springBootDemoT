<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"%>
<%@page import="java.awt.Color"%>
<%@page import="java.text.*"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//设置服务器页面
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
poCtrl.addCustomToolButton("全屏切换", "setFullScreen()", 4);  
poCtrl.addCustomToolButton("-", "", 0);
poCtrl.addCustomToolButton("关闭", "Close", 21);
/* //隐藏Office工具条
poCtrl.setOfficeToolbars(false);
//隐藏自定义工具栏
poCtrl.setCustomToolbar(false); */ 
poCtrl.setOfficeToolbars(false);//隐藏Office工具条
poCtrl.setAllowCopy(false);

poCtrl.setTitlebar(false); //隐藏标题栏
poCtrl.setMenubar(true); //隐藏菜单栏

poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");

String tempPath = (String)request.getAttribute("tempPath"); 
poCtrl.webOpen(request.getContextPath()+"/"+tempPath, OpenModeType.docReadOnly, "");
 
poCtrl.setTagId("PageOfficeCtrl1");//此行必需
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>编辑模板文件</title>
  </head>
<body>
    <script type="text/javascript">
    function AfterDocumentOpened() {
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(3, false); // 禁止保存
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(4, true); // 禁止另存
	    document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(5, true); //禁止打印
        document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(6, true); // 禁止页面设置
        document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;  
    }
    function Close() {
    	window.external.close();
    }
    
    function setFullScreen() {  
        document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;  
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

