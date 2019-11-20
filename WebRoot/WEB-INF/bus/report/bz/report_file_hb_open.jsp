<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="utf-8"%>
<%
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//设置服务器页面
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");

WordDocument worddoc = new WordDocument();
DataRegion data = worddoc.openDataRegion("PO_BUG_PAGE");
String jl=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getAttribute("bugPath");
data.setValue("[word]"+jl+"[/word]");
poCtrl.setWriter(worddoc);

//添加自定义按钮
poCtrl.addCustomToolButton("保存","Save",1);
String id = (String)request.getAttribute("id");
//设置保存页面
poCtrl.setSaveFilePage("ajaxSaveFile.do?id="+id);

poCtrl.addCustomToolButton("全屏切换", "setFullScreen()", 4);  
poCtrl.addCustomToolButton("-", "", 0);
poCtrl.addCustomToolButton("保存并关闭", "SaveAndClose", 21);
poCtrl.addCustomToolButton("关闭", "Close", 21);

poCtrl.setTitlebar(false); //隐藏标题栏
poCtrl.setMenubar(true); //隐藏菜单栏
//打开文档之后执行全屏
poCtrl.setJsFunction_AfterDocumentOpened("setFullScreen");
String tempPath = (String)request.getAttribute("tempPath");
poCtrl.webOpen(request.getContextPath()+"/"+tempPath, OpenModeType.docNormalEdit, "");

poCtrl.setTagId("PageOfficeCtrl1");//此行必需
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>在线编辑报告</title>
<script type="text/javascript">
function Save() {
	document.getElementById("PageOfficeCtrl1").WebSaveAsPDF();
    document.getElementById("PageOfficeCtrl1").WebSave();
}
function setFullScreen() {  
	 document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(3, false); // 禁止保存
     document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(4, true); // 禁止另存
	    document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(5, true); //禁止打印
     document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(6, true); // 禁止页面设置
    document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;  
}
function SaveAndClose() {
	document.getElementById("PageOfficeCtrl1").WebSaveAsPDF();
	document.getElementById("PageOfficeCtrl1").WebSave();
	window.external.close();
}
function Close() {
	window.external.close();
}
 
//文档关闭前先提示用户是否保存
function BeforeBrowserClosed() {
	if (document.getElementById("PageOfficeCtrl1").IsDirty) {
		if (confirm("提示：文档已被修改，是否继续关闭放弃保存 ？")) {
			return true;

		} else {

			return false;
		}

	}

}
</script>
  </head>
<body>
    <form id="form1">
	    <div style=" width:100%; height:700px;">
	        <po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
	    </div>
    </form>
</body>
</html>

